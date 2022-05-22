import request from '@/utils/request'

export function page(query) {
  return request({
    url: '/schedule/page',
    method: 'get',
    params: query
  })
}
