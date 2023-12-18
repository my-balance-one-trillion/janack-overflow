<script setup>
import {onMounted, ref} from 'vue';
import axios from "axios";
import dayjs from "dayjs";
import {useAuthStore} from "../../../stores/auth";

const monthlyList = ref({issue: [], solution: []});
const current = ref(dayjs().format("YYYY-MM"));


onMounted(async () => {
  await getMonthlyIssues();
});

const getMonthlyIssues = async () => {
  const [year, month] = current.value.split("-");

  await axios
      .get(`/savings/monthly-issues?year=${year}&month=${month}`, {
        headers: {
          Authorization: useAuthStore().token,
        }
      })
      .then((response) => {
        monthlyList.value.issue = response.data.issue.reverse();
        monthlyList.value.solution = response.data.solution.reverse();
      })
      .catch((error) => {
      })
};
const now = dayjs().format("YYYY-MM");

// 이전 월 조회
const prevMonth = () => {
  current.value = dayjs(current.value).subtract(1, "month").format("YYYY-MM");
  getMonthlyIssues();
};

// 다음 월 조회
const nextMonth = () => {
  if (current.value !== dayjs().format("YYYY-MM")) {
    current.value = dayjs(current.value).add(1, "month").format("YYYY-MM");
    getMonthlyIssues();
  }
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
      <td class="px-5 py-3 w-1/4">날짜</td>
      <td class="px-5 py-3 w-2/4">에러</td>
      <td class="px-5 py-3 w-1/4">금액</td>
    </tr>
    </thead>
    <tbody v-if="monthlyList.solution.length === 0"
           class="bg-grey-light flex items-center justify-center overflow-y-scroll w-full text-center h-60">
      <tr class="text-center">
        <td class="p-3 text-xl text-center">내역이 없습니다.</td>
      </tr>
    </tbody>
    <tbody v-else class="bg-grey-light flex flex-col items-center overflow-y-scroll w-full text-center h-60">
      <tr v-for="(item, index) in monthlyList.solution" :key="index" class="flex w-full border-b-2">
        <td class="px-5 py-3 w-1/4 text-left">{{ dayjs(item.createdAt).format('YYYY. MM. DD HH:mm') }}</td>
        <td class="px-5 py-3 w-2/4">{{ monthlyList.issue[index].title }}</td>
        <td class="px-5 py-3 w-1/4 text-main-red font-medium text-xl">
          {{ Number(monthlyList.issue[index].amount).toLocaleString() }}
        </td>
      </tr>
    </tbody>
  </table>
</template>

<style scoped>

</style>