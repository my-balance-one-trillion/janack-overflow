<template>
  <div class="not-overflow-y">
    <div class="app-body">
    <div class = "content center">
        <img id="loginTitle" src="src/assets/users/img/Sugar-Road Logo.png">
        <div id="loginBox" @keyup.enter="login">
            <form name="loginForm">
                <h4 id="please">Please Login</h4>
                <input id="loginText1" type="text" name="userId" @keyup.enter="submit()" v-model="state.form.id" placeholder="Id" autocomplete='off' required><br>
                <br>
                <input id="loginText2" type="password" name="userPassword" @keyup.enter="submit()" v-model="state.form.password" placeholder="Password" required><br>
                <br>
                <!-- <input type="submit" id="signIn" value="Login"> -->
            </form>
            <P style="margin-bottom: 10%;">
              <button id="signIn" @click="login">Login</button>
            </P>
            <br>
            <button id="signUp" onclick="location.href = '/signup'">Go to Sign Up</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {reactive} from "vue";
import axios from "axios";
import { useRoute, useRouter } from "vue-router";

axios.defaults.withCredentials = true;

export default {
  setup() {
    const router = useRouter();
    const state = reactive({
      form: {
        id: "",
        password: ""
      }
    })

    const login = async () => {      
      const response = await axios.post("http://localhost:8801/login", state.form, 
        {headers : {
          "Content-Type": "application/json",
          "X-Requested-With": "XMLHttpRequest"
          // "withCredentials" : true
          //토큰 값 가져와야하면
          //sessionStorage.getItem('token')
          //Authorization : 'token'
        }
        }).then((response)=>{
          console.log("헤더확인");
          console.log(response.headers.user);
          console.log(response.headers.authorization);
          sessionStorage.setItem('token',response.headers.authorization);
          sessionStorage.setItem('user', response.headers.user)
          
          window.alert(`반갑습니다, ${response.headers.user} 님!`);
          router.push('/')

          //토큰 값 가져와야하면
          //sessionStorage.getItem('token')
        }).catch( () => {
          console.log("로그인 실패");
          window.alert('로그인 실패했습니다');
        });
    }

    const logout = () => {  
          sessionStorage.removeItem("token");
          sessionStorage.removeItem("user");
          window.alert("로그아웃 수행");
    }

    const check = () => {      
      axios.get("http://localhost:8801/check",{
            headers: {
              'Authorization': sessionStorage.getItem("token")
            }
          }).then((res) => {       
        window.alert(res.data);
      });
    }

    return {state, login, logout, check}
  }
}
</script>

<style scoped>
@import "@/assets/users/login.css";
.not-overflow-y {
  height: 100%;
  overflow-y: hidden;
}
</style>