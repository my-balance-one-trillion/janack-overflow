<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";
import dayjs from "dayjs";
import {useAuthStore} from "@/stores/auth";

const accountInfo = ref({
  inputAccountRequestDTO: {},
  ruleRequestDTO: {},
});

onMounted(async () => {
  try {
    const response = await axios.get('/savings/progress', {
      headers: {
        Authorization: useAuthStore().token,
      }
    });
    const accountdata = response.data.inProgressAccount;
    accountInfo.value.inputAccountRequestDTO = accountdata;
    // 규칙 정보
    const ruledata = response.data.inProgressRule;
    accountInfo.value.ruleRequestDTO = ruledata;
  } catch (error) {
    console.error(error);
  }
});

const editAccount = async () => {
  try {
    const response = await axios.put('/savings',
        {
          inputAccountRequestDTO: accountInfo.value.inputAccountRequestDTO,
          ruleRequestDTO: accountInfo.value.ruleRequestDTO,
        }, {
          headers: {
            Authorization: useAuthStore().token,
          }
        });
    console.log(response);
  } catch (error) {
    console.log(error);
  }
}
</script>

<template>
  <div class="w-full space-y-10">
    <!-- 적금명 입력 -->
    <div class="my-10">
      <div class="text-3xl text-main-red">적금명</div>
      <div class="my-5">
        <div class="my-2 text-gray-500 text-xl font-light">현재 적금명이 마음에 안 들거나 변경하고 싶다면, 새로운 이름을 15자 이내로 작성해주세요.</div>
      </div>
      <div class="px-14 py-8 bg-gray-100 rounded-3xl shadow">
        <input
            v-model="accountInfo.inputAccountRequestDTO.acntName"
            class="w-1/2 text-gray-700 text-2xl font-light bg-transparent border-0 border-b-4 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-red-400"
            required type="text"/>
      </div>
    </div>

    <!-- 목표 -->
    <div class="my-10">
      <div class="text-3xl text-main-red">목표</div>
      <div class="my-5">
        <div class="my-2 text-gray-500 text-xl font-light">원하는 목표에 도달하기 위해 목표명과 목표 금액을 자유롭게 수정하세요.</div>
      </div>
      <div class="px-14 py-7 bg-gray-100 rounded-3xl shadow">
        <div class="my-5">
          <div class="text-red-500 text-2xl font-medium ">목표명</div>
          <input
              v-model="accountInfo.inputAccountRequestDTO.goalName"
              class="my-5 w-full text-gray-700 text-2xl font-light bg-transparent border-0 border-b-4 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-red-400"
              required type="text"/>
          <div>
          </div>
          <div class="my-5">
            <div class="text-red-500 text-2xl font-medium ">목표 금액</div>
            <input
                v-model="accountInfo.inputAccountRequestDTO.goalAmount"
                class="my-5 w-1/2 text-gray-700 text-2xl font-light bg-transparent border-0 border-b-4 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-red-400"
                required type="number"/>
            <span class="text-gray-800 text-3xl font-light">원</span>
            <div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 시간별 금액 설정 -->
    <div class="my-10">
      <div class="text-3xl text-main-red">시간별 금액 설정</div>
      <div class="my-5">
        <div class="my-2 text-gray-500 text-xl font-light">목표 달성을 위한 시간별 금액을 수정하여 목표에 더 가깝게 다가갈 수 있도록 해보세요.</div>
      </div>
      <div class="px-14 py-8 bg-gray-100 rounded-3xl shadow space-y-10">
        <div class="flex justify-center items-center">
          <div class="w-1/4 text-gray-800 text-2xl font-medium">30분 미만</div>
          <div>
            <input
                v-model="accountInfo.ruleRequestDTO.underThirty"
                class="mr-3 px-6 py-3 w-1/2 text-gray-700 text-2xl font-light bg-gray-200 border-0 focus:outline-none focus:ring-0 focus:border-red-400"
                type="text"/>
            <span class="px-6 py-3 text-gray-800 text-2xl font-light">원</span>
          </div>
        </div>
        <div class="flex justify-center items-center">
          <div class="w-1/4 text-gray-800 text-2xl font-medium">1시간 미만</div>
          <div>
            <input
                v-model="accountInfo.ruleRequestDTO.underHour"
                class="mr-3 px-6 py-3 w-1/2 text-gray-700 text-2xl font-light bg-gray-200 border-0 focus:outline-none focus:ring-0 focus:border-red-400"
                type="text"/>
            <span class="px-6 py-3 text-gray-800 text-2xl font-light">원</span>
          </div>
        </div>
        <div class="flex justify-center items-center">
          <div class="w-1/4 text-gray-800 text-2xl font-medium">3시간 미만</div>
          <div>
            <input
                v-model="accountInfo.ruleRequestDTO.underThreeHour"
                class="mr-3 px-6 py-3 w-1/2 text-gray-700 text-2xl font-light bg-gray-200 border-0 focus:outline-none focus:ring-0 focus:border-red-400"
                type="text"/>
            <span class="px-6 py-3 text-gray-800 text-2xl font-light">원</span>
          </div>
        </div>
        <div class="flex justify-center items-center">
          <div class="w-1/4 text-gray-800 text-2xl font-medium">3시간 이상</div>
          <div>
            <input
                v-model="accountInfo.ruleRequestDTO.overThreeHour"
                class="mr-3 px-6 py-3 w-1/2 text-gray-700 text-2xl font-light bg-gray-200 border-0 focus:outline-none focus:ring-0 focus:border-red-400"
                type="text"/>
            <span class="px-6 py-3 text-gray-800 text-2xl font-light">원</span>
          </div>
        </div>
      </div>
    </div>
    <div class="text-center my-20">
      <button class="bg-main-red hover:bg-hover-red m-2 px-10 py-5 rounded-xl " type="button" @click="editAccount()">
        <div class="text-white text-3xl font-bold ">적금 수정</div>
      </button>
    </div>
  </div>


</template>

<style scoped>

</style>