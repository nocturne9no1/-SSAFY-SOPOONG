import NewsFeed from '../views/feed/NewsFeed'
import FollowingPeopleFeed from '../views/feed/FollowingPeopleFeed'

export default [
  {
    path: '/feed',
    name: 'NewsFeed',
    component: NewsFeed,
  },
  {
    path: '/feed/follow',
    name: 'FollowingPeopleFeed',
    component: FollowingPeopleFeed
  }

]