import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '选课id',
    align:"center",
    dataIndex: 'selectId'
   },
   {
    title: '学生id',
    align:"center",
    dataIndex: 'studentId'
   },
   {
    title: '课程id',
    align:"center",
    dataIndex: 'courseId'
   },
   {
    title: '学期编码',
    align:"center",
    dataIndex: 'termCode'
   },
   {
    title: '教师id',
    align:"center",
    dataIndex: 'teacherId'
   },
   {
    title: '平时成绩',
    align:"center",
    dataIndex: 'usualScore'
   },
   {
    title: '平时成绩占比',
    align:"center",
    dataIndex: 'usualScorePersent'
   },
   {
    title: '期中成绩',
    align:"center",
    dataIndex: 'midScore'
   },
   {
    title: '期中成绩占比',
    align:"center",
    dataIndex: 'midScorePersent'
   },
   {
    title: '期末成绩',
    align:"center",
    dataIndex: 'finalScore'
   },
   {
    title: '期末成绩占比',
    align:"center",
    dataIndex: 'finalScorePersent'
   },
   {
    title: '综合成绩',
    align:"center",
    dataIndex: 'totalScore'
   },
   {
    title: '绩点',
    align:"center",
    dataIndex: 'gradePoint'
   },
   {
    title: '成绩状态',
    align:"center",
    dataIndex: 'scoreStatus_dictText'
   },
   {
    title: '备注',
    align:"center",
    dataIndex: 'remark'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '选课id',
    field: 'selectId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入选课id!'},
          ];
     },
  },
  {
    label: '学生id',
    field: 'studentId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入学生id!'},
          ];
     },
  },
  {
    label: '课程id',
    field: 'courseId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入课程id!'},
          ];
     },
  },
  {
    label: '学期编码',
    field: 'termCode',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入学期编码!'},
          ];
     },
  },
  {
    label: '教师id',
    field: 'teacherId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入教师id!'},
          ];
     },
  },
  {
    label: '平时成绩',
    field: 'usualScore',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入平时成绩!'},
          ];
     },
  },
  {
    label: '平时成绩占比',
    field: 'usualScorePersent',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入平时成绩占比!'},
          ];
     },
  },
  {
    label: '期中成绩',
    field: 'midScore',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入期中成绩!'},
          ];
     },
  },
  {
    label: '期中成绩占比',
    field: 'midScorePersent',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入期中成绩占比!'},
          ];
     },
  },
  {
    label: '期末成绩',
    field: 'finalScore',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入期末成绩!'},
          ];
     },
  },
  {
    label: '期末成绩占比',
    field: 'finalScorePersent',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入期末成绩占比!'},
          ];
     },
  },
  {
    label: '综合成绩',
    field: 'totalScore',
    component: 'InputNumber',
    //默认不可以填写
    componentProps: {
      disabled: true,
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: false, message: '请输入综合成绩!'},
          ];
     },
  },
  {
    label: '绩点',
    field: 'gradePoint',
    component: 'InputNumber',
    //默认不可以填写
    componentProps: {
      disabled: true,
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: false, message: '请输入绩点!'},
          ];
     },

  },
  {
    label: '成绩状态',
    field: 'scoreStatus',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"scorestatus"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入成绩状态!'},
          ];
     },
  },
  {
    label: '备注',
    field: 'remark',
    component: 'Input',
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
  selectId: {title: '选课id',order: 0,view: 'text', type: 'string',},
  studentId: {title: '学生id',order: 1,view: 'text', type: 'string',},
  courseId: {title: '课程id',order: 2,view: 'text', type: 'string',},
  termCode: {title: '学期编码',order: 3,view: 'text', type: 'string',},
  teacherId: {title: '教师id',order: 4,view: 'text', type: 'string',},
  usualScore: {title: '平时成绩',order: 5,view: 'number', type: 'number',},
  usualScorePersent: {title: '平时成绩占比',order: 6,view: 'number', type: 'number',},
  midScore: {title: '期中成绩',order: 7,view: 'number', type: 'number',},
  midScorePersent: {title: '期中成绩占比',order: 8,view: 'number', type: 'number',},
  finalScore: {title: '期末成绩',order: 9,view: 'number', type: 'number',},
  finalScorePersent: {title: '期末成绩占比',order: 10,view: 'number', type: 'number',},
  totalScore: {title: '综合成绩',order: 11,view: 'number', type: 'number',},
  gradePoint: {title: '绩点',order: 12,view: 'number', type: 'number',},
  scoreStatus: {title: '成绩状态',order: 13,view: 'list', type: 'string',dictCode: 'scorestatus',},
  remark: {title: '备注',order: 14,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
