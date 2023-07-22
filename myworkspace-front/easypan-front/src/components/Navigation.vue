<template>
    <div class="top-navigation">
        <template v-if="folderList.length > 0">
            <span class="back link" @click="backParent">返回上一级</span>
            <el-divider direction="vertical"></el-divider>
        </template>
        <span v-if="folderList.length == 0" class="all-file">全部文件</span>
        <span
          v-if="folderList.length > 0"
          class="link"
          @click="setCurrentFolder(-1)"
        >
          全部文件
        </span>
        <template v-for="(item, index) in folderList">
            <span class="iconfont icon-right"></span>
            <span
              class="link"
              v-if="index < folderList.length - 1"
              @click="setCurrentFolder(index)"
              >{{ item.fileName }}</span
            >
            <span v-if="index == folderList.length - 1" class="text">{{
              item.fileName
            }}</span>
        </template>
    </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick, watch } from "vue";
const { proxy } = getCurrentInstance();
import { useRouter, useRoute } from "vue-router";
const router = useRouter();
const route = useRoute();

const props = defineProps({
    watchPath: {
        type: Boolean,
        default: true,
    },
    shareId: {
        type: String,
    },
    adminShow: {
        type: Boolean,
        default: false,
    },
});

const api = {
    getFolderInfo: "/file/getFolderInfo",
    getFolderInfo4Share: "/showShare/getFolderInfo",
    getFolderInfo4Admin: "/admin/getFolderInfo",
};

// 分类
const category = ref();
// 目录集合
const folderList = ref([]);
// 当前目录
const currentFolder = ref({fileId: "0"});

const init = () => {
    folderList.value = [];
    currentFolder.value = {fileId: "0"};
    doCallback();
};

const openFolder = (data) => {
    const { fileId, fileName } = data;
    const folder = {
        fileName: fileName,
        fileId: fileId,
    };
    folderList.value.push(folder);
    currentFolder.value = folder;
    setPath();
};
defineExpose({ openFolder });

// 返回上一级
const backParent = () => {
    let currentIndex = null;
    for (let i = 0; i < folderList.value.length; i++) {
        if (folderList.value[i].fileId == currentFolder.value.fileId) {
            currentIndex = i;
            break;
        }
    }
    setCurrentFolder(currentIndex - 1);
};
// 点击导航  设置当前目录
const setCurrentFolder = (index) => {
    if (index == -1) {
      // 返回全部
      currentFolder.value = { fileId: "0" };
      folderList.value = [];
    } else {
        currentFolder.value = folderList.value[index];
        folderList.value.splice(index + 1, folderList.value.length);
    }
    setPath();
};

const setPath = () => {
    if (!props.watchPath) {
        // TODO 设置不监听路由回调方法
        doCallback();
        return;
    }
    let pathArray = [];
    folderList.value.forEach(item => {
        pathArray.push(item.fileId);
    })
    router.push({
        path: route.path,
        query: pathArray.length == 0 ? "" : { path: pathArray.join("/") },
    });
};

// 获取当前路径的目录
const getNavigationFolder = async (path) => {
    let url = api.getFolderInfo;
    if (proxy.shareId) {
        url = api.getFolderInfo4Share;
    }
    if (props.adminShow) {
        url = api.getFolderInfo4Admin;
    }
    let result = await proxy.Request({
        url: url,
        showLoading: false,
        params: {
            path: path,
            shareId: props.shareId,
        },
    });
    if (!result) {
        return;
    }
    folderList.value = result.data;
};

const emit = defineEmits(["navChange"]);
const doCallback = () => {
    emit("navChange", {
        categoryId: category.value,
        curFolder: currentFolder.value,
    });
};

watch(
    () => route,
    (newVal, oldVal) => {
        if (!props.watchPath) {
            return;
        }
        if (newVal.path.indexOf("/main") === -1 &&
            newVal.path.indexOf("/settings/fileList") === -1 &&
            newVal.path.indexOf("/share") === -1
        ) {
            return;
        }
        const path = newVal.query.path;
        category.value = newVal.params.category;
        if (path == undefined) {
            init();
        } else {
            getNavigationFolder(path);
            let pathArray = path.split("/");
            currentFolder.value = {
                fileId: pathArray[pathArray.length - 1],
            };
            doCallback();
        }
    },
    { immediate: true, deep: true }
);
</script>

<style lang="scss" scoped>
.top-navigation {
    font-size: 13px;
    display: flex;
    align-items: center;
    line-height: 40px;
    .all-file {
        font-weight: bold;
    }
    .link {
        color: #f701ff;
        cursor: pointer;
    }
    .icon-right {
        color: #f701ff;
        padding: 0px 5px;
        font-size: 13px;
    }
}
</style>
