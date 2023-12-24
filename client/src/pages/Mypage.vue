<template>
  <main class="my-10 flex items-start" v-if="authStore.userInfo.role ==='USER'">
    <aside class="flex flex-row">
      <div class="flex flex-col w-56 bg-white rounded-3xl overflow-hidden border border-sub-red">
        <div class="mt-6 mx-auto">
          <img id="profileimg" :src="`/images/${authStore.userInfo.profileImage}`" class="rounded-full w-32"
            alt="profile picture" />
        </div>

        <div class="mt-5 mx-auto">
          <h2 id="profilenick" class="text-lg tracking-wide">
            {{ authStore.userInfo.nickname }}
          </h2>
        </div>
        <div class="border-b-4 border-main-red w-40 mx-auto my-4"></div>

        <div class="mypage-title inline-flex items-center justify-center">
          <span class="text-2xl bold">마이페이지</span>
        </div>
        <ul class="flex flex-col py-4">
          <li>
            <div
              class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-xl hover:text-black-800 cursor-pointer"
              @click="changeComponent('Dashboard')">
              <span class="inline-flex items-center justify-center h-12 w-9 text-lg text-gray-400"><i
                  class="bx bx-home"></i></span>
              <span class="text-lg font-medium">대시보드</span>
            </div>
          </li>
          <li>
            <div
              class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-lg hover:text-gray-800 cursor-pointer"
              @click="changeComponent('UpdateInfo')">
              <span class="inline-flex items-center justify-center h-12 w-9 text-lg"><i class="bx bx-music"></i></span>
              <span class="text-lg font-medium">내 정보 재설정</span>
            </div>
          </li>
          <li>
            <div
              class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-lg hover:text-gray-800 cursor-pointer"
              @click="changeComponent('ProfileImage')">
              <span class="inline-flex items-center justify-center h-12 w-9 text-lg"><i class="bx bx-user"></i></span>
              <span class="text-lg font-medium">프로필 이미지 변경</span>
            </div>
          </li>
          <li>
            <div
              class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-lg hover:text-gray-800 cursor-pointer"
              @click="changeComponent('myissue')">
              <span class="inline-flex items-center justify-center h-12 w-9 text-lg"><i class="bx bx-drink"></i></span>
              <span class="text-lg font-medium">내가 등록한 에러 목록</span>
            </div>
          </li>
          <li>
            <div
              class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-xl hover:text-gray-800 cursor-pointer"
              @click="changeComponent('MyComment')">
              <span class="inline-flex items-center justify-center h-12 w-9 text-lg"><i
                  class="bx bx-shopping-bag"></i></span>
              <span class="text-lg font-medium">내가 작성한 댓글</span>
            </div>
          </li>
          <li>
            <router-link to="/saving"
              class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-lg hover:text-gray-800 cursor-pointer">
              <span class="inline-flex items-center justify-center h-12 w-9 text-lg"><i class="bx bx-chat"></i></span>
              <span class="text-lg font-medium text-main-red">내 적금
                <i class="fa-solid fa-arrow-up-right-from-square text-gray-500 text-sm cursor-pointer"></i></span>
            </router-link>
          </li>
        </ul>
      </div>
    </aside>
    <section class="ml-10 w-full">
      <Dashboard v-if="currentComponent === 'Dashboard'" />
      <UpdateInfo v-if="currentComponent === 'UpdateInfo'" />
      <MyIssue v-if="currentComponent === 'myissue'" />
      <MyComment v-if="currentComponent === 'MyComment'" />
      <ProfileImage v-if="currentComponent === 'ProfileImage'" />
    </section>
  </main>
  <main class="flex flex-col justify-center h-screen" v-else>
    <div class="flex flex-col justify-center items-center">
      <p>회원 전용 서비스입니다.</p>
      <p>
        <router-link to="/login" class="cursor-pointer text-main-red underline">로그인</router-link>
      </p>
    </div>
  </main>
</template>
<script setup>
import { onMounted, ref } from "vue";
import { useAuthStore } from "@/stores/auth";
import Dashboard from "@/components/mypage/Dashboard.vue";
import ProfileImage from "@/components/mypage/ProfileImage.vue";
import UpdateInfo from "@/components/mypage/UpdateInfo.vue";
import MyIssue from "@/components/mypage/MyIssue.vue";
import MyComment from "@/components/mypage/MyComment.vue";


const authStore = useAuthStore();
const currentComponent = ref("Dashboard");



const props = defineProps(['component']);

onMounted(() => {
  console.log("fromMain", props.component);
  if (props.component === 'myissue') {
    changeComponent("myissue");
  }
})


const changeComponent = (component) => {
  currentComponent.value = component;
};

</script>
<style scoped></style>
