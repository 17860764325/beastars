import request from '@/utils/request'

export function page(query) {
  return request({
    url: '/user/plan/page',
    method: 'get',
    params: query
  })
}

export function getList(query) {
  return request({
    url: '/user/plan/getList',
    method: 'get',
    params: query
  })
}

export function addObj(data) {
  return request({
    url: '/user/plan/add',
    method: 'post',
    data: data
  })
}

export function editObj(obj) {
  return request({
    url: '/user/plan/edit',
    method: 'post',
    data: obj
  })
}

// 删除
export function deletes(ids) {
  return request({
    url: '/user/plan/delete/' + ids,
    method: 'get'
  })
}

