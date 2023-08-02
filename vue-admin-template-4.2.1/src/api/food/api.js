import request from '@/utils/request'

export function page(query) {
  return request({
    url: '/user/food/page',
    method: 'get',
    params: query
  })
}

export function getList(query) {
  return request({
    url: '/user/food/getList',
    method: 'get',
    params: query
  })
}

export function addObj(data) {
  return request({
    url: '/user/food/add',
    method: 'post',
    data: data
  })
}

export function editObj(obj) {
  return request({
    url: '/user/food/edit',
    method: 'post',
    data: obj
  })
}

// 删除
export function deletes(ids) {
  return request({
    url: '/user/food/delete/' + ids,
    method: 'get'
  })
}

