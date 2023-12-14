<template>
    <div class="flex flex-col w-10/12 mx-auto">
        <!-- 에러 / 해결 제목박스 -->
        <div class="flex items-center justify-center w-full p-2 mt-10 text-3xl text-center rounded-xl bg-bg-grey">
            <div :class="activeTab === 0 ? 'bg-main-red text-white font-bold border-b-4 border-white' : 'font-bold text-white hover:bg-hover-red hover:ease-in hover:transition hover:duration-150'"
                @click="activeTab = 0" class="w-3/6 py-2 mx-1 border-0 cursor-pointer rounded-xl">
                {{ tabs[0] }}
            </div>
            <div :class="activeTab === 1 ? 'bg-main-grn text-white font-bold border-b-4 border-white' : 'font-bold text-white hover:bg-hover-grn hover:ease-in hover:transition hover:duration-150'"
                @click="activeTab = 1" class="w-3/6 py-2 mx-1 border-0 cursor-pointer rounded-xl">
                {{ tabs[1] }}
            </div>
        </div>

        <!--본문-->
        <div class="flex items-center justify-center mt-2 w-12/12 font-sub">
            <div class="w-full px-8 py-5 mx-auto my-4 text-lg bg-white rounded-lg shadow-md">
                <div v-show="activeTab === 0" class="w-full">
                    <h1 class="py-4 text-3xl font-bold">{{ issue.title }}</h1>
                    <div class="flex justify-between mb-4">
                        <div class="flex">
                            <fwb-badge v-for="keyword of keywords" class="px-2 mx-1 text-lg font-bold text-black rounded bg-badge-bg">{{ keyword
                            }}</fwb-badge>
                        </div>
                        <div class="w-fit">
                            <fwb-badge class="px-4 mx-1 text-lg font-bold text-white rounded-full bg-main-red">{{
                                issue.category }}</fwb-badge>
                        </div>
                    </div>
                    <p class="">
                        {{ issue.content }}
                    </p>
                    <div>
                        <div class="p-3 border-0 rounded-xl bg-bg-grey">
                            <div class="flex justify-end">
                                <i :class="['p-2', 'fa-lg', 'fa-clipboard', 'issueCodeBlock', 'hover:cursor-pointer', hovered ? 'fa-solid' : 'fa-regular']"
                                    @click="copyToClipboard" @mouseover="hovered = true" @mouseout="hovered = false"></i>
                                <!-- <i class=" fa-solid fa-copy"></i> -->
                            </div>
                            <code class="java" ref="issueCodeBlock">{{ issue.code }}</code>
                        </div>
                    </div>
                </div>

                <div v-show="activeTab === 1" class="w-full">
                    <p>{{ solution.content }}</p>
                    <div>
                        <div class="p-3 border-0 rounded-xl bg-bg-grey">
                            <div class="flex justify-end">
                                <i :class="['p-2', 'fa-lg', 'fa-clipboard', 'solutionCodeBlock', 'hover:cursor-pointer', hovered ? 'fa-solid' : 'fa-regular']"
                                    @click="copyToClipboard" @mouseover="hovered = true" @mouseout="hovered = false"></i>
                                <!-- <i class=" fa-solid fa-copy"></i> -->
                            </div>
                            <code class="javascript" ref="solutionCodeBlock">{{ solution.code }}</code>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 좋아요 -->
        <div class="flex items-center justify-end w-full p-2 my-3 space-x-3">
            <i :class="['fa-thumbs-up', 'fa-lg', 'hover:cursor-pointer', isLike ? 'fa-solid' : 'fa-regular']"
                @click="isLike ? cancleLikeAPI() : likeAPI()"><span class="pl-2">{{
                    likeCnt
                }}</span></i>
            <i class="fa-eye fa-lg fa-solid"><span class="pl-2">{{ issue.views
            }}</span></i>
        </div>

        <!-- 미디엄 추천 컨테이너 -->
        <h1 class="my-2 text-2xl">연관 미디엄 게시물</h1>
        <div class="grid w-full h-full grid-cols-1 gap-4 px-2 py-4 md:h-auto md:grid-cols-3">
            <!--item 1-->
            <div v-for="article in issue.articleList" class="w-full mb-6 select-none">
                <div class="relative pb-64">
                    <a :href="article.url" class="cursor-pointer">
                        <img class="absolute object-cover w-full h-full border-b-4 rounded-lg shadow-md cursor-pointer border-main-grn"
                            :src="article.imgUrl" alt="medium thumnail" />
                    </a>
                </div>
                <div class="relative w-full px-4 -mt-20">
                    <div class="bg-white border rounded-lg shadow-lg">
                        <div class="p-5">
                            <div class="flex items-center justify-between">
                                <div class="text-xs opacity-75">
                                    <span>
                                        {{ article.publishedAt }}
                                    </span>
                                </div>
                                <i class="fa-solid text-main-grn fa-hands-clapping fa-md"><span class="pl-2 text-xs">{{
                                    article.claps }}</span></i>
                            </div>

                            <a class="block mt-2 text-lg font-medium text-gray-800 truncate hover:underline"
                                :href="article.url">
                                {{ article.title }}
                            </a>
                        </div>
                        <div class="flex flex-col justify-between w-full px-4 pb-3">
                            <div class="w-fit">
                                <div class="text-gray-800 w-fit">
                                    <p class="overflow-hidden text-sm opacity-75 h-14 w-fit text-ellipsis">
                                        {{ article.subTitle }}
                                    </p>
                                </div>
                            </div>
                            <div class="mt-4">
                                <a class="flex items-center justify-end text-xs text-main-red btn-link hover:underline"
                                    :href="article.url">
                                    Read More &rarr;
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--댓글 박스-->
        <div class="my-4">
            <h1 class="text-2xl">댓글</h1>
        </div>
        <div class="flex justify-end">
            <div class="w-11/12 space-y-4">
                <div class="flex" v-for="comment in commentList">
                    <div class="flex-shrink-0 mr-3">
                        <img class="w-8 h-8 mt-2 rounded-full sm:w-10 sm:h-10"
                            src="https://cdn.pixabay.com/photo/2018/04/13/21/24/lion-3317670_1280.jpg" alt="">
                    </div>
                    <div class="flex-1 px-4 py-2 leading-relaxed border-b-4 rounded-lg border-sub-red sm:px-6 sm:py-4">
                        <strong>{{ comment.nickname }}</strong> <span class="text-xs text-gray-400">{{ comment.createdAt
                        }}</span>
                        <p class="text-sm">
                            {{ comment.comment }}
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <div class="flex justify-end mt-10">
            <form class="w-7/12">
                <fwb-textarea v-model="message" :rows="3" custom label="" placeholder="댓글을 작성해 주세요.">
                    <template #footer>
                        <div class="flex items-center justify-end">
                            <fwb-button type="button" @click="commentAPI" color="red">
                                댓글 작성
                            </fwb-button>
                        </div>
                    </template>
                </fwb-textarea>
            </form>
        </div>
    </div>
