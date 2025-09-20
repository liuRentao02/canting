import instance from "./index.js";

export function updateUser() {
}


export function deleteUser() {
}


export const getUserStaffList = (data)=>{
    return instance({
        url:"api/user/list",
        method:"GET",
        params: data
    })
}