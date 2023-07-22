<template>
    <div>登录中,请勿刷新页面</div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick } from "vue";
import { useRouter, useRoute } from "vue-router";
const { proxy } = getCurrentInstance();
const router = useRouter();
const route = useRoute();
const api = {
    logincallback: "/qqlogin/callback",
};

const login = async () => {
    let result = await proxy.Request({
        url: api.logincallback,
        params: router.currentRoute.value.query,
        errorCallback: () => {
            router.push("/");
        },
    });
    if (!result) {
        return;
    }
    let redirectUrl = result.data.errorCallback || "/";
    if (redirectUrl == "/login") {
        redirectUrl = "/";
    }
    proxy.VueCookies.set("userInfo", result.data.userInfo, 0);
    router.push(redirectUrl);
};

login();
</script>

<style lang="scss" scoped>
</style>
