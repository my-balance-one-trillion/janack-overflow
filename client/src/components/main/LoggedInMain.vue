<template>
  <div
    v-if="Object.keys(nowAccount).length > 0"
    class="px-12 flex flex-col rounded-[25px] border border-grey border-solid my-10 h-[610px] w-9/12 mx-auto justify-between relative shadow-md"
    style="background-image: linear-gradient(to bottom, white 50%, #bf1131 50%)"
  >
    <div class="flex-col py-3 text-sm min-w-fit">
      <div
        class="flex rounded-[20px] w-fit px-8 h-10 bg-sub-red text-white justify-center items-center my-1"
      >
        DAY {{ nowAccount.dayCnt }}
      </div>
      <div
        class="flex rounded-[20px] w-fit px-7 h-10 bg-sub-red text-white justify-center items-center"
      >
        TOTAL {{ nowAccount.savingCnt }}
      </div>
    </div>
    <span style="font-size: 51px">{{ nowAccount.acntName }}</span>
    <div
      class="flex flex-col justify-evenly h-1/2 min-w-fit w-7/12 mx-0 rounded-b-[25px]"
    >
      <div class="text-white font-sub" style="font-size: 38px">잔액</div>
      <div class="text-white" style="font-size: 41px">
        {{ nowAccount.acntAmount.toLocaleString() }}원
      </div>
      <div class="text-white font-sub" style="font-size: 38px">
        농협 {{ nowAccount.acntNum }}
      </div>
    </div>

    <div
      class="sm:hidden hidden md:flex flex-col absolute border bg-white rounded-[25px] w-4/12 my-24 right-6 items-center px-1 py-1"
    >
      <div class="flex w-11/12">
        <span style="font-size: 28px">목표</span>
      </div>
      <div class="text-center" style="font-size: 25px">
        {{ nowAccount.goalName }}
      </div>
      <Chart
        v-if="Object.keys(nowAccount).length > 0"
        :goalAmount="nowAccount.goalAmount"
        :acntAmount="nowAccount.acntAmount"
      />
      <div class="text-center" style="font-size: 21px">
        {{ nowAccount.goalAmount.toLocaleString() }}원
      </div>
      <div
        class="absolute pt-2 top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-0.5 text-3xl text-main-grn font-bold"
      >
        {{ ((nowAccount.acntAmount / nowAccount.goalAmount) * 100).toFixed(0) }}
        <span class="text-xs text-black">%</span>
      </div>
    </div>
  </div>
  <!-- 적금 없을시 -->
  <div
    v-if="nowAccount.status == '03' || Object.keys(nowAccount).length == 0"
    class="flex flex-col justify-center content-center boerder border-black border-solid rounded-[25px] w-9/12 mx-auto"
  >
    <div class="my-10 text-5xl">
      현재 사용중인 적금이 없습니다.<br />
      생성하러 가실까요?
    </div>
    <router-link
      to="/intro"
      type="button"
      class="px-10 py-5 m-2 mx-auto shadow bg-main-red hover:bg-hover-red rounded-xl w-fit"
    >
      <div class="text-4xl font-bold text-white">적금 생성하기</div>
    </router-link>
  </div>
  <div class="flex justify-end w-9/12 mx-auto">
    <button class="rounded-[25px] bg-main-red hover:bg-hover-red text-white w-fit h-fit px-5 py-3"
      style="font-size: 21px">
      <router-link to="/issue">에러 등록</router-link>

    </button>
  </div>
  <div class="border rounded-[25px] w-9/12 mx-auto my-10 p-4">
    <div class="flex justify-between mx-4">
      <span style="font-size: 38px"
        >나의 최근 <span class="text-main-red">에러</span></span
      >
      <router-link :to="'/mypage/myissue'">
        <span
          class="underline text-main-red"
          style="font-size: 21px; line-height: 200%"
          >내역 조회</span
        >
      </router-link>
    </div>
    <IssueTable :issueList="weeklyIssues" />
  </div>
</template>
<script setup>
import { VueElement, onMounted, ref } from "vue";
import axios from "axios";
import IssueTable from "@/components/main/IssueTable.vue";
import "vue3-circle-progress/dist/circle-progress.css";
import CircleProgress from "vue3-circle-progress";
import Chart from "@/components/Chart.vue";
import { useAuthStore } from "../../stores/auth";
import { useRouter } from "vue-router";

const router = useRouter();
//const nowAccount = ref({});
//const weeklyIssues = ref([]);
defineProps({
  nowAccount: {
    type: Object,
  },
  weeklyIssues: {
    type: Array,
  },
});
</script>
<style></style>
