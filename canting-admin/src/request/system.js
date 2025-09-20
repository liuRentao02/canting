import instance from "./index.js";

export const login = (data)=>{
    return instance({
        url: '/api/login',
        method: "POST",
        data
    })
}

export const register = (data)=>{
    return instance({
        url:'/api/user/register',
        method:"POST",
        data
    })
}

export const logout = () => {
  return instance({
      url:"api/logout",
      method:'POST'
  })
}