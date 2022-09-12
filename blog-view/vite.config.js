import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import {ElementPlusResolver, AntDesignVueResolver, TDesignResolver} from 'unplugin-vue-components/resolvers'
import {visualizer} from 'rollup-plugin-visualizer';
import {splitVendorChunkPlugin} from 'vite'
import {resolve} from 'path'
import viteCompression from "vite-plugin-compression";
// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        vue(),
        visualizer(),
        AutoImport({
            resolvers: [
                ElementPlusResolver(),
                AntDesignVueResolver(),
                TDesignResolver({
                    library: 'vue-next'
                })
            ],
        }),
        Components({
            resolvers: [
                ElementPlusResolver(),
                AntDesignVueResolver(),
                TDesignResolver({
                    library: 'vue-next'
                })
            ],
        }),
        splitVendorChunkPlugin(),
        viteCompression(),
    ],
    build: {
        rollupOptions: {
            input: {
                main: resolve(__dirname, 'index.html'),
            },
            output: {
                manualChunks: {
                    hljs: ['highlight.js'],
                    tdesign: ['tdesign-vue-next/es'],
                    element: ['element-plus'],
                    emoji: ['markdown-it-emoji'],
                    markdownIt: ['markdown-it'],
                    ant: ['ant-design-vue/es']
                }
            }
        }
    },
    define: {
        "process.env": {},
    },
})