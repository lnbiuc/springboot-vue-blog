<template>
  <t-card id="mainBg" :bordered="false" :cover="blog.bgImg" size="small">
  </t-card>
  <AnchorBlog :nav="nav"></AnchorBlog>
  <div class="content ui raised segment">
    <div class="header">
      <div class="title">{{ blog.title }}</div>
      <div class="info">
        <span style="color: royalblue"><UserOutlined/></span>&nbsp;
        <span>{{ blog.authorVo.username }}</span
        >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <span><LineChartOutlined/></span>&nbsp;
        <span style="color: orange">{{ blog.visitsCount }}</span
        >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span
          class="releaseTime"
          style="color: red"><ClockCircleOutlined/></span>&nbsp;
        <span class="releaseTime">{{ blog.releaseTime }}</span>
      </div>
      <div class="tags">
        <t-tag v-for="tag in blog.tagNames" :key="tag.tagId" class="tag">
          <template #icon>
            <t-icon name="discount"/>
          </template>
          &nbsp;{{ tag.tagName }}
        </t-tag>
      </div>
    </div>
    <div class="filing">
      <a class="ui orange ribbon label" @click="toFiling">
        <FolderOpenOutlined/>&nbsp;{{ blog.filingName.filingName }}
      </a>
    </div>
    <t-divider>正文</t-divider>
    <div id="nav"></div>
    <div class="contnent" v-html="content"></div>
    <t-divider>END</t-divider>
  </div>
</template>
<script>
import {getArticleByArticleId} from "../axios";
import MarkdownIt from "markdown-it";
import hljs from "highlight.js";
import Clipboard from 'clipboard'
import emoji from 'markdown-it-emoji'
import ins from 'markdown-it-ins'
import footnote from 'markdown-it-footnote'
import sub from 'markdown-it-sub'
import sup from 'markdown-it-sup'
import list from 'markdown-it-deflist'
import addr from 'markdown-it-abbr'
import mark from 'markdown-it-mark'
import anchor from 'markdown-it-anchor'
import toc from 'markdown-it-toc-done-right'
import s from 'string'
import table from 'markdown-it-multimd-table'
import {
  UserOutlined,
  EyeOutlined,
  LikeOutlined,
  ClockCircleOutlined,
  LineChartOutlined,
  FolderOpenOutlined,
} from "@ant-design/icons-vue";
import BreadCrumbs from "../components/BreadCrumbs.vue";
import AnchorBlog from "../components/AnchorBlog.vue";

export default {
  name: "BlogDetail",
  components: {
    AnchorBlog,
    UserOutlined,
    EyeOutlined,
    LikeOutlined,
    ClockCircleOutlined,
    LineChartOutlined,
    FolderOpenOutlined,
    BreadCrumbs,
  },
  data() {
    return {
      articleId: "default",
      blog: {},
      content: "",
      indexList: {},
      clipboard: '',
      nav: {}
    };
  },
  methods: {
    home() {
      this.$router.push("/");
    },
    toFiling() {
      this.$router.push("/filing");
    }
  },
  created() {
    this.articleId = this.$route.params.articleId;
    getArticleByArticleId(this.articleId).then((res) => {
      this.blog = res.data.data;
      const md = new MarkdownIt({
        html: true,
        linkify: true,
        typographer: true,
        highlight: function (str, lang) {
          // 当前时间加随机数生成唯一的id标识
          const codeIndex = parseInt(Date.now()) + Math.floor(Math.random() * 10000000)
          // 复制功能主要使用的是 clipboard.js
          let html = `<a class="copy-btn" type="button" data-clipboard-action="copy" data-clipboard-target="#copy${codeIndex}"><i class="copy outline icon"></i></a>`
          const linesLength = str.split(/\n/).length - 1
          // 生成行号
          let linesNum = '<span aria-hidden="true" class="line-numbers-rows">'
          for (let index = 0; index < linesLength; index++) {
            linesNum = linesNum + '<span></span>'
          }
          linesNum += '</span>'
          if (lang && hljs.getLanguage(lang)) {
            try {
              const preCode = hljs.highlight(lang, str, true).value
              html = html + preCode
              if (linesLength) {
                html += '<b class="name">' + lang + '</b>'
              }
              return `<pre class="hljs"><code>${html}</code>${linesNum}</pre><textarea style="position: absolute;top: -9999px;left: -9999px;z-index: -9999;" id="copy${codeIndex}">${str.replace(/<\/textarea>/g, '&lt;/textarea>')}</textarea>`
            } catch (error) {
              console.log(error)
            }
          }

          const preCode = md.utils.escapeHtml(str)
          html = html + preCode
          return `<pre class="hljs"><code>${html}</code>${linesNum}</pre><textarea style="position: absolute;top: -9999px;left: -9999px;z-index: -9999;" id="copy${codeIndex}">${str.replace(/<\/textarea>/g, '&lt;/textarea>')}</textarea>`
        }
      })
      md.use(emoji)
      md.use(ins)
      md.use(footnote)
      md.use(sub)
      md.use(sup)
      md.use(list)
      md.use(addr)
      md.use(mark)
      md.use(table)
      var string = s

      function legacySlugify(s) {
        return string(s).slugify().toString();
      }

      md.use(anchor, {permalink: true, permalinkBefore: true, permalinkSpace: false})

      const _this = this;
      md.use(toc,
          {
            containerClass: 'toc',//生成的容器的类名，这样最后返回来的字符串是 <nav class="toc"><nav/>
            containerId: 'toc',//生成的容器的ID，这样最后返回来的字符串是 <nav id="toc"><nav/>
            listType: 'ul',//导航列表使用ul还是ol
            listClass: 'ulClass',//li标签的样式名
            linkClass: 'aClass',//a标签的样式名
            level: [1, 2, 3],
            callback: function (str, ast) {
              _this.nav = str
            }
          }
      );
      this.content = md.render(this.blog.content)
    });
  },
  mounted() {
    this.$nextTick(() => {
      this.clipboard = new Clipboard('.copy-btn')
      // 复制成功失败的提示
      this.clipboard.on('success', (e) => {
        this.$message.success('复制成功')
      })
      this.clipboard.on('error', (e) => {
        this.$message.error('复制失败')
      })
    })
  },
  beforeDestroy() {
    this.clipboard.destroy()
  },
  destroy() {
    this.clipboard.destroy()
  },
  beforeRouteLeave() {
    this.clipboard.destroy()
  }
};
</script>
<style scoped>
#mainBg {
  position: absolute;
  z-index: 0;
}

