<script setup>

</script>

<template>
  <main class="h-screen w-full flex flex-col justify-center items-center ">
    <h1 class="text-9xl font-extrabold text-red-600 tracking-widest">404</h1>
    <div class="bg-main-grn text-white px-2 text-sm rounded rotate-12 absolute">
      Page Not Found
    </div>
    <button class="mt-5">
      <a
          class="relative inline-block text-sm font-medium text-[#FF6A3D] group active:text-orange-500 focus:outline-none focus:ring"
      >
        <span
            class="absolute inset-0 transition-transform translate-x-0.5 translate-y-0.5 bg-main-red group-hover:translate-y-0 group-hover:translate-x-0"
        ></span>

        <span class="relative block px-8 py-3 bg-[#1A2238] text-white border border-current">
          <router-link to="/">메인으로 이동</router-link>
        </span>
      </a>
    </button>
  </main>
</template>

<style scoped>

</style>