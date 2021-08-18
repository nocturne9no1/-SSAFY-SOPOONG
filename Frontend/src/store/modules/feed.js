import axios from 'axios'
// import router from '@/router'

const state = {
  followingPeopleList: null,
}
const getters = {
  getFollowingPeopleList: state => state.followingPeopleList
}

const mutations = {
  SET_FOLLOWING_LIST(state, followingPeopleList) {
    state.followingPeopleList = followingPeopleList
  }
}
const actions = {
  // 팔로우한 사람들 피드 리스트 가져오기
  followingPeopleFeedsList(context, id) {
    axios.get('feed/list', { params: {page: 0, size: 30, relationFollowing: id }, headers: { 'X-AUTH-TOKEN' : context.rootGetters.getToken } })
     .then(res => {
       console.log('팔로잉한 사람들 피드 리스트 불러오기 성공', res.data)
      })
  },

  // 팔로우한 사람 리스트 불러오기
  followingPeopleList(context, id) {
    axios.get('follow/followingList', { params: {page: 0, size: 30, relationFollowing: id }, headers: { 'X-AUTH-TOKEN' : context.rootGetters.getToken } })
     .then(res => {
       context.commit('SET_FOLLOWING_LIST', res.data.data.success)
      })
  },

  // 팔로우 언팔로우
  follow(context, data) {
    axios.post('follow', { relationFollowing: data[0], relationFollowed: 'rlaghtjr2' }, { headers: { 'X-AUTH-TOKEN' : context.rootGetters.getToken } })
      .then(res =>
        console.log(res.data)
      )
      .catch(err => console.error(err))
  }
}


export default {
  state, getters, mutations, actions
}