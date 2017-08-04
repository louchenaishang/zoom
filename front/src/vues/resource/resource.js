import Vue from 'vue'
import VueResource from 'vue-resource'

import sign from '../../common/sign'

Vue.use(VueResource);

//If your web server can't handle requests encoded as application/json, you can enable the emulateJSON option. This will send the request as application/x-www-form-urlencoded MIME type, as if from an normal HTML form.
Vue.http.options.emulateJSON = true
//If your web server can't handle REST/HTTP requests like PUT, PATCH and DELETE, you can enable the emulateHTTP option. This will set the X-HTTP-Method-Override header with the actual HTTP method and use a normal POST request.
Vue.http.options.emulateHTTP = true

//others see @ https://github.com/pagekit/vue-resource/blob/develop/docs/http.md


//interceptors
Vue.http.interceptors.push((request, next) => {
  // 请求发送前的处理逻辑
  sign(request);
  next((response) => {
    // 请求发送后的处理逻辑

    // 根据请求的状态，response参数会返回给successCallback或errorCallback
    return response
  })
})
