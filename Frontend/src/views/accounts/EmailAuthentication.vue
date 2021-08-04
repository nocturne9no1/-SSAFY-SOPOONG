<template>
  <div>
    <h1>이메일 인증</h1>
    <h3>
      인증 메일이 전송되었습니다.<br />메일을 확인하시고 인증코드를
      입력해주세요.
    </h3>
    <br />
    <div>
      <label for="">인증번호</label>
      <input
        v-model="authKey"
        id="authkey"
        placeholder="인증코드를 입력하세요."
        type="text"
      />
    </div>
    <button @click="requestAuth" >인증</button>
    <p>
      <a href="" @click="resend">인증메일 다시 보내기</a>
    </p>
  </div>
</template>

<script>
export default {
  name: "EmailAuthentication",
  components: {},
  data() {
    return {
      sampleData: "",
      authKey:"",
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
    resend() {
      alert('인증 메일이 재전송되었습니다.')
      // 과정 추가
    }
  },
};
</script>

<style></style>
