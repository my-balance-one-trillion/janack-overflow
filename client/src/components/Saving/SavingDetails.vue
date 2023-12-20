<script setup>
import MyAccount from "./Details/MyAccount.vue";
import MonthlyDetails from "./Details/MonthlyDetails.vue";
import Chart from "./Details/Chart.vue";

</script>

<template>
  <div class="w-full space-y-14">
    <!--    나의 적금-->
    <div class="my-10 text-2xl text-main-red">나의 적금</div>
    <my-account/>

    <!--    월별 내역 조회-->
    <div>
      <div class="mt-20 text-2xl text-main-red">월별 내역 조회</div>
      <monthly-details/>
    </div>

    <!--  대시보드 -->
    <div>
      <div class="w-full p-7 bg-gray-100 rounded-3xl shadow">
        <div class="p-6 border bg-white rounded-xl flex justify-center">
            <chart/>
        </div>
      </div>
    </div>

  </div>

</template>

<style scoped>

</style>