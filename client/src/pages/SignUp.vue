<template>
  <div class="flex flex-col min-h-screen py-6 sm:py-12">
    <div class="relative py-3 sm:max-w-xl sm:mx-auto">
      <div class="absolute inset-0 bg-gradient-to-r 
      from-green-700 to-red-600 shadow-lg transform 
      -skew-y-6 sm:skew-y-0 sm:-rotate-6 sm:rounded-3xl">
      </div>
      
      <div class="relative px-4 py-10 shadow-lg bg-gray-50 sm:rounded-3xl sm:p-20">
        <div class="max-w-md mx-auto">
          <div>
            <h1 class="text-2xl font-semibold text-center">Sign Up</h1>
          </div>
          
          <div class="divide-y divide-gray-200">
            <div class="py-8 space-y-4 text-base leading-6 text-gray-700 sm:text-lg sm:leading-7">
              <div class="relative">
                <form>
                <div class="relative">
                  <input autocomplete="off" id="email" name="email" type="text" v-model="state.input.email"
                  class="focus:border-main-red focus:ouline-none focus:ring-0 peer placeholder-transparent bg-gray-50 h-10 w-full border-t-0 border-l-0 border-r-0 border-b-2
                  border-red-700 text-gray-900
                  focus:outline-none focus:borer-rose-600" 
                  placeholder="Email" required/>
                  <label for="email" 
                  class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base 
                  peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 
                  transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">이메일</label>
                </div>

                <br>
                
                <div class="relative">
                  <input
                  autocomplete="off" id="password" name="password" type="password" v-model="state.input.password"
                  class="focus:border-main-red focus:ouline-none focus:ring-0 peer placeholder-transparent bg-gray-50 h-10 w-full border-t-0 border-l-0 border-r-0 border-b-2 
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
                      class="w-full h-10 text-gray-900 placeholder-transparent border-t-0 border-b-2 border-l-0 border-r-0 border-red-700 focus:border-main-red focus:ouline-none focus:ring-0 peer bg-gray-50 focus:outline-none focus:borer-rose-600" placeholder="Name" required/>
                      <label for="name" 
                      class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">이름</label>
                    </div>

                    <div class="relative">
                      <input autocomplete="off" id="nickName" name="nickName" type="text" v-model="state.input.nickname"
                      class="w-full h-10 text-gray-900 placeholder-transparent border-t-0 border-b-2 border-l-0 border-r-0 border-red-700 focus:border-main-red focus:ouline-none focus:ring-0 peer bg-gray-50 focus:outline-none focus:borer-rose-600" placeholder="Nick Name" required/>
                      <label for="nickName" 
                      class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">닉네임</label>
                    </div>
                  </div>

                  <br>

                  
                  <div class="flex justify-between gap-3">
                    <div class="relative">
                      <input
                      @input="formatPhone"
                      autocomplete="off" id="digit" name="digit" type="text" v-model="state.input.digit"
                      class="focus:border-main-red focus:ouline-none focus:ring-0 peer placeholder-transparent bg-gray-50 h-10 w-full border-t-0 border-l-0 border-r-0 border-b-2 
                      border-red-700 text-gray-900 focus:outline-none focus:borer-rose-600" placeholder="Digit" required/>
                      <label for="digit" 
                      class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">전화번호</label>
                    </div>


                    <div class="relative">
                      <DatePicker
                        class="w-full h-10 placeholder-transparent border-t-0 border-b-2 border-l-0 border-r-0 border-red-700 focus:ouline-none focus:borer-rose-600 focus:ring-0 bg-gray-50"
                        v-model="datePic"
                        :locale="locale"
                        :is-inline="true"
                      />

                      <label for="birth" 
                      class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">생년월일</label>
                    </div>
                  </div>
                  
                </div>

                <br>

                <div class="pt-3">
                <h6 class="pb-3">연결 계좌</h6>

                  <div class="flex justify-between gap-3">
                    <div class="relative">
                      <input autocomplete="off" id="bankName" name="bankName" type="text" v-model="state.input.bankName"
                      class="w-full h-10 text-gray-900 placeholder-transparent border-t-0 border-b-2 border-l-0 border-r-0 border-red-700 focus:border-main-red focus:ouline-none focus:ring-0 peer bg-gray-50 focus:outline-none focus:borer-rose-600" placeholder="bankName" required/>
                      <label for="bankName" 
                      class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">은행명</label>
                    </div>

                    <div class="relative">
                      <input autocomplete="off" id="holder" name="holder" type="text" v-model="state.input.holder"
                      class="w-full h-10 text-gray-900 placeholder-transparent border-t-0 border-b-2 border-l-0 border-r-0 border-red-700 focus:border-main-red focus:ouline-none focus:ring-0 peer bg-gray-50 focus:outline-none focus:borer-rose-600" placeholder="holder" required/>
                      <label for="holder" 
                      class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">예금주</label>
                    </div>
                  </div>

                  <br>

                  <div class="relative">
                    <input autocomplete="off" id="outputAcntNum" name="outputAcntNum" type="text" v-model="state.input.outputAcntNum"
                    class="w-full h-10 text-gray-900 placeholder-transparent border-t-0 border-b-2 border-l-0 border-r-0 border-red-700 focus:border-main-red focus:ouline-none focus:ring-0 peer bg-gray-50 focus:outline-none focus:borer-rose-600" placeholder="outputAcntNum" required/>
                    <label for="outputAcntNum" 
                    class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">계좌번호</label>
                  </div>
                </div>
              </form>

              </div>
              
              <div class="relative flex justify-center"> 
                <button class="py-1 text-white bg-red-700 rounded-3xl px-28" @click = "signup">회원가입</button>
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
      <h1 class="text-2xl font-semibold text-center">반가워요!</h1>
      <p>회원가입이 완료되었습니다!</p>
      <div class="modal-btn">
        <button @click="modalOpen">로그인 화면으로</button>
      </div>
    </div>
  </div> -->
