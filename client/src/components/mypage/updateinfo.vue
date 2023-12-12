<template>
  <h3 class="text-3xl text-main-red font-bold mb-7">회원 정보 수정하기</h3>
  <div class="border-b-4 border-main-red"></div>
  <article class="">
    <form class="max-w-md mx-auto my-10">
      <h4 class="mt-8 mb-3 text-lg">기본정보 수정</h4>

      <div class="relative z-0 w-full mb-5 group">
        <input
          type="email"
          name="floating_email"
          id="floating_email"
          class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
          placeholder=" "
          required
        />
        <label
          for="floating_email"
          class="peer-focus:font-medium absolute text-m text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 rtl:peer-focus:left-auto peer-focus:border-main-red peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
          >닉네임 변경</label
        >
      </div>
      <div class="relative z-0 w-full mb-5 group">
        <input
          type="password"
          name="floating_password"
          id="floating_password"
          class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
          placeholder=" "
          required
        />
        <label
          for="floating_password"
          class="peer-focus:font-medium absolute text-m text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-red peer-focus:dark:border-main-red peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
          >이름 변경</label
        >
      </div>
      <div class="relative z-0 w-full mb-5 group">
        <input
          type="password"
          name="repeat_password"
          id="floating_repeat_password"
          class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
          placeholder=" "
          required
        />
        <label
          for="floating_repeat_password"
          class="peer-focus:font-medium absolute text-m text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-red peer-focus:dark:border-main-red peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
          >생년월일</label
        >
      </div>
      <div class="relative z-0 w-full mb-5 group">
        <input
          type="password"
          name="repeat_password"
          id="floating_repeat_password"
          class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
          placeholder=" "
          required
        />
        <label
          for="floating_repeat_password"
          class="peer-focus:font-medium absolute text-m text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-red peer-focus:dark:border-main-red peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
          >전화번호</label
        >
      </div>

      <div class="grid md:grid-cols-2 md:gap-6">
        <div class="relative z-0 w-full mb-5 group">
          <input
            type="tel"
            pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}"
            name="floating_phone"
            id="floating_phone"
            class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
            placeholder=" "
            required
          />
          <label
            for="floating_phone"
            class="peer-focus:font-medium absolute text-m text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-red peer-focus:dark:border-main-red peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
            >패스워드</label
          >
        </div>
        <div class="relative z-0 w-full mb-5 group">
          <input
            type="tel"
            pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}"
            name="floating_phone"
            id="floating_phone"
            class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
            placeholder=" "
            required
          />
          <label
            for="floating_phone"
            class="peer-focus:font-medium absolute text-m text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-red peer-focus:dark:border-main-red peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
            >패스워드 수정</label
          >
        </div>
      </div>

      <h4 class="mt-8 mb-3 text-lg">계좌관련 수정</h4>
      <div class="grid md:grid-cols-2 md:gap-6">
        <div class="relative z-0 w-full mb-5 group">
          <input
            type="text"
            name="floating_first_name"
            id="floating_first_name"
            class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
            placeholder=" "
            required
          />
          <label
            for="floating_first_name"
            class="peer-focus:font-medium absolute text-m text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-red peer-focus:dark:border-main-red peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
            >입금주</label
          >
        </div>
        <div class="relative z-0 w-full mb-5 group">
          <input
            type="text"
            name="floating_last_name"
            id="floating_last_name"
            class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
            placeholder=" "
            required
          />
          <label
            for="floating_last_name"
            class="peer-focus:font-medium absolute text-m text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-red peer-focus:dark:border-main-red peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6"
            >거래은행</label
          >
        </div>
      </div>
      <div class="grid md:grid-cols-2 md:gap-6">
        <div class="relative z-0 w-full mb-5 group">
          <input
            type="tel"
            pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}"
            name="floating_phone"
            id="floating_phone"
            class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
            placeholder=" "
            required
          />
          <label
            for="floating_phone"
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
import axios from 'axios';
const token = localStorage.getItem('token');
console.log(token);
  async function readInfo(){
    const response = await axios.get('/mypage/myinfo', {
      headers: {
      'authorization': token,
    },
    });
    const { data } = response.data;
    console.log(data);
  }

  readInfo();

</script>
<style scoped></style>
