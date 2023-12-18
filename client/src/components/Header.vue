<template>
  <header class="flex items-center justify-between py-2">
    <div class="w-32">
      <router-link to="/">
        <img src="/images/logo.svg" alt="" class="object-contain" />
      </router-link>
    </div>
    <div class="flex justify-between gap-4 h-auto">
      <div class="mx-auto" @click="removeToken" v-if="useAuthStore().token">
        <router-link to="" class="text-gray-600 text-xl">LOGOUT</router-link>
      </div>
      <div class="mx-auto" v-else>
        <router-link to="/login" class="text-gray-600 text-xl">
          LOGIN
        </router-link>
      </div>
      <div class="mx-auto" v-if="useAuthStore().token">
        <router-link to="/mypage">
          <i class="fa-regular fa-circle-user fa-2xl"></i>
        </router-link>
      </div>
      <div class="w-7"></div>
      <div class="mx-auto bars-wrap">
        <button id="menu">
          <div ref="menuBar" id="menu-bar" @click="menuOnClick">
            <div id="bar1" class="bar"></div>
            <div id="bar2" class="bar"></div>
            <div id="bar3" class="bar"></div>
          </div>
          <nav ref="nav" class="nav" id="nav">
            <ul>
              <li><router-link to="/">메인</router-link></li>
              <li><router-link to="/saving">적금</router-link></li>
              <li><router-link to="/community">커뮤니티</router-link></li>
              <li><router-link to="/chat">채팅</router-link></li>
            </ul>
          </nav>
        </button>
        <div ref="menuBg" class="menu-bg" id="menu-bg"></div>
      </div>
    </div>
  </header>
</template>
<script setup>
import { ref } from "vue";
import { useAuthStore } from "@/stores/auth";
import axios from "axios";

const menuBar = ref(null);
const nav = ref(null);
const menuBg = ref(null);

const authStore = useAuthStore();

function removeToken() {
  localStorage.removeItem("token");
  authStore.clearToken();
  alert("로그아웃되었습니다.");

  // axios.get("http://localhost:8081/logout")
}

function menuOnClick() {
  menuBar.value.classList.toggle("change");
  nav.value.classList.toggle("change");
  menuBg.value.classList.toggle("change-bg");
}
</script>
