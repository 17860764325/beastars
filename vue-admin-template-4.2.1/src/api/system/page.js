import request from '@/utils/request'

export function getRouterListFromMysql() {
  return request({
    url: '/user/routerList',
    method: 'get'
  })
}

export function getUserFatherRouterPaths() {
  return request({
    url: '/user/getUserFatherRouterPaths',
    method: 'get'
  })
}

export function getUserSonRouterPaths() {
  return request({
    url: '/user/getUserSonRouterPaths',
    method: 'get'
  })
}

export function getPageList() {
  return request({
    url: '/page/treeList',
    method: 'get'
  })
}

export function getPageInfoByCode(code ) {
  return request({
    url: '/page/getPageInfoByCode/' + code,
    method: 'get'
  })
}

export function getMaxPageCode() {
  return request({
    url: '/page/getMaxPageCode',
    method: 'get'
  })
}
