import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '发布人ID',
    align:"center",
    dataIndex: 'userId'
   },
   {
    title: '发布人姓名',
    align:"center",
    dataIndex: 'userName'
   },
   {
    title: '类型',
    align:"center",
    dataIndex: 'type_dictText'
   },
   {
    title: '标题',
    align:"center",
    dataIndex: 'title'
   },
   {
    title: '详细描述',
    align:"center",
    dataIndex: 'content'
   },
   {
    title: '物品分类',
    align:"center",
    dataIndex: 'itemCategory_dictText'
   },
   {
    title: '丢失/拾取地点',
    align:"center",
    dataIndex: 'location'
   },
   {
    title: '状态',
    align:"center",
    dataIndex: 'status_dictText'
   },
   {
    title: '认领人ID',
    align:"center",
    dataIndex: 'claimUserId'
   },
   {
    title: '认领人姓名',
    align:"center",
    dataIndex: 'claimUserName'
   },
   {
    title: '图片',
    align:"center",
    dataIndex: 'image',
    customRender:render.renderImage,
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '发布人ID',
    field: 'userId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入发布人ID!'},
          ];
     },
  },
  {
    label: '发布人姓名',
    field: 'userName',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入发布人姓名!'},
          ];
     },
  },
  {
    label: '类型',
    field: 'type',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"losttype"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入类型!'},
          ];
     },
  },
  {
    label: '标题',
    field: 'title',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入标题!'},
          ];
     },
  },
  {
    label: '详细描述',
    field: 'content',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入详细描述!'},
          ];
     },
  },
  {
    label: '物品分类',
    field: 'itemCategory',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"lostitem_category"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入物品分类!'},
          ];
     },
  },
  {
    label: '丢失/拾取地点',
    field: 'location',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入丢失/拾取地点!'},
          ];
     },
  },
  {
    label: '状态',
    field: 'status',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"loststatus"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入状态!'},
          ];
     },
  },
  {
    label: '认领人ID',
    field: 'claimUserId',
    component: 'Input',
  },
  {
    label: '认领人姓名',
    field: 'claimUserName',
    component: 'Input',
  },
  {
    label: '图片',
    field: 'image',
     component: 'JImageUpload',
     componentProps:{
        fileMax: 0
      },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入图片!'},
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
  userId: {title: '发布人ID',order: 0,view: 'text', type: 'string',},
  userName: {title: '发布人姓名',order: 1,view: 'text', type: 'string',},
  type: {title: '类型',order: 2,view: 'list', type: 'string',dictCode: 'losttype',},
  title: {title: '标题',order: 3,view: 'text', type: 'string',},
  content: {title: '详细描述',order: 4,view: 'text', type: 'string',},
  itemCategory: {title: '物品分类',order: 5,view: 'list', type: 'string',dictCode: 'lostitem_category',},
  location: {title: '丢失/拾取地点',order: 6,view: 'text', type: 'string',},
  status: {title: '状态',order: 7,view: 'list', type: 'string',dictCode: 'loststatus',},
  claimUserId: {title: '认领人ID',order: 8,view: 'text', type: 'string',},
  claimUserName: {title: '认领人姓名',order: 9,view: 'text', type: 'string',},
  image: {title: '图片',order: 10,view: 'image', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}