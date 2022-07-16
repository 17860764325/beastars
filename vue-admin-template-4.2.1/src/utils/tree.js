export function getAllChildrenIds(ids, data) {
  if (data.children !== undefined) {
    for (let i = 0; i < data.children.length; i++) {
      ids.push(data.children[i].id)
      const child = data.children[i]
      getAllChildrenIds(ids, child)
    }
  } else {
    return
  }
}

// 方法1: 删除数组为空的节点
export function removeEmptyChild(data) {
  if (Array.isArray(data)) {
    data.map(item => {
      if (Array.isArray(item.children) && item.children.length) {
        removeEmptyChild(item.children)
      } else {
        delete item.children
      }
      return item
    })
  }
}

// 方法2: 删除数组为空的节点
export default function deleteNullArray(obj) {
  if (Array.isArray(obj)) {
    for (let i = 0; i < obj.length; i++) {
      if (Array.isArray(obj[i])) {
        if (obj[i].length === 0) {
          delete obj[i]
        }
      } else {
        deleteNullArray(obj[i])
      }
    }
  } else {
    let attr = {}
    for (attr in obj) {
      if (Array.isArray(obj[attr])) {
        if (obj[attr].length === 0) {
          delete obj[attr]
        } else {
          deleteNullArray(obj[attr])
        }
      }
    }
  }
}
