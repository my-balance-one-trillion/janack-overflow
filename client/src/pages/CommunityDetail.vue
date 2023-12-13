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
                    <h1 class="py-4 text-3xl font-bold">Vue.js 에 Tailwind 적용시키기 안될 때</h1>
                    <div class="flex justify-between mb-4">
                        <div class="flex">
                            <fwb-badge class="px-2 mx-1 font-bold text-black rounded bg-badge-bg">Language</fwb-badge>
                            <fwb-badge class="px-2 mx-1 font-bold text-black rounded bg-badge-bg">Vue.js</fwb-badge>
                        </div>
                        <div class="w-fit">
                            <fwb-badge
                                class="px-4 mx-1 text-lg font-bold text-white rounded-full bg-main-red">Syntax</fwb-badge>
                        </div>
                    </div>
                    <p class="">
                        Lorem ipsum dolor sit amet consectetur adipisicing elit. Quibusdam repellendus et fugit
                        alias modi in
                        earum id reiciendis ratione debitis perferendis, natus quis eos vel explicabo maxime distinctio
                        tenetur animi!
                    </p>
                    <div>
                        <div class="p-3 border-0 rounded-xl bg-bg-grey">
                            <div class="flex justify-end">
                                <i :class="['p-2', 'fa-lg', 'fa-clipboard', 'copyButton', 'hover:cursor-pointer', hovered ? 'fa-solid' : 'fa-regular']"
                                    @click="copyToClipboard" @mouseover="hovered = true" @mouseout="hovered = false"></i>
                                <!-- <i class=" fa-solid fa-copy"></i> -->
                            </div>
                            <code v-highlightjs class="javascript" ref="codeBlock">const s = new Date().toString()</code>
                        </div>
                    </div>
                </div>

                <div v-show="activeTab === 1" class="w-full">
                    Lorem, ipsum dolor sit amet consectetur adipisicing elit. Consectetur iste aliquid harum blanditiis
                    beatae sint fuga nam neque deleniti quod nesciunt, animi vitae maiores dolor ullam minus suscipit, ab
                    deserunt?
                </div>
            </div>
        </div>

        <!-- 좋아요 -->
        <div class="flex items-center justify-end w-full p-2 my-3">
            <i :class="['fa-thumbs-up', 'fa-lg', 'hover:cursor-pointer', likeHovered ? 'fa-solid' : 'fa-regular']" @click=""
                @mouseover="likeHovered = true" @mouseout="likeHovered = false"><span class="pl-2">55</span></i>
        </div>

        <!-- 미디엄 추천 컨테이너 -->
        <h1 class="my-2 text-2xl">연관 미디엄 게시물</h1>
        <div class="grid w-full h-full grid-cols-1 gap-4 px-2 py-4 md:h-auto md:grid-cols-3">
            <!--item 1-->
            <div class="w-full mb-6 select-none">
                <div class="relative pb-64">
                    <a href="#" class="cursor-pointer">
                        <img class="absolute object-cover w-full h-full border-b-4 rounded-lg shadow-md cursor-pointer border-main-grn"
                            src="https://miro.medium.com/v2/resize:fit:720/format:webp/1*Z91sIw81eeIsjEnLEtseaQ.jpeg"
                            alt="medium thumnail" />
                    </a>
                </div>
                <div class="relative w-full px-4 -mt-20">
                    <div class="bg-white border rounded-lg shadow-lg">
                        <div class="p-5">
                            <div class="flex items-center justify-between">
                                <div class="text-xs opacity-75">
                                    <span>
                                        2023-12-11
                                    </span>
                                </div>
                                <i class="fa-solid text-main-grn fa-hands-clapping fa-md"><span
                                        class="pl-2 text-xs">533</span></i>
                            </div>

                            <a class="block mt-2 text-lg font-medium text-gray-800 truncate hover:underline" href="#">
                                Best Practices: Entity Class Design with JPA and Spring Boot
                            </a>
                        </div>
                        <div class="flex flex-col justify-between w-full px-4 pb-3">
                            <div class="w-fit">
                                <div class="text-gray-800 w-fit">
                                    <p class="overflow-hidden text-sm opacity-75 h-14 w-fit text-ellipsis">
                                        "Who am I and what do I do …""Who am I and what do I do …""Who am I and what do I do
                                        …"
                                    </p>
                                </div>
                            </div>
                            <div class="mt-4">
                                <a class="flex items-center justify-end text-xs text-main-red btn-link hover:underline"
                                    href="#">
                                    Read More &rarr;
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- item 2 -->
            <div class="w-full mb-6 select-none">
                <div class="relative pb-64">
                    <a href="#" class="cursor-pointer">
                        <img class="absolute object-cover w-full h-full border-b-4 rounded-lg shadow-md cursor-pointer border-main-grn"
                            src="https://miro.medium.com/v2/resize:fit:720/format:webp/1*n_C92boSzKzBueoLloRdwQ.png"
                            alt="medium thumnail" />
                    </a>
                </div>
                <div class="relative w-full px-4 -mt-20">
                    <div class="bg-white border rounded-lg shadow-lg">
                        <div class="p-5">
                            <div class="flex items-center justify-between">
                                <div class="text-xs opacity-75">
                                    <span>
                                        2023-12-11
                                    </span>
                                </div>
                                <i class="fa-solid text-main-grn fa-hands-clapping fa-md"><span
                                        class="pl-2 text-xs">533</span></i>
                            </div>

                            <a class="block mt-2 text-lg font-medium text-gray-800 truncate hover:underline" href="#">
                                Best Practices: Entity Class Design with JPA and Spring Boot
                            </a>
                        </div>
                        <div class="flex flex-col justify-between w-full px-4 pb-3">
                            <div class="w-fit">
                                <div class="text-gray-800 w-fit">
                                    <p class="overflow-hidden text-sm opacity-75 h-14 w-fit text-ellipsis">
                                        "Who am I and what do I do …""Who am I and what do I do …""Who am I and what do I do
                                        …"
                                    </p>
                                </div>
                            </div>
                            <div class="mt-4">
                                <a class="flex items-center justify-end text-xs text-main-red btn-link hover:underline"
                                    href="#">
                                    Read More &rarr;
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--item 3-->
            <div class="w-full mb-6 select-none">
                <div class="relative pb-64">
                    <a href="#" class="cursor-pointer">
                        <img class="absolute object-cover w-full h-full border-b-4 rounded-lg shadow-md cursor-pointer border-main-grn "
                            src="https://miro.medium.com/v2/resize:fit:640/format:webp/0*ekTVGy6aYCrLyabo.png"
                            alt="medium thumnail" />
                    </a>
                </div>
                <div class="relative w-full px-4 -mt-20">
                    <div class="bg-white border rounded-lg shadow-lg">
                        <div class="p-5">
                            <div class="flex items-center justify-between">
                                <div class="text-xs opacity-75">
                                    <span>
                                        2023-12-11
                                    </span>
                                </div>
                                <i class="fa-solid text-main-grn fa-hands-clapping fa-md"><span
                                        class="pl-2 text-xs">533</span></i>
                            </div>

                            <a class="block mt-2 text-lg font-medium text-gray-800 truncate hover:underline" href="#">
                                Best Practices: Entity Class Design with JPA and Spring Boot
                            </a>
                        </div>
                        <div class="flex flex-col justify-between w-full px-4 pb-3">
                            <div class="w-fit">
                                <div class="text-gray-800 w-fit">
                                    <p class="overflow-hidden text-sm opacity-75 h-14 w-fit text-ellipsis">
                                        "Who am I and what do I do …""Who am I and what do I do …""Who am I and what do I do
                                        …"
                                    </p>
                                </div>
                            </div>
                            <div class="mt-4">
                                <a class="flex items-center justify-end text-xs text-main-red btn-link hover:underline"
                                    href="#">
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
                <div class="flex">
                    <div class="flex-shrink-0 mr-3">
                        <img class="w-8 h-8 mt-2 rounded-full sm:w-10 sm:h-10"
                            src="https://cdn.pixabay.com/photo/2018/04/13/21/24/lion-3317670_1280.jpg" alt="">
                    </div>
                    <div class="flex-1 px-4 py-2 leading-relaxed border-b-4 rounded-lg border-sub-red sm:px-6 sm:py-4">
                        <strong>Sarah</strong> <span class="text-xs text-gray-400">3:34 PM</span>
                        <p class="text-sm">
                            Lorem ipsum dolor sit amet, consetetur sadipscing elitr,
                            sed diam nonumy eirmod tempor invidunt ut labore et dolore
                            magna aliquyam erat, sed diam voluptua.
                        </p>
                    </div>
                </div>
                <div class="flex">
                    <div class="flex-shrink-0 mr-3">
                        <img class="w-8 h-8 mt-2 rounded-full sm:w-10 sm:h-10"
                            src="https://cdn.pixabay.com/photo/2019/09/04/22/59/panda-4452700_1280.jpg" alt="">
                    </div>
                    <div class="flex-1 px-4 py-2 leading-relaxed border-b-4 rounded-lg border-sub-red sm:px-6 sm:py-4">
                        <strong>Sarah</strong> <span class="text-xs text-gray-400">3:34 PM</span>
                        <p class="text-sm">
                            Lorem ipsum dolor sit amet, consetetur sadipscing elitr,
                            sed diam nonumy eirmod tempor invidunt ut labore et dolore
                            magna aliquyam erat, sed diam voluptua.
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
                            <fwb-button type="submit" color="red">
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
import { onMounted, ref } from 'vue';
import { FwbBadge } from 'flowbite-vue';
import { FwbCard } from 'flowbite-vue'
import { FwbButton, FwbTextarea } from 'flowbite-vue'
import ClipboardJS from 'clipboard';
import javascript from 'highlight.js/lib/languages/javascript';
import 'highlight.js/styles/default.css';
import HighlightJS from 'highlight.js';
import axios from 'axios';
import { useRoute } from 'vue-router';
import { func } from 'prop-types';

const activeTab = ref(0);

const tabs = ref([
    "에러",
    "해결",
]);

const codeBlock = ref(null);
const hovered = ref(false);
const likeHovered = ref(false);
let clipboardInstance = null; // ClipboardJS 인스턴스를 저장할 변수


const route = useRoute();
const issue = ref(null);

async function getIssueDetail() {
    console.log(route.params);
    await axios
        .get(
            "http://localhost:8081/community/detail/" + route.params.id
        )
        .then((response) => {
            console.log(response);
            // issue.value = 
        });
} 

onMounted(() => {
    HighlightJS.registerLanguage('javascript', javascript);
    HighlightJS.highlightElement(codeBlock.value);

    getIssueDetail();

    // ClipboardJS 인스턴스를 생성하고 이벤트 핸들러를 등록합니다.
    clipboardInstance = new ClipboardJS('.copyButton', {
        text: () => {
            return codeBlock.value.innerText;
        },
    });

    clipboardInstance.on('success', (e) => {
        console.log(e.text);
        alert('복사되었습니다.');
    });

    clipboardInstance.on('error', (e) => {
        console.log(e);
    });
});

</script>