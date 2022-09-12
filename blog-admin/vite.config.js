import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import {resolve} from "path";
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import {ElementPlusResolver, TDesignResolver} from 'unplugin-vue-components/resolvers'
import {visualizer} from 'rollup-plugin-visualizer';
import {splitVendorChunkPlugin} from 'vite'
import viteCompression from "vite-plugin-compression";
// https://vitejs.dev/config/
export default defineConfig({
    // plugins: [vue()],
    // // base: process.env.NODE_ENV === 'production' ? './' : '/',
    // build: {
    //     assetsDir: 'admin'
    // },
    plugins: [
        vue(),
        visualizer(),
        AutoImport({
            resolvers: [
                ElementPlusResolver(),
                TDesignResolver({
                    library: 'vue-next'
                })
            ],
        }),
        Components({
            resolvers: [
                ElementPlusResolver(),
                TDesignResolver({
                    library: 'vue-next'
                })
            ],
        }),
        splitVendorChunkPlugin(),
        viteCompression(),
    ],
    build: {
        assetsDir: 'admin',
        rollupOptions: {
            input: {
                main: resolve(__dirname, 'index.html'),
            },
            output: {
                manualChunks: {
                    hljs: ['highlight.js'],
                    tdesign: ['tdesign-vue-next/es'],
                    kangc: ['@kangc/v-md-editor'],
                    element: ['element-plus']
                }
            }
        }
    },
    define: {
        "process.env": {},
    },
})
