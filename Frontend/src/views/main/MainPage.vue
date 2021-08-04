<template>
  <div style="display: flex;">
    <ProfileBox/>
    <div class="main-wrap">
      <GmapMap
        ref="mapRef"
        :center="center"
        :zoom="2"
        class="google-map"
      >
      <div v-for="(m, index) in markers" :key="index">
        <gmap-marker
          :position="m.position"
          :clickalbe="true"
          :draggable="true"
          :icon="icon"
          @click="openWindow"
        />
        <gmap-info-window 
          @closeclick="m.windowOpen=false" 
          :opened="m.windowOpen" 
          :position="m.position"
        >
          {{m.location}}
        </gmap-info-window>

      </div>
      </GmapMap>
      <div class="buttons">
        <button class="btn">NEW</button>
        <button class="btn">EDIT</button>
      </div>
    </div>
  </div>
</template>
<script>
import ProfileBox from '../accounts/ProfileBox.vue'
import * as VueGoogleMaps from 'vue2-google-maps'
import Vue from 'vue'
// import GmapCustomMarker from 'vue2-gmap-custom-marker'
import '../../components/css/mainpage.scss'

Vue.use(VueGoogleMaps, {
  load: {
    key: "AIzaSyBwPv3o2CKXGTbC5qPIjTCdU5F1ToEExio",
    libraries: "places",
  }
})

export default {
  name:'MainPage',
  components: {
    ProfileBox,
    // 'gmap-custom-marker': GmapCustomMarker,
  },
  data() {
    return {
      // 처음 지도 띄울 때 중심의 좌표
      // 현재는 서울
      center: {
        lat: 37.5,
        lng: 127,
      },
      icon: {
        url: require("../../assets/temppin.png"),
        scaledSize: {width: 28, height: 28},
        labelOrigin: {x: 16, y: -10}
      },
      marker: [
          // 서울
        {
            lat: 37.5,
            lng: 127,

        },
        {
            lat: 34.86,
            lng: -119.1,

        },
      ],
      // 다녀온 나라 좌표
      // 향후 백에서 가져와야 함
      // 아래는 예시 좌표
      markers: [
        {
          location: 'Seoul',
          position: {
            lat: 37.5,
            lng: 127,
          },
          windowOpen: false
        },
        {
          location: 'LA',
          position: {
          lat: 34.86,
          lng: -119.1,
          },
          windowOpen: false
        },
        {
          location: 'Osaka',
          position: {
            lat: 34,
            lng: 135,
          },
          windowOpen: false
        },
        {
          location: 'Pokhara',
          position: {
            lat: 28.15,
            lng: 83.58,
          },
          windowOpen: false
        },
        {
          location: 'Mumbai',
          position: {
            lat: 18.58,
            lng: 72.49,
          },
          windowOpen: false
        },
        {
          location: 'London',
          position: {
            lat: 51.29,
            lng: 0,
          },
          windowOpen: false
        },
        {
          location: 'Torres del Paine',
          position: {
            lat: -51,
            lng: -73,
          },
          windowOpen: false
        },
      ],
      info_marker: null,
      infowindow: {lat: 10, lng: 10.0},
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
  methods: {
    openWindow() {
      this.windowOpen = true
    }
  }
}
</script>