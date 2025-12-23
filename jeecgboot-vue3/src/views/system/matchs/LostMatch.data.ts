import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '关联lost_item.id（当前物品ID）',
    align:"center",
    dataIndex: 'itemId_dictText'
   },
   {
    title: '关联lost_item.id（匹配的物品ID）',
    align:"center",
    dataIndex: 'matchItemId_dictText'
   },
   {
    title: '匹配分数（0-100分，分数越高匹配度越高）',
    align:"center",
    dataIndex: 'matchScore'
   },
   {
    title: '匹配时间',
    align:"center",
    dataIndex: 'matchTime'
   },
   {
    title: '是否通知：0未通知/1已通知（是否向发布人推送匹配提醒）',
    align:"center",
    dataIndex: 'isNotify'
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
    label: '关联lost_item.id（当前物品ID）',
    field: 'itemId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入关联lost_item.id（当前物品ID）!'},
          ];
     },
  },
  {
    label: '关联lost_item.id（匹配的物品ID）',
    field: 'matchItemId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入关联lost_item.id（匹配的物品ID）!'},
          ];
     },
  },
  {
    label: '匹配分数（0-100分，分数越高匹配度越高）',
    field: 'matchScore',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入匹配分数（0-100分，分数越高匹配度越高）!'},
          ];
     },
  },
  {
    label: '匹配时间',
    field: 'matchTime',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入匹配时间!'},
          ];
     },
  },
  {
    label: '是否通知：0未通知/1已通知（是否向发布人推送匹配提醒）',
    field: 'isNotify',
    component: 'InputNumber',
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
  itemId: {title: '关联lost_item.id（当前物品ID）',order: 0,view: 'number', type: 'number',},
  matchItemId: {title: '关联lost_item.id（匹配的物品ID）',order: 1,view: 'number', type: 'number',},
  matchScore: {title: '匹配分数（0-100分，分数越高匹配度越高）',order: 2,view: 'number', type: 'number',},
  matchTime: {title: '匹配时间',order: 3,view: 'datetime', type: 'string',},
  isNotify: {title: '是否通知：0未通知/1已通知（是否向发布人推送匹配提醒）',order: 4,view: 'number', type: 'number',},
  sort: {title: '排序',order: 5,view: 'number', type: 'number',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}