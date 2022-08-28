import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'
const RouterLists = 'RouterLists'


export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function setDicts(data) {
  return Cookies.set(RouterLists, data)
}

export function getDicts() {
  return Cookies.get(RouterLists)
}
