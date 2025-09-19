<script setup>
import { ref,markRaw } from 'vue'
import {
  User,
  ShoppingCart,
  Money,
  Star,
  Dish,
  List,
  Plus,
  Edit,
  Setting,
  Bell,
  Phone,
  CaretRight,
  Refresh, Location, Clock
} from '@element-plus/icons-vue'
import {ElMessage} from "element-plus";
const icons = markRaw({
  User,
  ShoppingCart,
  Money,
  Star,
  Dish,
  List,
  Plus,
  Edit,
  Setting,
  Bell,
  Phone,
  CaretRight,
  Refresh, Location, Clock
})
// 餐厅基本信息
const restaurantInfo = ref({
  name: '美味轩餐厅',
  address: '北京市朝阳区建国路88号',
  phone: '010-88888888',
  status: '营业中',
  today: new Date().toLocaleDateString('zh-CN', { year: 'numeric', month: 'long', day: 'numeric', weekday: 'long' })
})

// 统计数据
const statistics = ref({
  todayOrders: 156,
  todayRevenue: 28560,
  todayCustomers: 289,
  avgRating: 4.8
})

// 热门菜品数据
const popularDishes = ref([
  { id: 1, name: '宫保鸡丁', price: 38, sales: 89, rating: 4.9, image: 'https://via.placeholder.com/60' },
  { id: 2, name: '水煮鱼', price: 68, sales: 76, rating: 4.8, image: 'https://via.placeholder.com/60' },
  { id: 3, name: '麻婆豆腐', price: 28, sales: 65, rating: 4.7, image: 'https://via.placeholder.com/60' },
  { id: 4, name: '红烧肉', price: 48, sales: 58, rating: 4.9, image: 'https://via.placeholder.com/60' },
  { id: 5, name: '糖醋排骨', price: 42, sales: 52, rating: 4.6, image: 'https://via.placeholder.com/60' }
])

// 最近订单数据
const recentOrders = ref([
  { id: 'ORD001', customer: '张先生', table: 'A12', amount: 168, status: '已完成', time: '12:30' },
  { id: 'ORD002', customer: '李女士', table: 'B05', amount: 256, status: '进行中', time: '12:45' },
  { id: 'ORD003', customer: '王先生', table: 'C08', amount: 198, status: '待支付', time: '13:00' },
  { id: 'ORD004', customer: '赵女士', table: 'A03', amount: 312, status: '已完成', time: '13:15' },
  { id: 'ORD005', customer: '刘先生', table: 'B11', amount: 145, status: '已取消', time: '13:30' }
])

// 菜品分类统计
const categoryStats = ref([
  { name: '热菜', count: 45, percentage: 40 },
  { name: '凉菜', count: 20, percentage: 18 },
  { name: '汤品', count: 15, percentage: 13 },
  { name: '主食', count: 18, percentage: 16 },
  { name: '饮品', count: 14, percentage: 13 }
])

// 快捷操作
const quickActions = ref([
  { title: '新增订单', icon: icons[Plus], color: '#67C23A' },
  { title: '菜品管理', icon: icons[Dish], color: '#409EFF' },
  { title: '桌台管理', icon: icons[List], color: '#E6A23C' },
  { title: '会员管理', icon: icons[User], color: '#F56C6C' },
  { title: '财务报表', icon: icons[Money], color: '#909399' },
  { title: '系统设置', icon: icons[Setting], color: '#606266' }
])

// 刷新数据
const refreshData = () => {
  ElMessage.success('数据已刷新')
}
</script>

