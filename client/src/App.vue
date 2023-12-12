<script setup>
import Header from "@/components/Header.vue";
</script>

<template>
  <div class="bg-blue-200 page-wrapper">
    <Header></Header>
    <Router-View />
    <div class="test">
      text test 한글 테스트 <i class="fa-regular fa-circle-user"> </i>
    </div>
  </div>
</template>

<style scoped>
@import "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css";

.test {
  width: 100px;
  height: 100px;
  background-color: blue;
  margin: 0 auto;
}
</style>
