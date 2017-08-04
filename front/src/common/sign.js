import uuid from 'uuid'
import md5 from 'md5'

const appId = 'oR3YV1tti8'
const appSecret = 'tuJM@)DirR9%1)ijp#'


export default function (request) {

  var appNonceStr = uuid.v1().replace(new RegExp('-', "gm"), '')
  var appSign = md5(appId + appSecret + appNonceStr)

  if(request.params === undefined || request.params === null || request.params ===''){
    request.params = {}
  }

  request.params.ai = appId
  request.params.ans = appNonceStr
  request.params.as = appSign

}
