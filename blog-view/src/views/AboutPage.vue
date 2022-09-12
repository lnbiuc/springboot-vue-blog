<template>
  <SecondImg></SecondImg>
  <div class="mainContent">
    <div class="head"></div>
    <div class="secondContent">
      <div class="image hvr-grow-rotate">
        <img alt="head" src="https://typora-1308549476.cos.ap-nanjing.myqcloud.com/img/squre-modified.png">
      </div>
      <div class="text">
        <p>写你自己的</p>
      </div>
    </div>
  </div>
</template>

<script>
import SecondImg from "../components/SecondImg.vue";
import BreadCrumbs from "../components/BreadCrumbs.vue";

export default {
  name: "AboutPage",
  components: {SecondImg, BreadCrumbs},
  data() {
    return {}
  },
}
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

.secondContent {
  display: flex;
  flex-direction: column;
}

img {
  width: 200px;
}

.image {
  margin: 30px;
  text-align: center;
}

.text {
  margin: 30px;
  padding: 10px 10px;
}

@media screen and (max-width: 1000px) {
  .mainContent {
    width: 94%;
    margin: 25px auto;
    padding: 10px 10px;
  }

  img {
    width: 150px;
  }
}

h2 {
  color: #6A67CE;
}

h4 {
  color: #4285f4;
}

@media (prefers-color-scheme: dark) {
  .mainContent {
    background-color: #141414 !important;
    color: #fff !important;
  }
}
</style>