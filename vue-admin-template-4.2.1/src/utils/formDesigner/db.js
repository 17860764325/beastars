const FORM_DATA = 'formData'
const FORM_CONF = 'formConf'
const TREE_NODE_ID = 'treeNodeId'

export function getFormConfData(entityCode) {
  const str = localStorage.getItem(`${entityCode}_${FORM_DATA}`)
  if (str) return JSON.parse(str)
  return null
}

export function saveFormConfData(entityCode, obj) {
  localStorage.setItem(`${entityCode}_${FORM_DATA}`, JSON.stringify(obj))
}

export function getFormConf(entityCode) {
  const str = localStorage.getItem(`${entityCode}_${FORM_CONF}`)
  if (str) return JSON.parse(str)
  return null
}

export function getTreeNodeId() {
  const str = localStorage.getItem(TREE_NODE_ID)
  if (str) return parseInt(str, 10)
  return 100
}

export function saveTreeNodeId(id) {
  localStorage.setItem(TREE_NODE_ID, `${id}`)
}
