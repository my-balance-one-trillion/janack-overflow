<script setup>
const scrollToTop = () => {
  window.scrollTo({
    top: 0,
    behavior: 'smooth',
  });
}

</script>

<template>
    <div class="p-4 h-full rounded-3xl shadow-md bg-red-50 sticky">
      <!--적금 관리-->
      <div class="text-4xl font-bold p-6 border-b-4 border-b-main-red">적금</div>
      <ul class="space-y-5 my-5 mx-4">
        <li :class="{ 'bg-main-red text-white rounded-full': $route.path === '/saving/details' }">
          <router-link
              class="flex items-center px-8 py-4 rounded-full text-2xl font-medium hover:bg-main-red hover:text-white"
              to="/saving/details" @click="scrollToTop">적금 내역
          </router-link>
        </li>
        <li :class="{ 'bg-main-red text-white rounded-full': $route.path === '/saving/record' }">
          <router-link
              class="flex items-center px-8 py-4 rounded-full text-2xl font-medium hover:bg-main-red hover:text-white"
              to="/saving/record" @click="scrollToTop">적금 기록
          </router-link>
        </li>
        <li :class="{ 'bg-main-red text-white rounded-full': $route.path === '/saving/edit' }">
          <router-link
              class="flex items-center px-8 py-4 rounded-full text-2xl font-medium hover:bg-main-red hover:text-white"
              to="/saving/edit" @click="scrollToTop">적금 수정
          </router-link>
        </li>
        <li :class="{ 'bg-main-red text-white rounded-full': $route.path === '/saving/delete' }">
          <router-link
              class="flex items-center px-8 py-4 rounded-full text-2xl font-medium hover:bg-main-red hover:text-white"
              to="/saving/delete" @click="scrollToTop">적금 삭제
          </router-link>
        </li>
      </ul>
    </div>
</template>

<style scoped>
.sticky {
  position: sticky;
  top: 50px;
}
</style>