<template>
  <div>
    <!-- 추가할 것.
          정규표현식 이용한 패턴 관리.
          에러메세지 출력 구현? 이거 어째야될지 모르겠음...
          CSS(나중에)
    -->
    <div>
      <label for="id">ID : </label>
      <input
        v-model="signUpData.id"
        id="id"
        pattern="^([a-z0-9_]){6,50}$"
        placeholder="id를 입력해주세요."
        type="text"
      />
    </div>

    <div>
      <label for="nickname">Nickname : </label>
      <input
        v-model="signUpData.nickname"
        id="nickname"
        placeholder="닉네임을 입력해주세요."
        type="text"
      />
    </div>

    <div>
      <label for="email">Email : </label>
      <input
        v-model="signUpData.email"
        id="email"
        pattern=""
        placeholder="email을 입력해주세요."
        type="email"
      />
    </div>

    <div>
      <label for="password">Password : </label>
      <input
        v-model="signUpData.password"
        id="password"
        pattern=""
        placeholder="password를 입력해주세요."
        type="password"
      />
    </div>

    <div>
      <label for="confirm-password">Confirm Password : </label>
      <input
        v-model="signUpData.confirmPassword"
        id="confirm-password"
        pattern=""
        placeholder="password를 다시 한번 입력해주세요."
        type="password"
      />
    </div>

    <button @click="signUp(signUpData)">회원가입</button>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "SignUp",
  components: {},
  data() {
    return {
      sampleData: "",
      signUpData: {
        id: "",
        nickname: "",
        email: "",
        password: "",
        confirmPassword: "",
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
    ...mapActions(["signUp"]),
  },
};
</script>

<style scoped>
input:invalid {
  border: 3px solid red;
}
/* input:valid {
    background-color: greenyellow;
  } */
</style>
