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
    <div class="likeDiv">
      <!-- v-bind는 false값도 true로 인식? -->
      <i class="far fa-heart" :class="{ 'fas': liked }" @click="likedJournal()" :v-show="imgHover"></i>
    </div>
    <div class="textDiv" v-show="imgHover">
      <h1>여행일지 제목</h1>
      <p>
        Lorem ipsum dolor sit, amet consectetur adipisicing elit. Cum, quo? Aut, nisi, libero perspiciatis eligendi porro quas architecto quibusdam, est tempora deserunt consectetur maxime eius. A rem unde quae aliquam.
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
    this.tH = Math.round(this.image.height / (this.image.width / 400));
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

/* 좋아요 */
.likeDiv {
  position: absolute;
  top: 1rem;
  right: 1rem;

  width: 100%;

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