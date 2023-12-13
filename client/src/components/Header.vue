<template>
  <div class="flex items-center justify-between py-2">
    <div class="w-32">
      <img src="/images/logo.svg" alt="" class="object-contain" />
    </div>
    <div class="flex justify-between gap-4 h-auto">
      <div class="mx-auto">
        <router-link to="/mypage"
          ><i class="fa-regular fa-circle-user fa-xl"></i
        ></router-link>
      </div>
      <div class="mx-auto">
        <router-link to="/logout"
          ><i class="fa-solid fa-door-open fa-xl"></i
        ></router-link>
      </div>

      <div class="mx-auto">
        <i class="fa-solid fa-right-to-bracket fa-2xl"></i>
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
  </div>
</template>
<script setup>
import { ref } from "vue";

const menuBar = ref(null);
const nav = ref(null);
const menuBg = ref(null);

function menuOnClick() {
  menuBar.value.classList.toggle("change");
  nav.value.classList.toggle("change");
  menuBg.value.classList.toggle("change-bg");
}
</script>
