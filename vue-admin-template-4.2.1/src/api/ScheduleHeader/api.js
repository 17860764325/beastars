import request from '@/utils/request'

export function page(query) {
  return request({
    url: '/schedule/page',
    method: 'get',
    params: query
  })
}

export function addObj(data) {
  return request({
    url: '/schedule/add',
    method: 'post',
    data: data
  })
}

export function getDictInfoById(id) {
  return request({
    url: '/schedule/edit/' + id,
    method: 'get'
  })
}

export function editObj(obj) {
  return request({
    url: '/schedule/edit',
    method: 'post',
    data: obj
  })
}

// 删除
export function deletes(ids) {
  return request({
    url: '/schedule/delete/' + ids,
    method: 'get'
  })
}

// 删除
export function statusOk(ids) {
  return request({
    url: '/schedule/statusOk/' + ids,
    method: 'get'
  })
}

// 删除
export function statusNo(ids) {
  return request({
    url: '/schedule/statusNo/' + ids,
    method: 'get'
  })
}
