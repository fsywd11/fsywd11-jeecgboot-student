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
    title: '工号',
    align:"center",
    dataIndex: 'teacherNo'
   },
   {
    title: '所属学院',
    align:"center",
    dataIndex: 'college_dictText'
   },
   {
    title: '教研室',
    align:"center",
    dataIndex: 'department_dictText'
   },
   {
    title: '职称',
    align:"center",
    dataIndex: 'title_dictText'
   },
   {
    title: '身份证号',
    align:"center",
    dataIndex: 'teacherIdCard'
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
    label: '工号',
    field: 'teacherNo',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入工号!'},
          ];
     },
  },
  {
    label: '所属学院',
    field: 'college',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"COLLEGE"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入所属学院!'},
          ];
     },
  },
  {
    label: '教研室',
    field: 'department',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"DEPARTMENT"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入教研室!'},
          ];
     },
  },
  {
    label: '职称',
    field: 'title',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"TITLE"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入职称!'},
          ];
     },
  },
  {
    label: '身份证号',
    field: 'teacherIdCard',
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
  teacherNo: {title: '工号',order: 1,view: 'text', type: 'string',},
  college: {title: '所属学院',order: 2,view: 'list', type: 'string',dictCode: 'COLLEGE',},
  department: {title: '教研室',order: 3,view: 'list', type: 'string',dictCode: 'DEPARTMENT',},
  title: {title: '职称',order: 4,view: 'list', type: 'string',dictCode: 'TITLE',},
  teacherIdCard: {title: '身份证号',order: 5,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}