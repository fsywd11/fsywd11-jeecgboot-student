<template>
  <div>
    <!--引用表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection">
      <!--插槽:table标题-->
      <template #tableTitle>
        <a-button type="primary" v-auth="'newLostAndFound:lost_and_found:add'" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>
        <a-button type="primary" v-auth="'newLostAndFound:lost_and_found:exportXls'" preIcon="ant-design:export-outlined" @click="onExportXls">
          导出</a-button
        >
        <j-upload-button
          type="primary"
          v-auth="'newLostAndFound:lost_and_found:importExcel'"
          preIcon="ant-design:import-outlined"
          @click="onImportXls"
        >导入</j-upload-button
        >

        <a-dropdown v-if="selectedRowKeys.length > 0">
          <template #overlay>
            <a-menu>
              <a-menu-item key="1" @click="batchHandleDelete">
                <Icon icon="ant-design:delete-outlined"></Icon>
                删除
              </a-menu-item>
            </a-menu>
          </template>
          <a-button v-auth="'newLostAndFound:lost_and_found:deleteBatch'"
          >批量操作
            <Icon icon="mdi:chevron-down"></Icon>
          </a-button>
        </a-dropdown>
        <!-- 高级查询 -->
        <super-query :config="superQueryConfig" @search="handleSuperQuery" />
      </template>
      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)" />
      </template>
      <!--字段回显插槽-->
      <template v-slot:bodyCell="{ column, record, index, text }"> </template>
    </BasicTable>
    <!-- 表单区域 -->
    <LostAndFoundModal @register="registerModal" @success="handleSuccess"></LostAndFoundModal>
  </div>
</template>

<script lang="ts" name="newLostAndFound-lostAndFound" setup>
import { ref, reactive, computed, unref } from 'vue';
import { BasicTable, useTable, TableAction } from '/@/components/Table';
import { useModal } from '/@/components/Modal';
import { useListPage } from '/@/hooks/system/useListPage';
import LostAndFoundModal from './components/LostAndFoundModal.vue';
import { columns, searchFormSchema, superQuerySchema } from './LostAndFound.data';
import { list, deleteOne, batchDelete, getImportUrl, getExportUrl, saveOrUpdate } from './LostAndFound.api';
import { downloadFile } from '/@/utils/common/renderUtils';
import { useUserStore } from '/@/store/modules/user';
import { useMessage } from '/@/hooks/web/useMessage';
import { getDateByPicker } from '/@/utils';
//日期个性化选择
const fieldPickers = reactive({});
const queryParam = reactive<any>({});
const checkedKeys = ref<Array<string | number>>([]);
const userStore = useUserStore();
const { createMessage } = useMessage();
//注册model
const [registerModal, { openModal }] = useModal();
//注册table数据
const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
  tableProps: {
    title: '失物招领表',
    api: list,
    columns,
    canResize: true,
    formConfig: {
      //labelWidth: 120,
      schemas: searchFormSchema,
      autoSubmitOnEnter: true,
      showAdvancedButton: true,
      fieldMapToNumber: [],
      fieldMapToTime: [],
    },
    actionColumn: {
      width: 120,
      fixed: 'right',
    },
    beforeFetch: (params) => {
      if (params && fieldPickers) {
        for (let key in fieldPickers) {
          if (params[key]) {
            params[key] = getDateByPicker(params[key], fieldPickers[key]);
          }
        }
      }
      return Object.assign(params, queryParam);
    },
  },
  exportConfig: {
    name: '失物招领表',
    url: getExportUrl,
    params: queryParam,
  },
  importConfig: {
    url: getImportUrl,
    success: handleSuccess,
  },
});

const [registerTable, { reload }, { rowSelection, selectedRowKeys }] = tableContext;

// 高级查询配置
const superQueryConfig = reactive(superQuerySchema);

/**
 * 高级查询事件
 */
function handleSuperQuery(params) {
  Object.keys(params).map((k) => {
    queryParam[k] = params[k];
  });
  reload();
}
/**
 * 新增事件
 */
function handleAdd() {
  openModal(true, {
    isUpdate: false,
    showFooter: true,
  });
}

