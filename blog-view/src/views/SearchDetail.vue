<template>
  <SecondImg></SecondImg>
  <div class="mainContent">
    <div class="head">
      <p>{{ type }}&nbsp;<span style="color: #4285f4">{{ name }}</span>&nbsp;下的文章</p>
    </div>
  </div>
  <div class="content">
    <SearchBlogs :blogs="blogs"></SearchBlogs>
  </div>
</template>

<script>
import SecondImg from '../components/SecondImg.vue'
import Breadcrumbs from '../components/Breadcrumbs.vue'
import {searchFilingId, searchTagId} from "../axios";
import BlogCard from "../components/BlogCard.vue";
import Blogs from "../components/Blogs.vue";
import SearchBlogs from "../components/SearchBlogs.vue";

export default {
  name: "SearchDetail",
  components: {SearchBlogs, Blogs, BlogCard, SecondImg, Breadcrumbs},
  data() {
    return {
      type: '',
      params: '',
      name: '',
      blogs: {}
    }
  },
  methods: {
    getInfo(type, params) {
      if (type === '分类') {
        searchFilingId(params).then(res => {
          this.blogs = res.data.data.data;
        })
        return;
      }
      if (type === '标签') {
        searchTagId(params).then(res => {
          this.blogs = res.data.data.data;
        })
      }
    }
  },
  created() {
    this.type = this.$route.params.type;
    this.params = this.$route.params.params;
    this.name = this.$route.params.name;
    this.getInfo(this.type, this.params);
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
  margin: 15px auto;
}

@media screen and (max-width: 1000px) {
  .mainContent {
    width: 90%;
  }
}

@media (prefers-color-scheme: dark) {
  .mainContent {
    background-color: #333 !important;
    color: #fff !important;
  }
}
</style>