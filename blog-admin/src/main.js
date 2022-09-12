import {createApp} from 'vue'
import {createPinia} from 'pinia'

import App from './App.vue'
import router from './router'
// import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import VueMarkdownEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';
// import TDesign from 'tdesign-vue-next';

import Prism from 'prismjs';

VueMarkdownEditor.use(vuepressTheme, {
    Prism,
});
const app = createApp(App)

app.use(createPinia())
app.use(router)
// app.use(ElementPlus)
// app.use(TDesign);
app.use(VueMarkdownEditor);
app.config.globalProperties.$router = router
app.mount('#app')
