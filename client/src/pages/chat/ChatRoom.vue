<template>
  <div class="flex flex-col flex-auto mx-auto w-7/12 h-10/12 my-4 p-6">
    <div class="flex justify-between my-3">
      <router-link :to="'/chat'">◀</router-link>
      <span class="text-2xl">
        {{ roomInfo.roomName }}
      </span>
      <span v-if="userInfo">
      <button
        v-if="roomInfo.usersId == userInfo.id"
        class="rounded-[15px] bg-main-red text-white w-fit h-fit px-5"
        @click="deleteChatRoom"
      >
        삭제
      </button>
    </span>
    </div>
    <div
      class="flex flex-col flex-auto flex-shrink-0 rounded-2xl justify-end max-h-[800px] min-h-[800px] bg-gray-100 p-4"
    >
      <div class="flex flex-col h-full mb-4 overflow-x-auto">
        <div class="flex flex-col h-full overflow-y-scroll" ref="chatDiv">
          <div class="grid grid-cols-12 gap-y-2">
            <template
              v-if="Object.keys(userInfo).length > 0"
              v-for="(item, idx) in recvList"
              :key="idx"
            >
              <!-- 입장 메시지 -->
              <div
                v-if="item.type == 'ENTER' || item.type == 'QUIT'"
                class="col-start-1 col-end-13 p-3 rounded-lg flex justify-center items-center"
              >
                <div class="flex flex-row-reverse items-center justify-start">
                  <div
                    class="relative px-4 py-2 mr-3 text-sm bg-indigo-100 shadow rounded-xl"
                  >
                    <div>
                      {{ item.usersDTO.nickname + "님이 " + item.content }}
                    </div>
                  </div>
                </div>
              </div>
              <!-- 자기 메세지 -->
              <div
                v-else-if="item.usersDTO.id == userInfo.id"
                class="col-start-6 col-end-13 p-3 rounded-lg"
              >
                <div class="flex flex-row-reverse items-center justify-start">
                  <div
                    class="relative px-4 py-2 mr-3 text-sm bg-indigo-100 shadow rounded-xl"
                  >
                    <div>
                      {{ item.content }}
                    </div>
                  </div>
                  <div class="text-xs px-3">
                    {{ item.createdAt }}
                  </div>
                </div>
              </div>
              <!-- 타인 메세지 -->
              <div v-else class="col-start-1 col-end-8 p-3 rounded-lg">
                <div class="flex">
                  {{ item.usersDTO.nickname }}
                </div>
                <div class="flex flex-row items-center">
                  <img
                    class="w-10 h-10 rounded-full"
                    :src="'/images/' + item.usersDTO.profileImage"
                  />

                  <div
                    class="relative px-4 py-2 ml-3 text-sm bg-white shadow rounded-xl"
                  >
                    {{ item.content }}
                  </div>
                  <div class="text-xs px-3">
                    {{ item.createdAt }}
                  </div>
                </div>
              </div>
            </template>
          </div>
        </div>
      </div>
      <div
        class="flex flex-row items-center w-full h-16 px-4 bg-white rounded-xl"
      >
        <div class="flex-grow">
          <div class="relative w-full">
            <input
              class="flex w-full h-10 pl-4 border rounded-xl focus:outline-none focus:border-indigo-300"
              type="text"
              v-model="messageReq.content"
              @keyup.enter="sendMessage"
            />
            <button
              class="absolute top-0 right-0 flex items-center justify-center w-12 h-full text-gray-400 hover:text-gray-600"
            >
              <!-- <svg
                class="w-6 h-6"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M14.828 14.828a4 4 0 01-5.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                ></path>
              </svg> -->
            </button>
          </div>
        </div>
        <div class="ml-2">
          <button
            class="flex items-center justify-center flex-shrink-0 px-4 py-1 text-white bg-indigo-500 hover:bg-indigo-600 rounded-xl"
            @click="sendMessage"
          >
            <span>Send</span>
            <span class="ml-2">
              <svg
                class="w-4 h-4 -mt-px transform rotate-45"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8"
                ></path>
              </svg>
            </span>
          </button>
        </div>
      </div>
    </div>
    <div class="flex justify-end">
    <button
      class="rounded-[10px] bg-main-red text-white w-fit h-fit px-2 py-1"
      @click="quit"
    >
      나가기
    </button>
  </div>
  </div>
</template>
<!-- 필요한 메서드: 채팅내용 불러오기,  -->
<script setup>
import { defineProps, watch, watchEffect, nextTick } from "vue";
import { onMounted, ref } from "vue";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client/dist/sockjs.min.js";
import axios from "axios";
import { useAuthStore } from "../../stores/auth";
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";

