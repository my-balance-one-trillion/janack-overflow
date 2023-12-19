<template>
    <div class="flex items-center justify-end py-2">
        <!-- <Dropdown @selectLang="handleLangChange"></Dropdown> -->
        <i :class="['p-2', 'fa-lg', 'fa-clipboard', 'issueCodeBlock', 'hover:cursor-pointer', hovered ? 'fa-solid' : 'fa-regular']"
            @click="copyToClipboard" @mouseover="hovered = true" @mouseout="hovered = false"></i>
    </div>
    <codemirror v-model="props.code" placeholder="// 코드가 이곳에 표시됩니다." :style="{ height: '400px' }" :autofocus="true"
        id="codeMirror" disabled :indent-with-tab="true" :tab-size="4"
        style="width: 100%; margin: 0 auto; font-size: 16px; border-radius: 11px; padding: 8px; border: 5px solid white; border-style: none;" @ready="handleReady"
        :extensions="extensions" @change="log('change', $event)" @focus="log('focus', $event)"
        @blur="log('blur', $event)" />
</template>

<script setup>
import { ref, shallowRef, defineProps, onMounted } from 'vue';
import { Codemirror } from 'vue-codemirror';
import ClipboardJS from 'clipboard';
// import Dropdown from "@/components/Dropdown.vue";

import { javascript } from '@codemirror/lang-javascript';
import { java } from '@codemirror/lang-java';
import { python } from '@codemirror/lang-python';
import { sql } from '@codemirror/lang-sql';
import { html } from '@codemirror/lang-html';
import { css } from '@codemirror/lang-css';

import { nord } from 'cm6-theme-nord';
import { basicLight } from 'cm6-theme-basic-light';
import { basicDark } from 'cm6-theme-basic-dark';

const code = ref(null);
const lang = ref('java()');
const extensions = [eval(lang.value), nord];
const props = defineProps(['code']);
const hovered = ref(false);
const view = shallowRef();
const handleReady = (payload) => {
    view.value = payload.view;
}


let clipboardInstance = null; // ClipboardJS 인스턴스를 저장할 변수

function copyToClipboard(event) {
    console.log(event.target.classList[3]);
    const className = '.' + event.target.classList[3];
    console.log(className);

    const block = document.getElementById('codeMirror');
    console.log(block);
    let targetElement = null;

    targetElement = props.code;

    if (clipboardInstance) {
        clipboardInstance.destroy();
    }

    clipboardInstance = new ClipboardJS(className, {
        text: function () {
            return targetElement;
        }
    });
    console.log(clipboardInstance);

    clipboardInstance.on('success', function (e) {
        console.log(e);
        alert('코드가 클립보드에 복사되었습니다.');
    });

    clipboardInstance.on('error', function (e) {
        console.error('코드복사 중 오류가 발생했습니다:', e);
    });

}

onMounted(() => {
    lang.value = 'java()';
})
</script>
