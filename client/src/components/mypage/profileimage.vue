<template>
  <h3 class="text-3xl text-main-red font-bold mb-7">프로필 이미지 선택하기</h3>
  <div class="border-b-4 border-main-red"></div>
  <p class="text-xl my-4">이미지 선택</p>
  <p class="text-[#919090]">마음에 드는 프로필 사진을 골라주세요</p>
  <article class="mx-20 my-20">
    <div class="grid grid-cols-4 gap-10">
      <div>
        <img
          class="h-auto max-w-full rounded-lg border-yellow-400 cursor-pointer"
          src="/images/default.png"
          alt=""
          @click="setImgName"
        />
      </div>
      <div>
        <img
          class="h-auto max-w-full rounded-lg border-yellow-400 cursor-pointer"
          src="/images/money.png"
          alt=""
          @click="setImgName"
        />
      </div>
      <div>
        <img
          class="h-auto max-w-full rounded-lg border-yellow-400 cursor-pointer"
          src="/images/saving.png"
          alt=""
          @click="setImgName"
        />
      </div>
      <div>
        <img
          class="h-auto max-w-full rounded-lg border-yellow-400 cursor-pointer"
          src="/images/coding.png"
          alt=""
          @click="setImgName"
        />
      </div>
      <div>
        <img
          class="h-auto max-w-full rounded-lg border-yellow-400 cursor-pointer"
          src="/images/dev.png"
          alt=""
          @click="setImgName"
        />
      </div>
      <div>
        <img
          class="h-auto max-w-full rounded-lg border-yellow-400 cursor-pointer"
          src="/images/codebug.png"
          alt=""
          @click="setImgName"
        />
      </div>
      <div>
        <img
          class="h-auto max-w-full rounded-lg border-yellow-400 cursor-pointer"
          src="/images/bankbook.png"
          alt=""
          @click="setImgName"
        />
      </div>
      <div>
        <img
          class="h-auto max-w-full rounded-lg border-yellow-400 cursor-pointer"
          src="/images/error.png"
          alt=""
          @click="setImgName"
        />
      </div>
    </div>
  </article>
</template>
<script setup>
import axios from "axios";
import { onMounted } from "vue";
import { useAuthStore } from "@/stores/auth";

const authStore = useAuthStore();

// ---------------------------
// 프로필 이미지 확인
// ---------------------------

onMounted(() => {
  const imgArr = document.querySelectorAll(".grid img");
  for (let img of imgArr) {
    img.classList.remove("border-4");
    if (authStore.userInfo.profileImage == img.src.split("/").pop()) {
      img.classList.add("border-4");
    }
  }
});

// ---------------------------
// 프로필 이미지 변경
// ---------------------------

async function setImgName(e) {
  let imgName = e.target.src.split("/").pop();
  let updateimage = { profileImage: imgName };
  await axios.put("/mypage/profileimage", updateimage, {
    headers: {
      "content-type": "application/json",
      authorization: authStore.token,
    },
  });
  authStore.getUserInfo();
  const imgArr = document.querySelectorAll(".grid img");
  for (let img of imgArr) {
    img.classList.remove("border-4");
  }
  e.target.classList.add("border-4");
}
</script>
<style scoped></style>
