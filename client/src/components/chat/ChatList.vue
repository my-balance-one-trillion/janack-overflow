<template>
  <div
    class="w-full max-w-4xl p-4 mx-auto my-16 bg-white border border-gray-200 rounded-lg shadow sm:p-6 dark:bg-gray-800 dark:border-gray-700"
  >
    <div class="flex justify-between">
      <div>
        <h5
          class="mb-3 text-base font-semibold text-gray-900 md:text-xl dark:text-white"
          v-text="p.toggle ? '오픈 채팅' : '내 채팅'"
        ></h5>
        <p class="text-sm font-normal text-gray-500 dark:text-gray-400">
          다른 사람들의 에러 해결을 도와주세요!
        </p>
      </div>
      <button
        class="rounded-[15px] bg-main-red hover:bg-hover-red text-white w-fit h-fit px-5"
        style="font-size: 21px"
        @click="openModal"
      >
        생성
      </button>
    </div>
    <div class="min-h-[600px] max-h-[800px] overflow-y-scroll">
    <ul class="my-4 space-y-3">
      <template v-for="(room, index) in p.chatRoomList">
        <li>
          <div
            class="flex items-center p-3 text-base font-bold text-gray-900 rounded-lg bg-gray-50 hover:bg-gray-100 group hover:shadow dark:bg-gray-600 dark:hover:bg-gray-500 dark:text-white"
            @click="enterChatRoom(room.id, room.usersList)"
          >
          <span v-if="room.usersId == userInfo.id">👑</span>
            <span class="flex-1 ms-3 whitespace-nowrap">
              {{ room.roomName }}
            </span>
            <span
              class="w-1/12 inline-flex items-center justify-center px-3 py-0.5 ms-3 text-xs font-medium text-white bg-sub-red rounded dark:bg-gray-700 dark:text-gray-400"
              >{{ room.category }}
            </span>
            <span
              class="inline-flex items-center justify-center
              min-w-[50px] px-3 py-0.5 ms-3 text-xs font-medium text-gray-500 bg-gray-200 rounded dark:bg-gray-700 dark:text-gray-400"
              >{{ room.usersList.length }}/{{ room.max }}</span
            >
          </div>
        </li>
      </template>
    </ul>
  </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "../../stores/auth";

const authStore = useAuthStore();
const router = useRouter();

const userInfo = authStore.userInfo;
const emit = defineEmits(["open-modal"]);

function openModal() {
  emit("open-modal", true);
}

function enterChatRoom(roomId, usersList) {
  if (
    usersList.length < 4 ||
    usersList.some((user) => user.id == userInfo.id)
  ) {
    router.push("/chat/" + roomId);
  } else {
    alert("인원이 다 찼습니다.");
  }
}

const p = defineProps({
  chatRoomList: {
    type: Array,
  },
  toggle: {
    type: Boolean,
  },
});
</script>

<style></style>
