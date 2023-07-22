<template>
    <div class="login-body">
        <div class="bg"></div>
        <div class="login-panel">
            <el-form
            class="login-register"
                :model="formData"
                :rules="rules"
                ref="formDataRef"
                @submit.prevent
            >
            <div class="login-title">Easy云盘</div>
            <!-- input输入 -->
            <el-form-item prop="email" >
                <el-input
                size="large"
                clearable
                placeholder="请输入邮箱"
                v-model.trim="formData.email"
                maxLength="150"
                >
                <template #prefix>
                    <span class="iconfont icon-account"></span>
                </template>
            </el-input>
            </el-form-item>
            <!-- 登录密码 -->
            <el-form-item prop="password" v-if="opType == 1">
                <el-input
                    type="password"
                    size="large"
                    placeholder="请输入密码"
                    v-model.trim="formData.password"
                    show-password
                    >
                    <template #prefix>
                        <span class="iconfont icon-password"></span>
                    </template>
                </el-input>
            </el-form-item>
            <!-- 注册 -->
            <div v-if="opType == 0 || opType == 2">
                <el-form-item prop="emailCode">
                    <div class="send-email-panel">
                        <el-input
                        size="large"
                        placeholder="请输入邮箱验证码"
                        v-model.trim="formData.emailCode"
                    >
                        <template #prefix>
                            <span class="iconfont icon-checkcode"></span>
                        </template>
                    </el-input>
                    <el-button class="send-mail-btn" type="primary" size="large" @click="getEmailCode">
                        获取验证码
                    </el-button>
                    </div>
                    <el-popover placement="left" :width="500" trigger="click">
                        <div>
                            <p>1.在垃圾箱中查找邮箱验证码</p>
                            <p>2.在邮箱中头像->设置->反垃圾->白名单->设置邮件地址白名单</p>
                            <p>3.将邮箱【3503201604@qq.com】添加到白名单不知道怎么设置?</p>
                        </div>
                        <template #reference>
                            <span class="a-link" :style="{ 'font-size': '14px' }"
                            >未收到邮箱验证码?</span>
                        </template>
                    </el-popover>
                </el-form-item>
                <!-- 昵称 -->
                <el-form-item prop="nickName" v-if="opType == 0">
                    <el-input
                        size="large"
                        placeholder="请输入昵称"
                        v-model.trim="formData.nickName"
                        maxLength="20"
                    >
                        <template #prefix>
                            <span class="iconfont icon-account"></span>
                        </template>
                    </el-input>
                </el-form-item>
                <!-- 注册密码, 找回密码 -->
            <el-form-item prop="registerPassword">
                <el-input
                    type="password"
                    size="large"
                    placeholder="请输入密码"
                    v-model.trim="formData.registerPassword"
                    show-password
                >
                    <template #prefix>
                        <span class="iconfont icon-password"></span>
                    </template>
                </el-input>
            </el-form-item>
            <!-- 重复密码 -->
            <el-form-item prop="reRegisterPassword">
                <el-input
                    type="password"
                    size="large"
                    placeholder="请再次输入密码"
                    v-model.trim="formData.reRegisterPassword"
                    show-password
                >
                    <template #prefix>
                        <span class="iconfont icon-password"></span>
                    </template>
                </el-input>
            </el-form-item>
            </div>
            <!-- 验证码 -->
            <el-form-item prop="checkCode">
                <div class="check-code-panel">
                <el-input
                size="large"
                placeholder="请输入验证码"
                v-model.trim="formData.checkCode"
                @keyup.enter="doSubmit"
                >
                <template #prefix>
                    <span class="iconfont icon-checkcode"></span>
                </template>
              </el-input>
              <img
                :src="checkCodeUrl"
                class="check-code"
                @click="changeCheckCode(0)"
               />
             </div>
            </el-form-item>
            <!-- 登录 -->
            <el-form-item v-if="opType == 1">
                <div class="rememberme=panel">
                    <el-checkbox v-model="formData.rememberMe">记住我</el-checkbox>
                </div>
                <div class="no-account">
                    <a href="javascript:void(0)" class="a-link" @click="showPanel(2)">忘记密码?</a>
                    <a href="javascript:void(0)" class="a-link" @click="showPanel(0)">没有账号?</a>
                </div>
            </el-form-item>
            <!-- 找回密码 -->
            <el-form-item v-if="opType == 2">
                    <a href="javascript:void(0)" class="a-link" @click="showPanel(1)">去登录?</a>
            </el-form-item>
            <el-form-item v-if="opType == 0">
                    <a href="javascript:void(0)" class="a-link" @click="showPanel(1)">已有账号?</a>
            </el-form-item>
            <!-- 登录按钮 -->
            <el-form-item>
                <el-button
                  type="primary"
                  class="op-btn"
                  size="large"
                  @click="doSubmit"
                >
                    <span v-if="opType == 0">注册</span>
                    <span v-if="opType == 1">登录</span>
                    <span v-if="opType == 2">重置密码</span>
                </el-button>
            </el-form-item>
            <div class="login-btn-qq" v-if="opType == 1">
                快捷登录<img src="@/assets/qq.png" @click="qqLogin" />
            </div>
          </el-form>
        </div>
        <Dialog
            :show="dialogConfig4SendMailCode.show"
            :title="dialogConfig4SendMailCode.title"
            :buttons="dialogConfig4SendMailCode.buttons"
            width="500px"
            :showCancel="false"
            @close="dialogConfig4SendMailCode.show = false"
        >
        <el-form
            :model="formData4SendMailCode"
            :rules="rules"
            ref="formData4SendMailCodeRef"
            label-width="80px"
            @submit.prevent
        >
            <!-- 邮箱 -->
            <el-form-item label="邮箱">
                {{ formData.email }}
            </el-form-item>
            <!-- 邮箱验证码 -->
            <el-form-item label="验证码" prop="checkCode">
                <div class="check-code-panel">
                <el-input
                size="large"
                placeholder="请输入验证码"
                v-model.trim="formData4SendMailCode.checkCode"
                >
                <template #prefix>
                    <span class="iconfont icon-checkcode"></span>
                </template>
              </el-input>
              <img
                :src="checkCodeUrl4SendMailCode"
                class="check-code"
                @click="changeCheckCode(1)"
               />
             </div>
            </el-form-item>
          </el-form>
        </Dialog>
    </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import md5 from "js-md5";
