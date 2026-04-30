<template>
  <div class="app-container">
    <!-- 新增或修改巡更路线对话框  去掉高度设置  style="height: 800px"-->
    <el-dialog class="ruoyi_dialog" :title="title" :visible.sync="open" width="1000px" append-to-body>
      <!-- form中el-form-item加上display: inline-block;控制一行两列、一行一列 -->
      <el-form ref="addUpdateFormRef" :model="addUpdateForm" :rules="addUpdateFormRules" label-width="150px" style="padding-right: 30px" :scroll-to-error="true">
        <el-form-item v-show='true' label="路线编号" prop="routeCode" style="display: inline-block;width: 90%;">
          <el-input v-model="addUpdateForm.routeCode" :placeholder="$t('common.pleaseInput')" maxlength="25" show-word-limit clearable/>
        </el-form-item>
        <el-form-item v-show='true' label="路线名称" prop="routeName" style="display: inline-block;width: 90%;">
          <el-input v-model="addUpdateForm.routeName" :placeholder="$t('common.pleaseInput')" maxlength="50" show-word-limit clearable/>
        </el-form-item>
        <el-form-item v-show='true' :label="$t('common.status')" prop="patrolRouteStatus" style="display: inline-block;width: 90%;">
          <el-select clearable v-model="addUpdateForm.patrolRouteStatus" :placeholder="$t('common.pleaseSelect')" style="width: 100%">
            <el-option
              v-for="dict in dict.type.patrol_route_status"
              :key="dict.value"
              :label="dict.label"
              :value="String(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <!--                  <el-form-item v-show='true' label="巡更点列表" prop="pointList" style="display: inline-block;width: 90%;">-->
        <!--                    <el-select clearable v-model="addUpdateForm.pointList" :placeholder="$t('common.pleaseSelect')" style="width: 100%">-->
        <!--                      <el-option-->
        <!--                        v-for="dict in dict.type.a_patrol_point"-->
        <!--                        :key="dict.value"-->
        <!--                        :label="dict.label"-->
        <!--                        :value="String(dict.value)"-->
        <!--                      ></el-option>-->
        <!--                    </el-select>-->
        <!--                  </el-form-item>-->
        <el-form-item v-show='true' label="巡更点列表" prop="pointList" style="display: inline-block;width: 90%;">
          <div class="point-list-container">
            <!-- 添加巡更点按钮 -->
            <el-button
              type="primary"
              size="small"
              icon="el-icon-plus"
              @click="showAddPointDialog"
              style="margin-bottom: 10px"
            >
              添加巡更点
            </el-button>

            <!-- 巡更点列表 -->
            <el-table
              :data="addUpdateForm.pointListArr"
              border
              size="small"
              style="width: 100%"
            >
              <el-table-column
                prop="pointName"
                label="巡更点名称"

              >
                <template #default="{row}">
                  {{ getPointName(row.pointId) }}
                </template>
              </el-table-column>

              <!--              <el-table-column-->
              <!--                prop="orderNum"-->
              <!--                label="排序号"-->
              <!--                width="100"-->
              <!--              >-->
              <!--                <template #default="{row, $index}">-->
              <!--                  <el-input-number-->
              <!--                    v-model="row.orderNum"-->
              <!--                    :min="1"-->
              <!--                    :max="addUpdateForm.pointListArr.length"-->
              <!--                    size="mini"-->
              <!--                    @change="handleOrderChange($index)"-->
              <!--                  />-->
              <!--                </template>-->
              <!--              </el-table-column>-->

              <el-table-column
                :label="$t('common.operation')"
                width="220"
              >
                <template #default="{row, $index}">
                  <el-button
                    type="danger"
                    size="mini"
                    icon="el-icon-delete"
                    @click="removePoint($index)"
                  />
                  <el-button
                    type="primary"
                    size="mini"
                    icon="el-icon-top"
                    @click="movePointUp($index)"
                    :disabled="$index === 0"
                  />
                  <el-button
                    type="primary"
                    size="mini"
                    icon="el-icon-bottom"
                    @click="movePointDown($index)"
                    :disabled="$index === addUpdateForm.pointListArr.length - 1"
                  />
                </template>
              </el-table-column>
            </el-table>
          </div>

          <!-- 添加巡更点对话框 -->
          <el-dialog
            title="添加巡更点"
            :visible.sync="addPointDialogVisible"
            width="50%"
            append-to-body
          >
            <el-select
              v-model="selectedPointIds"
              :placeholder="$t('common.pleaseSelect')"
              style="width: 80%"
              multiple
              filterable
              :filter-method="filterPoints"
            >
              <el-option
                v-for="dict in filteredPoints"
                :key="dict.value"
                :label="dict.label"
                :value="String(dict.value)"
                :disabled="isPointSelected(dict.value)"
              >
                <span style="float: left">{{ dict.label }}</span>
                <span v-show="false" style="float: right; color: #8492a6; font-size: 13px">{{ dict.value }}</span>
              </el-option>
            </el-select>
            <div style="margin-top: 20px">
              <el-tag
                v-for="pointId in selectedPointIds"
                :key="pointId"
                closable
                @close="removeSelectedPoint(pointId)"
                style="margin-right: 10px; margin-bottom: 10px"
              >
                {{ getPointName(pointId) }}
              </el-tag>
            </div>

            <div slot="footer" class="dialog-footer">
              <el-button @click="addPointDialogVisible = false">{{ $t('common.cancel') }}</el-button>
              <el-button type="primary" @click="confirmAddPoints">{{ $t('common.confirm') }}</el-button>
            </div>
          </el-dialog>
        </el-form-item>
        <el-form-item v-show='true' :label="$t('common.remark')" prop="remark" style="display: inline-block;width: 90%;">
          <el-input v-model="addUpdateForm.remark" type="textarea" :rows="3" :placeholder="$t('common.pleaseInput')" maxlength="300" show-word-limit/>
        </el-form-item>
        <el-form-item v-show='false' label="所属用户" prop="userId" style="display: inline-block;width: 90%;">
          <el-select clearable v-model="addUpdateForm.userId" :placeholder="$t('common.pleaseSelect')" style="width: 100%">
            <el-option
              v-for="dict in dict.type.sys_user"
              :key="dict.value"
              :label="dict.label"
              :value="String(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import moment from 'moment';
