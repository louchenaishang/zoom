import axios from 'axios'
import router from '@/vues/route/route'
import addr from '@/api/addr'
import auth from '@/common/auth'
import NProgress from 'nprogress'
import {Notification} from 'element-ui';

// `withCredentials` indicates whether or not cross-site Access-Control requests
// should be made using credentials
// default false
// 跨域请求设置服务端返回的cookie
axios.defaults.withCredentials = true

//拦截器,请求
axios.interceptors.request.use(function (config) {
  auth(config)
  NProgress.start()
  return config
}, function (error) {
  NProgress.done()
  return Promise.reject(error)
})

//拦截器,响应
axios.interceptors.response.use(function (response) {
  NProgress.done()
  return response
}, function (error) {
  NProgress.done()
  if (error.response) {
    //console.log(error.response)
    let {status, statusText, data} = error.response
    let {exception, message, path} = data
    let resError = data.error
    let resStatus = data.status
    if (exception === 'org.springframework.security.authentication.BadCredentialsException') {
      Notification.error({
        title: '登陆失败',
        message: '账号或者密码错误'
      })
    } else if (status === 401) {
      Notification.error({
        title: '错误',
        message: '401,未认证,请您重新登陆'
      })
      sessionStorage.removeItem('token');
      sessionStorage.removeItem('user');
      router.replace({
        path: 'login',
        query: {redirect: router.currentRoute.fullPath}
      })
    } else if (status === 403) {
      Notification.error({
        title: '错误',
        message: '403,未授权,' + '[' + path + ']' + '您没有权限操作'
      })
    } else if (status === 404) {
      Notification.error({
        title: '错误',
        message: '404,该资源[' + path + ']不存在'
      })
    } else if (status === 500) {
      Notification.error({
        title: '错误',
        message: '500,' + '[' + path + ']' + ',' + message
      })
    } else if (status === 504) {
      Notification.error({
        title: '错误',
        message: '504,网关超时连接不上rest服务'
      })
    } else {
      Notification.error({
        title: '错误',
        message: status + ',' + '[' + path + ']' + ',' + message
      })
    }
  } else {
    Notification.error({
      title: '请求异常',
      message: '服务器网络连接错误'
    })
  }
  return Promise.reject(error)
})


let base = addr

const requestLogin = params => {
  return axios.post(`${base}/auth?captchaId=${params.captchaId}&captcha=${params.captcha}`, params)
}

const getPrincipal = params => {
  return axios.get(`${base}/auth/principal`, params)
}

const getApiVersion = params => {
  return axios.get(`${base}/admin/system/version`, params)
}

const getSystemSetting = params => {
  return axios.get(`${base}/admin/system/setting`, params)
}

const saveSystemSetting = params => {
  return axios.post(`${base}/admin/system/setting`, params)
}

const getUserListPage = params => {
  return axios.get(`${base}/admin/user`, {params: params})
}

const removeUser = params => {
  return axios.delete(`${base}/admin/user/${params.id}`, {params: params})
}

const editUser = params => {
  return axios.put(`${base}/admin/user/${params.id}`, params)
}

const addUser = params => {
  return axios.post(`${base}/admin/user`, params)
}

const getSkuList = params => {
  return axios.get(`${base}/admin/sku`, {params: params})
}

const getSku = params => {
  return axios.get(`${base}/admin/sku/${params.id}`, {params: params})
}

const editSku = params => {
  return axios.put(`${base}/admin/sku/${params.id}`, params)
}

const addSku = params => {
  return axios.post(`${base}/admin/sku`, params)
}

const getStoreList = params => {
  return axios.get(`${base}/admin/store`, {params: params})
}

const getStore = params => {
  return axios.get(`${base}/admin/store/${params.id}`, {params: params})
}

const editStore = params => {
  return axios.put(`${base}/admin/store/${params.id}`, params)
}

const addStore = params => {
  return axios.post(`${base}/admin/store`, params)
}

const getStoreTop10 = params => {
  return axios.get(`${base}/admin/store/top10`, {params: params})
}

const getOrderList = params => {
  return axios.get(`${base}/admin/order`, {params: params})
}

const getOrder = params => {
  return axios.get(`${base}/admin/order/${params.id}`, {params: params})
}

const editOrder = params => {
  return axios.put(`${base}/admin/order/${params.id}`, params)
}

const addOrder = params => {
  return axios.post(`${base}/admin/order`, params)
}

const getMemberList = params => {
  return axios.get(`${base}/admin/member`, {params: params})
}

const getMember = params => {
  return axios.get(`${base}/admin/member/${params.id}`, {params: params})
}

const editMember = params => {
  return axios.put(`${base}/admin/member/${params.id}`, params)
}

const addMember = params => {
  return axios.post(`${base}/admin/member`, params)
}

//对外暴露的api方法
const Api = {
  requestLogin,
  getPrincipal,
  getApiVersion,
  getSystemSetting,
  saveSystemSetting,
  getUserListPage,
  removeUser,
  editUser,
  addUser,
  getSkuList,
  getSku,
  editSku,
  addSku,
  getStoreList,
  getStore,
  editStore,
  addStore,
  getStoreTop10,
  getOrderList,
  getOrder,
  editOrder,
  addOrder,
  getMemberList,
  getMember,
  editMember,
  addMember,
}

export default Api
