<template>
  <div>
    <div class="top">
      <el-button
        type="success"
        :disabled="selectIdList.length == 0"
        @click="revertBatch"
      >
        <span class="iconfont icon-revert"></span>
        还原</el-button
      >
      <el-button
        type="danger"
        :disabled="selectIdList.length == 0"
        @click="delBatch"
      >
        <span class="iconfont icon-del"></span>
        批量删除</el-button
      >
    </div>
    <div class="file-list">
      <Table
        ref="dataTableRef"
        :columns="columns"
        :dataSource="tableData"
        :fetch="loadDataList"
        :initFetch="true"
        :options="tableOptions"
        @rowSelected="rowSelected"
      >
        <template #fileName="{ index, row }">
          <div
            class="file-item"
            @mouseenter="showOp(row)"
            @mouseleave="cancelShowOp(row)"
          >
            <template
              v-if="
                (row.fileType == 3 || row.fileType == 1) && row.status !== 0
              "
              >
              <Icon :cover="row.fileCover"></Icon>
            </template>
              <template v-else>
                <Icon v-if="row.folderType == 0" :fileType="row.fileType"></Icon>
                <Icon v-if="row.folderType == 1" :fileType="0"></Icon>
              </template>
                <span class="file-name" :title="row.fileName">{{
                  row.fileName
                }}</span>
            <span class="op">
              <template v-if="row.showOp">
                <span class="iconfont icon-link" @click="revert(row)"
                  >还原</span
                >
                <span class="iconfont icon-cancel" @click="delFile(row)"
                  >删除</span
                >
              </template>
            </span>
          </div>
        </template>
        <template #fileSize="{ index, row }">
          <span v-if="row.fileSize">
            {{ proxy.Utils.size2Str(row.fileSize) }}
          </span>
        </template>
      </Table>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance, nextTick } from "vue";
const { proxy } = getCurrentInstance();
const api = {
    loadDataList: "/recycle/loadRecycleList",
    delFile: "/recycle/delFile",
    recoverFile: "/recycle/recoverFile",
};

const columns = [
    {
        label: "文件名",
        prop: "fileName",
        scopedSlots: "fileName",
    },
    {
        label: "删除时间",
        prop: "recoveryTime",
        width: 200,
    },
    {
        label: "大小",
        prop: "fileSize",
        scopedSlots: "fileSize",
        width: 200,
    },
];
const tableData = ref({});
const tableOptions = {
    extHeight: 20,
    selectType: "checkbox",
};

const loadDataList = async () => {
    let params = {
        pageNo: tableData.value.pageNo,
        pageSize: tableData.value.pageSize,
    };
    let result = await proxy.Request({
        url: api.loadDataList,
        params,
    });
    if (!result) {
        return;
    }
    tableData.value = result.data;
};

// 多选 批量选择
const selectIdList = ref([]);
const rowSelected = (rows) => {
    selectIdList.value = [];
    rows.forEach((item) => {
        selectIdList.value.push(item.fileId);
    });
};

const showOp = (row) => {
    tableData.value.list.forEach((item) => {
        item.showOp = false;
    });
    row.showOp = true;
};

const cancelShowOp = (row) => {
    row.showOp = false;
};

// 恢复
const revert = (row) => {
    proxy.Confirm(`你确定要还原【${row.fileName}】吗?`, async () => {
        let result = await proxy.Request({
            url: api.recoverFile,
            params: {
                fileIds: row.fileId,
            },
        });
        if (!result) {
            return;
        }
        loadDataList();
    });
};

const revertBatch = () => {
    proxy.Confirm(`你确定要还原这些文件吗?`, async () => {
        let result = await proxy.Request({
            url: api.recoverFile,
            params: {
                fileIds: selectIdList.value.join(","),
            },
        });
        if (!result) {
            return;
        }
        loadDataList();
    });
};

// 删除文件
const emit = defineEmits(["reload"]);
const delFile = (row) => {
    proxy.Confirm(`你确定要删除【${row.fileName}】吗? 删除后无法恢复`, async () => {
        let result = await proxy.Request({
            url: api.delFile,
            params: {
                fileIds: row.fileId,
            },
        });
        if (!result) {
            return;
        }
        loadDataList();
        emit("reload");
    });
};

const delBatch = () => {
    proxy.Confirm(`你确定要删除这些文件吗? 删除后无法恢复`, async () => {
        let result = await proxy.Request({
            url: api.delFile,
            params: {
                fileIds: selectIdList.value.join(","),
            },
        });
        if (!result) {
            return;
        }
        loadDataList();
        emit("reload");
    });
};
</script>

<style lang="scss" scoped>
@import "@/assets/file.list.scss";
.file-list {
    margin-top: 10px;
    .file-item {
        .op {
            width: 120px;
        }
    }
}
</style>
