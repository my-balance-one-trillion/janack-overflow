<script setup>

</script>

<template>

  <div class="my-20 flex justify-center">
    <img class="w-1/4" src="/images/logo.svg" alt="logo">
  </div>
  <div class="space-y-6 text-center">
    <div class="text-2xl my-2  font-medium">에러를 기록하는 만큼 📝 내 통장에 돈이 쌓여요💰</div>
    <div class="text-3xl my-2 text-red-700  font-medium">잔액이 overflow될 때까지 에러 기록을 남겨보세요!</div>
  </div>
  <div class="text-center my-20">
    <router-link to="/open" type="button" class="bg-main-red hover:bg-hover-red m-2 px-10 py-5 rounded-xl shadow">
      <div class="text-white text-3xl font-bold ">잔액오버플로우 시작하기</div>
    </router-link>
  </div>


  <div class="px-14 py-8 my-10 bg-gray-100 rounded-3xl shadow">
    <div class="my-10">
      <div class="text-red-600 text-2xl font-medium ">에러는 성장의 시작</div>
      <div class="my-3">
        <div class="my-3 text-zinc-500 text-xl font-light ">개발자는 항상 에러와 함께하지만, 그 에러들은 여러분을 더 나은 개발자로 이끌어줄 것입니다.</div>
        <div class="my-3 text-zinc-500 text-xl font-light ">'잔액오버플로우'의 에러노트를 통해 성장을 기록하세요.</div>
      </div>
    </div>
    <div class="my-10">
      <div class="text-red-600 text-2xl font-medium ">자동 적금, 더 간편하게</div>
      <div class="my-3">
        <div class="my-3 text-zinc-500 text-xl font-light ">연동된 계좌와 설정한 규칙에 따라 목표 달성을 위한 자동 적금이 가능합니다.</div>
        <div class="my-3 text-zinc-500 text-xl font-light ">개발에 집중하면서도 손쉽게 돈을 모으고 성취감을 느껴보세요.</div>
      </div>
    </div>
    <div class="my-10">
      <div class="text-red-600 text-2xl font-medium ">에러를 공유하세요</div>
      <div class="my-3">
        <div class="my-3 text-zinc-500 text-xl font-light ">잔액오버플로우의 커뮤니티에서 발생한 에러와 그에 대한 해결책을 자유롭게 공유해보세요.</div>
        <div class="my-3 text-zinc-500 text-xl font-light ">다양한 의견과 지식을 얻어 더 나은 개발자로 성장해보세요.</div>
      </div>
    </div>

  </div>

</template>

<style scoped>

</style>