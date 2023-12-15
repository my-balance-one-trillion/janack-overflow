import { defineStore } from "pinia";
import axios from "axios";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    token: null,
    userInfo: null,
  }),
  actions: {
    setToken(token) {
      this.token = token;
      localStorage.setItem("token", token);
    },
    clearToken() {
      this.token = null;
      localStorage.removeItem("token");
      this.userInfo = null;
    },
    setUserInfo(userInfo) {
      this.userInfo = userInfo;
    },
    initializeTokenFromStorage() {
      this.token = localStorage.getItem("token") || null;
    },
    async getUserInfo() {
      try {
        const response = await axios.get("/mypage/myinfo", {
          headers: {
            authorization: this.token,
          },
        });
        this.setUserInfo(response.data);
      } catch (error) {
        console.error("getUserInfo 오류:", error);
      }
    },
  },
});
