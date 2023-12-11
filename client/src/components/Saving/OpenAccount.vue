<script setup>
import ProgressBar from "@/components/Saving/OpenAccountComponent/ProgressBar.vue";

</script>

<template>
<!-- 적금명 입력 -->
  <div class="text-black my-5 text-5xl font-bold font-['esamanru']">잔액오버플로우 시작하기</div>
  <progress-bar :current-step="1" :total-steps="3"/>

  <div class="my-10">
    <div class="text-main-red text-4xl font-medium font-['esamanru']">적금명 입력</div>
    <div class="my-5">
      <div class="my-2 text-gray-500 text-2xl font-['esamanru'] font-light">적금에 부여할 이름을 입력해주세요.</div>
      <div class="my-2 text-gray-500 text-2xl font-['esamanru'] font-light">최대 15자까지 가능하며, 목표에 어울리는 이름을 생각해보세요.</div>
    </div>
  </div>

  <div class="px-14 py-8 my-10 bg-gray-100 rounded-3xl shadow">
    <div class="my-9">
      <div class="text-red-500 text-3xl font-medium font-['esamanru']">적금명</div>
      <input
          class="my-5 w-1/2 text-gray-300 text-black text-3xl font-light font-['esamanru'] bg-transparent border-0 border-b-4 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-red-400"
          placeholder="적금명을 입력해주세요."
          required type="text"/>
      <div>
      </div>
    </div>
  </div>

  <div class="text-right my-20">
    <button class="bg-main-red hover:bg-hover-red m-2 px-10 py-5 rounded-xl shadow" type="button">
      <div class="text-white text-4xl font-bold font-['esamanru']">다음</div>
    </button>
  </div>

<!-- 목표 설정 -->
  <div class="text-black my-5 text-5xl font-bold font-['esamanru']">잔액오버플로우 시작하기</div>
  <progress-bar :current-step="2" :total-steps="3"/>

  <div class="my-10">
    <div class="text-main-red text-4xl font-medium font-['esamanru']">목표 설정</div>
    <div class="my-5">
      <div class="my-2 text-gray-500 text-2xl font-['esamanru'] font-light">어떤 목표에 도전하고 싶나요?</div>
      <div class="my-2 text-gray-500 text-2xl font-['esamanru'] font-light">적금을 통해 달성하고 싶은 목표와 금액 입력해주세요.</div>
    </div>
  </div>

  <div class="px-14 py-8 my-10 bg-gray-100 rounded-3xl shadow">
    <div class="my-9">
      <div class="text-red-500 text-3xl font-medium font-['esamanru']">목표명</div>
      <input
          class="my-5 w-full text-gray-300 text-black text-3xl font-light font-['esamanru'] bg-transparent border-0 border-b-4 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-red-400"
          placeholder="목표명을 입력해주세요."
          required type="text"/>
      <div>
      </div>
      <div class="my-9">
        <div class="text-red-500 text-3xl font-medium font-['esamanru']">목표 금액</div>
        <input
            class="my-5 w-1/2 text-gray-300 text-gray-800 text-3xl font-light font-['esamanru'] bg-transparent border-0 border-b-4 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-red-400"
            placeholder="목표 금액을 입력해주세요."
            required type="number"/>
        <span class="text-gray-800 text-3xl font-light font-['esamanru']">원</span>
        <div>
        </div>
      </div>
    </div>
  </div>

  <div class="text-right my-20">
    <button class="bg-main-red hover:bg-hover-red m-2 px-10 py-5 rounded-xl shadow" type="button">
      <div class="text-white text-4xl font-bold font-['esamanru']">다음</div>
    </button>
  </div>

  <!-- 시간별 금액 설정 -->
  <div class="text-black my-5 text-5xl font-bold font-['esamanru']">잔액오버플로우 시작하기</div>
  <progress-bar :current-step="3" :total-steps="3"/>

  <div class="my-10">
    <div class="text-main-red text-4xl font-medium font-['esamanru']">시간별 금액 설정</div>
    <div class="my-5">
      <div class="my-2 text-gray-500 text-2xl font-['esamanru'] font-light">에러를 해결하는 시간을 고려하여 다양한 시간대에 따른 금액을 설정해보세요.</div>
      <div class="my-2 text-gray-500 text-2xl font-['esamanru'] font-light">에러를 해결하는 데 소요되는 시간에 따라 자동으로 저금됩니다.</div>
    </div>
  </div>

  <div class="px-14 py-8 my-10 bg-gray-100 rounded-3xl shadow">
    <div class="my-9 flex items-center">
      <div class="w-1/3 text-gray-800 text-3xl font-medium font-['esamanru']">30분 미만 소요 시</div>
      <div class="">
        <input type="text" class="mr-3 text-gray-500 text-2xl font-light font-['esamanru'] bg-gray-200 border-0 focus:outline-none focus:ring-0 focus:border-red-400"/>
        <span class="px-6 py-3 text-gray-800 text-3xl font-light font-['esamanru']">원</span>
      </div>
      <div>
      </div>

    </div>
  </div>

  <div class="text-right my-20">
    <button class="bg-main-red hover:bg-hover-red m-2 px-10 py-5 rounded-xl shadow" type="button">
      <div class="text-white text-4xl font-bold font-['esamanru']">다음</div>
    </button>
  </div>
</template>

<style scoped>

</style>