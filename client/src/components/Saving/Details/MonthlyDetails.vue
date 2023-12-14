<script setup>
import {onMounted, ref} from 'vue';
import axios from "axios";
import dayjs from "dayjs";

const issueList = ref([]);

onMounted(async () => {
  try {
    const response = await axios.get('/issues');
    issueList.value = response.data;
    filterDataByMonth();
  } catch (error) {
    console.log(error);
  }
});

const now = dayjs().format("YYYY-MM");
const current = ref(now);

// 이전 월 조회
const prevMonth = () => {
  const [year, month] = current.value.split('-');
  const prevDate = new Date(year, month - 2);
  current.value = `${prevDate.getFullYear()}-${prevDate.getMonth() + 1}`;
  filterDataByMonth();
};

// 다음 월 조회
const nextMonth = () => {
  const [year, month] = current.value.split('-');
  const nextDate = new Date(year, month);
  current.value = `${nextDate.getFullYear()}-${nextDate.getMonth() + 1}`;
  filterDataByMonth();
};

// 월별 데이터 필터링 함수
const filterDataByMonth = () => {
  const filteredList = [];

  // 현재 월을 가져옴
  const [year, month] = current.value.split('-');

  // issueList를 순회하면서 현재 월과 일치하는 데이터를 filteredList에 추가
  issueList.value.forEach((item) => {
    const itemYear = dayjs(item.createdAt).format('YYYY');
    const itemMonth = dayjs(item.createdAt).format('MM');

    if (itemYear === year && itemMonth === month) {
      filteredList.push(item);
    }
  });

  // 필터링된 데이터를 issueList에 할당
  issueList.value = filteredList;
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