<script setup>

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
      <img src="/images/congrats.svg">
    </div>
  </div>

  <div class="w-9/12 m-auto px-32 py-10 my-10 bg-gray-100 rounded-3xl shadow text-center">
    <div class="mt-5 text-center text-4xl font-bold">스프링마스터 적금</div>
    <div class="my-16 flex justify-between">
      <div class="text-3xl font-medium">목표 금액</div>
      <div class="text-3xl text-gray-700 font-light">200,000원</div>
    </div>
    <div class="my-16 flex justify-between">
      <div class="text-3xl font-medium">적금 계좌</div>
      <div class="text-3xl text-gray-700 font-light">1234-5678-0000000</div>
    </div>
    <div class="my-16 mb-10 flex justify-between">
      <div class="text-3xl font-medium">가입일</div>
      <div class="text-3xl text-gray-700 font-light">2023. 12. 12</div>
    </div>
  </div>

  <div class="w-9/12 m-auto my-20 text-center  flex justify-between">
    <button class="bg-btn-grey hover:bg-gray-400 m-2 px-10 py-5 rounded-xl shadow" type="button">
      <div class="text-white text-4xl font-bold ">메인 이동</div>
    </button>
    <button class="bg-main-red hover:bg-hover-red m-2 px-10 py-5 rounded-xl shadow" type="button">
      <div class="text-white text-4xl font-bold ">에러 등록</div>
    </button>
  </div>
</template>

<style scoped>

</style>