const { proxy } = getCurrentInstance();
const router = useRouter();
const route = useRoute();
const api = {
    checkCode: "/api/checkCode",
    sendEmailCode: "/sendEmailCode",
    register: "/register",
    login: "/login",
    resetPwd: "/resetPwd",
    qqlogin: "/qqlogin",
};

// 操作类型 0:注册 1:登录 2:重置密码
const opType = ref(1);
const showPanel = (type) => {
    opType.value = type;
    restForm();
};

onMounted(() => {
    showPanel(1);
});

const checkRePassword = (rule, value, callback) => {
    if ( value !== formData.value.registerPassword ) {
        callback(new Error(rule.message));
    } else {
        callback();
    }
};
const formData = ref({});
const formDataRef = ref();
const rules = {
    email: [
        { required: true, message: "请输入正确的邮箱" },
        { validator: proxy.Verify.email, message: "请输入正确的邮箱" },
        ],
        password: [{ required: true, message: "请输入密码" }],
        emailCode: [{ required: true, message: "请输入邮箱验证码" }],
        nickName: [{ required: true, message: "请输入昵称" }],
        registerPassword: [
            { required: true, message: "请输入密码" },
            { 
                validator: proxy.Verify.password,
                message: "密码只能是数字, 字母， 特殊字符8-18位",
            },
        ],
        reRegisterPassword: [
            { required: true, message: "请再次输入密码" },
            {
                validator: checkRePassword,
                message: "两次输入的密码不一致",
            },
        ],
        checkCode: [{ required: true, message: "请输入图片验证码" }],
};

const checkCodeUrl = ref(api.checkCode);
const checkCodeUrl4SendMailCode = ref(api.checkCode)
const changeCheckCode = (type) => {
    if (type == 0) {
      checkCodeUrl.value = 
        api.checkCode + "?type=" + type + "&time=" + new Date().getTime();
    } else {
        checkCodeUrl4SendMailCode.value = 
        api.checkCode + "?type=" + type + "&time=" + new Date().getTime();
    }
};

// 发送邮箱验证码
const formData4SendMailCode = ref({});
const formData4SendMailCodeRef = ref();

const dialogConfig4SendMailCode = reactive({
    show: false,
    title: "发送邮箱验证码",
    buttons: [
        {
            type: "primary",
            text: "发送验证码",
            click: (e) => {
                sendEmailCode();
            },
        },
    ],
});
const getEmailCode = () => {
    formDataRef.value.validateField("email", (valid) => {
        if (!valid) {
            return;
        }
        dialogConfig4SendMailCode.show = true;
        nextTick( () => {
            changeCheckCode(1);
            formData4SendMailCodeRef.value.resetFields();
            formData4SendMailCode.value = {
                email: formData.value.email,
            };
        });
    });
};
// 发送邮箱验证码
const sendEmailCode = ()=> {
    formData4SendMailCodeRef.value.validate(async (valid) => {
        if (!valid) {
            return;
        }
        const params = Object.assign({}, formData4SendMailCode.value);
        params.type = opType.value == 0 ? 0 : 1;
        let result = await proxy.Request ({
            url: api.sendEmailCode,
            params: params,
            errorCallback: () => {
                changeCheckCode(1);
            },
        });
        if (!result) {
            return;
        }
        proxy.Message.success("验证码发送成功,请登录邮箱查看");
        dialogConfig4SendMailCode.show = false;
    });
};

