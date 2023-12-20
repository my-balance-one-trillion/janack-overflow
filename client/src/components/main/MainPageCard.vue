<template>
  <div class="flex flex-col w-5/12 mx-1">
    <div class="max-w-5xl px-10 py-6 my-4 bg-white rounded-lg shadow-md">
      <div class="flex items-center justify-between">
        <span class="text-sm font-light text-gray-600">{{ props.issue.createdAt }}</span>
        <div class="flex">
          <fwb-badge class="px-2 py-1 text-base font-bold text-black rounded bg-badge-bg" v-text="props.issue.category"></fwb-badge>
        </div>
      </div>
      <div class="mt-2">
        <div class="flex justify-between">
          <a :href="'/community/detail/'+props.issue.id" class="text-xl font-bold text-gray-700 hover:text-gray-600 h-[56px] line-clamp-2">{{ props.issue.title }}</a>
          <div class="my-auto mr-2 whitespace-nowrap">
            <i class="fa-regular fa-thumbs-up fa-xl"></i>
            <span class="ml-2 text-sm">{{ props.issue.likes }}</span>
          </div>
        </div>
        <p class="mt-2 h-[40px] overflow-hidden text-sm text-gray-600 line-clamp-2">{{ props.issue.content }}</p>
      </div>
      <div class="flex items-center justify-between mt-4">
        <a class="text-sm font-bold text-sub-grn hover:underline hover:text-main-grn" :href="'/community/detail/'+props.issue.id">Read more</a>
        <div class="flex">
          <a class="flex items-center" href="#">
            <img class="hidden object-cover w-10 h-10 mx-4 rounded-full sm:block"
                 :src="'/images/'+props.issue.communityUsersDTO.profileImage" alt="avatar">
            <h1 class="font-bold text-gray-700">{{ props.issue.communityUsersDTO.nickname }}</h1>
          </a>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { FwbBadge } from 'flowbite-vue';
    const props = defineProps({
        issue:{
            type: Object
        }
    })
    console.log("issue:", props.issue.amount);
</script>
<style scoped>
</style>