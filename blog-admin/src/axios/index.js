import axios from 'axios';
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import {ElMessage} from "element-plus";

NProgress.configure({showSpinner: false, minimum: 0.2, easeing: 'swing', speed: 500, trickleRate: 0.2});

//前置拦截
axios.interceptors.request.use(config => {
    config.headers = {'token': window.localStorage.getItem("token")}
    NProgress.start()
    return config;
})

axios.interceptors.response.use(function (response) {
    // 对响应数据做点什么
    return response;
}, function (error) {
    // 对响应错误做点什么
    if (error.response.status === 401) {
        window.localStorage.clear();
        this.$router.push("/login").then(() => {
            ElMessage.error("登陆状态失效");
        })
        NProgress.done();
    }
});

//后置拦截
axios.interceptors.response.use(response => {
        NProgress.done();
        if (response.status === 401) {
            window.localStorage.clear();
            this.$router.push("/login").then(() => {
                ElMessage.error("登陆状态失效");
            })
        }
        let code = response.data.code;
        switch (code) {
            case 200:
                return response;
            case 300:
                ElMessage.error("用户名或密码不合法");
                break;
            case 501:
                ElMessage.error("用户不存在");
                break;
            case 400:
                ElMessage.error("密码错误");
                break;
            case 405:
                ElMessage.error("用户名已经被注册");
                break;
            case 502:
                ElMessage.error("密码错误");
                break;
            case 503:
                ElMessage.error("未登录");
                break;
            case 504:
                ElMessage.error("没有访问权限");
                break;
            case 505:
                ElMessage.error("已经登录");
                break;
            case 600:
                ElMessage.error("手机号格式有误");
                break;
            case 601:
                ElMessage.error("手机号已被注册");
                break;
            case 602:
                ElMessage.error("未发生验证码");
                break;
            case 603:
                ElMessage.error("验证码错误");
                break;
            case 604:
                ElMessage.error("发生失败");
                break;
            case 605:
                ElMessage.error("验证码已发送，请60s后再试");
                break;
            case 420:
                ElMessage.error("请求参数完整");
                break;
            case 511:
                ElMessage.error("标签已经存在");
                break;
            case 512:
                ElMessage.error("更新失败");
                break;
            case 513:
                ElMessage.error("不是作者");
                break;
            case 514:
                ElMessage.error("请求的文章不存再或已经被删除");
                break;
            case 515:
                ElMessage.error("分类已存在");
                break;
            case 516:
                ElMessage.error("保存失败，请先提交");
                break;
            case 517:
                ElMessage.error("文章已经发布");
                break;
            case 518:
                ElMessage.error("分类未被创建");
                break;
            case 500:
                ElMessage.error("500-系统错误");
                break;
        }
    }
)


axios.defaults.baseURL = 'http://localhost:8888/api';
axios.defaults.timeout = 120000 // 设置默认timeout为2分钟


export function getArticleAdmin(current, size) {
    return axios({
        url: './admin/articles/get',
        method: 'POST',
        data: {
            "pageNumber": current,
            "pageSize": size,
        }
    })
}

export function getTagAdmin(current, size) {
    return axios({
        url: './admin/tag/get',
        method: 'POST',
        data: {
            "pageNumber": current,
            "pageSize": size,
        }
    })
}

export function getFilingAdmin(current, size) {
    return axios({
        url: './admin/filing/get',
        method: 'POST',
        data: {
            "pageNumber": current,
            "pageSize": size,
        }
    })
}

export function upLoadImg(file) {
    let formData = new FormData();
    formData.append('img', file[0])
    return axios({
        url: './upload/img',
        method: 'post',
        data: formData,
        headers: {'Content-Type': 'multipart/form-data'}
    })
}

export function getFiling() {
    return axios({
        url: './filing/all',
        method: 'POST'
    })
}

export function getTag() {
    return axios({
        url: './tag/all',
        method: 'POST'
    })
}

export function publishArticle(articleId, title, introduction, content, tag, filingName, bgImg) {
    return axios({
        url: './admin/articles/publish',
        method: 'POST',
        data: {
            'articleId': articleId,
            'title': title,
            'introduction': introduction,
            'content': content,
            'tag': tag,
            'filingName': filingName,
            'bgImg': bgImg
        }
    })
}

