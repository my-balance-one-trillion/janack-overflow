<template>
  <header class="flex items-center justify-between py-2">
    <div class="w-32">
      <router-link to="/">
        <img src="/images/logo.svg" alt="" class="object-contain" />
      </router-link>
    </div>
    <div class="flex justify-between items-center gap-4">
      <div class="login-wrap h-6">
        <div
          @click="removeToken"
          v-if="authStore.token"
          class="text-gray-600 text-xl"
        >
          LOGOUT
        </div>
        <router-link to="/login" class="text-gray-600 text-xl" v-else>
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
      <div class="menu-wrap" v-if="role.role !== 'ADMIN' || !authStore.token">
        <div class="bars-wrap flex justify-end">
          <div ref="menuBar" id="menu-bar" @click="menuOnClick" class="z-30">
            <div id="bar1" class="bar"></div>
            <div id="bar2" class="bar"></div>
            <div id="bar3" class="bar"></div>
          </div>
        </div>

        <nav ref="nav" class="nav z-30" id="nav">
          <ul>
            <li><router-link to="/">메인</router-link></li>
            <li v-if="authStore.token">
              <router-link to="/saving">적금</router-link>
            </li>
            <li><router-link to="/community">커뮤니티</router-link></li>
            <li v-if="authStore.token">
              <router-link to="/chat">채팅</router-link>
            </li>
          </ul>
        </nav>
        <div ref="menuBg" class="menu-bg z-20" id="menu-bg"></div>
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
  // location.reload();
  localStorage.removeItem("token");
  authStore.clearToken();
  alert("로그아웃되었습니다.");
  console.log("현재 주소", currentRoute.path);
  if (currentRoute.path == "/") {
    location.reload();
  }
  router.push("/");
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
.menu-wrap {
  position: relative;
}
#menu-bar {
  width: 35px;
  cursor: pointer;
}

.bar {
  height: 5px;
  width: 100%;
  background-color: #000;
  border-radius: 5px;
  transition: 0.3s ease;
}

#bar1 {
  transform: translateY(-4px);
}

#bar3 {
  transform: translateY(4px);
}

.menu-bg {
  top: -25px;
  right: 0;
  position: absolute;
  width: 0;
  height: 0;
  background: radial-gradient(circle, #bf1131, #bf1131);
  border-radius: 50%;
  transition: 0.3s ease;
}

.nav {
  position: absolute;
  transform: translate(-100%, -10%);
  transition: 0.3s ease;
  display: none;
  width: 10rem;
}

.nav ul {
  padding: 0 10px 0 22px;
}

.nav li {
  list-style: none;
  padding: 12px 0;
  text-align: right;
}

.nav li a {
  color: white;
  font-size: 25px;
  text-decoration: none;
}

.nav li a:hover {
  font-weight: bold;
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
  transform: translateY(-6px) rotateZ(45deg);
}

.change-bg {
  width: 520px;
  height: 470px;
  transform: translate(60%, -30%);
}

/*
 * search btn
 */
.glow {
  top: -10%;
  left: -10%;
  width: 120%;
  height: 120%;
  border-radius: 100%;
}

.glow-1 {
  animation: glow1 4s linear infinite;
}

.glow-2 {
  animation: glow2 4s linear infinite;
  animation-delay: 100ms;
}

.glow-3 {
  animation: glow3 4s linear infinite;
  animation-delay: 200ms;
}

.glow-4 {
  animation: glow4 4s linear infinite;
  animation-delay: 300ms;
}

@keyframes glow1 {
  0% {
    transform: translate(10%, 10%) scale(1);
  }

  25% {
    transform: translate(-10%, 10%) scale(1);
  }

  50% {
    transform: translate(-10%, -10%) scale(1);
  }

  75% {
    transform: translate(10%, -10%) scale(1);
  }

  100% {
    transform: translate(10%, 10%) scale(1);
  }
}

@keyframes glow2 {
  0% {
    transform: translate(-10%, -10%) scale(1);
  }

  25% {
    transform: translate(10%, -10%) scale(1);
  }

  50% {
    transform: translate(10%, 10%) scale(1);
  }

  75% {
    transform: translate(-10%, 10%) scale(1);
  }

  100% {
    transform: translate(-10%, -10%) scale(1);
  }
}

@keyframes glow3 {
  0% {
    transform: translate(-10%, 10%) scale(1);
  }

  25% {
    transform: translate(-10%, -10%) scale(1);
  }

  50% {
    transform: translate(10%, -10%) scale(1);
  }

  75% {
    transform: translate(10%, 10%) scale(1);
  }

  100% {
    transform: translate(-10%, 10%) scale(1);
  }
}

@keyframes glow4 {
  0% {
    transform: translate(10%, -10%) scale(1);
  }

  25% {
    transform: translate(10%, 10%) scale(1);
  }

  50% {
    transform: translate(-10%, 10%) scale(1);
  }

  75% {
    transform: translate(-10%, -10%) scale(1);
  }

  100% {
    transform: translate(10%, -10%) scale(1);
  }
}
</style>
