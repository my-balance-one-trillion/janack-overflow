<template>
  <main class="my-10 flex items-start">
    <aside class="flex flex-row">
      <div class="flex flex-col w-56 bg-white rounded-3xl overflow-hidden border border-sub-red">
        <div class="mt-6 mx-auto">
          <img
            src="https://api.lorem.space/image/face?w=120&h=120&hash=bart89fe"
            class="rounded-full w-20"
            alt="profile picture"
            srcset=""
          />
        </div>

        <div class="mt-8 mx-auto">
          <h2 class="text-lg tracking-wide">개발왕</h2>
        </div>
        <div class="border-b-4 border-main-red w-40 mx-auto my-4"></div>

        <div class="mypage-title inline-flex items-center justify-center">
          <span class="text-2xl bold">마이페이지</span>
        </div>
        <ul class="flex flex-col py-4">
          <li>
            <div
              class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-xl hover:text-black-800"
              @click="changeComponent('dashboard')"
            >
              <span
                class="inline-flex items-center justify-center h-12 w-9 text-lg text-gray-400"
                ><i class="bx bx-home"></i
              ></span>
              <span class="text-lg font-medium">대시보드</span>
                </div>
          </li>
          <li>
            <div
              class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-lg hover:text-gray-800"
              @click="changeComponent('updateinfo')"
            >
              <span
                class="inline-flex items-center justify-center h-12 w-9 text-lg"
                ><i class="bx bx-music"></i
              ></span>
              <span class="text-lg font-medium">내 정보 재설정</span>
                </div>
          </li>
          <li>
            <div
              class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-lg hover:text-gray-800"
              @click="changeComponent('profileimage')"
            >
              <span
                class="inline-flex items-center justify-center h-12 w-9 text-lg"
                ><i class="bx bx-user"></i
              ></span>
              <span class="text-lg font-medium">프로필 이미지 변경</span>
                </div>
          </li>
          <li>
            <div
              class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-lg hover:text-gray-800"
              @click="changeComponent('myissue')"
            >
              <span
                class="inline-flex items-center justify-center h-12 w-9 text-lg"
                ><i class="bx bx-drink"></i
              ></span>
              <span class="text-lg font-medium">내가 등록한 에러 목록</span>
                </div>
          </li>
          <li>
            <div
              class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-xl hover:text-gray-800"
              @click="changeComponent('mycomment')"
            >
              <span
                class="inline-flex items-center justify-center h-12 w-9 text-lg"
                ><i class="bx bx-shopping-bag"></i
              ></span>
              <span class="text-lg font-medium">내가 작성한 댓글</span>
                </div>
          </li>
          <li>
            <Router-link to="/saving"
              class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-lg hover:text-gray-800"
            >

              <span
                class="inline-flex items-center justify-center h-12 w-9 text-lg"
                ><i class="bx bx-chat"></i
              ></span>
              <span class="text-lg font-medium">내 적금 <i class="fa-solid fa-arrow-up-right-from-square text-gray-500 text-sm"></i></span>
            </router-link>
          </li>
          
        </ul>
      </div>
    </aside>
    <section class="ml-10 w-full">
      <!-- <component :is="currentComponent" /> -->
      <dashboard v-if="currentComponent === 'dashboard'" />
      <updateinfo v-if="currentComponent === 'updateinfo'" />
      <myissue v-if="currentComponent === 'myissue'" />
      <mycomment v-if="currentComponent === 'mycomment'" />
      <profileimage v-if="currentComponent === 'profileimage'" />
    </section>
  </main>
</template>
<script setup>
import { ref } from 'vue';
import dashboard from "../components/mypage/dashboard.vue";
import profileimage from "../components/mypage/profileimage.vue";
import updateinfo from "../components/mypage/updateinfo.vue";
import myissue from "../components/mypage/myissue.vue";
import mycomment from "../components/mypage/mycomment.vue";
import { RouterLink } from 'vue-router';

const currentComponent  = ref('dashboard');

const changeComponent  = (component) =>{
  currentComponent.value = component;
  console.log(component);
};

</script>
<style scoped></style>
