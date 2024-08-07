<template>
  <div>
    <!--引用表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection">
      <!--插槽:table标题-->
      <template #tableTitle>
        <!-- <a-button type="primary" v-auth="'tiktok:creator:add'" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button> -->

        <a-dropdown-button :loading="exportLoading" @click="clickExportExcel(5000)" v-auth="'tiktok:creator:exportXls'">
          导出
          <template #overlay>
            <a-menu>
              <a-menu-item key="1" @click="clickExportExcel(1000)">1000条</a-menu-item>
              <a-menu-item key="2" @click="clickExportExcel(5000)">5000条</a-menu-item>
              <a-menu-item key="3" @click="clickExportExcel(10000)">10000条</a-menu-item>
            </a-menu>
          </template>
          <template #icon><DownOutlined /></template>
        </a-dropdown-button>
        <!-- <j-upload-button type="primary" v-auth="'tiktok:creator:importExcel'" preIcon="ant-design:import-outlined" @click="onImportXls"
          >导入</j-upload-button -->
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <template #overlay>
            <a-menu>
              <a-menu-item key="1" @click="batchHandleDelete">
                <Icon icon="ant-design:delete-outlined"></Icon>
                删除
              </a-menu-item>
            </a-menu>
          </template>
          <a-button v-auth="'test:test_note:deleteBatch'"
            >批量操作
            <Icon icon="mdi:chevron-down"></Icon>
          </a-button>
        </a-dropdown>
        <!-- 高级查询 -->
        <super-query :config="superQueryConfig" @search="handleSuperQuery" />
      </template>
      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)" />
      </template>
      <!--字段回显插槽-->
      <template v-slot:bodyCell="{ column, record, index, text }">
        <!-- 头像 -->
        <template v-if="column.dataIndex === 'avatarLarger'">
          <!-- <Avatar :size="60" :srcset="text" /> -->
          <Image :width="50" :height="50" :src="text" fallback="	http://32303333.s21i.faiusr.com/4/ABUIABAEGAAgmYaesQYonKe4ygUw5wc4sQI.png" />
        </template>
        <!-- 主页挂载 -->
        <template v-if="column.dataIndex === 'bioLink'">
          <a :href="text">{{ text }}</a>
        </template>
      </template>
    </BasicTable>
    <!-- 表单区域 -->
    <TestNoteModal @register="registerModal" @success="handleSuccess"></TestNoteModal>
  </div>
</template>

<script lang="ts" name="test-testNote" setup>
  import { ref, reactive, computed, unref } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useModal } from '/@/components/Modal';
  import { useListPage } from '/@/hooks/system/useListPage';
  import TestNoteModal from './components/TestNoteModal.vue';
  import { columns, searchFormSchema, superQuerySchema } from './TestNote.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl } from './TestNote.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import { useUserStore } from '/@/store/modules/user';
  import { Image } from 'ant-design-vue';
  import { DownOutlined } from '@ant-design/icons-vue';
  const queryParam = reactive<any>({});
  const checkedKeys = ref<Array<string | number>>([]);
  const userStore = useUserStore();
  //注册model
  const [registerModal, { openModal }] = useModal();
  //注册table数据
  const exportLoading = ref(false);
  const menuData = ref([
    {
      label: '5000条',
      key: '5000',
    },
    {
      label: '10000条',
      key: '10000',
    },
  ]);
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'TikTok达人信息列表',
      api: list,
      columns,
      canResize: false,
      formConfig: {
        //labelWidth: 120,
        schemas: searchFormSchema,
        autoSubmitOnEnter: true,
        showAdvancedButton: true,
        fieldMapToNumber: [['age', ['age_begin', 'age_end']]],
        fieldMapToTime: [],
      },
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      beforeFetch: (params) => {
        return Object.assign(params, queryParam);
      },
    },
    exportConfig: {
      name: 'TikTok达人信息列表',
      url: getExportUrl,
      params: queryParam,
    },
    importConfig: {
      url: getImportUrl,
      success: handleSuccess,
    },
  });
  const [registerTable, { reload, getPaginationRef }, { rowSelection, selectedRowKeys }] = tableContext;

  const clickExportExcel = async (limit) => {
    const pageData = getPaginationRef();
    const thisNum = pageData.current * pageData.pageSize - pageData.pageSize;
    await onExportXls(thisNum, limit);
    exportLoading.value = false;
  };
  // 高级查询配置
  const superQueryConfig = reactive(superQuerySchema);

  /**
   * 高级查询事件
   */
  function handleSuperQuery(params) {
    Object.keys(params).map((k) => {
      queryParam[k] = params[k];
    });
    reload();
  }
  /**
   * 新增事件
   */
  function handleAdd() {
    openModal(true, {
      isUpdate: false,
      showFooter: true,
    });
  }
  /**
   * 编辑事件
   */
  function handleEdit(record: Recordable) {
    openModal(true, {
      record,
      isUpdate: true,
      showFooter: true,
    });
  }
  /**
   * 详情
   */
  function handleDetail(record: Recordable) {
    openModal(true, {
      record,
      isUpdate: true,
      showFooter: false,
    });
  }
  /**
   * 删除事件
   */
  async function handleDelete(record) {
    await deleteOne({ id: record.id }, handleSuccess);
  }
  /**
   * 批量删除事件
   */
  async function batchHandleDelete() {
    await batchDelete({ ids: selectedRowKeys.value }, handleSuccess);
  }
  /**
   * 成功回调
   */
  function handleSuccess() {
    (selectedRowKeys.value = []) && reload();
  }
  /**
   * 操作栏
   */
  function getTableAction(record) {
    return [
      {
        label: '编辑',
        onClick: handleEdit.bind(null, record),
        auth: 'tiktok:creator:edit',
      },
    ];
  }
  /**
   * 下拉操作栏
   */
  function getDropDownAction(record) {
    return [
      {
        label: '详情',
        onClick: handleDetail.bind(null, record),
      },
      {
        label: '删除',
        popConfirm: {
          title: '是否确认删除',
          confirm: handleDelete.bind(null, record),
          placement: 'topLeft',
        },
        auth: 'tiktok:creator:delete',
      },
    ];
  }
</script>

<style lang="less" scoped>
  :deep(.ant-picker),
  :deep(.ant-input-number) {
    width: 100%;
  }
</style>
