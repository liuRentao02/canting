<script setup>
import {markRaw, ref} from 'vue'
import {Fold,
  Expand,
  Food,
  Bell,
  User,
  SwitchButton,
  CaretBottom,
  Clock,
  HomeFilled,
  ShoppingCart,
  DataLine,
  Grid,
  Dish,
  Ticket,Setting} from '@element-plus/icons-vue'
import {logout} from "../request/user.js";
import router from "../router/index.js";
import {useUserStore} from "../store/user.js";

// 控制侧边栏折叠状态
const isCollapse = ref(false)

// 餐厅基本信息
const restaurantInfo = ref({
  name: '美味轩餐厅',
  address: '北京市朝阳区建国路88号',
  phone: '010-12345678',
  openTime: '10:00 - 22:00',
  status: '营业中'
})


const activeMenu = ref('dashboard')

// 菜单数据
const menuItems = [
  {
    index: '/',  // 首页使用根路径
    icon: markRaw(HomeFilled),
    title: '首页',
    path: '/',
  },
  {
    index: '/orders',
    icon: markRaw(ShoppingCart),
    title: '订单管理',
    path: '/orders',
    children: [
      {
        index: '/orders/list',
        title: '订单列表',
        path: 'list',  // 子路由使用相对路径
      },
      {
        index: '/orders/history',
        title: '历史订单',
        path: 'history',  // 子路由使用相对路径
      }
    ]
  },
  {
    index: '/dishes',
    icon: markRaw(Dish),
    title: '菜品管理',
    path: '/dishes',
    children: [
      {
        index: '/dishes/list',
        title: '菜品列表',
        path: 'list',
      },
      {
        index: '/dishes/category',
        title: '菜品分类',
        path: 'category',
      },
      {
        index: '/dishes/add',
        title: '添加菜品',
        path: 'add',
      }
    ]
  },
  {
    index: '/tables',
    icon: markRaw(Grid),
    title: '餐桌管理',
    path: '/tables',
  },
  {
    index: '/user',
    icon: markRaw(User),
    title: '客户管理',
    path: '/user',
  },
  {
    index: '/coupons',
    icon: markRaw(Ticket),
    title: '优惠券管理',
    path: '/coupons',
  },
  {
    index: '/reports',
    icon: markRaw(DataLine),
    title: '数据报表',
    path: '/reports',
    children: [
      {
        index: '/reports/sales',
        title: '销售报表',
        path: 'sales',
      },
      {
        index: '/reports/dishes',
        title: '菜品分析',
        path: 'dishes',
      }
    ]
  },
  {
    index: '/settings',
    icon: markRaw(Setting),
    title: '系统设置',
    path: '/settings',
  }
]


// 处理菜单点击
const handleMenuClick = (index) => {
  activeMenu.value = index
  console.log('点击菜单:', index)
}

const logoutSys =async () => {
  await logout()
  useUserStore().clearToken()
  await router.push("/login")
}
</script>

