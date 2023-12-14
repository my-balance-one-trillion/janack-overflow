<template>
  <div class="container mx-auto max-w-custom">
    <Header></Header>
    <Router-View v-if="userInfoLoaded" />
  </div>
</template>

<script setup>
import axios from "axios";
import { onBeforeMount ,ref } from "vue";
import Header from "@/components/Header.vue";
import { useAuthStore } from "@/stores/auth";

const authStore = useAuthStore();
const userInfoLoaded = ref(false);

onBeforeMount (async () => {
  if (authStore.token) {
    await getUserInfo();
    console.log("사용자 정보가 스토어에 등록되었습니다.")
    userInfoLoaded.value = true;
  }
});

async function getUserInfo() {
  const response = await axios.get("/mypage/myinfo", {
    headers: {
      authorization: authStore.token,
    },
  });
  authStore.setUserInfo(response.data);  
  console.log("함수 내 유저 인포 생성.")
}
console.log("함수 외 유저 인포 생성.")
</script>

<style scoped>
@import "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css";
.max-w-custom {
  max-width: 1300px;
}
</style>
