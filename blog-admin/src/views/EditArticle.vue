<template>
  <div class="publishContent">
    <div class="titleForm">
      <el-form :model="ruleForm" :rules="rules" ref="baseForm">
        <el-form-item label="名称" prop="title">
          <el-input v-model="ruleForm.title"/>
        </el-form-item>
        <el-form-item label="简介" prop="introduction">
          <el-input v-model="ruleForm.introduction"/>
        </el-form-item>
      </el-form>
      <div id="edit">
        <v-md-editor
            v-model="ruleForm.content"
            :disabled-menus="[]"
            @upload-image="handleUploadImage"
            height="400px">
        </v-md-editor>
      </div>
    </div>
    <div class="rightForm">
      <div class="selectForm">
        <el-form>
          <el-form-item label="分类">
            <el-select v-model="ruleForm.filingName"
                       class="m-2"
                       allow-create
                       clearable
                       filterable
                       default-first-option
                       placeholder="选择分类">
              <el-option
                  v-for="filing in info.filingNames"
                  :key="filing.id"
                  :label="filing.filingName"
                  :value="filing.filingName"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="标签">
            <el-select v-model="ruleForm.tag"
                       class="m-2"
                       allow-create
                       multiple
                       clearable
                       default-first-option
                       filterable
                       multiple-limit="4"
                       placeholder="选择分类">
              <el-option
                  v-for="tag in info.tags"
                  :key="tag.tagId"
                  :label="tag.tagName"
                  :value="tag.tagName"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="背景">
            <el-input style="width: 219px" v-model="ruleForm.bgImg"/>
          </el-form-item>
        </el-form>
      </div>
      <div class="imgUp">
        <el-upload
            :on-error="this.upError"
            :on-success="this.upSuccess"
            action='https://beyondhorizon.top/upload/img'
            auto-upload
            class="upload-demo"
            drag
            enctype="multipart/form-data"
            name="img"
        >
          <el-icon class="el-icon--upload">
            <upload-filled/>
          </el-icon>
          <div class="el-upload__text">
            Drop file here or <em>click to upload</em>
          </div>
        </el-upload>
      </div>
      <div class="submit">
        <el-form>
          <el-form-item>
            <el-button style="width: 100%;" type="primary" @click="confirmOperation()">提 交</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import {UploadFilled} from '@element-plus/icons-vue'
import {ElMessage} from 'element-plus'
import {editArticle, getArticleById, getFiling, getTag, upLoadImg} from "../axios";

export default {
  name: "EditArticle",
  components: {UploadFilled},
  data() {
    return {
      rules: {
        title: [{required: true, message: '不可为空', trigger: 'change'}],
        introduction: [{required: true, message: '不可为空', trigger: 'change'}],
      },
      ruleForm: {
        articleId: '',
        title: '',
        introduction: '',
        content: '',
        filingName: '',
        tag: [],
        bgImg: ''
      },
      info: {
        filingNames: [],
        tags: []
      }
    }
  },
  methods: {
    handleUploadImage(event, insertImage, files) {
      upLoadImg(files).then(res => {
        insertImage({
          url: res.data.data,
          width: 'auto',
          height: 'auto',
          desc: 'image'
        })
      })
    },
    confirmOperation() {
      this.$refs.baseForm.validate((valid) => {
        if (valid) {
          editArticle(this.ruleForm).then(() => {
            ElMessage.success("修改成功")
          })
        }
      })
    },
    getFiling() {
      getFiling().then(res => {
        this.info.filingNames = res.data.data
      })
    },
    getTag() {
      getTag().then(res => {
        this.info.tags = res.data.data
      })
    },
    upSuccess(response, file, fileList) {
      if (response.code === 200) {
        ElMessage.success("上传成功")
        this.ruleForm.bgImg = response.data
      }
    },
    upError(err, file, fileList) {
      let msg = "上传失败" + err
      ElMessage.error("上传失败" + msg)
    },
    loadArticle() {
      getArticleById(this.ruleForm.articleId).then(res => {
        this.ruleForm.title = res.data.data.title
        this.ruleForm.introduction = res.data.data.introduction
        this.ruleForm.content = res.data.data.content
        let tags = [];
        for (let tag of res.data.data.tagNames) {
          tags.push(tag.tagName)
        }
        this.ruleForm.tag = tags
        this.ruleForm.filingName = res.data.data.filingName.filingName
        this.ruleForm.bgImg = res.data.data.bgImg
      })
    }
  },
  created() {
    this.ruleForm.articleId = this.$route.params.articleId;
    this.loadArticle()
    this.getFiling()
    this.getTag()
  }
}
</script>

<style scoped>
.publishContent {
  display: flex;
  flex-direction: row;
  width: 100%;
}

.titleForm {
  display: flex;
  flex-direction: column;
  flex: 8;
  padding: 15px 15px;
}

.rightForm {
  display: flex;
  flex-direction: column;
  flex: 2;
  padding: 15px 15px;
}

@media screen and (max-width: 1000px) {
  .publishContent {
    flex-direction: column;
  }
}
</style>