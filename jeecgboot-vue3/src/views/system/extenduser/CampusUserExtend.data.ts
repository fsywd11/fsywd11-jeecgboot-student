import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '关联sys_user.id（用户主键）',
    align:"center",
    dataIndex: 'userId_dictText'
   },
   {
    title: '角色类型：1学生/2教师/3管理员（关联数据字典ROLE_TYPE）',
    align:"center",
    dataIndex: 'roleType_dictText'
   },
   {
    title: '联系电话（与sys_user.phone区分，可选AES加密）',
    align:"center",
    dataIndex: 'phone'
   },
   {
    title: '第三方登录-微信openid（对应sys_user.third_id）',
    align:"center",
    dataIndex: 'wechatOpenid'
   },
   {
    title: '第三方登录-githubopenid',
    align:"center",
    dataIndex: 'dingtalkOpenid'
   },
   {
    title: '第三方登录类型（对应sys_user.third_type）',
    align:"center",
    dataIndex: 'thirdType'
   },
   {
    title: '状态：1正常/2冻结（参考sys_user.status）',
    align:"center",
    dataIndex: 'status'
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
    label: '关联sys_user.id（用户主键）',
    field: 'userId',
    defaultValue: "2002386770540187649",
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"sys_user,username,realname,id"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入关联sys_user.id（用户主键）!'},
          ];
     },
    dynamicDisabled:true
  },
  {
    label: '角色类型：1学生/2教师/3管理员（关联数据字典ROLE_TYPE）',
    field: 'roleType',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"ROLE_TYPES"
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入角色类型：1学生/2教师/3管理员（关联数据字典ROLE_TYPE）!'},
          ];
     },
  },
  {
    label: '联系电话（与sys_user.phone区分，可选AES加密）',
    field: 'phone',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: false},
                 { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号码!'},
          ];
     },
  },
  {
    label: '第三方登录-微信openid（对应sys_user.third_id）',
    field: 'wechatOpenid',
    component: 'Input',
    dynamicDisabled:true
  },
  {
    label: '第三方登录-githubopenid',
    field: 'dingtalkOpenid',
    component: 'Input',
    dynamicDisabled:true
  },
  {
    label: '第三方登录类型（对应sys_user.third_type）',
    field: 'thirdType',
    component: 'Input',
  },
  {
    label: '状态：1正常/2冻结（参考sys_user.status）',
    field: 'status',
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
  userId: {title: '关联sys_user.id（用户主键）',order: 0,view: 'list', type: 'string',dictTable: "sys_user", dictCode: 'id', dictText: 'username,realname',},
  roleType: {title: '角色类型：1学生/2教师/3管理员（关联数据字典ROLE_TYPE）',order: 1,view: 'number', type: 'number',dictCode: 'ROLE_TYPES',},
  phone: {title: '联系电话（与sys_user.phone区分，可选AES加密）',order: 2,view: 'text', type: 'string',},
  wechatOpenid: {title: '第三方登录-微信openid（对应sys_user.third_id）',order: 3,view: 'text', type: 'string',},
  dingtalkOpenid: {title: '第三方登录-githubopenid',order: 4,view: 'text', type: 'string',},
  thirdType: {title: '第三方登录类型（对应sys_user.third_type）',order: 5,view: 'text', type: 'string',},
  status: {title: '状态：1正常/2冻结（参考sys_user.status）',order: 6,view: 'number', type: 'number',},
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