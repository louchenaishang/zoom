import NotFound from '../../views/404View.vue'
import HomeView from '../../views/HomeView.vue'
import LoginView from '../../views/LoginView.vue'
import UsersView from '../../views/nav1/UsersView.vue'
import FormView from '../../views/nav1/FormView.vue'
import TableView from '../../views/nav1/TableView.vue'
//import EchartsView from '../../views/charts/EchartsView.vue'  //这玩意巨大 5m
import Page1 from '../../views/nav1/Page1.vue'
import Page2 from '../../views/nav1/Page2.vue'
import Page3 from '../../views/nav1/Page3.vue'
import Page4 from '../../views/nav2/Page4.vue'
import Page5 from '../../views/nav2/Page5.vue'

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
    name: '导航一',
    iconCls: 'el-icon-message',
    children: [
      {path: '/users', component: UsersView, name: 'list'},
      {path: '/form', component: FormView, name: 'form'},
      {path: '/table', component: TableView, name: 'table'},
      {path: '/page1', component: Page1, name: '页面1'},
      {path: '/page2', component: Page2, name: '页面2'}
    ]
  },
  {
    path: '/',
    component: HomeView,
    name: '导航二',
    iconCls: 'fa fa-id-card-o',
    children: [
      {path: '/page3', component: Page3, name: '页面3'},
      {path: '/page4', component: Page4, name: '页面4'}
    ]
  },
  {
    path: '/',
    component: HomeView,
    name: '',
    iconCls: 'fa fa-address-card',
    leaf: true,//只有一个节点
    children: [
      {path: '/page5', component: Page5, name: '导航三'}
    ]
  },
  // {
  //   path: '/',
  //   component: HomeView,
  //   name: '图表',
  //   iconCls: 'fa fa-bar-chart',
  //   children: [
  //     {path: '/echarts', component: EchartsView, name: 'Charts'}
  //   ]
  // },
  {
    path: '*',
    hidden: true,
    redirect: {path: '/404'}
  }
]

export default routes

