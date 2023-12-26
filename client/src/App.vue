<template>
  <div class="container mx-auto max-w-custom min-h-screen overflow-hidden">
    <Header :role="accountRole" />
    <Router-View />
  </div>
  <Footer />
  <Modal v-if="isModalOpen" @close="closeModal" :msg="alertMsg" :mode="modalMode">
  </Modal>
</template>

<script setup>
import { onBeforeMount, ref,watch } from "vue";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import { useAuthStore } from "@/stores/auth";
import { useRouter } from "vue-router";
import Modal from './components/Modal.vue';

const authStore = useAuthStore();
const router = useRouter();
const accountRole = ref([]);

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
      accountRole.value = authStore.userInfo.role;
    }
  } catch (error) {
    console.error("getUserInfo 오류:", error);
  }

}

// --------------------
// 모달 로직
// --------------------

const isModalOpen = ref(false);
const alertMsg = ref('');
const modalMode = ref('');

window.alert = (message) => {
  isModalOpen.value = true;
  modalMode.value = 'alert';
  alertMsg.value = message;
}

const closeModal = () => {
  isModalOpen.value = false;
  alertMsg.value = '';
};

</script>

<style scoped>
@import "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css";

.max-w-custom {
  max-width: 1300px;
}
</style>