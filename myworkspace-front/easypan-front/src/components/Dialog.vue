<template>
    <div>
        <el-dialog
            :show-close="showClose"
            :draggable="true"
            :model-value="show"
            :close-on-click-modal="false"
            :title="title"
            class="cust-dialog"
            :top="top + 'px'"
            :width="width"
            @close="close"
        >
        <div
            class="dialog-body"
            :style="{ 'max-height': maxHeight + 'px', padding: padding + 'px' }"
        >
            <slot></slot>
    </div>
    <template v-if="(buttons && buttons.length > 0) || showCancel">
        <div class="dialog-footer">
            <el-button  link @click="close" v-if="showCancel"> 取消 </el-button>
            <el-button
                class="dialog-buttons"
                v-for="btn in buttons"
                :type="btn.type || 'primary' "
                @click="btn.click"
            >
                {{ btn.text }}
        </el-button>
        </div>
    </template>
    </el-dialog>
    </div>
</template>

<script setup>
const props = defineProps ({
    title: {
        type: String,
    },
    show: {
        type: Boolean,
        default: false,
    },
    showClose: {
        type: Boolean,
        default: true,
    },
    showCancel: {
        type: Boolean,
        default: true,
    },
    top: {
        type: Number,
        default: 50,
    },
    width: {
        type: String,
        default: "30%",
    },
    buttons: {
        type: Array,
    },
    padding: {
        type: Number,
        default: 15,
    },
});

const maxHeight = window.innerHeight - props.top - 100;

const emit = defineEmits();
const close = () => {
    emit("close");
};
</script>

<style lang="scss">
.cust-dialog {
    margin: 30px auto 10px !important;
    .el-dialog__body {
        padding: 0px;
    }
    .dialog-body {
        border-top: 1px solid #fc0404;
        border-bottom: 1px solid #3efc04;
        min-height: 80px;
        overflow: auto;
    }
    .dialog-footer {
        text-align: right;
        padding: 5px 20px;
    }
    .dialog-buttons{
        background: linear-gradient(45deg, transparent 5%, #f701ff 5%);
        border: 0;
        color: #fff;
        letter-spacing: 1px;
        line-height: 44px;
        box-shadow: 6px 0px 0px #04ebfc;
        outline: transparent;
        position: relative;
    }
    .dialog-buttons::after {
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
        .dialog-buttons:hover::after {
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