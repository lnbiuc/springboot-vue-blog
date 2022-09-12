<template>
  <div class="affix-container">
    <div ref="affixContainerRef" class="affix-container-demo1">
      <t-affix
          ref="affixRef"
          :offset-bottom="-2000"
          :offset-top="90">
        <t-anchor>
          <div class="mainContent">
            <div class="tagCloud">
              <div class="head">
                <p>
                  <TagsOutlined/>&nbsp;标签云
                </p>
              </div>
              <hr/>
              <div class="tags">
                <t-tag v-for="tag in tags" class="hvr-grow tag" @click="toTag(tag.tagId,tag.tagName)">
                  <template #icon>
                    <t-icon name="discount"/>
                  </template>
                  {{ tag.tagName }}
                </t-tag>
              </div>
            </div>
          </div>
        </t-anchor>
      </t-affix>

    </div>
  </div>
</template>
<script>
import BarsOutlined from "@ant-design/icons-vue/lib/icons/BarsOutlined";
import {getTag} from "../axios";
import TagsOutlined from "@ant-design/icons-vue/lib/icons/TagsOutlined";

export default {
  name: "AnchorTagCloud",
  components: {BarsOutlined, TagsOutlined},
  data() {
    return {
      tags: []
    }
  },
  methods: {
    toTag(params, name) {
      this.$router.push({name: 'Result', params: {type: '标签', params: params, name: name}})
    }
  },
  created() {
    getTag().then(res => {
      this.tags = res.data.data;
    })
  }
};
</script>
<style scoped>
.affix-container {
  position: relative;
  width: 100%;
}

.affix-container-demo1 {
  position: absolute;
  right: 69.5vw;
  zoom: 110%;
  z-index: 19;
}

.mainContent {
  overflow: hidden;
  display: flex;
  flex-direction: column;
  box-shadow: 0 0 10px 1px rgb(30, 30, 30, 0.1);
  border-radius: 3px;
  background-color: #ffffff;
}

.tagCloud {
  flex: 3;
  padding: 10px 10px;
  width: 100%;
}

.tag {
  margin: 4px 4px;
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