const currentRoute = useRoute();
const chatId = currentRoute.params.chatId;
const router = useRouter();
const messageRes = ref({});

const chatDiv = ref(null);

const userName = ref("");
const recvList = ref([]);
const roomInfo = ref({});
const userInfo = ref({});

const messageReq = ref({
  type: "ENTER",
  roomId: null,
  content: "",
  userId: null,
});

const isNewUser = ref(true);
// const authStore = useAuthStore();
// const userInfo = authStore.userInfo;

var connected;
var stompClient;
onMounted(async () => {
  // userInfo = await authStore.userInfo;
  console.log("실행");
  await axios
    .get("http://localhost:8081/mypage/myinfo", {
      headers: {
        authorization: useAuthStore().token,
      },
    })
    .then((response) => {
      userInfo.value = response.data;
      console.log("유저 정보 받기", userInfo.value.id);
      messageReq.value.userId = userInfo.value.id;
    });

  await axios
    .get("/chatrooms/" + chatId, {
      headers: {
        authorization: useAuthStore().token,
      },
    })
    .then((response) => {
      recvList.value = response.data.messageList;
      roomInfo.value = response.data.roomInfo;
      messageReq.value.roomId = roomInfo.value.id;
      console.log("리시브", recvList.value, "방정보", roomInfo.value);
    });

  connect();

  await axios
    .get("/chatrooms/enter/" + chatId, {
      headers: {
        authorization: useAuthStore().token,
      },
    })
    .then((response) => {
      console.log("입장", response);
    });
  if (chatDiv.value) {
    chatDiv.value.scrollTop = chatDiv.value.scrollHeight;
  }
});

function sendMessage() {
  if (messageReq.value.content !== "") {
    send();
    messageReq.value.content = "";
  }
}

function connect() {
  const serverURL = "http://192.168.3.102:8081/ws";
  let socket = new SockJS(serverURL);
  stompClient = Stomp.over(socket);

  console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
  stompClient.connect(
    {},
    (frame) => {
      connected = true;
      console.log("소켓 연결 성공", frame);

      stompClient.subscribe("/sub/cache/" + roomInfo.value.roomId, (res) => {
        console.log("구독으로 받은 메시지 입니다.", res.body);
        recvList.value.push(JSON.parse(res.body));
      });
      //입장
      for (const [id, value] of Object.entries(roomInfo.value.usersList)) {
        console.log("키", id, "벨류", value.id, userInfo.value.id);
        if (value.id == userInfo.value.id) {
          console.log("이미 존재함");
          isNewUser.value = false;
          break;
        }
      }
      //새로운 유저
      if (isNewUser.value) {
        enter();
      }
      messageReq.value.type = "TALK";
    },
    (error) => {
      // 소켓 연결 실패
      console.log("소켓 연결 실패", error);
      connected = false;
    }
  );
}

function enter() {
  messageReq.value.content = "입장하셨습니다.";
  send();
  messageReq.value.content = "";
  messageReq.value.type = "TALK";
}

//퇴장
function quit() {
  const confirm = window.confirm("진짜 퇴장하실건가요?");
  if (confirm) {
    messageReq.value.type = "QUIT";
    messageReq.value.content = "퇴장하셨습니다.";
    console.log("퇴장실행");
    send();
    messageReq.value.content = "";

    axios
      .delete("/chatrooms/quit/" + chatId, {
        headers: {
          authorization: useAuthStore().token,
        },
      })
      .then((response) => {
        stompClient.disconnect();
        console.log("퇴장", response.data);
        window.alert("퇴장.");
        router.push("/chat");
      })
      .catch((error) => {
        window.alert("퇴장 실패");
      });
  }
}

function send() {
  console.log("Send  content:", messageReq.value);
  if (stompClient && stompClient.connected) {
    stompClient.send(
      "/pub/cache/" + roomInfo.value.roomId,
      JSON.stringify(messageReq.value),
      {}
    );
  }
}

function deleteChatRoom() {
  const confirm = window.confirm("진짜 삭제하실건가요?");
  if (confirm) {
    axios
      .delete("/chatrooms/" + chatId, {
        headers: {
          authorization: useAuthStore().token,
        },
      })
      .then((response) => {
        console.log("삭제", response.data);
        window.alert("삭제되었습니다.");
        router.push("/chat");
      })
      .catch((error) => {
        window.alert("삭제 실패");
      });
  }
}
watch(
  recvList,
  () => {
    if (chatDiv.value) {
      nextTick(() => {
        chatDiv.value.scrollTop = chatDiv.value.scrollHeight;
      });
    }
  },
  { deep: true }
);
</script>

<style></style>
