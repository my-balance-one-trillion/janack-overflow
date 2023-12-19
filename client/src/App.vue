<template>
  <div class="container mx-auto max-w-custom overflow-hidden">
    <Header />
    <Router-View />
    <Footer />
  </div>
</template>

<script setup>
import axios from "axios";
import { onBeforeMount } from "vue";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import { useAuthStore } from "@/stores/auth";
import { useRouter } from "vue-router";

const authStore = useAuthStore();
const router = useRouter();


// --------------------
// 유저정보 우선실행 로직
// --------------------


onBeforeMount(async () => {
  await handleRouteLogic();
});

router.beforeEach(async (to, from, next) => {
  await handleRouteLogic();
  next();
});

async function handleRouteLogic() {

  authStore.initializeTokenFromStorage();
  try {
    if (authStore.token) {
      await authStore.getUserInfo();
    }
  } catch (error) {
    console.error("getUserInfo 오류:", error);
  }

}

</script>

<style scoped>
@import "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css";

.max-w-custom {
  max-width: 1300px;
}
</style>