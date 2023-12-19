<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import dayjs from "dayjs";
import {useAuthStore} from "../../stores/auth";

const accountInfo = ref({});
const createDate = ref('');

// 현재 진행중인 계좌 정보 가져오기
const getAccountInProgress = async () => {
  await axios
      .get('/savings/progress',{
        headers: {
          Authorization: useAuthStore().token,
        },
      })
      .then((response) => {
        const data = response.data.inProgressAccount;
        accountInfo.value = data;
        createDate.value = dayjs(data.createdAt).format('YYYY년 MM월 DD일');
      })
      .catch((error) => {

      })
}

onMounted(async () => {
  await getAccountInProgress();
});

</script>


<template>
  <div class="flex justify-center my-20">
    <img class="w-1/4 object-center" src="/images/logo.svg" alt="">
  </div>

  <div class="flex justify-center items-center gap-7">
    <div>
      <div class="text-3xl my-2  font-light">축하합니다! '잔액오버플로우'의 적금이 성공적으로 시작되었습니다.</div>
      <div class="text-4xl my-2  font-medium">개발자로서의 성장과 재미있는 적금 생활을 즐겨보세요!</div>
    </div>
    <div>
      <img src="/images/congrats.svg" alt="">
    </div>
  </div>

  <div class="w-9/12 m-auto px-32 py-10 my-10 bg-gray-100 rounded-3xl shadow text-center">
    <div class="mt-5 text-center text-4xl text-main-red font-bold">{{  accountInfo.acntName}} <span class="text-black">적금</span></div>
    <div class="my-16 flex justify-between">
      <div class="text-3xl font-medium">목표</div>
      <div class="text-3xl text-gray-700 font-light">{{ accountInfo.goalName }}</div>
    </div>
    <div class="my-16 flex justify-between">
      <div class="text-3xl font-medium">목표 금액</div>
      <div class="text-3xl text-gray-700 font-light">{{ Number(accountInfo.goalAmount).toLocaleString() }}원</div>
    </div>
    <div class="my-16 flex justify-between">
      <div class="text-3xl font-medium">적금 계좌</div>
      <div class="text-3xl text-gray-700 font-light">농협 {{ accountInfo.acntNum }}</div>
    </div>
    <div class="my-16 mb-10 flex justify-between">
      <div class="text-3xl font-medium">가입일</div>
      <div class="text-3xl text-gray-700 font-light">{{ createDate }}</div>
    </div>
  </div>

  <div class="w-9/12 m-auto my-20 text-center  flex justify-between">
    <router-link to="/" class="bg-btn-grey hover:bg-gray-400 m-2 px-10 py-5 rounded-xl shadow" type="button">
      <div class="text-white text-4xl font-bold ">메인 이동</div>
    </router-link>
    <router-link to="/issue" class="bg-main-red hover:bg-hover-red m-2 px-10 py-5 rounded-xl shadow" type="button">
      <div class="text-white text-4xl font-bold ">에러 등록</div>
    </router-link>
  </div>
</template>

<style scoped>

</style>