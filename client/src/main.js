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

var cnt=0;
router.beforeEach((to, from, next) => {
    // 이전 페이지의 경로를 저장 to: 온곳 from: 가는곳
    console.log("to.fullPath", to.fullPath, "from.fullPath", from.fullPath);
    sessionStorage.setItem('previousRoute', from.fullPath);
    if(to.fullPath === '/signup'){
        alert('sad');
        if(from.fullPath === '/signup') {
            router.push('/login');
            return;
        } 
    } else if(to.fullPath === '/passMail'){
        cnt++;
        console.log("잡힘", cnt,  "to.fullPath", to.fullPath, "from.fullPath", from.fullPath);
        
        if(from.fullPath === '/login' && cnt === 2){
            router.push('/');
            return;
        }
    }
    next();
});

app.use(VueHighlightJS);
app.mount("#app");