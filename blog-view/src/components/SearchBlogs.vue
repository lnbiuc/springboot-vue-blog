<template>
  <div v-for="blog in blogs" :key="blog.articleId" class="card">
    <div :id="blog.articleId" class="anchor"></div>
    <t-card
        :cover="blog.bgImg"
        :description="blog.introduction"
        :headerBordered="true"
        :style="{ width: '100%', marginBottom: '50px' }"
        :title="blog.title"
        hover-shadow
        size="middle">
      <template #actions>
        <t-tag class="hvr-grow" theme="warning" @click="toFiling(blog.filingName.id,blog.filingName.filingName)">
          <folder-icon/>&nbsp;{{ blog.filingName.filingName }}
        </t-tag>
      </template>
      <template #footer>
        <t-row align="middle" justify="space-between">
          <t-row align="middle" class="tag">
            <div
                v-for="tag in blog.tagNames"
                :key="tag.tagId"
                class="tdesign-demo-block-column">
              <div class="tdesign-demo-block-row">
                <t-tag class="hvr-grow" @click="toTag(tag.tagId,tag.tagName)">
                  <template #icon>
                    <t-icon name="discount"/>
                  </template>
                  {{ tag.tagName }}
                </t-tag>
              </div>
            </div>
          </t-row>
          <a class="detailLink hvr-grow" @click="jump(blog.articleId)">阅读全文</a>
        </t-row>
      </template>
    </t-card>
  </div>
</template>

<script>
import {
  ThumbUpIcon,
  ChatIcon,
  ShareIcon,
  UserIcon,
  BrowseIcon,
  FolderIcon,
} from "tdesign-icons-vue-next";
import {EyeOutlined} from "@ant-design/icons-vue";

export default {
  name: "SearchBlogs",
  props: ["blogs"],
  components: {
    FolderIcon,
    BrowseIcon,
    ThumbUpIcon,
    ChatIcon,
    ShareIcon,
    EyeOutlined,
    UserIcon,
  },
  methods: {
    jump(articleId) {
      this.$router.push({
        name: "BlogDetail",
        params: {articleId: articleId},
      });
    },
    toFiling(params, name) {
      this.$router.push({name: 'Result', params: {type: '分类', params: params, name: name}})
    },
    toTag(params, name) {
      this.$router.push({name: 'Result', params: {type: '标签', params: params, name: name}})
    }
  },
  created() {
    let prefersDarkMode = window.matchMedia('(prefers-color-scheme: dark)').matches;
    if (prefersDarkMode) {
      document.documentElement.setAttribute('theme-mode', 'dark');
    } else {
      document.documentElement.removeAttribute('theme-mode');
    }
  }
};
</script>

<style scoped>
.card {
  width: 50vw;
  margin: 0 auto;
}

.anchor {
  position: relative;
  top: -100px;
}

.tdesign-demo-block-column {
  margin-right: 10px;
}

.detailLink {
  font-size: 17px;
}

@media screen and (max-width: 1000px) {
  .card {
    width: 90%;
    margin: 0 auto;
  }

  .anchor {
    top: -15px;
  }
}

</style>
