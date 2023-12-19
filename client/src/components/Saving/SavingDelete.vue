<script setup>

import axios from "axios";
import {useAuthStore} from "../../stores/auth";
import router from "../../router";

const deleteAccount = async () => {
  try {
    await axios.put('/savings/giveup', {},
         {
          headers: {
            Authorization: useAuthStore().token,
          }
        });
  window.alert("적금이 삭제되었습니다.\n적금 기록에서 내역을 확인할 수 있어요.");
    await router.push({
      path: "/",
    });
  } catch (error) {
    console.log(error);
  }
}
</script>

<template>
  <div class="w-full space-y-20 text-center">
    <div class="flex justify-center mt-20">
      <img class="w-44 h-44" src="/images/emojiSad.svg"  alt="emoji"/>
    </div>
    <div class="space-y-8">
      <div class="text-3xl text-rose-950">정말로 적금을 삭제하시겠어요? </div>
      <div class="text-2xl">적금을 삭제하면 해당 적금에 대한 모든 데이터는 보존되나, 적금 상태는 <span class="text-main-red font-bold">포기</span>로 변경됩니다.</div>
      <div class="text-2xl">삭제 후에는 이어서 저축할 수 없고, 복구가 불가능하니 신중하게 선택해주세요.</div>
    </div>
    <div>
      <button @click="deleteAccount" type="button" class="bg-rose-950 hover:bg-rose-900 m-2 px-10 py-5 rounded-xl ">
        <div class="text-white text-3xl font-bold ">적금 삭제</div>
      </button>
    </div>
  </div>
</template>

<style scoped>

</style>