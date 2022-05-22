// 链式调用加法
Number.prototype.add = function (num) {
  return Number(math.add(math.bignumber(this.valueOf()), math.bignumber(num)))
}
// 链式调用减法
Number.prototype.subtract = function (num) {
  return Number(math.subtract(math.bignumber(this.valueOf()), math.bignumber(num)))
}
// 链式调用乘法
Number.prototype.multiply = function (num) {
  return Number(math.multiply(math.bignumber(this.valueOf()), math.bignumber(num)))
}
// 链式调用除法
Number.prototype.divide = function (num) {
  return Number(math.divide(math.bignumber(this.valueOf()), math.bignumber(num)))
}
// 判断值是否为0
Number.prototype.isZero = function () {
  return this.valueOf() === 0
}
// 判断值是否不为0
Number.prototype.isNotZero = function () {
  return !this.valueOf().isZero()
}
