<template>
  <main>
    <aside>
      <div class="min-h-screen flex flex-row">
        <div
          class="flex flex-col w-56 bg-white rounded-3xl overflow-hidden border-2 border-sub-red"
        >
          <div class="flex items-center justify-center h-20 shadow-md">
            <h1 class="text-3xl uppercase text-indigo-500">Logo</h1>
          </div>
          <div class="mypage-title">
            <span class="text-4xl bold">마이페이지</span>
          </div>
          <ul class="flex flex-col py-4">
            <li>
              <a
                href="#"
                class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-2xl hover:text-black-800"
              >
                <span
                  class="inline-flex items-center justify-center h-12 w-12 text-2xl text-gray-400"
                  ><i class="bx bx-home"></i
                ></span>
                <span class="text-2xl font-medium">대시보드</span>
              </a>
            </li>
            <li>
              <a
                href="#"
                class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-2xl hover:text-gray-800"
              >
                <span
                  class="inline-flex items-center justify-center h-12 w-12 text-2xl"
                  ><i class="bx bx-music"></i
                ></span>
                <span class="text-2xl font-medium">내 정보 재설정</span>
              </a>
            </li>
            <li>
              <a
                href="#"
                class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-2xl hover:text-gray-800"
              >
                <span
                  class="inline-flex items-center justify-center h-12 w-12 text-lg text-2xl"
                  ><i class="bx bx-drink"></i
                ></span>
                <span class="text-2xl font-medium">내가 등록한 에러 목록</span>
              </a>
            </li>
            <li>
              <a
                href="#"
                class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-2xl hover:text-gray-800"
              >
                <span
                  class="inline-flex items-center justify-center h-12 w-12 text-2xl"
                  ><i class="bx bx-shopping-bag"></i
                ></span>
                <span class="text-2xl font-medium">내가 작성한 댓글</span>
              </a>
            </li>
            <li>
              <a
                href="#"
                class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-2xl hover:text-gray-800"
              >
                <span
                  class="inline-flex items-center justify-center h-12 w-12 text-2xl"
                  ><i class="bx bx-chat"></i
                ></span>
                <span class="text-2xl font-medium">내 적금</span>
              </a>
            </li>
            <li>
              <a
                href="#"
                class="flex flex-row items-center h-12 transform hover:translate-x-2 transition-transform ease-in duration-200 text-2xl hover:text-gray-800"
              >
                <span
                  class="inline-flex items-center justify-center h-12 w-12 text-2xl"
                  ><i class="bx bx-user"></i
                ></span>
                <span class="text-2xl font-medium">프로필 이미지 변경</span>
              </a>
            </li>
          </ul>
        </div>
      </div>
    </aside>
    <section></section>
  </main>
</template>
<script setup></script>
<style scoped></style>
