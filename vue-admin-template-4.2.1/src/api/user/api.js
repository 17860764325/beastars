import request from '@/utils/request'

// 获取所有字典（hr-table字典值使用）
export function getAllUsers() {
  return request({
    url: '/user/sth/list',
    method: 'get'
  })
}

