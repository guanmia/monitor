import { param2Obj } from '@/utils'

const userMap = {
  admin: {
    error_code:0,
    data:{
      id:1,
      role: ['admin'],
      token: 'admin',
      introduction: '我是超级管理员',
      email: 'admin@test.com',
      name: 'admin'
    }
  },
  user: {
    error_code:1,
    data:{
      id:2,
      role: ['user'],
      token: 'user',
      introduction: '我是编辑',
      email: 'user@test.com',
      name: 'user'
    }
  }
}

const loginMap = {
  admin: {
    "access_token": "admin",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VyMSIsInNjb3BlIjpbIm9wZW5pZCJdLCJhdGkiOiJmZDFjNmRlZS0xZjU4LTQzYmQtYWRjMy05YzZjNDJlZWI0ZWYiLCJleHAiOjE1MTg2NjI3MjYsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiZDg4MGRkZDgtMzZjZS00NWVjLThhZDktZDE3MzRmMTI1MWJiIiwiY2xpZW50X2lkIjoiYWNtZSJ9.WflJeu6KSHDNow8UO0NgNOBG4LtGdcS1l71bNynh6GOcWzFMEyeeFNGSNEc477orEf_g8sEBgQjTDDD7pRU3AiGnHO8sov9RtXhQNLPv6cRTklBuKW-Pmlsaj_nmphGHdr0HOZybZcsJpxIOG7UrvIBYfP2MKzCs_114lCMS1WE56lvjY4ZjGgfVRtHO922q5wHgFbSkLEL-gFswbQf6B_OEee86kXw1HFXBwvYeV94T5YLfL10BXacWLh_BD5DmEk00qYmsZFdsVNUQSOfh1SIkSkCOrIBHkZa_sMDMSTxmZxK7pWu3dWglJuX0c2P6UNm2fDgqm9PzK7AgpSd0Ug",
    "expires_in": Date.now()+259200000,
    "scope": "openid",
    "jti": "fd1c6dee-1f58-43bd-adc3-9c6c42eeb4ef"
},
  user: {
    "access_token": "user",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VyMSIsInNjb3BlIjpbIm9wZW5pZCJdLCJhdGkiOiJmZDFjNmRlZS0xZjU4LTQzYmQtYWRjMy05YzZjNDJlZWI0ZWYiLCJleHAiOjE1MTg2NjI3MjYsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiZDg4MGRkZDgtMzZjZS00NWVjLThhZDktZDE3MzRmMTI1MWJiIiwiY2xpZW50X2lkIjoiYWNtZSJ9.WflJeu6KSHDNow8UO0NgNOBG4LtGdcS1l71bNynh6GOcWzFMEyeeFNGSNEc477orEf_g8sEBgQjTDDD7pRU3AiGnHO8sov9RtXhQNLPv6cRTklBuKW-Pmlsaj_nmphGHdr0HOZybZcsJpxIOG7UrvIBYfP2MKzCs_114lCMS1WE56lvjY4ZjGgfVRtHO922q5wHgFbSkLEL-gFswbQf6B_OEee86kXw1HFXBwvYeV94T5YLfL10BXacWLh_BD5DmEk00qYmsZFdsVNUQSOfh1SIkSkCOrIBHkZa_sMDMSTxmZxK7pWu3dWglJuX0c2P6UNm2fDgqm9PzK7AgpSd0Ug",
    "expires_in": Date.now()+259200000,
    "scope": "openid",
    "jti": "fd1c6dee-1f58-43bd-adc3-9c6c42eeb4ef"
}
}

export default {
  loginByUsername: config => {
    const { username } = JSON.parse(config.body)
    //console.log(userMap[username].data)
    return loginMap[username]
  },
  getUserInfo: config => {
    const { token } = param2Obj(config.url)
    if (userMap[token]) {
      return userMap[token]
    } else {
      return false
    }
  },
  logout: () => {return {error_code:0,data:{}}}
}
