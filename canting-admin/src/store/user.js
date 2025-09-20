// store/system.js
import { defineStore } from "pinia";
import { ref } from "vue";

export const useUserStore = defineStore("user", () => {
    const token = ref("")

    // 添加设置 token 的方法
    const setToken = (newToken) => {
        token.value = newToken
    }

    // 添加清除 token 的方法
    const clearToken = () => {
        token.value = ""
    }

    return {
        token,
        setToken,
        clearToken
    }
}, {
    persist: {
        key: 'userInfo',
        storage: localStorage,
        paths: ["token"]
    }
})
