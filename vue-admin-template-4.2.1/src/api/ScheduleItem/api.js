import request from '@/utils/request'

export function getInfo(id) {
  return request({
    url: '/scheduleItem/getInfo/' + id,
    method: 'get'
  })
}

// 保存信息
export function saveInfo(data) {
  return request({
    url: '/scheduleItem/saveInfo',
    method: 'post',
    data: data
  })
}

// 保存图片
export function uploadImage(data) {
  return request({
    url: '/scheduleItem/uploadImage',
    method: 'post',
    data: data
  })
}
