<template>
  <div class="control-container">
    <el-card>
      <template slot="header">
        <div class="card-header">
          <span>集控化功能</span>
        </div>
      </template>
      
      <el-tabs v-model="activeTab">
        <!-- 设备管理 -->
        <el-tab-pane label="设备管理" name="device">
          <el-row :gutter="20">
            <el-col :span="24">
              <el-input
                v-model="deviceSearch"
                placeholder="搜索设备"
                prefix-icon="el-icon-search"
                style="margin-bottom: 10px;"
              />
              <el-table :data="filteredDevices" style="width: 100%">
                <el-table-column prop="deviceId" label="设备ID" width="120" />
                <el-table-column prop="deviceName" label="设备名称" />
                <el-table-column prop="deviceType" label="设备类型" />
                <el-table-column prop="location" label="位置" />
                <el-table-column prop="status" label="状态" width="100">
                  <template slot-scope="scope">
                    <el-tag :type="scope.row.status === '在线' ? 'success' : 'danger'">
                      {{ scope.row.status }}
                    </el-tag>
                  </template>
                </el-table-column>
                <el-table-column prop="lastOnlineTime" label="最后在线时间" width="180" />
                <el-table-column label="操作" width="120">
                  <template slot-scope="scope">
                    <el-button
                      type="primary"
                      size="small"
                      @click="controlDevice(scope.row)"
                      :disabled="scope.row.status !== '在线'"
                    >
                      控制
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-col>
          </el-row>
        </el-tab-pane>
        
        <!-- 区域控制 -->
        <el-tab-pane label="区域控制" name="area">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-card>
                <template slot="header">
                  <span>水源水区域</span>
                </template>
                <el-button
                  type="primary"
                  @click="controlArea('水源水区域')"
                  :disabled="!areaControls['水源水区域']"
                >
                  {{ areaControls['水源水区域'] ? '关闭' : '开启' }}区域控制
                </el-button>
              </el-card>
            </el-col>
            <el-col :span="12">
              <el-card>
                <template slot="header">
                  <span>中间站区域</span>
                </template>
                <el-button
                  type="primary"
                  @click="controlArea('中间站区域')"
                  :disabled="!areaControls['中间站区域']"
                >
                  {{ areaControls['中间站区域'] ? '关闭' : '开启' }}区域控制
                </el-button>
              </el-card>
            </el-col>
            <el-col :span="12">
              <el-card>
                <template slot="header">
                  <span>高位水池区域</span>
                </template>
                <el-button
                  type="primary"
                  @click="controlArea('高位水池区域')"
                  :disabled="!areaControls['高位水池区域']"
                >
                  {{ areaControls['高位水池区域'] ? '关闭' : '开启' }}区域控制
                </el-button>
              </el-card>
            </el-col>
            <el-col :span="12">
              <el-card>
                <template slot="header">
                  <span>水井区域</span>
                </template>
                <el-button
                  type="primary"
                  @click="controlArea('水井区域')"
                  :disabled="!areaControls['水井区域']"
                >
                  {{ areaControls['水井区域'] ? '关闭' : '开启' }}区域控制
                </el-button>
              </el-card>
            </el-col>
          </el-row>
        </el-tab-pane>
        
        <!-- 远程操作 -->
        <el-tab-pane label="远程操作" name="remote">
          <el-form :model="remoteForm" label-width="100px">
            <el-form-item label="操作类型">
              <el-select v-model="remoteForm.operationType" placeholder="选择操作类型">
                <el-option label="启动" value="start" />
                <el-option label="停止" value="stop" />
                <el-option label="重启" value="restart" />
                <el-option label="调整参数" value="adjust" />
              </el-select>
            </el-form-item>
            <el-form-item label="目标设备">
              <el-select v-model="remoteForm.deviceId" placeholder="选择设备">
                <el-option
                  v-for="device in devices"
                  :key="device.deviceId"
                  :label="device.deviceName"
                  :value="device.deviceId"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="操作参数" v-if="remoteForm.operationType === 'adjust'">
              <el-input v-model="remoteForm.parameters" placeholder="输入操作参数" />
            </el-form-item>
            <el-form-item label="安全验证">
              <el-input
                v-model="remoteForm.securityCode"
                type="password"
                placeholder="输入安全验证码"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="executeRemoteOperation">执行远程操作</el-button>
              <el-button @click="resetRemoteForm">重置</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        
        <!-- 控制日志 -->
        <el-tab-pane label="控制日志" name="log">
          <el-table :data="controlLogs" style="width: 100%">
            <el-table-column prop="logId" label="日志ID" width="120" />
            <el-table-column prop="operationType" label="操作类型" />
            <el-table-column prop="target" label="操作目标" />
            <el-table-column prop="operator" label="操作人" width="120" />
            <el-table-column prop="operationTime" label="操作时间" width="180" />
            <el-table-column prop="result" label="操作结果" width="100">
              <template slot-scope="scope">
                <el-tag :type="scope.row.result === '成功' ? 'success' : 'danger'">
                  {{ scope.row.result }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="remark" label="备注" />
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-card>
    
    <!-- 设备控制对话框 -->
    <el-dialog
      title="设备控制"
      :visible.sync="deviceControlDialogVisible"
      width="500px"
    >
      <el-form :model="deviceControlForm" label-width="100px">
        <el-form-item label="设备名称">
          <el-input v-model="deviceControlForm.deviceName" disabled />
        </el-form-item>
        <el-form-item label="控制指令">
          <el-select v-model="deviceControlForm.command" placeholder="选择控制指令">
            <el-option label="启动" value="start" />
            <el-option label="停止" value="stop" />
            <el-option label="重启" value="restart" />
            <el-option label="调整参数" value="adjust" />
          </el-select>
        </el-form-item>
        <el-form-item label="参数" v-if="deviceControlForm.command === 'adjust'">
          <el-input v-model="deviceControlForm.parameters" placeholder="输入参数" />
        </el-form-item>
        <el-form-item label="安全密码">
          <el-input
            v-model="deviceControlForm.securityCode"
            type="password"
            placeholder="输入安全密码"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="deviceControlDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitDeviceControl">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'ControlDevice',
  data() {
    return {
      activeTab: 'device',
      deviceSearch: '',
      devices: [
        {
          deviceId: 'DEV001',
          deviceName: '水源水泵1',
          deviceType: '水泵',
          location: '水源水区域',
          status: '在线',
          lastOnlineTime: '2026-03-28 12:30:45'
        },
        {
          deviceId: 'DEV002',
          deviceName: '中间站泵2',
          deviceType: '水泵',
          location: '中间站区域',
          status: '在线',
          lastOnlineTime: '2026-03-28 12:29:30'
        },
        {
          deviceId: 'DEV003',
          deviceName: '高位水池泵3',
          deviceType: '水泵',
          location: '高位水池区域',
          status: '离线',
          lastOnlineTime: '2026-03-28 10:15:20'
        },
        {
          deviceId: 'DEV004',
          deviceName: '水井泵4',
          deviceType: '水泵',
          location: '水井区域',
          status: '在线',
          lastOnlineTime: '2026-03-28 12:31:10'
        }
      ],
      areaControls: {
        '水源水区域': true,
        '中间站区域': true,
        '高位水池区域': false,
        '水井区域': true
      },
      remoteForm: {
        operationType: '',
        deviceId: '',
        parameters: '',
        securityCode: ''
      },
      controlLogs: [
        {
          logId: 'LOG001',
          operationType: '启动',
          target: '水源水泵1',
          operator: 'admin',
          operationTime: '2026-03-28 12:00:00',
          result: '成功',
          remark: '正常启动'
        },
        {
          logId: 'LOG002',
          operationType: '停止',
          target: '中间站泵2',
          operator: 'admin',
          operationTime: '2026-03-28 11:30:00',
          result: '成功',
          remark: '维护停止'
        }
      ],
      deviceControlDialogVisible: false,
      deviceControlForm: {
        deviceName: '',
        command: '',
        parameters: '',
        securityCode: ''
      }
    }
  },
  computed: {
    filteredDevices() {
      if (!this.deviceSearch) {
        return this.devices
      }
      return this.devices.filter(device => 
        device.deviceName.includes(this.deviceSearch) ||
        device.deviceId.includes(this.deviceSearch) ||
        device.location.includes(this.deviceSearch)
      )
    }
  },
  methods: {
    controlDevice(device) {
      this.deviceControlForm.deviceName = device.deviceName
      this.deviceControlForm.command = ''
      this.deviceControlForm.parameters = ''
      this.deviceControlForm.securityCode = ''
      this.deviceControlDialogVisible = true
    },
    submitDeviceControl() {
      // 模拟控制设备
      this.$message.success('设备控制指令已发送')
      this.deviceControlDialogVisible = false
      
      // 添加控制日志
      this.controlLogs.unshift({
        logId: 'LOG' + (this.controlLogs.length + 1).toString().padStart(3, '0'),
        operationType: this.deviceControlForm.command === 'start' ? '启动' : 
                      this.deviceControlForm.command === 'stop' ? '停止' :
                      this.deviceControlForm.command === 'restart' ? '重启' : '调整参数',
        target: this.deviceControlForm.deviceName,
        operator: 'admin',
        operationTime: new Date().toLocaleString(),
        result: '成功',
        remark: this.deviceControlForm.parameters || '无参数'
      })
    },
    controlArea(area) {
      this.areaControls[area] = !this.areaControls[area]
      this.$message.success(`${area}控制已${this.areaControls[area] ? '开启' : '关闭'}`)
    },
    executeRemoteOperation() {
      if (!this.remoteForm.operationType || !this.remoteForm.deviceId || !this.remoteForm.securityCode) {
        this.$message.error('请填写完整的操作信息')
        return
      }
      
      // 模拟远程操作
      this.$message.success('远程操作已执行')
      
      // 添加控制日志
      const device = this.devices.find(d => d.deviceId === this.remoteForm.deviceId)
      this.controlLogs.unshift({
        logId: 'LOG' + (this.controlLogs.length + 1).toString().padStart(3, '0'),
        operationType: this.remoteForm.operationType === 'start' ? '启动' : 
                      this.remoteForm.operationType === 'stop' ? '停止' :
                      this.remoteForm.operationType === 'restart' ? '重启' : '调整参数',
        target: device ? device.deviceName : this.remoteForm.deviceId,
        operator: 'admin',
        operationTime: new Date().toLocaleString(),
        result: '成功',
        remark: this.remoteForm.parameters || '无参数'
      })
      
      this.resetRemoteForm()
    },
    resetRemoteForm() {
      this.remoteForm = {
        operationType: '',
        deviceId: '',
        parameters: '',
        securityCode: ''
      }
    }
  }
}
</script>

<style scoped>
.control-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.dialog-footer {
  text-align: right;
}
</style>
