<template>
  <div class="min-h-screen bg-gray-100 py-6 flex flex-col justify-center sm:py-12">
    <div class="relative py-3 sm:max-w-xl sm:mx-auto">
      <div class="absolute inset-0 bg-gradient-to-r 
      from-red-300 to-red-600 shadow-lg transform 
      -skew-y-6 sm:skew-y-0 sm:-rotate-6 sm:rounded-3xl"></div>
      
      <div class="relative px-4 py-10 bg-white shadow-lg sm:rounded-3xl sm:p-20">
        <div class="max-w-md mx-auto">
          <div>
            <h1 class="text-2xl text-center font-semibold">Sign Up</h1>
          </div>
          
          <div class="divide-y divide-gray-200">
            <div class="py-8 text-base leading-6 space-y-4 text-gray-700 sm:text-lg sm:leading-7">
              <div class="relative">
                <div class="relative">
                  <input autocomplete="off" id="email" name="email" type="text" 
                  class="peer placeholder-transparent h-10 w-full border-t-0 border-l-0 border-r-0 border-b-2
                  border-red-700 text-gray-900 
                  focus:outline-none focus:borer-rose-600" placeholder="Email" />
                  <label for="email" 
                  class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base 
                  peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 
                  transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">Email</label>
                </div>

                <br>
                
                <div class="relative">
                  <input autocomplete="off" id="password" name="password" type="password" 
                  class="peer placeholder-transparent h-10 w-full border-t-0 border-l-0 border-r-0 border-b-2 
                  border-red-700 text-gray-900 focus:outline-none focus:borer-rose-600" placeholder="Password" />
                  <label for="password" 
                  class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">Password</label>
                </div>

                <br>
                
                <div class="relative">
                  <input autocomplete="off" id="name" name="name" type="text" 
                  class="peer placeholder-transparent h-10 w-full border-t-0 border-l-0 border-r-0 border-b-2 
                  border-red-700 text-gray-900 focus:outline-none focus:borer-rose-600" placeholder="Name" />
                  <label for="name" 
                  class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">Name</label>
                </div>

                <br>
                
                <div class="relative">
                  <input autocomplete="off" id="nickName" name="nickName" type="text" 
                  class="peer placeholder-transparent h-10 w-full border-t-0 border-l-0 border-r-0 border-b-2 
                  border-red-700 text-gray-900 focus:outline-none focus:borer-rose-600" placeholder="Nick Name" />
                  <label for="nickName" 
                  class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">Nick Name</label>
                </div>

                <br>
                
                <div class="relative">
                  <input autocomplete="off" id="digit" name="digit" type="text" 
                  class="peer placeholder-transparent h-10 w-full border-t-0 border-l-0 border-r-0 border-b-2 
                  border-red-700 text-gray-900 focus:outline-none focus:borer-rose-600" placeholder="Digit" />
                  <label for="digit" 
                  class="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">Digit</label>
                </div>
              </div>
              
              <div class="relative flex justify-center"> 
                <button class="bg-red-700 text-white rounded-3xl px-28 py-1" @click = "modalOpen">회원가입</button>
              </div>
              
            </div>
          </div>
        </div>
          <div class="flex justify-center">
                <p class="pl-5 text-sm">이미 회원이신가요?</p>
                <p class="pl-2 text-sm text-red-700">로그인</p>
          </div>
      </div>
    </div>
  </div>

  <div class="modal-wrap" v-show="modalCheck">
    <div class="modal-container">
      <h1 class="text-2xl text-center font-semibold">반가워요!</h1>
      <p>회원가입이 완료되었습니다!</p>
      <div class="modal-btn">
        <button @click="modalOpen">로그인 화면으로</button>
      </div>
    </div>
  </div>
</template>

<script>
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
