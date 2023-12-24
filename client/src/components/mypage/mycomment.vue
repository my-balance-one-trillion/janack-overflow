<template>
  <h3 class="text-3xl text-main-red font-bold mb-7">내가 등록한 댓글 목록</h3>
  <div class="border-b-4 border-main-red"></div>
  <p class="text-xl my-4">댓글 목록</p>
  <p class="text-[#919090]">작성한 댓글을 확인할 수 있습니다.</p>

  <article class="">
    <div class="w-full xl:w-11/12 m-12 xl:mb-0 px-4 mx-auto mt-2 flex flex-col items-center">
      <div class="relative flex flex-col min-w break-words bg-white w-full my-12 shadow-lg rounded">
        <div class="block overflow-x-auto">
          <table class="min-w-full">
            <thead class="bg-white border-b">
              <tr>
                <th scope="col" class="text-m font-medium text-gray-900 px-6 py-4 text-left w-4/12">
                  댓글내용
                </th>
                <th scope="col" class="text-m font-medium text-gray-900 px-6 py-4 text-left w-4/12">
                  원본글
                </th>
                <th scope="col" class="text-m font-medium text-gray-900 px-6 py-4 text-left w-2/12">
                  작성일
                </th>
                <th scope="col" class="text-m font-medium text-gray-900 px-6 py-4 text-left w-2/12">
                  삭제하기
                </th>
              </tr>
            </thead>
            <tbody>
              <tr class="border-b" v-for="item in commentList">
                <td class="text-m text-gray-900 font-light px-6 py-4 line-clamp-2 w-64 h-9">
                  {{ item.comment }}
                </td>
                <td class="text-m text-gray-900 font-light px-6 py-4 w-64 h-9 whitespace-nowrap">
                  {{ truncateText(item.issue_title)}}
                </td>
                <td class="text-m text-gray-900 font-light px-6 py-4 whitespace-nowrap">
                  {{ item.createdAt }}
                </td>
                <td class="text-m text-gray-900 font-light px-6 py-4 whitespace-nowrap">
                  <span class="text-main-red font-bold cursor-pointer" @click="deleteMyComment(item.id)">Delete</span>
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
              @click="getCommentList(i)" v-else>
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

const commentList = ref([]);
const pageSet = ref([]);
let pageInt = reactive([]);

// ----------------------------
// 댓글 불러오기
// ----------------------------

getCommentList(0);
async function getCommentList(i) {
  try{
    const response = await axios.get(`/mypage/mycomment?page=${i}&size=5`, {
      headers: {
        "Authorization": useAuthStore().token
      }
    });
    const { data, pageDTO, pageNumber } = response.data;
    commentList.value = data;
    pageSet.value = pageDTO;
    pageInt = pageNumber;
  }catch(error){
    alert("사용자의 댓글을 불러올수 없습니다.");
    router.push('/mypage');
  }
}

function pageForward(i) {
  try{
    let warpForward = i - 6;
    if (warpForward <= 0) {
      getCommentList(0);
    } else {
      getCommentList(warpForward);
      
    }
  }catch(error){
    router.push('/error');
  }
}

function pageNext(i) {
  try{
    let warpNext = i + 4;
    if (warpNext > pageSet.value.totalPages - 1) {
      getCommentList(pageSet.value.totalPages - 1);
    } else {
      getCommentList(warpNext);
    }
  }catch(error){
    router.push('/error');
  }
}

// ----------------------------
// 댓글 지우기
// ----------------------------

async function deleteMyComment(commentid) {
  let deleteConfirm = window.confirm("정말 삭제하시겠습니까?");
  if (deleteConfirm) {
    await axios.delete(`/mypage/mycomment/${commentid}`, {
      headers: {
        "Authorization": useAuthStore().token
      }
    });
    getCommentList();
  } else {
    return;
  }
}

// ----------------------------
// 댓글 지우기
// ----------------------------
function truncateText(text) {
  if (text.length > 20) {
    return text.substring(0, 20) + '...';
  }
  return text;
}

</script>
<style scoped></style>
