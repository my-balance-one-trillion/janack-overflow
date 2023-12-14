<template>
  <div class="flex my-10 gap-12">
    <saving-side-bar class="w-1/4"></saving-side-bar>
    <router-view class="w-3/4 "></router-view>
  </div>
</template>

<script setup>
import SavingSideBar from "@/components/Saving/SavingSideBar.vue";
</script>
<style scoped>
aside{
  width: 20%;
}
section{
  width: 80%;
}
</style>