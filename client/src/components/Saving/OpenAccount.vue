<script setup>
import ProgressBar from "@/components/Saving/component/ProgressBar.vue";
import {ref} from "vue";
import router from "../../router";
import axios from "axios";
import {useAuthStore} from "../../stores/auth";

let currentStep = ref(1); // 현재 단계
const nextStep = () => { // 다음 단계 이동
  if (currentStep.value < 3) {
    currentStep.value++;
    scrollToTop();
  }
}
const prevStep = () => { // 이전 단계 이동
  if (currentStep.value > 1) {
    currentStep.value--;
    scrollToTop();
  }
}
const scrollToTop = () => { // 페이지 상단으로 스크롤하는 함수
  window.scrollTo({
    top: 0,
    behavior: 'smooth',
  });
}

const accountInfo = ref({
  inputAccountRequestDTO: {
    acntName: "",
    goalName: "",
    goalAmount: 0,
  },
  ruleRequestDTO: {
    underThirty: 0,
    underHour: 0,
    underThreeHour: 0,
    overThreeHour: 0,
  },
});
const goOpenAccountFinish = async () => {
  await axios
      .post('/savings',
          {
            inputAccountRequestDTO: accountInfo.value.inputAccountRequestDTO,
            ruleRequestDTO: accountInfo.value.ruleRequestDTO,
          }, {
            headers: {
              Authorization: useAuthStore().token,
            }
          })
      .then(() => {
        router.push({
          path: "/open/finish",
        })
      })
      .catch(() => {
      })
}
</script>

