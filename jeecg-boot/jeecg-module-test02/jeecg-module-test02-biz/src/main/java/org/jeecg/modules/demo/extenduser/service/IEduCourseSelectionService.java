package org.jeecg.modules.demo.extenduser.service;

import org.jeecg.modules.demo.extenduser.entity.EduCourseSelection;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 选课记录表
 * @Author: jeecg-boot
 * @Date:   2025-12-26
 * @Version: V1.0
 */
public interface IEduCourseSelectionService extends IService<EduCourseSelection> {


    String checkCourseTimeConflict(String studentId, String courseId);
}
