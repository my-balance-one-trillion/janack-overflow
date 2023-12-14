<script setup>
import {onMounted, ref} from 'vue';
import axios from "axios";
import dayjs from "dayjs";
import {useAuthStore} from "../../../stores/auth";

// 적금 정보
const accountInfo = ref({
  anctName: '',
  acntNum: '',
  acntAmount: 0,
  goalName: '',
  goalAmount: 0,
  createdAt: '',
});

const now = dayjs();

onMounted(async () => {
  try {
    const response = await axios.get('/savings/progress', {
      headers: {
        Authorization: useAuthStore().token,
      }
    });
    const data = response.data.inProgressAccount;
    accountInfo.value.anctName = data.acntName;
    accountInfo.value.acntNum = data.acntNum;
    accountInfo.value.acntAmount = data.acntAmount;
    accountInfo.value.goalName = data.goalName;
    accountInfo.value.goalAmount = data.goalAmount;
    accountInfo.value.createdAt = Math.floor(dayjs(now).diff(data.createdAt, "day", true))+1;
  } catch (error) {
    console.error(error);
  }
});

</script>

<template>
    <div
        class="w-2/3 px-10 py-10 m-auto mb-20 bg-gray-100 rounded-3xl shadow text-center flex justify-between items-center gap-7">
      <div class="text-left space-y-3">
        <div class="inline-flex items-center bg-red-100 text-main-red text-xs px-4 py-2 rounded-full shadow">Day {{ accountInfo.createdAt }}</div>
        <div class="text-2xl text-main-red">{{  accountInfo.anctName  }}<span class="text-black"> 적금</span></div>
        <div class="">{{  accountInfo.acntNum }}</div>
      </div>
      <div class="text-3xl text-main-red font-bold">{{ Number(accountInfo.acntAmount).toLocaleString() }}<span class="text-black font-medium"> 원</span></div>
    </div>
</template>

<style scoped>

</style>