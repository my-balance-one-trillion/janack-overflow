import "./assets/css/common.css";
import 'tailwindcss/tailwind.css';
import { createApp } from "vue";
import App from "./App.vue";
import axios from "axios";
import { createPinia } from 'pinia';
import router from "./router";
import VueHighlightJS from 'vue3-highlightjs'
// import 'highlight.js/styles/solarized-light.css'

//axios 기본설정
axios.defaults.baseURL = "http://192.168.3.102:8081";
//pinia 기본설정
const pinia = createPinia();
const app = createApp(App);
app.config.globalProperties.$axios = axios;
app.use(pinia);
app.use(router);
app.use(VueHighlightJS);
app.mount("#app");
