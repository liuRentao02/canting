import { createWebHistory, createRouter } from "vue-router";
import Login from "../page/login.vue";

const routes = [
    {
        path: '/',
        name: "First",
        component: () => import('../page/First.vue'),
        children: [
            {
                path: '',  // 修改为空字符串，作为默认子路由
                component: () => import('../components/HomeComponents.vue')
            },
            {
                path: 'user',  // 子路由路径不需要加/
                component: () => import('../components/UserComponents.vue')
            },
            {
                path: 'orders',  // 子路由路径不需要加/
                component: () => import('../components/OrdersComponents.vue'),  // 建议使用专门的组件
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
                component: () => import('../components/DishesComponents.vue'),  // 建议使用专门的组件
                children: [  // 修正拼写：children 不是 children
                    {
                        path: 'list',  // 子路由路径不需要加/
                        component: () => import('../components/DishesList.vue'),
                    },
                    {
                        path: 'category',  // 子路由路径不需要加/
                        component: () => import('../components/DishesCategory.vue'),
                    },
                    {
                        path: 'add',  // 子路由路径不需要加/
                        component: () => import('../components/DishesAdd.vue'),
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
                component: () => import('../components/ReportsComponents.vue')
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
        component: () => Login
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
    // 这里可以添加路由守卫逻辑
    next()
})

export default router