<template>
  <div class="p-10">
    <div class="text-black mt-10 mb-2 text-4xl font-bold ">잔액오버플로우 시작하기</div>

    <!-- 1단계: 적금명 입력 -->
    <div v-show="currentStep === 1">
      <progress-bar :current-step="1" :total-steps="3"/>
      <div class="my-10">
        <div class="text-main-red text-3xl font-medium">적금명 입력</div>
        <div class="my-5">
          <div class="my-2 text-gray-500 text-xl font-light">적금에 부여할 이름을 입력해주세요.</div>
          <div class="my-2 text-gray-500 text-xl font-light"></div>
        </div>
      </div>

      <div class="px-14 py-8 my-10 bg-gray-100 rounded-3xl shadow">
        <div class="my-9">
          <div class="text-sub-red text-2xl font-medium">적금명</div>
          <input
              v-model="accountInfo.inputAccountRequestDTO.acntName"
              class="my-5 w-1/2 text-gray-700 text-2xl font-light  bg-transparent border-0 border-b-4 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-sub-red"
              name="acntName" placeholder="적금명을 입력해주세요."
              required
              type="text"
          />
          <div>
          </div>
        </div>
      </div>
    </div>

    <!-- 2단계: 목표 설정 -->
    <div v-show="currentStep === 2">
      <progress-bar :current-step="2" :total-steps="3"/>

      <div class="my-10">
        <div class="text-main-red text-3xl font-medium">목표 설정</div>
        <div class="my-5">
          <div class="my-2 text-gray-500 text-xl font-light">어떤 목표에 도전하고 싶나요?</div>
          <div class="my-2 text-gray-500 text-xl font-light">적금을 통해 달성하고 싶은 목표와 금액 입력해주세요.</div>
        </div>
      </div>

      <div class="px-14 py-8 my-10 bg-gray-100 rounded-3xl shadow">
        <div class="my-9">
          <div class="text-sub-red text-2xl font-medium ">목표명</div>
          <input
              v-model="accountInfo.inputAccountRequestDTO.goalName"
              class="my-5 w-full text-gray-700 text-2xl font-light bg-transparent border-0 border-b-4 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-sub-red"
              name="goalName" placeholder="목표명을 입력해주세요."
              required
              type="text"
          />
          <div>
          </div>
          <div class="my-9">
            <div class="text-sub-red text-2xl font-medium ">목표 금액</div>
            <input
                v-model="accountInfo.inputAccountRequestDTO.goalAmount"
                class="my-5 w-1/2 text-gray-700 text-2xl font-light bg-transparent border-0 border-b-4 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-sub-red"
                name="goalAmount" placeholder="목표 금액을 입력해주세요."
                required
                type="number"
            />
            <span class="text-gray-800 text-2xl font-light">원</span>
            <div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 3단계: 시간별 금액 설정 -->
    <div v-show="currentStep === 3">
      <progress-bar :current-step="3" :total-steps="3"/>
      <div class="my-10">
        <div class="text-main-red text-3xl font-medium ">시간별 금액 설정</div>
        <div class="my-5">
          <div class="my-2 text-gray-500 text-xl font-light">에러를 해결하는 데 소요되는 시간에 따라 자동으로 저금됩니다.</div>
        </div>
      </div>

      <div class="px-14 py-8 my-10 bg-gray-100 rounded-3xl shadow space-y-14">
        <div class="my-9 flex justify-center items-center">
          <div class="w-1/4 text-gray-800 text-2xl font-medium">30분 미만</div>
          <div>
            <input
                v-model="accountInfo.ruleRequestDTO.underThirty"
                class="mr-3 px-6 py-3 w-1/2 text-gray-700 text-2xl font-light bg-gray-200 border-0 focus:outline-none focus:ring-0 focus:border-sub-red"
                name="underThirty"
                type="text"
            />
            <span class="px-6 py-3 text-gray-800 text-2xl font-light">원</span>
          </div>
        </div>
        <div class="my-9 flex justify-center items-center">
          <div class="w-1/4 text-gray-800 text-2xl font-medium">1시간 미만</div>
          <div>
            <input
                v-model="accountInfo.ruleRequestDTO.underHour"
                class="mr-3 px-6 py-3 w-1/2 text-gray-700 text-2xl font-light bg-gray-200 border-0 focus:outline-none focus:ring-0 focus:border-sub-red"
                name="underHour"
                type="text"
            />
            <span class="px-6 py-3 text-gray-800 text-2xl font-light">원</span>
          </div>
        </div>
        <div class="my-9 flex justify-center items-center">
          <div class="w-1/4 text-gray-800 text-2xl font-medium">3시간 미만</div>
          <div>
            <input
                v-model="accountInfo.ruleRequestDTO.underThreeHour"
                class="mr-3 px-6 py-3 w-1/2 text-gray-700 text-2xl font-light bg-gray-200 border-0 focus:outline-none focus:ring-0 focus:border-sub-red"
                name="underThreeHour"
                type="text"
            />
            <span class="px-6 py-3 text-gray-800 text-2xl font-light">원</span>
          </div>
        </div>
        <div class="my-9 flex justify-center items-center">
          <div class="w-1/4 text-gray-800 text-2xl font-medium">3시간 이상</div>
          <div>
            <input
                v-model="accountInfo.ruleRequestDTO.overThreeHour"
                class="mr-3 px-6 py-3 w-1/2 text-gray-700 text-2xl font-light bg-gray-200 border-0 focus:outline-none focus:ring-0 focus:border-sub-red"
                name="overThreeHour"
                type="text"
            />
            <span class="px-6 py-3 text-gray-800 text-2xl font-light">원</span>
          </div>
        </div>
      </div>
    </div>


    <div class="text-right my-20 flex justify-between flex-row-reverse">
      <button v-if="currentStep < 3" class="bg-main-red hover:bg-hover-red m-2 px-10 py-5 rounded-xl shadow"
              type="button"
              @click="nextStep">
        <div class="text-white text-2xl font-bold ">다음</div>
      </button>
      <button v-if="currentStep === 3" class="bg-main-grn hover:bg-hover-grn m-2 px-10 py-5 rounded-xl shadow"
              type="button" @click="goOpenAccountFinish()">
        <div class="text-white text-2xl font-bold ">등록</div>
      </button>
      <button v-if="currentStep > 1" class="bg-main-red hover:bg-hover-red m-2 px-10 py-5 rounded-xl shadow"
              type="button" @click="prevStep">
        <div class="text-white text-2xl font-bold ">이전</div>
      </button>
    </div>
  </div>

</template>

<style scoped>

</style>