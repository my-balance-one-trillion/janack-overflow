<template>
  <LoggedInMain v-if="isLoggedIn"/>
  <LoggedOutMain v-else />
</template>

<script setup>
  import { onMounted, ref } from 'vue';
  import axios from "axios";
  import MainPageCard from "../components/main/MainPageCard.vue";
  import LoggedInMain from '../components/main/LoggedInMain.vue';
  import LoggedOutMain from '../components/main/LoggedOutMain.vue';
  const issueList = ref([]);
  const isLoggedIn = true;
  onMounted(()=>{
    axios.get("http://localhost:8081/main")
    .then((response) => {
        issueList.value = response
        console.log("response", response);
    })
  })
</script>

<style scoped>
</style>