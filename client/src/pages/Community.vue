<template>
    <div class="w-full pt-20 mx-auto">
        <div class="relative w-full p-12 sm:max-w-2xl sm:mx-auto">
            <div class="relative z-0 p-1 overflow-hidden rounded-full">
                <div class="relative z-50 flex bg-white rounded-full">
                    <input type="text" id="searchInput" placeholder="검색어를 입력해 주세요"
                        class="flex-1 px-3 py-2 text-sm text-gray-500 border-0 rounded-full outline-none focus:border-0 focus:outline-none">
                    <button
                        class="px-6 py-2 font-semibold text-white rounded-full bg-main-grn hover:bg-hover-grn focus:bg-hover-grn focus:outline-none"
                        @click="searchIssue">Search</button>
                </div>
                <div class="absolute z-10 bg-main-red glow glow-1"></div>
                <div class="absolute z-20 bg-main-grn glow glow-2"></div>
                <div class="absolute z-30 bg-sub-red glow glow-3"></div>
                <div class="absolute z-40 bg-sub-grn glow glow-4"></div>
            </div>
        </div>
        <div class="flex justify-end">
            <button id="dropdownRadioButton" data-dropdown-toggle="dropdownDefaultRadio"
                class="text-white bg-sub-grn hover:bg-sub-grn focus:ring-4 focus:outline-none focus:ring-main-grn font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:bg-sub-grn dark:hover:bg-sub-grn dark:focus:ring-main-grn"
                type="button">Dropdown radio <svg class="w-2.5 h-2.5 ms-3" aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                        d="m1 1 4 4 4-4" />
                </svg>
            </button>

            <!-- Dropdown menu -->
            <div id="dropdownDefaultRadio"
                class="z-10 hidden w-48 bg-white divide-y divide-gray-100 rounded-lg shadow dark:bg-gray-700 dark:divide-gray-600">
                <ul class="p-3 space-y-3 text-sm text-gray-700 dark:text-gray-200" aria-labelledby="dropdownRadioButton">
                    <li>
                        <div class="flex items-center">
                            <input id="default-radio-1" type="radio" value="" name="default-radio"
                                class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
                            <label for="default-radio-1"
                                class="text-sm font-medium text-gray-900 ms-2 dark:text-gray-300">Default radio</label>
                        </div>
                    </li>
                    <li>
                        <div class="flex items-center">
                            <input checked id="default-radio-2" type="radio" value="" name="default-radio"
                                class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
                            <label for="default-radio-2"
                                class="text-sm font-medium text-gray-900 ms-2 dark:text-gray-300">Checked state</label>
                        </div>
                    </li>
                    <li>
                        <div class="flex items-center">
                            <input id="default-radio-3" type="radio" value="" name="default-radio"
                                class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
                            <label for="default-radio-3"
                                class="text-sm font-medium text-gray-900 ms-2 dark:text-gray-300">Default radio</label>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <div class="flex flex-wrap justify-center w-10/12 mx-auto space-y-10">
            <div v-for="(issue, index) in solvedIssue"
                class="w-10/12 max-w-5xl px-10 py-6 my-4 bg-white border border-b-4 rounded-lg shadow-lg min-w-min border-main-grn">
                <div class="flex items-center justify-between">
                    <span class="font-light text-gray-600">dec 12, 2023</span>
                    <div class="flex">
                        <fwb-badge class="px-2 py-1 font-bold text-black rounded bg-badge-bg">{{ issue.category
                        }}</fwb-badge>
                    </div>
                </div>
                <div class="mt-2">
                    <div class="flex justify-between">
                        <a class="text-2xl font-bold text-black hover:text-gray-800" href="#">{{ issue.title }}</a>
                        <div class="my-auto mr-2">
                            <i class="fa-regular fa-thumbs-up fa-xl"></i>
                            <span class="ml-2">{{ issue.likes }}</span>
                        </div>
                    </div>
                    <p class="mt-2 text-gray-600">{{ issue.content }}</p>
                </div>
                <div class="flex items-center justify-between mt-4">
                    <div>
                        <a class="flex items-center" href="#">
                            <img class="hidden object-cover w-10 h-10 mr-3 rounded-full sm:block"
                                src="https://cdn.pixabay.com/photo/2018/03/26/20/49/tiger-3264048_1280.jpg" alt="avatar">
                            <h1 class="font-bold text-gray-700">{{ issue.communityUsersDTO.nickname }}</h1>
                        </a>
                    </div>
                    <router-link class="mr-2 font-bold text-sub-grn hover:underline hover:text-main-grn"
                        :to='`/community/detail/${issue.id}`'>Read more</router-link>
                </div>
            </div>
            <fwb-pagination class="py-10" @click="move" v-model="currentPage" :total-items=totalItems></fwb-pagination>
        </div>
    </div>
</template>
<script setup>
import { FwbBadge } from 'flowbite-vue';
import { FwbPagination } from 'flowbite-vue';
import { ref, onMounted } from 'vue';
import { initFlowbite } from 'flowbite'
import axios from "axios";
const currentPage = ref(1);
const totalPages = ref(0);
const totalItems = ref(0);
const page = ref({});
const solvedIssue = ref(null);
const pageNo = ref(0);


// move 를 search 일때와 그냥 일때 따로 인자값을 줘야 하나??

const move = async (pageNum) => {
    pageNo.value = Number(pageNum.target.innerText);
    getSolvedIssueList(pageNo);
}

const searchIssue = async () => {
    let search = document.querySelector('#searchInput').value;
    // category 에따른 검색도 구현해야 함
    const url = '/community/solvedissue/search?title=' + search;
    const resp = await axios.get(url);
    console.log(resp);
    if (resp.status === 200) {
        solvedIssue.value = resp.data.content;
    } else {
        alert("게시물을 불러오는데 실패하였습니다.");
    }
}

async function getSolvedIssueList(pageNo) {
    if (pageNo != null) {
        pageNo.value = pageNo.value - 1;
        await axios
            .get(
                "/community/solvedissue?pageNo=" + (pageNo.value)
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
                "/community/solvedissue"
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

onMounted(() => {
    initFlowbite();
    getSolvedIssueList();
});

</script>
