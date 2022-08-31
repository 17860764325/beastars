import request from '@/utils/request'

export function page(query) {
  return request({
    url: '/loginIpLog/page',
    method: 'get',
    params: query
  })
}
