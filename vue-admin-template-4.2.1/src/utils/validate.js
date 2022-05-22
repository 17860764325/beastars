import i18n from '../lang'
/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername(str) {
  const valid_map = ['admin', 'editor']
  return valid_map.indexOf(str.trim()) >= 0
}

const validateLength = (rule, value, callback) => {
  if (value) {
    if (rule.min && rule.max && (rule.max < value.length || value.length < rule.min)) {
      callback(
        new Error(
          `${i18n.t('validate.length') + rule.min + i18n.t('validate.reach') + rule.max + i18n.t('validate.char')}`
        )
      )
    }
    if (rule.min && !rule.max && value.length < rule.min) {
      callback(new Error(`${i18n.t('validate.lengthMaxThan') + rule.min + i18n.t('validate.char')}`))
    }
    if (!rule.min && rule.max && rule.max < value.length) {
      callback(new Error(`${i18n.t('validate.lengthMinThan') + rule.max + i18n.t('validate.char')}`))
    }
    if (rule.equal && value.length !== rule.equal) {
      callback(new Error(`${i18n.t('validate.lengthEaquals') + rule.equal + i18n.t('validate.char')}`))
    }
    callback()
  } else {
    callback()
  }
}
/* 半角字符检查*/
const isValidateHalfChar = (rule, value, callback) => {
  if (value) {
    // const re = /[-~｡-ﾟ]/
    // const re = /^[a-zA-Z0-9\\u4e00-\\u9fa5]$/
    const re = /^[.\\x00-\xff]+$/
    // const re = /[\\x00-\xff]/g
    if (!re.test(value)) {
      callback(new Error(i18n.t('validate.halfChar')))
    } else {
      callback()
    }
  } else {
    callback()
  }
}
/* 半角英文检查*/
const isValidateHalfEnglish = (rule, value, callback) => {
  if (value) {
    const re = /^[a-zA-Z]+$/
    if (!re.test(value)) {
      callback(new Error(i18n.t('validate.halfEnglish')))
    } else {
      callback()
    }
  } else {
    callback()
  }
}
/* 半角数字检查*/
const isValidateHalfNum = (rule, value, callback) => {
  if (value) {
    const re = /^[0-9]*$/
    if (!re.test(value)) {
      callback(new Error(i18n.t('validate.halfNum')))
    } else {
      callback()
    }
  } else {
    callback()
  }
}
/* 半角英数字检查*/
const isValidateHalfENUM = (rule, value, callback) => {
  if (value) {
    const re = /^[a-zA-Z0-9]+$/
    if (!re.test(value)) {
      callback(new Error(i18n.t('validate.halfENUM')))
    } else {
      callback()
    }
  } else {
    callback()
  }
}
/* 半角大写英数字检查*/
const isValidateHalfUpperENUM = (rule, value, callback) => {
  if (value) {
    const re = /^[A-Z0-9]+$/
    if (!re.test(value)) {
      callback(new Error(i18n.t('validate.halfUpperENUM')))
    } else {
      callback()
    }
  } else {
    callback()
  }
}
/* 中文检查*/
const isValidateCh = (rule, value, callback) => {
  if (value) {
    const re = /[\u4e00-\u9fa5]/
    if (!re.test(value)) {
      callback(new Error(i18n.t('validate.ch')))
    } else {
      callback()
    }
  } else {
    callback()
  }
}
/* 非中文检查*/
const isValidateNoCh = (rule, value, callback) => {
  if (value) {
    const re = /[\u4e00-\u9fa5]/
    if (re.test(value)) {
      callback(new Error(i18n.t('validate.noCh')))
    } else {
      callback()
    }
  } else {
    callback()
  }
}
/* 合法uri*/
const isValidateURL = (rule, value, callback) => {
  if (value) {
    const re =
      /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/
    if (!re.test(value)) {
      callback(new Error(i18n.t('validate.url')))
    } else {
      callback()
    }
  } else {
    callback()
  }
}
/* 小写字母*/
const isValidateLowerCase = (rule, value, callback) => {
  if (value) {
    const re = /^[a-z]+$/
    if (!re.test(value)) {
      callback(new Error(i18n.t('validate.lowerCase')))
    } else {
      callback()
    }
  } else {
    callback()
  }
}
/* 大写字母*/
const isValidateUpperCase = (rule, value, callback) => {
  if (value) {
    const re = /^[A-Z]+$/
    if (!re.test(value)) {
      callback(new Error(i18n.t('validate.upperCase')))
    } else {
      callback()
    }
  } else {
    callback()
  }
}
/* 密码强度校验*/
const validatePasswordStreflgth = (rule, value, callback) => {
  if (value) {
    let flag = 0
    if (value.match(/([a-z])+/)) {
      flag++
    }
    if (value.match(/([0-9])+/)) {
      flag++
    }
    if (value.match(/([A-Z])+/)) {
      flag++
    }
    if (value.match(/([\W])+/) && !value.match(/(![\u4E00-\u9FA5])+/) && value.indexOf(' ') === -1) {
      flag++
    }
    if (value.match(/([\u4E00-\u9FA5])+/)) {
      callback(new Error(i18n.t('validate.passwordNoCn')))
    }
    if (value.indexOf(' ') !== -1) {
      callback(new Error(i18n.t('validate.notBlankSpace')))
    }
    if (value.length < 6 || value.length > 20) {
      callback(new Error(i18n.t('validate.length6Tolength20')))
    }
    if (!rule.strength || rule.strength === 'strong') {
      switch (flag) {
        case 0:
        case 1:
        case 2:
          callback(new Error(i18n.t('validate.passwordStrong')))
          break
        default:
          callback()
          break
      }
    } else if (rule.strength === 'medium') {
      switch (flag) {
        case 0:
        case 1:
          callback(new Error(i18n.t('validate.passwordMedium')))
          break
        default:
          callback()
          break
      }
    } else if (rule.strength === 'weak') {
      callback()
    }
  } else {
    callback()
  }
}
/* Email*/
const isValidateEmail = (rule, value, callback) => {
  if (value) {
    const re =
      /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    if (!re.test(value)) {
      callback(new Error(i18n.t('validate.email')))
    } else {
      callback()
    }
  } else {
    callback()
  }
}
/* 邮编*/
const isValidateZipCode = (rule, value, callback) => {
  if (value) {
    const re = /^[0-9]{6}$/
    if (!re.test(value)) {
      callback(new Error(i18n.t('validate.zipCode')))
    } else {
      callback()
    }
  } else {
    callback()
  }
}
/*  电话校验（010-12345678 | 0532-12345678 | 0532-1234567 | 1（345789）012345678）*/
const isValidatePhoneNumber = (rule, value, callback) => {
  if (value) {
    const re = /^\d{3}-\d{8}|\d{4}-\d{7,8}|1[345789]\d{9}$/
    if (!re.test(value)) {
      callback(new Error(i18n.t('validate.phoneNumber')))
    } else {
      callback()
    }
  } else {
    callback()
  }
}

