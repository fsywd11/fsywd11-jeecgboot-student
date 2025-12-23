import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '关联campus_user_extend.id（用户扩展表主键）',
    align:"center",
    dataIndex: 'extendId_dictText'
   },
   {
    title: '工号（唯一，参考sys_user.work_no）',
    align:"center",
    dataIndex: 'teacherNo'
   },
   {
    title: '部门（如计算机学院教学办）',
    align:"center",
    dataIndex: 'dept_dictText'
   },
   {
    title: '授课科目（如Java程序设计）',
    align:"center",
    dataIndex: 'subject_dictText'
   },
   {
    title: '职称（如讲师/副教授）',
    align:"center",
    dataIndex: 'title'
   },
   {
    title: '身份证号（可选AES加密）',
    align:"center",
    dataIndex: 'idCard'
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
    label: '关联campus_user_extend.id（用户扩展表主键）',
    field: 'extendId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入关联campus_user_extend.id（用户扩展表主键）!'},
          ];
     },
  },
  {
    label: '工号（唯一，参考sys_user.work_no）',
    field: 'teacherNo',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入工号（唯一，参考sys_user.work_no）!'},
                 {...rules.duplicateCheckRule('campus_teacher', 'teacher_no',model,schema)[0]},
          ];
     },
  },
  {
    label: '部门（如计算机学院教学办）',
    field: 'dept',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"dept"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入部门（如计算机学院教学办）!'},
          ];
     },
  },
  {
    label: '授课科目（如Java程序设计）',
    field: 'subject',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"subject"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入授课科目（如Java程序设计）!'},
          ];
     },
  },
  {
    label: '职称（如讲师/副教授）',
    field: 'title',
    component: 'Input',
  },
  {
    label: '身份证号（可选AES加密）',
    field: 'idCard',
    component: 'Input',
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
  extendId: {title: '关联campus_user_extend.id（用户扩展表主键）',order: 0,view: 'number', type: 'number',},
  teacherNo: {title: '工号（唯一，参考sys_user.work_no）',order: 1,view: 'text', type: 'string',},
  dept: {title: '部门（如计算机学院教学办）',order: 2,view: 'list', type: 'string',dictCode: 'dept',},
  subject: {title: '授课科目（如Java程序设计）',order: 3,view: 'list', type: 'string',dictCode: 'subject',},
  title: {title: '职称（如讲师/副教授）',order: 4,view: 'text', type: 'string',},
  idCard: {title: '身份证号（可选AES加密）',order: 5,view: 'text', type: 'string',},
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