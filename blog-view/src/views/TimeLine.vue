<template>
  <SecondImg></SecondImg>
  <div class="mainContent">
    <div class="head">文章归档</div>
    <div v-for="time in times" :key="time.timeStrings.year" class="time">
      <div class="year">
        <i class="icon play circle" style="color: rgb(21, 151, 21); font-size: 110%"></i>
        {{ time.timeStrings.year }}年&nbsp;{{ time.timeStrings.month }}月
      </div>
      <div
          v-for="article in time.articleVos"
          :key="article.articleId"
          class="article">
        <div class="line">
          <div class="line2"></div>
        </div>
        <div class="articleInfo">
          <p class="hover">
						<span
                class="releaseTime"
                v-text="littleTimeStr(article.releaseTime)"></span>
            <span class="releaseTimeIcon"><FileDoneOutlined/></span>
            <router-link
                :to="{
								name: 'BlogDetail',
								params: { articleId: article.articleId },}"
                class="hvr-grow title">
              {{ article.title }}
            </router-link>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span class="introduction">{{ article.introduction }}</span>
          </p>
        </div>
      </div>
    </div>
    <div class="year">
      <i class="icon play circle" style="color: rgb(21, 151, 21); font-size: 120%"></i>
      Start !
    </div>
  </div>
</template>

<script>
import {getTimeLineData} from "../axios";
import SecondImg from "../components/SecondImg.vue";
import {FileDoneOutlined} from "@ant-design/icons-vue";
import BreadCrumbs from "../components/BreadCrumbs.vue";

export default {
  name: "TimeLine",
  components: {SecondImg, FileDoneOutlined, BreadCrumbs},
  data() {
    return {
      times: {},
    };
  },
  methods: {
    littleTimeStr(str) {
      var newstring = str.substring(8, 10) + " 日";
      return newstring;
    },
  },
  created() {
    getTimeLineData().then((res) => {
      this.times = res.data.data;
    });
  },
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

.year {
  padding: 5px 5px;
  border: 3px royalblue solid;
  border-radius: 5px;
  width: 175px;
  font-size: 20px;
  font-weight: 800;
  margin: 30px 110px;
}

.line {
  position: relative;
}

.line2 {
  position: absolute;
  width: 5px;
  background-color: rgb(197, 193, 193);
  left: 195px;
  height: 117px;
  top: -30px;
  z-index: 0;
}

.articleInfo {
  position: relative;
  left: 270px;
  font-size: 20px;
  width: 55%;
}

.introduction {
  float: right;
  font-size: 80%;
  color: grey;
}

.hover {
  padding: 10px 0;
}

.releaseTime {
  position: absolute;
  left: -200px;
  font-size: 90%;
  padding: 4px 10px;
  border-radius: 4px;
  background-color: rgb(237, 237, 237);
}

.releaseTimeIcon {
  left: -229px;
  position: absolute;
  font-size: 90%;
  padding: 4px 10px;
  border-radius: 4px;
  background-color: rgb(237, 237, 237);
}

.title {
  padding: 4px 10px;
  border-radius: 4px;
  background-color: rgb(237, 237, 237);
  color: #1E1E1E;
}

.title:hover {
  background-color: #6A67CE;
  color: #ffffff;
}

.head {
  font-size: 35px;
  text-align: center;
  font-weight: 400;
}

@media screen and (max-width: 1000px) {
  .mainContent {
    width: 95%;
    margin-top: 15px;
    overflow: scroll;
  }

  .introduction {
    display: none;
  }

  .year {
    margin: 30px auto;
  }

  .title {
    left: -136px;
    position: relative;
    max-width: 200px;
  }

  .releaseTime {
    left: -250px;
  }

  .releaseTimeIcon {
    left: -221px;
  }

  .line2 {
    height: 110px;
    left: 105px;
  }

  .articleInfo {
    font-size: 15px;
    width: auto;
  }
}

@media (prefers-color-scheme: dark) {
  .mainContent {
    background-color: #141414 !important;
    color: #fff !important;
  }

  .line2 {
    background-color: #444 !important
  }

  .title {
    background-color: #333 !important;
    color: #ddd !important;
  }

  .title:hover {
    background-color: #fff !important;
    color: #444444 !important;
  }

  .releaseTime {
    background-color: #333 !important;
    color: #ddd !important;
  }

  .releaseTimeIcon {
    background-color: #333 !important;
    color: #ddd !important;
  }
}
</style>
