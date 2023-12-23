<template>
    <div class="w-full pt-20 mx-auto">
        <div class="relative w-3/4 mx-auto p-7 sm:max-w-xl sm:mx-auto" ref="targetRef">
            <div class="relative z-0 p-1 overflow-hidden rounded-full">
                <div class="relative z-50 flex bg-white rounded-full">
                    <input type="text" v-model="searchInput" id="searchInput" placeholder="검색어를 입력해 주세요"
                        class="flex-1 px-4 py-3 text-gray-500 border-0 rounded-full outline-none text-md focus:border-0 focus:outline-none">
                    <!-- <button
                        class="px-6 py-2 font-semibold text-white rounded-full bg-main-grn hover:bg-hover-grn focus:bg-hover-grn focus:outline-none"
                        @click="searchIssue">Search</button> -->
                </div>
                <div class="absolute z-10 bg-main-red glow glow-1"></div>
                <div class="absolute z-20 bg-main-grn glow glow-2"></div>
                <div class="absolute z-30 bg-sub-red glow glow-3"></div>
                <div class="absolute z-40 bg-sub-grn glow glow-4"></div>
            </div>
        </div>
        <div class="flex justify-end pr-16 mb-5 lg:w-11/12 lg:pr-12 md:w-11/12 sm:w-auto md:pr-8 sm:pr-20">
            <button id="dropdownRadioButton" data-dropdown-toggle="dropdownDefaultRadio"
                class="text-white bg-main-grn hover:bg-hover-grn focus:ring-4 focus:outline-none focus:ring-sub-grn font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:bg-sub-grn dark:hover:bg-sub-grn dark:focus:ring-sub-grn"
                type="button">FILTER <svg class="w-2.5 h-2.5 ms-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg"
                    fill="none" viewBox="0 0 10 6">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                        d="m1 1 4 4 4-4" />
                </svg>
            </button>

            <!-- Dropdown menu -->
            <div id="dropdownDefaultRadio"
                class="z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow w-36 dark:bg-gray-700 dark:divide-gray-600">
                <ul class="p-3 space-y-3 text-sm text-gray-700 dark:text-gray-200" aria-labelledby="dropdownRadioButton">
                    <li>
                        <div class="flex items-center">
                            <input checked id="default-radio-0" type="radio" value="" name="default-radio"
                                v-model="selectedFilter"
                                class="w-4 h-4 bg-gray-100 border-gray-300 text-main-red focus:ring-main-red dark:focus:ring-sub-red dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
                            <label for="default-radio-0"
                                class="text-sm font-medium text-gray-900 ms-2 dark:text-gray-300">default</label>
                        </div>
                    </li>
                    <li>
                        <div class="flex items-center">
                            <input id="default-radio-1" type="radio" value="syntax" name="default-radio"
                                v-model="selectedFilter"
                                class="w-4 h-4 bg-gray-100 border-gray-300 text-main-red focus:ring-main-red dark:focus:ring-sub-red dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
                            <label for="default-radio-1"
                                class="text-sm font-medium text-gray-900 ms-2 dark:text-gray-300">syntax</label>
                        </div>
                    </li>
                    <li>
                        <div class="flex items-center">
                            <input id="default-radio-2" type="radio" value="language" name="default-radio"
                                v-model="selectedFilter"
                                class="w-4 h-4 bg-gray-100 border-gray-300 text-main-red focus:ring-main-red dark:focus:ring-sub-red dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
                            <label for="default-radio-2"
                                class="text-sm font-medium text-gray-900 ms-2 dark:text-gray-300">language</label>
                        </div>
                    </li>
                    <li>
                        <div class="flex items-center">
                            <input id="default-radio-3" type="radio" value="database" name="default-radio"
                                v-model="selectedFilter"
                                class="w-4 h-4 bg-gray-100 border-gray-300 text-main-red focus:ring-main-red dark:focus:ring-sub-red dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
                            <label for="default-radio-3"
                                class="text-sm font-medium text-gray-900 ms-2 dark:text-gray-300">database</label>
                        </div>
                    </li>
                    <li>
                        <div class="flex items-center">
                            <input id="default-radio-3" type="radio" value="os" name="default-radio"
                                v-model="selectedFilter"
                                class="w-4 h-4 bg-gray-100 border-gray-300 text-main-red focus:ring-main-red dark:focus:ring-sub-red dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
                            <label for="default-radio-3"
                                class="text-sm font-medium text-gray-900 ms-2 dark:text-gray-300">os</label>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <div class="flex flex-wrap justify-center w-11/12 mx-auto space-y-10">
            <div v-if="!solvedIssue">
                <Spinner></Spinner>
            </div>
            <div v-for="(issue, index) in solvedIssue"
                class="w-10/12 max-w-5xl px-10 py-6 my-4 break-all bg-white border border-b-4 rounded-lg shadow-lg min-w-min border-main-grn">
                <div class="flex items-center justify-between">
                    <span class="font-light text-gray-600">{{ issue.createdAt }}</span>
                    <div class="flex">
                        <fwb-badge class="px-2 py-1 font-bold text-black rounded bg-badge-bg">{{ issue.category
                        }}</fwb-badge>
                    </div>
                </div>
                <div class="mt-2">
                    <div class="flex justify-between">
                        <a class="h-8 text-2xl font-bold text-black line-clamp-1 hover:text-gray-800"
                            :href='`/community/detail/${issue.id}`'>{{ issue.title }}</a>
                        <div class="flex items-center my-auto mr-2 w-fit">
                            <i class="fa-regular fa-thumbs-up fa-xl"></i>
                            <span class="pt-2 ml-2 text-md">{{ issue.likes }}</span>
                        </div>
                    </div>
                    <p class="mt-2 text-gray-600 line-clamp-2">{{ issue.content }}</p>
                </div>
                <div class="flex items-center justify-between mt-4">
                    <div>
                        <div class="flex items-center">
                            <img class="hidden object-cover w-10 h-10 mr-3 rounded-full sm:block"
                                :src="'/images/' + issue.communityUsersDTO.profileImage" alt="avatar">
                            <h1 class="font-bold text-gray-700">{{ issue.communityUsersDTO.nickname }}</h1>
                        </div>
                    </div>
                    <router-link class="mr-2 font-bold text-sub-grn hover:underline hover:text-main-grn"
                        :to='`/community/detail/${issue.id}`'>Read more</router-link>
                </div>
            </div>
            <div class="flex justify-center w-full">
                <fwb-pagination @click="handleClick" class="py-10" v-model="currentPage"
                    :total-items=totalItems></fwb-pagination>
            </div>
        </div>
    </div>
