<template>
  <div class="createjourney-wrap">
    <!-- 현재 스텝 표시 -->
    <div>
      {{nowStep}}
    </div>
    <div>
      <UploadImage
        v-if="nowStep === 1"
        @next-step="nextStep"
        @upload-image="uploadImage"
      />
      <CreatePlace
        v-else-if="nowStep === 2"
        :images="images"
        :travel="travel"
        @next-step="nextStep"
      />
      <PackageJourney
        v-else-if="nowStep === 3"
        :images="images"
        :travel="travel"
      />
    </div>
  </div>
</template>
<script>
import UploadImage from './UploadImage.vue'
import CreatePlace from './CreatePlace.vue'
import PackageJourney from './PackageJourney.vue'


export default {
  name:'createJourney',
  components: {
    UploadImage, CreatePlace, PackageJourney,
  },
  data() {
    return {
      // 최종적으로 서버에 넘길 자료
      travel: {
        travelTitle: '',
        travelContent: '',
        travelIsVisible: true,
        placeList: [],
        imageIdx: '',
        travelLat: '',
        travelLng: '',
        mainImage: '',
      },

      // 1. image upload 이후 2. markerDetail로 넘기기 전에 and 3. 마무리 에서 쓸 사진 자료 유통 창고
      images: [],

      // 현재 몇 단계인지 알려줄 변수
      nowStep: 1,
    };
  },

  beforeCreate() {},
  created() {}, 
  beforeMount() {}, 
  mounted() {}, 
  beforeUpdate() {},
  updated() {},
  beforeUnmount() {}, 
  unmounted() {},

  watch: {
    // 새로운 이미지 업로드 될 시마다 marker 정보 생성
    images: function () {
      // images 배열에서 각 image 에 대한 marker 정보 생성
      for (let image of this.images) {
        console.log('hi')
        console.log(image)
        const marker = new Object()

        marker.title = ''
        marker.comment = ''
        marker.category = {
          main: '',
          sub: '',
        }
        marker.rates = {
          rate1: 0,
          rate2: 0,
          rate3: 0,
        }
        marker.transport = ''
        marker.imageIdx = 0
        marker.visitDate = image.dateTime
        marker.position = {
          lat: image.position.lat,
          lng: image.position.lng
        }
        const eachImage = new Object
        eachImage.file = image.file
        eachImage.position = image.position
        eachImage.isPlaceLeader = true
        eachImage.isTravelLeader = false
        eachImage.preview = image.preview
        eachImage.dateTime = image.dateTime
        marker.fileList = [
          eachImage
        ]

        marker.imageNum = image.number

        this.travel.placeList.push(marker)
      }
    }

  },

  methods: {
    nextStep() {
      this.nowStep += 1
    },

    uploadImage(images) {
      this.images = images
    },


  }
}
</script>