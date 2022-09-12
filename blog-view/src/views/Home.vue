<template>
  <MainImg></MainImg>
  <div class="titleBlank"></div>
  <Blogs :blogs="blogs"></Blogs>
  <div class="page">
    <t-pagination
        v-model="current"
        v-model:pageSize="size"
        :pageSizeOptions="pageSizeOptions"
        :total="total"
        @currentChange="page(current,size)"
        @pageSizeChange="page(current,size)"/>
  </div>
  <t-divider>END</t-divider>
  <div class="endBlank"></div>
</template>

<script>
import MainImg from "../components/MainImg.vue";
import Blogs from "../components/Blogs.vue";
import {getBlogList} from "../axios";
import About from "../components/About.vue";
import AnchorTagCloud from "../components/AnchorTagCloud.vue";

export default {
  name: "Home",
  components: {AnchorTagCloud, MainImg, Blogs, About},
  data() {
    return {
      blogs: [],
      total: '',
      size: '',
      current: '1',
      pageSizeOptions: [3, 5, 10]
    }
  },
  methods: {
    page(current, size) {
      this.current = current
      this.size = size
      getBlogList(current, size).then(res => {
        this.blogs = res.data.data.articleVos
        this.total = res.data.data.total
        this.size = res.data.data.pageSize
        this.current = res.data.data.currentPage
      })
    }
  },
  created() {
    this.page(1, 5)
  }
}
</script>

<style scoped>
.page {
  width: 56%;
  margin: 0 auto;
}

.titleBlank {
  height: 50px;
  position: relative;
}

.endBlank {
  height: 30px
}

@media screen and (max-width: 1000px) {
  .page {
    width: 90%;
  }

  .titleBlank {
    height: 20px;
  }

  .endBlank {
    height: 0
  }
}

@media (prefers-color-scheme: dark) {

}
</style>