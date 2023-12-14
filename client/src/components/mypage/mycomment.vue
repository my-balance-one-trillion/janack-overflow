<template>
  <h3 class="text-3xl text-main-red font-bold mb-7">내가 등록한 댓글 목록</h3>
  <div class="border-b-4 border-main-red"></div>
  <p class="text-xl my-4">댓글 목록</p>
  <p class="text-[#919090]">작성한 댓글을 확인할 수 있습니다.</p>

  <article class="">
    <div
      class="w-full xl:w-8/12 m-12 xl:mb-0 px-4 mx-auto mt-2 flex flex-col items-center"
    >
      <div
        class="relative flex flex-col min-w break-words bg-white w-full my-12 shadow-lg rounded"
      >
        <div class="block overflow-x-auto">
          <table class="min-w-full">
            <thead class="bg-white border-b">
              <tr>
                <th
                  scope="col"
                  class="text-sm font-medium text-gray-900 px-6 py-4 text-left w-5/12"
                >
                  댓글내용
                </th>
                <th
                  scope="col"
                  class="text-sm font-medium text-gray-900 px-6 py-4 text-left w-2/12"
                >
                  원본글
                </th>
                <th
                  scope="col"
                  class="text-sm font-medium text-gray-900 px-6 py-4 text-left w-2/12"
                >
                  작성일
                </th>
                <th
                  scope="col"
                  class="text-sm font-medium text-gray-900 px-6 py-4 text-left w-2/12"
                >
                  삭제하기
                </th>
              </tr>
            </thead>
            <tbody>
              <tr class="border-b" v-for="item in myCommentList.content">
                <td
                  class="text-sm text-gray-900 font-light px-6 py-4 whitespace-nowrap"
                >
                  {{ item.comment }}
                </td>
                <td
                  class="text-sm text-gray-900 font-light px-6 py-4 whitespace-nowrap"
                >
                  {{ item.issue_title }}
                </td>
                <td
                  class="text-sm text-gray-900 font-light px-6 py-4 whitespace-nowrap"
                >
                  {{ item.createdAt }}
                </td>
                <td
                  class="text-sm text-gray-900 font-light px-6 py-4 whitespace-nowrap"
                >
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
            <a
              href="#"
              class="flex items-center justify-center px-3 h-8 ms-0 leading-tight text-gray-500 bg-white border border-e-0 border-gray-300 rounded-s-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
              >Previous</a
            >
          </li>
          <li>
            <a
              href="#"
              class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
              >1</a
            >
          </li>
          <li>
            <a
              href="#"
              class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
              >2</a
            >
          </li>
          <li>
            <a
              href="#"
              aria-current="page"
              class="flex items-center justify-center px-3 h-8 text-main-red border border-gray-300 bg-red-100 hover:bg-red-200 hover:text-hover-red dark:border-gray-700 dark:bg-gray-700 dark:text-white"
              >3</a
            >
          </li>
          <li>
            <a
              href="#"
              class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
              >4</a
            >
          </li>
          <li>
            <a
              href="#"
              class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
              >5</a
            >
          </li>
          <li>
            <a
              href="#"
              class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 rounded-e-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
              >Next</a
            >
          </li>
        </ul>
      </nav>
    </div>
  </article>
</template>
<script setup>
import { ref } from 'vue';
import axios from 'axios';
import { useAuthStore } from "@/stores/auth";

const myCommentList = ref([]);

// ----------------------------
// 댓글 불러오기
// ----------------------------

getMyCommentList();
async function getMyCommentList(){
  const response = await axios.get("/mypage/mycomment",{
    headers:{
      "Authorization": useAuthStore().token
    }
  });
  myCommentList.value = response.data;
  console.log(myCommentList.value.content)
}

// ----------------------------
// 댓글 지우기
// ----------------------------

async function deleteMyComment(commentid){
  let deleteConfirm = window.confirm("정말 삭제하시겠습니까?");
  if(deleteConfirm){
    await axios.delete(`/mypage/mycomment/${commentid}`,{
      headers:{
        "Authorization":useAuthStore().token
      }
    });
    getMyCommentList();
  }else{
    return;
  }
}

</script>
<style scoped></style>
