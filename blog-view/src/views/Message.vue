<template>
  <SecondImg></SecondImg>
  <div class="mainContent">
    <p class="title">留言板</p>
    <div class="message">
      <t-comment
          class="oneMsg"
          v-for="msg in msgs"
          :avatar="msg.avatar"
          :author="msg.nickname"
          :datetime="msg.time"
          :content="msg.content"
      >
        <template #actions>
        <span key="chat">
        <span>FROM IP: {{ msg.ip }}</span>
      </span>
        </template>
      </t-comment>
    </div>
    <div class="page">
      <t-pagination
          v-model="current"
          v-model:pageSize="size"
          :total="total"
          @currentChange="page(current,size)"
          @pageSizeChange="page(current,size)"/>
    </div>
    <div class="form">
      <el-form :model="ruleForm" :rules="rules" ref="baseForm" status-icon label-width="auto">
        <el-form-item label="QQ" prop="qq">
          <el-input v-model="ruleForm.qq" placeholder="请输入QQ号！会根据QQ号自动获取头像和昵称"/>
        </el-form-item>
        <el-form-item label="留言" prop="content">
          <el-input v-model="ruleForm.content" type="textarea" placeholder="留言"/>
        </el-form-item>
        <el-button style="float: right" @click="confirmOperation" type="primary">发送</el-button>
      </el-form>
    </div>
  </div>
</template>

<script>
import SecondImg from "../components/SecondImg.vue";
import {getMessage, sendMessage} from "../axios";
import {ElMessage} from "element-plus";

export default {
  name: "Message",
  components: {SecondImg},
  data() {
    return {
      total: '',
      size: '',
      current: '1',
      msgs: {},
      rules: {
        qq: [{required: true, message: '不可为空', trigger: 'change'}],
        content: [{required: true, type: 'string', message: '不可为空', trigger: 'change'}],
      },
      ruleForm: {
        qq: '',
        content: ''
      }
    }
  },
  methods: {
    page(current, size) {
      this.current = current
      this.size = size
      getMessage(current, size).then(res => {
        if (res.data.code === 519) {
          window.alert("QQ号或评论内容格式不合法")
          ElMessage.error("QQ号或评论内容格式不合法");
        }
        if (res.data.code === 520) {
          window.alert("QQ号不存在，请输入正确的QQ号")
          ElMessage.error("QQ号不存在，请输入正确的QQ号");
        }
        this.msgs = res.data.data.data
        this.size = res.data.data.size
        this.current = res.data.data.current
        this.total = res.data.data.total;
      })
    },
    confirmOperation() {
      this.$refs.baseForm.validate((valid) => {
        if (valid) {
          sendMessage(this.ruleForm.qq, this.ruleForm.content).then(() => {
            this.page(this.current, this.size)
            this.ruleForm.qq = ''
            this.ruleForm.content = ''
          })
        }
      })
    },
  },
  created() {
    this.page(1, 10)
    let prefersDarkMode = window.matchMedia('(prefers-color-scheme: dark)').matches;
    if (prefersDarkMode) {
      document.documentElement.setAttribute('theme-mode', 'dark');
    } else {
      document.documentElement.removeAttribute('theme-mode');
    }
  }
}
</script>

<style scoped>
.mainContent {
  width: 56%;
  margin: 50px auto;
  box-shadow: 1px 0 10px 10px rgb(30, 30, 30, 0.1);
  padding: 25px 30px;
  border-radius: 5px;
  position: relative;
  background-color: #fff;
  min-height: 100vh;
}

.title {
  font-size: 40px;
  font-weight: 600;
  text-align: center;
}

.message {
  min-height: 60vh;
}

.oneMsg {
  background-color: #eeeeee;
  margin: 10px auto;
  padding: 15px 15px;
  border-radius: 8px;
}

.form {
  margin-top: 20px;
}

@media screen and (max-width: 1000px) {
  .mainContent {
    width: 94%;
    margin: 25px auto;
    padding: 10px 10px;
  }
}

@media (prefers-color-scheme: dark) {
  .mainContent {
    background-color: #141414 !important;
    color: #fff !important;
  }

  .oneMsg {
    background-color: #0e1013;
  }
}
</style>