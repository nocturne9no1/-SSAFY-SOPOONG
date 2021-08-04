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
        <img class="photo" src="https://cloudfront-ap-northeast-1.images.arcpublishing.com/chosun/EKE66P73OGOHBUQ4TG5XP6JOTQ.jpg" alt="profile-photo">
      </div>
      <div class="text-box">
        <div class="nickname">
          {{nickname}}
        </div>
        <div class="email">
          {{email}}
        </div>
        <div class="introduce">
          {{introduce}}
        </div>
        <div class="follow">
          <div class="following">
            <div>
              following
            </div>
            <div>
              {{following}}
            </div>
          </div>
          <div class="follower">
            <div>
              Followers
            </div>
            <div>
              {{follower}}
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
      nickname: 'deriyakki',
      email: 'nocturne9no1@gmail.com',
      introduce: 'hi',
      following: 1,
      follower: 1000,
    };
  },
  // 페이지 생성 전, 프로필 데이터 받아와야 함
  beforeCreate() {},
  created() {}, 
  beforeMount() {}, 
  mounted() {}, 
  beforeUpdate() {},
  updated() {},
  beforeUnmount() {}, 
  unmounted() {},
  methods: {}
}
</script>