</template>

<script setup>
import { onMounted, ref, watchEffect } from 'vue';
import { createStore, useStore } from 'vuex';
import { FwbBadge } from 'flowbite-vue';
import { FwbCard } from 'flowbite-vue'
import { FwbButton, FwbTextarea } from 'flowbite-vue'
import ClipboardJS from 'clipboard';

import java from 'highlight.js/lib/languages/java';
import 'highlight.js/styles/default.css';
import hljs from 'highlight.js/lib/core';

import axios from 'axios';
import { useRoute } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import { storeToRefs } from 'pinia';

const authStore = useAuthStore()
const { userInfo } = storeToRefs(authStore);

// const authStore = ref();
// const userInfo = ref({});

const activeTab = ref(0);

const tabs = ref([
    "에러",
    "해결",
]);

const issueCodeBlock = ref(null);
const solutionCodeBlock = ref(null);
const hovered = ref(false);
const likeHovered = ref(false);
// TODO keywords split 해서 저장
const keywords = ref([]);

const route = useRoute();
const issue = ref({});
const solution = ref({});
const commentList = ref(null);
const isLike = ref(false);
const likeCnt = ref(0);
const message = ref(null);

async function getIssueDetail() {
    await axios
        .get(
            "/community/detail/" + route.params.id
        )
        .then((response) => {
            console.log(response.data);
            issue.value = response.data;
            solution.value = response.data.solutionDTO;
            issueCodeBlock.value = response.data.code;
            solutionCodeBlock.value = response.data.solutionDTO.code;
            commentList.value = response.data.commentResponseDtoList;
            keywords.value = response.data.keyword.split(',').map(keyword => keyword.trim());
        });
}

