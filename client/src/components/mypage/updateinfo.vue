<template>
  <h3 class="text-3xl font-bold text-main-red mb-7">회원 정보 수정하기</h3>
  <div class="border-b-4 border-main-red"></div>
  <article class="">
    <form class="max-w-md mx-auto my-10" @submit.prevent="updateInfo">
      <h4 class="my-7 text-xl">기본정보 수정</h4>

      <div class="relative z-0 w-full mb-5 group">
        <input type="text" name="nickname" id="floating_nickname"
          class="block py-2.5 px-0 w-full text-m text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
          placeholder=" " required v-model="userInfo.nickname" />
        <label for="floating_nickname"
          class="peer-focus:font-medium absolute text-lg text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 rtl:peer-focus:left-auto peer-focus:border-main-red peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">닉네임
          변경</label>
      </div>
      <div class="relative z-0 w-full mb-5 group">
        <input type="text" name="name" id="floating_name"
          class="block py-2.5 px-0 w-full text-m text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
          placeholder=" " required v-model="userInfo.name" />
        <label for="floating_name"
          class="peer-focus:font-medium absolute text-lg text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-red peer-focus:dark:border-main-red peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">이름
          변경</label>
      </div>
      <div class="relative z-50 w-full mb-5 group">
        <DatePicker name="birth" id="floating_birth"
          class="block py-2.5 px-0 w-full text-m focus:ouline-none focus:borer-rose-600 focus:ring-0 dark:text-white dark:border-gray-600 dark:focus:border-main-red placeholder-transparent h-10 bg-transparent border-t-0 border-l-0 border-r-0 border-b-2 border-red-700"
          v-model="birthDate" :locale="locale" :is-inline="true" @update:model-value="updateBirth" />
        <label for="floating_birth"
          class="peer-focus:font-medium absolute text-lg text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-red peer-focus:dark:border-main-red peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">생년월일</label>
      </div>
      <div class="relative z-0 w-full mb-5 group">
        <input type="text" name="digit" id="floating_digit" @input="formatPhone"
          class="block py-2.5 px-0 w-full text-m text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
          placeholder=" " required v-model="userInfo.digit" />
        <label for="floating_digit"
          class="peer-focus:font-medium absolute text-lg text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-red peer-focus:dark:border-main-red peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">전화번호</label>
      </div>
      <div class="relative z-0 w-full mb-5 group">
        <input type="password" name="password" id="floating_password"
          class="block py-2.5 px-0 w-full text-m text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
          placeholder=" " required v-model="inputPassword" />
        <label for="floating_password"
          class="peer-focus:font-medium absolute text-m text-main-red dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-red peer-focus:dark:border-main-red peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">수정확인을
          위해 패스워드를 입력해주세요</label>
        <p class="text-red-700" :class="{ 'hidden': passwordOk }">
          패스워드가 일치하지 않습니다.
        </p>
      </div>

      <h4 class="mt-8 mb-3 text-xl">패스워드 변경</h4>
      <div class="grid md:grid-cols-2 md:gap-6">
        <div class="relative z-0 w-full mb-5 group">
          <input type="password" name="updatePassword" id="floating_updatePassword"
            class="block py-2.5 px-0 w-full text-m text-gray-900 bg-transparent border-0 border-b-2 border-sub-grn appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-grn focus:outline-none focus:ring-0 focus:border-main-grn peer"
            placeholder=" " v-model="inputUpdatePassword" />
          <label for="floating_updatePassword"
            class="peer-focus:font-medium absolute text-lg text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-grn peer-focus:dark:border-main-grn peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">패스워드
            수정</label>
        </div>
        <div class="relative z-0 w-full mb-5 group">
          <input type="password" name="updatePasswordConfirm" id="floating_updatePasswordConfirm"
            class="block py-2.5 px-0 w-full text-m text-gray-900 bg-transparent border-0 border-b-2 border-sub-grn appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-grn focus:outline-none focus:ring-0 focus:border-main-grn peer"
            placeholder=" " v-model="inputUpdatePasswordConfirm" />
          <label for="floating_updatePasswordConfirm"
            class="peer-focus:font-medium absolute text-lg text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-grn peer-focus:dark:border-main-grn peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">패스워드
            수정 확인</label>
          <p class="text-red-700" :class="{ 'hidden': confirmOk }">
            수정값과 확인값이 다릅니다.
          </p>
        </div>
      </div>

      <h4 class="mt-8 mb-3 text-lg">계좌관련 수정</h4>
      <div class="grid md:grid-cols-2 md:gap-6">
        <div class="relative z-0 w-full mb-5 group">
          <input type="text" name="holder" id="floating_holder"
            class="block py-2.5 px-0 w-full text-m text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
            placeholder=" " v-model="userInfo.holder" />
          <label for="floating_holder"
            class="peer-focus:font-medium absolute text-lg text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-red peer-focus:dark:border-main-red peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">입금주</label>
        </div>
        <div class="relative z-0 w-full mb-5 group">
          <input type="text" name="bankName" id="floating_bankName"
            class="block py-2.5 px-0 w-full text-m text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
            placeholder=" " v-model="userInfo.bankName" />
          <label for="floating_bankName"
            class="peer-focus:font-medium absolute text-m text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-red peer-focus:dark:border-main-red peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">거래은행</label>
        </div>
      </div>
      <div class="grid md:grid-cols-2 md:gap-6">
        <div class="relative z-0 w-full mb-5 group">
          <input type="text" name="outputAcntNum" id="floating_outputAcntNum"
            class="block py-2.5 px-0 w-full text-m text-gray-900 bg-transparent border-0 border-b-2 border-sub-red appearance-none dark:text-white dark:border-gray-600 dark:focus:border-main-red focus:outline-none focus:ring-0 focus:border-main-red peer"
            placeholder=" " required v-model="userInfo.outputAcntNum" />
          <label for="floating_outputAcntNum"
            class="peer-focus:font-medium absolute text-m text-gray-700 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:border-main-red peer-focus:dark:border-main-red peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">계좌번호</label>
        </div>
      </div>
      <div class="flex justify-end">
        <button type="submit"
          class="text-white bg-main-red hover:bg-hover-red focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-m w-full sm:w-auto px-5 py-2.5 text-center dark:bg-main-red dark:hover:bg-hover-red dark:focus:ring-blue-800">
          수정하기
        </button>
      </div>
    </form>
  </article>