.content {
  box-shadow: 1px 0 10px 10px rgb(30, 30, 30, 0.1);
  width: 65%;
  margin: 50px auto;
  padding: 50px 50px;
  font-size: 120%;
  z-index: 2;
  border-radius: 5px;
  background-color: #fff;
}

.title {
  text-align: center;
  font-size: 40px;
}

.info {
  text-align: center;
}

.tags {
  float: right;
}

.tag {
  margin-left: 10px;
}

.filing {
  position: absolute;
  left: 13px;
  z-index: 13;
}


@media screen and (max-width: 1000px) {
  .content {
    width: 95%;
    margin: 20px auto;
    padding: 20px 10px;
  }

  .releaseTime {
    display: none;
  }
}

.contnent {
  overflow-x: scroll;
  overflow-y: hidden;
  scroll-behavior: smooth;
}

::-webkit-scrollbar {
  display: none; /* Chrome Safari */
}

@media (prefers-color-scheme: dark) {
  .contnent, .content {
    background-color: #0d1117 !important;
    color: #fff !important;
  }
}
</style>
<style lang="scss">
pre.hljs:hover {
  b.name {
    display: none;
    transition: all 500ms;
  }

  .copy-btn {
    display: block;
    transition: all 500ms;
  }
}

pre.hljs {
  padding: 12px 2px 12px 40px !important;
  border-radius: 5px !important;
  position: relative;
  font-size: 17px !important;
  line-height: 22px !important;
  overflow: hidden !important;
  background-color: #161b22 !important;

  code {
    display: block !important;
    margin: 0 10px !important;
    overflow-x: auto !important;

    &::-webkit-scrollbar {
      z-index: 11;
      width: 6px;
    }

    &::-webkit-scrollbar:horizontal {
      height: 6px;
    }

    &::-webkit-scrollbar-thumb {
      border-radius: 5px;
      width: 6px;
      background: #666;
    }

    &::-webkit-scrollbar-corner, &::-webkit-scrollbar-track {
      background: #1E1E1E;
    }

    &::-webkit-scrollbar-track-piece {
      background: #1E1E1E;
      width: 6px
    }
  }

  .line-numbers-rows {
    position: absolute;
    pointer-events: none;
    top: 12px;
    bottom: 12px;
    left: 0;
    font-size: 100%;
    width: 40px;
    text-align: center;
    letter-spacing: -1px;
    border-right: 1px solid rgba(0, 0, 0, .66);
    user-select: none;
    counter-reset: linenumber;

    span {
      pointer-events: none;
      display: block;
      counter-increment: linenumber;

      &:before {
        content: counter(linenumber);
        color: #999;
        display: block;
        text-align: center;
      }
    }
  }

  b.name {
    position: absolute;
    top: 2px;
    right: 8px;
    z-index: 10;
    color: #999;
    pointer-events: none;
  }

  .copy-btn {
    position: absolute;
    top: 0;
    right: 0;
    z-index: 10;
    border-radius: 5px;
    border: 1px solid #5b5b5b;
    padding: 5px 5px;
    color: #5b5b5b;
    font-size: 20px;
    margin: 5px 5px;
    display: none;
  }

  .copy-btn:hover {
    color: #fff;
    border: 1px solid #fff;
    transition: all 200ms;
  }

  .copy-btn:active {
    color: #62905e;
    border: 1px solid #62905e;
  }

  .copy-btn:focus {
    color: #62905e;
    border: 1px solid #62905e;
  }
}
</style>
