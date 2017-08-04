export default function (request) {
  let token = JSON.parse(sessionStorage.getItem('token'));
  if (token) {
    request.headers.Authorization = 'Bearer ' + token
  }
}
