import {createRouter, createWebHashHistory} from 'vue-router'
import Login from '../views/Login.vue'
import Home from "../views/Home.vue";
import Index from "../components/Index.vue";
import Publish from "../components/edit/Publish.vue";
import ArticleAdmin from "../components/admin/ArticleAdmin.vue";
import DeletedArticle from "../components/admin/DeletedArticle.vue";
import TagAdmin from "../components/admin/TagAdmin.vue";
import FilingAdmin from "../components/admin/FilingAdmin.vue";
import EditArticle from "../views/EditArticle.vue";
import MessageAdmin from "../components/admin/MessageAdmin.vue";
import ErrorLogAdmin from "../components/admin/ErrorLogAdmin.vue";
import TruckLogAdmin from "../components/admin/TruckLogAdmin.vue";

const routes = [
    {
        path: '/login',
        name: 'login',
        component: Login,
        meta: {name: '登录'}
    },
    {
        path: '/',
        name: 'home',
        component: Home,
        redirect: '/index',
        children: [
            {
                path: '/index',
                name: 'Index',
                component: Index,
                meta: {name: '首页'},
            },
            {
                path: '/publish',
                name: 'publish',
                component: Publish,
                meta: {name: '发布文章'},
            },
            {
                path: '/article',
                name: 'article',
                component: ArticleAdmin,
                meta: {name: '文章管理'},
            },
            {
                path: '/deleted',
                name: 'deletedArticle',
                component: DeletedArticle,
                meta: {title: '文章管理'}
            },
            {
                path: '/tag',
                name: 'tag',
                component: TagAdmin,
                meta: {name: '标签管理'},
            },
            {
                path: '/filing',
                name: 'filing',
                component: FilingAdmin,
                meta: {name: '分类管理'},
            },
            {
                path: '/edit/:articleId',
                name: 'editArticle',
                component: EditArticle,
                meta: {name: "编辑文章"}
            },
            {
                path: '/message',
                name: 'message',
                component: MessageAdmin,
                meta: {name: "留言管理"}
            },
            {
                path: '/error',
                name: 'error',
                component: ErrorLogAdmin,
                meta: {name: "错误日志"}
            },
            {
                path: '/truck',
                name: 'truck',
                component: TruckLogAdmin,
                meta: {name: "访客信息"}
            }
        ]
    },
]
const router = new createRouter({
    history: createWebHashHistory(import.meta.env.BASE_URL),
    routes
})

export default router
