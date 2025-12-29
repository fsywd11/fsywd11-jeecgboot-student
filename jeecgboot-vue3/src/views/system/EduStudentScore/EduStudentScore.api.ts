import {defHttp} from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

const { createConfirm } = useMessage();

enum Api {
  list = '/test02/eduStudentScore/list',
  save = '/test02/eduStudentScore/add',
  edit = '/test02/eduStudentScore/edit',
  deleteOne = '/test02/eduStudentScore/delete',
  deleteBatch = '/test02/eduStudentScore/deleteBatch',
  importExcel = '/test02/eduStudentScore/importExcel',
  exportXls = '/test02/eduStudentScore/exportXls',
  studentscorelist = '/test02/eduStudentScore/studentlist',
  teacherlist = '/test02/eduStudentScore/teacherlist',
}
/**
 * 导出api
 * @param params
 */
export const getExportUrl = Api.exportXls;
/**
 * 导入api
 */
export const getImportUrl = Api.importExcel;
/**
 * 列表接口
 * @param params
 */
export const list = (params) =>
  defHttp.get({url: Api.list, params});




/**
 * 学生列表接口
 * @param params
 */
export const studentlist = (params) =>
  defHttp.get({url: Api.studentscorelist, params});


/**
 * 教师列表接口
 * @param params
 */
export const teacherlist = (params) =>
  defHttp.get({url: Api.teacherlist, params});

/**
 * 删除单个
 */
export const deleteOne = (params,handleSuccess) => {
  return defHttp.delete({url: Api.deleteOne, params}, {joinParamsToUrl: true}).then(() => {
    handleSuccess();
  });
}
/**
 * 批量删除
 * @param params
 */
export const batchDelete = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认删除',
    content: '是否删除选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.delete({url: Api.deleteBatch, data: params}, {joinParamsToUrl: true}).then(() => {
        handleSuccess();
      });
    }
  });
}
/**
 * 保存或者更新
 * @param params
 */
export const saveOrUpdate = (params, isUpdate) => {
  let url = isUpdate ? Api.edit : Api.save;
  return defHttp.post({url: url, params});
}
