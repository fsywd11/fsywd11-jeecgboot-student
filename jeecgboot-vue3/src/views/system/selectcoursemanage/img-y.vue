<template>
  <div class="course-selection-container">
    <!-- 顶部导航栏 -->
    <header class="main-header">
      <div class="header-inner">
        <div class="logo-section">
          <svg class="icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg">
            <path d="M840 736H696v-80c0-4.4-3.6-8-8-8h-56c-4.4 0-8 3.6-8 8v80H416v-80c0-4.4-3.6-8-8-8h-56c-4.4 0-8 3.6-8 8v80H184c-17.7 0-32 14.3-32 32v208c0 17.7 14.3 32 32 32h656c17.7 0 32-14.3 32-32V768c0-17.7-14.3-32-32-32zM232 848V784h168v64H232zm216 0V784h168v64H448zm216 0V784h168v64H664zM840 560H184c-17.7 0-32 14.3-32 32v208c0 4.4 3.6 8 8 8h688c4.4 0 8-3.6 8-8V184c0-4.4-3.6-8-8-8H184c-4.4 0-8 3.6-8 8v400c0 17.7 14.3 32 32 32z" fill="#4299e1"/>
          </svg>
          <h1>智慧选课平台</h1>
        </div>
        <div class="user-section">
          <div class="avatar">
            <span>{{ studentrealname.substring(0,1) }}</span>
          </div>
          <div class="user-info">
            <div class="username">{{ studentrealname }}</div>
            <div class="role">在校学生</div>
          </div>
        </div>
      </div>
    </header>

    <!-- 主要内容区 -->
    <main class="content-wrapper">
      <!-- 已选课程区域 -->
      <section class="course-panel selected-panel">
        <div class="panel-header">
          <div class="panel-icon">
            <svg viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg">
              <path d="M928 160H96c-17.7 0-32 14.3-32 32v640c0 17.7 14.3 32 32 32h832c17.7 0 32-14.3 32-32V192c0-17.7-14.3-32-32-32zm-40 656H136V240h752v576zM512 432c-4.4 0-8 3.6-8 8v320c0 4.4 3.6 8 8 8s8-3.6 8-8V440c0-4.4-3.6-8-8-8zm0-192c-4.4 0-8 3.6-8 8v160c0 4.4 3.6 8 8 8s8-3.6 8-8V248c0-4.4-3.6-8-8-8z" fill="#38b2ac"/>
            </svg>
          </div>
          <h2>我的选课清单</h2>
          <div class="count-badge">{{ selectedCourses.length }}</div>
        </div>

        <div class="panel-content">
          <div v-if="selectedCourses.length > 0" class="course-grid">
            <div class="course-card" v-for="course in selectedCourses" :key="course.id">
              <div class="card-header">
                <h3 class="course-title">{{ course.courseName }}</h3>
                <div class="course-code">{{ course.courseNo }}</div>
              </div>
              <div class="card-body">
                <div class="info-row">
                  <span class="label">选课ID：</span>
                  <span class="value">{{ course.id }}</span>
                </div>
                <div class="info-row">
                  <span class="label">学生编号：</span>
                  <span class="value">{{ course.studentNo || '未知' }}</span>
                </div>
                <div class="info-row">
                  <span class="label">选课状态：</span>
                  <span class="value status-tag">{{ course.status === '1' ? '已选' : '未知' }}</span>
                </div>
              </div>
            </div>
          </div>
          <div v-else class="empty-state">
            <svg class="empty-icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg">
              <path d="M816 373.333c0 66.133-53.867 120-120 120s-120-53.867-120-120 53.867-120 120-120 120 53.867 120 120z m0 64c-37.333 0-64-28.8-64-64s26.667-64 64-64 64 28.8 64 64-26.667 64-64 64z m-213.333 149.333l-42.667 42.667-128-128-96 96-42.667-42.667 138.667-138.667c-10.667-10.667-21.333-21.333-32-32-64-64-64-160 0-224s160-64 224 0c64 64 64 160 0 224-10.667 10.667-21.333 21.333-32 32l-138.667 138.667z m-213.333-469.333c-170.667 0-304 133.333-304 304s133.333 304 304 304 304-133.333 304-304-133.333-304-304-304z m0 554.667c-138.667 0-256-117.333-256-256s117.333-256 256-256 256 117.333 256 256-117.333 256-256 256z" fill="#cbd5e0"/>
            </svg>
            <p class="empty-text">你还没有选择任何课程</p>
            <p class="empty-desc">快去挑选心仪的课程吧</p>
          </div>
        </div>
      </section>

      <!-- 可选课程区域 -->
      <section class="course-panel available-panel">
        <div class="panel-header">
          <div class="panel-icon">
            <svg viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg">
              <path d="M880 184H712v-64c0-4.4-3.6-8-8-8h-56c-4.4 0-8 3.6-8 8v64H384v-64c0-4.4-3.6-8-8-8h-56c-4.4 0-8 3.6-8 8v64H144c-17.7 0-32 14.3-32 32v664c0 17.7 14.3 32 32 32h736c17.7 0 32-14.3 32-32V216c0-17.7-14.3-32-32-32zM648.3 426.8L537 315.5c-4.4-4.4-10.8-4.4-15.2 0l-85.5 85.5c-4.4 4.4-4.4 10.8 0 15.2l45.9 45.9c4.4 4.4 10.8 4.4 15.2 0l111.3-111.3c4.4-4.4 4.4-10.8 0-15.2zM320 642c0 4.4 3.6 8 8 8h368c4.4 0 8-3.6 8-8v-48c0-4.4-3.6-8-8-8H328c-4.4 0-8 3.6-8 8v48z" fill="#9f7aea"/>
            </svg>
          </div>
          <h2>可选课程列表</h2>
        </div>

        <div class="panel-content">
          <EduCouses />
        </div>
      </section>
    </main>
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
/* 全局样式重置与基础设置 */
:root {
  --primary-color: #4299e1;
  --secondary-color: #9f7aea;
  --success-color: #38b2ac;
  --text-primary: #2d3748;
  --text-secondary: #718096;
  --text-tertiary: #a0aec0;
  --bg-primary: #f7fafc;
  --bg-secondary: #ffffff;
  --border-color: #e2e8f0;
  --shadow-sm: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
  --shadow-md: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
  --shadow-lg: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
  --radius-sm: 4px;
  --radius-md: 8px;
  --radius-lg: 12px;
}

