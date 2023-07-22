<template>
    <div class="sys-setting-panel">
      <el-form
        :model="formData"
        :rules="rules"
        ref="formDataRef"
        label-width="150px"
        @submit.prevent
      >
        <!-- 用户配置 -->
        <el-form-item label="注册邮箱标题" prop="registerEmailTitle">
          <el-input
            clearable
            placeholder="请输入注册邮件验证码邮件标题"
            v-model.trim="formData.registerEmailTitle"
          ></el-input>
        </el-form-item>
        <!-- 用户配置 -->
        <el-form-item label="注册邮箱内容" prop="registerEmailContent">
          <el-input
            clearable
            placeholder="请输入注册邮件验证码邮件内容%s占位符为验证码内容"
            v-model.trim="formData.registerEmailContent"
          ></el-input>
        </el-form-item>
        <!-- 用户配置 -->
        <el-form-item label="初始空间大小" prop="userInitUseSpace">
          <el-input
            clearable
            placeholder="初始化空间大小"
            v-model.trim="formData.userInitUseSpace"
          >
            <template #suffix>MB</template>
          </el-input>
        </el-form-item>
        <!-- 保存按钮 -->
        <el-form-item label="" prop="">
          <el-button class="save-btn" type="primary" @click="saveSettings">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick } from "vue";
import { useRouter, useRoute } from "vue-router";
const { proxy } = getCurrentInstance();
const router = useRouter();
const route = useRoute();

const api = {
    getSysSettings: "/admin/getSysSettings",
    saveSettings: "/admin/saveSysSettings",
};

const formData = ref({});
const formDataRef = ref();
const rules = {
    registerEmailTitle: [
      { required: true, message: "请输入注册邮件验证码邮件标题" },
    ],
    registerEmailContent: [
      { required: true, message: "请输入注册邮件验证码邮件内容" },
    ],
    userInitUseSpace: [
      { required: true, message: "请输入初始化空间大小" },
      {
        validator: proxy.Verify.number,
        message: "空间大小只能是数字",
      },
    ],
};

const getSysSettings = async () => {
    let result = await proxy.Request({
        url: api.getSysSettings,
    });
    if (!result) {
        return;
    }
    formData.value = result.data;
};
getSysSettings();

const saveSettings = async () => {
    formDataRef.value.validate(async (valid) => {
        if (!valid) {
            return;
        }
        let params = {};
        Object.assign(params, formData.value);
        let result = await proxy.Request({
            url: api.saveSettings,
            params: params,
        });
        if (!result) {
            return;
        }
        proxy.Message.success("保存成功");
    });
};
</script>

<style lang="scss" scoped>
.sys-setting-panel {
    margin-top: 20px;
    width: 600px;
    .save-btn {
        width: 50%;
        margin:0 auto;
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
</style>
