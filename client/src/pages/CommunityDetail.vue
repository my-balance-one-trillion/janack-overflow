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
                    <h1 class="py-4 text-3xl font-bold break-words">{{ issue.title }}</h1>
                    <div class="flex justify-between mb-4">
                        <div class="flex">
                            <fwb-badge v-for="keyword of keywords"
                                class="px-2 mx-1 text-lg font-bold text-black rounded bg-badge-bg">{{ keyword
                                }}</fwb-badge>
                        </div>
                        <div class="w-fit">
                            <fwb-badge class="px-4 mx-1 text-lg font-bold text-white rounded-full bg-main-red">{{
                                issue.category }}</fwb-badge>
                        </div>
                    </div>
                    <p class="w-full break-all" v-html="issue.content">

                    </p>
                    <div class="flex flex-col">
                        <Monaco :code="issue.code"></Monaco>
                    </div>
                </div>

                <div v-show="activeTab === 1" class="w-full">
                    <p class="w-full break-all" v-html="solution.content"></p>
                    <div>
                        <Monaco :code="solutionCode"></Monaco>
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
        <div class="flex flex-col items-center justify-center p-3" v-if="!articleList">
            <p class="font-bold text-md">게시물을 불러오는 중입니다...</p>
            <Spinner></Spinner>
        </div>
        <div v-else-if="articleLength === 0" class="text-center">연관 게시물이 없습니다.</div>
        <div v-else class="grid w-full h-full grid-cols-1 gap-4 px-2 py-4 md:h-auto md:grid-cols-3">
            <!--item 1-->
            <div v-for="article in articleList" class="w-full mb-6 select-none">
                <div class="relative pb-64">
                    <a :href="article.url" target='_blank' class="cursor-pointer">
                        <img v-if="!article.imgUrl"
                            class="absolute object-cover w-full h-full border-b-4 rounded-lg shadow-md cursor-pointer border-main-grn"
                            src="../../public/images/errorImg.jpeg" alt="medium thumnail" />
                        <img v-if="article.imgUrl"
                            class="absolute object-cover w-full h-full border-b-4 rounded-lg shadow-md cursor-pointer border-main-grn"
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

                            <a class="block mt-2 text-lg font-medium text-gray-800 truncate line-clamp-1 hover:underline"
                                :href="article.url" target='_blank'>
                                {{ article.title }}
                            </a>
                        </div>
                        <div class="flex flex-col justify-between w-full px-4 pb-3">
                            <div class="w-fit">
                                <div class="text-gray-800 w-fit">
                                    <p class="overflow-hidden text-sm opacity-75 line-clamp-2 h-14 w-fit text-ellipsis">
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
        <template v-if="useAuthStore().token != null">
            <div v-if="commentListLength === 0" class="py-2 text-center text-main-red">아직 댓글이 없습니다. 댓글을 작성해주세요!</div>
        </template>
        <template v-if="useAuthStore().token === null">
            <div class="flex flex-col items-center justify-center mx-auto">
                <p class="pb-2 text-gray-400">로그인이 필요한 서비스입니다!</p>
                <div class="flex items-center justify-center w-40 h-12 mx-auto b animate-pulse">
                    <div
                        class="absolute items-center h-10 overflow-hidden transition duration-300 ease-out transform shadow-2xl cursor-pointer w-36 bg-main-red i rounded-xl hover:scale-x-110 hover:scale-y-105">
                    </div>
                    <router-link to='/login' class="z-10 font-semibold text-center text-white">로그인 하러가기</router-link>
                </div>
            </div>
        </template>
        <div v-if="commentListLength != 0" class="flex items-center justify-end text-center text-main-red">
            <fwb-pagination v-model="currentPage" :layout="'table'" :per-page="5" :total-items="totalCommentElements"
                class="mb-2" />
        </div>
        <div class="flex justify-end pb-5">
            <div class="w-10/12 space-y-4">
                <div class="flex" v-for="comment in commentList">
                    <div class="flex-shrink-0 mr-3">
                        <img class="w-8 h-8 mt-2 rounded-full sm:w-10 sm:h-10" :src="'/images/' + comment.profileImage"
                            alt="">
                    </div>
                    <div class="flex-1 px-4 py-2 leading-relaxed border-b-4 rounded-lg border-sub-red sm:px-6 sm:py-4">
                        <strong>{{ comment.nickname }}</strong> <span class="text-xs text-gray-400">{{ comment.createdAt
                        }}</span>
                        <p class="text-sm" v-html="comment.comment"></p>
                    </div>

                    <template v-if="useAuthStore().token != null">
                        <div class="flex items-center" v-if="comment.nickname === userInfo.nickname">
                            <button @click="deleteMyComment(comment.id)"
                                class="inline-flex items-center px-2 py-1 ml-2 font-bold text-gray-800 bg-white border-b-2 rounded shadow-md border-x-sub-red hover:border-sub-red hover:bg-hover-red hover:text-white">
                                <!-- <span class="mr-2">Delete</span> -->
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                                    <path fill="currentcolor"
                                        d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12 19 6.41z" />
                                </svg>
                            </button>
                        </div>
                    </template>
                </div>
            </div>

        </div>
        <template v-if="useAuthStore().token != null">
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
        </template>
    </div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue';
import { FwbBadge } from 'flowbite-vue';
import { FwbButton, FwbTextarea, FwbPagination } from 'flowbite-vue'
import Spinner from "@/components/Spinner.vue";
import Monaco from "@/components/Monaco.vue";

import axios from 'axios';
import { useRoute, useRouter, createWebHistory } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import { storeToRefs } from 'pinia';

const authStore = useAuthStore()
const { userInfo } = storeToRefs(authStore);
const router = useRouter();
const activeTab = ref(0);
const tabs = ref([
    "에러",
    "해결",
]);

