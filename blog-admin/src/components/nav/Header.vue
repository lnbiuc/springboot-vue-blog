<template>
  <div class="line">
    <t-head-menu
        default-value="1"
        theme="dark"
        expand-type="popup">
      <template #logo>
        <p class="logo">AdminBoard</p>
      </template>
      <t-menu-item value="1" @click="index(1)">上次编辑</t-menu-item>
      <t-menu-item value="2" @click="index(2)">发布文章</t-menu-item>
      <t-submenu value="3">
        <template #title>
          <span>文章管理</span>
        </template>
        <t-menu-item value="3-1" @click="index(3)">已发布</t-menu-item>
        <t-menu-item value="4-2" @click="index(4)">已删除</t-menu-item>
      </t-submenu>
      <t-menu-item value="5" @click="index(5)">标签管理</t-menu-item>
      <t-menu-item value="6" @click="index(6)">分类管理</t-menu-item>
      <t-menu-item value="9" @click="index(9)">留言管理</t-menu-item>
      <t-menu-item value="10" @click="index(10)">访客信息</t-menu-item>
      <t-menu-item value="11" @click="index(11)">错误信息</t-menu-item>
      <template #operations>
        <t-submenu value="12">
          <template #title>
            <span>{{ userName }}</span>
          </template>
          <t-menu-item value="8" @click="index(8)">登录</t-menu-item>
          <t-menu-item value="7" @click="index(7)">退出</t-menu-item>
        </t-submenu>
      </template>
    </t-head-menu>
  </div>
</template>

<script>
import {logout, userInfo} from "../../axios";
import {ElMessage} from "element-plus";

export default {
  name: "Header",
  data() {
    return {
      levelList: [],
      userName: '',
      id: ''
    }
  },
  methods: {
    index(index) {
      switch (index) {
        case 1:
          return this.$router.push("/index")
        case 2:
          return this.$router.push("/publish")
        case 3:
          return this.$router.push("/article")
        case 4:
          return this.$router.push("/deleted")
        case 5:
          return this.$router.push("/tag")
        case 6:
          return this.$router.push("/filing")
        case 7:
          return this.logout()
        case 8:
          return this.$router.push("/login")
        case 9:
          return this.$router.push("/message")
        case 10:
          return this.$router.push("/truck")
        case 11:
          return this.$router.push("/error")
      }
    },
    logout() {
      logout().then(() => {
        ElMessage.success("退出成功")
        this.$router.push("/login")
        window.localStorage.setItem("token", "")
        window.localStorage.setItem("user", "")
      })
    },
    getUserInfo() {
      userInfo().then(res => {
        this.userName = res.data.data.username
        this.id = res.data.data.id
      })
    }
  },
  created() {
    this.getUserInfo()
  }
}
</script>

<style scoped>
.line {
  display: flex;
  flex-direction: row;
  width: 100%;
  background-color: #fff;
  box-shadow: 1px 0 10px 1px rgb(10, 10, 10, 0.1);
  border-radius: 3px;
  align-items: center;
}

.logo {
  margin-left: 15px;
  color: white;
  font-size: 20px;
  font-weight: 500;
}
</style>