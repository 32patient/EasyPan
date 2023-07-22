<template>
    <div class="share">
        <div class="body-content">
            <div class="logo">
                <span class="iconfont icon-pan"></span>
                <span class="name">Easy云盘</span>
            </div>
            <div class="code-panel">
                <div class="file-info">
                    <div class="avatar">
                        <Avatar
                          :userId="shareInfo.userId"
                          :avatar="shareInfo.avatar"
                          :width="50"
                        ></Avatar>
                    </div>
                    <div class="share-info">
                        <div class="user-info">
                            <span class="nick-name">{{ shareInfo.nickName }}</span>
                            <span class="share-time">分享于 {{ shareInfo.shareTime }}</span>
                        </div>
                        <div class="file-name">分享文件：{{ shareInfo.fileName }}</div>
                    </div>
                </div>
                <div class="code-body">
                    <div class="tips">请输入提取码: </div>
                    <div class="input-area">
                        <el-form
                          :model="formData"
                          :rules="rules"
                          ref="formDataRef"
                          @submit.prevent
                        >
                          <!-- 输入框 -->
                          <el-form-item prop="code">
                            <el-input
                              class="input"
                              clearable
                              v-model.trim="formData.code"
                              @keyup.enter="checkShare"
                            ></el-input>
                            <el-button class="get-btn" type="primary" @click="checkShare"
                              >提取文件</el-button
                            >
                          </el-form-item>
                        </el-form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick } from "vue";
const { proxy } = getCurrentInstance();
import { useRouter, useRoute } from "vue-router";
const router = useRouter();
const route = useRoute();

const api = {
    getShareInfo: "/showShare/getShareInfo",
    checkShareCode: "/showShare/checkShareCode",
};

const shareId = route.params.shareId;
const shareInfo = ref({});
const getShareInfo = async () => {
    let result = await proxy.Request({
        url: api.getShareInfo,
        params: {
            shareId,
        },
    });
    if (!result) {
        return;
    }
    shareInfo.value = result.data;
};
getShareInfo();

const formData = ref({});
const formDataRef = ref();
const rules = {
    code: [
        { required: true, message: "请输入提取码" },
        { min: 5, message: "提取码为5位" },
        { max: 5, message: "提取码为5位" },
    ],
};

const checkShare = async () => {
    formDataRef.value.validate(async (valid) => {
        if (!valid) {
            return;
        }
        let params = {};
        Object.assign(params, formData.value);
        let result = await proxy.Request({
            url: api.checkShareCode,
            params: {
                shareId: shareId,
                code: formData.value.code,
            },
        });
        if (!result) {
            return;
        }
        router.push(`/share/${shareId}`);
    });
};
</script>

<style lang="scss" scoped>
.share {
    height: calc(100vh);
    background: url("../../assets/share_bg.png");
    background-repeat: repeat-x;
    background-position: 0 bottom;
    background-color: #eef2f6;
    display: flex;
    justify-content: center;
    .body-content {
        margin-top: calc(100vh / 5);
        width: 500px;
        .logo {
            display: flex;
            align-items: center;
            justify-content: center;
            .icon-pan {
                font-size: 60px;
                color: #01f7ff;
            }
            .name {
                font-weight: bold;
                margin-left: 5px;
                font-size: 25px;
                color: #01f7ff;
                animation-name: glitched;
                animation-duration: calc(.9s * 3.5);
                animation-iteration-count: infinite;
                animation-timing-function: linear;
                }
                @keyframes glitched {
                0% { left: -4px; transform: skew(-20deg); }
                11% { left: 2px; transform: skew(0deg); }
                50% { transform: skew(0deg); }
                51% { transform: skew(10deg); }
                60% { transform: skew(0deg); }
                100% { transform: skew(0deg); }
            }
        }
        .code-panel {
            margin-top: 20px;
            background: #fff;
            border-radius: 5px;
            overflow: hidden;
            box-shadow: 0 0 7px 1px #5757574f;
            .file-info {
                padding: 10px 20px;
                background: #01f7ff;
                color: #fff;
                display: flex;
                align-items: center;
                .avatar {
                    margin-right: 5px;
                }
                .share-info {
                    .user-info {
                        display: flex;
                        align-items: center;
                        .nick-name {
                            font-size: 15px;
                            color: #af01ff;
                        }
                        .share-time {
                            margin-left: 20px;
                            font-size: 12px;
                            color: #af01ff;
                        }
                    }
                    .file-name {
                        color: #af01ff;
                        margin-top: 10px;
                        font-size: 12px;
                    }
                }
            }
            .code-body {
                padding: 30px 20px 60px 20px;
                .tips {
                    font-weight: bold;
                }
                .input-area {
                    margin-top: 15px;
                    .input {
                        flex: 1;
                        margin-right: 10px;
                    }
                    .get-btn {
                        background: linear-gradient(45deg, transparent 5%, #ea01ff 5%);
                        border: 0;
                        color: #fff;
                        line-height: 33px;
                        box-shadow: 6px 0px 0px #04ebfc;
                        outline: transparent;
                        position: relative;
                        }
                        button::after {
                        --slice-0: inset(50% 50% 50% 50%);
                        --slice-1: inset(80% -6px 0 0);
                        --slice-2: inset(50% -6px 30% 0);
                        --slice-3: inset(10% -6px 85% 0);
                        --slice-4: inset(40% -6px 43% 0);
                        --slice-5: inset(80% -6px 5% 0);
                        content: 'LanVinci';
                        display: block;
                        position: absolute;
                        top: 0;
                        left: 0;
                        right: 0;
                        bottom: 0;
                        background: linear-gradient(45deg, transparent 3%, #00E6F6 3%, #00E6F6 5%, #ff0101 5%);
                        text-shadow: -3px -3px 0px #f8f405, 3px 3px 0px #00E6F6;
                        clip-path: var(--slice-0);
                        }
                        button:hover::after {
                        animation: 1s glitch;
                        animation-timing-function: steps(2, end);
                        }
                        @keyframes glitch {
                        0% { clip-path: var(--slice-1); transform: translate(-20px, -10px); }
                        10% { clip-path: var(--slice-3); transform: translate(10px, 10px); }
                        20% { clip-path: var(--slice-1); transform: translate(-10px, 10px); }
                        30% { clip-path: var(--slice-3); transform: translate(0px, 5px); }
                        40% { clip-path: var(--slice-2); transform: translate(-5px, 0px); }
                        50% { clip-path: var(--slice-3); transform: translate(5px, 0px); }
                        60% { clip-path: var(--slice-4); transform: translate(5px, 10px); }
                        70% { clip-path: var(--slice-2); transform: translate(-10px, 10px); }
                        80% { clip-path: var(--slice-5); transform: translate(20px, -10px); }
                        90% { clip-path: var(--slice-1); transform: translate(-10px, 0px); }
                        100% { clip-path: var(--slice-1); transform: translate(0); }
                    }
                }
            }
        }
    }
}
</style>
