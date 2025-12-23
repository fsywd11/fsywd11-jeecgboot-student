import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '关联lost_item.id（物品ID）',
    align:"center",
    dataIndex: 'itemId_dictText'
   },
   {
    title: '关联sys_user.id（认领人ID）',
    align:"center",
    dataIndex: 'claimUserId_dictText'
   },
   {
    title: '认领理由（如“物品是我的，手机尾号6789，有专属贴纸”）',
    align:"center",
    dataIndex: 'claimReason'
   },
   {
    title: '审核状态：0待审核/1通过/2拒绝（关联数据字典AUDIT_STATUS）',
    align:"center",
    dataIndex: 'auditStatus_dictText'
   },
   {
    title: '关联sys_user.id（审核人ID，管理员）',
    align:"center",
    dataIndex: 'auditUserId'
   },
   {
    title: '审核时间',
    align:"center",
    dataIndex: 'auditTime'
   },
   {
    title: '审核备注（如拒绝理由：“提供的特征与物品描述不符”）',
    align:"center",
    dataIndex: 'auditRemark'
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
    label: '关联lost_item.id（物品ID）',
    field: 'itemId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入关联lost_item.id（物品ID）!'},
          ];
     },
  },
  {
    label: '关联sys_user.id（认领人ID）',
    field: 'claimUserId',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"sys_user,realname,id"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入关联sys_user.id（认领人ID）!'},
          ];
     },
  },
  {
    label: '认领理由（如“物品是我的，手机尾号6789，有专属贴纸”）',
    field: 'claimReason',
    component: 'InputTextArea',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入认领理由（如“物品是我的，手机尾号6789，有专属贴纸”）!'},
          ];
     },
  },
  {
    label: '审核状态：0待审核/1通过/2拒绝（关联数据字典AUDIT_STATUS）',
    field: 'auditStatus',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"AUDIT_STATUS"
     },
  },
  {
    label: '关联sys_user.id（审核人ID，管理员）',
    field: 'auditUserId',
    component: 'Input',
  },
  {
    label: '审核时间',
    field: 'auditTime',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
  {
    label: '审核备注（如拒绝理由：“提供的特征与物品描述不符”）',
    field: 'auditRemark',
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
  itemId: {title: '关联lost_item.id（物品ID）',order: 0,view: 'number', type: 'number',},
  claimUserId: {title: '关联sys_user.id（认领人ID）',order: 1,view: 'list', type: 'string',dictTable: "sys_user", dictCode: 'id', dictText: 'realname',},
  claimReason: {title: '认领理由（如“物品是我的，手机尾号6789，有专属贴纸”）',order: 2,view: 'textarea', type: 'string',},
  auditStatus: {title: '审核状态：0待审核/1通过/2拒绝（关联数据字典AUDIT_STATUS）',order: 3,view: 'number', type: 'number',dictCode: 'AUDIT_STATUS',},
  auditUserId: {title: '关联sys_user.id（审核人ID，管理员）',order: 4,view: 'text', type: 'string',},
  auditTime: {title: '审核时间',order: 5,view: 'datetime', type: 'string',},
  auditRemark: {title: '审核备注（如拒绝理由：“提供的特征与物品描述不符”）',order: 6,view: 'text', type: 'string',},
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