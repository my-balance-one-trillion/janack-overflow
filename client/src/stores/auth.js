import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || null,
    userInfo: null
  }),
  actions: {
    setToken(token) {
      this.token = token;
      localStorage.setItem('token', token);
    },
    clearToken() {
      this.token = null;
      localStorage.removeItem('token');
      this.userInfo = null;
    },
    setUserInfo(userInfo) {
      this.userInfo = userInfo;
    }
  },
});
