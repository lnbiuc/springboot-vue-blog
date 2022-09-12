<template>
  <div class="occupancy"></div>
  <div class="head">
    <div class="logo">Violet's Blog</div>
    <div class="links">
      <a class="link" @click="visible = true">
        <menu-fold-outlined/>
      </a>
      <!--      <a class="link" v-if="!open">-->
      <!--        <menu-unfold-outlined/>-->
      <!--      </a>-->
    </div>
  </div>

  <el-drawer v-model="visible" :show-close="false" size="250" class="drawer">
    <template #header="{ close, titleId, titleClass }">
      <a class="close" @click="visible = false">
        <menu-unfold-outlined/>&nbsp;&nbsp;&nbsp;&nbsp;关闭
      </a>
    </template>
    <div class="navs">
      <a class="nav" @click="index(1);visible = false">
        <home-filled/>
        主页</a>
      <a class="nav" @click="index(2);visible = false">
        <switcher-filled/>
        归档</a>
      <a class="nav" @click="index(3);visible = false">
        <hdd-filled/>
        分类</a>
      <a class="nav" @click="index(4);visible = false">
        <meh-filled/>
        关于</a>
      <a class="nav" @click="index(6);visible = false">
        <message-filled/>
        留言板</a>
      <a @click="index(5);visible = false" class="nav">
        <search-outlined/>
        搜索</a>
      <a @click="goGithub();visible = false" class="nav">
        <github-outlined/>
        Github
      </a>
    </div>
  </el-drawer>
</template>

<script>
import {
  DownSquareFilled,
  DownSquareOutlined,
  HddFilled,
  HomeFilled,
  MehFilled,
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  SafetyCertificateFilled,
  SwitcherFilled,
  SearchOutlined,
  GithubOutlined,
  MessageFilled
} from "@ant-design/icons-vue";
import {ElDrawer} from 'element-plus'

export default {
  name: "HeaderRebuildPhone",
  components: {
    MenuFoldOutlined, MenuUnfoldOutlined, DownSquareOutlined,
    HomeFilled,
    SwitcherFilled,
    HddFilled,
    MehFilled,
    SafetyCertificateFilled,
    DownSquareFilled,
    SearchOutlined,
    GithubOutlined,
    MessageFilled
  },
  data() {
    return {
      open: false,
      visible: false
    }
  },
  methods: {
    goGithub() {
      window.open("https://github.com/lnbiuc");
    },
    index(index) {
      if (index === 1) {
        this.$router.push("/home");
      } else if (index === 2) {
        this.$router.push("/timeLine");
      } else if (index === 3) {
        this.$router.push("/filing");
      } else if (index === 4) {
        this.$router.push('/about')
      } else if (index === 5) {
        this.$router.push('/search');
      } else if (index === 6) {
        this.$router.push('/message');
      }
    },
  },
}
</script>

<style scoped>
.occupancy {
  height: 60px;
}

.head {
  position: fixed;
  top: 0;
  height: 60px;
  width: 100%;
  display: flex;
  flex-direction: row;
  z-index: 10;
  backdrop-filter: saturate(50%) blur(8px);
  background: rgba(255, 255, 255, .7);
  box-shadow: 0 1px 10px rgba(0, 0, 0, 0.05)
}

.logo {
  display: flex;
  width: 200px;
  align-items: center;
  margin-left: 20px;
  font-size: 25px;
  color: #6A67CE;
  font-weight: 700;
  overflow: hidden;
}

.navs {
  flex: 5;
  display: flex;
  align-items: center;
  padding-left: 20px;
  flex-direction: column;
}

.nav {
  padding: 10px 10px;
  font-size: 20px;
  display: block;
  width: 100%;
}

.nav:hover {
  background-color: #4285f4;
  color: white;
}

.links {
  flex: 2;
  display: flex;
  align-items: center;
  justify-content: right;
}

.link, .close {
  font-size: 25px;
  margin: 0 10px;
  color: #6A67CE;
}

.link:hover {
  color: #4285f4;
}

.link:checked {
  transform: rotate(180deg);
}

.close:hover {
  color: #4285f4;
}

.close:checked {
  transform: rotate(180deg);
}

@media (prefers-color-scheme: dark) {
  .head {
    background: rgba(36, 36, 36, .7);
    box-shadow: 0 1px 10px rgba(0, 0, 0, 0.05);
  }

  .nav {
    color: #dcdcdc;
  }
}
</style>