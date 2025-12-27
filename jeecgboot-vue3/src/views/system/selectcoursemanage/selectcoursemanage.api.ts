import { defHttp } from '/@/utils/http/axios';

// 保持原有API路径不变（不修改educrouse拼写）
enum Api {
  // 已选课程列表
  getSelectedCourses = '/eduselectcourse/eduCourseSelection/getSelectedCourses',
  // 可选课程列表（保留原有拼写educrouse）
  getOptionalCourses = '/educrouse/eduCourse/getOptionalCourses',
  // 选课操作
  selectCourse = '/eduselectcourse/eduCourseSelection/selectCourse',
  // 课程时间冲突检测
  checkCourseConflict = '/eduselectcourse/eduCourseSelection/checkCourseConflict',
}

/**
 * 获取已选课程列表
 * @param params { studentId: 学生ID }
 */
export const getSelectedCourses = (params) => {
  return defHttp.get({ url: Api.getSelectedCourses, params });
};

/**
 * 获取可选课程列表
 * @param params { keyword: 搜索关键词 } 新增参数支持
 */
export const getOptionalCourses = (params = {}) => {
  return defHttp.get({ url: Api.getOptionalCourses, params });
};

/**
 * 课程时间冲突检测
 * @param params { studentId: 学生ID, courseId: 课程ID }
 */
export const checkCourseConflict = (params) => {
  return defHttp.get({ url: Api.checkCourseConflict, params });
};

/**
 * 执行选课操作
 * @param params { studentId: 学生ID, courseId: 课程ID }
 */
export const selectCourse = (params) => {
  // 手动拼接参数到URL，确保@RequestParam能读到
  const queryString = new URLSearchParams(params).toString();
  const fullUrl = `${Api.selectCourse}?${queryString}`;
  // POST请求，参数在URL里
  return defHttp.post({ url: fullUrl });
};
