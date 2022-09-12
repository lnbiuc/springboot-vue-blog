import axios from 'axios';
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

axios.defaults.baseURL = 'http://localhost:8888/api';


NProgress.configure({showSpinner: false, minimum: 0.2, easeing: 'swing', speed: 500, trickleRate: 0.2});

//前置拦截
axios.interceptors.request.use(config => {
    NProgress.start()
    return config;
})

//后置拦截
axios.interceptors.response.use(response => {
        NProgress.done()
        //获取返回结果
        let code = response.data.code;
        if (code === 519) {
            window.alert("QQ号或评论内容格式不合法")
        } else if (code === 520) {
            window.alert("QQ号不存在，请输入正确的QQ号")
        }
        return response;
    }
)

export function getBlogList(pageNumber, pageSize) {
    return axios({
        url: "./articles/title?pageNumber=" + pageNumber + "&pageSize=" + pageSize,
        method: 'GET',
    })
}

export function getCountInfo() {
    return axios({
        url: "./count",
        method: 'POST'
    })
}

export function getArticleByArticleId(articleId) {
    return axios({
        url: "./articles/views/" + articleId,
        method: 'POST'
    })
}

export function getTimeLineData() {
    return axios({
        url: './articles/time',
        method: 'POST'
    })
}

export function getFilingInfo() {
    return axios({
        url: './filing/filing',
        method: 'POST'
    })
}

export function searchTitle(title) {
    let formData = new FormData;
    formData.append("title", title)
    return axios({
        url: './search/title',
        method: 'POST',
        data: formData
    })
}

export function searchTagName(tagName) {
    let formData = new FormData;
    formData.append("tagName", tagName)
    return axios({
        url: './search/tagName',
        method: 'POST',
        data: formData
    })
}

export function searchFilingName(filingName) {
    let formData = new FormData;
    formData.append("filingName", filingName)
    return axios({
        url: './search/filingName',
        method: 'POST',
        data: formData
    })
}

export function searchFilingId(filingId) {
    let formData = new FormData;
    formData.append("filingId", filingId);
    return axios({
        url: './search/filingId',
        method: 'POST',
        data: formData
    })
}

export function searchTagId(tagId) {
    let formData = new FormData;
    formData.append("tagId", tagId);
    return axios({
        url: './search/tagId',
        method: 'POST',
        data: formData
    })
}

export function getTag() {
    return axios({
        url: './tag/all',
        method: 'POST'
    })
}

export function getMessage(current, size) {
    return axios({
        url: './message/get',
        method: 'POST',
        data: {
            "pageNumber": current,
            "pageSize": size,
        }
    })
}

export function sendMessage(qq, msg) {
    let formData = new FormData;
    formData.append("qqNumber", qq)
    formData.append("content", msg)
    return axios({
        url: './message/send',
        method: 'POST',
        data: formData
    })
}