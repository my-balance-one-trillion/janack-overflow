<script setup>
import {onMounted, ref} from 'vue';
import axios from "axios";
import dayjs from "dayjs";
import {useAuthStore} from "@/stores/auth";

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
  <div class="flex items-center justify-center my-10 gap-7">
    <button class="px-4 py-2 text-white rounded bg-main-red" @click="prevMonth">&lt</button>
    <div class="w-40 text-2xl text-center">{{ current.split('-')[0] + "년 " + current.split('-')[1] + "월" }}</div>
    <button v-show="current!==now" class="px-4 py-2 text-white rounded bg-main-red" @click="nextMonth">&gt</button>
    <button v-show="current===now" class="px-4 py-2 text-white rounded bg-btn-grey" disabled>&gt</button>
  </div>
  <table class="w-full border-t-4 border-b-4 border-gray-300">
    <thead class="flex w-full text-2xl bg-gray-100 border-b-4">
      <tr class="flex w-full text-center">
        <td class="w-1/4 px-5 py-3">날짜</td>
        <td class="w-2/4 px-5 py-3">에러</td>
        <td class="w-1/4 px-5 py-3">금액</td>
      </tr>
    </thead>
    <tbody v-if="monthlyList.solution.length === 0"
           class="flex items-center justify-center w-full overflow-y-scroll text-center bg-grey-light h-60">
      <tr class="text-center">
        <td class="p-3 text-xl text-center">내역이 없습니다.</td>
      </tr>
    </tbody>
    <tbody v-else class="flex flex-col items-center w-full overflow-y-scroll text-center bg-grey-light h-60">
      <tr v-for="(item, index) in monthlyList.solution" :key="index" class="flex w-full border-b-2">
        <td class="w-1/4 px-5 py-3 text-left">{{ dayjs(item.createdAt).format('YYYY. MM. DD HH:mm') }}</td>
        <td class="w-2/4 px-5 py-3 h-8 line-clamp-1">{{ monthlyList.issue[index].title }}</td>
        <td class="w-1/4 px-5 py-3 text-xl font-medium text-main-red">
          {{ Number(monthlyList.issue[index].amount).toLocaleString() }}
        </td>
      </tr>
    </tbody>
  </table>
</template>

<style scoped>

</style>