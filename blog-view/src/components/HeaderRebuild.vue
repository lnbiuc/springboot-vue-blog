<template>
  <div class="occupancy"></div>
  <div class="head">
    <div class="logo">Violet's Blog</div>
    <div class="navs">
      <a class="nav" @click="index(1)">
        <home-filled/>
        主页</a>
      <a class="nav" @click="index(2)">
        <switcher-filled/>
        归档</a>
      <a class="nav" @click="index(3)">
        <hdd-filled/>
        分类</a>
      <a class="nav" @click="index(4)">
        <meh-filled/>
        关于</a>
      <a class="nav" @click="index(6)">
        <message-filled/>
        留言板</a>
    </div>
    <div class="links">
      <a @click="index(5)" class="link">
        <t-icon class="t-menu__operations-icon" name="search"/>
      </a>
      <a @click="goGithub()" class="link">
        <t-icon class="t-menu__operations-icon" name="logo-github-filled"/>
      </a>
    </div>
  </div>
</template>

<script>
import {
  DownSquareOutlined,
  HomeFilled,
  SwitcherFilled,
  HddFilled,
  MehFilled,
  SafetyCertificateFilled,
  DownSquareFilled,
  MessageFilled
} from "@ant-design/icons-vue";

export default {
  name: "HeaderRebuild",
  components: {
    DownSquareOutlined,
    HomeFilled,
    SwitcherFilled,
    HddFilled,
    MehFilled,
    SafetyCertificateFilled,
    DownSquareFilled,
    MessageFilled
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
}

.nav {
  width: 70px;
  margin: 0 15px;
  font-size: 16px;
  color: #1E1E1E;
  display: inline-block;

}

.nav:hover {
  color: #6A67CE;
  font-weight: 750;
  transition: all 0.5s;
}

.links {
  flex: 2;
  display: flex;
  align-items: center;
  justify-content: right;
}

.link {
  font-size: 25px;
  margin: 0 10px;
}

@media (prefers-color-scheme: dark) {
  .head {
    background: rgba(36, 36, 36, .7);
    box-shadow: 0 1px 10px rgba(0, 0, 0, 0.05);
  }

  .nav {
    color: #dcdcdc;
  }

  .t-menu__operations-icon {
    color: #dcdcdc;
  }

  .t-menu__operations-icon:hover {
    background-color: black;
  }
}
</style>