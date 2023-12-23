<template>
  <header class="flex items-center justify-between py-2">
    <div class="w-32">
      <router-link to="/">
        <img src="/images/logo.svg" alt="" class="object-contain" />
      </router-link>
    </div>
    <div class="flex justify-between gap-4 h-auto">
      <div class="login-wrap">
        <div class="mx-auto" @click="removeToken" v-if="authStore.token">
          <router-link to="" class="text-gray-600 text-xl">
            LOGOUT
          </router-link>
        </div>
        <div class="mx-auto" v-else>
          <router-link to="/login" class="text-gray-600 text-xl">
            LOGIN
          </router-link>
        </div>
      </div>
      <div class="contol-wrap" v-if="authStore.token">
        <div class="mx-auto" v-if="role.role == 'USER'">
          <router-link to="/mypage">
            <i class="fa-regular fa-circle-user fa-2xl"></i> </router-link>
        </div>
        <div class="mx-auto" v-if="role.role == 'ADMIN'">
          <router-link to="/admin">
            <i class="fa-solid fa-gear fa-2xl"></i>
          </router-link>
        </div>
      </div>
      <div class="w-7" v-if="role.role !== 'ADMIN' || !authStore.token"></div>
      <div class="mx-auto bars-wrap" v-if="role.role !== 'ADMIN' || !authStore.token">
        <button id="menu">
          <div ref="menuBar" id="menu-bar" @click="menuOnClick">
            <div id="bar1" class="bar"></div>
            <div id="bar2" class="bar"></div>
            <div id="bar3" class="bar"></div>
          </div>
          <nav ref="nav" class="nav" id="nav">
            <ul>
              <li><router-link to="/">메인</router-link></li>
              <li v-if="authStore.token"><router-link to="/saving">적금</router-link></li>
              <li><router-link to="/community">커뮤니티</router-link></li>
              <li v-if="authStore.token"><router-link to="/chat">채팅</router-link></li>
            </ul>
          </nav>
        </button>
        <div ref="menuBg" class="menu-bg" id="menu-bg"></div>
      </div>
    </div>
  </header>
</template>
<script setup>
import { ref, defineProps } from "vue";
import { useAuthStore } from "@/stores/auth";
import { useRouter, useRoute } from "vue-router";

const menuBar = ref(null);
const nav = ref(null);
const menuBg = ref(null);

const authStore = useAuthStore();
const router = useRouter();
const currentRoute = useRoute();

const role = ref([]);
const props = defineProps(['role']);
role.value = props;
console.log(role.value.role)

function removeToken() {
  // location.reload();
  localStorage.removeItem("token");
  authStore.clearToken();
  alert("로그아웃되었습니다.");
  console.log("현재 주소", currentRoute.path);
  if (currentRoute.path == "/") {
    location.reload();
  }
  router.push('/');
}

function menuOnClick() {
  menuBar.value.classList.toggle("change");
  nav.value.classList.toggle("change");
  menuBg.value.classList.toggle("change-bg");
}
</script>
