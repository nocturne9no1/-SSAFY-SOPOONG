<template>
  <div class="profile-box">
    <div class="setting-btn">
      <router-link to="/settings">
        <span class="setting-icon">
          <i class="fas fa-lg fa-cog"></i>
        </span>
      </router-link>
    </div>
    <div class="container">
      <div class="profile-photo-box">
        <img class="photo" :src="profileData.image" alt="profile-photo">
      </div>
      <div class="text-box">
        <div class="nickname">
          {{ profileData.userNickname }}
        </div>
        <div class="email">
          {{ profileData.userEmail }}
        </div>
        <div class="introduce" v-if="profileData.userComment">
          {{ profileData.userComment }}
        </div>
        <div class="introduce" v-else>
          {{ introduce }}
        </div>
        <div class="follow">
          <div class="following">
            <div>
              following
            </div>
            <div>
              {{ this.$store.getters['getUserProfile'].followings }}
            </div>
          </div>
          <div class="follower">
            <div>
              Followers
            </div>
            <div >
              {{ profileData.followers }}
            </div>
          </div>
        </div>
      </div>
      
    </div>
  </div>
</template>

<script>
import '../../components/css/profilebox.scss'

export default {
  name:'',
  components: {},
  data() {
    return {
      profileData: null,
      nickname: 'deriyakki',
      email: 'nocturne9no1@gmail.com',
      introduce: 'hi',
      following: 1,
      follower: 1000,
    };
  },
  // 페이지 생성 전, 프로필 데이터 받아와야 함
  beforeCreate() {},
  created() {
    if ( this.$store.getters['isSignedIn'] ) {
      this.profileData = this.$store.getters['getUserProfile']
    }
  }, 
  beforeMount() {}, 
  mounted() {
  }, 
  beforeUpdate() {},
  updated() {
  },
  beforeUnmount() {}, 
  unmounted() {},
  watch: {
  },
  methods: {}
}
</script>