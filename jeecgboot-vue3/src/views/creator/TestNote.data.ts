import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: 'User Id',
    align: 'center',
    sorter: true,
    dataIndex: 'id',
    resizable: true,
  },
  {
    title: 'Unique Id',
    align: 'center',
    sorter: true,
    dataIndex: 'uniqueId',
    resizable: true,
  },
  {
    title: '名称',
    align: 'center',
    dataIndex: 'nickname',
    resizable: true,
  },
  {
    title: '头像',
    align: 'center',
    dataIndex: 'avatarLarger',
  },
  {
    title: '邮箱',
    align: 'center',
    dataIndex: 'signature',
    resizable: true,
  },
  {
    title: '国家',
    align: 'center',
    dataIndex: 'region',
    // customRender: ({ text }) => {
    //   text = !text ? '' : text.length > 10 ? text.substr(0, 10) : text;
    //   return text;
    // },
  },
  {
    title: '主页挂载',
    align: 'center',
    dataIndex: 'bioLink',
    resizable: true,
  },
  {
    title: '粉丝数',
    align: 'center',
    // sorter: true,
    dataIndex: 'followerCount',
  },
  {
    title: '关注数',
    align: 'center',
    // sorter: true,
    dataIndex: 'followingCount',
  },
  {
    title: '点赞数',
    align: 'center',
    // sorter: true,
    dataIndex: 'heart',
  },
  {
    title: '视频数',
    align: 'center',
    // sorter: true,
    dataIndex: 'videoCount',
  },
  {
    title: '朋友数',
    align: 'center',
    // sorter: true,
    dataIndex: 'friendCount',
  },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: 'User Id',
    field: 'id',
    component: 'Input',
    // componentProps: ({ formActionType }) => {
    //   const { setFieldsValue } = formActionType;
    //   return {
    //     setFieldsValue: setFieldsValue,
    //     code: 'tj_user_report',
    //     fieldConfig: [{ source: 'username', target: 'name' }],
    //     multi: true,
    //   };
    // },

    //colProps: {span: 6},
  },
  {
    label: 'Unique Id',
    field: 'uniqueId',
    component: 'Input',
    //colProps: {span: 6},
  },
  // {
  //   label: '性别',
  //   field: 'sex',
  //   component: 'JDictSelectTag',
  //   componentProps: {
  //     dictCode: 'sex',
  //   },
  //   //colProps: {span: 6},
  // },
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: 'User Id',
    field: 'id',
    component: 'Input',
    componentProps: {
      disabled: true,
    },
  },
  {
    label: 'Unique Id',
    field: 'uniqueId',
    component: 'Input',
    componentProps: {
      disabled: true,
    },
  },
  {
    label: '名称',
    field: 'nickname',
    component: 'Input',
    componentProps: {
      disabled: true,
    },
  },
  {
    label: '头像',
    field: 'avatarLarger',
    component: 'JImageUpload',
    componentProps: {
      disabled: true,
    },
  },
];

// 高级查询数据
export const superQuerySchema = {
  // name: {
  //   title: '用户名',
  //   order: 0,
  //   view: 'popup',
  //   type: 'string',
  //   code: 'tj_user_report',
  //   orgFields: 'username',
  //   destFields: 'name',
  //   popupMulti: false,
  // },
  // age: { title: '年龄', order: 1, view: 'number', type: 'number' },
  // sex: { title: '性别', order: 2, view: 'list', type: 'string', dictCode: 'sex' },
  // birthday: { title: '生日', order: 3, view: 'date', type: 'string' },
  // contents: { title: '请假原因', order: 4, view: 'umeditor', type: 'string' },
  // sheng: { title: '地区', order: 5, view: 'pca', type: 'string' },
  // year: { title: '年', order: 6, view: 'date', type: 'string' },
  // month: { title: '月', order: 7, view: 'date', type: 'string' },
};

/**
 * 流程表单调用这个方法获取formSchema
 * @param param
 */
export function getBpmFormSchema(_formData): FormSchema[] {
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
