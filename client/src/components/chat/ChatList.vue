<template>
  <div
    class="mx-auto my-16 w-full max-w-4xl p-4 bg-white border border-gray-200 rounded-lg shadow sm:p-6 dark:bg-gray-800 dark:border-gray-700"
  >
    <h5
      class="mb-3 text-base font-semibold text-gray-900 md:text-xl dark:text-white"
    >
    채팅방 목록
    </h5>
    <p class="text-sm font-normal text-gray-500 dark:text-gray-400">
      다른 사람들의 에러 해결을 도와주세요!
    </p>
    <ul class="my-4 space-y-3">
      <template v-for="(room, index) in p.chatRoomList">
      <li>
        <div
          class="flex items-center p-3 text-base font-bold text-gray-900 rounded-lg bg-gray-50 hover:bg-gray-100 group hover:shadow dark:bg-gray-600 dark:hover:bg-gray-500 dark:text-white"
        >
          <span class="flex-1 ms-3 whitespace-nowrap">{{ room.roomName }}</span>
          <span>{{room.now}}/{{ room.max }}</span>
          <span
            class="inline-flex items-center justify-center px-2 py-0.5 ms-3 text-xs font-medium text-gray-500 bg-gray-200 rounded dark:bg-gray-700 dark:text-gray-400"
            >방주인</span
          >
        </div>
      </li>
    </template>
    </ul>
    <div>
      <a
        href="#"
        class="inline-flex items-center text-xs font-normal text-gray-500 hover:underline dark:text-gray-400"
      >
        <svg
          class="w-3 h-3 me-2"
          aria-hidden="true"
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 20 20"
        >
          <path
            stroke="currentColor"
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M7.529 7.988a2.502 2.502 0 0 1 5 .191A2.441 2.441 0 0 1 10 10.582V12m-.01 3.008H10M19 10a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"
          />
        </svg>
        Why do I need to connect with my wallet?</a
      >
    </div>
  </div>
</template>

<script setup>
import { defineProps } from "vue";

const p = defineProps({
    chatRoomList:{
        type: Array,
    }
})
</script>

<style>

</style>