</template>

<script>
import { ref, reactive } from "vue";
import {ko} from 'date-fns/locale';
import DatePicker from 'vue3-datepicker';
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

    const formatPhone = () => {
      let input = state.input.digit.replace(/[^0-9]/g, '');

      if(input.length <= 3){
        state.input.digit = input;
      }else if (input.length <= 7) {
        state.input.digit = `${input.slice(0, 3)}-${input.slice(3)}`;
      } else {
        state.input.digit = `${input.slice(0, 3)}-${input.slice(3, 7)}-${input.slice(7)}`;
      }
    }

    const exMail = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
    const exPhone = /^[0-9]{3}\-([0-9]{3}|[0-9]{4})\-([0-9]{3}|[0-9]{4})/;
    const exPass = /^[A-Za-z0-9_\.\-`~!@#\$%^&*\|\\?;:+=\[\]<>\(\),_'"-]{9,}/;

    const signup = async () => {     
      //axios 실행하기 전에 체크
      if(exMail.test(state.input.email) === false) {
        alert('이메일 형식이 올바르지 않습니다.');
        return false;
      }
      
      if(exPass.test(state.input.password) === false) {
        alert('보안을 위해 패스워드는 최소 9자 이상 작성해주세요');
        return false;
      }
      
      if(exPhone.test(state.input.digit) === false) {
        alert('전화번호 형식이 올바르지 않습니다.\n하이픈(-) 포함');
        return false;
      }

      const res = await axios.post('http://localhost:8081/signup', state.input, 
        {
          headers : {
          "Content-Type": "application/json",
          }
        }
        ).then((res)=>{
          console.log(res);
          console.log(res.headers);
          
          window.alert(`회원가입이 수행되었습니다`);
          router.push('/login');

        }).catch( (error) => {
          console.log("put error", error.response.data);
            window.alert(error.response.data);
        });
    }

    return {
      datePic,
      year,
      month,
      day,
      locale,
      state,
      formatPhone,
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
