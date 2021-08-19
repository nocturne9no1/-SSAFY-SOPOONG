import axios from 'axios'
import router from '@/router'

const state = {
  myTravelJournal: null,
  presentTravelIdx: null,
  travelDetailList: null,
  nowTravel: null,
}
const getters = {
  getMyTravelJournal: state => state.myTravelJournal,
  getTravelDetail: state => state.travelDetailList,
  getPresentTravelIdx: state => state.presentTravelIdx,
  getNowTravel: state => state.nowTravel
}

const mutations = {
  SET_MY_TRAVEL_JOURNAL(state, journals) {
    state.myTravelJournal = journals
  },
  SET_TRAVEL_DETAIL(state, travelDetail) {
    state.travelDetailList = travelDetail[1]
    state.presentTravelIdx = travelDetail[0]
  },
  SET_NOW_TRAVEL(state, nowTravel) {
    state.nowTravel = nowTravel
  }
}
const actions = {
  travelJournalList(context, userId) {
    axios.get('travel/travelList', { params: {userId : userId}, headers: { 'X-AUTH-TOKEN' : context.rootState.accounts.authToken, 'Access-Control-Allow-Origin': '*' } })
      .then(res => {
        context.commit("SET_MY_TRAVEL_JOURNAL", res.data.data)
      })
  },
  
  travelDetail(context, travelIdx) {
    axios.get('travel/travelDetail', { params: {travelIdx : travelIdx}, headers: { 'X-AUTH-TOKEN' : context.rootState.accounts.authToken } })
      .then(res => {
        // 현재 여행일지 인덱스 기억하기.
        context.commit('SET_TRAVEL_DETAIL', [travelIdx, res.data.data.placeList])
        router.push('/travel')
      })
  },

  deleteTravelDetail(context, placeIdx) {
    axios.delete('travel/delete', { params: { placeIdx : placeIdx }, headers: { 'X-AUTH-TOKEN' : context.rootState.accounts.authToken } })
      .then(res => {
        console.log(res.data)
      })
  },

  nowTravel(context, nowTravel) {
    context.commit("SET_NOW_TRAVEL", nowTravel)
  }
}


export default {
  state, getters, mutations, actions
}