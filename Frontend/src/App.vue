<template>
  <div id="app">
    <div id="nav">
      <!-- Query문 데이터 보내서 링크 주소 만들기 실습 -->
      <router-link to="/" @click="data">Home</router-link> |
      <router-link
        :to="{
          name: 'users',
          params: {
            userId: `{{ idnum }}`,
            name: '4dich',
          },
          query: {
            group: idnum,
            category: 'trial',
          },
        }"
        >About</router-link
      >
    </div>
    <router-view />
  </div>
</template>

<script>
export default ({
  name: 'App',
  data() {
    return {
      idnum: 4321,
    }
  },

})
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
