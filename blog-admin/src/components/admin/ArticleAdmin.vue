<template>
  <div class="articleMainContent">
    <div class="tableContent">

      <el-table :data="data" max-height="100vh - 100px" class="table" border>
        <el-table-column prop="article.articleId" fixed label="ID" header-align="center" align="center" sortable/>
        <el-table-column prop="article.title" label="标题" header-align="center" align="center" sortable/>
        <el-table-column prop="article.introduction" label="简介" header-align="center" align="center" sortable/>
        <el-table-column prop="article.releaseTime" label="发表时间" header-align="center" align="center" sortable/>
        <el-table-column prop="article.updateTime" label="更新时间" header-align="center" align="center" sortable/>
        <el-table-column prop="article.setTop" label="置顶" header-align="center" align="center" sortable>
          <template #default="scope">
            <el-tag v-if="scope.row.article.setTop" type="success"
            >{{ scope.row.article.setTop }}
            </el-tag
            >
            <el-tag v-else type="danger"
            >{{ scope.row.article.setTop }}
            </el-tag
            >
          </template>
        </el-table-column>
        <el-table-column prop="article.deleted" label="删除" header-align="center" align="center" sortable>
          <template #default="scope">
            <el-tag v-if="scope.row.article.deleted" type="success"
            >{{ scope.row.article.deleted }}
            </el-tag
            >
            <el-tag v-else type="danger"
            >{{ scope.row.article.deleted }}
            </el-tag
            >
          </template>
        </el-table-column>
        <el-table-column prop="article.visitsCount" label="阅读量" header-align="center" align="center" sortable/>
        <el-table-column prop="article.likeCount" label="点赞量" header-align="center" align="center" sortable/>
        <el-table-column label="背景图" prop="article.bgImg" width="120">
          <template v-slot="scope">
            <img :src="scope.row.article.bgImg" style="height: 60px" alt="">
          </template>
        </el-table-column>
        <el-table-column label="标签" prop="tagNames" width="150">
          <template v-slot="scope">
            <el-tag v-for="tag in scope.row.tagNames" key="tag.tagId" size="mini" style="margin: 2px 2px">
              {{ tag.tagName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="filingName.filingName" label="分类" header-align="center" align="center" sortable/>
        <el-table-column fixed="right" label="操作" header-align="center" align="center">
          <template #default="scope">
            <el-button size="small" type="success" @click="handleEdit(scope.$index, scope.row)"
            >编辑
            </el-button
            >
            <el-button
                size="small"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)"
            >删除
            </el-button
            >
            <el-button
                size="small"
                type="primary"
                @click="handleSetTop(scope.$index, scope.row)"
            >置顶
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
          layout="prev, pager, next, jumper,total"
          @current-change='getInfo'/>
    </div>

    <el-dialog
        v-model="dialog.delete"
        title="确认"
        width="30%">
      <p>确认删除文章，标题：
        <span style="font-weight: 800;color: #4285f4">{{ deleteForm.title }}</span>
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

import {articleSetTop, deleteArticle, getArticleAdmin} from "../../axios";
import {ElMessage} from "element-plus";

export default {
  name: "ArticleAdmin",
  data() {
    return {
      page: {
        current: '',
        size: 10,
        total: 0
      },
      data: [],
      dialog: {
        delete: false
      },
      deleteForm: {
        articleId: '',
        title: ''
      }
    }
  },
  methods: {
    getInfo(current) {
      getArticleAdmin(current, this.page.size).then(res => {
        this.data = res.data.data.paramsList
        this.page.size = res.data.data.pageSize
        this.page.total = res.data.data.total
        this.page.current = res.data.data.currentPage
      })
    },
    submitDelete() {
      deleteArticle(this.deleteForm.articleId).then(() => {
        ElMessage.success("删除成功")
        this.getInfo(this.page.current, this.page.size)
      })
    },
    handleDelete(index, row) {
      this.deleteForm.articleId = row.article.articleId
      this.deleteForm.title = row.article.title
      this.dialog.delete = true
    },
    handleEdit(index, row) {
      this.$router.push({name: 'editArticle', params: {'articleId': row.article.articleId}})
    },
    handleSetTop(index, row) {
      articleSetTop(row.article.articleId).then(() => {
        this.getInfo(this.page.current, this.page.size)
      })
    }
  },
  created() {
    this.getInfo(1, this.page.size)
  }
}
</script>

<style scoped>
.articleMainContent {
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