const { createConfirm } = useMessage();
const APPLY_STATUS = '2'; // 已申请状态
const INIT_STATUS = '1';  // 初始/取消申请后的状态（可根据业务自定义）

/**
 * 申请事件（原有逻辑保留，优化提示）
 */
async function handleApply(record: Recordable) {
  createConfirm({
    iconType: 'warning',
    title: '确认申请',
    content: `是否确认申请该失物？`,
    okText: '确认',
    cancelText: '取消',
    onOk: async () => {
      try {
        // 构造申请参数：设置状态为2，填写申请人信息
        const applyParams = {
          id: record.id,
          status: APPLY_STATUS,
          claimUserId: userStore.getUserInfo.id,
          claimUserName: userStore.getUserInfo.realname,
        };

        await saveOrUpdate(applyParams, true);
        createMessage.success('申请成功！');
        handleSuccess(); // 刷新表格
      } catch (error) {
        createMessage.error('申请失败，请稍后重试！');
      }
    },
  });
}

/**
 * 取消申请事件
 */
async function handleCancelApply(record: Recordable) {
  createConfirm({
    iconType: 'warning',
    title: '确认取消申请',
    content: `是否确认取消该失物的申请？`,
    okText: '确认',
    cancelText: '取消',
    onOk: async () => {
      try {
        // 构造取消申请参数：恢复初始状态，清空申请人信息
        const cancelParams = {
          id: record.id,
          status: INIT_STATUS, // 恢复为初始状态（可自定义）
          claimUserId: '',   // 清空申请人ID
          claimUserName: '',   // 清空申请人姓名
        };

        await saveOrUpdate(cancelParams, true); // 复用原有修改接口
        createMessage.success('取消申请成功！');
        handleSuccess(); // 刷新表格
      } catch (error) {
        createMessage.error('取消申请失败，请稍后重试！');
      }
    },
  });
}

/**
 * 详情
 */
function handleDetail(record: Recordable) {
  openModal(true, {
    record,
    isUpdate: true,
    showFooter: false,
  });
}
/**
 * 删除事件
 */
async function handleDelete(record) {
  await deleteOne({ id: record.id }, handleSuccess);
}
/**
 * 批量删除事件
 */
async function batchHandleDelete() {
  await batchDelete({ ids: selectedRowKeys.value }, handleSuccess);
}
/**
 * 成功回调
 */
function handleSuccess() {
  selectedRowKeys.value = [];
  reload();
}
/**
 * 操作栏：动态控制申请/取消申请按钮
 */
function getTableAction(record) {
  const currentUserId = userStore.getUserInfo.id; // 当前登录用户ID
  // 统一转字符串比较，避免数据类型不一致导致判断错误
  const currentUserIdStr = String(currentUserId);
  const claimUserIdStr = String(record.claimUserId || '');

  // 状态为2（已申请）
  if (record.status === APPLY_STATUS) {
    // 仅申请人自己可见“取消申请”按钮
    if (currentUserIdStr === claimUserIdStr) {
      return [
        {
          label: '取消申请',
          onClick: handleCancelApply.bind(null, record),
          auth: 'newLostAndFound:lost_and_found:edit', // 复用原有编辑权限
        },
      ];
    } else {
      // 非申请人，不显示任何操作按钮
      return [];
    }
  } else if (record.status === INIT_STATUS) {
    // 状态为1（未申请），显示“申请”按钮
    return [
      {
        label: '申请',
        onClick: handleApply.bind(null, record),
        auth: 'newLostAndFound:lost_and_found:edit',
      },
    ];
  }
}
/**
 * 下拉操作栏
 */
function getDropDownAction(record) {
  return [
    {
      label: '详情',
      onClick: handleDetail.bind(null, record),
    },
    {
      label: '删除',
      popConfirm: {
        title: '是否确认删除',
        confirm: handleDelete.bind(null, record),
        placement: 'topLeft',
      },
      auth: 'newLostAndFound:lost_and_found:delete',
    },
  ];
}
</script>

<style lang="less" scoped>
:deep(.ant-picker),
:deep(.ant-input-number) {
  width: 100%;
}
</style>
