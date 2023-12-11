import "./assets/css/common.css";
import { createApp } from "vue";
import App from "./App.vue";
import axios from "axios";
import { createPinia } from "pinia";

//axios 기본설정
axios.defaults.baseURL = "https://localhost:8081";

//pinia 기본설정
const pinia = createPinia();

const app = createApp(App);
app.config.globalProperties.$axios = axios;
app.use(pinia);
app.mount("#app");
