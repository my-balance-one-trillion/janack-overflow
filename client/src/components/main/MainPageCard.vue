<template>
  <div class="flex flex-col w-5/12 mx-1">
    <div class="max-w-5xl px-10 py-6 my-4 bg-white rounded-lg shadow-md">
      <div class="flex items-center justify-between">
        <span class="font-light text-gray-600">dec 12, 2023(createAt)</span>
        <div class="flex">
          <fwb-badge
            class="px-2 py-1 font-bold text-black rounded bg-badge-bg"
            v-text="props.issue.category"
          ></fwb-badge>
        </div>
      </div>
      <div class="mt-2">
        <div class="flex justify-between">
          <a
            class="text-2xl font-bold text-gray-700 hover:text-gray-600"
            href="#"
            >{{ props.issue.title }}</a
          >
          <div class="my-auto mr-2">
            <i class="fa-regular fa-thumbs-up fa-xl"></i>
            <span class="ml-2">{{ props.issue.likes }}</span>
          </div>
        </div>
        <p
          class="mt-2 text-gray-600 overflow-hidden"
          style="height: 80px; overflow: hidden"
        >
          {{ props.issue.content }}
        </p>
      </div>
      <div class="flex items-center justify-between mt-4">
        <a
          class="font-bold text-sub-grn hover:underline hover:text-main-grn"
          href="#"
          >Read more</a
        >
        <div class="flex">
          <a class="flex items-center" href="#">
            <img
              class="hidden object-cover w-10 h-10 mx-4 rounded-full sm:block"
              src="https://cdn.pixabay.com/photo/2018/03/26/20/49/tiger-3264048_1280.jpg"
              alt="avatar"
            />
            <h1 class="font-bold text-gray-700">
              {{ props.issue.communityUsersDTO.nickname }}
            </h1>
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from "vue";
const props = defineProps({
  issue: {
    type: Object,
  },
});
console.log("issue:", props.issue.amount);
</script>

<style scoped></style>