</template>
<script setup>
import { FwbBadge } from 'flowbite-vue';
import { FwbPagination } from 'flowbite-vue';
import { ref, onMounted, watch } from 'vue';
import { initFlowbite } from 'flowbite'
import axios from "axios";
import Spinner from "@/components/Spinner.vue";
import { useRoute, useRouter } from 'vue-router';

const searchInput = ref('');
const currentPage = ref(1);
const totalPages = ref(0);
const totalItems = ref(0);
const page = ref({});
const solvedIssue = ref(null);
const pageNo = ref(0);
const selectedFilter = ref('');
const isPage = ref(null);
console.log("currentPage.value ::" + currentPage.value);
const targetRef = ref(null);
// filter 부분 함수로 빼야함
watch(selectedFilter, async () => {
    currentPage.value = 1;
    await searchIssue();
});

watch(currentPage, async (newValue, oldValue) => {
    console.log("newValue :" + newValue + "oldValue : " + oldValue);
    currentPage.value = newValue;
    await searchIssue();
});

watch(searchInput, async () => {
    currentPage.value = 1;
    await searchIssue();
});

async function searchIssue() {
    if (currentPage.value != null && currentPage.value > 1) {
        await axios
            .get(
                '/community/solvedissue/search?title=' + searchInput.value + '&category=' + selectedFilter.value + '&pageNo=' + (currentPage.value - 1)
            )
            .then((response) => {
                console.log(response);
                solvedIssue.value = response.data.content;
                totalPages.value = response.data.totalPages;
                totalItems.value = response.data.totalElements;
                page.value = response.data.pageable;

            });
    } else {
        await axios
            .get(
                '/community/solvedissue/search?title=' + searchInput.value + '&category=' + selectedFilter.value
            )
            .then((response) => {
                console.log(response);
                solvedIssue.value = response.data.content;
                totalPages.value = response.data.totalPages;
                totalItems.value = response.data.totalElements;
                page.value = response.data.pageable;
            });
    }
}
const handleClick = () => {
    targetRef.value.scrollIntoView({ behavior: 'smooth' });
};
onMounted(() => {

    initFlowbite();
    searchIssue();
});

</script>