const code = ref(null);
const solutionCode = ref(null);
const issueCodeBlock = ref(null);
const solutionCodeBlock = ref(null);
const hovered = ref(false);
const likeHovered = ref(false);
const keywords = ref([]);
const currentPage = ref(1);
const totalCommentElements = ref(0);
const route = useRoute();
const issue = ref({});
const articleLength = ref(0);
const solution = ref({});
const commentList = ref(null);
const commentListLength = ref(0);
const isLike = ref(false);
const likeCnt = ref(0);
const message = ref(null);
const lang = ref(null);
const codeStyle = ref(null);
const articleList = ref(null);
const closedIssueFlag = ref(false);


// console.log(userInfo.value.id);
watch(commentListLength, async (newValue, oldValue) => {
    console.log("newValue :" + newValue + "oldValue : " + oldValue);
    commentListLength.value = newValue;
    await getCommentListAPI();
});

watch(currentPage, async (newValue, oldValue) => {
    console.log("newValue :" + newValue + "oldValue : " + oldValue);
    currentPage.value = newValue;
    await getCommentListAPI();
});

async function formatLineBreaks(text) {
    console.log("!@!@@!@!@ : " + text);
    return text.split('\n').join('<br>');
}

async function handle(e) {
    code.value = issue.value.code;
    lang.value = e;
}

async function solutionCodehandle(e) {
    solutionCode.value = solution.value.code;
    lang.value = e;
}

async function getIssueDetail() {
    await axios
        .get(
            "/community/detail/" + route.params.id,
            {
                headers: {
                    Authorization: useAuthStore().token,
                },
            }
        )
        .then(async (response) => {
            console.log(response.data);
            issue.value = response.data;

            issue.value.content = await formatLineBreaks(issue.value.content);

            solution.value = response.data.solutionDTO;
            solution.value.content = await formatLineBreaks(solution.value.content);
            issueCodeBlock.value = response.data.code;
            code.value = response.data.code;
            solutionCodeBlock.value = response.data.solutionDTO.code;
            keywords.value = response.data.keyword.split(',').map(keyword => keyword.trim());
            solutionCode.value = response.data.solutionDTO.code;
            closedIssueFlag.value = true;
        })
        .catch((error) => {
            closedIssueFlag.value = false;
            alert("잘못된 접근입니다.");
            router.push('/community');
        });

    if (closedIssueFlag.value === false) {
        return;
    } else {
        getLikesCnt();
        getLikesAPI();
        getCommentListAPI();
        getArticelList();
    }
}

async function getArticelList() {
    await axios
        .get(
            `/community/solvedissue/${route.params.id}/articles`
        )
        .then((response) => {
            console.log(response.data);
            articleList.value = response.data;
            console.log(response.data.length);
            articleLength.value = response.data.length;
        })
        .catch((error) => {
            console.log(error);
            articleLength.value = 0;
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
    let pageNo = currentPage.value - 1;
    const url = `/community/comment/${route.params.id}?pageNo=` + pageNo;
    const resp = await axios.get(url);
    commentList.value = resp.data.content;
    console.log("샷 확인", resp.data)
    for (let i = 0; i < commentList.value.length; i++) {
        commentList.value[i].comment = await formatLineBreaks(commentList.value[i].comment);
    }

    totalCommentElements.value = resp.data.totalElements;
    commentListLength.value = commentList.value.length;
}

const commentAPI = async () => {
    if (message.value.trim() === '' || message.value == null) {
        alert('댓글을 입력해주세요.');
        return false;
    }

    const url = `/community/comment/${route.params.id}/${userInfo.value.id}`;

    let data = {
        content: message.value,
    };
    const resp = await axios.post(url, data, {
        headers: {
            authorization: useAuthStore().token,
        },
    });

    if (resp.status === 200) {

        getCommentListAPI();
        message.value = null;
    } else {
        alert("댓글 작성에 실패하였습니다.");
    }
}

async function deleteMyComment(commentid) {
    let deleteConfirm = window.confirm("댓글을 삭제하시겠습니까?");

    if (deleteConfirm) {
        await axios.delete(`/community/comment/${commentid}`, {
            headers: {
                "Authorization": useAuthStore().token
            }
        });
        getCommentListAPI();
    } else {
        return;
    }
}

const getLikesAPI = async () => {
    if (authStore.token === null) {
        return;
    }
    const url = `/community/likes/${route.params.id}/${userInfo.value.id}`; //usersId

    try {
        const resp = await axios.get(url, {
            headers: {
                authorization: useAuthStore().token,
            },
        });
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
    if (authStore.token == null) {
        alert("로그인이 필요한 서비스입니다.");
        router.push('/login');
        return;
    }
    // console.log(userInfo.value.id);
    // console.log("123123" + useAuthStore().token);
    const url = `/community/likes/${route.params.id}/${userInfo.value.id}`;
    const resp = await axios.post(url, {}, {
        headers: {
            authorization: authStore.token,
        },
    });

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
    const resp = await axios.delete(url, {
        headers: {
            authorization: useAuthStore().token,
        },
    });
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
    initFlowbite();
    getIssueDetail();

});

</script>
<style scoped>
.i::before {
    content: "";
    position: absolute;
    width: 0px;
    height: 0px;
    opacity: 20%;
    background: white;
    /*   background: #3B82F6; */
    /* Centering */
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    margin: auto;
}

.i:hover:before {
    animation: anim-in 0.7s forwards ease-out;
}

@keyframes anim-in {
    100% {
        opacity: 0%;
        border-radius: 0;
        width: 600px;
        height: 600px;
    }

    0% {
        width: 0px;
        height: 0px;
        border-radius: 100%;
        opacity: 20%;
    }
}
</style>