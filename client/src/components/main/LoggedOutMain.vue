<template>
  <div class="flex my-5 mt-20">
    <div class="flex flex-col h-full">
      <img src="/images/main-text.svg" width="700px" />
      <div class="flex items-center content-center min-h-[300px]">
        <router-link
          to="/intro"
          type="button"
          class="px-10 py-5 m-2 mx-auto shadow bg-main-red hover:bg-hover-red rounded-xl w-fit"
        >
          <div class="text-4xl font-bold text-white ">
            적금 생성하기
          </div>
        </router-link>
      </div>
    </div>

    <div class="relative my-2">
      <div class="absolute top-0 right-0">
        <img
          class="float-right z-1"
          src="/images/main-coin.gif"
          alt="gif"
          width="286px"
          height="216px"
        />
      </div>
      <img class="z-2 top-3 w-[657px] h-[657px]" src="/images/mainImg1.png" />
    </div>
  </div>
  <div class="main-middle">
    <div class="text-3xl">
      최근 올라온<span class="text-main-red"> 에러</span>
    </div>
    <div class="flex flex-wrap justify-center my-5">
      <MainPageCard
        v-for="(issue, index) in issueList"
        :issue="issue"
        :key="index"
      />
    </div>
  </div>

  <div class="flex relative w-5/6 min-h-[700px] mt-36 text-center mx-auto">
    <div class="absolute">
      <div
        class="flex opacity-70 rounded-full justify-center items-center w-[320px] h-[320px]"
        style="background-color: #57846f"
      >
        <i class="fa-solid fa-8x fa-piggy-bank" style="color: #ffffff"></i>
      </div>
      <div class="my-12 text-center">
        <div style="font-size: 35px">적금 서비스</div>
        <div style="font-size: 21px">
          코딩하다가 만났던<br />에러를 해결하는 동시에<br />
          의미있게 적금하세요!<br />
          
        </div>
      </div>
    </div>
    <div class="absolute left-1/4">
      <div
        class="flex opacity-70 rounded-full justify-center items-center w-[320px] h-[320px]"
        style="background-color: #d9b5b0"
      >
        <i class="fa-solid fa-8x fa-hourglass-half" style="color: #ffffff"></i>
      </div>
      <div class="my-12 text-center">
        <div style="font-size: 35px">스톱워치</div>
        <div style="font-size: 21px">
          에러 해결 시간에<br />
          따라 적금 액수가<br />
          달라져요!
        </div>
      </div>
    </div>
    <div class="absolute left-2/4">
      <div
        class="flex opacity-70 rounded-full justify-center items-center w-[320px] h-[320px]"
        style="background-color: #b0d9c6"
      >
        <i
          class="fa-regular fa-8x fa-rectangle-list"
          style="color: #ffffff"
        ></i>
      </div>
      <div class="my-12 text-center">
        <div style="font-size: 35px">에러노트</div>
        <div style="font-size: 21px">
          에러를 기록하고<br />
          해결 방법을<br />
          추천받으면서 손쉽게<br />
          기록하세요!
        </div>
      </div>
    </div>
    <div class="absolute left-3/4">
      <div
        class="flex opacity-70 rounded-full items-center justify-center w-[320px] h-[320px]"
        style="background-color: #caaacf"
      >
        <i class="fa-solid fa-8x fa-comments" style="color: #ffffff"></i>
      </div>
      <div class="my-12 text-center">
        <div class="items-center" style="font-size: 35px">커뮤니티</div>
        <div style="font-size: 21px">
          자신이 해결한 에러를<br />공유하고 사용자들과<br />
          함께 토의해봐요!
        </div>
      </div>
    </div>
  </div>
  <div></div>
</template>
<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";
import MainPageCard from "./MainPageCard.vue";

const issueList = ref([]);

onMounted(() => {
  axios.get("http://localhost:8081/main/unlogin").then((response) => {
    issueList.value = response.data;
    // console.log("response", issueList.value);
  });
});
</script>
<style></style>
