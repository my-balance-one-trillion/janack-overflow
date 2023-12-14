<template>
  <h3 class="text-3xl text-main-red font-bold mb-7">회원 정보 수정하기</h3>
  <div class="border-b-4 border-main-red"></div>
  <article class="">
    <form class="max-w-md mx-auto my-10" @submit.prevent="updateInfo">
      <h4 class="mt-8 mb-3 text-lg">기본정보 수정</h4>

      <div class="relative z-0 w-full mb-5 group">
        <input
          type="text"
          name="nickname"
          id="floating_nickname"
          class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
          placeholder=" "
          required
          v-model="userInfo.nickname"
        />
        <label
          for="floating_nickname"
          class="peer-focus:font-medium absolute text-m text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 rtl:peer-focus:left-auto peer-focus:border-main-red peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
          >닉네임 변경</label
        >
      </div>
      <div class="relative z-0 w-full mb-5 group">
        <input
          type="text"
          name="name"
          id="floating_name"
          class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
          placeholder=" "
          required
          v-model="userInfo.name"
        />
        <label
          for="floating_name"
          class="peer-focus:font-medium absolute text-m text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-red peer-focus:dark:border-main-red peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
          >이름 변경</label
        >
      </div>
      <div class="relative z-0 w-full mb-5 group">
        <input
          type="text"
          name="birth"
          id="floating_birth"
          class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
          placeholder=" "
          required
          v-model="userInfo.birth"
        />
        <label
          for="floating_birth"
          class="peer-focus:font-medium absolute text-m text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-red peer-focus:dark:border-main-red peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
          >생년월일</label
        >
      </div>
      <div class="relative z-0 w-full mb-5 group">
        <input
          type="text"
          name="digit"
          id="floating_digit"
          class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
          placeholder=" "
          required
          v-model="userInfo.digit"
        />
        <label
          for="floating_digit"
          class="peer-focus:font-medium absolute text-m text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-red peer-focus:dark:border-main-red peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
          >전화번호</label
        >
      </div>
      <div class="relative z-0 w-full mb-5 group">
        <input
          type="password"
          name="password"
          id="floating_password"
          class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
          placeholder=" "
          required
          v-model="inputPassword"
        />
        <label
          for="floating_password"
          class="peer-focus:font-medium absolute text-m text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-red peer-focus:dark:border-main-red peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
          >수정확인을 위해 패스워드를 입력해주세요</label
        >
      </div>

      <h4 class="mt-8 mb-3 text-lg">패스워드 변경</h4>
      <div class="grid md:grid-cols-2 md:gap-6">
        <div class="relative z-0 w-full mb-5 group">
          <input
            type="password"
            name="updatePassword"
            id="floating_updatePassword"
            class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-sub-grn appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-grn focus:outline-none focus:ring-0 focus:border-main-grn peer"
            placeholder=" "
            v-model="inputUpdatePassword"
          />
          <label
            for="floating_updatePassword"
            class="peer-focus:font-medium absolute text-m text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-grn peer-focus:dark:border-main-grn peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
            >패스워드 수정</label
          >
        </div>
        <div class="relative z-0 w-full mb-5 group">
          <input
            type="password"
            name="updatePasswordConfirm"
            id="floating_updatePasswordConfirm"
            class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-sub-grn appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-grn focus:outline-none focus:ring-0 focus:border-main-grn peer"
            placeholder=" "
            v-model="inputUpdatePasswordConfirm"
          />
          <label
            for="floating_updatePasswordConfirm"
            class="peer-focus:font-medium absolute text-m text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-grn peer-focus:dark:border-main-grn peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
            >패스워드 수정 확인</label
          >
        </div>
      </div>

      <h4 class="mt-8 mb-3 text-lg">계좌관련 수정</h4>
      <div class="grid md:grid-cols-2 md:gap-6">
        <div class="relative z-0 w-full mb-5 group">
          <input
            type="text"
            name="holder"
            id="floating_holder"
            class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
            placeholder=" "
            v-model="userInfo.holder"
          />
          <label
            for="floating_holder"
            class="peer-focus:font-medium absolute text-m text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-red peer-focus:dark:border-main-red peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
            >입금주</label
          >
        </div>
        <div class="relative z-0 w-full mb-5 group">
          <input
            type="text"
            name="bankName"
            id="floating_bankName"
            class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
            placeholder=" "
            v-model="userInfo.bankName"
          />
          <label
            for="floating_bankName"
            class="peer-focus:font-medium absolute text-m text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-red peer-focus:dark:border-main-red peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
            >거래은행</label
          >
        </div>
      </div>
      <div class="grid md:grid-cols-2 md:gap-6">
        <div class="relative z-0 w-full mb-5 group">
          <input
            type="text"
            name="outputAcntNum"
            id="floating_outputAcntNum"
            class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
            placeholder=" "
            required
            v-model="userInfo.outputAcntNum"

          />
          <label
            for="floating_outputAcntNum"
            class="peer-focus:font-medium absolute text-m text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-red peer-focus:dark:border-main-red peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
            >계좌번호</label
          >
        </div>
      </div>
      <div class="flex justify-end">
      <button
        type="submit"
        class="text-white bg-main-red hover:bg-hover-red focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-main-red dark:hover:bg-hover-red dark:focus:ring-blue-800"
      >
        Submit
      </button>
    </div> 
    </form>
  </article>
</template>

<script setup>
import { useAuthStore } from '@/stores/auth'
import { ref } from 'vue';
import axios from 'axios';

const authStore = useAuthStore();
const userInfo = authStore.userInfo;
const inputPassword = ref("");
const inputUpdatePassword = ref("");
const inputUpdatePasswordConfirm = ref("");

// ----------------------------
// 기존 정보 불러오기
// ----------------------------


// ----------------------------
// 회원정보 수정하기
// ----------------------------

async function updateInfo(e){
  let updateInfo = {
    "name": userInfo.name,
    "password": inputPassword.value,
    "digit": userInfo.digit,
    "birth": userInfo.birth,
    "nickname": userInfo.nickname,
    "holder": userInfo.holder,
    "bankName": userInfo.bankName,
    "outputAcntNum": userInfo.outputAcntNum
  };
  await axios.put('/community/myinfo/16',updateInfo,{
    headers:{
      'content-type':'application/json',
      'authorization': authStore.token
    }
  });
}

</script>
<style scoped></style>
