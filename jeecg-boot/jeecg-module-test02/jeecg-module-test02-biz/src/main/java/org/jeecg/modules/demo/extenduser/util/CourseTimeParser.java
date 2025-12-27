package org.jeecg.modules.demo.extenduser.util;

import cn.hutool.core.util.StrUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 课程时间解析工具类
 * 处理格式："周一1-2节,周三3-4节" → 结构化的星期+节次
 */
public class CourseTimeParser {

    // 星期映射：中文星期 → 数字（1=周一，7=周日）
    private static final Map<String, Integer> WEEK_MAP = new HashMap<>();

    static {
        WEEK_MAP.put("周一", 1);
        WEEK_MAP.put("周二", 2);
        WEEK_MAP.put("周三", 3);
        WEEK_MAP.put("周四", 4);
        WEEK_MAP.put("周五", 5);
        WEEK_MAP.put("周六", 6);
        WEEK_MAP.put("周日", 7);
    }

    /**
     * 解析课程时间文本，返回结构化的时间段列表
     * @param courseTime 课程时间文本（如"周一1-2节,周三3-4节"）
     * @return 时间段列表：每个元素是Map，包含week（星期）、start（起始节次）、end（结束节次）
     */
    public static List<Map<String, Integer>> parseCourseTime(String courseTime) {
        List<Map<String, Integer>> timeList = new ArrayList<>();
        if (StrUtil.isBlank(courseTime)) {
            return timeList;
        }

        // 拆分多个时间段（如"周一1-2节,周三3-4节"拆成["周一1-2节", "周三3-4节"]）
        String[] timeSegments = courseTime.split(",");
        for (String segment : timeSegments) {
            segment = segment.trim(); // 去除空格

            // 提取星期（如"周一1-2节" → "周一"）
            String weekStr = null;
            for (String weekKey : WEEK_MAP.keySet()) {
                if (segment.startsWith(weekKey)) {
                    weekStr = weekKey;
                    break;
                }
            }
            if (weekStr == null) {
                continue; // 格式异常则跳过
            }
            Integer week = WEEK_MAP.get(weekStr);

            // 使用正则表达式提取节次区间（如"周一1-2节" → "1-2"）
            Pattern pattern = Pattern.compile("\\d+-\\d+");
            Matcher matcher = pattern.matcher(segment);
            if (matcher.find()) {
                String sectionStr = matcher.group();
                String[] sections = sectionStr.split("-");
                if (sections.length != 2) {
                    continue;
                }
                int startSection;
                int endSection;
                try {
                    startSection = Integer.parseInt(sections[0]);
                    endSection = Integer.parseInt(sections[1]);
                } catch (NumberFormatException e) {
                    continue; // 数字格式异常则跳过
                }

                // 封装结构化数据
                Map<String, Integer> timeMap = new HashMap<>();
                timeMap.put("week", week);
                timeMap.put("start", startSection);
                timeMap.put("end", endSection);
                timeList.add(timeMap);
            }
        }
        return timeList;
    }

    /**
     * 判断两个时间段是否冲突
     * @param time1 已选课程的时间段（week+start+end）
     * @param time2 新选课程的时间段（week+start+end）
     * @return true=冲突，false=不冲突
     */
    public static boolean isTimeConflict(Map<String, Integer> time1, Map<String, Integer> time2) {
        // 星期不同，直接不冲突
        if (!time1.get("week").equals(time2.get("week"))) {
            return false;
        }
        // 节次重叠判断：比如[1,2]和[2,3]算冲突，[1,2]和[3,4]不冲突
        int start1 = time1.get("start");
        int end1 = time1.get("end");
        int start2 = time2.get("start");
        int end2 = time2.get("end");
        // 冲突条件：新时间段的起始节次 ≤ 已选的结束节次，且新时间段的结束节次 ≥ 已选的起始节次
        return start2 <= end1 && end2 >= start1;
    }
}
