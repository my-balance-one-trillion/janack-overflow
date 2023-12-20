<script setup>
import {onMounted, ref} from 'vue';
import axios from "axios";
import dayjs from "dayjs";
import {useAuthStore} from "../../../stores/auth";

const accountInfo = ref({});
const now = dayjs();
const daydiff = ref();

onMounted(async () => {
  getAccountInProgress();
});
const getAccountInProgress = async () => {
  await axios
      .get('/savings/progress',{
        headers: {
          Authorization: useAuthStore().token,
        },
      })
      .then((response) => {
        const data = response.data.inProgressAccount;
        accountInfo.value = data;
        daydiff.value = Math.ceil(dayjs(now).diff(data.createdAt, "day", true))+1;
        console.log(daydiff.value);
      })
      .catch((error) => {

      })
}
</script>

<template>
    <div
        class="w-2/3 px-10 py-10 m-auto mb-20 bg-gray-100 rounded-3xl shadow text-center flex justify-between items-center gap-7">
      <div class="text-left space-y-3">
        <div class="inline-flex items-center bg-red-100 text-main-red text-xs px-4 py-2 rounded-full shadow">Day {{ daydiff }}</div>
        <div class="text-2xl text-main-red">{{  accountInfo.acntName  }}<span class="text-black"> 적금</span></div>
        <div class="">농협 {{  accountInfo.acntNum }}</div>
      </div>
      <div class="text-3xl text-main-red font-bold">{{ Number(accountInfo.acntAmount).toLocaleString() }}<span class="text-black font-medium"> 원</span></div>
    </div>
</template>

<style scoped>

</style>