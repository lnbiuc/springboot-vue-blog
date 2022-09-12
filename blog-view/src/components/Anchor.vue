<template>
  <div class="affix-container">
    <div ref="affixContainerRef" class="affix-container-demo1">
      <t-affix
          ref="affixRef"
          :offset-bottom="-2000"
          :offset-top="90">
        <t-anchor>
          <div class="mainContent">
            <div class="fixed">
              <div class="head">
                <BarsOutlined/>&nbsp;索引
              </div>
              <hr/>
              <t-anchor-item
                  v-for="blog in blogs"
                  :href="'#' + blog.articleId"
                  :title="blog.title"/>
            </div>
          </div>
        </t-anchor>
      </t-affix>

    </div>
  </div>
</template>
<script>
import BarsOutlined from "@ant-design/icons-vue/lib/icons/BarsOutlined";
import TagsOutlined from "@ant-design/icons-vue/lib/icons/TagsOutlined";

export default {
  name: "Anchor",
  components: {BarsOutlined, TagsOutlined},
  props: ["blogs"],
};
</script>
<style scoped>
.affix-container {
  position: relative;
  background-color: #fff;
}

.affix-container-demo1 {
  position: absolute;
  left: 69.5vw;
  zoom: 110%;
  z-index: 19;
}

.mainContent {
  overflow: hidden;
  width: 15vw;
  display: flex;
  flex-direction: column;
  box-shadow: 1px 0 5px 2px rgb(30, 30, 30, 0.1);
  border-radius: 3px;
  background-color: #ffffff;
}

.fixed {
  flex: 3;
  padding: 10px 10px;
}

.head {
  text-align: center;
  margin: 5px;
  font-size: 15px;
  color: #4285f4;
}

@media screen and (max-width: 1000px) {
  .affix-container {
    display: none;
  }
}

@media (prefers-color-scheme: dark) {
  .mainContent {
    background-color: #222 !important;
    color: #fff !important;
  }
}
</style>
