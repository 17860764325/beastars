<template>
  <div class="app-container">
    <!--      // 修改头像区域-->
    <div v-if="headImg">
      <h2>所有头像：</h2>
      <div class="pageHead">
        <div style="width:100%;display: flex">
          <el-card
            v-for="(item, index) in imgHeadList"
            v-bind:key="index"
            :class="selectedIndex === index?'cardHeadSelected':'cardHeadNoSelected'"
          >
            <img
              style="width: 80px; height: 80px; border: 1px solid;"
              :src="require('../../../../assets/'+ item)"
              @click="chooseImg(item,index)"
            />
            <!--      加上一个选中时候显示的图标-->
          </el-card>
        </div>
      </div>
      <div style="display: flex; justify-content: space-evenly;">
        <!--      用户原本的图标-->
        <div>
          <h2>原头像：</h2>
          <img
            style="width: 130px; height: 100px; border: 1px solid;  border-radius: 5px;"
            :src="require('../../../../assets/'+ userLocalImg)"
          />
        </div>

        <!--      选中的图标-->
        <div>
          <h2>更改后的头像：</h2>
          <img
            style="width: 130px; height: 100px; border: 1px solid;   border-radius: 5px;"
            :src="require('../../../../assets/'+ headImgSelected)"
          />
        </div>
      </div>

    </div>
    <!--修改背景图片-->
    <div v-if="backGroundImg" style="display: flex">
      <div>
        <h2>所有背景图：</h2>
        <div class="pageBackground">
          <div
            style="width:100%;height: 250px;display: flex;flex-wrap: wrap;justify-content: center;  overflow-y: auto;"
          >
            <el-card
              v-for="(item, index) in imgBackgroundList"
              v-bind:key="index"
              :class="selectedIndex === index?'cardBackgroundSelected':'cardBackgroundNoSelected'"
            >
              <img
                :class="selectedIndex === index?'imgBackgroundSelected':'imgBackgroundNoSelected'"
                :src="require('../../../../assets/'+ item)"
                @click="chooseImg(item,index)"
              />
              <!--      加上一个选中时候显示的图标-->
            </el-card>
          </div>
        </div>
      </div>
      <div>
        <h2>预览：</h2>
        <el-card class="card">
          <el-row>
            <!--  头像-->
            <div class="headImgDiv">
              <img
                class="headImg"
                :src="require('../../../../assets/'+userLocalImg)"
              />
            </div>
            <!--  背景图-->
            <img class="img" :src="require('../../../../assets/'+ backgroundImgSelected)"/>
          </el-row>
          <h2>主页预览效果～</h2>
        </el-card>
      </div>
    </div>
<!--    提交 操作-->
    <el-button @click="closeDilog()">取消...</el-button>
    <el-button @click="changeImg()" type="primary">确认修改！</el-button>

  </div>
</template>

<script>
import { setUserBackgroundImg, setUserHeadImg } from '@/api/user/api.js'

