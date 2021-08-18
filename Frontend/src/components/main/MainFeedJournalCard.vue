<template>
  <div
    class="card"
    :style="{
      height: tH + 'px',
      gridRowEnd: gap,
      borderRadius: '20px',
    }"
    @mouseover="imgHoverCheck()"
    @mouseout="mouseOutCheck()"
    @click="journalDetail()"
  >
    <div class="image">
      <!-- <img :src="image.urls.small" class="image" :class="{ imgBlur : imgHover }" :style="{ width: `${imgSize()}%` }" @mouseover="imgHoverCheck()" @mouseout="mouseOutCheck()" /> -->
      <img :src='imgSrc' alt="travelImage">
    </div>
    <div class="profilePhotoDiv">
          <img class="photo" src="https://cloudfront-ap-northeast-1.images.arcpublishing.com/chosun/EKE66P73OGOHBUQ4TG5XP6JOTQ.jpg" alt="profile-photo">
    </div>
    <div class="nickFollowDiv">
      <p>유저아이디</p>
      <button>following</button>
    </div>
    <div class="likeDiv">
      <!-- v-bind는 false값도 true로 인식? -->
      <i class="far fa-heart" :class="{ 'fas': liked }" @click="likedJournal()" :v-show="imgHover"></i>
    </div>
    
    <div class="textDiv" v-show="imgHover">
      <!-- <h1>여행일지 제목</h1> -->
      <h1>{{ travel.travelTitle }}</h1>
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
// import { mapGetters } from 'vuex'

export default {
  props: {
    // image: {
    //   type: Object,
    //   required: true,
    // },
    travel: Object,
  },

  data: () => ({
    gap: null,
    tH: null,
    imgHover: false,
    lt: 100,
    liked: false,
  }),

  created() {
    // this.tH = Math.round(this.image.height / (this.image.width / 400));
    // const gap = Math.round(this.tH / 10);
    // this.gap = `span ${gap}`;
  },
  computed: {
    imgSrc() {
      return 'https://i5a404.p.ssafy.io/api/image/'+this.travel.imageOriginTitle
    }
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
    likedJournal() {
      this.liked = !this.liked
      // 데이터 보내서 수정해야 함.
      return this.liked
    },
    // 일지 디테일로 연결
    journalDetail() {
      // this.$router.push('/')
      console.log(this.image)
      // 멀티플 파라미터 쏘고 싶을 때
      this.$store.dispatch('', [this.image])
    }
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
  top: 1.2rem;
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

/* 닉네임 + 팔로우 버튼 */
.nickFollowDiv {
  position: absolute;
  top: 1rem;
  left: calc(80px);
}

.nickFollowDiv > p {
  margin-block: 10px 5px;

  font-size: 18px;
  font-family: 'Do Hyeon';
  color: #F7F8F4;
}

.nickFollowDiv > button {

}


/* 좋아요 */
.likeDiv {
  position: absolute;
  top: 1rem;
  right: 1rem;

  /* width: 100%; */
  /* height: 60px; */

  text-align: right;

  font-size: 4ex;
  color: white;

}

i {
  cursor: pointer;
}

.fas {
  color: red;
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
}

/* 글 div위에 mouse hover시에도 작동할 수 있도록 */
.imgBlur {
  filter: blur(4px);
  opacity: .7;
}

h1::after {
  content: "  ♥";
}

/* 별점 */
.checked {
  color: orange;
}


</style>