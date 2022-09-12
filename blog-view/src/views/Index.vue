<template>
  <div class="site">
    <!--    <HeaderTdFull v-if="pc"></HeaderTdFull>-->
    <!--    <HeaderTdPhone v-if="phone"></HeaderTdPhone>-->
    <HeaderRebuild v-if="pc"></HeaderRebuild>
    <HeaderRebuildPhone v-if="phone"></HeaderRebuildPhone>
    <router-view></router-view>
    <BackTop :duration="1000"/>
  </div>
  <Footer></Footer>
</template>

<script>
import {BackTop} from "ant-design-vue";
import Header from "../components/Header.vue";
import HeaderTdFull from "../components/HeaderTdFull.vue";
import HeaderTdPhone from "../components/HeaderTdPhone.vue";
import Footer from "../components/Footer.vue";
import About from "../components/About.vue";
import HeaderRebuild from "../components/HeaderRebuild.vue";
import HeaderRebuildPhone from "../components/HeaderRebuildPhone.vue";
import {ElNotification} from "element-plus";

export default {
  name: "Index",
  components: {HeaderRebuildPhone, HeaderRebuild, Header, BackTop, HeaderTdFull, HeaderTdPhone, Footer, About},
  data() {
    return {
      pc: true,
      phone: false,
    };
  },
  methods: {
    setHeader() {
      let width = document.documentElement.clientWidth;
      if (width < 1000) {
        this.phone = true;
        this.pc = false;
        return
      }
      ElNotification.warning({
        title: 'Notification',
        message: "请在chrome浏览器中打开此页面。" +
            "safari和firefox可能会遇到显示bug和索引无法跳转bug，未在edge中测试！",
        position: 'top-right',
        duration: 20000
      })
    },
  },
  created() {
    this.setHeader();
  },
};
</script>

<style scoped>
.site {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}
</style>
