<template>
  <div class="loginContent">
    <el-form :model="ruleForm" :rules="rules" ref="baseForm">
      <el-form-item label="账号" prop="username">
        <el-input v-model="ruleForm.username"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="ruleForm.password" type="password"/>
      </el-form-item>
      <el-button style="width: 100%" @click="confirmOperation" type="primary">登录</el-button>
    </el-form>
  </div>
</template>

<script>
import {login} from "../axios";

export default {
  name: "Login",
  data() {
    return {
      rules: {
        username: [{required: true, message: '不可为空', trigger: 'change'}],
        password: [{required: true, message: '不可为空', trigger: 'change'}],
      },
      ruleForm: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    confirmOperation() {
      this.$refs.baseForm.validate((valid) => {
        if (valid) {
          login(this.ruleForm.username, this.ruleForm.password).then(res => {
            window.localStorage.setItem('token', res.data.data.token)
            this.$router.push('/')
          })
        }
      })
    },
  }
}
</script>

<style scoped>
.loginContent {
  width: 100%;
  height: 60vh;
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
}
</style>