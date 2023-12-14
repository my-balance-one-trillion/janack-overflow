<template>
  <div class="container mx-auto max-w-custom">
    <Header></Header>
    <Router-View />
  </div>
</template>

<script setup>
import axios from "axios";
import { onMounted } from "vue";
import Header from "@/components/Header.vue";
import { useAuthStore } from "./stores/auth";


onMounted(async () => {
  if (useAuthStore().token) {
    await getUserInfo();
  }
});

async function getUserInfo() {
  const response = await axios.get("/mypage/myinfo", {
    headers: {
      authorization: useAuthStore().token,
    },
  });
  useAuthStore().setUserInfo(response.data);
  console.log(useAuthStore().userInfo)
}
console.log(useAuthStore().userInfo)
</script>

<style scoped>
@import "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css";
.max-w-custom {
  max-width: 1300px;
}
</style>
