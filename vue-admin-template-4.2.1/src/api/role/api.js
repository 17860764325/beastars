import request from '@/utils/request'

// 获取所有字典（hr-table字典值使用）
export function page(query) {
  return request({
    url: '/role/page',
    method: 'get',
    params: query
  })
}
// 删除权限
export function deletes(ids) {
  return request({
    url: '/role/delete/' + ids,
    method: 'get'
  })
}
// 新增权限
export function addObj(obj) {
  return request({
    url: '/role/add',
    method: 'post',
    data: obj
  })
}
// 修改权限
export function editObj(obj) {
  return request({
    url: '/role/edit',
    method: 'post',
    data: obj
  })
}

// 根据权限值id查询权限值信息
export function getroleInfoByid(id) {
  return request({
    url: '/role/edit/' + id,
    method: 'get'
  })
}