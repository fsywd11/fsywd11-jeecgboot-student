import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '课程编码（唯一）',
    align:"center",
    dataIndex: 'courseCode'
   },
   {
    title: '课程名称（如Java程序设计）',
    align:"center",
    dataIndex: 'courseName'
   },
   {
    title: '关联campus_teacher.id（授课教师ID）',
    align:"center",
    dataIndex: 'teacherId_dictText'
   },
   {
    title: '学分（如2.0/3.5）',
    align:"center",
    dataIndex: 'credit'
   },
   {
    title: '最大选课人数（默认100人）',
    align:"center",
    dataIndex: 'maxStudent'
   },
   {
    title: '当前选课人数（初始0）',
    align:"center",
    dataIndex: 'currentStudent'
   },
   {
    title: '上课时间（格式：周一3-4节/2024-09-01|10:00-12:00）',
    align:"center",
    dataIndex: 'classTime'
   },
   {
    title: '上课地点（如图书馆302教室）',
    align:"center",
    dataIndex: 'classPlace'
   },
   {
    title: '课程描述（可选）',
    align:"center",
    dataIndex: 'courseDesc'
   },
   {
    title: '课程状态：1正常/2已结束/3未启用（参考sys_user.status）',
    align:"center",
    dataIndex: 'status_dictText'
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
    label: '课程编码（唯一）',
    field: 'courseCode',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入课程编码（唯一）!'},
          ];
     },
  },
  {
    label: '课程名称（如Java程序设计）',
    field: 'courseName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入课程名称（如Java程序设计）!'},
          ];
     },
  },
  {
    label: '关联campus_teacher.id（授课教师ID）',
    field: 'teacherId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入关联campus_teacher.id（授课教师ID）!'},
          ];
     },
  },
  {
    label: '学分（如2.0/3.5）',
    field: 'credit',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入学分（如2.0/3.5）!'},
          ];
     },
  },
  {
    label: '最大选课人数（默认100人）',
    field: 'maxStudent',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入最大选课人数（默认100人）!'},
          ];
     },
  },
  {
    label: '当前选课人数（初始0）',
    field: 'currentStudent',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入当前选课人数（初始0）!'},
          ];
     },
  },
  {
    label: '上课时间（格式：周一3-4节/2024-09-01|10:00-12:00）',
    field: 'classTime',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入上课时间（格式：周一3-4节/2024-09-01|10:00-12:00）!'},
          ];
     },
  },
  {
    label: '上课地点（如图书馆302教室）',
    field: 'classPlace',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入上课地点（如图书馆302教室）!'},
          ];
     },
  },
  {
    label: '课程描述（可选）',
    field: 'courseDesc',
    component: 'InputTextArea',
  },
  {
    label: '课程状态：1正常/2已结束/3未启用（参考sys_user.status）',
    field: 'status',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"COURSE_STATUS"
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
  courseCode: {title: '课程编码（唯一）',order: 0,view: 'text', type: 'string',},
  courseName: {title: '课程名称（如Java程序设计）',order: 1,view: 'text', type: 'string',},
  teacherId: {title: '关联campus_teacher.id（授课教师ID）',order: 2,view: 'number', type: 'number',},
  credit: {title: '学分（如2.0/3.5）',order: 3,view: 'number', type: 'number',},
  maxStudent: {title: '最大选课人数（默认100人）',order: 4,view: 'number', type: 'number',},
  currentStudent: {title: '当前选课人数（初始0）',order: 5,view: 'number', type: 'number',},
  classTime: {title: '上课时间（格式：周一3-4节/2024-09-01|10:00-12:00）',order: 6,view: 'text', type: 'string',},
  classPlace: {title: '上课地点（如图书馆302教室）',order: 7,view: 'text', type: 'string',},
  courseDesc: {title: '课程描述（可选）',order: 8,view: 'textarea', type: 'string',},
  status: {title: '课程状态：1正常/2已结束/3未启用（参考sys_user.status）',order: 9,view: 'number', type: 'number',dictCode: 'COURSE_STATUS',},
  sort: {title: '排序',order: 10,view: 'number', type: 'number',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}