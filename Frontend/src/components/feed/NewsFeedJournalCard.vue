<template>
  <!-- 클릭 div가 아닌 다른곳으로 옮겨줘야할듯. -->
  <div
    class="card"
    :style="{
      height: tH + 'px',
      gridRowEnd: gap,
      borderRadius: '20px',
    }"
    @mouseover="imgHoverCheck()"
    @mouseout="mouseOutCheck()"
  >
    <div class="image" @click="journalDetail()">
      <img :src="image.urls.small" class="image" :class="{ imgBlur : imgHover }" :style="{ width: `${imgSize()}%` }" @mouseover="imgHoverCheck()" @mouseout="mouseOutCheck()" />
    </div>
    <div class="profilePhotoDiv">
          <img class="photo" src="https://cloudfront-ap-northeast-1.images.arcpublishing.com/chosun/EKE66P73OGOHBUQ4TG5XP6JOTQ.jpg" alt="profile-photo">
    </div>
    <div class="nickFollowDiv">
      <p>{{ image.user.name }}</p>
      <!-- 들고있는 자료 쏴주는법 참고! -->
      <button v-if="!image.liked_by_user" @click="toFollow(image.user.name)" class="followButton">follow</button>
      <button v-else @click="toFollow(image.user.name)" class="unfollowButton">following</button>
    </div>
    <div class="likeBookMarkDiv">
      <!-- v-bind는 false값도 true로 인식? -->
      <i class="far fa-bookmark" :class="{ 'fas': scraped, 'fas-bookmark': scraped }" @click="scrapedJournal()" :v-show="imgHover" style="margin-right: 10px"></i>
      <i class="far fa-heart" :class="{ 'fas': liked, 'fas-heart': liked }" @click="likedJournal([id, travelIdx])" :v-show="imgHover"></i>
    </div>
    <div class="textDiv" v-show="imgHover" @click="journalDetail()">
      <h1>여행일지 제목</h1>
      <p class="travelReview">한줄 요약 : 정말 아름다웠던 여행지! 내가 사랑하고 사랑했던 프랑크푸르트! 어언 떠나온 지 7년이라는 세월이 흘러 스타크래프트 립버전 1.16 다운로드 $지금 가입시 16종의 히어로 증정 %$</p>
      <!-- 별점 -->
      <span class="fa fa-star" :class="{ checked : checkRating(1) }"></span>
      <span class="fa fa-star" :class="{ checked : checkRating(2) }"></span>
      <span class="fa fa-star" :class="{ checked : checkRating(3) }"></span>
      <span class="fa fa-star" :class="{ checked : checkRating(4) }" ></span>
      <span class="fa fa-star" :class="{ checked : checkRating(5) }"></span>
    </div>
    
  </div>
</template>

<script>
// import { mapActions } from 'vuex';
// import { mapGetters } from 'vuex'

export default {
  props: {
    image: {
      type: Object,
      required: true,
    },
  },

  data: () => ({
    gap: null,
    tH: null,
    imgHover: false,
    lt: 100,

    liked: false,
    scraped: false,
    isFollowing: false,

  }),

  watch: {
    image: {
      deep: true,
      immediate: true,
      handler() {
        console.log("하위 컴포넌트에서 watch체크")
      }
    }
  },

  created() {
    this.tH = Math.round(this.image.height / (this.image.width / 400));
    const gap = Math.round(this.tH / 10);
    this.gap = `span ${gap}`;
  },

  async updated() {
    // // 팔로우한 사람 동시에 버튼 바꾸기 위해 사용
    // this.$store.dispatch('followingPeopleList', this.$store.getters['getUserProfile'].userId)
    // // 값 가져오기
    // this.$store.getters['getFollowingPeopleList']
    // // 팔로잉한 유저 정보를 확인해서 일치한다면 following 버튼을 토글하기.
    // // followingUser목록을 돌면서, 현재 이미지 전체 목록도 돌아서 following 버튼을 동시에 토글해줘야 하는데..
    // // v-for마다 v-if줘서, image에 새 항목을 하나 준 후, 새 항목이 true라면 보이게 하고 전체 이미지 for문 돌면서 이미지에 저장된 닉네임 in FollowingList면 그거 그대로 다른 버튼도 true될수있또록!
    // for (var followingUser of this.$store.getters['getFollowingPeopleList']) {
    //   // if (this.image.user.name === 'Bundo Kim')
    //   console.log(this.image.user.name, '내가 마우스 올린 이미지 사용자')
    //   console.log(followingUser.userNickname, '팔로잉하는 사용자 닉네임 for문돌며 하나씩 출력')
    //   var followingButton = document.querySelector('.followButton')
    //   console.log(followingButton)
      
    // }
  },

  methods: {
    imgHoverCheck() {
      this.imgHover = true;
    },
    mouseOutCheck() {
      this.imgHover = false;
    },
    imgSize() {
      this.lt = 100
      return this.lt
    },
    // 별점을 백에서 받아와 정보 비교할 예정.
    checkRating(x) {
      if (x >= this.$store.getters['getRating']) {
        return 1
      }
      else return 1
    },
    // 좋아요 버튼 토글 구현. 게시글 내에 좋아요 저장 구현해야 함.
    likedJournal(idTravelIdx) {
      this.liked = !this.liked
      // 데이터 보내서 수정해야 함.
      this.$store.dispatch('like', idTravelIdx);
    },
    scrapedJournal() {
      this.scraped = !this.scraped
    },
    // 일지 디테일로 연결
    journalDetail() {
      // this.$router.push('/')
      console.log(this.image)
      // 멀티플 파라미터 쏘고 싶을 때
      this.$store.dispatch('', [this.image])
    },

    // 팔로우
    toFollow(userId) {
      this.isFollowing = !this.isFollowing;
      // 팔로우하기
      this.$store.dispatch('follow',[this.$store.getters['getUserProfile'].userId, userId]);
      // 상위로 데이터 쏘기
      this.$emit('onFollow', userId);
    },

  }
};
</script>

