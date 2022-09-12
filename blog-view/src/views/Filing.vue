<template>
  <SecondImg></SecondImg>
  <div class="mainContent">
    <div class="head">文章分类</div>
    <div v-for="filing in filings" :key="filing.filingId">
      <div class="oneFiling">
        <a class="ui orange ribbon label" @click="toFiling(filing.filingId,filing.filingName)">
          <FolderOpenOutlined :id="filing.filingName"/>&nbsp;{{ filing.filingName }}
        </a>
        <div class="ui cards">
          <div v-for="article in filing.articles" :key="article.articleId" class="card">
            <div class="content">
              <div class="header">{{ article.title }}<span class="releaseTime">{{ article.releaseTime }}</span></div>
              <div class="description">{{ article.introduction }}</div>
            </div>
            <div class="ui bottom attached button" @click="detail(article.articleId)">
              <FileMarkdownOutlined/>
              查看全文
            </div>
          </div>
        </div>
      </div>
    </div>
    <t-divider>END</t-divider>
  </div>
</template>

<script>
import SecondImg from "../components/SecondImg.vue";
import BreadCrumbs from "../components/BreadCrumbs.vue";
import {getFilingInfo} from "../axios";
import {FolderOpenOutlined, FileMarkdownOutlined} from "@ant-design/icons-vue";

export default {
  name: "Filing",
  components: {BreadCrumbs, SecondImg, FolderOpenOutlined, FileMarkdownOutlined},
  data() {
    return {
      filings: {},
    }
  },
  methods: {
    getFilingInfo() {
      getFilingInfo().then(res => {
        this.filings = res.data.data
      })
    },
    detail(articleId) {
      this.$router.push({
        name: "BlogDetail",
        params: {articleId: articleId},
      });
    },
    toFiling(params, name) {
      this.$router.push({name: 'Result', params: {type: '分类', params: params, name: name}})
    },
  },
  created() {
    this.getFilingInfo()
  }
};
</script>

<style scoped>
.mainContent {
  width: 56%;
  margin: 50px auto;
  box-shadow: 1px 0 10px 10px rgb(30, 30, 30, 0.1);
  padding: 25px 25px;
  border-radius: 5px;
  position: relative;
  background-color: #fff;
  min-height: 100vh;
}

.head {
  font-size: 35px;
  text-align: center;
  font-weight: 400;
}

.cards {
  width: 94.5%;
  margin: 0 auto;
  justify-content: space-between;
}

.releaseTime {
  float: right;
  font-size: 55%;
  color: #777;
}

.oneFiling {
  box-shadow: 1px 0 5px 2px rgb(30, 30, 30, 0.1);
  border-radius: 5px;
  background-color: #fff;
  width: 90%;
  padding: 15px 0;
  margin: 20px auto 50px;
}

@media screen and (max-width: 1000px) {
  .mainContent {
    width: 94%;
    margin: 25px auto;
    padding: 10px 10px;
  }

  .oneFiling {
    margin: 20px auto 30px;
  }

  .ui > .card {
    margin: 20px auto;
  }
}

@media (prefers-color-scheme: dark) {
  .mainContent {
    background-color: #141414 !important;
    color: #fff !important;
  }

  .oneFiling {
    background-color: #222 !important;
    color: #fff !important;
  }

  .card, .header, .description {
    background-color: #222 !important;
    color: #fff !important;
  }

  .button {
    background-color: #222;
    color: #fff;
    border: 1px solid rgba(255, 255, 255, 0.6);
  }

  .button:hover {
    background-color: #fff !important;
    color: #333 !important;
  }
}
</style>
