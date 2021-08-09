<template>
  <div>
    <nav>
      <ul class="nav-container">
        <div class="nav-left">
          <li class="nav-item nav-logo">
            <router-link to="/main">
              <img class="logo" src="../assets/logo.png" alt="Sopoong logo">
            </router-link></li>
            <router-link to="/main" class="text-logo">
              SOPOONG
            </router-link>
          <li class="nav-item nav-newsfeed"><router-link to="/feed">NewsFeed</router-link></li>
        </div>
        <div class="nav-right">
          <div class="search-bar" id="search-bar">
            <input type="search" v-model="searchWord" @keydown.enter="onEnter">
          </div>
          <div v-if="!isLogin" class="nav-right">
            <li class="nav-item nav-login"><router-link to="signin">Login</router-link></li>
            <li class="nav-item nav-signup"><router-link to="signup">Signup</router-link></li>
          </div>
          <div v-else class="nav-right">
            <li class="nav-item">알람</li>
            <li class="nav-item">프사</li>
          </div>
        </div>
      </ul>
    </nav>
  </div>
</template>

<script>
import "./css/navbar.scss"
export default {
  data() {
    return {
      // 향후 vuex에서 로그인 여부를 가져오는 것으로 바꿔야함
      // test 용
      isLogin: false,
      isSearchClick: false,
      searchWord: '',
    }
  },
  
  methods: {
    onEnter() {
      // 향후 검색 페이지로 이동
      console.log(this.searchWord)
    },
  },
}
</script>

<style>
</style>
