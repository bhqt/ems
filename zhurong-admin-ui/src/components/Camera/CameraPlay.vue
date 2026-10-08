<template>
  <div class="camera-window">
    <div class="camera-title">{{ video.cameraName }}</div>
    <div ref="videoBox" class="video-box" :id="code"></div>
  </div>
</template>
<script>
import EZUIKit from "ezuikit-js";
import { getUrlBySerialNumber } from "@/api/system/cameraConfig";
export default {
  // 单个视频播放组件
  name: "cameraPlay",
  props: ["code"],
  data() {
    return {
      player: null,
      ro: null,
      inited: false,
      fallbackTimer: null,
      video: {
        cameraName: "",
      },
    };
  },
  methods: {
    getMonitors() {
      getUrlBySerialNumber(this.code)
        .then((response) => {
          this.video = response.data || {};
        })
        .then(() => {
          this.initVideo();
        })
        .catch((err) => {
          console.error("[CameraPlay] getUrlBySerialNumber 失败", this.code, err);
        });
    },
    initVideo() {
      if (this.inited) return;

      // 1) 鉴权参数校验：没 token / 没 url 直接放弃，避免给萤石云发 "sessionID=" 这种无效请求
      if (!this.video.cameraToken || !this.video.url) {
        console.warn(
          "[CameraPlay] 缺少 accessToken 或 url，跳过初始化",
          this.code,
          { token: this.video.cameraToken, url: this.video.url }
        );
        return;
      }

      const box = this.$refs.videoBox;
      if (!box) {
        console.warn("[CameraPlay] videoBox 未挂载", this.code);
        return;
      }

      // 2) 等容器真实有尺寸再实例化（grid/flex 布局下 mounted 阶段宽高常为 0）
      const start = () => {
        if (this.inited) return;
        const w = box.offsetWidth || 480;
        const h = box.offsetHeight || 270;
        try {
          this.player = new EZUIKit.EZUIKitPlayer({
            id: this.code, // 视频容器ID
            accessToken: this.video.cameraToken,
            url: this.video.url,
            // simple - 极简版; pcLive-pc直播；pcRec-pc回放；	mobileLive-移动端直播；mobileRec-移动端回放;security - 安防版;voice-语音版;
            template: "e92ab948240a43579973104642a265b4", //模板
            //plugin: ['talk'], // 加载插件，talk-对讲
            audio: 0,
            width: w,
            height: h,
          });
          this.inited = true;
        } catch (e) {
          console.error("[CameraPlay] EZUIKitPlayer 实例化失败", e);
        }
      };

      if (box.offsetWidth === 0 || box.offsetHeight === 0) {
        // 监听尺寸变化，拿到第一帧非零尺寸再初始化
        this.ro = new ResizeObserver(() => {
          if (box.offsetWidth > 0 && box.offsetHeight > 0) {
            start();
            if (this.ro) {
              this.ro.disconnect();
              this.ro = null;
            }
          }
        });
        this.ro.observe(box);
        // 兜底：500ms 后强制一次
        this.fallbackTimer = setTimeout(() => {
          if (!this.inited) start();
        }, 500);
      } else {
        start();
      }
    },
  },
  mounted() {
    // 父组件是 v-if，DOM 真正挂完再请求
    this.$nextTick(() => this.getMonitors());
  },
  beforeDestroy() {
    if (this.ro) {
      this.ro.disconnect();
      this.ro = null;
    }
    if (this.fallbackTimer) {
      clearTimeout(this.fallbackTimer);
      this.fallbackTimer = null;
    }
    if (this.player) {
      try {
        this.player.stop();
      } catch (e) {
        /* ignore */
      }
      try {
        if (typeof this.player.destroy === "function") {
          this.player.destroy();
        }
      } catch (e) {
        /* ignore */
      }
      this.player = null;
    }
    this.inited = false;
  },
};
</script>
<style scoped>
.camera-window {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
.video-box {
  height: calc(100% - 40px);
  width: 100%;
}
.camera-title {
  height: 40px;
  width: 100%;
  background-color: rgba(0, 0, 0, 0.05);
  color: #fff;
  line-height: 40px;
  padding: 0 12px;
}
</style>
