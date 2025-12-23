import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '关联campus_student.id（选课学生ID）',
    align:"center",
    dataIndex: 'studentId_dictText'
   },
   {
    title: '关联edu_course.id（课程ID）',
    align:"center",
    dataIndex: 'courseId_dictText'
   },
   {
    title: '选课时间',
    align:"center",
    dataIndex: 'selectTime'
   },
   {
    title: '是否退选：0未退选/1已退选',
    align:"center",
    dataIndex: 'isCancel_dictText'
   },
   {
    title: '退选时间（未退选则为NULL）',
    align:"center",
    dataIndex: 'cancelTime'
   },
   {
    title: '选课年份（用于分表，如2024）',
    align:"center",
    dataIndex: 'selectYear'
   },
   {
    title: '排序',
    align:"center",
    dataIndex: 'sort'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '关联campus_student.id（选课学生ID）',
    field: 'studentId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入关联campus_student.id（选课学生ID）!'},
          ];
     },
  },
  {
    label: '关联edu_course.id（课程ID）',
    field: 'courseId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入关联edu_course.id（课程ID）!'},
          ];
     },
  },
  {
    label: '选课时间',
    field: 'selectTime',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入选课时间!'},
          ];
     },
    dynamicDisabled:true
  },
  {
    label: '是否退选：0未退选/1已退选',
    field: 'isCancel',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"SELECT_STATUS"
     },
  },
  {
    label: '退选时间（未退选则为NULL）',
    field: 'cancelTime',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
  {
    label: '选课年份（用于分表，如2024）',
    field: 'selectYear',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入选课年份（用于分表，如2024）!'},
          ];
     },
  },
  {
    label: '排序',
    field: 'sort',
    component: 'InputNumber',
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

// 高级查询数据
export const superQuerySchema = {
  studentId: {title: '关联campus_student.id（选课学生ID）',order: 0,view: 'number', type: 'number',},
  courseId: {title: '关联edu_course.id（课程ID）',order: 1,view: 'number', type: 'number',},
  selectTime: {title: '选课时间',order: 2,view: 'datetime', type: 'string',},
  isCancel: {title: '是否退选：0未退选/1已退选',order: 3,view: 'number', type: 'number',dictCode: 'SELECT_STATUS',},
  cancelTime: {title: '退选时间（未退选则为NULL）',order: 4,view: 'datetime', type: 'string',},
  selectYear: {title: '选课年份（用于分表，如2024）',order: 5,view: 'number', type: 'number',},
  sort: {title: '排序',order: 6,view: 'number', type: 'number',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}