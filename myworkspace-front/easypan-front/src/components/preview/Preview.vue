<template>
    <PreviewImage
      ref="imageViewRef"
      :imageList="[imageUrl]"
      v-if="fileInfo.fileCategory == 3"
    >
    </PreviewImage>
    <Window
      :show="windowShow"
      @close="closeWindow"
      :width="fileInfo.fileCategory == 1 ? 1500 : 900"
      :title="fileInfo.fileName"
      :align="fileInfo.fileCategory == 1 ? 'center' : 'top'"
      v-else
    >
    <PreviewVideo :url="url" v-if="fileInfo.fileCategory == 1"></PreviewVideo>
    <PreviewDoc :url="url" v-if="fileInfo.fileType == 5"></PreviewDoc>
    <PreviewExcel :url="url" v-if="fileInfo.fileType == 6"></PreviewExcel>
    <PreviewPdf :url="url" v-if="fileInfo.fileType == 4"></PreviewPdf>
    <PreviewTxt
      :url="url"
      v-if="fileInfo.fileType == 7 || fileInfo.fileType == 8"
    ></PreviewTxt>
    <PreviewMusic
      :url="url"
      :fileName="fileInfo.fileName"
      v-if="fileInfo.fileCategory == 2"
    ></PreviewMusic>

    <PreviewDownload
      :createDownloadUrl="createDownloadUrl"
      :downloadUrl="downloadUrl"
      :fileInfo="fileInfo"
      v-if="fileInfo.fileCategory == 5 && fileInfo.fileType != 8"
    ></PreviewDownload>
    </Window>
</template>

<script setup>
import PreviewVideo from "@/components/preview/PreviewVideo.vue";
import PreviewDoc from "@/components/preview/PreviewDoc.vue";
import PreviewExcel from "@/components/preview/PreviewExcel.vue";
import PreviewPdf from "@/components/preview/PreviewPdf.vue";
import PreviewTxt from "@/components/preview/PreviewTxt.vue";
import PreviewMusic from "@/components/preview/PreviewMusic.vue";
import PreviewDownload from "@/components/preview/PreviewDownload.vue";

import { ref, reactive, getCurrentInstance, computed, nextTick } from "vue";
const { proxy } = getCurrentInstance();
import PreviewImage from "./PreviewImage.vue";

const imageUrl = computed( () => {
    return (
        proxy.globalInfo.imageUrl + fileInfo.value.fileCover.replaceAll("_.", ".")
    );
});

const windowShow = ref(false);
const closeWindow = () => {
    windowShow.value = false;
};

const FILE_URL_MAP = {
    0: {
        fileUrl: "/file/getFile",
        videoUrl: "/file/ts/getVideoInfo",
        createDownloadUrl: "/file/createDownloadUrl",
        downloadUrl: "/api/file/download",
    },
    1: {
        fileUrl: "/admin/getFile",
        videoUrl: "/admin/ts/getVideoInfo",
        createDownloadUrl: "/admin/createDownloadUrl",
        downloadUrl: "/api/admin/download",
    },
    2: {
        fileUrl: "/showShare/getFile",
        videoUrl: "/showShare/ts/getVideoInfo",
        createDownloadUrl: "/showShare/createDownloadUrl",
        downloadUrl: "/api/showShare/download",
    },
};

const url = ref(null);
const createDownloadUrl = ref(null);
const downloadUrl = ref(null);

const fileInfo = ref({});
const imageViewRef = ref();
const showPreview = (data, showPart) => {
    fileInfo.value = data;
    if (data.fileCategory == 3) {
        nextTick( () => {
            imageViewRef.value.show(0);
        });
    } else {
        windowShow.value = true;
        let _url = FILE_URL_MAP[showPart].fileUrl;
        let _createDownloadUrl = FILE_URL_MAP[showPart].createDownloadUrl;
        let _downloadUrl = FILE_URL_MAP[showPart].downloadUrl;

        if (data.fileCategory == 1) {
            _url = FILE_URL_MAP[showPart].videoUrl;
        }
        if (showPart == 0) {
            _url = _url + "/" + data.fileId;
            _createDownloadUrl = _createDownloadUrl + "/" + data.fileId;
        } else if (showPart == 1) {
            _url = _url + "/" + data.userId + "/" + data.fileId;
            _createDownloadUrl =
              _createDownloadUrl + "/" + data.userId + "/" + data.fileId;
        } else if (showPart == 2) {
            _url = _url + "/" + data.shareId + "/" + data.fileId;
            _createDownloadUrl =
              _createDownloadUrl + "/" + data.shareId + "/" + data.fileId;
        }
        url.value = _url;
        createDownloadUrl.value = _createDownloadUrl;
        downloadUrl.value = _downloadUrl;
    }
};

defineExpose({ showPreview });
</script>

<style lang="scss" scoped>
</style>
