import request from '@/utils/request'

export function getInfo(id) {
  return request({
    url: '/scheduleItem/getInfo/' + id,
    method: 'get'
  })
}

export function saveInfo(data) {
  return request({
    url: '/scheduleItem/saveInfo',
    method: 'post',
    data: data
  })
}
