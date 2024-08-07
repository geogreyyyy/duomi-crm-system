<template>
  <BasicModal v-bind="$attrs" @register="registerModal" destroyOnClose :title="title" :width="1000" @ok="handleSubmit">
    <Divider orientation="left" className="divider">达人基本信息</Divider>
    <BasicForm @register="userInfoRegisterForm" name="TestNoteForm" />
    <Divider orientation="left" className="divider">统计数据</Divider>
    <BasicForm @register="userStatsRegisterForm" name="TestNoteForm" />
    <Divider orientation="left" className="divider">带货数据</Divider>
    <BasicForm @register="creatorUserRegisterForm" name="TestNoteForm" />
  </BasicModal>
</template>

<script lang="ts" setup>
  import { ref, computed, unref } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { formSchema, userStatsformSchema, creatorUserformSchema } from '../TestNote.data';
  import { saveOrUpdate, queryById } from '../TestNote.api';
  // Emits声明
  const emit = defineEmits(['register', 'success']);
  const isUpdate = ref(true);
  const isDetail = ref(false);
  //表单配置
  // 用户基本信息
  const [
    userInfoRegisterForm,
    {
      setProps: userInfoSetProps,
      resetFields: userInfoResetFields,
      setFieldsValue: userInfoSetFieldsValue,
      validate: userInfoValidate,
      scrollToField: userInfoScrollToField,
    },
  ] = useForm({ schemas: formSchema, showActionButtonGroup: false, baseColProps: { span: 12 }, labelWidth: 120 });
  // 用户基本统计数据
  const [
    userStatsRegisterForm,
    {
      setProps: userStatsSetProps,
      resetFields: userStatsResetFields,
      setFieldsValue: userStatsSetFieldsValue,
      validate: userStatsValidate,
      scrollToField: userStatsScrollToField,
    },
  ] = useForm({ schemas: userStatsformSchema, showActionButtonGroup: false, baseColProps: { span: 12 }, labelWidth: 120 });
  // 带货数据
  const [
    creatorUserRegisterForm,
    {
      setProps: creatorUserSetProps,
      resetFields: creatorUserResetFields,
      setFieldsValue: creatorUserSetFieldsValue,
      validate: creatorUserValidate,
      scrollToField: creatorUserScrollToField,
    },
  ] = useForm({ schemas: creatorUserformSchema, showActionButtonGroup: false, baseColProps: { span: 12 }, labelWidth: 120 });

  //表单赋值
  const [registerModal, { setModalProps, closeModal }] = useModalInner(async (data) => {
    //重置表单
    await userInfoResetFields();
    await userStatsResetFields();
    await creatorUserResetFields();
    setModalProps({ confirmLoading: false, showCancelBtn: !!data?.showFooter, showOkBtn: !!data?.showFooter });
    isUpdate.value = !!data?.isUpdate;
    isDetail.value = !!data?.showFooter;
    if (unref(isUpdate)) {
      await queryById({ id: data.record.id }).then((resp) => {
        //表单赋值
        userInfoSetFieldsValue({
          ...resp.userInfo,
        });
        userStatsSetFieldsValue({
          ...resp.userStats,
        });
        creatorUserSetFieldsValue({
          ...resp.creatorUserInfo,
        });
      });
    }
    // 隐藏底部时禁用整个表单
    userInfoSetProps({ disabled: !data?.showFooter });
    userStatsSetProps({ disabled: !data?.showFooter });
    creatorUserSetProps({ disabled: !data?.showFooter });
  });
  //设置标题
  const title = computed(() => (!unref(isUpdate) ? '新增' : !unref(isDetail) ? '详情' : '编辑'));
  //表单提交事件
  async function handleSubmit(v) {
    try {
      let userInfoValues = await userInfoValidate();
      let userStatsValues = await userStatsValidate();
      let creatorUserValues = await creatorUserValidate();
      setModalProps({ confirmLoading: true });
      //提交表单
      await saveOrUpdate(userInfoValues, isUpdate.value);
      //关闭弹窗
      closeModal();
      //刷新列表
      emit('success');
    } catch ({ errorFields }) {
      if (errorFields) {
        const firstField = errorFields[0];
        if (firstField) {
          console.log('🚀 ~ handleSubmit ~ firstField:', firstField);
          // userInfoScrollToField(firstField.name, { behavior: 'smooth', block: 'center' });
          // userStatsScrollToField(firstField.name, { behavior: 'smooth', block: 'center' });
          // creatorUserScrollToField(firstField.name, { behavior: 'smooth', block: 'center' });
        }
      }
      return Promise.reject(errorFields);
    } finally {
      setModalProps({ confirmLoading: false });
    }
  }
</script>

<style lang="less" scoped>
  /** 时间和数字输入框样式 */
  :deep(.ant-input-number) {
    width: 100%;
  }

  :deep(.ant-calendar-picker) {
    width: 100%;
  }
  .jeecg-basic-form {
    margin-top: 2%;
  }
  .divider {
    margin: 5%;
    font-size: 18px;
    font-weight: 800;
  }
</style>