export function getDeletedArticle(current, size) {
    return axios({
        url: './admin/articles/deletedArticle',
        method: 'POST',
        data: {
            "pageNumber": current,
            "pageSize": size
        }
    })
}

export function recoverArticle(articleId) {
    let formData = new FormData();
    formData.append("articleId", articleId)
    return axios({
        url: './admin/articles/recover',
        method: 'POST',
        data: formData
    })
}

export function deleteTag(tagId) {
    let formData = new FormData;
    formData.append("tagId", tagId)
    return axios({
        url: './admin/tag/delete',
        method: 'POST',
        data: formData
    })
}

export function editTag(tagId, tagName) {
    let formData = new FormData;
    formData.append("tagId", tagId);
    formData.append("tagName", tagName)
    return axios({
        url: './admin/tag/edit',
        method: 'POST',
        data: formData
    })
}

export function addNewTag(tagName) {
    let formData = new FormData;
    formData.append("tagName", tagName);
    return axios({
        url: './admin/tag/add',
        method: 'POST',
        data: formData
    })
}

export function addFiling(filingName) {
    let formData = new FormData;
    formData.append("filingName", filingName)
    return axios({
        url: './admin/filing/add',
        method: 'POST',
        data: formData
    })
}

export function editFilingName(filingId, filingName) {
    let formData = new FormData;
    formData.append("filingId", filingId)
    formData.append("filingName", filingName)
    return axios({
        url: './admin/filing/reName',
        method: 'POST',
        data: formData
    })
}

export function deleteFiling(filingId) {
    let formData = new FormData;
    formData.append("filingId", filingId)
    return axios({
        url: './admin/filing/delete',
        method: 'POST',
        data: formData
    })
}

export function login(username, password) {
    return axios({
        url: './user/login',
        method: 'POST',
        data: {
            "username": username,
            "password": password
        }
    })
}

export function userInfo() {
    return axios({
        url: './user/status',
        method: 'POST'
    })
}

export function logout() {
    return axios({
        url: './user/logout',
        method: 'POST'
    })
}

export function deleteArticle(articleId) {
    let formData = new FormData;
    formData.append("articleId", articleId)
    return axios({
        url: './admin/articles/delete',
        method: 'POST',
        data: formData
    })
}

export function editArticle(editForm) {
    return axios({
        url: './admin/articles/edit',
        method: 'POST',
        data: editForm
    })
}

export function getArticleById(articleId) {
    let formData = new FormData;
    formData.append("articleId", articleId)
    return axios({
        url: './articles/views/' + articleId,
        method: "POST",
        data: formData
    })
}

export function getLastEdit() {
    return axios({
        url: './admin/articles/lastEdit',
        method: 'POST'
    })
}

export function autoSave(articleId, content) {
    let formData = new FormData;
    formData.append("articleId", articleId)
    formData.append("content", content)
    return axios({
        url: './admin/articles/save',
        method: 'POST',
        data: formData
    })
}

export function articleSetTop(articleId) {
    let formData = new FormData;
    formData.append("articleId", articleId)
    return axios({
        url: './admin/articles/setTop',
        method: 'POST',
        data: formData
    })
}

export function getAllMessage(current, size) {
    return axios({
        url: './message/get',
        method: 'POST',
        data: {
            pageNumber: current,
            pageSize: size
        }
    })
}

export function deleteMessageById(id) {
    let formData = new FormData;
    formData.append("messageId", id)
    return axios({
        url: './admin/message/delete',
        method: 'POST',
        data: formData
    })
}

export function getErrorLog(current, size) {
    return axios({
        url: './admin/error/get',
        method: 'POST',
        data: {
            pageNumber: current,
            pageSize: size
        }
    })
}

export function getTruckLog(current, size) {
    return axios({
        url: './admin/truck/get',
        method: 'POST',
        data: {
            pageNumber: current,
            pageSize: size
        }
    })
}

export function deleteTruckLogByIp(ip) {
    let formData = new FormData;
    formData.append("ip", ip)
    return axios({
        url: './admin/truck/delete',
        method: 'POST',
        data: formData
    })
}