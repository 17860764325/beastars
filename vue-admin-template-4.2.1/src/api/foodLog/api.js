import request from '@/utils/request'

export function page(query) {
  return request({
    url: '/user/log/page',
    method: 'get',
    params: query
  })
}

export function getList(query) {
  return request({
    url: '/user/log/getList',
    method: 'get',
    params: query
  })
}

export function addObj(data) {
  return request({
    url: '/user/log/add',
    method: 'post',
    data: data
  })
}

export function editObj(obj) {
  return request({
    url: '/user/log/edit',
    method: 'post',
    data: obj
  })
}

// 删除
export function deletes(ids) {
  return request({
    url: '/user/log/delete/' + ids,
    method: 'get'
  })
}

