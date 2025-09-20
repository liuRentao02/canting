<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import {register} from "../request/system.js";
import {ElMessage} from "element-plus";
import {Lock,Iphone,User} from "@element-plus/icons-vue"
const router = useRouter()

// 表单数据
const formData = ref({
  username: '',
  phone: '',
  password: '',
  confirmPassword: '',
  agreeTerms: false
})

// 表单验证规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号码', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== formData.value.password) {
          callback(new Error('两次输入密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ],
  agreeTerms: [
    {
      validator: (rule, value, callback) => {
        if (!value) {
          callback(new Error('请同意服务条款和隐私政策'))
        } else {
          callback()
        }
      },
      trigger: 'change'
    }
  ]
}

// 表单引用
const formRef = ref(null)

// 注册方法
const registers = async () => {
  if (!formRef.value) return

  try {
    await formRef.value.validate()

    const response = await register(formData.value)

    if (response.code === 200){
      ElMessage.success(response.message)
      await router.push("/login")
    }else {
      ElMessage.error(response.message)
    }
  } catch (error) {
    console.error('表单验证失败', error)
  }
}

// 跳转到登录页
const goToLogin = () => {
  router.push('/login')
}
</script>

<template>
    <div class="register-container">
      <div class="register-box">
        <h2 class="register-title">用户注册</h2>

        <el-form
            ref="formRef"
            :model="formData"
            :rules="rules"
            label-position="top"
            size="large"
        >
          <el-form-item label="用户名" prop="username">
            <el-input
                v-model="formData.username"
                placeholder="请输入用户名"
                :prefix-icon="User"
            />
          </el-form-item>

          <el-form-item label="手机号" prop="phone">
            <el-input
                v-model="formData.phone"
                placeholder="请输入手机号"
                :prefix-icon="Iphone"
            />
          </el-form-item>

          <el-form-item label="密码" prop="password">
            <el-input
                v-model="formData.password"
                type="password"
                placeholder="请输入密码"
                :prefix-icon="Lock"
                show-password
            />
          </el-form-item>

          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input
                v-model="formData.confirmPassword"
                type="password"
                placeholder="请再次输入密码"
                :prefix-icon="Lock"
                show-password
            />
          </el-form-item>

          <el-form-item prop="agreeTerms">
            <el-checkbox v-model="formData.agreeTerms">
              我已阅读并同意 <a href="#" class="terms-link">服务条款</a> 和 <a href="#" class="terms-link">隐私政策</a>
            </el-checkbox>
          </el-form-item>

          <el-form-item>
            <el-button
                type="primary"
                class="register-button"
                @click="registers"
            >
              注册
            </el-button>
          </el-form-item>
        </el-form>

        <div class="register-footer">
          <p>已有账号？<a href="#" @click.prevent="goToLogin" class="login-link">立即登录</a></p>
        </div>
      </div>
    </div>
</template>

<style scoped>
/* 隐藏默认滚动条 */
::-webkit-scrollbar {
  width: 0;
  height: 0;
  display: none;
}

/* 确保内容不会溢出 */
html, body {
  overflow: hidden;
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
}

.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  padding: 20px;
}

.register-box {
  width: 100%;
  max-width: 400px;
  padding: 30px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.register-title {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
  font-weight: 500;
}

.terms-link {
  color: #409eff;
  text-decoration: none;
}

.terms-link:hover {
  text-decoration: underline;
}

.register-button {
  width: 100%;
  padding: 12px 0;
  font-size: 16px;
}

.register-footer {
  text-align: center;
  font-size: 14px;
  color: #666;
  margin-top: 20px;
}

.register-footer p {
  margin: 0;
}

.login-link {
  color: #409eff;
  text-decoration: none;
  font-weight: 500;
}

.login-link:hover {
  text-decoration: underline;
}
</style>