import {getToken} from "@/utils/auth";
import {getDeptTreeFilterData} from "@/api/system/dept.js";
import patrolPath from "@/api/autoee/patrolPath";
import patrolPathExtend from "@/api/autoee/patrolPathExtend";

export default {
  name: 'addUpdateDialog',
  dicts: ['sys_user', 'patrol_route_status', 'a_patrol_point', 'sys_dept'],
  data() {
    return {
      datePickerOptions: {
        shortcuts: [] // 先初始化空数组,实例创建后再访问 $common 并赋值,data 函数在组件实例初始化阶段（beforeCreate 之前）执行，此时组件实例尚未完全创建，$common 这类挂载在实例上的属性还未初始化，因此无法访问
      },
      dateTimePickerOptions: {
        shortcuts: [] // 先初始化空数组,实例创建后再访问 $common 并赋值,data 函数在组件实例初始化阶段（beforeCreate 之前）执行，此时组件实例尚未完全创建，$common 这类挂载在实例上的属性还未初始化，因此无法访问
      },
      dicts: {},
      deptTreeFilterData: null,
      open: false,
      title: "",
      loading: false,
      vueAppBaseApi: process.env.VUE_APP_BASE_API,
      addOrUpdate: "",
      selectedPointId: null,
      // 初始化 pointListArr 为数组
      addUpdateForm: {
        // ...其他字段
        pointListArr: []
      },
      addPointDialogVisible: false,
      selectedPointIds: [], // 存储选中的巡更点ID数组
      pointFilterText: '', // 搜索查询文本
      allPoints: [], // 所有巡更点数据
      filteredPoints: [], // 查询后的巡更点数据
      addUpdateFormRules: {
        // 路线编号
        routeCode: [
          {required: true, message: "路线编号不能为空", trigger: "blur"},
        ],
        // 路线名称
        routeName: [
          {required: true, message: "路线名称不能为空", trigger: "blur"},
        ],
        // 状态
        patrolRouteStatus: [
          {required: true, message: "状态不能为空", trigger: "change"},
        ],
        // 巡更点列表
        pointList: [
          {required: true, message: "巡更点列表不能为空", trigger: "change"},
        ],
        // 备注
        remark: [],
        // 所属用户
        userId: [],
        // 所属部门
        deptId: [],
        // 创建者
        createBy: [],
        // 创建时间
        createTime: [],
        // 更新者
        updateBy: [],
        // 更新时间
        updateTime: [],
        // 删除标志
        delFlag: [],
        // 删除者
        delBy: [],
        // 删除时间
        delTime: []
      },
    }
  },
  created() {
    // 实例创建后再访问 $common 并赋值
    this.datePickerOptions.shortcuts = this.$common.datePickerOptionsShortcuts().map(shortcut => ({
      text: shortcut.text,
      onClick(picker) {
        const value = shortcut.value();
        picker.$emit('pick', value);
      }
    }));
    // 实例创建后再访问 $common 并赋值
    this.dateTimePickerOptions.shortcuts = this.$common.dateTimePickerOptionsShortcuts().map(shortcut => ({
      text: shortcut.text,
      onClick(picker) {
        const value = shortcut.value();
        picker.$emit('pick', value);
      }
    }));
  },
  methods: {
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.addUpdateForm = {
        id: null,
        routeCode: null,
        routeName: null,
        patrolRouteStatus: null,
        pointList: null,  // 初始化为数组
        pointListArr: [],  // 初始化为数组
        remark: null,
        userId: null,
        deptId: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null,
        delBy: null,
        delTime: null
      };
      this.resetForm("addUpdateFormRef");
    },
    /** 打开新增窗口 */
    openPatrolPathAddDialog(parentQueryParams, dicts) {
      this.dicts = dicts;
      this.reset();
      this.title = "添加巡更路线";
      this.addOrUpdate = "add";
      patrolPathExtend.openAddDialogExtend(this, parentQueryParams);
      this.open = true;
    },
    /** 打开修改窗口 */
    openPatrolPathUpdateDialog(id, dicts) {
      this.reset();
      patrolPath.selectDataByPkPatrolPath(id).then(response => {
        this.addUpdateForm = response.data;
        // 将逗号分隔的字符串转换为 pointListArr 数组
        if (this.addUpdateForm.pointList) {
          this.addUpdateForm.pointListArr = this.addUpdateForm.pointList.split(',').map((pointId, index) => ({
            pointId: pointId,
            orderNum: index + 1
          }));
        } else {
          this.addUpdateForm.pointListArr = [];
        }


        this.title = "修改巡更路线";
        this.addOrUpdate = "update";
        this.open = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      // 将 pointListArr 数组转换为逗号分隔的字符串
      if (this.addUpdateForm.pointListArr && this.addUpdateForm.pointListArr.length > 0) {
        this.addUpdateForm.pointList = this.addUpdateForm.pointListArr.map(item => item.pointId).join(',');
      } else {
        this.addUpdateForm.pointList = '';
      }
      this.$refs.addUpdateFormRef.validate(valid => {
        if (valid) {

          if (this.addUpdateForm.id != null) {
            patrolPath.updateNullValueByPatrolPath(this.addUpdateForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.$emit("addUpdateSubmitCallback");
            });
          } else {
            patrolPath.addPatrolPath(this.addUpdateForm).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.$emit("addUpdateSubmitCallback");
            });
          }
        }
      });
    },
    // 获取巡更点名称
    getPointName(pointId) {
      const point = this.dict.type.a_patrol_point.find(item => item.value === pointId);
      return point ? point.label : '未知点位';
    },



    // 删除巡更点
    removePoint(index) {
      this.addUpdateForm.pointListArr.splice(index, 1);
      // 重新排序
      this.addUpdateForm.pointListArr.forEach((item, idx) => {
        item.orderNum = idx + 1;
      });
    },

    // 上移
    movePointUp(index) {
      if (index > 0) {
        const temp = this.addUpdateForm.pointListArr[index - 1];
        this.$set(this.addUpdateForm.pointListArr, index - 1, this.addUpdateForm.pointListArr[index]);
        this.$set(this.addUpdateForm.pointListArr, index, temp);
        this.updateOrderNumbers();
      }
    },

    // 下移
    movePointDown(index) {
      if (index < this.addUpdateForm.pointListArr.length - 1) {
        const temp = this.addUpdateForm.pointListArr[index + 1];
        this.$set(this.addUpdateForm.pointListArr, index + 1, this.addUpdateForm.pointListArr[index]);
        this.$set(this.addUpdateForm.pointListArr, index, temp);
        this.updateOrderNumbers();
      }
    },

    // 排序号改变
    handleOrderChange(index) {
      this.updateOrderNumbers();
    },

    // 更新排序号
    updateOrderNumbers() {
      this.addUpdateForm.pointListArr.forEach((item, idx) => {
        item.orderNum = idx + 1;
      });
    },
    // 显示添加巡更点对话框
    showAddPointDialog() {
      this.allPoints = this.dict.type.a_patrol_point;
      console.log("this.allPoints=", JSON.stringify(this.allPoints))
      this.filteredPoints = [...this.allPoints];
      this.selectedPointIds = [];
      this.addPointDialogVisible = true;
    },

    // 查询巡更点
    filterPoints(query) {
      this.filteredPoints = this.allPoints.filter(item => {
        return item.label.toLowerCase().includes(query.toLowerCase()) ||
          item.value.toLowerCase().includes(query.toLowerCase());
      });
    },

    // 检查巡更点是否已被选择
    isPointSelected(pointId) {
      return this.addUpdateForm.pointListArr.some(item => item.pointId === pointId);
    },

    // 从已选中移除巡更点
    removeSelectedPoint(pointId) {
      this.selectedPointIds = this.selectedPointIds.filter(id => id !== pointId);
    },

    // 确认添加多个巡更点
    confirmAddPoints() {
      if (this.selectedPointIds.length === 0) {
        this.$message.warning('请至少选择一个巡更点');
        return;
      }

      // 获取选中的巡更点信息
      const newPoints = this.allPoints.filter(item => this.selectedPointIds.includes(item.value)).filter(item => !this.isPointSelected(item.value)) // 查询掉已存在的
        .map(item => ({
          pointId: item.value,
          orderNum: this.addUpdateForm.pointListArr.length + 1
        }));

      // 添加到列表中
      this.addUpdateForm.pointListArr = [
        ...this.addUpdateForm.pointListArr,
        ...newPoints
      ];

      this.addPointDialogVisible = false;
      this.selectedPointIds = [];
    },


  },
  mounted() {
    // mounted扩展方法
    patrolPathExtend.addUpdateMountedStartExtend(this);
  }
}
</script>

<style scoped>
.point-list-container {
  border: 1px solid #ebeef5;
  padding: 10px;
  border-radius: 4px;
}

.el-table {
  margin-top: 10px;
}

.el-button + .el-button {
  margin-left: 5px;
}

/* 多选下拉框样式 */
/deep/ .el-select-dropdown__item {
  display: flex;
  justify-content: space-between;
}

/deep/ .el-select-dropdown__item.is-disabled {
  color: #c0c4cc;
  cursor: not-allowed;
}
</style>
