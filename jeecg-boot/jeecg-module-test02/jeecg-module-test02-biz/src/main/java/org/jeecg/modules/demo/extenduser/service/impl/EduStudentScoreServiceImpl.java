package org.jeecg.modules.demo.extenduser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.demo.extenduser.entity.EduStudentScore;
import org.jeecg.modules.demo.extenduser.mapper.EduStudentScoreMapper;
import org.jeecg.modules.demo.extenduser.service.IEduStudentScoreService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @Description: 学生成绩表
 * @Author: jeecg-boot
 * @Date:   2025-12-28
 * @Version: V1.0
 */
@Service
@Slf4j
public class EduStudentScoreServiceImpl extends ServiceImpl<EduStudentScoreMapper, EduStudentScore> implements IEduStudentScoreService {

    @Override
    public void sevenDaysLaterBusiness() {
        //自动检查成绩，若是课程已结课，则自动计算成绩的绩点和综合成绩，根据成绩和占比
        // 1. 查询【状态为3】的待结算成绩数据
        QueryWrapper<EduStudentScore> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("score_status", "3"); // 状态3=待结算
        List<EduStudentScore> waitSettleList = this.list(queryWrapper);

        if (waitSettleList.isEmpty()) {
            log.info("当前无待结算（状态3）的成绩数据");
            return;
        }

        // 2. 遍历数据，计算总成绩+绩点
        for (EduStudentScore score : waitSettleList) {
            // 2.1 取出各成绩和对应占比（占比是百分比，需转小数）
            Double usualScore = score.getUsualScore();
            Double usualPersent = score.getUsualScorePersent() / 100.0; // 例：30% → 0.3
            Double midScore = score.getMidScore();
            Double midPersent = score.getMidScorePersent() / 100.0;
            Double finalScore = score.getFinalScore();
            Double finalPersent = score.getFinalScorePersent() / 100.0;

            // 2.2 计算总成绩（成绩×占比 求和）
            Double totalScore = (usualScore * usualPersent) + (midScore * midPersent) + (finalScore * finalPersent);
            // 保留2位小数，避免浮点误差
            totalScore = BigDecimal.valueOf(totalScore)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
            score.setTotalScore(totalScore);

            // 2.3 计算5.0制绩点, 规则：5.0制
            // 保留小数点后一位，避免浮点误差
            Double gpa = calculate5PointGpa(totalScore);
            gpa = BigDecimal.valueOf(gpa)
                    .setScale(1, RoundingMode.HALF_UP)
                    .doubleValue();
            score.setGradePoint(gpa);
        }

        // 3. 批量更新数据到数据库
        boolean updateSuccess = this.updateBatchById(waitSettleList);
        if (updateSuccess) {
            log.info("成绩结算完成，共处理{}条数据", waitSettleList.size());
        } else {
            log.error("成绩结算数据更新失败");
        }
    }

    /**
     * 5.0制绩点换算规则（行业通用版）：
     * 90~100分 → 4.0~5.0；80~89分 → 3.0~3.9；70~79分 → 2.0~2.9；
     * 60~69分 → 1.0~1.9；60分以下 → 0
     */
    private Double calculate5PointGpa(Double totalScore) {
        if (totalScore == null) return 0.0;
        double score = totalScore;
        if (score >= 90) {
            double gpa = 4.0 + (score - 90) * 0.1;
            return Math.min(gpa, 5.0); // 上限5.0
        } else if (score >= 80) {
            return 3.0 + (score - 80) * 0.1;
        } else if (score >= 70) {
            return 2.0 + (score - 70) * 0.1;
        } else if (score >= 60) {
            return 1.0 + (score - 60) * 0.1;
        } else {
            return 0.0;
        }
    }
}
