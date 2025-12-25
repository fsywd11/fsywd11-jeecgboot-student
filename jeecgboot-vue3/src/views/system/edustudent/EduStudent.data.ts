import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '关联用户ID',
    align:"center",
    dataIndex: 'userId_dictText'
   },
   {
    title: '学号',
    align:"center",
    dataIndex: 'studentNo'
   },
   {
    title: '学院',
    align:"center",
    dataIndex: 'college_dictText'
   },
   {
    title: '专业',
    align:"center",
    dataIndex: 'major_dictText'
   },
   {
    title: '年级',
    align:"center",
    dataIndex: 'grade_dictText'
   },
   {
    title: '班级',
    align:"center",
    dataIndex: 'className'
   },
   {
    title: '身份证号',
    align:"center",
    dataIndex: 'studentIdCard'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '关联用户ID',
    field: 'userId',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"sys_user,realname,id"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入关联用户ID!'},
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
    label: '学院',
    field: 'college',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"COLLEGE"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入学院!'},
          ];
     },
  },
  {
    label: '专业',
    field: 'major',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"MAJOR"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入专业!'},
          ];
     },
  },
  {
    label: '年级',
    field: 'grade',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"GRADE"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入年级!'},
          ];
     },
  },
  {
    label: '班级',
    field: 'className',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入班级!'},
          ];
     },
  },
  {
    label: '身份证号',
    field: 'studentIdCard',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入身份证号!'},
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
  userId: {title: '关联用户ID',order: 0,view: 'list', type: 'string',dictTable: "sys_user", dictCode: 'id', dictText: 'realname',},
  studentNo: {title: '学号',order: 1,view: 'text', type: 'string',},
  college: {title: '学院',order: 2,view: 'list', type: 'string',dictCode: 'COLLEGE',},
  major: {title: '专业',order: 3,view: 'list', type: 'string',dictCode: 'MAJOR',},
  grade: {title: '年级',order: 4,view: 'list', type: 'string',dictCode: 'GRADE',},
  className: {title: '班级',order: 5,view: 'text', type: 'string',},
  studentIdCard: {title: '身份证号',order: 6,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}