async function getLikesCnt() {
    await axios
        .get(
            "/community/likes/" + route.params.id
        )
        .then((response) => {
            if (response.status === 200) {
                console.log("likesCnt : " + response.data);
                likeCnt.value = response.data;
            } else {
                alert("좋아요 갯수를 불러오는데 실패하였습니다.");
            }
        })
}

const getCommentListAPI = async () => {
    const url = "/community/comment/" + route.params.id;
    const resp = await axios.get(url);
    console.log(resp);
    if (resp.status === 200) {
        commentList.value = resp.data;
    } else {
        alert("댓글 불러오는데 실패하였습니다.");
    }
}

const commentAPI = async () => {
    console.log(message);
    const url = `/community/comment/${route.params.id}/${userInfo.value.id}`;
    console.log(url);
    let data = {
        content: message.value,
    };
    const resp = await axios.post(url, data);
    console.log(resp);
    if (resp.status === 200) {
        getCommentListAPI();
    } else {
        alert("댓글 작성에 실패하였습니다.");
    }
}

let clipboardInstance = null; // ClipboardJS 인스턴스를 저장할 변수

function copyToClipboard(event) {
    const className = '.' + event.target.classList[3];
    console.log(className);

    let targetElement = null;
    if (className === '.solutionCodeBlock') {
        targetElement = solutionCodeBlock.value;
    } else {
        targetElement = issueCodeBlock.value;
    }

    if (clipboardInstance) {
        clipboardInstance.destroy();
    }

    clipboardInstance = new ClipboardJS(className, {
        text: function () {
            return targetElement.innerText;
        }
    });

    clipboardInstance.on('success', function (e) {
        alert('텍스트가 클립보드에 복사되었습니다.');
    });

    clipboardInstance.on('error', function (e) {
        console.error('클립보드 복사 중 오류가 발생했습니다:', e);
    });

}

const getLikesAPI = async () => {
    // console.log(userInfo.value);
    const url = `/community/likes/${route.params.id}/${userInfo.value.id}`; //usersId

    try {
        const resp = await axios.get(url);
        console.log(resp);
        if (resp.status === 200) {
            isLike.value = true;

        } else if (resp.status === 204) {
            isLike.value = false;
        }
    } catch (error) {
        // 에러 발생 시 처리
        console.log('API 요청 중 에러가 발생하였습니다.');
        console.error(error);
    }

}

const likeAPI = async () => {
    console.log(isLike.value);
    const url = `/community/likes/${route.params.id}/${userInfo.value.id}`;
    const resp = await axios.post(url);

    console.log(resp);

    if (resp.status === 200) {

        isLike.value = true;
        // store.commit('addLike', route.params.id); // 좋아요 상태를 추가하는 변이 호출
        getLikesCnt();
    } else {
        alert('좋아요에 실패하였습니다.');
    }
};

const cancleLikeAPI = async () => {
    console.log(isLike.value);
    const url = `/community/likes/${route.params.id}/${userInfo.value.id}`;
    const resp = await axios.delete(url);
    console.log(resp);
    if (resp.status === 200) {
        // 좋아요 취소
        isLike.value = false;
        // store.commit('removeLike', route.params.id);
        getLikesCnt();
    } else {
        alert('댓글 작성에 실패하였습니다.');
    }
};

onMounted(() => {
    getLikesAPI();
    getIssueDetail();
    hljs.highlightAll();
});

</script>