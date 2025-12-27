<template>
  <div class="edu-course-container">
    <!--引用表格-->
    <BasicTable @register="registerTable">
      <!--插槽:table标题-->
      <template #tableTitle>
        <!-- 高级查询 -->
        <super-query :config="superQueryConfig" @search="handleSuperQuery" />
      </template>

      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" />
      </template>

      <!--字段回显插槽：显示剩余名额-->
      <template v-slot:bodyCell="{ column, record, index, text }"> </template>
    </BasicTable>

    <!-- 表单区域 -->
    <EduCourseModal @register="registerModal" @success="handleSuccess"></EduCourseModal>
  </div>
</template>

<script lang="ts" name="educrouse-eduCourse" setup>
  import { reactive, inject, onMounted, ComputedRef, Ref } from 'vue';
  import { BasicTable, TableAction, ActionItem } from '/@/components/Table';
  import { useModal } from '/@/components/Modal';
  import { useListPage } from '/@/hooks/system/useListPage';
  import EduCourseModal from '/@/views/system/educrouse/components/EduCourseModal.vue';
  import { columns, searchFormSchema, superQuerySchema } from '/@/views/system/educrouse/EduCourse.data';
  import { getImportUrl, getExportUrl, list } from '/@/views/system/educrouse/EduCourse.api';
  import { getDateByPicker } from '/@/utils';

  // 导入父组件定义的类型
  interface CourseItem {
    id: string | number;
    courseId: string | number;
    courseName: string;
    courseNo: string;
    maxStudent?: number | string;
    currentStudent?: number | string;
  }

  // 或者更新接口定义（推荐）
  interface CourseSelectionProvide {
    studentId: ComputedRef<string | number>;
    selectedCourses: Ref<CourseItem[]>;
    loading: Ref<boolean>;
    isCourseSelected: (courseId: string | number) => boolean;
    refreshSelectedCourses: () => Promise<void>;
    createMessage: ReturnType<typeof useMessage>['createMessage'];
    handleSelectCourse: (course: CourseItem) => Promise<void>;
  }

  // 修复：指定inject的类型，解决unknown类型问题
  const courseSelection = inject<CourseSelectionProvide>('courseSelection');
  if (!courseSelection) {
    throw new Error('courseSelection provide 未定义');
  }

  // 解构获取父组件的状态和方法
  const { loading, isCourseSelected, refreshSelectedCourses, createMessage, handleSelectCourse: parentHandleSelectCourse } = courseSelection;

  // 状态管理（修复未使用的变量）
  const fieldPickers = reactive<Record<string, any>>({});
  const queryParam = reactive<Record<string, any>>({});
  // 注册modal（修复未使用的变量）
  const [registerModal, { openModal: _openModal }] = useModal();

  // 注册table数据
  const { tableContext } = useListPage({
    tableProps: {
      title: '课程表',
      api: list,
      columns,
      canResize: true,
      formConfig: {
        schemas: searchFormSchema,
        autoSubmitOnEnter: true,
        showAdvancedButton: true,
        fieldMapToNumber: [],
        fieldMapToTime: [],
      },
      actionColumn: {
        width: 180,
        fixed: 'right',
      },
      beforeFetch: (params) => {
        if (params && fieldPickers) {
          for (const key in fieldPickers) {
            if (params[key]) {
              params[key] = getDateByPicker(params[key], fieldPickers[key]);
            }
          }
        }
        return Object.assign(params, queryParam);
      },
    },
    exportConfig: {
      name: '课程表',
      url: getExportUrl,
      params: queryParam,
    },
    importConfig: {
      url: getImportUrl,
      success: handleSuccess,
    },
  });

  // 修复：未使用的变量添加下划线前缀
  const [registerTable, { reload }, { rowSelection: _rowSelection, selectedRowKeys }] = tableContext;

  // 高级查询配置
  const superQueryConfig = reactive(superQuerySchema);

  /**
   * 选课操作 - 调用父组件的方法，保证逻辑统一
   */
  const handleSelectCourse = async (course: CourseItem): Promise<void> => {
    await parentHandleSelectCourse(course);
    reload();
  };

  import { deleteOne } from '/@/views/system/eduselectcourse/EduCourseSelection.api';
  import { useMessage } from '@/hooks/web/useMessage';
  import { useUserStore } from '/@/store/modules/user';
  const userStore = useUserStore();
  /**
   * 退课操作（修复参数数量问题）
   */
  async function handleDelete(record: CourseItem): Promise<void> {
    try {
      await deleteOne({ courseId: record.id, studentId: userStore.getUserInfo.id }, handleSuccess);
      await refreshSelectedCourses(); // 刷新父组件的已选课程列表
      reload(); // 刷新表格
      handleSuccess(); // 调用成功回调
    } catch (error) {
      // 修复：error类型未知问题
      const errMsg = error instanceof Error ? error.message : '操作失败';
      createMessage.error('退课失败：' + errMsg);
    }
  }

  /**
   * 高级查询事件
   */
  function handleSuperQuery(params: Record<string, any>): void {
    Object.keys(params).forEach((k) => {
      queryParam[k] = params[k];
    });
    reload();
  }

  /**
   * 成功回调 - 统一更新所有相关数据
   */
  function handleSuccess(): void {
    selectedRowKeys.value = [];
    reload();
    void refreshSelectedCourses(); // 同步更新父组件的已选课程
  }

  /**
   * 操作栏配置 - 修复ActionItem类型错误 + 调整退课按钮禁用逻辑
   */
  function getTableAction(record: CourseItem): ActionItem[] {
    const isSelected = isCourseSelected(record.id);
    const maxStudent = Number(record.maxStudent) || 0;
    const currentStudent = Number(record.currentStudent) || 0;
    const remaining = maxStudent - currentStudent;

    // 修复：color类型必须是指定的字面量类型 + 调整退课按钮disabled逻辑
    const actions: ActionItem[] = [
      {
        label: loading.value ? '处理中...' : isSelected ? '已选' : '选课',
        onClick: () => {
          if (!isSelected) {
            void handleSelectCourse(record);
          }
          return false;
        },
        disabled: loading.value || isSelected || remaining <= 0,
      },
      {
        label: '退课',
        onClick: () => {
          if (isSelected) {
            void handleDelete(record);
          }
          return false;
        },
        // 核心修改：仅当 加载中 / 未选中 / 剩余名额<=0 时禁用退课按钮
        // 已选中且非加载中且有剩余名额时，退课按钮可用
        disabled: loading.value || !isSelected || remaining <= 0,
      },
    ];

    return actions;
  }

  // 页面初始化
  onMounted(() => {
    // 监听已选课程变化，实时刷新表格状态
    if (courseSelection.selectedCourses.value) {
      reload();
    }
  });
</script>

<style lang="less" scoped>
  :deep(.ant-picker),
  :deep(.ant-input-number) {
    width: 100%;
  }

  .edu-course-container {
    width: 100%;
    font-family: 'Microsoft YaHei', sans-serif;
  }

  .page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .student-info {
    color: #666;
    font-size: 14px;
  }
</style>
