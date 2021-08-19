<template>
  <div style="display: flex;">
    <profile-box />
    <div class="journalCardList">
      <div class="wrapper">
        <div class="heading">
          <h1>SoPoong 소식</h1>
          <!-- <button class="filterButton">Filter</button> -->
        </div>
        <div class="cards" v-if="images.length">
          <news-feed-journal-card
            v-for="image in images"
            :key="image.travelIdx"
            :image="image"
            @onFollow="onFollow"
            @onLike="onLike"
          />
        </div>
        <div class="cards-loading" v-else>
          Loading image....
        </div>
      </div>
    </div>
    <FeedFilter />
  </div>
</template>

<script>
import NewsFeedJournalCard from "@/components/feed/NewsFeedJournalCard.vue";
import axios from "axios";
import ProfileBox from '@/views/accounts/ProfileBox.vue';
import FeedFilter from '@/components/FeedFilter.vue';

export default {
  name: "",
  components: {
    NewsFeedJournalCard,
    ProfileBox,
    FeedFilter,
  },
  data() {
    return {
      sampleData: "",
      images: [],
      like: false,
      // watch에서 like반복 막기 위함
      likeData: null,
      follow: false,
      // watch에서 follow반복 막기 위함
      followData: null,
    };
  },

  watch: {
    follow: {
      deep: true,
      immediate: true,
      async handler() {
        // this.getAllFeedsList()
        // setTimeout(() => this.getAllFeedsList(), 1000)
        if ( this.followData !== null) {
          await axios.post('follow', { relationFollowing: this.followData[0], relationFollowed: this.followData[1] }, { headers: { 'X-AUTH-TOKEN' : this.$store.getters['getToken'] }})
            .then(res => {
              res
          })
          await axios.get('feed/all', { params: { page:0, size:30, userId: this.$store.getters['getUserProfile'].userId} })
            .then(res => {
                this.images = res.data.data.success
                this.$store.commit('SET_ALL_FEEDS_LIST', res.data.data.success)
          })
          // 다른 페이지도 입히기
          await axios.get('feed/follow', { params: {page: 0, size: 30, userId: this.$store.getters['getUserProfile'].userId}, headers: { 'X-AUTH-TOKEN' : this.$store.getters['getToken'] }})
            .then(res => {
              this.$store.commit('SET_FOLLOWING_PEOPLE_FEEDS_LIST', res.data.data.success)
          })
        }
      }
    },

    like: {
      deep: true,
      immediate: true,
      async handler() {
        if ( this.likeData !== null) {
          await axios.post('good', { userId: this.likeData[0], travelIdx: this.likeData[1] }, { headers: { 'X-AUTH-TOKEN' : this.$store.getters['getToken'] }})
            .then(res => {
              console.log(res.data)
          })
          await axios.get('feed/all', { params: { page:0, size:30, userId: this.$store.getters['getUserProfile'].userId} })
            .then(res => {
                this.images = res.data.data.success
                this.$store.commit('SET_ALL_FEEDS_LIST', res.data.data.success)
          })
          await axios.get('feed/follow', { params: {page: 0, size: 30, userId: this.$store.getters['getUserProfile'].userId}, headers: { 'X-AUTH-TOKEN' : this.$store.getters['getToken'] }})
            .then(res => {
              this.$store.commit('SET_FOLLOWING_PEOPLE_FEEDS_LIST', res.data.data.success)
          })
        }
      }
    }
  
  },

  beforeCreate() {},
  created() {
    console.log('생성')
    this.getAllFeedsList();
  },
  beforeMount() {},
  mounted() {
    // 팔로우 중인 사람의 게시글 내 'follow'버튼을 'following'으로 바꾸기 위해 추가.
    // 여기 필요없을듯
    if (this.$store.getters['getUserProfile'].userId) {
      this.$store.dispatch('followingPeopleFeedsList', this.$store.getters['getUserProfile'].userId)
    }
  },
  beforeUpdate() {
  },
  updated() {
  },
  beforeUnmount() {},
  unmounted() {},
  methods: {
    // 전체 피드 가져와서 카드 적용
    getAllFeedsList() {
      // 로그인 유저
      if (this.$store.getters['isSignedIn']) {
        this.$store.dispatch('allFeedsList', this.$store.getters['getUserProfile'].userId)
      } else {
        this.$store.dispatch('allFeedsList', null)
      }
      // setTimeout(() => this.images = this.$store.getters['getAllFeedsList'], 1000)
      this.images = this.$store.getters['getAllFeedsList']
      // console.log(this.images)
    },

    onFollow(data) {
      this.follow = !this.follow
      this.followData = data
    },

    onLike(data) {
      this.like = !this.like
      this.likeData = data
    }
  }
};
</script>

<style scoped>
/* .journalCardList {
  display: block;
  position: inherit;
} */

.wrapper {
  margin-left: 10rem;
  margin-top: 7rem;
  margin-right: 3rem;
}
.heading h1 {
  text-align: center;
  padding: 0 1rem;
}
.wrapper > div {
  margin: 3rem auto;
}
.wrapper .cards {
  display: grid;
  column-gap: 10px;
  row-gap: 7px;
  grid-template-columns: repeat(3, 400px);
  justify-content: center;
  max-width: var(--card-container-width);
}
.cards-loading {
  font-size: 1.25rem;
  text-align: center;
}

.heading {
  position: relative;
}

/* .filterButton {
  position: absolute;
  right: 2rem;
} */
</style>