.course-selection-container {
  min-height: 100vh;
  background-color: var(--bg-primary);
  font-family: 'Inter', 'PingFang SC', 'Microsoft YaHei', sans-serif;
  color: var(--text-primary);
}

/* 顶部导航栏样式 */
.main-header {
  background-color: var(--bg-secondary);
  box-shadow: var(--shadow-sm);
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-inner {
  max-width: 1440px;
  margin: 0 auto;
  padding: 0 24px;
  height: 72px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo-section {
  display: flex;
  align-items: center;
  gap: 12px;
}

.logo-section .icon {
  width: 32px;
  height: 32px;
}

.logo-section h1 {
  font-size: 20px;
  font-weight: 600;
  margin: 0;
  color: var(--primary-color);
}

.user-section {
  display: flex;
  align-items: center;
  gap: 12px;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: var(--primary-color);
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: 600;
}

.user-info {
  text-align: right;
}

.username {
  font-weight: 500;
  font-size: 14px;
}

.role {
  font-size: 12px;
  color: var(--text-secondary);
}

/* 主要内容区样式 */
.content-wrapper {
  max-width: 1440px;
  margin: 0 auto;
  padding: 24px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 24px;
}

/* 课程面板通用样式 */
.course-panel {
  background-color: var(--bg-secondary);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-md);
  overflow: hidden;
  transition: all 0.3s ease;
}

.course-panel:hover {
  box-shadow: var(--shadow-lg);
}

.panel-header {
  padding: 20px 24px;
  display: flex;
  align-items: center;
  gap: 12px;
  border-bottom: 1px solid var(--border-color);
}

.panel-icon {
  width: 28px;
  height: 28px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.panel-icon svg {
  width: 100%;
  height: 100%;
}

.panel-header h2 {
  font-size: 18px;
  font-weight: 600;
  margin: 0;
  flex: 1;
}

.count-badge {
  background-color: var(--success-color);
  color: white;
  font-size: 12px;
  padding: 2px 8px;
  border-radius: 100px;
  font-weight: 500;
}

.panel-content {
  padding: 24px;
  min-height: 600px;
}

/* 已选课程面板样式 */
.selected-panel .panel-header {
  border-bottom-color: rgba(56, 178, 172, 0.1);
}

.selected-panel .panel-header h2 {
  color: var(--success-color);
}

.course-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 16px;
}

.course-card {
  border: 1px solid var(--border-color);
  border-radius: var(--radius-md);
  overflow: hidden;
  transition: all 0.2s ease;
}

.course-card:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-sm);
  border-color: var(--success-color);
}

.course-card .card-header {
  background-color: rgba(56, 178, 172, 0.05);
  padding: 16px;
  border-bottom: 1px solid var(--border-color);
}

.course-title {
  font-size: 16px;
  font-weight: 600;
  margin: 0 0 8px 0;
  color: var(--text-primary);
}

.course-code {
  font-size: 12px;
  color: var(--text-secondary);
}

.card-body {
  padding: 16px;
}

.info-row {
  display: flex;
  margin-bottom: 8px;
  font-size: 14px;
}

.info-row:last-child {
  margin-bottom: 0;
}

.label {
  color: var(--text-secondary);
  min-width: 70px;
}

.value {
  color: var(--text-primary);
  flex: 1;
}

.status-tag {
  background-color: rgba(56, 178, 172, 0.1);
  color: var(--success-color);
  padding: 2px 8px;
  border-radius: var(--radius-sm);
  font-size: 12px;
}

/* 空状态样式 */
.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  padding: 40px 0;
  text-align: center;
}

.empty-icon {
  width: 80px;
  height: 80px;
  margin-bottom: 16px;
  color: var(--text-tertiary);
}

.empty-text {
  font-size: 16px;
  font-weight: 500;
  color: var(--text-secondary);
  margin: 0 0 8px 0;
}

.empty-desc {
  font-size: 14px;
  color: var(--text-tertiary);
  margin: 0;
}

/* 可选课程面板样式 */
.available-panel .panel-header {
  border-bottom-color: rgba(159, 122, 234, 0.1);
}

.available-panel .panel-header h2 {
  color: var(--secondary-color);
}

/* 响应式适配 */
@media (max-width: 768px) {
  .content-wrapper {
    grid-template-columns: 1fr;
  }

  .course-grid {
    grid-template-columns: 1fr;
  }
}
</style>
