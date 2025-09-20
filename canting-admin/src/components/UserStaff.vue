<script setup>
import {getUserStaffList, deleteUser, updateUser} from "../request/userStaff.js";
import { onMounted, ref } from "vue";
import { ElCard, ElTable, ElTableColumn, ElButton, ElMessage, ElMessageBox, ElPagination } from 'element-plus';
import { Edit, Delete, Plus } from '@element-plus/icons-vue';

const userList = ref([])
const dialogVisible = ref(false)
const currentUser = ref({})
const pagination = ref({
  currentPage: 1,
  pageSize: 5,
  total: 0
})

onMounted(async () => {
  await fetchUserList()
})

const fetchUserList = async () => {
  const response = await getUserStaffList({
    "num":1,
    "currentPage": pagination.value.currentPage,
    "size": pagination.value.pageSize
  })
  console.log(response)
  // 修改这里以匹配后端返回的数据结构
  userList.value = response.data.records
  pagination.value.currentPage = response.data.current || 1
  pagination.value.pageSize = response.data.size
  pagination.value.total = response.data.total
}

const handleSizeChange = (val) => {
  pagination.value.pageSize = val
  pagination.value.currentPage = 1
  fetchUserList()
}

const handleCurrentChange = (val) => {
  pagination.value.currentPage = val
  fetchUserList()
}

const handleEdit = (row) => {
  currentUser.value = {...row}
  dialogVisible.value = true
}

const handleDelete = (row) => {
  ElMessageBox.confirm(
      `确定要删除用户 ${row.username} 吗?`,
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(async () => {
        try {
          await deleteUser(row.id)
          ElMessage({
            type: 'success',
            message: '删除成功',
          })
          // 如果当前页只有一条数据且不是第一页，删除后返回上一页
          if (userList.value.length === 1 && pagination.value.currentPage > 1) {
            pagination.value.currentPage--
          }
          await fetchUserList()
        } catch (error) {
          ElMessage({
            type: 'error',
            message: '删除失败',
          })
        }
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '已取消删除',
        })
      })
}

const handleAdd = () => {
  currentUser.value = {
    username: '',
    nickname: '',
    phone: '',
    role: ''
  }
  dialogVisible.value = true
}

const saveUser = async () => {
  try {
    if (currentUser.value.id) {
      await updateUser(currentUser.value)
      ElMessage({
        type: 'success',
        message: '更新成功',
      })
    } else {
      // 这里应该是添加用户的API调用
      // await addUser(currentUser.value)
      ElMessage({
        type: 'success',
        message: '添加成功',
      })
    }
    dialogVisible.value = false
    await fetchUserList()
  } catch (error) {
    ElMessage({
      type: 'error',
      message: '操作失败',
    })
  }
}
</script>

<template>
  <ElCard class="user-table-card">
    <template #header>
      <div class="card-header">
        <span>用户列表</span>
        <ElButton type="primary" :icon="Plus" @click="handleAdd">添加用户</ElButton>
      </div>
    </template>

    <ElTable :data="userList" style="width: 100%" stripe border>
      <ElTableColumn prop="username" label="用户名" width="120" />
      <ElTableColumn prop="nickname" label="昵称"/>
      <ElTableColumn prop="phone" label="电话" />
      <ElTableColumn prop="role" label="角色" width="120" />
      <ElTableColumn prop="createTime" label="创建时间"/>
      <ElTableColumn label="操作" width="100">
        <template #default="scope">
          <ElButton
              type="primary"
              :icon="Edit"
              circle
              size="small"
              @click="handleEdit(scope.row)"
              title="编辑"
          />
          <ElButton
              type="danger"
              :icon="Delete"
              circle
              size="small"
              @click="handleDelete(scope.row)"
              title="删除"
          />
        </template>
      </ElTableColumn>
    </ElTable>

    <!-- 分页器 -->
    <div class="pagination-container">
      <ElPagination
          v-model:current-page="pagination.currentPage"
          v-model:page-size="pagination.pageSize"
          :page-sizes="[5, 10, 20, 50]"
          :small="false"
          :disabled="false"
          :background="true"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pagination.total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
    </div>
  </ElCard>

  <!-- 用户编辑/添加对话框 -->
  <ElDialog
      v-model="dialogVisible"
      :title="currentUser.id ? '编辑用户' : '添加用户'"
      width="500px"
  >
    <ElForm :model="currentUser" label-width="80px">
      <ElFormItem label="用户名">
        <ElInput v-model="currentUser.username" />
      </ElFormItem>
      <ElFormItem label="昵称">
        <ElInput v-model="currentUser.nickname" />
      </ElFormItem>
      <ElFormItem label="电话">
        <ElInput v-model="currentUser.phone" />
      </ElFormItem>
      <ElFormItem label="角色">
        <ElInput v-model="currentUser.role" />
      </ElFormItem>
    </ElForm>
    <template #footer>
      <span class="dialog-footer">
        <ElButton @click="dialogVisible = false">取消</ElButton>
        <ElButton type="primary" @click="saveUser">确定</ElButton>
      </span>
    </template>
  </ElDialog>
</template>

<style scoped>
.user-table-card {
  margin: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: bold;
  font-size: 16px;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

:deep(.el-table th) {
  background-color: #f5f7fa;
  color: #606266;
  font-weight: bold;
}

:deep(.el-table--striped .el-table__body tr.el-table__row--striped td) {
  background-color: #fafafa;
}

:deep(.el-table__body tr:hover > td) {
  background-color: #f5f7fa;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>
