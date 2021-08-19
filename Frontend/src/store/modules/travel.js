import axios from 'axios'
import router from '@/router'

const state = {
  myTravelJournal: null,
  presentTravel: null,
  presentTravelIdx: null,
  travelDetailList: null,
}
const getters = {
  // 나의 전체 여행일지
  getMyTravelJournal: state => state.myTravelJournal,
  // 현재 보고있는 여행일지 총정보
  getTravel: state => state.presentTravel,
  // 현재 여행일지의 모든 위치일지
  getTravelDetail: state => state.travelDetailList,
  // 현재 보고 있는 여행일지 인덱스
  getPresentTravelIdx: state => state.presentTravelIdx,
}

const mutations = {
  SET_MY_TRAVEL_JOURNAL(state, journals) {
    state.myTravelJournal = journals
  },
  SET_TRAVEL_DETAIL(state, travelDetail) {
    state.presentTravel = travelDetail[0]
    state.presentTravelIdx = travelDetail[1]
    state.travelDetailList = travelDetail[2]
  }
}
const actions = {
  travelJournalList(context, userId) {
    axios.get('travel/travelList', { params: {userId : userId}, headers: { 'X-AUTH-TOKEN' : context.rootState.accounts.authToken, 'Access-Control-Allow-Origin': '*' } })
      .then(res => {
        context.commit("SET_MY_TRAVEL_JOURNAL", res.data.data)
      })
  },
  
  travelDetail(context, travel) {
    axios.get('travel/travelDetail', { params: {travelIdx : travel.travelIdx}, headers: { 'X-AUTH-TOKEN' : context.rootState.accounts.authToken } })
      .then(res => {
        // 현재 여행일지 인덱스 기억하기.
        console.log(travel)
        context.commit('SET_TRAVEL_DETAIL', [travel, travel.travelIdx, res.data.data.placeList])
        router.push('/travel')
      })
  },
  
  deleteTravel(context, travelIdx) {
    axios.delete('travel/delete', { params: { travelIdx : travelIdx }, headers: { 'X-AUTH-TOKEN' : context.rootState.accounts.authToken } })
      .then(res => {
        console.log("삭제성공", res.data)
        if (context.state.myTravelJournal) {
          for (let idx in context.state.myTravelJournal) {
            if ( context.state.myTravelJournal[idx].travelIdx === travelIdx) {
              context.state.myTravelJournal.splice(idx, 1)
            }
          }
        }
        router.go();
      })
  },

  deleteTravelDetail(context, placeIdx) {
    axios.delete('place/delete', { params: { placeIdx : placeIdx }, headers: { 'X-AUTH-TOKEN' : context.rootState.accounts.authToken } })
      .then(res => {
        console.log("삭제오나료",res.data)
        if (context.state.travelDetailList) {
          for (let idx in context.state.travelDetailList) {
            if ( context.state.travelDetailList[idx].placeIdx === placeIdx ) {
              context.state.travelDetailList.splice(idx, 1)
              console.log("확인", context.state.travelDetailList)
              if (context.state.travelDetailList.length === 0) {
                // 상세 일지가 없으니 현재 일지도 지워버리기
                // context.dispatch('deleteTravel', context.state.presentTravelIdx)
                // 여행일지로 내보낼까?
                // router.push('/main');
              } 
            }
          }
        }
        // router.go();
      })
  }
}


export default {
  state, getters, mutations, actions
}