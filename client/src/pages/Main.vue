<template>
  <LoggedInMain v-if="isLoggedIn"/>
  <LoggedOutMain v-else />
</template>
<script setup>
  import LoggedInMain from '../components/main/LoggedInMain.vue';
  import LoggedOutMain from '../components/main/LoggedOutMain.vue';
  const isLoggedIn = false;
</script>
<style scoped>
</style>
