import Vue from 'vue'
import VueProgressBar from 'vue-progressbar'

const options = {
  color: 'rgb(93,132,241)',
  failedColor: '#C1EE09',
  thickness: '3px',
  transition: {
    speed: '0.2s',
    opacity: '0.6s',
    termination: 500
  },
  autoRevert: true,
  location: 'top',
  inverse: false
}

Vue.use(VueProgressBar, options)
