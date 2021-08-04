<template>
  <div class="SignInDiv">
    <div class="SignInDivInner">
      <!-- <h1>Sign In</h1> -->
      <img src="https://cdn.discordapp.com/attachments/869113013892964412/870324360420929586/logo_png.png" alt="">
      <div class="IdDiv">
        <!-- <label for="id">ID </label> -->
        <input
          v-model="signInData.id"
          id="id"
          pattern="^([a-z0-9_]){6,50}$"
          placeholder="ID"
          type="text"
        />
      </div>

      <div>
        <!-- <label for="password">Password : </label> -->
        <input
          v-model="signInData.password"
          id="password"
          pattern=""
          placeholder="Password"
          type="password"
        />
      </div>

      <button @click="signIn(signInData)">Login</button>
    </div>
    <span>
      첫 소풍이신가요? 지금
      <a @click="signUp">회원가입</a>
      하세요!
    </span>
  </div>
</template>

<script>
export default {
  name: "SignIn",
  components: {},
  data() {
    return {
      sampleData: "",
      signInData: {
        id: "",
        password: "",
      },
      error: {
        id: false,
        email: false,
        password: false,
      },
    };
  },
  beforeCreate() {},
  created() {},
  beforeMount() {},
  mounted() {},
  beforeUpdate() {},
  updated() {},
  beforeUnmount() {},
  unmounted() {},
  methods: {
    signIn() {
      alert("click!")
    },
    signUp() {
      this.$router.push('/signup')
    }
  },
};
</script>

<style scoped>
div {
  font-family: monospace;
}

input#id:invalid {
  border: 3px solid red;
}
/* input:valid {
    background-color: greenyellow;
  } */

.SignInDiv {
  /* display: table; */
  /* inline-block 사용시 margin과 함께 사용. inline-block은 가로 중앙정렬만 가능 */
  /* display: inline-block; */

  /* 다른 중앙정렬 방식 position ~ transform */
  position: absolute;
  top: 50%;
  left: 50%; 
  transform: translate(-50%, -50%);

  width: 390px;
  height: 300px;
  /* 상우하좌 padding으로 내부컨텐츠 정렬 구현 */
  padding: 10px 45px 40px 45px;
  /* margin: 50px auto; */
  /* margin-block: 10%; */

  /* text-align: center; */

  border-radius: 15px;
  /* background-color: rgba(0, 0, 0, .75); */
  background-color: rgba(183, 220, 204, .5);
  /* opacity: 0.5; */
}

.SignInDivInner {
  /* display: table-cell; */
  margin:auto;

  /* vertical-align: middle; */
}

h1 {
  color: white;
  text-align: start;
}

img {
  width: 100px;
  margin-right: auto;
}

.IdDiv {
  margin: 5px auto;
}

input {
  width: 300px;
  height: 30px;
  /* 상 좌우 하 */
  padding: 12px 20px 5px;

  border: none;
  border-radius: 4px;
  background-color: rgba(255, 255, 255, .5 );

  color: white;
  font-size: 16px;

}
/* 글자 입력시 테두리 삭제 */
input:focus {
  outline: none;
}

input::placeholder {
  color: rgba(237, 119, 102, 1);
  font-family: monospace;
}

button {
  width: 150px;
  height: 40px;
  margin: 30px;
  padding: 12px 20px 5x;
  
  border: none;
  border-radius: 20px;
  background-color: rgba(255, 255, 255, .5 );
  
  color: rgba(237, 119, 102, 1);
  font-size: 20px;
  font-weight: bold;
  font-family: monospace;
  text-decoration: none;

  cursor: pointer;
}

span {
  color: rgba(237, 119, 102, 1);
  font-size: 15px;
}

span > a {
  color: white;
  font-weight: bold;
  cursor: pointer;
}
</style>
