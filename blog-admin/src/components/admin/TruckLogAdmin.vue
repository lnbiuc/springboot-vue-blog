<template>
  <div class="truckMainContent">
    <div class="tableContent">
      <el-table :data="data" class="table" max-height="100vh - 100px" border>
        <el-table-column prop="id" label="id" header-align="center" align="center"/>
        <el-table-column prop="module" label="模块" header-align="center" align="center"/>
        <el-table-column prop="operation" label="操作" header-align="center" align="center"/>
        <el-table-column prop="ip" label="ip" header-align="center" align="center"/>
        <el-table-column prop="executeTime" label="处理时间(ms)" header-align="center" align="center"/>
        <el-table-column prop="brName" label="浏览器" header-align="center" align="center"/>
        <el-table-column prop="osName" label="系统" header-align="center" align="center"/>
        <el-table-column prop="time" label="请求时间" header-align="center" align="center"/>
      </el-table>
    </div>
    <div class="page">
      <el-pagination
          style="width: 100%"
          :current-page="page.current"
          :page-size="page.size"
          :total="page.total"
          :page-sizes="[10,20,50]"
          layout="sizes, prev, pager, next, jumper,total"
          @size-change="size"
          @current-change='getInfo'/>
      <div class="form">
        <el-form :model="ruleForm" :rules="rules" ref="baseForm" status-icon label-width="auto">
          <el-form-item label="ip" prop="ip">
            <el-input v-model="ruleForm.ip"/>
          </el-form-item>
        </el-form>
      </div>
      <el-button type="primary" @click="confirmOperation">清除自己的访问数据</el-button>
    </div>
  </div>
</template>

<script>
import {deleteTruckLogByIp, getTruckLog} from "../../axios";
import {ElMessage} from "element-plus";

export default {
  name: "TruckLogAdmin",
  data() {
    return {
      page: {
        current: '',
        size: 20,
        total: 0
      },
      data: [],
      rules: {
        ip: [{required: true, message: '不可为空', trigger: 'change'}],
      },
      ruleForm: {
        ip: '',
      }
    }
  },
  methods: {
    getInfo(current) {
      getTruckLog(current, this.page.size).then(res => {
        this.data = res.data.data.data
        this.page.size = res.data.data.size
        this.page.current = res.data.data.current
        this.page.total = res.data.data.total
      })
    },
    size(size) {
      this.page.size = size
      this.getInfo(this.page.current)
    },
    confirmOperation() {
      this.$refs.baseForm.validate((valid) => {
        if (valid) {
          deleteTruckLogByIp(this.ruleForm.ip).then(res => {
            ElMessage.success("删除了" + res.data.data + "条数据")
            this.ruleForm.ip = ''
            this.getInfo(1)
          })
        }
      })
    },
  },
  created() {
    this.getInfo(1)
  }
}
</script>

<style scoped>
.truckMainContent {
  width: 100%;
  box-shadow: 1px 0 10px 1px rgb(10, 10, 10, 0.1);
  border-radius: 3px;
  display: flex;
  flex-direction: column;
}

.tableContent {
  height: calc(100vh - 100px);
}

.page {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
</style>