/* 字符串不能有空格*/
const isValidateSpace = (rule, value, callback) => {
  if (value) {
    const re = /^[^ ]+$/
    if (!re.test(value)) {
      callback(new Error(i18n.t('validate.space')))
    } else {
      callback()
    }
  } else {
    callback()
  }
}

/* 校验数字并且应该大于0 */
const isValidatePositive = (rule, value, callback) => {
  if (value <= 0 || isNaN(value)) {
    callback(new Error(i18n.t('validate.positive')))
  } else {
    callback()
  }
}

/*  手机号码校验 2021-03*/
const isValidateMobilePhoneNumber = (rule, value, callback) => {
  if (value) {
    const re =
      /^((13[0-9])|(14[5-9])|(15([0-3]|[5-9]))|(16[6-7])|(17[1-8])|(18[0-9])|(19[1|3])|(19[5|6])|(19[8|9]))\d{8}$/
    if (!re.test(value)) {
      callback(new Error(i18n.t('validate.phoneNumber')))
    } else {
      callback()
    }
  } else {
    callback()
  }
}

export const isColumnKey = (rule, value, callback) => {
  const reg2 = /(^_([a-zA-Z0-9]_?)*$)|(^[a-zA-Z](_?[a-zA-Z0-9])*_?$)/
  if (!reg2.test(value)) {
    return callback(
      new Error(
        '您输入的数据库字段名称：“' +
          value +
          '”格式不正确；提示：【首位可以是字母以及下划线。首位之后可以是字母，数字以及下划线。下划线后不能接下划线】'
      )
    )
  } else {
    return callback()
  }
}

export const attrLengthValidator = (rule, value, callback) => {
  if (value) {
    const intValue = parseInt(value)
    if (intValue > 0) {
      callback()
    } else {
      callback(new Error('值只能是正整数'))
    }
  } else {
    callback()
  }
}

export default {
  validateLength,
  isValidateHalfEnglish,
  isValidateHalfChar,
  isValidateHalfNum,
  isValidateHalfENUM,
  isValidateHalfUpperENUM,
  isValidateCh,
  isValidateNoCh,
  isValidateURL,
  isValidateLowerCase,
  isValidateUpperCase,
  validatePasswordStreflgth,
  isValidateEmail,
  isValidateZipCode,
  isValidatePhoneNumber,
  isValidateSpace,
  isValidatePositive,
  isValidateMobilePhoneNumber,
  isColumnKey,
  attrLengthValidator
}

