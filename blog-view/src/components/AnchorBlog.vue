<template>
  <div class="relative">
    <div class="fixed">
      <div class="head">
        <span style="color: #6A67CE;font-weight: 600">&nbsp;<BarsOutlined/>&nbsp;索引</span>
      </div>
      <div style="height: 70px"></div>
      <div class="nav" v-html="nav"></div>
    </div>
  </div>
</template>
<script>
import BarsOutlined from "@ant-design/icons-vue/lib/icons/BarsOutlined";
import TagsOutlined from "@ant-design/icons-vue/lib/icons/TagsOutlined";

export default {
  name: "AnchorBlog",
  components: {BarsOutlined, TagsOutlined},
  props: ["nav"]
};
</script>
<style scoped>
.relative {
  position: relative;
}

.fixed {
  position: fixed;
  right: 1%;
  display: flex;
  flex-direction: column;
  top: 110px;
  width: 16%;
  overflow: scroll;
  height: 75vh;
  backdrop-filter: saturate(50%) blur(8px);
  background: rgba(255, 255, 255, .7);
  box-shadow: 0 0 5px 3px rgb(30, 30, 30, 0.1);
  border-radius: 5px;
}


::-webkit-scrollbar {
  display: none;
}

.nav {
  overflow: scroll;
}

.head {
  display: flex;
  flex-direction: column;
  flex: 1;
  text-align: center;
  margin: 0 auto;
  padding: 10px 10px;
  position: fixed;
  color: white;
  font-size: 20px;
}

@media screen and (max-width: 1000px) {
  .relative {
    display: none;
  }
}

@media (prefers-color-scheme: dark) {
  .fixed {
    background: rgba(36, 36, 36, .7);
    box-shadow: 0 1px 10px rgba(0, 0, 0, 0.05);
  }
}
</style>
