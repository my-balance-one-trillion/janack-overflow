<template >
  <div v-if="Object.keys(nowAccount).length> 0" class="px-4 flex flex-col rounded-[25px] border border-grey border-solid my-10 h-[610px] w-9/12 mx-auto justify-between relative shadow-md"
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
  <!-- 적금 없을시 -->
  <div v-if="nowAccount.status == '03' || Object.keys(nowAccount).length==0" class="flex flex-col justify-center content-center boerder border-black border-solid rounded-[25px]  w-9/12 mx-auto">
    <div class="text-5xl my-10">
      현재 사용중인 적금이 없습니다.<br>
      생성하러 가실까요?
    </div>
    <router-link to="/open" type="button" class="mx-auto bg-main-red hover:bg-hover-red m-2 px-10 py-5 rounded-xl shadow w-fit">
      <div class="text-white text-4xl font-bold ">적금 생성하기</div>
    </router-link>
  </div>
  <div v-else class="w-9/12 mx-auto flex justify-end">
    <button class="rounded-[25px] bg-main-red text-white w-[180px] h-[70px]" style="font-size: 31px;"><router-link to="/issue">이슈 등록</router-link></button>
</div>
  <div v-if="weeklyIssues.length >0" class="border rounded-[25px] w-9/12 mx-auto my-10 p-4">
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
import IssueTable from "@/components/main/IssueTable.vue";
import "vue3-circle-progress/dist/circle-progress.css";
import CircleProgress from "vue3-circle-progress";
import Chart from '@/components/Chart.vue';
import { useAuthStore } from '../../stores/auth';
import { useRouter } from 'vue-router';

const noContent = ref(true);
const mounted = ref(false);
const router = useRouter();
const nowAccount = ref({});
const weeklyIssues = ref([]);
var goalAmount;
var acntAmount;
onMounted(async()=>{
  await axios.get("/main/login", {
      headers: {
        authorization: useAuthStore().token,
      },
    })
  .then((response) => {
    console.log("상태", response.status);
    if(response.data.nowAccount != undefined){
      nowAccount.value = response.data.nowAccount;
      console.log("계좌 정보", response.data.nowAccount);
      // alert("계좌가 없습니다. 생성해주세요")
      // router.push("/saving");
      console.log("이슈 정보", response.data.weeklyIssues.length);
    } 
    if (response.data.weeklyIssues){
    weeklyIssues.value = response.data.weeklyIssues;
    console.log("값 넣기", weeklyIssues.value);
    goalAmount = nowAccount.value.goalAmount.toLocaleString();
    acntAmount = nowAccount.value.acntAmount.toLocaleString();
    }
    console.log("통신 후 값", nowAccount.value, weeklyIssues.value);
    console.log(Object.keys(nowAccount.value).length);
  })
  // .catch((error)  => {
  //   console.error("에러 발생", error.response);
      
  // }
  // )

  mounted.value = true;
})
</script>
<style></style>