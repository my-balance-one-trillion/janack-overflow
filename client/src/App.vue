<template>
  <div class="container mx-auto max-w-custom">
    <Header></Header>
    <Router-View />
  </div>
</template>

<script setup>
import axios from "axios";
import { onBeforeMount } from "vue";
import Header from "@/components/Header.vue";
import { useAuthStore } from "@/stores/auth";
import { useRouter } from "vue-router";

const authStore = useAuthStore();
const router = useRouter();

onBeforeMount(async () => {
  await handleRouteLogic();
});

router.beforeEach(async (to, from, next) => {
  await handleRouteLogic();
  next();
});

async function handleRouteLogic() {
  try {
    if (authStore.token) {
      await getUserInfo();
      console.log("사용자 정보가 스토어에 등록되었습니다.");
    }
  } catch (error) {
    console.error("getUserInfo 오류:", error);
  }
}

async function getUserInfo() {
  const response = await axios.get("/mypage/myinfo", {
    headers: {
      authorization: authStore.token,
    },
  });
  authStore.setUserInfo(response.data);  
}
</script>

<style scoped>
@import "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css";
.max-w-custom {
  max-width: 1300px;
}
</style>
