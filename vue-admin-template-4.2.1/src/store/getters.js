const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  permission_routes: state => state.permission.routes,
  cookie_routes: state => state.permission.cookieRoutes,
  dicts: state => state.user.dicts
}

export default getters
