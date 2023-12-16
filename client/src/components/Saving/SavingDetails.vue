<script setup>
import HorizontalBarChart from "./component/HorizontalBarChart.vue";
import LineChart from "./component/LineChart.vue";
import MyAccount from "./Details/MyAccount.vue";
import MonthlyDetails from "./Details/MonthlyDetails.vue";

const depositCounts = [10, 15, 8, 12, 20]; // 월별 적금 횟수 데이터
const depositAmounts = [0, 20000, 40000, 50000, 100000]; // 월별 누적 금액
</script>

<template>

  <div class="w-full space-y-14">
    <!--    나의 적금-->
    <div class="my-10 text-3xl text-main-red">나의 적금</div>
    <my-account></my-account>

    <!--    월별 내역 조회-->
    <div>
      <div class="mt-20 text-3xl text-main-red">월별 내역 조회</div>
      <monthly-details></monthly-details>
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