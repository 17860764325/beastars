import request from '@/utils/request'
// 查询所有角色
export function getAllCharacter(dictType) {
  return request({
    url: '/role/getAll',
    method: 'get'
  })
}

// 根据用户id查询用户的角色
export function getCharacterByUserId(userid) {
  return request({
    url: '/role/getCharacterByUserId/' + userid,
    method: 'get'
  })
}

// 根据用户id查询用户的角色
export function saveCharacter(data) {
  return request({
    url: '/role/saveCharacter',
    method: 'post',
    data: data
  })
}