<style scoped>
.card {
  position: relative;
  
  background-color: gray;
  box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px;

}
.image > img {
  border-radius: 20px;
}

.image > img:hover {
  cursor: pointer;
  filter: blur(4px);
  opacity: .7;
}

/* 프로필 이미지 */
.profilePhotoDiv {
  position: absolute;
  top: 1rem;
  left: 1rem;

  /* width: 100%; */

  text-align: left;
}

.profilePhotoDiv > .photo {
  width: 50px;
  height: 60px;
  border-radius: 70%;
  overflow: hidden;
  position: relative;
  /* display: flex; */

  top : 100%;

  /* width: 100%;
  height: 100%;
  object-fit: cover; */
}

/* 닉네임 + 팔로우 Div */
.nickFollowDiv {
  position: absolute;
  top: 0.7rem;
  left: calc(80px);
  /* backdrop-filter: blur(10px); */
}

.nickFollowDiv > p {
  max-width: 200px;
  margin-block: 10px 5px;

  backdrop-filter: blur(2px);
  /* background-color: rgba(80, 110, 97, 0.1); */

  font-size: 18px;
  font-family: 'Do Hyeon';
  color: #F7F8F4;
  /* text-shadow: 3px 2px 2px #F2F1F6; */
  
  /* color: rgba(237, 119, 102, 1); */
}

/* 팔로우 버튼 */
.followButton {
  width: 75px;
  height: 20px;
  
  border: none;
  border-radius: 10px;

  color: #F7F8F4;
  font-family: 'Do Hyeon';
  background-color: rgba(183, 220, 204, 1);

  cursor: pointer;
}

.unfollowButton {
  width: 75px;
  height: 20px;
  
  border: none;
  border-radius: 10px;

  color: #F7F8F4;
  font-family: 'Do Hyeon';
  background-color: rgba(237, 119, 102, 1);

  cursor: pointer;
}

/* 좋아요, 북마크 */
.likeBookMarkDiv {
  position: absolute;
  top: 1.5rem;
  right: 1rem;

  /* width: 100%; */
  /* height: 60px; */

  text-align: right;

  font-size: 2ex;
  color: white;

}

i {
  cursor: pointer;
}

.fas-heart {
  color: rgba(237, 119, 102, 1);
  text-shadow: rgb(209, 48, 48) 1px 0 10px;
}

.fas-bookmark {
  color: pink;
  text-shadow: rgb(241, 157, 199) 1px 0 10px;
}

/* 이미지 위 출력 div */

.textDiv {
  position: absolute;
  bottom: 5%;

  width: 100%;

  text-align: center;

  color: white;
  font-weight: bold;

  cursor: pointer;

  /* transform: translateY(calc(70px + 1em));
  transition: all 1s; */
}

/* .textDiv:hover {
  transform: translateY(0);
  transition: all 1s;
} */


/* 글 div위에 mouse hover시에도 작동할 수 있도록 */
.imgBlur {
  filter: blur(4px);
  opacity: .7;
}

h1::after {
  content: "  ♥";
}

/* 여행글 */
.travelReview {
  text-align: center;

  /* 좌우패딩 */
  padding-inline: 20px;

  /* 한줄 */
  /* white-space: nowrap; */
  /* 한줄 마지막 줄임말 부분 처리 */
  /* text-overflow: ellipsis; */


  /* 여러줄 */
  white-space: normal;
  /* line-height의 배수 = height로 몇줄 지정 */
  height: 2em;
  line-height: 1;
  
  /* 여러줄 숨긴 부분 처리 + clamp가 height와 같아야 ...이 제대로 출력*/
  display: -webkit-box; 
  -webkit-line-clamp: 2; 
  -webkit-box-orient: vertical;

  /* 기준 넘는 글자 숨기기 */
  overflow: hidden; 
}

/* 별점 */
.checked {
  color: orange;
}


</style>