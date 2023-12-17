<template>
  <!-- <div>
    유저이름:
    <input v-model="userId" type="text" />
    내용: <input v-model="content" type="text" @keyup="sendMessage" />

  </div> -->

  <div class="flex flex-col flex-auto mx-auto w-7/12 h-[800px] p-6">
    <div class="flex justify-between my-3">
      <router-link :to="'/chat'">◀</router-link>
      {{ roomInfo.roomName }}
      <button
        class="rounded-[15px] bg-main-red text-white w-fit h-fit px-5"
        @click="deleteChatRoom"
      >
        삭제
      </button>
    </div>
    <div
      class="flex flex-col flex-auto flex-shrink-0 rounded-2xl justify-center max-h-[800px] h-full bg-gray-100 p-4"
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
                class="col-start-7 col-end-9 p-3 rounded-lg whitespace-nowrap"
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
                v-else-if="item.usersDTO.id == userInfo.value.id"
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
                </div>
              </div>
            </template>
          </div>
        </div>
      </div>
      <div
        class="flex flex-row items-center w-full h-16 px-4 bg-white rounded-xl"
      >
        <div class="flex-grow ml-4">
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
              <svg
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
              </svg>
            </button>
          </div>
        </div>
        <div class="ml-4">
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
    <button
      class="rounded-[15px] bg-main-red text-white w-fit h-fit px-5"
      @click="quit"
    >
      나가기
    </button>
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
const userInfo = {};
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
    .get("http://localhost:8081/chatrooms/" + chatId, {
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
  if (chatDiv.value) {
    chatDiv.value.scrollTop = chatDiv.value.scrollHeight;
  }
  connect();
});

function sendMessage(e) {
  if (messageReq.value.content !== "") {
    send();
    messageReq.value.content = "";
  }
}
function connect() {
  const serverURL = "http://localhost:8081/ws";
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
      .delete("http://localhost:8081/chatrooms/" + chatId, {
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
