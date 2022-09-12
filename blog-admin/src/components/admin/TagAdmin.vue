<template>
  <div class="tagMainContent">
    <div class="tableContent">

      <el-table :data="data" max-height="100vh - 100px" class="table" border>
        <el-table-column prop="tagId" label="标签ID" header-align="center" align="center" sortable/>
        <el-table-column prop="tagName" label="标签名称" header-align="center" align="center" sortable/>
        <el-table-column prop="count" label="引用次数" header-align="center" align="center" sortable/>
        <el-table-column>
          <template #header>
            <div class="addForm">
              <el-input v-model="addForm.tagName" size="small" placeholder="输入标签名"/>
              <el-button size="small" @click="addNewTag"
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
        title="修改标签"
        width="30%">
      <p>原标签:&nbsp;&nbsp;
        <span style="font-weight: 800;color: #4285f4">{{ editForm.oldTagName }}</span>
      </p>
      <el-form>
        <el-form-item label="新标签名">
          <el-input v-model="editForm.tagName">
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
      <p>确认删除标签：&nbsp;&nbsp;
        <span style="font-weight: 800;color: #4285f4">{{ deleteForm.oldTagName }}</span>
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
import {addNewTag, deleteTag, editTag, getTagAdmin} from "../../axios";
import {ElMessage} from "element-plus";

export default {
  name: "TagAdmin",
  data() {
    return {
      page: {
        current: '',
        size: 10,
        total: 0
      },
      data: [],
      dialog: {
        edit: false,
        delete: false
      },
      editForm: {
        oldTagName: '',
        tagId: '',
        tagName: ''
      },
      addForm: {
        tagName: ''
      },
      deleteForm: {
        oldTagName: '',
        tagId: ''
      }
    }
  },
  methods: {
    getInfo(current) {
      getTagAdmin(current, this.page.size).then(res => {
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
    handleEdit(index, row) {
      this.editForm.oldTagName = row.tagName
      this.editForm.tagId = row.tagId
      this.dialog.edit = true
    },
    submitEdit() {
      console.log("editForm")
      editTag(this.editForm.tagId, this.editForm.tagName).then(res => {
        ElMessage.success("修改成功");
        this.editForm.tagName = ''
        this.getInfo(this.page.current);
      })
    },
    handleDelete(index, row) {
      this.deleteForm.oldTagName = row.tagName;
      this.deleteForm.tagId = row.tagId
      this.dialog.delete = true
    },
    submitDelete() {
      deleteTag(this.deleteForm.tagId).then(() => {
        ElMessage.success("删除成功");
        this.getInfo(this.page.current)
      })
    },
    addNewTag() {
      addNewTag(this.addForm.tagName).then(() => {
        ElMessage.success("添加成功");
        this.addForm.tagName = ''
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
.tagMainContent {
  width: 100%;
  box-shadow: 1px 0 10px 1px rgb(10, 10, 10, 0.1);
  border-radius: 3px;
  display: flex;
  flex-direction: column;
}

.tableContent {
  height: calc(100vh - 100px);
}

.table {
  width: 100%;
}

.addForm {
  display: flex;
  flex-direction: row;
}
</style>