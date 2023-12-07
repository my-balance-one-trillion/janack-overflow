<template>
  <div class = "content center">
      <div class="top">
          <img id="loginTitle" src="src/assets/users/img/Sugar-Road Logo.png">
      </div>
          <div class="middle">
              <form id="signForm" @keyup.enter="userSignup">
                  <div class="formTop">
                      <h3 id="please">Please Sign Up</h3>
                      <label class="photo" for="input-image">
                          <img src="src/assets/users/img/photo.png" id = "userImage">
                      </label>
                  </div>
                  <div class="formMiddle">
                  <input class="inputText" type="text" name="userId" v-model="InId" placeholder="User Id" required><br>
                  <input class="inputText" type="password" name="userPassword"
                  v-model="Password" placeholder="Password" required><br>
                  <input class="inputText" type="text" name="userName"
                  v-model="InName" placeholder="User Name" required><br>
                  <input class="inputText" type="text" name="nickname"
                  v-model="InNickname" placeholder="User NickName" required><br>
                  <input class="inputText" type="text" name="userEmail" v-model="InEmail" placeholder="User Email" required><br>
                  <input type="file" name="image" style="display:none" id="input-image" onchange="changeProfile(this)"><br>
                  <button id="submitForm" @click="userSignup">Sign Up</button>
                  </div>
              </form>
          </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import { useRoute, useRouter } from "vue-router";

const InId = ref('');
const InImg = ref('');
const Password = ref('');
const InName = ref('');
const InNickname = ref('');
const InEmail = ref('');
const router = useRouter();

function userSignup(event){
  console.log("post request")
  event.preventDefault();
  axios.post(`http://3.36.127.22:3306/signup`, {
      id: InId.value,
      userPassword: Password.value,
      userImagePath: InImg.value,
      userName: InName.value,
      nickname: InNickname.value,
      userEmail: InEmail.value
  },{
      headers: {
          "Content-Type": "application/json",
      },
  })
      .then((response) => {
          console.log("put response", response);
          window.alert(response.data);
          router.push('/')
      })
      .catch(response => {
          console.log("put error", response.response.data);
          window.alert(response.response.data);
      })
}
</script>

<style scoped>
@import "@/assets/users/signup.css";
.not-overflow-y {
height: 100%;
overflow-y: hidden;
}
</style>