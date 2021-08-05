import axios from 'axios'
import router from '@/router'
// import cookies from 'vue-cookies'

const state = {}
const getters = {}
const mutations = {}
const actions = {
  requestEmailAuth(context, authKey) {
    console.log(context)
    const url = '???'
    axios.get(url, authKey)
      .then(res => {
        console.log(res.data)
        router.push('/')
      })
      .catch(err => {
        console.log(err)
      })
  },
  
  signIn(context, signInData) {
    // 로그인시도
    context.dispatch('postSignInData', signInData),
    // 프로필 정보 얻어오기
    context.dispatch('getProfile', signInData.id)
  },

  getProfile(context, id) {
    console.log(context, id, "accounts.js 29번째줄 개발하자.")
  },
  
  postSignInData(context, signInData) {
    axios.get('localhost:8080/auth/login', signInData)
      .then(res => {
        context.commit('SET_TOKEN', res.data.key) // 보내주는 cookie key 저장
        // 여기서도 프로필 정보 얻어와야될수도있음
        router.push('/')
      })
      .catch(err => console.error(err))
  },

  signUp(context, signUpData) {
    context.distpatch('postSignUpData', signUpData)
  },

  postSignUpData(context, signUpData) {
    axios.post('localhost:8080/auth/register', signUpData)
      .then(res => {
        // 여기서 할게 없음
        // 회원가입이 성공적으로 되었다 메세지?
        console.log(res)
        console.log(context)
      })
      .catch(err => console.error(err))
  },

}

export default {
  state, getters, mutations, actions
}