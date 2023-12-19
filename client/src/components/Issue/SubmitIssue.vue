<script setup>
import { ref, defineEmits, onMounted } from "vue";
import axios from "axios";
import { useAuthStore } from "@/stores/auth";
import Tagify from '@yaireo/tagify'
import '@yaireo/tagify/src/tagify.scss';
import { Codemirror } from "vue-codemirror";
import { java } from '@codemirror/lang-java'
import { html } from '@codemirror/lang-html'
import { nord, nordInit } from '@uiw/codemirror-theme-nord';

const issueInfo = ref({
    title: '',
    content: '',
    category: '',
    code: '',
    keyword: '',
});

const emit = defineEmits(['step-changed']);
const step = ref(1);

const tagInput = ref(null);
const extensions = [java(), nord]

onMounted(() => {
    const tagify = new Tagify(tagInput.value, {
        whitelist: ['java', 'spring', 'python'],
        maxTags: 3,
    });

    tagify.on('add', (e) => {
        // if (!tagify.settings.whitelist.includes(e.detail.data.value)) {
        //   tagify.removeTags(e.detail.tag);
        // }
        issueInfo.value.keyword = tagify.value.map(tag => tag.value).join(',');
    });
});


async function submitIssue() {
    await axios
        .post('/issues', issueInfo.value,
            {
                headers: {
                    Authorization: useAuthStore().token,
                }
            }
        )
        .then(() => {
            step.value = 2;
            emit('step-changed', step.value);
        })
        .catch((error) => {
            console.log(error);
        })
}

</script>

<template>
    <div class="flex items-center justify-center mt-2 w-12/12 font-sub">
        <div class="w-full px-8 py-5 mx-auto my-4 bg-white rounded-lg shadow-md">
            <!--  에러 title -->
            <input v-model="issueInfo.title"
                class="w-full my-5 text-2xl font-light text-gray-700 bg-transparent border-0 border-b-4 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-main-red"
                name="title" placeholder="에러를 한 문장으로 간결하게 요약해주세요." required type="text" />
            <!--에러 키워드, 카테고리-->
            <div class="flex justify-between mb-4">
                <div>
                    <input ref="tagInput" placeholder="키워드 (최대 3개)" type="text" />
                </div>
                <div>
                    <select v-model="issueInfo.category"
                        class="bg-gray-50 border-4 border-gray-300 text-gray-900 text-xl rounded-lg focus:ring-main-red focus:border-main-red block w-full p-2.5">
                        <option selected value="syntax">syntax</option>
                        <option value="language">language</option>
                        <option value="database">database</option>
                        <option value="os">os</option>
                    </select>
                </div>

            </div>
            <!--  에러 내용  -->
            <textarea v-model="issueInfo.content"
                class="w-full h-48 p-5 my-5 text-2xl font-light text-gray-700 bg-transparent border-4 border-gray-300 appearance-none resize-none rounded-2xl focus:outline-none focus:ring-0 focus:border-main-red"
                name="content" placeholder="에러에 대한 내용을 작성해주세요.&#13;&#10;발생 상황, 에러 메시지 등을 포함하여 설명해주세요." required
                type="text" />
            <!--에러 코드-->
            <div>
                <!-- <CodeEditor v-model="issueInfo.code"
                                :languages="[['java', 'Java'], ['python', 'Python'], ['javascript', 'Javascript']]" :line-nums="true"
                                theme="isbl-editor-dark" width="100%"></CodeEditor> -->
            </div>
        </div>
        <div>
            <select v-model="issueInfo.category"
                class="bg-gray-50 border-4 border-gray-300 text-gray-900 text-xl rounded-lg focus:ring-main-red focus:border-main-red block w-full p-2.5">
                <option selected value="syntax">syntax</option>
                <option value="language">language</option>
                <option value="database">database</option>
                <option value="os">os</option>
            </select>
        </div>

    </div>
    <!--  에러 내용  -->
    <textarea v-model="issueInfo.content"
        class="w-full h-48 p-5 my-5 text-2xl font-light text-gray-700 bg-transparent border-4 border-gray-300 appearance-none resize-none rounded-2xl focus:outline-none focus:ring-0 focus:border-main-red"
        name="content" placeholder="에러에 대한 내용을 작성해주세요.&#13;&#10;발생 상황, 에러 메시지 등을 포함하여 설명해주세요." required type="text" />
    <!--에러 코드-->
    <div>
        <!--        <code-editor v-model="issueInfo.code" class="text-lg" @update:modelValue="updateCode"></code-editor>-->
        <codemirror v-model="issueInfo.code" :autofocus="true" :extensions="extensions" :font-size="20"
            :indent-with-tab="true" :style="{ height: '200px' }" :tab-size="4" class="text-lg" placeholder="// 코드를 입력하세요" />
    </div>

    <div class="flex items-center justify-between">
        <div>
            <div>에러를 등록하면 자동으로 시간이 측정되어 적금할 수 있는 금액이 달라집니다.</div>
            <div>성공적인 에러 해결로 목표에 한 발짝 더 가까워지세요!</div>
        </div>
    </div>
    <div>
        <button class="px-10 py-5 m-2 shadow bg-main-red hover:bg-hover-red rounded-xl" @click="submitIssue()">
            <div class="text-3xl font-bold text-white font-main">에러 등록</div>
        </button>
    </div>
</template>

<style scoped></style>