<template>
  <div class="min-h-screen bg-gray-100 py-6 flex flex-col justify-center sm:py-12">
    <div class="relative py-3 sm:max-w-xl sm:mx-auto">
      <div class="absolute inset-0 bg-gradient-to-r 
      from-red-300 to-red-600 shadow-lg transform 
      -skew-y-6 sm:skew-y-0 sm:-rotate-6 sm:rounded-3xl"></div>
      
      <div class="relative px-4 py-10 bg-white shadow-lg sm:rounded-3xl sm:p-20">
        <div class="max-w-md mx-auto">
          <div>
            <h1 class="text-2xl text-center font-semibold">Main</h1>
          </div>
  
          <div class="divide-y divide-gray-200">
            <div class="py-8 text-base leading-6 space-y-4 text-gray-700 sm:text-lg sm:leading-7">
  
              <p class="text-sm">response 전달받으면 하단에 텍스트 표시</p>
              <p class="text-sm text-red-700" v-if="result">Get Mapping</p>
  
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  </template>

<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";
const result = ref();

onMounted(() => {
  axios.get("http://localhost:8081/main").then((response) => {
    console.log(response.data);
    result.value = response.data;
  });
});
</script>

<style scoped></style>
