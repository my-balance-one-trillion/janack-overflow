<template>
  <h3 class="text-3xl text-main-red font-bold mb-7">내가 등록한 에러 목록</h3>
  <div class="border-b-4 border-main-red"></div>
  <p class="text-xl my-4">에러 목록</p>
  <p class="text-[#919090]">작성한 에러를 확인할 수 있습니다.</p>
  <article class="">
    <div class="w-full xl:w-11/12 m-12 xl:mb-0 px-4 mx-auto mt-2 flex flex-col items-center">
      <div class="relative flex flex-col min-w break-words bg-white w-full my-12 shadow-lg">
        <div class="block overflow-x-auto">
          <table class="min-w-full">
            <thead class="bg-white border-b">
              <tr>
                <th scope="col" class="text-m font-medium text-main-red px-4 py-4 text-left ">
                  에러명
                </th>
                <th scope="col" class="text-m font-medium text-main-red px-4 py-4 text-left">
                  등록일
                </th>
                <th scope="col" class="text-m font-medium text-main-red px-4 py-4 text-center">
                  카테고리
                </th>
                <th scope="col" class="text-m font-medium text-main-red px-4 py-4 text-center">
                  조회수
                </th>
                <th scope="col" class="text-m font-medium text-main-red px-4 py-4 text-center">
                  해결여부
                </th>
              </tr>
            </thead>
            <tbody>
              <tr class="bg-white border-b" v-for="item in issueList">

                <td class="text-m text-gray-900 font-light px-6 py-4 line-clamp-1 w-64 h-9">
                  {{ item.title }}
                </td>
                <td class="text-m text-gray-900 font-light px-6 py-4 whitespace-nowrap">
                  {{ item.createdAt }}

                </td>
                <td class="text-m text-gray-900 font-light px-6 py-4 whitespace-nowrap text-center">
                  {{ item.category }}
                </td>
                <td class="text-m text-gray-900 font-light px-6 py-4 whitespace-nowrap text-center">
                  {{ item.views }}
                </td>
                <td class="text-m text-gray-900 font-light px-6 py-4 whitespace-nowrap text-center">
                  <span v-if="item.status == '01'" class="bg-main-red text-white rounded-lg p-1"> 진행중</span>
                  <span v-if="item.status == '02'" class="bg-yellow-300 text-white rounded-lg p-1"> 포기</span>
                  <span v-if="item.status == '03'" class="bg-main-grn text-white rounded-lg p-1"> 해결</span>
                </td>
              </tr>

            </tbody>
          </table>
        </div>
      </div>
      <nav aria-label="Page navigation example">
        <ul class="inline-flex -space-x-px text-sm">
          <li>
            <div
              class="flex items-center justify-center px-3 h-8 ms-0 leading-tight text-gray-500 bg-white border border-e-0 border-gray-300 rounded-s-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white cursor-pointer"
              @click="pageForward(pageSet.page)">
              Previous</div>
          </li>
          <li v-for="i in pageInt">
            <div
              class="flex items-center justify-center px-3 h-8 leading-tight text-main-red bg-red-200 border border-gray-300 hover:bg-red-200 hover:text-hover-red dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
              v-if="pageSet.page == i + 1">
              {{ i + 1 }}</div>
            <div
              class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white cursor-pointer"
              @click="getIssueList(i)" v-else>
              {{ i + 1 }}</div>
          </li>

          <li>
            <div
              class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 rounded-e-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white cursor-pointer"
              @click="pageNext(pageSet.page)">
              Next</div>
          </li>
        </ul>
      </nav>
    </div>
  </article>
</template>
<script setup>
import { reactive, ref } from 'vue';
import axios from 'axios';
import { useAuthStore } from "@/stores/auth";

const issueList = ref([]);
const pageSet = ref([]);
let pageInt = reactive([]);

getIssueList(0);
async function getIssueList(i) {
  try{
    const response = await axios.get(`/mypage/myissue?page=${i}&size=5`, {
      headers: {
        "Authorization": useAuthStore().token
      }
    });
    const { data, pageDTO, pageNumber } = response.data;
    issueList.value = data;
    pageSet.value = pageDTO;
    pageInt = pageNumber;
  }catch(error){
    alert("사용자의 글을 불러올수 없습니다.");
    router.push('/mypage');
  }
}

function pageForward(i) {
  try{
    let warpForward = i - 6;
    if (warpForward <= 0) {
      getIssueList(0);
    } else {
      getIssueList(warpForward);
  
    }
  }catch(error){
    router.push('/error');
  }
}

function pageNext(i) {
  try{
    let warpNext = i + 4;
    if (warpNext > pageSet.value.totalPages - 1) {
      getIssueList(pageSet.value.totalPages - 1);
    } else {
      getIssueList(warpNext);
    }
  }catch(error){
    router.push('/error');
  }
}

</script>
<style scoped></style>
