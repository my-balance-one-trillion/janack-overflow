<template>
  <div class="min-h-screen py-6 flex flex-col  sm:py-12">
    <div class="relative py-3 sm:max-w-xl sm:mx-auto">
      <div class="absolute inset-0 bg-gradient-to-r 
      from-red-300 to-red-600 shadow-lg transform 
      -skew-y-6 sm:skew-y-0 sm:-rotate-6 sm:rounded-3xl">
      </div>
      
      <div class="relative px-4 py-10 bg-gray-100 shadow-lg sm:rounded-3xl sm:p-20">
        <div class="max-w-md mx-auto">
          <div>
            <h1 class="text-2xl text-center font-semibold">Sign Up</h1>
          </div>
          
          <div class="divide-y divide-gray-200">
            <div class="py-8 text-base leading-6 space-y-4 text-gray-700 sm:text-lg sm:leading-7">
              <div class="relative">
                <form>
                <div class="relative">
                  <input autocomplete="off" id="email" name="email" type="text" v-model="state.input.email"
                  class="focus:ouline-none focus:ring-0 peer placeholder-transparent h-10 w-full border-t-0 border-l-0 border-r-0 border-b-2
                  border-red-700 text-gray-900 
                  focus:outline-none focus:borer-rose-600" placeholder="Email" required/>
                  <label for="email" 
                  class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base 
                  peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 
                  transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">Email</label>
                </div>

                <br>
                
                <div class="relative">
                  <input autocomplete="off" id="password" name="password" type="password" v-model="state.input.password"
                  class="focus:ouline-none focus:ring-0 peer placeholder-transparent h-10 w-full border-t-0 border-l-0 border-r-0 border-b-2 
                  border-red-700 text-gray-900 focus:outline-none focus:borer-rose-600" placeholder="Password" required/>
                  <label for="password" 
                  class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">패스워드</label>
                </div>

                <br>

                <div class="pt-3">
                <h6 class="pb-3">프로필</h6>
                
                  <div class="flex justify-between gap-3">
                    <div class="relative">
                      <input autocomplete="off" id="name" name="name" type="text" v-model="state.input.name"
                      class="focus:ouline-none focus:ring-0 peer placeholder-transparent h-10 w-full border-t-0 border-l-0 border-r-0 border-b-2 
                      border-red-700 text-gray-900 focus:outline-none focus:borer-rose-600" placeholder="Name" required/>
                      <label for="name" 
                      class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">이름</label>
                    </div>

                    <div class="relative">
                      <input autocomplete="off" id="nickName" name="nickName" type="text" v-model="state.input.nickname"
                      class="focus:ouline-none focus:ring-0 peer placeholder-transparent h-10 w-full border-t-0 border-l-0 border-r-0 border-b-2 
                      border-red-700 text-gray-900 focus:outline-none focus:borer-rose-600" placeholder="Nick Name" required/>
                      <label for="nickName" 
                      class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">닉네임</label>
                    </div>
                  </div>

                  <br>

                  <div class="flex justify-between gap-3">
                    <div class="relative">
                      <input autocomplete="off" id="digit" name="digit" type="text" v-model="state.input.digit"
                      class="focus:ouline-none focus:ring-0 peer placeholder-transparent h-10 w-full border-t-0 border-l-0 border-r-0 border-b-2 
                      border-red-700 text-gray-900 focus:outline-none focus:borer-rose-600" placeholder="Digit" required/>
                      <label for="digit" 
                      class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">전화번호</label>
                    </div>

                    <div class="relative">
                      <DatePicker class="w-10/12 ml-6"
                        v-model="datePic"
                        language="ko"
                        append-icon="mdi-calendar"
                        :is-inline="true"
                      />
                    </div>

                    <!-- <div class="relative">
                      <input autocomplete="off" id="birth" name="birth" type="text" v-model="state.input.birth"
                      class="focus:ouline-none focus:ring-0 peer placeholder-transparent h-10 w-full border-t-0 border-l-0 border-r-0 border-b-2 
                      border-red-700 text-gray-900 focus:outline-none focus:borer-rose-600" placeholder="Birth" required/>
                      <label for="birth" 
                      class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">생년월일</label>
                    </div> -->
                  </div>
                </div>

                <br>

                <div class="pt-3">
                <h6 class="pb-3">연결 계좌</h6>

                  <div class="flex justify-between gap-3">
                    <div class="relative">
                      <input autocomplete="off" id="bankName" name="bankName" type="text" v-model="state.input.bankName"
                      class="focus:ouline-none focus:ring-0 peer placeholder-transparent h-10 w-full border-t-0 border-l-0 border-r-0 border-b-2 
                      border-red-700 text-gray-900 focus:outline-none focus:borer-rose-600" placeholder="bankName" required/>
                      <label for="bankName" 
                      class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">은행명</label>
                    </div>

                    <div class="relative">
                      <input autocomplete="off" id="holder" name="holder" type="text" v-model="state.input.holder"
                      class="focus:ouline-none focus:ring-0 peer placeholder-transparent h-10 w-full border-t-0 border-l-0 border-r-0 border-b-2 
                      border-red-700 text-gray-900 focus:outline-none focus:borer-rose-600" placeholder="holder" required/>
                      <label for="holder" 
                      class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">예금주</label>
                    </div>
                  </div>

                  <br>

                  <div class="relative">
                    <input autocomplete="off" id="outputAcntNum" name="outputAcntNum" type="text" v-model="state.input.outputAcntNum"
                    class="focus:ouline-none focus:ring-0 peer placeholder-transparent h-10 w-full border-t-0 border-l-0 border-r-0 border-b-2 
                    border-red-700 text-gray-900 focus:outline-none focus:borer-rose-600" placeholder="outputAcntNum" required/>
                    <label for="outputAcntNum" 
                    class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">계좌번호</label>
                  </div>
                </div>
              </form>

              </div>
              
              <div class="relative flex justify-center"> 
                <button class="bg-red-700 text-white rounded-3xl px-28 py-1" @click = "signup">회원가입</button>
              </div>
              
            </div>
          </div>
        </div>
          <div class="flex justify-center">
                <p class="pl-5 text-sm">이미 회원이신가요?</p>
                <router-link to="/login" 
                class="pl-2 text-sm text-red-700">로그인</router-link>
          </div>
      </div>
    </div>
  </div>

  <!-- <div class="modal-wrap" v-show="modalCheck">
    <div class="modal-container">
      <h1 class="text-2xl text-center font-semibold">반가워요!</h1>
      <p>회원가입이 완료되었습니다!</p>
      <div class="modal-btn">
        <button @click="modalOpen">로그인 화면으로</button>
      </div>
    </div>
  </div> -->
