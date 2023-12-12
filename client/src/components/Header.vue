<template>
  <div class="flex items-center justify-between h-10 bg-red-200">
    <div class="w-32 bg-slate-300">
      <img src="../assets/images/logo.svg" alt="" class="object-contain" />
    </div>
    <div class="flex justify-around h-auto w-28 bg-slate-200">
      <div class="mx-auto">
        <button><i class="fa-regular fa-circle-user fa-2xl"></i></button>
      </div>
      <div class="mx-auto bars-wrap">
        <button id="menu">
          <div ref="menuBar" id="menu-bar" @click="menuOnClick">
            <div id="bar1" class="bar"></div>
            <div id="bar2" class="bar"></div>
            <div id="bar3" class="bar"></div>
          </div>
          <nav ref="nav" class="nav" id="nav">
            <ul>
              <li><a href="#">메인</a></li>
              <li><a href="#">적금</a></li>
              <li><a href="#">커뮤니티</a></li>
              <li><a href="#">마이페이지</a></li>
            </ul>
          </nav>
        </button>
        <div ref="menuBg" class="menu-bg" id="menu-bg"></div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref } from 'vue';

const menuBar = ref(null);
const nav = ref(null);
const menuBg = ref(null);

function menuOnClick() {
  menuBar.value.classList.toggle("change");
  nav.value.classList.toggle("change");
  menuBg.value.classList.toggle("change-bg");
}
</script>
