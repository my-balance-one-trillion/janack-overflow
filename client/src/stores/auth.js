import { createPinia } from "pinia";

const pinia = createPinia();

export const useAuthStore = pinia.defineStore('auth',{
    state:()=>({
        token: localStorage.getItem('token') || null,
        userInfo: null
    })
    action:{
        setToken(token){
            this.token = token;
            localStorage.setItem('token',token);
        },
        clearToken(){
            this.token = null;
            localStorage.removeItem('token');
            this.userInfo = null;
        },
        setUserInfo(userInfo){
            this.userInfo = userInfo;
        }
    },
}),
