<template>
  <div class="course-selection-container">
    <div class="page-header">
      <h2>学生选课系统</h2>
      <div class="student-info"> 当前登录学生姓名：{{ studentrealname }} </div>
    </div>

    <!-- 已选课程区域 -->
    <div class="course-section">
      <h3>已选课程</h3>
      <div class="course-list" v-if="selectedCourses.length > 0">
        <div class="course-card" v-for="course in selectedCourses" :key="course.id">
          <div class="course-header">
            <span class="course-name">{{ course.courseName }}</span>
            <span class="course-no">课程编号：{{ course.courseNo }}</span>
          </div>
          <div class="course-body">
            <div class="course-item">选课ID：{{ course.id }}</div>
            <div class="course-item">学生编号：{{ course.studentNo || '未知' }}</div>
            <div class="course-item">选课状态：{{ course.status === '1' ? '已选' : '未知' }}</div>
          </div>
        </div>
      </div>
      <div class="empty-tip" v-else>暂无已选课程</div>
    </div>

    <!-- 可选课程区域 -->
    <div class="course-section">
      <h3>可选课程</h3>
      <EduCouses />
    </div>
  </div>
</template>

<script setup lang="ts">
  import { ref, onMounted, computed, provide, ComputedRef, Ref } from 'vue';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { useUserStore } from '/@/store/modules/user';
  import { getSelectedCourses, selectCourse } from '/@/views/system/selectcoursemanage/selectcoursemanage.api';
  import EduCouses from '@/views/system/selectcoursemanage/commpents/EduCouses.vue';

  // 定义类型接口
  interface CourseItem {
    id: string | number;
    courseId: string | number;
    courseName: string;
    courseNo: string;
    studentNo?: string;
    status?: string;
    maxStudent?: number | string;
    currentStudent?: number | string;
  }

  interface CourseSelectionProvide {
    studentId: ComputedRef<string>;
    selectedCourses: Ref<CourseItem[]>;
    loading: Ref<boolean>;
    isCourseSelected: (courseId: string | number) => boolean;
    refreshSelectedCourses: () => Promise<void>;
    createMessage: ReturnType<typeof useMessage>['createMessage'];
    handleSelectCourse: (course: CourseItem) => Promise<void>;
  }

  // 获取用户信息
  const userStore = useUserStore();
  const studentrealname = userStore.getUserInfo.realname || '';
  const studentId = computed(() => {
    const id = userStore.getUserInfo.id;
    return typeof id === 'string' ? id : (id || '3d464b4ea0d2491aab8a7bde74c57e95').toString();
  });

  // 状态管理 - 响应式数据
  const { createMessage } = useMessage();
  const selectedCourses = ref<CourseItem[]>([]);
  const loading = ref(false);

  // 统一的课程选中状态检查方法
  const isCourseSelected = (courseId: string | number): boolean => {
    return selectedCourses.value.some((item) => item.courseId === courseId);
  };

  // 获取已选课程列表 - 优化数据处理逻辑
  const fetchSelectedCourses = async (): Promise<void> => {
    try {
      const res = await getSelectedCourses({ studentId: studentId.value });
      // 标准化数据处理
      let selectedData: CourseItem[] = [];
      if (res?.success !== false && res) {
        if (Array.isArray(res.result)) {
          selectedData = res.result as CourseItem[];
        } else if (typeof res.result === 'object' && res.result !== null) {
          selectedData = [res.result as CourseItem];
        } else if (Array.isArray(res)) {
          selectedData = res as CourseItem[];
        }
      }
      selectedCourses.value = JSON.parse(JSON.stringify(selectedData));
    } catch (error) {
      const errMsg = error instanceof Error ? error.message : '未知错误';
      createMessage.error('获取已选课程失败：' + errMsg);
    }
  };

  // 选课操作 - 统一管理
  const handleSelectCourse = async (course: CourseItem): Promise<void> => {
    if (!course?.id) {
      createMessage.warning('课程信息异常，无法选课');
      return;
    }
    if (isCourseSelected(course.id)) {
      createMessage.warning('该课程已选，无需重复选择');
      return;
    }

    const maxStudent = Number(course.maxStudent) || 0;
    const currentStudent = Number(course.currentStudent) || 0;
    const remaining = maxStudent - currentStudent;

    if (remaining <= 0) {
      createMessage.warning('该课程名额已满，无法选课');
      return;
    }

    loading.value = true;
    try {
      const selectRes = await selectCourse({
        studentId: studentId.value,
        courseId: course.id,
      });
      if (selectRes === '选课成功' || selectRes?.success) {
        await fetchSelectedCourses(); // 刷新已选课程
      } else {
        createMessage.error('选课失败：' + (selectRes?.message || '操作失败'));
      }
    } catch (error) {
    } finally {
      loading.value = false;
    }
  };

  //  核心优化：通过provide向子组件提供响应式数据和方法（带类型）
  provide<CourseSelectionProvide>('courseSelection', {
    studentId,
    selectedCourses,
    loading,
    isCourseSelected,
    refreshSelectedCourses: fetchSelectedCourses,
    createMessage,
    handleSelectCourse,
  });

  // 页面初始化加载数据
  onMounted(async () => {
    await fetchSelectedCourses();
  });
</script>

<style scoped>
  .course-selection-container {
    max-width: 100%;
    margin: 0 auto;
    padding: 20px;
    font-family: 'Microsoft YaHei', sans-serif;
  }

  .page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 30px;
    padding-bottom: 10px;
    border-bottom: 1px solid #eee;
  }

  .page-header h2 {
    margin: 0;
    color: #333;
  }

  .student-info {
    color: #666;
    font-size: 14px;
  }

  .course-section {
    margin-bottom: 40px;
  }

  .course-section h3 {
    color: #409eff;
    margin-bottom: 20px;
    font-size: 18px;
    border-left: 4px solid #409eff;
    padding-left: 10px;
  }

  .course-list {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
    gap: 20px;
    margin-bottom: 20px;
  }

  .course-card {
    border: 1px solid #e6e6e6;
    border-radius: 8px;
    padding: 15px;
    background: #fff;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
    transition: all 0.3s;
  }

  .course-card:hover {
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }

  .course-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;
    padding-bottom: 10px;
    border-bottom: 1px dashed #eee;
  }

  .course-name {
    font-size: 16px;
    font-weight: bold;
    color: #333;
  }

  .course-no {
    font-size: 12px;
    color: #999;
  }

  .course-body {
    margin-bottom: 15px;
  }

  .course-item {
    font-size: 14px;
    color: #666;
    margin-bottom: 8px;
    display: flex;
    justify-content: space-between;
  }

  .empty-tip {
    text-align: center;
    padding: 30px;
    color: #999;
    background: #f9f9f9;
    border-radius: 8px;
  }
</style>
