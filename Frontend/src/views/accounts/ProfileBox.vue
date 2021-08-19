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
        <img class="photo" :src="imgSrc" alt="profile-photo">
      </div>
      <div class="text-box">
        <div class="nickname">
          {{profile.userNickname}}
        </div>
        <div class="email">
          {{profile.userEmail}}
        </div>
        <div class="introduce">
          {{profile.userComment}}
        </div>
        <div class="follow">
          <div class="following">
            <div>
              following
            </div>
            <div>
              {{profile.followings}}
            </div>
          </div>
          <div class="follower">
            <div>
              Followers
            </div>
            <div>
              {{profile.followers}}
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
      // 고마웠다 데이터야...
      nickname: 'deriyakki',
      email: 'nocturne9no1@gmail.com',
      introduce: 'hi',
      following: 1,
      follower: 1000,

      profile: this.$store.state.accounts.userProfile
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

  computed: {
    imgSrc() {
      return 'https://i5a404.p.ssafy.io/api/image/'+this.$store.state.accounts.userProfile.imageOriginTitle
    }
  },

  methods: {}
}
</script>