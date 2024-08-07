import { defHttp } from '/@/utils/http/axios';
import { useMessage } from '/@/hooks/web/useMessage';

const { createConfirm } = useMessage();

enum Api {
  list = '/tiktok_user_info/list',
  queryById = '/tiktok_user_info/queryById',
  save = '/test/testNote/add',
  edit = '/test/testNote/edit',
  deleteOne = '/test/testNote/delete',
  deleteBatch = '/test/testNote/deleteBatch',
  importExcel = '/test/testNote/importExcel',
  exportXls = '/tiktok_user_info/exportXls',
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
  defHttp.get({ url: Api.list, params }).then((resp) => {
    resp.records.forEach((element) => {
      if (element.bioLink) {
        element.bioLink = extractLinkFromJSONString(element.bioLink);
      }
    });
    return resp;
  });
function extractLinkFromJSONString(jsonString) {
  // 使用正则表达式匹配"link"键及其对应的值
  // 注意：这个正则表达式假设JSON格式是正确的，并且"link"键只出现一次
  const regex = /'link':\s*'([^"]*)'/;
  const match = jsonString.match(regex);
  // 如果找到匹配项，返回第二个捕获组（即链接的值）
  if (match && match.length > 1) {
    return match[1];
  }
  // 如果没有找到匹配项，返回null或其他适当的值
  return null;
}

export const queryById = (params) =>
  defHttp.get({ url: Api.queryById, params }).then((resp) => {
    console.log('🚀 ~ defHttp.get ~ params:', params);
    console.log('🚀 ~ defHttp.get ~ resp:', resp);

    return resp;
  });
/**
 * 删除单个
 */
export const deleteOne = (params, handleSuccess) => {
  return defHttp.delete({ url: Api.deleteOne, params }, { joinParamsToUrl: true }).then(() => {
    handleSuccess();
  });
};
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
      return defHttp.delete({ url: Api.deleteBatch, data: params }, { joinParamsToUrl: true }).then(() => {
        handleSuccess();
      });
    },
  });
};
/**
 * 保存或者更新
 * @param params
 */
export const saveOrUpdate = (params, isUpdate) => {
  let url = isUpdate ? Api.edit : Api.save;
  return defHttp.post({ url: url, params });
};
