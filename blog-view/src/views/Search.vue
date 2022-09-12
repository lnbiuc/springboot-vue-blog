<template>
  <SecondImg></SecondImg>
  <div class="mainContent">
    <div class="head">搜索</div>
    <div class="searchGroup">
      <div class="t-demo-tabs">
        <div class="t-demo-tabs__desc">
          <t-radio-group v-model="index" variant="default-filled">
            <t-radio-button value='1'> 标题</t-radio-button>
            <t-radio-button value='2'> 分类</t-radio-button>
            <t-radio-button value='3'> 标签</t-radio-button>
          </t-radio-group>
        </div>
      </div>
      <div class="searchInput">
        <a-input-search
            v-model:value="params"
            enter-button="Search"
            placeholder="输入关键词搜索"
            size="large"
            @search="search"
        />
      </div>
      <div class="total">
        <p>共找到&nbsp;<span style="color: #4285f4;">{{ total }}&nbsp;</span>条记录</p>
      </div>
    </div>
  </div>
  <SearchBlogs :blogs="blogs"></SearchBlogs>
</template>

<script>
import BreadCrumbs from '../components/BreadCrumbs.vue';
import SecondImg from "../components/SecondImg.vue";
import Blogs from "../components/Blogs.vue";
import {searchTagName, searchTitle, searchFilingName} from "../axios";
import SearchBlogs from "../components/SearchBlogs.vue";

export default {
  name: "Search",
  components: {SearchBlogs, SecondImg, BreadCrumbs, Blogs},
  data() {
    return {
      index: '',
      params: '',
      blogs: {},
      total: 0
    }
  },
  methods: {
    search() {
      this.blogs = {};

      if (this.index === '1') {
        this.searchTitle();
        return;
      }
      if (this.index === '2') {
        this.searchFiling();
        return;
      }
      if (this.index === '3') {
        this.searchTag();
        return;
      }
      this.searchTitle();
    },
    searchTitle() {
      searchTitle(this.params).then(res => {
        this.total = res.data.data.total;
        this.blogs = res.data.data.data;
      })
    },
    searchFiling() {
      searchFilingName(this.params).then(res => {
        this.total = res.data.data.total;
        this.blogs = res.data.data.data;
      })
    },
    searchTag() {
      searchTagName(this.params).then(res => {
        this.total = res.data.data.total;
        this.blogs = res.data.data.data;
      })
    }
  }
}
</script>

<style scoped>
.mainContent {
  width: 50vw;
  margin: 50px auto;
  box-shadow: 1px 0 10px 10px rgb(30, 30, 30, 0.1);
  padding: 25px 25px;
  border-radius: 5px;
  position: relative;
  background-color: #fff;
}

.head {
  font-size: 35px;
  text-align: center;
  font-weight: 400;
}

.searchGroup {
  margin: 10px 50px;
  display: flex;
  flex-direction: column;
  align-content: space-between;
}

.searchInput {
  margin-top: 20px;
}

.total {
  margin-top: 10px;
}

@media screen and (max-width: 1000px) {
  .mainContent {
    width: 95%;
    margin: 20px auto;
  }
}

@media (prefers-color-scheme: dark) {
  .mainContent {
    background-color: #333 !important;
    color: #fff !important;
  }
}
</style>