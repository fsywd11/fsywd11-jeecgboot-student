import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '学生ID',
    align:"center",
    dataIndex: 'studentId'
   },
   {
    title: '学号',
    align:"center",
    dataIndex: 'studentNo'
   },
   {
    title: '课程ID',
    align:"center",
    dataIndex: 'courseId'
   },
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
    label: '学生ID',
    field: 'studentId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入学生ID!'},
          ];
     },
  },
  {
    label: '学号',
    field: 'studentNo',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入学号!'},
          ];
     },
  },
  {
    label: '课程ID',
    field: 'courseId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入课程ID!'},
          ];
     },
  },
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
    label: '状态',
    field: 'status',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"SELECTSTATUS"
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
  studentId: {title: '学生ID',order: 0,view: 'text', type: 'string',},
  studentNo: {title: '学号',order: 1,view: 'text', type: 'string',},
  courseId: {title: '课程ID',order: 2,view: 'text', type: 'string',},
  courseNo: {title: '课程编号',order: 3,view: 'text', type: 'string',},
  courseName: {title: '课程名称',order: 4,view: 'text', type: 'string',},
  status: {title: '状态',order: 5,view: 'list', type: 'string',dictCode: 'SELECTSTATUS',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}