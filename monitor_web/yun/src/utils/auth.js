import Cookies from 'js-cookie'
import { autoLogin } from '@/api/login'
const TokenKey = 'Admin-Token'
const ReToken = 'Re-Token'
const ExpireTime = 'Expire_Token'
const AUTOLOGIN = 'AUTO_Token'

export function getToken() {
  let expirein = localStorage.getItem(ExpireTime);
  if (parseInt(expirein) - Date.now() > 0)
    return localStorage.getItem(TokenKey)
  else {
    if (localStorage.getItem(AUTOLOGIN)) {
      if (refreshToken(localStorage.getItem(ReToken)))
        return localStorage.getItem(TokenKey)
    }
  }

  localStorage.removeItem(TokenKey);
  localStorage.removeItem(ReToken);
  localStorage.removeItem(ExpireTime);
  localStorage.removeItem(AUTOLOGIN);
  return false;
}

export function checkTokenExpiresin() {
  let expirein = localStorage.getItem(ExpireTime);
  if (parseInt(expirein) - Date.now() > 0)
    return true;

  return false;
}

export function refreshToken(rtoken) {
  return autoLogin(rtoken).then(response => {
    const data = response
    setToken(data.access_token, data.refresh_token, Date.now() + data.expires_in * 1000, true)
    return true;
  }).catch(() => {
    return false;
  });

  return false;
}


export function setToken(token, rtoken, expirein, autologin) {
  localStorage.setItem(TokenKey, token);
  localStorage.setItem(ReToken, rtoken);
  localStorage.setItem(ExpireTime, expirein);
  localStorage.setItem(AUTOLOGIN, autologin);
}

export function removeToken() {
  localStorage.removeItem(TokenKey);
  localStorage.removeItem(ReToken);
  localStorage.removeItem(ExpireTime);
  localStorage.removeItem(AUTOLOGIN);
}

export function getAutoLogin() {
  return localStorage.getItem(AUTOLOGIN);
}
