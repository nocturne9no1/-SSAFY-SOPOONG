<template>
  <div style="display: flex;">
    <profile-box />
    <div class="journalCardList">
      <div class="wrapper">
        <div class="heading">
          <h1>메인 뉴스 피드</h1>
          <!-- <button class="filterButton">Filter</button> -->
        </div>
        <div class="cards" v-if="images.length">
          <news-feed-journal-card
            v-for="image in images"
            :key="image.id"
            :image="image"
            @onFollow="onFollow"
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

const DEFAULT_IMAGES_COUNT = 30;

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
    };
  },

  beforeCreate() {},
  async created() {
    // 기본이 development로 설정되어 있고 , 그 외에도 여러 모드가 있다.
    // console.log(process.env.NODE_ENV)
    if (process.env.NODE_ENV === "production") {
      await this.getRandomImages(DEFAULT_IMAGES_COUNT);
    } else {
      await this.getRandomImagesFromLocal();
    }
  },
  beforeMount() {},
  async mounted() {
    // 팔로우 중인 사람의 게시글 내 'follow'버튼을 'following'으로 바꾸기 위해 추가.
    await this.$store.dispatch('followingPeopleFeedsList', this.$store.getters['getUserProfile'].userId)
  },
  beforeUpdate() {
  },
  updated() {
  },
  beforeUnmount() {},
  unmounted() {},
  methods: {
    /**
     * @param {number} count
     */
    async getRandomImages(count) {
      try {
        const { data } = await axios.get(
          process.env.VUE_APP_URL + "/photos/random",
          {
            headers: {
              Authorization: "Client-ID " + process.env.VUE_APP_ACCESS_KEY,
            },
            params: {
              count,
            },
          }
        );
        // Binding data to this component data
        this.images = data;

      } catch (error) {
        console.error(error);
      }
    },
    async getRandomImagesFromLocal() {
      try {
        const { default: localData } = await import('@/assets/test_data.json')
        this.images = localData

      } catch (err) {
        console.error(err)
      }
    },

    onFollow(data) {
      for (let image of this.images) {
        if (image.user.name === data && image.isFollowing) {
          // isFollowing은 image내에 새 필드를 추가하는건데 , 자식 컴포넌트가 실시간 변화를 감지해 내지 못함.
          // image.isFollowing = !image.isFollowing
          image.liked_by_user = !!image.liked_by_user
        }
        else if (image.user.name === data) {
          // image.isFollowing = true
          // image.liked_by_user = !image.liked_by_user
        }
      }
      // 실시간 변동 체크용
      this.images[0].liked_by_user = !this.images[0].liked_by_user
    }
  },
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
