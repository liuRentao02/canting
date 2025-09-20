// src/services/axios.js
import axios from 'axios';
import {useUserStore} from "../store/user.js";

const instance = axios.create({
    baseURL: 'http://localhost:8080',
});

// 添加请求拦截器
instance.interceptors.request.use(config => {
    // 在发送请求之前做些什么
    const userStore = useUserStore()
    const token = userStore.token
    if (token){
        // 将token添加到请求头
        config.headers['Authorization'] = `Bearer ${token}`
    }
    return config;
}, error => {
    // 对请求错误做些什么
    return Promise.reject(error);
});

// 添加响应拦截器
instance.interceptors.response.use(response => {
    // 对响应数据做点什么
    return response.data;
}, error => {
    // 对响应错误做些什么
    return Promise.reject(error);
});

export default instance;
