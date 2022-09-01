import request from '@/utils/request'

// 获取所有字典（hr-table字典值使用）
export function getAllUsers() {
  return request({
    url: '/system/user/sth/list',
    method: 'get'
  })
}

// 获取当前登陆用户的信息
export function getUserInfo() {
  return request({
    url: '/system/user/sth/getUserInfoByToken',
    method: 'get'
  })
}

// 获取当前登陆用户的id
export function setUserHeadImg(userHeadImg) {
  return request({
    url: '/system/user/sth/setUserHeadImg/' + userHeadImg,
    method: 'get'
  })
}

// 获取当前登陆用户的id
export function setUserBackgroundImg(userBackgroundImg) {
  return request({
    url: '/system/user/sth/setUserBackgroundImg/' + userBackgroundImg,
    method: 'get'
  })
}

