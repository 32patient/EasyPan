<template>
    <div class="avatar-upload">
        <div class="avatar-show">
          <template v-if="localFile">
            <img :src="localFile" />
          </template>
          <template v-else>
            <img
              :src="`${modelValue.qqAvatar}`"
              v-if="modelValue && modelValue.qqAvatar"
            />
            <img :src="`/api/getAvatar/${modelValue.userId}`" v-else />
          </template>
        </div>
        <div class="select-btn">
            <el-upload
              name="file"
              :show-file-list="false"
              accept=".png,.PNG,.jpg,.JPG,.jpeg,.JPEG,.gif,.GIF,.bmp,.BMP"
              :multiple="false"
              :http-request="uploadImage"
            >
              <el-button class="select-button" type="primary">选择</el-button>
            </el-upload>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance } from "vue";
import { useRouter, useRoute } from "vue-router";
const { proxy } = getCurrentInstance();
const router = useRoute();
const route = useRoute();

const timestamp = ref("");

const props = defineProps({
    modelValue: {
        type: Object,
        default: null,
    },
});

const localFile = ref(null);
const emit = defineEmits();
const uploadImage = async (file) => {
    file = file.file;
    let img = new FileReader();
    img.readAsDataURL(file);
    img.onload = ({ target }) => {
        localFile.value = target.result;
    };
    emit("update:modelValue", file);
};
</script>

<style lang="scss">
.avatar-upload {
    display: flex;
    justify-content: center;
    align-items: end;
    .avatar-show {
        background: rgb(245, 245, 245);
        width: 150px;
        height: 150px;
        display: flex;
        align-items: center;
        justify-content: center;
        overflow: hidden;
        position: relative;
        .iconfont {
            font-size: 50px;
            color: #ddd;
        }
        img {
            width: 100%;
            height: 100%;
        }
        .op {
            position: absolute;
            color: #f701ff;
            top: 80px;
        }
    }
    .select-btn {
        margin-left: 10px;
        vertical-align: bottom;
        .select-button {
            background: linear-gradient(45deg, transparent 5%, #ff01c8 5%);
            border: 0;
            color: #fff;
            letter-spacing: 1px;
            box-shadow: 6px 0px 0px #ffee00;
            outline: transparent;
            position: relative;
        }
    }
}
</style>