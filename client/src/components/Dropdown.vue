<template>
    <div class="flex items-center w-fit">
        <!-- <div class="flex items-center w-fit"> -->
        <label for="langs" class="block w-20 mb-2 text-xs font-bold text-gray-900 dark:text-white">언어 선택</label>
        <!-- </div> -->
        <select id="langs"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-4/5 p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            @change="handleLangChange">
            <option selected value="plain">text</option>
            <option value="python">python</option>
            <option value="java">java</option>
            <option value="javascript">javascript</option>
            <option value="kotlin">kotlin</option>
            <option value="sql">mysql</option>
            <option value="html">html</option>
        </select>
    </div>
</template>
<script setup>
import { onMounted, ref } from 'vue';
import { initFlowbite } from 'flowbite';
import { defineEmits } from 'vue'

const selectedLang = ref('plain');
const emit = defineEmits(['selectLang']);

const handleLangChange = (event) => {
    selectedLang.value = event.target.value;
    emit('selectLang', selectedLang.value);
}

onMounted(() => {
    emit('selectLang', selectedLang.value);
    initFlowbite();
})
</script>