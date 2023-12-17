<template>
  <div class="min-h-screen bg-gray-100 py-6 flex flex-col justify-center sm:py-12">
    <div class="relative py-3 sm:max-w-xl sm:mx-auto">
      <div
        class="absolute inset-0 bg-gradient-to-r from-red-300 to-red-600 shadow-lg transform -skew-y-6 sm:skew-y-0 sm:-rotate-6 sm:rounded-3xl">
      </div>

      <div class="relative px-4 py-10 bg-white shadow-lg sm:rounded-3xl sm:p-20">
        <div class="max-w-md mx-auto">
          <div>
            <h1 class="text-3xl font-semibold text-center">Login</h1>
          </div>

          <div class="divide-y divide-gray-200">
            <div class="py-8 text-base leading-6 space-y-4 text-gray-700 sm:text-lg sm:leading-7">
              <div class="relative pl-14" @keyup.enter="login">
                <div class="relative">
                  <input autocomplete="off" id="email" name="email" type="text"
                    class="peer placeholder-transparent h-10 w-10/12 border-t-0 border-l-0 border-r-0 border-b-2 border-red-700 text-gray-900 focus:outline-none focus:borer-rose-600"
                    v-model="state.input.email" placeholder="Email" />
                  <label for="email"
                    class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">Email</label>
                </div>

                <br />

                <div class="relative">
                  <input autocomplete="off" id="password" name="password" type="password"
                    class="peer placeholder-transparent h-10 w-10/12 border-t-0 border-l-0 border-r-0 border-b-2 border-red-700 text-gray-900 focus:outline-none focus:borer-rose-600"
                    v-model="state.input.password" placeholder="Password" />
                  <label for="password"
                    class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">Password</label>
                </div>
              </div>

              <div class="relative flex justify-center">
                <button class="bg-red-700 text-white rounded-3xl px-28 py-1" @click="login">
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
          <router-link to="/" class="pl-2 text-sm text-red-700">비밀번호 찾기</router-link>
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
  try {
    const response = await axios.post(
      "http://localhost:8081/login",
      state.input,
      {
        headers: {
          "Content-Type": "application/json",
          "X-Requested-With": "XMLHttpRequest",
        },
      }
    );

    // 기존 코드
    // localStorage.setItem("token", response.headers.authorization);

    // 변경된 코드
    authStore.setToken(response.headers.authorization);

    window.alert("로그인이 수행되었습니다");
    router.push("/");
  } catch (error) {
    console.log("로그인 실패", error);
    window.alert("로그인 실패했습니다");
  }
};
</script>

<style scoped>
input:focus {
  outline: none;
}
</style>