export default {
  name: 'SelectImage',
  props: {
    type: {
      type: String
    },
    // 头像
    userLocalImg: {
      type: String
    },
    // 背景图
    userLocalBackGroundImg: {
      type: String
    }
  },
  data() {
    return {
      headImg: false,
      backGroundImg: false,
      list: [],
      selectedIndex: undefined,
      imgHeadList: ['navbar_image/1.png', 'navbar_image/2.png', 'navbar_image/3.png', 'navbar_image/4.png', 'navbar_image/5.png', 'navbar_image/6.png', 'navbar_image/7.png', 'navbar_image/8.png', 'navbar_image/9.png', 'navbar_image/10.png', 'navbar_image/11.png'],
      imgBackgroundList: ['weekWallPaper/1.jpg', 'weekWallPaper/2.jpg', 'weekWallPaper/3.png', 'weekWallPaper/4.png', 'weekWallPaper/5.jpg', 'weekWallPaper/6.jpg', 'weekWallPaper/7.jpg', 'weekWallPaper/8.jpg', 'weekWallPaper/9.jpg', 'weekWallPaper/10.jpg', 'weekWallPaper/11.jpg', 'weekWallPaper/12.jpg', 'weekWallPaper/13.png', 'weekWallPaper/14.jpg'],
      selected: false,
      headImgSelected: undefined,
      backgroundImgSelected: undefined
    }
  },
  created() {
    console.log(this.type)
    if (this.type === 'b') {
      this.backgroundImgSelected = this.userLocalBackGroundImg
      this.headImg = false
      this.backGroundImg = true
    } else if (this.type === 'h') {
      this.headImgSelected = this.userLocalImg
      this.headImg = true
      this.backGroundImg = false
    }
    console.log(this.list)
  },
  methods: {
    chooseImg(data, index) {
      // 选中状态
      this.selected = true
      this.selectedIndex = index
      console.log(data)
      console.log(this.type === 'b')
      if (this.type === 'b') {
        this.backgroundImgSelected = data
      } else if (this.type === 'h') {
        this.headImgSelected = data
      }
    },
    closeDilog() {
      this.$emit('cancel')
    },
    changeImg() {
      // 修改图片
      // 根据选择背景还是选择头像，然后判断端选择的是否为空
      if (this.type === 'b') {
        // 背景图片
        if (this.backgroundImgSelected === undefined) {
          this.$notify({
            title: '失败',
            message: '请选择背景图片！',
            type: 'success'
          })
        } else if (this.backgroundImgSelected === this.userLocalBackGroundImg) {
          // 如果选择的图片和原来的图片相同
          this.$notify({
            title: '成功',
            message: '你并没有改动你的背景照片🫤',
            type: 'success'
          })
          this.closeDilog()
        } else {
          // 保存更改值
          setUserBackgroundImg(this.backgroundImgSelected).then(res => {
            if (res.status === 200) {
              this.$notify({
                title: '成功',
                message: '修改成功',
                type: 'success'
              })
              this.closeDilog()
            } else {
              this.$notify({
                title: '失败',
                message: '修改失败',
                type: 'error'
              })
              this.closeDilog()
            }
          })
        }
      } else if (this.type === 'h') {
        // 头像
        if (this.headImgSelected === undefined) {
          this.$notify({
            title: '失败',
            message: '请选择头像图片！',
            type: 'success'
          })
        } else if (this.headImgSelected === this.userLocalImg) {
          // 如果选择的图片和原来的图片相同
          this.$notify({
            title: '成功',
            message: '你并没有改动你的头像🫤',
            type: 'success'
          })
          this.closeDilog()
        } else {
          setUserHeadImg(this.headImgSelected).then(res => {
            if (res.status === 200) {
              this.$notify({
                title: '成功',
                message: '修改成功',
                type: 'success'
              })
              this.closeDilog()
            } else {
              this.$notify({
                title: '失败',
                message: '修改失败',
                type: 'error'
              })
              this.closeDilog()
            }
          })
        }
      }
    }
  }
}
</script>

<style scoped>
.pageHead {
  display: flex;
  border: 1px solid;
  border-radius: 5px;
  width: 100%;
  flex-direction: column-reverse;
}

.pageBackground {
  display: flex;
  border: 1px solid;
  border-radius: 5px;
  width: 200px;
  flex-direction: column-reverse;
}

/deep/ .el-card__body {
  padding: 8px;
}

.cardHeadNoSelected {
  width: 110px;
  height: 110px;
  text-align: center;
}

.cardHeadSelected {
  width: 200px;
  height: 110px;
  background-color: #fff2cc;
  border: 2px solid #f6b26b;
  border-radius: 5px;
  text-align: center;
}

.cardBackgroundNoSelected {
  width: 120px;
  height: 110px;
  text-align: center;
}

.cardBackgroundSelected {
  width: 200px;
  height: 110px;
  background-color: #fff2cc;
  border: 2px solid #f6b26b;
  border-radius: 5px;
  text-align: center;

}

.imgBackgroundNoSelected {
  width: 100px;
  height: 80px;
  border: 1px solid;
}

.imgBackgroundSelected {
  width: 120px;
  height: 80px;
  border: 1px solid;
}

.card {
  width: 350px;
  height: 250px;
}

.img {
  width: 100%;
  height: 190px;
}

.headImg {
  width: 100px;
  height: 80px;
  border-radius: 10px;
}

.headImgDiv {
  z-index: 1;
  position: absolute;
  top: 125px;
  left: 10px;
}

</style>
