<template>
  <header class="flex items-center justify-between py-2">
    <div class="w-32">
      <router-link to="/">
        <img src="/images/logo.svg" alt="" class="object-contain" />
      </router-link>
    </div>
    <div class="flex justify-between items-center gap-4">
      <div class="login-wrap h-6">
        <div class="text-gray-600 text-xl cursor-pointer" v-if="authStore.token" @click="removeToken">
          LOGOUT
        </div>
        <router-link to="/login" class="text-gray-600 text-xl cursor-pointer" v-else>
          LOGIN
        </router-link>
      </div>
      <div class="contol-wrap" v-if="authStore.token">
        <div class="mx-auto" v-if="role.role == 'USER'">
          <router-link to="/mypage" class="flex items-center">
            <i class="fa-regular fa-circle-user fa-2xl"></i>
          </router-link>
        </div>
        <div class="mx-auto" v-if="role.role == 'ADMIN'">
          <router-link to="/admin">
            <i class="fa-solid fa-gear fa-2xl"></i>
          </router-link>
        </div>
      </div>
      <div class="menu-wrap relative" v-if="role.role !== 'ADMIN' || !authStore.token">
        <div class="bars-wrap flex justify-end">
          <div ref="menuBar" id="menu-bar" @click="menuOnClick" class="z-30 w-9 cursor-pointer">
            <div id="bar1" class="bar h-1 bg-black rounded-xl duration-200"></div>
            <div id="bar2" class="bar h-1 bg-black rounded-xl duration-200"></div>
            <div id="bar3" class="bar h-1 bg-black rounded-xl duration-200"></div>
          </div>
        </div>
        <nav ref="nav" class="nav z-30 absolute w-40 -translate-x-40 hidden">
          <ul>
            <li class="text-right p-3">
              <router-link to="/" class="text-white text-2xl hover:font-bold">메인</router-link>
            </li>
            <li class="text-right p-3" v-if="authStore.token">
              <router-link to="/saving" class="text-white text-2xl hover:font-bold">적금</router-link>
            </li>
            <li class="text-right p-3">
              <router-link to="/community" class="text-white text-2xl hover:font-bold">커뮤니티</router-link>
            </li>
            <li class="text-right p-3" v-if="authStore.token">
              <router-link to="/chat" class="text-white text-2xl hover:font-bold">채팅</router-link>
            </li>
          </ul>
        </nav>
        <div ref="menuBg" class="menu-bg z-20 absolute" id="menu-bg"></div>
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
const props = defineProps(["role"]);
role.value = props;
console.log(role.value.role);

function removeToken() {
  let logoutConfirm = confirm('로그아웃 하시겠습니까?');
  if(logoutConfirm){
    localStorage.removeItem("token");
    authStore.clearToken();
    console.log("현재 주소", currentRoute.path);
    if (currentRoute.path == "/") {
      location.reload();
    }
    router.push("/");
  }else{
    return;
  }
}

function menuOnClick() {
  menuBar.value.classList.toggle("change");
  nav.value.classList.toggle("change");
  menuBg.value.classList.toggle("change-bg");
}
</script>

<style scoped>
/* ------------------------------------
헤더 nav transform 
------------------------------------ */

#bar1 {
  transform: translateY(-4px);
}

#bar3 {
  transform: translateY(4px);
}

.menu-bg {
  top: -25px;
  right: 0;
  width: 0;
  height: 0;
  background: radial-gradient(circle, #bf1131, #bf1131);
  border-radius: 50%;
  transition: 0.3s ease;
}

.change {
  display: block;
}

.change .bar {
  background-color: white;
}

.change #bar1 {
  transform: translateY(4px) rotateZ(-45deg);
}

.change #bar2 {
  opacity: 0;
}

.change #bar3 {
  transform: translateY(-4px) rotateZ(45deg);
}

.change-bg {
  width: 520px;
  height: 470px;
  transform: translate(60%, -30%);
}
</style>
