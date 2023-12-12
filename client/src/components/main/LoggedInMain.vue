<template>
<div class="flex my-5">
  <div>
    <div class="truncate" style="font-size: 96px;"><span class="text-sub-red">코딩</span>하며 마주친<br>
    <span class="text-main-red">에러</span>를 <span class="text-main-grn">기록</span>하고</div>
    <div class="block" style="color: #7C7A7A; font-size: 48px">해결되면 저축도 하고!<br>
  해결한 경험을 공유해보세요!</div>
  </div>
  <div class="relative my-2">
    <div class="absolute top-0 right-0">
      <img class="z-1 float-right" src="@/assets/images/main-coin.gif" alt="gif" width="286px" height="216px">
    </div>
    <img class=" z-2 top-3" src="/src/assets/images/mainImg1.png">
</div>
</div>
<div class="main-middle ">
  <div class="text-5xl">최근 올라온<span class="text-main-red"> 에러</span></div>
  <div class="flex flex-wrap justify-center my-5">
    <MainPageCard></MainPageCard>
    <MainPageCard></MainPageCard>
    <MainPageCard></MainPageCard>
    <MainPageCard></MainPageCard>
    <MainPageCard></MainPageCard>
    <MainPageCard></MainPageCard>
    <MainPageCard></MainPageCard>
    <MainPageCard></MainPageCard>
    <MainPageCard></MainPageCard>
    <MainPageCard></MainPageCard>
    
  </div>
</div>

<div class="main-bottom flex relative my-10 text-center mx-auto">
  <div class="absolute">
  <div class="flex opacity-70 rounded-full justify-center items-center w-[350px] h-[350px]" style="background-color: #57846F;">
    <i class="fa-solid fa-8x fa-piggy-bank" style="color: #ffffff;"></i>
  </div>
  <div class="text-center my-12">
  <div style="font-size: 51px;">적금 서비스</div>
  <div style="font-size: 32px;">코딩하다가 만났던<br>에러를 해결하는<br> 동시에 적금을 하면서<br> 의미있게 적금하세요!</div>
</div>
</div>
  <div class="absolute left-1/4">
  <div class="flex opacity-70 rounded-full justify-center items-center w-[350px] h-[350px]" style="background-color: #D9B5B0;">
    <i class="fa-solid fa-8x fa-hourglass-half" style="color: #ffffff;"></i>
  </div>
  <div class="text-center my-12">
  <div style="font-size: 51px;">스톱워치</div>
  <div style="font-size: 32px;">에러 해결 시간에<br> 
따라 적금 액수가<br> 
달라져요!</div>
</div>
</div>
<div class="absolute left-2/4">
  <div class="flex opacity-70 rounded-full justify-center items-center w-[350px] h-[350px]" style="background-color: #B0D9C6;">
    <i class="fa-regular fa-8x fa-rectangle-list" style="color: #ffffff;"></i>
  </div>
  <div class="text-center my-12">
  <div style="font-size: 51px;">에러노트</div>
  <div style="font-size: 32px;">에러를 설명과 <br>코드를 함께 <br>
기입하고 해결 방법을<br> 
추천받으면서 손쉽게<br> 
기록하세요!</div>
</div>
</div>
<div class="absolute left-3/4">
  <div class="flex opacity-70 rounded-full items-center justify-center  w-[350px] h-[350px]" style="background-color: #CAAACF;height: 360px;width: 360px;">
    <i class="fa-solid fa-8x fa-comments" style="color: #ffffff;"></i>
  </div>
  <div class="text-center my-12">
  <div class="items-center" style="font-size: 51px;">커뮤니티</div>
  <div style="font-size: 32px;">자신이 해결한 에러를<br>공유하고 사용자들과<br> 
함께 토의해봐요!</div>
</div>
</div>
</div>
<div>

</div>
</template>
<script setup>
import MainPageCard from './MainPageCard.vue';
</script>
<style>
</style>