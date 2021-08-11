import axios from 'axios'
import router from '@/router'
import cookies from 'vue-cookies'

const state = {
  authToken: cookies.get('X-AUTH-TOKEN'),
  userProfile: null,
}
const getters = {
  isSignedIn: state => !!state.authToken,
  // 로그아웃시 정보 삭제용
  config: state => ({
    headers: {
      "X-AUTH-TOKEN": `Token ${state.authToken}`
    }
  })
}
const mutations = {
  SET_TOKEN(state, token) {
    state.authToken = token
  },
  GET_PROFILE(state, userData) {
    state.userProfile = userData
  }
}
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
    axios.get(`http://localhost:8080/user/${id}`) // 이거 각자 정보 불러오는 구조가 어떻게 될지?
      .then(res => {context.commit("GET_PROFILE", res.data)})
      .catch(err => console.error(err))
  },
  
  postSignInData(context, signInData) {
    axios.get('http://localhost:8080/auth/login', { params :{ id: signInData.id, password: signInData.password } })
      .then(res => {
        context.commit('SET_TOKEN', res.data.key) // 보내주는 cookie key 저장? 키값이 이렇게 오는게 맞나?
        cookies.set('X-AUTH-TOKEN', res.data.key) // 키 , 값, 만료일
        // this.$cookies.set('auth-token', res.data.key, "7d")  // 글로벌 설정으로 쿠키 가져올때(main.js).
        // 여기서도 프로필 정보 얻어와야될수도있음
        console.log(res.data);
        alert("로그인 성공!!>.<");
        router.push('/main')
      })
      .catch(err => console.error(err))
  },

  signUp(context, signUpData) {
    context.dispatch('postSignUpData', signUpData)
  },

  postSignUpData(context, signUpData) {
    axios.post('http://localhost:8080/auth/register', signUpData)
      .then(res => {
        // 여기서 할게 없음
        // 회원가입이 성공적으로 되었다 메세지?
        console.log(res)
        console.log(context)
        router.push('/signin')
      })
      .catch(err => console.error(err))
  },


}

export default {
  state, getters, mutations, actions
}