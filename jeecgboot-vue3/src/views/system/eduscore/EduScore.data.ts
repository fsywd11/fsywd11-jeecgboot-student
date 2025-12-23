import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '关联edu_selection.id（选课记录ID）',
    align:"center",
    dataIndex: 'selectionId_dictText'
   },
   {
    title: '分数（如85.50/60.00，NULL表示未录入）',
    align:"center",
    dataIndex: 'score'
   },
   {
    title: '成绩等级（如优秀/良好/及格/不及格）',
    align:"center",
    dataIndex: 'scoreLevel_dictText'
   },
   {
    title: '成绩录入时间',
    align:"center",
    dataIndex: 'scoreTime'
   },
   {
    title: '关联campus_teacher.id（录入教师ID）',
    align:"center",
    dataIndex: 'teacherId_dictText'
   },
   {
    title: '备注（如缺考/缓考）',
    align:"center",
    dataIndex: 'remark'
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
    label: '关联edu_selection.id（选课记录ID）',
    field: 'selectionId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入关联edu_selection.id（选课记录ID）!'},
          ];
     },
  },
  {
    label: '分数（如85.50/60.00，NULL表示未录入）',
    field: 'score',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: false},
                 { pattern: '^([1-9]\d?|0|100)$', message: '不符合校验规则!'},
          ];
     },
  },
  {
    label: '成绩等级（如优秀/良好/及格/不及格）',
    field: 'scoreLevel',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"score"
     },
  },
  {
    label: '成绩录入时间',
    field: 'scoreTime',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
  {
    label: '关联campus_teacher.id（录入教师ID）',
    field: 'teacherId',
    component: 'InputNumber',
  },
  {
    label: '备注（如缺考/缓考）',
    field: 'remark',
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
  selectionId: {title: '关联edu_selection.id（选课记录ID）',order: 0,view: 'number', type: 'number',},
  score: {title: '分数（如85.50/60.00，NULL表示未录入）',order: 1,view: 'number', type: 'number',},
  scoreLevel: {title: '成绩等级（如优秀/良好/及格/不及格）',order: 2,view: 'list', type: 'string',dictCode: 'score',},
  scoreTime: {title: '成绩录入时间',order: 3,view: 'datetime', type: 'string',},
  teacherId: {title: '关联campus_teacher.id（录入教师ID）',order: 4,view: 'number', type: 'number',},
  remark: {title: '备注（如缺考/缓考）',order: 5,view: 'text', type: 'string',},
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