<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import {login} from "../request/user.js";
import {useUserStore} from "../store/user.js";
import {ElMessage} from "element-plus";
const router = useRouter()
const username = ref('')
const password = ref('')
const rememberMe = ref(false)
const isLoading = ref(false)
const userStore = useUserStore()
const handleLogin = async () => {
  if (!username.value || !password.value) {
    ElMessage.info('请输入用户名和密码')
    return
  }

  isLoading.value = true

  try {
    const response = (await login({
      username: username.value,
      password: password.value
    }))
    console.log(response)
    if (response.code === 200){
      userStore.setToken(response.data.token)
      ElMessage.success(response.message)
      router.push("/")
    }else {
      ElMessage.error(response.message)
    }
  } catch (error) {
    ElMessage.error('登录失败，请检查用户名和密码')
  } finally {
    isLoading.value = false
  }
}

const goToRegister = () => {
  router.push('/register')
}
</script>

<template>
  <div class="login-container">
    <el-card class="login-card">
      <div class="login-header">
        <h1>欢迎登录</h1>
        <p>请输入您的账号和密码</p>
      </div>

      <el-form @submit.prevent="handleLogin" class="login-form">
        <el-form-item>
          <el-input
              v-model="username"
              placeholder="请输入用户名"
              prefix-icon="User"
              autocomplete="username"
          />
        </el-form-item>

        <el-form-item>
          <el-input
              v-model="password"
              type="password"
              placeholder="请输入密码"
              prefix-icon="Lock"
              autocomplete="current-password"
              show-password
          />
        </el-form-item>

        <div class="form-options">
          <el-checkbox v-model="rememberMe">记住我</el-checkbox>
          <el-link type="primary" :underline="false">忘记密码?</el-link>
        </div>

        <el-button
            type="primary"
            class="login-button"
            :loading="isLoading"
            @click="handleLogin"
        >
          {{ isLoading ? '登录中...' : '登录' }}
        </el-button>
      </el-form>

      <div class="login-footer">
        <p>还没有账号? <a href="#" @click.prevent="goToRegister" style="color: #409eff">立即注册</a></p>
      </div>
    </el-card>
  </div>
</template>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.login-card {
  width: 100%;
  max-width: 400px;
  border-radius: 10px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-header h1 {
  font-size: 24px;
  color: #333;
  margin-bottom: 10px;
}

.login-header p {
  color: #666;
  font-size: 14px;
}

.login-form {
  margin-bottom: 20px;
}

.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.login-button {
  width: 100%;
  padding: 12px 0;
  font-size: 16px;
}

.login-footer {
  text-align: center;
  font-size: 14px;
}

.login-footer p {
  margin: 0;
}
</style>
