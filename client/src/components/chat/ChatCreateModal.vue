<template>
  <div
    class="fixed left-0 right-0 z-50 items-center justify-center overflow-x-hidden overflow-y-auto bg-black bg-opacity-50 h-modal md:h-full top-4 md:inset-0"
    @click.self="closeModal"
    
  >
    <div
      class="relative w-full max-w-md px-4 h-full md:h-auto mx-auto my-[200px]"
    >
      <!-- Modal content -->
      <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
        <div class="flex justify-end p-2">
          <button
            type="button"
            class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-800 dark:hover:text-white"
            @click="closeModal"
          >
            <svg
              class="w-5 h-5"
              fill="currentColor"
              viewBox="0 0 20 20"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                fill-rule="evenodd"
                d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                clip-rule="evenodd"
              ></path>
            </svg>
          </button>
        </div>
        <form class="px-6 pb-4 space-y-6 lg:px-8 sm:pb-6 xl:pb-8" @submit.prevent>
          <h3 class="text-xl font-medium text-gray-900 dark:text-white">
            채팅방 생성
          </h3>
          <div>
            <label
              for="room-name"
              class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300"
              >방 제목</label
            >
            <input
              v-model="roomReq.roomName"
              type="text"
              name="roon-name"
              id="room-name"
              class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-sub-grn focus:border-sub-grn block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white"
              placeholder="채팅방 제목"
              required
            />
          </div>
          <div>
            <label
              for="category"
              class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300"
              >카테고리</label
            >
            <select
              v-model="roomReq.category"
              id="category"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-sub-grn focus:border-sub-grn block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500"
              required
            >
              <option value="" disabled hidden selected>카테고리</option>
              <option value="syntax">Syntax</option>
              <option value="language">Language</option>
              <option value="database">Data Base</option>
              <option value="os">OS</option>
            </select>
          </div>
        </form>
        <div class="flex justify-end p-5">
            <button
              @click="sendReq"
              class="w-2/6 text-white bg-main-grn hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-sub-grn"
            >
              생성 하기
            </button>
          </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { defineProps, defineEmits, ref } from "vue";
import { useAuthStore } from '../../stores/auth';
const roomReq = ref({
  roomName: null,
  category: '',
});

const emit = defineEmits(["toggle-modalCheck", "send-roomInfo"]);

function closeModal() {
  emit("toggle-modalCheck", false);
}
function sendReq() {
  console.log("emit-data", roomReq.value);
  emit("send-roomInfo", roomReq.value);
}
</script>
<style></style>
