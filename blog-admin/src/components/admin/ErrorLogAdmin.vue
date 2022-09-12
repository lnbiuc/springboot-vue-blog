<template>
  <div class="errorMainContent">
    <div class="tableContent">
      <el-table :data="data" class="table" max-height="100vh - 100px" border>
        <el-table-column prop="id" label="id" header-align="center" align="center"/>
        <el-table-column prop="stackTrace" label="详细信息" header-align="center" align="center"/>
        <el-table-column prop="timestamp" label="错误时间" header-align="center" align="center"/>
      </el-table>
    </div>
    <div class="page">
      <el-pagination
          style="width: 100%"
          :current-page="page.current"
          :page-size="page.size"
          :total="page.total"
          :page-sizes="[10,20,30]"
          layout="sizes, prev, pager, next, jumper,total"
          @size-change="size"
          @current-change='getInfo'/>
    </div>
  </div>
</template>

<script>
import {getErrorLog} from "../../axios";

export default {
  name: "ErrorLogAdmin",
  data() {
    return {
      page: {
        current: '',
        size: 10,
        total: 0
      },
      data: [],
    }
  },
  methods: {
    getInfo(current) {
      getErrorLog(current, this.page.size).then(res => {
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
  },
  created() {
    this.getInfo(1)
  }
}
</script>

<style scoped>
.errorMainContent {
  width: 100%;
  box-shadow: 1px 0 10px 1px rgb(10, 10, 10, 0.1);
  border-radius: 3px;
  display: flex;
  flex-direction: column;
}

.tableContent {
  height: calc(100vh - 100px);
}

</style>