<template>
  <div class="dashboard-container">
    <!-- 欢迎区域 -->
    <div class="welcome-section">
      <div class="welcome-text">
        <h1>欢迎回来，管理员</h1>
        <p>{{ restaurantInfo.today }}</p>
      </div>
      <el-button type="primary" :icon="icons[Refresh]" @click="refreshData">刷新数据</el-button>
    </div>

    <!-- 统计卡片 -->
    <el-row :gutter="20" class="statistics-row">
      <el-col :xs="24" :sm="12" :md="6" v-for="(stat, index) in statistics" :key="index">
        <el-card class="stat-card" body-style="{ padding: '20px' }">
          <div class="stat-content">
            <div class="stat-icon" :class="`stat-icon-${index}`">
              <el-icon><component :is="index === 0 ? ShoppingCart : index === 1 ? Money : index === 2 ? User : Star" /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">
                {{ index === 1 ? '¥' : '' }}{{ stat }}
              </div>
              <div class="stat-label">
                {{ index === 0 ? '今日订单' : index === 1 ? '今日营收' : index === 2 ? '今日客数' : '平均评分' }}
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 主要内容区域 -->
    <el-row :gutter="20" class="main-content">
      <!-- 左侧内容 -->
      <el-col :xs="24" :md="16">
        <!-- 热门菜品 -->
        <el-card class="content-card">
          <template #header>
            <div class="card-header">
              <span>热门菜品</span>
              <el-button type="text">查看全部 <el-icon><CaretRight /></el-icon></el-button>
            </div>
          </template>
          <el-table :data="popularDishes" style="width: 100%">
            <el-table-column label="菜品" width="200">
              <template #default="{ row }">
                <div class="dish-item">
                  <el-image :src="row.image" class="dish-image" />
                  <span>{{ row.name }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="price" label="价格" width="100">
              <template #default="{ row }">¥{{ row.price }}</template>
            </el-table-column>
            <el-table-column prop="sales" label="销量" width="100" />
            <el-table-column prop="rating" label="评分" width="100">
              <template #default="{ row }">
                <el-rate v-model="row.rating" disabled text-color="#ff9900" />
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template #default>
                <el-button type="primary" size="small" :icon="icons[Edit]">编辑</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>

        <!-- 最近订单 -->
        <el-card class="content-card">
          <template #header>
            <div class="card-header">
              <span>最近订单</span>
              <el-button type="text">查看全部 <el-icon><CaretRight /></el-icon></el-button>
            </div>
          </template>
          <el-table :data="recentOrders" style="width: 100%">
            <el-table-column prop="id" label="订单号" width="120" />
            <el-table-column prop="customer" label="客户" width="120" />
            <el-table-column prop="table" label="桌号" width="100" />
            <el-table-column prop="amount" label="金额" width="120">
              <template #default="{ row }">¥{{ row.amount }}</template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="120">
              <template #default="{ row }">
                <el-tag :type="row.status === '已完成' ? 'success' : row.status === '进行中' ? 'primary' : row.status === '待支付' ? 'warning' : 'info'">
                  {{ row.status }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="time" label="时间" width="100" />
            <el-table-column label="操作">
              <template #default>
                <el-button type="primary" size="small">详情</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <!-- 右侧内容 -->
      <el-col :xs="24" :md="8">
        <!-- 菜品分类统计 -->
        <el-card class="content-card">
          <template #header>
            <div class="card-header">
              <span>菜品分类统计</span>
            </div>
          </template>
          <div class="category-stats">
            <div v-for="(category, index) in categoryStats" :key="index" class="category-item">
              <div class="category-info">
                <span class="category-name">{{ category.name }}</span>
                <span class="category-count">{{ category.count }}道</span>
              </div>
              <el-progress :percentage="category.percentage" :color="['#409EFF', '#67C23A', '#E6A23C', '#F56C6C', '#909399'][index]" />
            </div>
          </div>
        </el-card>

        <!-- 快捷操作 -->
        <el-card class="content-card">
          <template #header>
            <div class="card-header">
              <span>快捷操作</span>
            </div>
          </template>
          <div class="quick-actions">
            <el-button v-for="(action, index) in quickActions" :key="index" class="action-btn" :style="{ '--btn-color': action.color }">
              <el-icon><component :is="action.icon" /></el-icon>
              <span>{{ action.title }}</span>
            </el-button>
          </div>
        </el-card>

        <!-- 餐厅信息 -->
        <el-card class="content-card">
          <template #header>
            <div class="card-header">
              <span>餐厅信息</span>
            </div>
          </template>
          <div class="restaurant-info">
            <div class="info-item">
              <el-icon><Phone /></el-icon>
              <span>{{ restaurantInfo.phone }}</span>
            </div>
            <div class="info-item">
              <el-icon><Location /></el-icon>
              <span>{{ restaurantInfo.address }}</span>
            </div>
            <div class="info-item">
              <el-icon><Clock /></el-icon>
              <span>营业时间: 10:00 - 22:00</span>
            </div>
            <div class="info-item">
              <el-icon><Bell /></el-icon>
              <span>当前状态: <el-tag type="success">{{ restaurantInfo.status }}</el-tag></span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
.dashboard-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: 100vh;
}

.welcome-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.welcome-text h1 {
  font-size: 24px;
  margin: 0;
  color: #303133;
}

.welcome-text p {
  margin: 5px 0 0;
  color: #909399;
}

.statistics-row {
  margin-bottom: 20px;
}

.stat-card {
  border-radius: 8px;
  transition: all 0.3s;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.stat-content {
  display: flex;
  align-items: center;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 15px;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
}

.stat-label {
  font-size: 14px;
  color: #909399;
  margin-top: 5px;
}

.main-content {
  margin-bottom: 20px;
}

.content-card {
  margin-bottom: 20px;
  border-radius: 8px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.dish-item {
  display: flex;
  align-items: center;
}

.dish-image {
  width: 40px;
  height: 40px;
  border-radius: 4px;
  margin-right: 10px;
}

.category-stats {
  padding: 10px 0;
}

.category-item {
  margin-bottom: 15px;
}

.category-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 5px;
}

.category-name {
  font-weight: bold;
}

.category-count {
  color: #909399;
}

.quick-actions {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
}

.action-btn {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 80px;
  border: 1px solid #ebeef5;
  background-color: #fff;
  transition: all 0.3s;
}

.action-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.restaurant-info {
  padding: 10px 0;
}

.info-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

@media (max-width: 768px) {

  .quick-actions {
    grid-template-columns: repeat(3, 1fr);
  }
}
</style>
