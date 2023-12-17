<template>
  <div>
    <div class="flex justify-center">
      <button 
      :class="isSelected[0]? classList[0] : classList[1]"
      @click="[getAllList(), toggle(0)]">
        오픈 채팅
      </button>
      <button 
      :class="isSelected[1]? classList[0] : classList[1]"
      @click="[getMyList(), toggle(1)]">
        내 채팅
      </button>
    </div>
  </div>
  
  <div>
    <ChatList 
    :chatRoomList="chatRoomList"
    :toggle="isSelected[0]"
    @open-modal="openModal"
     />
     <ChatCreateModal 
     v-show="modalCheck"
     @toggle-modalCheck="closeModal"
     @send-roomInfo="createChatRoom"/>
  </div>
  
</template>

<script setup>
import { useRoute, useRouter } from "vue-router";
import { onMounted, ref } from "vue";
import axios from "axios";
import ChatList from "@/components/chat/ChatList.vue";
import ChatCreateModal from '../../components/chat/ChatCreateModal.vue';
import { useAuthStore } from '../../stores/auth';


const classList = ["text-white p-4 rounded bg-main-red shadow-md", "p-4 rounded bg-white text-main-red shadow-md"];

const isSelected = ref([true, false]);
const modalCheck = ref(false);
const chatRoomList = ref([]);
const router = useRouter();

function toggle(index){
    isSelected.value = [false, false];
    isSelected.value[index] = !isSelected.value[index];
}
function getMyList(){
    axios.get("http://localhost:8081/chatrooms/my", {
    headers: {
      'authorization': useAuthStore().token,
    }
}
    ).then((response) => {
    chatRoomList.value = response.data;
    console.log("chatRoomList", chatRoomList.value);
  });
}
function getAllList() {
  axios.get("http://localhost:8081/chatrooms").then((response) => {
    chatRoomList.value = response.data;
    console.log("chatRoomList", chatRoomList.value);
  });
}

function openModal(data){
  console.log("openModal", data);
  modalCheck.value = data;
}

function closeModal(data){
  console.log("emit", data);
  modalCheck.value = data;
}

function createChatRoom(data){
  console.log("createChatRoom", data);
  axios.post("http://localhost:8081/chatrooms", data, {
      headers: {
        authorization: useAuthStore().token,
      },
    })
    .then((response) => {
      console.log("post 성공", response.data);
      router.go(0)
    })
}
onMounted(() => {
  getAllList();
});
</script>
<style>
</style>
