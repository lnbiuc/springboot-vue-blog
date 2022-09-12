<template>
  <footer>
    <div class="countInfo">
      <div class="statistic">
        <div class="value" style="color:orange"><i class="clock icon"></i>{{ existTime }}d</div>
        <div class="label"><p class="describe">网站存活</p></div>
      </div>
      <div class="statistic">
        <div class="value" style="color:royalblue"><i class="calendar check outline icon"></i>{{ totalViews }}</div>
        <div class="label"><P class="describe">总阅读量</P></div>
      </div>
      <div class="statistic">
        <div class="value" style="color:salmon"><i class="file alternate icon"></i>{{ totalBlogs }}</div>
        <div class="label"><p class="describe">总博客数</p></div>
      </div>
      <div class="statistic">
        <div class="value" style="color:green"><i class="tags icon"></i>{{ totalTags }}</div>
        <div class="label"><p class="describe">总标签数</p></div>
      </div>
    </div>
    <div class="create">
      <span>Copyright © ️2022️&nbsp;|&nbsp;Violet&nbsp;|&nbsp;</span>
      <span><a href="#">陕ICP备2021015553号-1</a></span>
    </div>
  </footer>
</template>

<script>
import {getCountInfo} from "../axios";
import {
  AreaChartOutlined,
  FieldTimeOutlined,
  FileMarkdownOutlined,
  TagsOutlined,
} from "@ant-design/icons-vue";

export default {
  name: "Footer",
  components: {
    AreaChartOutlined,
    FieldTimeOutlined,
    FileMarkdownOutlined,
    TagsOutlined,
  },
  data() {
    return {
      totalViews: "",
      existTime: "",
      totalBlogs: "",
      totalTags: "",
    };
  },
  created() {
    getCountInfo().then((res) => {
      this.totalViews = res.data.data.totalViews;
      this.existTime = res.data.data.existTime;
      this.totalBlogs = res.data.data.totalBlogs;
      this.totalTags = res.data.data.totalTags;
    });
  },
};
</script>

<style scoped>
footer {
  width: 100%;
  display: flex;
  flex-direction: row;
  text-align: center;
  justify-content: space-between;
  align-items: center;
  backdrop-filter: saturate(50%) blur(8px);
  background: rgba(255, 255, 255, .7);
  box-shadow: 0 0 2px 0 rgba(0, 0, 0, 0.18);
}

.countInfo {
  display: flex;
  flex-direction: row;
  margin-top: 10px;
}

.create {
  font-size: 18px;
  margin-right: 40px;
}

.value {
  font-size: 25px;
}

.label {
  font-size: 15px;
}

.statistic {
  margin: 0 25px;
}

.describe {
  margin-bottom: 10px;
}

@media screen and (max-width: 1000px) {
  footer {
    flex-direction: column;
  }

  .describe {
    display: none;
  }
}

@media (prefers-color-scheme: dark) {
  footer {
    color: #fff !important;
    background: rgba(36, 36, 36, .7);
    box-shadow: 0 1px 10px rgba(0, 0, 0, 0.05);
  }
}
</style>