</template>

<script>
import { ref, reactive } from "vue";
import DatePicker from 'vue3-datepicker';
import {ko} from 'date-fns/locale';
import dayjs from 'dayjs';
import axios from "axios";
import { useRouter } from "vue-router";

export default {

  components: {
    DatePicker,
  },

  setup() {
    const datePic = ref(new Date());

    const year = datePic.value.getFullYear();
    const month = datePic.value.getMonth() + 1;
    const day = datePic.value.getDate();

    const locale = reactive(ko); //한글 달력

    const router = useRouter();
    const state = reactive({
      input: {
        email: "",
        password: "",
        name: "",
        nickname: "",
        digit: "",
        birth: year + '-' + month + '-' + day,
        bankName: "",
        holder: "",
        outputAcntNum: ""
      }
    })

    const signup = async () => {      
      const response = await axios.post('http://localhost:8081/signup', state.input, 
        {
          headers : {
          "Content-Type": "application/json",
          }
        }
        ).then((response)=>{
          console.log(response);
          console.log(response.headers);
          
          window.alert(response.data);
          window.alert(`회원가입이 수행되었습니다`);
          router.push('/login')

        }).catch( (response) => {
          console.log("put error", response.response.data);
            window.alert(response.response.data);
        });
    }

    return {
      datePic,
      year,
      month,
      day,
      locale,
      state, 
      signup
    }
  }
}

// data() {
//   return {
//     modalCheck: false,
//   }
// },
// methods: {
//   modalOpen() {
//     this.modalCheck = !this.modalCheck
//   }
// }
</script>

<style scoped>
/* dimmed */
.modal-wrap {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
}
/* modal or popup */
.modal-container {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 550px;
  background: #fff;
  border-radius: 10px;
  padding: 20px;
  box-sizing: border-box;
}
</style>
