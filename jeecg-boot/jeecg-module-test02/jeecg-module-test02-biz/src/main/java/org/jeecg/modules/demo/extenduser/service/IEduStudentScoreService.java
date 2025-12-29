package org.jeecg.modules.demo.extenduser.service;

import org.jeecg.modules.demo.extenduser.entity.EduStudentScore;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 学生成绩表
 * @Author: jeecg-boot
 * @Date:   2025-12-28
 * @Version: V1.0
 */
public interface IEduStudentScoreService extends IService<EduStudentScore> {

    void sevenDaysLaterBusiness();
}