</template>

<script setup>
import { useAuthStore } from "@/stores/auth";
import { ref, reactive } from "vue";
import axios from "axios";
import { ko } from 'date-fns/locale';
import DatePicker from 'vue3-datepicker';

const authStore = useAuthStore();
const userInfo = ref({ ...authStore.userInfo });
const inputPassword = ref("");
const inputUpdatePassword = ref("");
const inputUpdatePasswordConfirm = ref("");
const passwordOk = ref('');
const confirmOk = ref('');
passwordOk.value = true;
confirmOk.value = true;
const locale = reactive(ko); //한글 달력

const birthDate = ref(new Date(userInfo._rawValue.birth));

function updateBirth(selectedDate) {
  birthDate.value = new Date(selectedDate);
};

console.log(userInfo);

const formatPhone = () => {
  let input = userInfo.value.digit.replace(/[^0-9]/g, '');

  if (input.length <= 3) {
    userInfo.value.digit = input;
  } else if (input.length <= 7) {
    userInfo.value.digit = `${input.slice(0, 3)}-${input.slice(3)}`;
  } else {
    userInfo.value.digit = `${input.slice(0, 3)}-${input.slice(3, 7)}-${input.slice(7)}`;
  }
};

// ----------------------------
// 회원정보 수정하기
// ----------------------------

const exPhone = /^[0-9]{3}\-([0-9]{3}|[0-9]{4})\-([0-9]{3}|[0-9]{4})/;
const exPass = /^[A-Za-z0-9_\.\-`~!@#\$%^&*\|\\?;:+=\[\]<>\(\),_'"-]{9,}/;

async function updateInfo() {

  try {
    let updateInfo = reactive({
      name: userInfo.value.name,
      password: inputPassword.value,
      digit: userInfo.value.digit,
      birth: birthDate.value.toISOString().split('T')[0], // 날짜를 YYYY-MM-DD 형식으로 포맷
      nickname: userInfo.value.nickname,
      holder: userInfo.value.holder,
      bankName: userInfo.value.bankName,
      outputAcntNum: userInfo.value.outputAcntNum,
      newPassword: inputUpdatePassword.value,
      newPasswordConfirm: inputUpdatePasswordConfirm.value,
    });

    //axios 실행하기 전에 체크

    if(inputUpdatePassword.value === ''){
      
    } else if(exPass.test(inputUpdatePassword.value) === false) {
          alert('보안을 위해 패스워드는 최소 9자 이상 작성해주세요');
          return;
    }

    if (exPhone.test(userInfo.value.digit) === false) {
      alert('전화번호 형식이 올바르지 않습니다.\n하이픈(-) 포함');
      return;
    }

    const response = await axios.put("/mypage/myinfo", updateInfo, {
      headers: {
        "content-type": "application/json",
        authorization: authStore.token,
      },
    });

    authStore.getUserInfo();
    if (response.data == "ok") {
      alert("수정완료");
      inputPassword.value = "";
      inputUpdatePassword.value = "";
      inputUpdatePasswordConfirm.value = "";
      passwordOk.value = true;
      confirmOk.value = true;
    } else if (response.data == "passwordError") {
      passwordOk.value = false;
    } else {
      confirmOk.value = false;
    }
  } catch (error) {
    console.error("에러:", error);
    alert(error.response.data, "에러");
  }
}

</script>
<style scoped></style>
