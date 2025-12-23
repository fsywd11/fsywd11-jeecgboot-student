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
    title: '学号（唯一，参考sys_user.work_no）',
    align:"center",
    dataIndex: 'studentNo'
   },
   {
    title: '学院（如计算机学院）',
    align:"center",
    dataIndex: 'college_dictText'
   },
   {
    title: '专业（如软件工程）',
    align:"center",
    dataIndex: 'major_dictText'
   },
   {
    title: '年级（如2024级）',
    align:"center",
    dataIndex: 'grade_dictText'
   },
   {
    title: '班级（如软件2401班）',
    align:"center",
    dataIndex: 'className'
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
    label: '学号（唯一，参考sys_user.work_no）',
    field: 'studentNo',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入学号（唯一，参考sys_user.work_no）!'},
                 {...rules.duplicateCheckRule('campus_student', 'student_no',model,schema)[0]},
          ];
     },
  },
  {
    label: '学院（如计算机学院）',
    field: 'college',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"college"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入学院（如计算机学院）!'},
          ];
     },
  },
  {
    label: '专业（如软件工程）',
    field: 'major',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"majors"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入专业（如软件工程）!'},
          ];
     },
  },
  {
    label: '年级（如2024级）',
    field: 'grade',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"grades"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入年级（如2024级）!'},
          ];
     },
  },
  {
    label: '班级（如软件2401班）',
    field: 'className',
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
  studentNo: {title: '学号（唯一，参考sys_user.work_no）',order: 1,view: 'text', type: 'string',},
  college: {title: '学院（如计算机学院）',order: 2,view: 'list', type: 'string',dictCode: 'college',},
  major: {title: '专业（如软件工程）',order: 3,view: 'list', type: 'string',dictCode: 'majors',},
  grade: {title: '年级（如2024级）',order: 4,view: 'list', type: 'string',dictCode: 'grades',},
  className: {title: '班级（如软件2401班）',order: 5,view: 'text', type: 'string',},
  idCard: {title: '身份证号（可选AES加密）',order: 6,view: 'text', type: 'string',},
  sort: {title: '排序',order: 7,view: 'number', type: 'number',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}