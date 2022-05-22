import request from '@/utils/request'
export function saveColumnSetting(data) {
  return request({
    url: '/api/admin/columnsShow/saveColumnSetting',
    method: 'post',
    data
  })
}

export function getColumnSetting(model) {
  return request({
    url: '/api/admin/columnsShow/getColumnSetting/' + model,
    method: 'get'
  })
}
