import ChangePassword from '../components/accounts/ChangePassword.vue'
import DeleteAccount from '../components/accounts/DeleteAccount.vue'
import EmailAuthentication from '../views/accounts/EmailAuthentication.vue'
import ProfileSettings from '../views/accounts/ProfileSettings.vue'
import SignIn from '../views/accounts/SignIn.vue' 
import SignUp from '../views/accounts/SignUp.vue'

export default [
  {
    path: '/settings',
    name: 'Settings',
    component: ProfileSettings,
    children: [
      {
        // /user/:id/profile 과 일치 할 때
        // UserProfile은 User의 <router-view> 내에 렌더링 됩니다.
        path: 'changepassword',
        name: 'ChangePassword',
        component: ChangePassword,
      },
      {
        path: 'deleteaccount',
        name: 'DeleteAccount',
        component: DeleteAccount,
      },
    ]
  },
  {
    path: '/emailauthentication',
    component: EmailAuthentication,
  },
  {
    path: '/signin',
    name: 'SignIn',
    component: SignIn,
  },
  {
    path: '/signup',
    name: 'SignUp',
    component: SignUp,
  },
]