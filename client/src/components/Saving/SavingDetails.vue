<script setup>
import {ref} from 'vue';
import HorizontalBarChart from "./component/HorizontalBarChart.vue";
import LineChart from "./component/LineChart.vue";

const now = new Date().getFullYear() + "-" + (new Date().getMonth() + 1);
const current = ref(new Date().getFullYear() + "-" + (new Date().getMonth() + 1));

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

const depositCounts = [10, 15, 8, 12, 20]; // 월별 적금 횟수 데이터
const depositAmounts = [0, 20000, 40000, 50000, 100000]; // 월별 누적 금액
</script>

<template>

  <div class="w-full space-y-14">
    <!--    나의 적금-->
    <div class="my-10 text-3xl text-main-red">나의 적금</div>
    <div
        class="w-2/3 px-10 py-10 m-auto mb-20 bg-gray-100 rounded-3xl shadow text-center flex justify-between items-center gap-7">
      <div class="text-left space-y-3">
        <div class="inline-flex items-center bg-red-100 text-main-red text-xs px-4 py-2 rounded-full shadow">Day 2</div>
        <div class="text-2xl">스프링 적금</div>
        <div class="">1234-5678-000000</div>
      </div>
      <div class="text-3xl text-main-red font-bold">124,000원</div>
    </div>

    <!--    월별 내역 조회-->
    <div>
      <div class="mt-20 text-3xl text-main-red">월별 내역 조회</div>
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
        <tr class="flex w-full border-b-2">
          <td class="p-3 w-1/5">2023. 12. 12 15:39</td>
          <td class="p-3 w-2/5">NullpointerException</td>
          <td class="p-3 w-1/5 text-main-red font-medium text-xl">4,000</td>
          <td class="p-3 w-1/5">120,000원</td>
        </tr>
        <tr class="flex w-full border-b-2">
          <td class="p-3 w-1/5">2023. 12. 12 15:39</td>
          <td class="p-3 w-2/5">NullpointerException</td>
          <td class="p-3 w-1/5 text-main-red font-medium text-xl">4,000</td>
          <td class="p-3 w-1/5">120,000원</td>
        </tr>
        <tr class="flex w-full border-b-2">
          <td class="p-3 w-1/5">2023. 12. 12 15:39</td>
          <td class="p-3 w-2/5">NullpointerException</td>
          <td class="p-3 w-1/5 text-main-red font-medium text-xl">4,000</td>
          <td class="p-3 w-1/5">120,000원</td>
        </tr>
        <tr class="flex w-full border-b-2">
          <td class="p-3 w-1/5">2023. 12. 12 15:39</td>
          <td class="p-3 w-2/5">NullpointerException</td>
          <td class="p-3 w-1/5 text-main-red font-medium text-xl">4,000</td>
          <td class="p-3 w-1/5">120,000원</td>
        </tr>
        <tr class="flex w-full border-b-2">
          <td class="p-3 w-1/5">2023. 12. 12 15:39</td>
          <td class="p-3 w-2/5">NullpointerException</td>
          <td class="p-3 w-1/5 text-main-red font-medium text-xl">4,000</td>
          <td class="p-3 w-1/5">120,000원</td>
        </tr>
        <tr class="flex w-full border-b-2">
          <td class="p-3 w-1/5">2023. 12. 12 15:39</td>
          <td class="p-3 w-2/5">NullpointerException</td>
          <td class="p-3 w-1/5 text-main-red font-medium text-xl">4,000</td>
          <td class="p-3 w-1/5">120,000원</td>
        </tr>
        </tbody>
      </table>
    </div>

    <!--  대시보드 -->
    <div>
      <div class="flex justify-center  p-7 bg-gray-100 rounded-3xl shadow">
        <div class="p-4 border m-auto bg-white rounded-xl">
          <div class="text-2xl">월별 적금 횟수</div>
          <div class="">
            <HorizontalBarChart :depositCounts="depositCounts"/>
          </div>
        </div>
        <div class="p-4 border m-auto bg-white rounded-xl">
          <div class="text-2xl">월별 적금 추이</div>
          <div class="">
            <LineChart :depositAmounts="depositAmounts"/>
          </div>
        </div>
      </div>
    </div>

  </div>

</template>

<style scoped>

</style>