<template>
    <div class="flex flex-col items-center w-10/12 mx-auto">
        <div class="flex items-center justify-center mt-10 rounded-lg bg-bg-grey">
            <div :class="activeTab === 0 ? 'text-main-red text-2xl font-bold border-main-red border-b-8' : 'text-2xl font-bold text-gray-500 hover:text-hover-red'"
                @click="activeTab = 0" class="py-2 cursor-pointer px-60">
                {{ tabs[0] }}
            </div>
            <div :class="activeTab === 1 ? 'text-main-grn text-2xl font-bold border-main-grn border-b-8' : 'text-2xl font-bold text-gray-500 hover:text-hover-grn'"
                @click="activeTab = 1" class="py-2 cursor-pointer px-60">
                {{ tabs[1] }}
            </div>
        </div>
        <div class="flex items-center justify-center w-12/12">
            <div class="max-w-5xl p-4 px-10 py-6 mx-auto my-4 text-lg bg-white rounded-lg shadow-md w-12/12">
                <p v-show="activeTab === 0">
                    Lorem ipsum dolor sit amet consectetur adipisicing elit. Quibusdam repellendus et fugit alias modi in
                        earum id reiciendis ratione debitis perferendis, natus quis eos vel explicabo maxime distinctio
                        tenetur animi!
                </p>
                <p v-show="activeTab === 1">
                    Content 2
                </p>
            </div>
        </div>

    </div>
</template>

<script setup>
import { ref } from 'vue';
const activeTab = ref(0);
const tabs = ref([
    "에러",
    "해결",
]);
</script>