<template>
<el-container>
  <el-header class="header">
    <div class="header-left">
      <el-icon class="fold-btn" @click="isCollapse = !isCollapse">
        <Fold v-if="!isCollapse"/>
        <Expand v-else/>
      </el-icon>
      <div class="logo">
        <el-icon size="30"><Food /></el-icon>
        <span class="restaurant-name">{{ restaurantInfo.name }}</span>
      </div>
    </div>

    <div class="header-right">
      <div class="header-item">
        <el-badge :value="3" class="notification-badge">
          <el-icon size="20"><Bell /></el-icon>
        </el-badge>
      </div>

      <div class="header-item">
        <el-tooltip content="营业时间" placement="bottom">
          <div class="business-hours">
            <el-icon><Clock /></el-icon>
            <span>{{ restaurantInfo.openTime }}</span>
          </div>
        </el-tooltip>
      </div>

      <div class="header-item user-info">
        <el-avatar :size="32" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"/>
        <div class="user-detail">
          <div class="username">管理员</div>
          <div class="role">超级管理员</div>
        </div>
        <el-dropdown trigger="click">
          <el-icon class="user-dropdown"><CaretBottom /></el-icon>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>
                <el-icon><User /></el-icon>个人中心
              </el-dropdown-item>
              <el-dropdown-item>
                <el-icon><Setting /></el-icon>系统设置
              </el-dropdown-item>
              <el-dropdown-item divided @click.prevent="logoutSys">
                <el-icon><SwitchButton /></el-icon>退出登录
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
  </el-header>
  <el-container class="main-container">
    <el-aside :width="isCollapse ? '64px' : '200px'" class="sidebar">
      <!-- 菜单 -->
      <el-menu
          :default-active="activeMenu"
          :collapse="isCollapse"
          :collapse-transition="false"
          class="sidebar-menu"
          @select="handleMenuClick"
          router
      >
        <template v-for="item in menuItems" :key="item.index">
          <!-- 有子菜单的情况 -->
          <el-sub-menu v-if="item.children" :index="item.index">
            <template #title>
              <el-icon><component :is="item.icon" /></el-icon>
              <span>{{ item.title }}</span>
            </template>

            <el-menu-item
                v-for="child in item.children"
                :key="child.index"
                :index="child.index"
            >
              <span>{{ child.title }}</span>
            </el-menu-item>
          </el-sub-menu>

          <!-- 没有子菜单的情况 -->
          <el-menu-item v-else :index="item.index">
            <el-icon><component :is="item.icon" /></el-icon>
            <span>{{ item.title }}</span>
          </el-menu-item>
        </template>
      </el-menu>
    </el-aside>
    <el-main class="main-content">
      <router-view></router-view>
    </el-main>
  </el-container>
</el-container>
</template>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  padding: 0 20px;
  height: 60px;
}

.header-left {
  display: flex;
  align-items: center;
}

.fold-btn {
  font-size: 20px;
  cursor: pointer;
  margin-right: 20px;
  color: #606266;
  transition: color 0.3s;
}

.fold-btn:hover {
  color: #409EFF;
}

.logo {
  display: flex;
  align-items: center;
  gap: 10px;
}

.restaurant-name {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 25px;
}

.header-item {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.notification-badge {
  color: #606266;
}

.business-hours {
  display: flex;
  align-items: center;
  gap: 5px;
  color: #606266;
  font-size: 14px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 0 10px;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.user-info:hover {
  background-color: #f5f7fa;
}

.user-detail {
  display: flex;
  flex-direction: column;
}

.username {
  font-size: 14px;
  font-weight: 500;
  color: #303133;
  line-height: 1.2;
}

.role {
  font-size: 12px;
  color: #909399;
  line-height: 1.2;
}

.user-dropdown {
  color: #909399;
  cursor: pointer;
}

/* 修改部分开始 */
/* 外层容器样式 */
.el-container {
  height: 100vh;
  overflow: hidden;
}

/* 主容器样式 */
.main-container {
  height: calc(100vh - 60px); /* 减去头部高度 */
  overflow: hidden;
}

/* 侧边栏样式 */
.sidebar {
  background-color: #304156;
  height: 100%;
  transition: width 0.3s;
  overflow: hidden; /* 侧边栏不滚动 */
}

.sidebar-menu {
  border-right: none;
  background-color: #304156;
  height: 100%;
  overflow: hidden; /* 菜单不滚动 */
}

/* 主内容区域样式 */
.main-content {
  height: 100%;
  padding: 20px;
  overflow-y: auto; /* 只有主内容区域可以滚动 */
  background-color: #f0f2f5;
}

/* 隐藏主内容区域的滚动条但保持滚动功能 */
.main-content::-webkit-scrollbar {
  width: 6px;
}

.main-content::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 3px;
}

.main-content::-webkit-scrollbar-track {
  background: #f1f1f1;
}
/* 修改部分结束 */

/* 菜单项样式 */
:deep(.el-menu-item) {
  color: #bfcbd9;
}

:deep(.el-menu-item:hover) {
  background-color: #263445 !important;
  color: #fff !important;
}

/* 子菜单标题样式 */
:deep(.el-sub-menu__title) {
  color: #bfcbd9;
}

:deep(.el-sub-menu__title:hover) {
  background-color: #263445 !important;
  color: #fff !important;
}

/* 子菜单项样式 */
:deep(.el-menu .el-menu-item) {
  background-color: #1f2d3d !important;
}

:deep(.el-menu .el-menu-item:hover) {
  background-color: #263445 !important;
}
</style>
