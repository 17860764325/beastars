<template>
  <div class="navbar">
    <img class="backageImage" v-lazy="require('../../assets/navbar_image/12.png')">
    <img class="backageImage" v-lazy="require('../../assets/navbar_image/2.png')" @click="imageClick('1')">
    <img class="backageImage" v-lazy="require('../../assets/navbar_image/3.png')" @click="imageClick('1')">
    <img class="backageImage" v-lazy="require('../../assets/navbar_image/1.png')" @click="imageClick('1')">
    <img class="backageImage" v-lazy="require('../../assets/navbar_image/4.png')" @click="imageClick('1')">
    <img class="backageImage" v-lazy="require('../../assets/navbar_image/5.png')" @click="imageClick('1')">
    <img class="backageImage" v-lazy="require('../../assets/navbar_image/6.png')" @click="imageClick('1')">
    <img class="backageImage" v-lazy="require('../../assets/navbar_image/7.png')" @click="imageClick('1')">
    <img class="backageImage" v-lazy="require('../../assets/navbar_image/8.png')" @click="imageClick('1')">
    <img class="backageImage" v-lazy="require('../../assets/navbar_image/9.png')" @click="imageClick('1')">
    <img class="backageImage" v-lazy="require('../../assets/navbar_image/10.png')" @click="imageClick('1')">
    <img class="backageImage" v-lazy="require('../../assets/navbar_image/11.png')" @click="imageClick('1')">
    <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar"/>

    <breadcrumb class="breadcrumb-container"/>

    <div class="right-menu">
      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <img  v-lazy="require('../../assets/'+ (avatar? avatar:'1.png'))" class="user-avatar">
          <i class="el-icon-caret-bottom"/>
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <router-link to="/">
            <el-dropdown-item>
              Home
            </el-dropdown-item>
          </router-link>
          <!-- <a target="_blank" href="https://panjiachen.github.io/vue-element-admin-site/#/">
            <el-dropdown-item>Docs</el-dropdown-item>
          </a> -->
          <el-dropdown-item divided>
            <span style="display:block;" @click="logout">Log Out</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import { getUserInfo } from '@/api/user/api.js'

export default {
  components: {
    Breadcrumb,
    Hamburger
  },
  computed: {
    ...mapGetters([
      'sidebar'
    ])
  },
  data() {
    return {
      avatar: undefined
    }
  },
  async created() {
    const res = await getUserInfo()
    console.log(res)
    this.avatar = res.data.userHeadImg
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      await this.$store.dispatch('user/resetToken')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    },
    imageClick(item) {
      console.log(item)
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, .08);

  .backageImage {

    border-radius: 20%;
    border: black solid 1px;
    height: 100%;
  }

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 60px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
