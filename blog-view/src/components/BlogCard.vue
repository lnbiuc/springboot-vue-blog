<template>
  <div class="mainCard" v-for="blog in blogs">
    <div class="head">
      <p>{{ blog.title }}</p>
      <p>{{ blog.introduction }}<span style="font-size: 70%;float: right">{{ blog.releaseTime }}</span></p>
    </div>
    <div class="img">
      <img :src="blog.bgImg" alt="img">
    </div>
    <div class="tag">

    </div>
  </div>
</template>

<script>
export default {
  name: "BlogCard",
  props: ['blogs']
}
</script>

<style scoped>
.mainCard {
  width: 80%;
  min-height: 300px;
  box-shadow: 1px 0 10px 10px rgb(30, 30, 30, 0.1);
  margin-top: 30px;
  border-right: 3px;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.head {
  flex: 1;
}

.img {
  flex: 4;
  overflow: hidden;
}

.tag {
  flex: 1;
}
</style>