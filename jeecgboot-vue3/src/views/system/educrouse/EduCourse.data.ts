import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '课程编号',
    align:"center",
    dataIndex: 'courseNo'
   },
   {
    title: '课程名称',
    align:"center",
    dataIndex: 'courseName'
   },
   {
    title: '开课学院',
    align:"center",
    dataIndex: 'college_dictText'
   },
   {
    title: '学分',
    align:"center",
    dataIndex: 'credit'
   },
   {
    title: '课时',
    align:"center",
    dataIndex: 'classHour'
   },
   {
    title: '授课教师ID',
    align:"center",
    dataIndex: 'teacherId'
   },
   {
    title: '授课教师姓名',
    align:"center",
    dataIndex: 'teacherName'
   },
   {
    title: '上课时间',
    align:"center",
    dataIndex: 'courseTime'
   },
   {
    title: '上课教室',
    align:"center",
    dataIndex: 'classroom'
   },
   {
    title: '最大选课人数',
    align:"center",
    dataIndex: 'maxStudent'
   },
   {
    title: '当前选课人数',
    align:"center",
    dataIndex: 'currentStudent'
   },
   {
    title: '状态',
    align:"center",
    dataIndex: 'status_dictText'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '课程编号',
    field: 'courseNo',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入课程编号!'},
          ];
     },
  },
  {
    label: '课程名称',
    field: 'courseName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入课程名称!'},
          ];
     },
  },
  {
    label: '开课学院',
    field: 'college',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"COLLEGE"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入开课学院!'},
          ];
     },
  },
  {
    label: '学分',
    field: 'credit',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入学分!'},
          ];
     },
  },
  {
    label: '课时',
    field: 'classHour',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入课时!'},
          ];
     },
  },
  {
    label: '授课教师ID',
    field: 'teacherId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入授课教师ID!'},
          ];
     },
  },
  {
    label: '授课教师姓名',
    field: 'teacherName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入授课教师姓名!'},
          ];
     },
  },
  {
    label: '上课时间',
    field: 'courseTime',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入上课时间!'},
          ];
     },
  },
  {
    label: '上课教室',
    field: 'classroom',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入上课教室!'},
          ];
     },
  },
  {
    label: '最大选课人数',
    field: 'maxStudent',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入最大选课人数!'},
          ];
     },
  },
  {
    label: '当前选课人数',
    field: 'currentStudent',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入当前选课人数!'},
          ];
     },
  },
  {
    label: '状态',
    field: 'status',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"COURSE_STATUS"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入状态!'},
          ];
     },
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
  courseNo: {title: '课程编号',order: 0,view: 'text', type: 'string',},
  courseName: {title: '课程名称',order: 1,view: 'text', type: 'string',},
  college: {title: '开课学院',order: 2,view: 'list', type: 'string',dictCode: 'COLLEGE',},
  credit: {title: '学分',order: 3,view: 'text', type: 'string',},
  classHour: {title: '课时',order: 4,view: 'text', type: 'string',},
  teacherId: {title: '授课教师ID',order: 5,view: 'text', type: 'string',},
  teacherName: {title: '授课教师姓名',order: 6,view: 'text', type: 'string',},
  courseTime: {title: '上课时间',order: 7,view: 'text', type: 'string',},
  classroom: {title: '上课教室',order: 8,view: 'text', type: 'string',},
  maxStudent: {title: '最大选课人数',order: 9,view: 'text', type: 'string',},
  currentStudent: {title: '当前选课人数',order: 10,view: 'text', type: 'string',},
  status: {title: '状态',order: 11,view: 'list', type: 'string',dictCode: 'COURSE_STATUS',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}