<template>
  <div class="flex flex-col justify-center min-h-screen py-6 bg-white sm:py-12">
    <div class="relative py-3 sm:max-w-xl sm:mx-auto">
      <div
        class="absolute inset-0 bg-gradient-to-r from-green-700 to-red-600 shadow-lg transform -skew-y-6 sm:skew-y-0 sm:-rotate-6 sm:rounded-3xl">
      </div>

      <div class="relative px-4 py-10 shadow-lg bg-gray-50 sm:rounded-3xl sm:p-20">
        <div class="max-w-md mx-auto">
          <div>
            <h1 class="text-3xl font-semibold text-center">Login</h1>
          </div>

          <div class="divide-y divide-gray-200">
            <div class="py-8 space-y-4 text-base leading-6 text-gray-700 sm:text-lg sm:leading-7">
              <div class="relative pl-14" @keyup.enter="login">
                <div class="relative">
                  <input autocomplete="off" id="email" name="email" type="text"
                    class="w-10/12 h-10 text-gray-900 placeholder-transparent border-t-0 border-b-2 border-l-0 border-r-0 border-red-700 focus:ouline-none focus:ring-0 focus:border-main-red peer bg-gray-50 focus:outline-none focus:borer-rose-600"
                    v-model="state.input.email" placeholder="Email" />
                  <label for="email"
                    class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">Email</label>
                </div>

                <br />

                <div class="relative">
                  <input autocomplete="off" id="password" name="password" type="password"
                    class="w-10/12 h-10 text-gray-900 placeholder-transparent border-t-0 border-b-2 border-l-0 border-r-0 border-red-700 focus:ouline-none focus:ring-0 focus:border-main-red peer bg-gray-50 focus:outline-none focus:borer-rose-600"
                    v-model="state.input.password" placeholder="Password" />
                  <label for="password"
                    class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">Password</label>
                </div>
              </div>

              <div class="relative flex justify-center">
                <button class="py-1 text-white bg-red-700 rounded-3xl px-28" 
                @click="login">
                  로그인
                </button>
              </div>
            </div>
          </div>
        </div>
        <div class="flex justify-center">
          <p class="text-sm">첫 방문이신가요?</p>
          <router-link to="/signup" class="pl-2 text-sm text-red-700">회원가입</router-link>

          <p class="pl-5 text-sm">비밀번호를 잊으셨나요?</p>
          <router-link to="/passMail" class="pl-2 text-sm text-red-700">비밀번호 찾기</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/stores/auth";

axios.defaults.withCredentials = true;

const router = useRouter();
const authStore = useAuthStore();
const state = reactive({
  input: {
    email: "",
    password: "",
  },
});

const login = async () => {

  try{

    const res = await axios.post(
      "/login",
      state.input,
      {
        headers: {
          "Content-Type": "application/json",
          "X-Requested-With": "XMLHttpRequest",
        },
      }
    )
  
    authStore.setToken(res.headers.authorization);
      await authStore.getUserInfo();
      
      if (authStore.userInfo.role === 'ADMIN') {
        router.push('/admin');
      } else {
        const previousRoute = sessionStorage.getItem('previousRoute');
        if (previousRoute) {
          // 이전 페이지로 이동
          sessionStorage.removeItem('previousRoute');
          router.push(previousRoute);
        } else {
          // 이전 페이지가 없을 경우 기본 경로로 이동
          router.push("/");
        }
      }

  } catch(error) {
    console.error(error);
    window.alert(error.response.data);
  };

};
</script>

<style scoped>
input:focus {
  outline: none;
}
</style>
