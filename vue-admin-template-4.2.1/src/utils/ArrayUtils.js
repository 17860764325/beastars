/**
 * 功能：数组处理方法，覆盖，合并，比较后覆盖（比较元素中的某一属性，或元素是否完全相同）
 * 作者：刘玉
 * 日期：2020-05-06
 */
/**
 * array 必传，原来的数组
 * other 必传，被比较的数组
 * status 必传，
 * status === 'cover'是覆盖，返回被比较的数组other；
 * status === 'merge'合并，不进行唯一性比较，直接合并两个数组
 * status === 'uniqMerge'比较后合并，进行唯一性比较
 * identity 非必传，默认为比较数组中元素是否完全相同；
 * identity传值时， 通过比较数组元素中， 该属性是否重复， 如果重复， 取原数组中的元素
 */
export function compareAndMerge(array, other, status, identity) {
  if (status === 'cover') return other
  const mergedArray = [...array, ...other]
  if (status === 'merge') return mergedArray
  if (status === 'uniqMerge') {
    if (!identity) {
      return _.uniqWith(mergedArray, _.isEqual)
    } else {
      return _.uniqBy(mergedArray, item => item[identity])
    }
  }
}
