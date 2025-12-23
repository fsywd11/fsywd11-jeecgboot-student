import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '关联sys_user.id（发布人ID）',
    align:"center",
    dataIndex: 'userId_dictText'
   },
   {
    title: '物品类型：1证件/2电子设备/3生活用品/4其他（关联数据字典ITEM_TYPE）',
    align:"center",
    dataIndex: 'itemType_dictText'
   },
   {
    title: '物品性质：0失物/1招领',
    align:"center",
    dataIndex: 'itemType2_dictText'
   },
   {
    title: '物品名称（如黑色华为手机）',
    align:"center",
    dataIndex: 'itemName'
   },
   {
    title: '物品描述（如机身有刻字“XXX”，带蓝色手机壳）',
    align:"center",
    dataIndex: 'itemDesc'
   },
   {
    title: '丢失/捡到地点（如图书馆3楼阅览室）',
    align:"center",
    dataIndex: 'lostPlace'
   },
   {
    title: '丢失/捡到时间',
    align:"center",
    dataIndex: 'lostTime'
   },
   {
    title: '物品图片URL（多个图片用逗号分隔，MinIO/OSS存储）',
    align:"center",
    dataIndex: 'imageUrl',
    customRender:render.renderImage,
   },
   {
    title: '物品状态：0待认领/1待审核/2已认领/3已失效',
    align:"center",
    dataIndex: 'status_dictText'
   },
   {
    title: '联系电话（发布人电话，可选AES加密）',
    align:"center",
    dataIndex: 'contactPhone'
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
    label: '关联sys_user.id（发布人ID）',
    field: 'userId',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"sys_user,realname,id"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入关联sys_user.id（发布人ID）!'},
          ];
     },
    dynamicDisabled:true
  },
  {
    label: '物品类型：1证件/2电子设备/3生活用品/4其他（关联数据字典ITEM_TYPE）',
    field: 'itemType',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"ITEM_TYPES"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入物品类型：1证件/2电子设备/3生活用品/4其他（关联数据字典ITEM_TYPE）!'},
          ];
     },
  },
  {
    label: '物品性质：0失物/1招领',
    field: 'itemType2',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"ITEM_TYPE2"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入物品性质：0失物/1招领!'},
          ];
     },
  },
  {
    label: '物品名称（如黑色华为手机）',
    field: 'itemName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入物品名称（如黑色华为手机）!'},
          ];
     },
  },
  {
    label: '物品描述（如机身有刻字“XXX”，带蓝色手机壳）',
    field: 'itemDesc',
    component: 'InputTextArea',
  },
  {
    label: '丢失/捡到地点（如图书馆3楼阅览室）',
    field: 'lostPlace',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入丢失/捡到地点（如图书馆3楼阅览室）!'},
          ];
     },
  },
  {
    label: '丢失/捡到时间',
    field: 'lostTime',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入丢失/捡到时间!'},
          ];
     },
  },
  {
    label: '物品图片URL（多个图片用逗号分隔，MinIO/OSS存储）',
    field: 'imageUrl',
     component: 'JImageUpload',
     componentProps:{
        fileMax: 0
      },
  },
  {
    label: '物品状态：0待认领/1待审核/2已认领/3已失效',
    field: 'status',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"ITEM_STATUS"
     },
  },
  {
    label: '联系电话（发布人电话，可选AES加密）',
    field: 'contactPhone',
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
  userId: {title: '关联sys_user.id（发布人ID）',order: 0,view: 'list', type: 'string',dictTable: "sys_user", dictCode: 'id', dictText: 'realname',},
  itemType: {title: '物品类型：1证件/2电子设备/3生活用品/4其他（关联数据字典ITEM_TYPE）',order: 1,view: 'number', type: 'number',dictCode: 'ITEM_TYPES',},
  itemType2: {title: '物品性质：0失物/1招领',order: 2,view: 'number', type: 'number',dictCode: 'ITEM_TYPE2',},
  itemName: {title: '物品名称（如黑色华为手机）',order: 3,view: 'text', type: 'string',},
  itemDesc: {title: '物品描述（如机身有刻字“XXX”，带蓝色手机壳）',order: 4,view: 'textarea', type: 'string',},
  lostPlace: {title: '丢失/捡到地点（如图书馆3楼阅览室）',order: 5,view: 'text', type: 'string',},
  lostTime: {title: '丢失/捡到时间',order: 6,view: 'datetime', type: 'string',},
  imageUrl: {title: '物品图片URL（多个图片用逗号分隔，MinIO/OSS存储）',order: 7,view: 'image', type: 'string',},
  status: {title: '物品状态：0待认领/1待审核/2已认领/3已失效',order: 8,view: 'number', type: 'number',dictCode: 'ITEM_STATUS',},
  contactPhone: {title: '联系电话（发布人电话，可选AES加密）',order: 9,view: 'text', type: 'string',},
  sort: {title: '排序',order: 10,view: 'number', type: 'number',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}