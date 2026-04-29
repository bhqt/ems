<template>
  <el-dialog
    :title="title"
    :visible.sync="open"
    width="90%"
    :close-on-click-modal="false"
    append-to-body
  >
    <el-tabs v-model="activeTab" type="border-card">
      <el-tab-pane :label="$t('autoeeModule.stockInRecord')" name="stockIn">
        <!-- 入库记录表格 -->
        <el-table
          :data="stockInData"
          border
          style="width: 100%"
          v-loading="loading.stockIn"
        >
          <el-table-column prop="id" label="入库编号" align="center" />
          <el-table-column prop="dangerGoodsId" label="危化品名称" align="center">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.a_danger_goods_info" :value="scope.row.dangerGoodsId" />
            </template>
          </el-table-column>
          <el-table-column prop="quantity" label="入库数量" align="center" />
          <el-table-column prop="supplier" label="存放地点" align="center" />
          <el-table-column prop="userId" label="操作人员" align="center">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_user" :value="scope.row.userId" />
            </template>
          </el-table-column>
          <el-table-column prop="updateTime" label="更新时间" align="center">
            <template slot-scope="scope">
              <span>{{ $common.formatDateTime(scope.row.updateTime) }}</span>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          v-show="pageInfo.stockIn.total > 0"
          :total="pageInfo.stockIn.total"
          :page.sync="pageInfo.stockIn.pageNum"
          :limit.sync="pageInfo.stockIn.pageSize"
          @pagination="loadStockInData"
        />
      </el-tab-pane>
      <el-tab-pane :label="$t('autoeeModule.stockOutRecord')" name="stockOut">
        <!-- 出库记录表格 -->
        <el-table
          :data="stockOutData"
          border
          style="width: 100%"
          v-loading="loading.stockOut"
        >
          <el-table-column prop="id" label="出库编号" align="center" />
          <el-table-column prop="dangerGoodsStockInId" label="入库编号" align="center">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.a_danger_goods_stock_in" :value="scope.row.dangerGoodsStockInId" />
            </template>
          </el-table-column>
          <el-table-column prop="dangerGoodsId" label="危化品名称" align="center">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.a_danger_goods_info" :value="scope.row.dangerGoodsId" />
            </template>
          </el-table-column>
          <el-table-column prop="quantity" label="出库数量" align="center" />
          <el-table-column prop="reason" label="出库原因" align="center" :show-overflow-tooltip="true" />
          <el-table-column prop="userId" label="操作人员" align="center">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.sys_user" :value="scope.row.userId" />
            </template>
          </el-table-column>
          <el-table-column prop="updateTime" label="更新时间" align="center">
            <template slot-scope="scope">
              <span>{{ $common.formatDateTime(scope.row.updateTime) }}</span>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          v-show="pageInfo.stockOut.total > 0"
          :total="pageInfo.stockOut.total"
          :page.sync="pageInfo.stockOut.pageNum"
          :limit.sync="pageInfo.stockOut.pageSize"
          @pagination="loadStockOutData"
        />
      </el-tab-pane>
    </el-tabs>
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancel">关闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
import moment from 'moment';
import { getToken } from "@/utils/auth";
import { parseTime } from "@/utils/index";

// 导入API
import dangerGoodsStockIn from "@/api/autoee/dangerGoodsStockIn";
import dangerGoodsStockOut from "@/api/autoee/dangerGoodsStockOut";
import dangerGoodsInfo from "@/api/autoee/dangerGoodsInfo";

export default {
  name: 'DangerGoodsInOutHistoryDialog',
  dicts: ['a_danger_goods_info', 'sys_user', 'a_danger_goods_stock_in'],
  data() {
    return {
      // 遮罩层
      open: false,
      // 标题
      title: "危化品出入库记录",
      // 当前选中的tab
      activeTab: 'stockIn',
      // 危化品ID
      dangerGoodsId: null,
      // 危化品名称
      dangerGoodsName: '',
      // 入库记录数据
      stockInData: [],
      // 出库记录数据
      stockOutData: [],
      // 加载状态
      loading: {
        stockIn: false,
        stockOut: false
      },
      // 分页信息
      pageInfo: {
        stockIn: {
          pageNum: 1,
          pageSize: 10,
          total: 0
        },
        stockOut: {
          pageNum: 1,
          pageSize: 10,
          total: 0
        }
      }
    };
  },
  methods: {
    // 打开对话框
    openDialog(row) {
      this.dangerGoodsId = row.dangerGoodsId;
      this.dangerGoodsName = this.getDangerGoodsName(row.dangerGoodsId);
      this.title = `${this.dangerGoodsName} - 危化品出入库记录`;
      this.open = true;
      this.pageInfo.stockIn.pageNum = 1;
      this.pageInfo.stockOut.pageNum = 1;
      this.activeTab = 'stockIn';
      // 默认加载入库记录
      this.loadStockInData();
    },

    // 获取危化品名称
    getDangerGoodsName(dangerGoodsId) {
      const dangerGoodsInfoDict = this.dict.type.a_danger_goods_info || [];
      const goodsItem = dangerGoodsInfoDict.find(item => item.value === dangerGoodsId);
      return goodsItem ? goodsItem.label : '';
    },

    // 加载入库记录
    loadStockInData() {
      this.loading.stockIn = true;
      const queryParams = {
        pageNum: this.pageInfo.stockIn.pageNum,
        pageSize: this.pageInfo.stockIn.pageSize,
        dangerGoodsId: this.dangerGoodsId,
        orderByColumn: 'updateTime',
        isAsc: 'descending'
      };
      dangerGoodsStockIn.selectPageListDangerGoodsStockIn(queryParams).then(response => {
        this.stockInData = response.rows;
        this.pageInfo.stockIn.total = response.total;
        this.loading.stockIn = false;
      }).catch(() => {
        this.loading.stockIn = false;
      });
    },

    // 加载出库记录
    loadStockOutData() {
      this.loading.stockOut = true;
      const queryParams = {
        pageNum: this.pageInfo.stockOut.pageNum,
        pageSize: this.pageInfo.stockOut.pageSize,
        dangerGoodsId: this.dangerGoodsId,
        orderByColumn: 'updateTime',
        isAsc: 'descending'
      };
      dangerGoodsStockOut.selectPageListDangerGoodsStockOut(queryParams).then(response => {
        this.stockOutData = response.rows;
        this.pageInfo.stockOut.total = response.total;
        this.loading.stockOut = false;
      }).catch(() => {
        this.loading.stockOut = false;
      });
    },

    // 切换tab时加载对应数据
    handleTabChange(tabName) {
      if (tabName === 'stockIn' && this.stockInData.length === 0) {
        this.loadStockInData();
      } else if (tabName === 'stockOut' && this.stockOutData.length === 0) {
        this.loadStockOutData();
      }
    },

    // 关闭对话框
    cancel() {
      this.open = false;
      this.stockInData = [];
      this.stockOutData = [];
    }
  },
  watch: {
    activeTab: {
      handler(newTab) {
        this.handleTabChange(newTab);
      },
      immediate: false
    }
  }
};
</script>

<style scoped>
.dialog-footer {
  text-align: center;
}
</style>