// 重置表单
const restForm = () => {
    changeCheckCode(0);
    formDataRef.value.resetFields();
    formData.value = {};
    // 登录
    if (opType.value == 1) {
        const cookieLoginInfo = proxy.VueCookies.get("loginInfo");
        if (cookieLoginInfo) {
            formData.value = cookieLoginInfo;
        }
    }
};
// 登录、注册、重置密码、提交表单
const doSubmit = ()=> {
    formDataRef.value.validate(async (valid) => {
        if (!valid) {
            return;
        }
        let params = {};
        Object.assign(params, formData.value);
        // 注册
        if (opType.value == 0 || opType.value == 2) {
            params.password = params.registerPassword;
            delete params.registerPassword;
            delete params.reRegisterPassword;
        }
        // 登录
        if (opType.value == 1) {
            let cookieLoginInfo = proxy.VueCookies.get("loginInfo");
            let cookiePassword = cookieLoginInfo == null ? null : cookieLoginInfo.password;
            if (params.password !== cookiePassword) {
                params.password = md5(params.password);
            }
        }
        let url = null;
        if (opType.value == 0) {
            url = api.register;
        } else if (opType.value == 1) {
            url = api.login;
        } else if (opType.value == 2) {
            URL = api.resetPwd;
        }
        let result = await proxy.Request({
            url: url,
            params: params,
            errorCallback: () => {
                changeCheckCode(0);
            }
        })
        if (!result) {
            return;
        }
        // 注册返回
        if (opType.value == 0) {
            proxy.Message.success("注册成功,请登录");
            showPanel(1);
        } else if (opType.value == 1) {
            if (params.rememberMe) {
                const loginInfo = {
                    email: params.email,
                    password: params.password,
                    rememberMe: params.rememberMe,
                };
                proxy.VueCookies.set("loginInfo", loginInfo, "7d");
            } else {
                proxy.VueCookies.remove("loginInfo");
            }
            proxy.Message.success("登录成功");
            // 存储cookie
            proxy.VueCookies.set("userInfo", result.data, 0);
            // 重定向到原始页面
            const redirectUrl = route.query.redirectUrl || "/";
            router.push(redirectUrl);
        } else if (opType.value == 2) {
            // 重置密码
            proxy.Message.success("重置密码成功,请登录");
            showPanel(1);
        }
    });
};

// qq登录
const qqLogin = async () => {
    let result = await proxy.Request ({
        url: api.qqlogin,
        params: {
            callbackUrl: route.query.redirectUrl || "",
        },
    });
    if (!result) {
        return;
    }
    proxy.VueCookies.remove("userInfo");
    document.location.href = result.data;
};
</script>

<style lang="scss" scoped>
.login-body {
    height: calc(100vh);
    background-size: cover;
    background: url("../assets/login_bg.jpg");
    display: flex;
    .bg {
        flex: 1;
        background-size: cover;
        background-position: left;
        background-size: 660px;
        background-repeat: no-repeat;
        background-image: url("../assets/login_img.svg");
    }
    .login-panel {
        width: 430px;
        margin-right: 5%;
        margin-top: calc((100vh - 550px) / 2);
        .login-register {
            padding: 25px;
            background: #fff;
            border-radius: 5px;
            .login-title {
                text-align: center;
                font-size: 18px;
                font-weight: bold;
                color: #e704fc;
                letter-spacing: 1px;
                margin-bottom: 20px;
                animation-name: glitched;
                animation-duration: calc(.8s * 1.4);
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
            .send-email-panel {
                display: flex;
                width: 100%;
                justify-content: space-between;
                .send-mail-btn {
                    margin-left: 5px;
                    background: linear-gradient(45deg, transparent 5%, #f701ff 5%);
                    border: 0;
                    letter-spacing: 1px;
                    line-height: 44px;
                    box-shadow: 3px 0px 0px #04ebfc;
                    outline: transparent;
                    position: relative;
                }
            }
            .rememberme-panel {
                width: 100%;
            }
            .no-account {
                width: 100%;
                display: flex;
                justify-content: space-between;
            }
            .op-btn {
                width: 100%;
                background: linear-gradient(45deg, transparent 5%, #f701ff 5%);
                border: 0;
                color: #fff;
                letter-spacing: 3px;
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
    .check-code-panel {
        width: 100%;
        display: flex;
        .check-code {
            margin-left: 5px;
            cursor: pointer;
        }
    }
    .login-btn-qq {
        margin-top: 20px;
        text-align: center;
        display: flex;
        align-items: center;
        justify-content: center;
        img {
            cursor: pointer;
            margin-left: 10px;
            width: 20px;
        }
    }
}
</style>
