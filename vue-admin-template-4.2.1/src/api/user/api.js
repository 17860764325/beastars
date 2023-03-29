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

// 获根据ID修改用户的密码
export function changePassWord(data) {
  return request({
    url: '/system/user/sth/changeUserPassword',
    method: 'post',
    data
  })
}

// 用户个人修改自己的信息
export function changeUserInfo(data) {
  return request({
    url: '/system/user/sth/changeUserInfo',
    method: 'post',
    data
  })
}
