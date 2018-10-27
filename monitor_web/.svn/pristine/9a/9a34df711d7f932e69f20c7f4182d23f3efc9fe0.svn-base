import request from '@/utils/request'


export function loginByUsername(username, password) {
  const data = {
    grant_type:"password", 
    username,
    password
  }
  // let headers = {
  //   "Authorization": "Basic " + btoa(process.env.API_ID+":"+process.env.API_KEY)    
  // }
  return request({
    url: '/oauth/token',
    method: 'post',
    data,
    auth:{
      username:process.env.API_ID,
      password:process.env.API_KEY
    }
  })
}

export function autoLogin(re_token) {
  const data = {
    grant_type:"refresh_token", 
    refresh_token:re_token
  }
  // let headers = {
  //   "Authorization": "Basic " + btoa(process.env.API_ID+":"+process.env.API_KEY)    
  // }
  return request({
    url: '/oauth/token',
    method: 'post',
    data,
    auth:{
      username:process.env.API_ID,
      password:process.env.API_KEY
    }
  })
}

export function logout(token) {
  return request({
    url: '/admin/user/logout',
    method: 'post'
  })
  // const data = {
  //   access_token:token
  // }
  // // let headers = {
  // //   "Authorization": "Basic " + btoa(process.env.API_ID+":"+process.env.API_KEY)    
  // // }
  // return request({
  //   url: '/oauth/token',
  //   method: 'delete',
  //   data,
  //   auth:{
  //     username:process.env.API_ID,
  //     password:process.env.API_KEY
  //   }
  // })
}

export function getUserInfo(token) {
  return request({
    url: '/admin/user/info',
    method: 'get',
    params: {  }
  })
}
