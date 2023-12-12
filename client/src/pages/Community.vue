<template>
    <div class="pt-20 mx-auto">
        <div class="relative w-full p-12 sm:max-w-2xl sm:mx-auto">
            <div class="relative z-0 p-1 overflow-hidden rounded-full">
                <form role="form" class="relative z-50 flex bg-white rounded-full">
                    <input type="text" placeholder="enter your search here" class="flex-1 px-3 py-2 text-gray-700 border-0 rounded-full outline-none focus:border-0 focus:outline-none">
                    <button
                        class="px-6 py-2 font-semibold text-white rounded-full bg-main-grn hover:bg-hover-grn focus:bg-hover-grn focus:outline-none">Search</button>
                </form>
                <div class="absolute z-10 bg-main-red glow glow-1"></div>
                <div class="absolute z-20 bg-main-grn glow glow-2"></div>
                <div class="absolute z-30 bg-sub-red glow glow-3"></div>
                <div class="absolute z-40 bg-sub-grn glow glow-4"></div>
            </div>
        </div>
        <div class="flex flex-wrap justify-center w-10/12 mx-auto">
            <div class="max-w-5xl px-10 py-6 my-4 bg-white rounded-lg shadow-md">
                <div class="flex items-center justify-between">
                    <span class="font-light text-gray-600">dec 12, 2023</span>
                    <div class="flex">
                        <fwb-badge class="px-2 py-1 font-bold text-black rounded bg-badge-bg">Language</fwb-badge>
                        <fwb-badge class="px-2 py-1 font-bold text-black rounded bg-badge-bg">Vue.js</fwb-badge>
                    </div>

                </div>
                <div class="mt-2">
                    <div class="flex justify-between">
                        <a class="text-2xl font-bold text-gray-700 hover:text-gray-600" href="#">Vue.js를 처음 배우는 사람들에게
                            전합니다.</a>
                        <div class="my-auto mr-2">
                            <i class="fa-regular fa-thumbs-up fa-xl"></i>
                            <span class="ml-2">90</span>
                        </div>
                    </div>
                    <p class="mt-2 text-gray-600">Vue.js는 처음 접하는 사람에게는 좀 어려울 수 있지만, 그런 난관을 해결하는 과정에서 이해의 쾌감과 성취감을 느낄 수 있어요.
                        Vue.js를 배우면서 살짝 헤매더라도 포기하지 말고 계속 도전해보세요. 그렇게 어려운 과정을 거쳐서 얻는 지식과 능력은 정말 값진 보물이 될 거예요. Vue.js의 매력을
                        발견하고 개발의 재미를 더욱 높여보세요. 저는 당신이 Vue.js를 마스터하며 즐거움을 느끼는 그 순간을 기대하고 있어요! 힘내세요!</p>
                </div>
                <div class="flex items-center justify-between mt-4">
                    <a class="font-bold text-sub-grn hover:underline hover:text-main-grn" href="#">Read more</a>
                    <div class="flex">

                        <a class="flex items-center" href="#">
                            <img class="hidden object-cover w-10 h-10 mx-4 rounded-full sm:block"
                                src="https://cdn.pixabay.com/photo/2018/03/26/20/49/tiger-3264048_1280.jpg" alt="avatar">
                            <h1 class="font-bold text-gray-700">JasonYesBro</h1>
                        </a>
                    </div>
                </div>
            </div>
            <fwb-pagination v-model="currentPage" :total-items="100"></fwb-pagination>
        </div>
    </div>
</template>
<script setup>
import { FwbBadge } from 'flowbite-vue'
import { FwbPagination } from 'flowbite-vue'
import { ref } from 'vue'

const currentPage = ref(1)
</script>