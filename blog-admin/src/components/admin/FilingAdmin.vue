<template>
  <div class="filingMainContent">
    <div class="tableContent">
      <el-table :data="data" max-height="100vh - 100px" class="table" border>
        <el-table-column prop="filingName.id" label="分类ID" header-align="center" align="center" sortable/>
        <el-table-column prop="filingName.filingName" label="分类名称" header-align="center" align="center" sortable/>
        <el-table-column prop="count" label="子文件个数" header-align="center" align="center" sortable/>
        <el-table-column>
          <template #header>
            <div class="addForm">
              <el-input v-model="addForm.filingName" size="small" placeholder="输入分类名"/>
              <el-button size="small" @click="addFiling"
              >ADD NEW
              </el-button
              >
            </div>
          </template>
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.$index, scope.row)"
            >Edit
            </el-button
            >
            <el-button
                size="small"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)"
            >Delete
            </el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="page">
      <el-pagination
          style="width: 100%"
          :current-page="page.current"
          :page-size="page.size"
          :total="page.total"
          :page-sizes="[5,10,20]"
          layout="sizes, prev, pager, next, jumper,total"
          @size-change="size"
          @current-change='getInfo'/>
    </div>
  </div>
  <div>
    <el-dialog
        v-model="dialog.edit"
        title="修改分类名"
        width="30%">
      <p>原名称:&nbsp;&nbsp;
        <span style="font-weight: 800;color: #4285f4">{{ editForm.oldFilingName }}</span>
      </p>
      <el-form>
        <el-form-item label="新分类名">
          <el-input v-model="editForm.filingName">
          </el-input>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialog.edit = false">取消</el-button>
        <el-button type="primary" @click="dialog.edit = false;submitEdit()"
        >确定</el-button
        >
      </span>
      </template>
    </el-dialog>
    <el-dialog
        v-model="dialog.delete"
        title="确认"
        width="30%">
      <p>确认删除分类：
        <span style="font-weight: 800;color: #4285f4">{{ deleteForm.filingName }}</span>
      </p>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialog.delete = false">取消</el-button>
        <el-button type="primary" @click="dialog.delete = false;submitDelete()"
        >确定</el-button
        >
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import {addFiling, deleteFiling, editFilingName, getFilingAdmin} from "../../axios";
import {ElMessage} from "element-plus";

export default {
  name: "FilingAdmin",
  data() {
    return {
      data: [],
      page: {
        current: '',
        size: 10,
        total: 0
      },
      addForm: {
        filingName: ''
      },
      dialog: {
        edit: false,
        delete: false
      },
      editForm: {
        oldFilingName: '',
        filingId: '',
        filingName: ''
      },
      deleteForm: {
        filingId: '',
        filingName: ''
      }
    }
  },
  methods: {
    getInfo(current) {
      getFilingAdmin(current, this.page.size).then(res => {
        this.data = res.data.data.paramsList
        this.page.size = res.data.data.pageSize
        this.page.total = res.data.data.total
        this.page.current = res.data.data.currentPage
      })
    },
    size(size) {
      this.page.size = size
      this.getInfo(this.page.current)
    },
    addFiling() {
      addFiling(this.addForm.filingName).then(() => {
        ElMessage.success("添加成功")
        this.getInfo(this.page.current)
      })
    },
    handleEdit(index, row) {
      this.editForm.filingId = row.filingName.id
      this.editForm.oldFilingName = row.filingName.filingName
      this.dialog.edit = true
    },
    submitEdit() {
      editFilingName(this.editForm.filingId, this.editForm.filingName).then(() => {
        ElMessage.success("修改成功")
        this.getInfo(this.page.current)
      })
    },
    handleDelete(index, row) {
      this.deleteForm.filingId = row.filingName.id
      this.deleteForm.filingName = row.filingName.filingName
      this.dialog.delete = true
    },
    submitDelete() {
      deleteFiling(this.deleteForm.filingId).then(() => {
        ElMessage.success("删除成功")
        this.getInfo(this.page.current)
      })
    }
  },
  created() {
    this.getInfo(1)
  }
}
</script>

<style scoped>
.filingMainContent {
  width: 100%;
  box-shadow: 1px 0 10px 1px rgb(10, 10, 10, 0.1);
  border-radius: 3px;
  display: flex;
  flex-direction: column;
}

.tableContent {
  height: calc(100vh - 100px);
}

.addForm {
  display: flex;
  flex-direction: row;
}
</style>