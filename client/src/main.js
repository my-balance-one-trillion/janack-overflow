import "./assets/css/common.css";
import 'tailwindcss/tailwind.css';
import {
    createApp
} from "vue";
import App from "./App.vue";
import axios from "axios";
import {
    createPinia
} from 'pinia';
import router from "./router";
import VueHighlightJS from 'vue3-highlightjs'
// import 'highlight.js/styles/solarized-light.css'

//axios 기본설정
axios.defaults.baseURL = "http://localhost:8081";
//pinia 기본설정
const pinia = createPinia();
const app = createApp(App);
app.config.globalProperties.$axios = axios;
app.use(pinia);
app.use(router);

router.beforeEach((to, from, next) => {
    // 이전 페이지의 경로를 저장
    console.log(to.fullPath);
    sessionStorage.setItem('previousRoute', from.fullPath);
    if(to.fullPath === '/signup'){
        alert('sad');
        if(from.fullPath === '/signup') {
            router.push('/login');
            return;
        }
    }
    next();
});

app.use(VueHighlightJS);
app.mount("#app");