import axios from "axios";
import { Message, MessageBox, Notification } from "element-ui";
import store from "@/store";
import { objPropTrim } from "@/utils/index";
import { getToken } from "@/utils/auth";
import i18n from "../lang";
import app from "../main";

// 禁用IE下get请求缓存
axios.defaults.headers.get["Cache-Control"] = "no-cache";
axios.defaults.headers.get.Pragma = "no-cache";

// create an axios instance
const service = axios.create({
  // baseURL: process.env.BASE_API, // api的base_url
  timeout: 5000 // request timeout
});

// request拦截器
service.interceptors.request.use(
  config => {
    // Do something before request is sent
    // 自动去除参数中字符串前后的空格
    if (config.data) {
      objPropTrim(config.data);
    }
    if (config.params) {
      objPropTrim(config.params);
    }
    if (store.getters.token) {
      config.headers.Authorization = getToken(); // 让每个请求携带token--['X-Token']为自定义key 请根据实际情况自行修改
    }
    config.headers["Accept-Language"] = store.getters.language;
    config.headers.eiversion = process.env.EIVERSION;
    store.dispatch("app/loadingStart");
    app.$Progress.start();
    return config;
  },
  error => {
    // Do something with request error
    //  console.log(error) // for debug
    Promise.reject(error);
    app.$Progress.fail();
  }
);

// respone拦截器
service.interceptors.response.use(
  response => {
    /**
     * 下面的注释为通过response自定义code来标示请求状态，当code返回如下情况为权限有问题，登出并返回到登录页
     * 如通过xmlhttprequest 状态码标识 逻辑可写在下面error中
     */
    const res = response.data;
    app.$Progress.finish();
    store.dispatch("app/loadingEnd");
    // 后台抛出异常
    if (res.status && res.status === 30101) {
      Notification.warning({
        title: i18n.t("request.indicate"),
        message: res.message,
        duration: 2 * 1000
      });
      // 服务器异常
    } else if (res.status && res.status !== 200) {
      Message({
        message: res.message,
        type: "error",
        duration: 5 * 1000
      });
    } else {
      return response.data;
    }
  },
  error => {
    //  console.log('err' + error) // for debug
    store.dispatch("app/loadingEnd");
    app.$Progress.fail();
    const response = error.response;
    // 服务超时
    if (response === undefined) {
      Message({
        message: i18n.t("request.timeout"),
        type: "error",
        duration: 5 * 1000
      });
      return Promise.reject(error);
    }
    const info = response.data;
    // 已经被登场，是否重复登录
    if (response.status === 401 || info.status === 40101) {
      MessageBox.confirm(
        i18n.t("request.logOut"),
        i18n.t("request.confirmLogOut"),
        {
          confirmButtonText: i18n.t("request.loginAgain"),
          cancelButtonText: i18n.t("request.cancel"),
          type: "warning"
        }
      ).then(() => {
        store.dispatch("user/LogOut").then(() => {
          location.reload(); // 为了重新实例化vue-router对象 避免bug
        });
      });
    }
    // 禁止
    if (response.status === 403) {
      Notification.warning({
        title: i18n.t("request.disable"),
        message: info.message,
        type: "error",
        duration: 2 * 1000
      });
      return Promise.reject("error");
    }
    // 后端服务异常
    if (response.status === 504) {
      Message({
        message: i18n.t("request.exception"),
        type: "error",
        duration: 5 * 1000
      });
      return Promise.reject(error);
    }
    Message({
      message: info.message,
      type: "error",
      duration: 5 * 1000
    });
    return Promise.reject(error);
  }
);

export default service;
