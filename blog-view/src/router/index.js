import {createRouter, createWebHistory} from "vue-router";
import Index from "../views/Index.vue";
import Home from "../views/Home.vue";
import BlogDetail from '../views/BlogDetail.vue'
import TimeLine from '../views/TimeLine.vue'
import Filing from '../views/Filing.vue'
import AboutPage from "../views/AboutPage.vue";
import Search from "../views/Search.vue";
import SearchDetail from "../views/SearchDetail.vue";
import Message from '../views/Message.vue'

const routes = [
    {
        path: "/:catchAll(.*)",
        redirect: '/'
    },
    {
        path: "/",
        name: "index", //index包含header footer等需要在每个页面都会显示的东西
        component: Index,
        redirect: '/home',
        meta: {title: '主页'},
        children: [
            {
                path: '/home',
                name: 'home',
                component: Home,
            },
            {
                path: '/article/:articleId',
                name: 'BlogDetail',
                component: BlogDetail,
                meta: {title: '博客'}
            },
            {
                path: '/timeLine',
                name: 'TimeLine',
                component: TimeLine,
                meta: {title: '归档'}
            },
            {
                path: '/filing',
                name: 'Filing',
                component: Filing,
                meta: {title: '分类'}
            },
            {
                path: '/about',
                name: 'About',
                component: AboutPage,
                meta: {title: '关于我'}
            },
            {
                path: '/search',
                name: 'Search',
                component: Search,
                meta: {title: '搜索'}
            },
            {
                path: '/result',
                name: 'Result',
                component: SearchDetail,
                meta: {title: "搜索结果"}
            },
            {
                path: '/message',
                name: 'Message',
                component: Message,
                meta: {title: "留言板"}
            }
        ]
    },
];


const router = createRouter({
    history: createWebHistory(),
    routes,
});
router.afterEach(() => {
    window.scrollTo(0, 0);
})
export default router;