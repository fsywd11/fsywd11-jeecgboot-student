package org.jeecg.modules.demo.extenduser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.jeecg.modules.demo.extenduser.entity.EduCourse;
import org.jeecg.modules.demo.extenduser.entity.EduCourseSelection;
import org.jeecg.modules.demo.extenduser.mapper.EduCourseMapper;
import org.jeecg.modules.demo.extenduser.mapper.EduCourseSelectionMapper;
import org.jeecg.modules.demo.extenduser.service.IEduCourseSelectionService;
import org.jeecg.modules.demo.extenduser.util.CourseTimeParser;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @Description: 选课记录表
 * @Author: jeecg-boot
 * @Date:   2025-12-26
 * @Version: V1.0
 */
@Service
public class EduCourseSelectionServiceImpl extends ServiceImpl<EduCourseSelectionMapper, EduCourseSelection> implements IEduCourseSelectionService {
    @Resource
    private EduCourseMapper courseMapper;
    @Resource
    private EduCourseSelectionMapper selectionMapper;

    /**
     * 检测学生选新课是否存在时间冲突
     * @param studentId 学生ID
     * @param newCourseId 新选课程ID
     * @return 冲突结果：无冲突返回null；有冲突返回冲突的课程名称
     */
    public String checkCourseTimeConflict(String studentId, String newCourseId) {
        // 1. 查询新选课程的时间，并解析为结构化数据
        EduCourse newCourse = courseMapper.selectById(newCourseId);
        if (newCourse == null) {
            throw new RuntimeException("课程不存在");
        }
        List<Map<String, Integer>> newCourseTimeList = CourseTimeParser.parseCourseTime(newCourse.getCourseTime());
        if (newCourseTimeList.isEmpty()) {
            throw new RuntimeException("课程时间格式异常");
        }

        // 2. 查询该学生已选的有效课程（状态=1：正常选课，未退课）
        LambdaQueryWrapper<EduCourseSelection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduCourseSelection::getStudentId, studentId)
                .eq(EduCourseSelection::getStatus, 1); // 只查未退课的课程
        List<EduCourseSelection> selectedCourses = selectionMapper.selectList(wrapper);

        // 3. 遍历已选课程，检测时间冲突
        for (EduCourseSelection selection : selectedCourses) {
            // 查询已选课程的详细信息（含上课时间）
            EduCourse selectedCourse = courseMapper.selectById(selection.getCourseId());
            List<Map<String, Integer>> selectedTimeList = CourseTimeParser.parseCourseTime(selectedCourse.getCourseTime());

            // 对比新选课程和已选课程的所有时间段
            for (Map<String, Integer> newTime : newCourseTimeList) {
                for (Map<String, Integer> selectedTime : selectedTimeList) {
                    if (CourseTimeParser.isTimeConflict(selectedTime, newTime)) {
                        // 存在冲突，返回冲突的课程名称
                        return selectedCourse.getCourseName();
                    }
                }
            }
        }

        // 4. 无冲突返回null
        return null;
    }

}
