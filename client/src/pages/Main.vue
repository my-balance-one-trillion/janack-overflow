<template>
  <LoggedInMain v-if="isLoggedin"/>
  <LoggedOutMain v-else />
</template>
<script setup>
  import { ref } from 'vue'
  import { useAuthStore } from '../stores/auth';
  import LoggedInMain from '@/components/main/LoggedInMain.vue';
  import LoggedOutMain from '@/components/main/LoggedOutMain.vue';
  
  const isLoggedin = ref();
  isLoggedin.value = localStorage.getItem("token");
  console.log("로그인 여부", isLoggedin.value);

 
</script>
<style scoped>
</style>
