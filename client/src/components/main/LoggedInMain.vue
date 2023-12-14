<template>
  <div class="px-4 flex flex-col rounded-[25px] border border-grey border-solid my-10 h-[610px] w-9/12 mx-auto justify-between relative shadow-md"
  style="background-image: linear-gradient(to bottom, white 50%, #BF1131 50%);">
    <div class="flex-col w-40 py-3">
      <div class="flex rounded-[20px] w-fit px-8 h-10 bg-sub-red text-white justify-center items-center my-1">DAY {{ nowAccount.dayCnt }}</div>
      <div class="flex rounded-[20px] w-fit px-7 h-10 bg-sub-red text-white justify-center items-center">TOTAL {{ nowAccount.savingCnt }}</div>
  </div>
    <span style="font-size: 64px;">{{ nowAccount.acntName }}</span>
    <div class="flex flex-col justify-evenly h-1/2 w-7/12 mx-0 rounded-b-[25px]">
      <div class="text-white font-sub" style="font-size: 51px;">잔액</div>
      <div class="text-white" style="font-size: 64px;">{{ acntAmount }}원</div>
    <div class="text-white font-sub" style="font-size: 38px;">농협 {{ nowAccount.acntNum }}</div>
    </div>

    <div class="flex flex-col absolute border bg-white rounded-[25px] w-4/12 my-24 right-6 items-center">
      <div class="text-left" style="font-size: 31px;">목표</div>
      <div class="text-center" style="font-size: 31px;">{{ nowAccount.goalName }}</div>
      <Chart
      v-if="Object.keys(nowAccount).length>0"
      :goalAmount="nowAccount.goalAmount" 
      :acntAmount="nowAccount.acntAmount"/>
      <div class="text-center" style="font-size: 21px;">{{ goalAmount }}원</div>
    </div>
  </div>
  <div class="w-9/12 mx-auto flex justify-end">
  <button class="rounded-[25px] bg-main-red text-white w-[180px] h-[70px]" style="font-size: 31px;">이슈 등록</button>
</div>
  <div class="border rounded-[25px] w-9/12 mx-auto my-10 p-4">
    <div class="flex justify-between mx-4">
    <span style="font-size: 51px;">나의 최근 <span class="text-main-red">에러</span></span>
    <span class="text-main-red underline" style="font-size: 31px ; line-height: 300%;">내역 조회</span>
  </div>
     <IssueTable v-if="weeklyIssues.length>0"
     :issueList="weeklyIssues" 
     />
  </div>
</template>
<script setup>
import { onMounted, ref } from 'vue';
import axios from 'axios';
import IssueTable from "./IssueTable.vue";
import "vue3-circle-progress/dist/circle-progress.css";
import CircleProgress from "vue3-circle-progress";
import Chart from '../Chart.vue';

const nowAccount = ref({});
const weeklyIssues = ref([]);
var goalAmount;
var acntAmount;
onMounted(()=>{
  axios.get("http://localhost:8081/main/1")
  .then((response) => {
    nowAccount.value = response.data.nowAccount;
    weeklyIssues.value = response.data.weeklyIssues;
    console.log(nowAccount.value);
    goalAmount = nowAccount.value.goalAmount.toLocaleString();
    acntAmount = nowAccount.value.acntAmount.toLocaleString();
  })
})
</script>
<style></style>