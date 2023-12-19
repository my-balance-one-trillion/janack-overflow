<template>
    <div class="flex items-center w-fit">
        <select id="langs"
            class="block w-auto p-1 text-sm text-gray-900 bg-white border-2 border-gray-300 rounded-lg focus:ring-blue-500 focus:border-blue-500"
            @change="handleLangChange">
            <!-- <option selected value="plain">text</option> -->
            <option selected value="python()">python</option>
            <option value="java()">java</option>
            <option value="javascript()">javascript</option>
            <option value="css()">css</option>
            <option value="sql()">mysql</option>
            <option value="html()">html</option>
        </select>
    </div>
</template>
<script setup>
import { onMounted, ref } from 'vue';
import { initFlowbite } from 'flowbite';
import { defineEmits } from 'vue'

const selectedLang = ref('');
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