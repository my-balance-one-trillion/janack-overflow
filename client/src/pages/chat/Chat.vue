<template>
  <div>
    유저이름:
    <input v-model="userId" type="text" />
    내용: <input v-model="content" type="text" @keyup="sendMessage" />
    <div v-for="(item, idx) in recvList" :key="idx">
      <h3>유저이름: {{ item.userId }}</h3>
      <h3>내용: {{ item.content }}</h3>
    </div>
  </div>
</template>
<!-- 필요한 메서드: 채팅내용 불러오기,  -->
<script setup>
import { onMounted, ref } from "vue";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client/dist/sockjs.min.js";

const messageRes = ref({});
const messageReq = ref({});
const userName = ref("");
const userId = ref();
const content = ref("");
const recvList = ref([]);
var connected;
var stompClient;
onMounted(() => {
  connect();
});

function sendMessage(e) {
  if (e.keyCode === 13 && userId.value !== "" && content.value !== "") {
    send();
    content.value = "";
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
      // 소켓 연결 성공
      connected = true;
      console.log("소켓 연결 성공", frame);
      // 서버의 메시지 전송 endpoint를 구독합니다.
      // 이런형태를 pub sub 구조라고 합니다.
      stompClient.subscribe("/sub/cache/"+roomId.value, (res) => {
        console.log("구독으로 받은 메시지 입니다.", res.body);

        // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
        recvList.value.push(JSON.parse(res.body));
      });
    },
    (error) => {
      // 소켓 연결 실패
      console.log("소켓 연결 실패", error);
      connected = false;
    }
  );
}

function send() {
  console.log("Send  content:" + content.value);
  if (stompClient && stompClient.connected) {
    const msg = {
      userId: userId.value,
      content: content.value,
      
      
    };
    stompClient.send("/pub/cache/"+roomId.value, JSON.stringify(msg), {});
  }
}
</script>

<style></style>
