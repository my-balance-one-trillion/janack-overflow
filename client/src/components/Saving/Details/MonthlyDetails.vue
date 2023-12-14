<script setup>
import {onMounted, ref} from 'vue';
import axios from "axios";
import dayjs from "dayjs";
import {useAuthStore} from "../../../stores/auth";

const issueList = ref([]);
onMounted(async () => {
  getMonthlyIssues();
});


const getMonthlyIssues = async () => {
  // 현재 날짜를 기준으로 년도와 월 추출
  const now = new Date();
  const year = now.getFullYear();
  const month = now.getMonth() + 1;

  try {
    const response = await axios.get(`/monthly-issues?year=${year}&month=${month}`,{
      headers: {
        Authorization: useAuthStore().token,
      }
    });
    console.log(response.data);
    issueList.value = response.data;
  } catch (error) {
    console.error(error);
  }
};
const now = dayjs().format("YYYY-MM");
const current = ref(now);

// 이전 월 조회
const prevMonth = () => {
  const [year, month] = current.value.split('-');
  const prevDate = new Date(year, month - 2);
  current.value = `${prevDate.getFullYear()}-${prevDate.getMonth() + 1}`;
};

// 다음 월 조회
const nextMonth = () => {
  const [year, month] = current.value.split('-');
  const nextDate = new Date(year, month);
  current.value = `${nextDate.getFullYear()}-${nextDate.getMonth() + 1}`;
};


</script>

<template>
  <div class="my-10 flex justify-center gap-7 items-center">
    <button class="px-4 py-2 bg-main-red text-white rounded" @click="prevMonth">&lt</button>
    <div class="text-2xl w-40 text-center">{{ current.split('-')[0] + "년 " + current.split('-')[1] + "월" }}</div>
    <button v-show="current!==now" class="px-4 py-2 bg-main-red text-white rounded" @click="nextMonth">&gt</button>
    <button v-show="current===now" class="px-4 py-2 bg-btn-grey text-white rounded" disabled>&gt</button>
  </div>
  <table class="w-full border-gray-300 border-t-4 border-b-4">
    <thead class="bg-gray-100 text-2xl flex w-full border-b-4">
    <tr class="flex w-full text-center">
      <td class="p-3 w-1/5">날짜</td>
      <td class="p-3 w-2/5">에러</td>
      <td class="p-3 w-1/5">금액</td>
      <td class="p-3 w-1/5">잔액</td>
    </tr>
    </thead>
    <tbody class="bg-grey-light flex flex-col items-center overflow-y-scroll w-full text-center h-60">
    <tr v-for="(item, index) in issueList" :key="index" class="flex w-full border-b-2">
      <td class="p-3 w-1/5">{{ dayjs(item.createdAt).format('YYYY. MM. DD hh:mm') }}</td>
      <td class="p-3 w-2/5">{{ item.title }}</td>
      <td class="p-3 w-1/5 text-main-red font-medium text-xl">{{ Number(item.amount).toLocaleString() }}</td>
      <td class="p-3 w-1/5"></td>
    </tr>
    </tbody>
  </table>
</template>

<style scoped>

</style>