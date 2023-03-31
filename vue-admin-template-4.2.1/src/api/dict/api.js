import request from '@/utils/request'

// 根据字典类型查询字典数据信息
export function getDicts(dictType) {
  return request({
    url: '/dict/type/' + dictType,
    method: 'get'
  })
}

// 获取所有字典（hr-table字典值使用）
export function getAllDicts() {
  return request({
    url: '/dict/dictsMap',
    method: 'get'
  })
}

// 字典值分页查询
export function page(query) {
  return request({
    url: '/dict/page',
    method: 'get',
    params: query
  })
}

// 字典值新增
export function addObj(obj) {
  return request({
    url: '/dict/add',
    method: 'post',
    data: obj
  })
}

// 根据字典值id查询字典值信息
export function getDictInfoById(id) {
  return request({
    url: '/dict/edit/' + id,
    method: 'get'
  })
}

// 字典值修改
export function editObj(obj) {
  return request({
    url: '/dict/edit',
    method: 'post',
    data: obj
  })
}

// 删除字典
export function deletes(ids) {
  return request({
    url: '/dict/delete/' + ids,
    method: 'get'
  })
}
