import App from './App.vue'
import router from './router'
// import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import {createApp} from "vue";
// import Antd from 'ant-design-vue';
// import 'ant-design-vue/dist/antd.css';
// import hover from 'hover.css'
// import TDesign from 'tdesign-vue-next';
import 'tdesign-vue-next/es/style/index.css';
import 'highlight.js/styles/github-dark.css';
// import 'highlight.js/styles/androidstudio.css'

const app = createApp(App);

app.use(router)
// app.use(ElementPlus)
// app.use(Antd)
// app.use(hover)
// app.use(TDesign)
app.config.globalProperties.$router = router
app.mount('#app')