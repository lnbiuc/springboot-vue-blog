<template>
  <t-card class="hvr-wobble-horizontal" id="mainBg" :bordered="false" :cover="img"></t-card>
</template>

<script>
export default {
  name: "MainImg",
  data() {
    return {
      img: "https://typora-1308549476.cos.ap-nanjing.myqcloud.com/img/a2551175c0ffbca69be319962e9a48a66d1efae5.jpeg",
    };
  },
};
</script>

<style scoped>
#mainBg {
  background-size: cover;
  overflow: hidden;
  position: relative;
  z-index: 1;
}
</style>
