import NotFound from '../../views/404View.vue'
import HomeView from '../../views/HomeView.vue'
import WelcomeView from '../../views/WelcomeView.vue'
import LoginView from '../../views/LoginView.vue'
//import UsersView from '../../views/nav1/UsersView.vue'
//import FormView from '../../views/nav1/FormView.vue'
import UsersListView from '../../views/nav1/UsersListView.vue'
import DeptsListView from '../../views/nav1/DeptsListView.vue'
//import EchartsView from '../../views/charts/EchartsView.vue'  //这玩意巨大 5m
//import Page1 from '../../views/nav1/Page1.vue'
//import Page2 from '../../views/nav1/Page2.vue'
//import Page3 from '../../views/nav1/Page3.vue'
//import Page4 from '../../views/nav2/Page4.vue'
//import Page5 from '../../views/nav2/Page5.vue'

let routes = [
  {
    path: '/login',
    component: LoginView,
    name: '',
    hidden: true
  },
  {
    path: '/404',
    component: NotFound,
    name: '',
    hidden: true
  },
  {
    path: '/',
    component: HomeView,
    name: '',
    iconCls: 'fa fa-home',
    leaf: true,//只有一个节点
    children: [
      {path: '/welcome', component: WelcomeView, name: '首页'}
    ]
  },
  {
    path: '/',
    component: HomeView,
    name: '公司人员组织库',
    iconCls: 'fa fa-address-card-o',
    children: [
      {path: '/usersList', component: UsersListView, name: '员工列表', iconCls: 'fa fa-user'},
      {path: '/deptsList', component: DeptsListView, name: '部门列表', iconCls: 'fa fa-street-view'}
    ]
  },
  {
    path: '*',
    hidden: true,
    redirect: {path: '/404'}
  }
]

export default routes

