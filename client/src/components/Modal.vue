<template>
  <div class="fixed inset-0 flex items-center justify-center">
    <div class="fixed inset-0 bg-black opacity-50"></div>
    <div class="relative bg-white p-8 rounded-lg shadow-md flex flex-col justify-center items-center gap-5">
      <!-- 모달 내용 -->
      <p>{{ props.msg }}</p>
      <div>
        <!-- <button class="rounded-lg bg-main-grn text-white w-32 h-10 mr-5" @click="handleConfirm" v-if="props.mode == 'confirm'">확인</button> -->
        <button class="rounded-lg bg-main-red text-white w-32 h-10" @click="closeModal">닫기</button>
      </div>

    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';
const props = defineProps(['mode','msg']);
const emits = defineEmits();

function closeModal(){
  emits('close'); 
}

</script>

<style scoped>
/* Tailwind CSS 스타일링 */
</style>
