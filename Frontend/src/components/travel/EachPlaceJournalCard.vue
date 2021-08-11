<template>
  <div
    class="card"
    :style="{
      height: tH + 'px',
      gridRowEnd: gap,
      backgroundColor: 'gray',
      borderRadius: '20px',
    }"
    @mouseover="imgHoverCheck()"
    @mouseout="mouseOutCheck()"
    @click="journalDetail()"
  >
    <div class="image">
      <img :src="image.urls.small" class="image" :class="{ imgBlur : imgHover }" :style="{ width: `${imgSize()}%` }" @mouseover="imgHoverCheck()" @mouseout="mouseOutCheck()" />
    </div>
    
    <div class="textDiv" v-show="imgHover">
      <h2>개별일지 제목</h2>
      <p>
        Lorem ipsum dolor sit amet consectetur adipisicing elit. Unde maiores corporis voluptate? Odit laboriosam ex modi quis accusantium omnis quo dolorum fugit. Itaque alias accusantium quas voluptatem, tempore possimus delectus.
      </p>
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
  }),

  created() {
    this.tH = Math.round(this.image.height / (this.image.width / 300));
    const gap = Math.round(this.tH / 10);
    this.gap = `span ${gap}`;
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
}
img {
  border-radius: 20px;
}

img:hover {
  cursor: pointer;
  filter: blur(4px);
  opacity: .7;
}
/* 프로필 */
.profileDiv {
  position: absolute;
  top: 1.2rem;
  left: 1rem;

  width: 100%;

  text-align: left;
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

h2 {
  /* font-size: 3vh; */
}
h2::after {
  content: " ★";
}

p {
  text-align: center;

  /* 좌우패딩 */
  padding-inline: 20px;

  /* 한줄 */
  /* white-space: nowrap; */
  /* 한줄 마지막 줄임말 부분 처리 */
  /* text-overflow: ellipsis; */


  /* 여러줄 */
  white-space: normal;
  /* line-height 배수로 몇줄 지정 */
  height: 3em;
  line-height: 1;
  
  /* 여러줄 숨긴 부분 처리 */
  display: -webkit-box; 
  -webkit-line-clamp: 3; 
  -webkit-box-orient: vertical;

  /* 기준 넘는 글자 숨기기 */
  overflow: hidden; 

  



}

/* 별점 */
.checked {
  color: orange;
}


</style>