<template>
  <div id="app">
    <Navbar/>
    <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </div>
    <div>
      <button>
        <router-link to="/settings/changepassword">비밀번호 변경</router-link>
      </button>
    </div>
    <router-view/>
  </div>
</template>

<script>
import Navbar from './components/Navbar.vue'
import './components/css/style.scss'

export default ({
  name: 'App',
  components: {
    Navbar,
  }
})
</script>


<style lang="scss">
</style>
