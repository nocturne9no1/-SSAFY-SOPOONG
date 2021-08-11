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
          :icon="icon"
          @click="openWindow(m)"
        />
        <gmap-info-window 
          @closeclick="m.windowOpen=false" 
          :opened="m.windowOpen"
          :position="m.position"
          :options="{
            pixelOffset: {
            width: 0,
            height: -25
          }
        }"
        >
          {{m.location}}
        </gmap-info-window>

      </div>
      </GmapMap>
      <div class="buttons">
        <!-- 향후 버튼 클릭 이벤트로 url 연결 -->
        <button class="btn">NEW</button>
        <!-- 향후 버튼 클릭으로 수정 이벤트 발생 -->
        <button class="btn">EDIT</button>
      </div>
      <!-- 만약 글이 없다면 첫 글 생성하라 알려줌 -->
      <div class="first-journal">
        <div class="plus-btn">
          <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="50" height="50" viewBox="0 0 70 70">
            <defs>
            <filter id="Shadow_3" x="9" y="10" width="52" height="52" filterUnits="userSpaceOnUse">
              <feOffset dy="2" input="SourceAlpha"/>
              <feGaussianBlur stdDeviation="2" result="blur"/>
              <feFlood flood-opacity="0.2"/>
              <feComposite operator="in" in2="blur"/>
              <feComposite in="SourceGraphic"/>
            </filter>
            <filter id="Shadow_2" x="0" y="0" width="50" height="50" filterUnits="userSpaceOnUse">
              <feOffset dy="1" input="SourceAlpha"/>
              <feGaussianBlur stdDeviation="5" result="blur-2"/>
              <feFlood flood-opacity="0.122"/>
              <feComposite operator="in" in2="blur-2"/>
              <feComposite in="SourceGraphic"/>
            </filter>
            <filter id="Shadow_1" x="7.5" y="10.5" width="55" height="55" filterUnits="userSpaceOnUse">
              <feOffset dy="4" input="SourceAlpha"/>
              <feGaussianBlur stdDeviation="2.5" result="blur-3"/>
              <feFlood flood-opacity="0.141"/>
              <feComposite operator="in" in2="blur-3"/>
              <feComposite in="SourceGraphic"/>
            </filter>
            <clipPath id="clip-path">
              <circle id="FAB_Mask" data-name="FAB Mask" cx="20" cy="20" r="20" fill="#fff"/>
            </clipPath>
            </defs>
            <g id="Light_FAB_3._Mini_Resting" data-name="Light 🌕/ FAB / 3. Mini/Resting" transform="translate(15 14)">
              <g id="Circle_Elevation_Dark_04dp" data-name="Circle Elevation/Dark 🌑/04dp" transform="translate(-109)">
                <g transform="matrix(1, 0, 0, 1, 94, -14)" filter="url(#Shadow_3)">
                  <rect id="Shadow_3-2" data-name="Shadow 3" width="40" height="40" rx="20" transform="translate(15 14)" fill="#fff"/>
                </g>
                <g transform="matrix(1, 0, 0, 1, 94, -14)" filter="url(#Shadow_2)">
                  <rect id="Shadow_2-2" data-name="Shadow 2" width="40" height="40" rx="20" transform="translate(15 14)" fill="#fff"/>
                </g>
                <g transform="matrix(1, 0, 0, 1, 94, -14)" filter="url(#Shadow_1)">
                  <rect id="Shadow_1-2" data-name="Shadow 1" width="40" height="40" rx="20" transform="translate(15 14)" fill="#fff"/>
                </g>
              </g>
              <g id="Fill" clip-path="url(#clip-path)">
                <path id="_FAB_Color" data-name="🎨 FAB Color" d="M0,0H40V40H0Z" fill="#ed7766"/>
              </g>
              <g id="State" clip-path="url(#clip-path)">
                <g id="States_Light_Primary_Container" data-name="States/Light 🌕/Primary Container">
                  <rect id="States_Light_Primary_Container_100_Overlay" data-name="States/Light 🌕/Primary Container 100%/Overlay" fill="rgba(255,255,255,0)"/>
                </g>
              </g>
              <g id="icon_content_add_24px" data-name="icon/content/add_24px" transform="translate(8 8)">
                <rect id="Boundary" width="24" height="24" fill="none"/>
                <path id="_Color" data-name=" ↳Color" d="M14,8H8v6H6V8H0V6H6V0H8V6h6Z" transform="translate(5 5)" fill="rgba(247,248,244,0.87)"/>
              </g>
            </g>
          </svg>

        </div>
        <div class="first-journal-word">
          Add Your First Journal!
        </div>
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
          windowOpen: false,
        },
        {
          location: 'LA',
          position: {
          lat: 34.86,
          lng: -119.1,
          },
          windowOpen: false,
        },
        {
          location: 'Osaka',
          position: {
            lat: 34,
            lng: 135,
          },
          windowOpen: false,
        },
        {
          location: 'Pokhara',
          position: {
            lat: 28.15,
            lng: 83.58,
          },
          windowOpen: false,
        },
        {
          location: 'Mumbai',
          position: {
            lat: 18.58,
            lng: 72.49,
          },
          windowOpen: false,
        },
        {
          location: 'London',
          position: {
            lat: 51.29,
            lng: 0,
          },
          windowOpen: false,
        },
        {
          location: 'Torres del Paine',
          position: {
            lat: -51,
            lng: -73,
          },
          windowOpen: false,
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
    openWindow(m) {
      m.windowOpen = true
    }
  }
}
</script>
