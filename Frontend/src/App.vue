<template>
  <div id="app">
    <div id="nav">
      <router-link to="/signin">로그인</router-link> |
      <router-link to="/signup">회원가입</router-link> |
      <router-link to="/123">임시 일지작성 화면</router-link>
    </div>
    <div>
      <button>
        <router-link to="/settings/changepassword">비밀번호 변경</router-link>
      </button>
    </div>
    <router-view/>
  </div>
</template>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  // background-color: black;
}

#nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>