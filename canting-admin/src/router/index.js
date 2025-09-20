import { createWebHistory, createRouter } from "vue-router";
import {useUserStore} from "../store/user.js";

const routes = [
    {
        path: '/',
        name: "First",
        component: () => import('../page/First.vue'),
        children: [
            {
                path: '',  // 修改为空字符串，作为默认子路由
                name: 'Home',
                component: () => import('../components/HomeComponents.vue')
            },
            {
                path: 'user',  // 子路由路径不需要加/
                children: [
                    {
                        path: "staff",
                        component: ()=>import('../components/UserStaff.vue')
                    },
                    {
                        path: "customers",
                        component: ()=>import("../components/UserCustomers.vue")
                    }
                ]
            },
            {
                path: 'orders',  // 子路由路径不需要加/
                children: [  // 修正拼写：children 不是 children
                    {
                        path: 'list',  // 子路由路径不需要加/
                        component: () => import('../components/OrdersList.vue'),
                    },
                    {
                        path: 'history',  // 子路由路径不需要加/
                        component: () => import('../components/OrdersHistory.vue'),
                    }
                ]
            },
            {
                path: 'dishes',  // 子路由路径不需要加/
                children: [  // 修正拼写：children 不是 children
                    {
                        path: 'list',  // 子路由路径不需要加/
                        component: () => import('../components/DishesList.vue'),
                    },
                    {
                        path: 'category',  // 子路由路径不需要加/
                        component: () => import('../components/DishesCategory.vue'),
                    }
                ]
            },
            {
                path: 'tables',  // 子路由路径不需要加/
                component: () => import('../components/TablesComponents.vue')
            },
            {
                path: 'coupons',  // 子路由路径不需要加/
                component: () => import('../components/CouponsComponents.vue')
            },
            {
                path: 'reports',  // 子路由路径不需要加/
                children: [
                    {
                        path: "sales",
                        component: ()=>import('../components/SalesReport.vue')
                    },
                    {
                        path: "dishes",
                        component: ()=>import("../components/DishReport.vue")

                    }
                ]
            },
            {
                path: 'settings',  // 子路由路径不需要加/
                component: () => import('../components/SettingsComponents.vue')
            }
        ]
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import("../page/login.vue")
    },
    {
        path: "/register",
        name: 'Register',
        component: () => import("../page/register.vue")
    },
    {
        path: '/:pathMatch(.*)*',  // 添加404路由
        redirect: '/'
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    console.log('Navigating to:', to.path)
    const userStore = useUserStore()
    const userToken = userStore.token

    // 如果访问的是登录或注册页面，直接放行
    if (to.path === '/login' || to.path === '/register') {
        next()
        return
    }

    // 如果有token，继续访问
    if (userToken) {
        next()
    } else {
        // 没有token，跳转到登录页面
        next('/login')
    }
})


export default router
