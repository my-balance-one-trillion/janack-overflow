<script setup>
import SavingSideBar from "@/components/Saving/SavingSideBar.vue";
import Intro from "@/components/Saving/Intro.vue";
import {onMounted, ref} from "vue";
import axios from "axios";
import {useAuthStore} from "../stores/auth";
import dayjs from "dayjs";

const state = ref(0);
onMounted(async () => {
  await getAccountInProgress();
});

async function getAccountInProgress () {
  await axios
      .get('/savings/progress',{
        headers: {
          Authorization: useAuthStore().token,
        },
      })
      .then((response) => {
        state.value = 1;
      })
      .catch((error) => {
        state.value = 0;
      })
}
</script>

<template>
  <intro v-if="state===0"></intro>
  <div  v-if="state===1" class="flex my-10 gap-12">
    <saving-side-bar class="w-1/4"></saving-side-bar>
    <router-view class="w-3/4 "></router-view>
  </div>
</template>


<style scoped>
aside{
  width: 20%;
}
section{
  width: 80%;
}
</style>