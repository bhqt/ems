<template>
  <div class="control-container">
    <el-card>
      <template slot="header">
        <div class="card-header">
          <span>{{ $t('controlModule.title') }}</span>
        </div>
      </template>

      <el-tabs v-model="activeTab">
        <el-tab-pane :label="$t('controlModule.deviceManagement')" name="device">
          <el-row :gutter="20">
            <el-col :span="24">
              <el-input
                v-model="deviceSearch"
                :placeholder="$t('controlModule.placeholder.searchDevice')"
                prefix-icon="el-icon-search"
                style="margin-bottom: 10px;"
              />
              <el-table :data="filteredDevices" style="width: 100%">
                <el-table-column prop="deviceId" :label="$t('controlModule.deviceId')" width="120" />
                <el-table-column prop="deviceName" :label="$t('controlModule.deviceName')" />
                <el-table-column prop="deviceType" :label="$t('controlModule.deviceType')" />
                <el-table-column prop="location" :label="$t('controlModule.location')" />
                <el-table-column prop="status" :label="$t('controlModule.status')" width="100">
                  <template slot-scope="scope">
                    <el-tag :type="scope.row.status === $t('controlModule.online') ? 'success' : 'danger'">
                      {{ scope.row.status === $t('controlModule.online') ? $t('controlModule.online') : $t('controlModule.offline') }}
                    </el-tag>
                  </template>
                </el-table-column>
                <el-table-column prop="lastOnlineTime" :label="$t('controlModule.lastOnlineTime')" width="180" />
                <el-table-column :label="$t('controlModule.action')" width="120">
                  <template slot-scope="scope">
                    <el-button
                      type="primary"
                      size="small"
                      @click="controlDevice(scope.row)"
                      :disabled="scope.row.status !== $t('controlModule.online')"
                    >
                      {{ $t('controlModule.control') }}
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-col>
          </el-row>
        </el-tab-pane>

        <el-tab-pane :label="$t('controlModule.areaControl')" name="area">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-card>
                <template slot="header">
                  <span>{{ $t('controlModule.waterSourceArea') }}</span>
                </template>
                <el-button
                  type="primary"
                  @click="controlArea($t('controlModule.waterSourceArea'))"
                  :disabled="!areaControls[$t('controlModule.waterSourceArea')]"
                >
                  {{ areaControls[$t('controlModule.waterSourceArea')] ? $t('controlModule.close') : $t('controlModule.open') }}{{ $t('controlModule.areaControlSuffix') }}
                </el-button>
              </el-card>
            </el-col>
            <el-col :span="12">
              <el-card>
                <template slot="header">
                  <span>{{ $t('controlModule.middleStationArea') }}</span>
                </template>
                <el-button
                  type="primary"
                  @click="controlArea($t('controlModule.middleStationArea'))"
                  :disabled="!areaControls[$t('controlModule.middleStationArea')]"
                >
                  {{ areaControls[$t('controlModule.middleStationArea')] ? $t('controlModule.close') : $t('controlModule.open') }}{{ $t('controlModule.areaControlSuffix') }}
                </el-button>
              </el-card>
            </el-col>
            <el-col :span="12">
              <el-card>
                <template slot="header">
                  <span>{{ $t('controlModule.highPoolArea') }}</span>
                </template>
                <el-button
                  type="primary"
                  @click="controlArea($t('controlModule.highPoolArea'))"
                  :disabled="!areaControls[$t('controlModule.highPoolArea')]"
                >
                  {{ areaControls[$t('controlModule.highPoolArea')] ? $t('controlModule.close') : $t('controlModule.open') }}{{ $t('controlModule.areaControlSuffix') }}
                </el-button>
              </el-card>
            </el-col>
            <el-col :span="12">
              <el-card>
                <template slot="header">
                  <span>{{ $t('controlModule.wellArea') }}</span>
                </template>
                <el-button
                  type="primary"
                  @click="controlArea($t('controlModule.wellArea'))"
                  :disabled="!areaControls[$t('controlModule.wellArea')]"
                >
                  {{ areaControls[$t('controlModule.wellArea')] ? $t('controlModule.close') : $t('controlModule.open') }}{{ $t('controlModule.areaControlSuffix') }}
                </el-button>
              </el-card>
            </el-col>
          </el-row>
        </el-tab-pane>

        <el-tab-pane :label="$t('controlModule.remoteOperation')" name="remote">
          <el-form :model="remoteForm" label-width="100px">
            <el-form-item :label="$t('controlModule.operationType')">
              <el-select v-model="remoteForm.operationType" :placeholder="$t('controlModule.placeholder.selectOperationType')">
                <el-option :label="$t('controlModule.start')" value="start" />
                <el-option :label="$t('controlModule.stop')" value="stop" />
                <el-option :label="$t('controlModule.restart')" value="restart" />
                <el-option :label="$t('controlModule.adjust')" value="adjust" />
              </el-select>
            </el-form-item>
            <el-form-item :label="$t('controlModule.targetDevice')">
              <el-select v-model="remoteForm.deviceId" :placeholder="$t('controlModule.placeholder.selectDevice')">
                <el-option
                  v-for="device in devices"
                  :key="device.deviceId"
                  :label="device.deviceName"
                  :value="device.deviceId"
                />
              </el-select>
            </el-form-item>
            <el-form-item :label="$t('controlModule.operationParams')" v-if="remoteForm.operationType === 'adjust'">
              <el-input v-model="remoteForm.parameters" :placeholder="$t('controlModule.placeholder.inputParams')" />
            </el-form-item>
            <el-form-item :label="$t('controlModule.securityVerify')">
              <el-input
                v-model="remoteForm.securityCode"
                type="password"
                :placeholder="$t('controlModule.placeholder.inputSecurityCode')"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="executeRemoteOperation">{{ $t('controlModule.execute') }}</el-button>
              <el-button @click="resetRemoteForm">{{ $t('controlModule.reset') }}</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <el-tab-pane :label="$t('controlModule.controlLog')" name="log">
          <el-table :data="controlLogs" style="width: 100%">
            <el-table-column prop="logId" :label="$t('controlModule.logId')" width="120" />
            <el-table-column prop="operationType" :label="$t('controlModule.operationTypeCol')" />
            <el-table-column prop="target" :label="$t('controlModule.target')" />
            <el-table-column prop="operator" :label="$t('controlModule.operator')" width="120" />
            <el-table-column prop="operationTime" :label="$t('controlModule.operationTime')" width="180" />
            <el-table-column prop="result" :label="$t('controlModule.result')" width="100">
              <template slot-scope="scope">
                <el-tag :type="scope.row.result === $t('controlModule.success') ? 'success' : 'danger'">
                  {{ scope.row.result === $t('controlModule.success') ? $t('controlModule.success') : $t('controlModule.fail') }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="remark" :label="$t('controlModule.remark')" />
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-card>

    <el-dialog
      :title="$t('controlModule.deviceControl')"
      :visible.sync="deviceControlDialogVisible"
      width="500px"
    >
      <el-form :model="deviceControlForm" label-width="100px">
        <el-form-item :label="$t('controlModule.deviceNameLabel')">
          <el-input v-model="deviceControlForm.deviceName" disabled />
        </el-form-item>
        <el-form-item :label="$t('controlModule.controlCommand')">
          <el-select v-model="deviceControlForm.command" :placeholder="$t('controlModule.placeholder.selectCommand')">
            <el-option :label="$t('controlModule.start')" value="start" />
            <el-option :label="$t('controlModule.stop')" value="stop" />
            <el-option :label="$t('controlModule.restart')" value="restart" />
            <el-option :label="$t('controlModule.adjust')" value="adjust" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('controlModule.params')" v-if="deviceControlForm.command === 'adjust'">
          <el-input v-model="deviceControlForm.parameters" :placeholder="$t('controlModule.placeholder.inputParamsLabel')" />
        </el-form-item>
        <el-form-item :label="$t('controlModule.securityPwd')">
          <el-input
            v-model="deviceControlForm.securityCode"
            type="password"
            :placeholder="$t('controlModule.placeholder.inputSecurityPwd')"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="deviceControlDialogVisible = false">{{ $t('common.cancel') }}</el-button>
        <el-button type="primary" @click="submitDeviceControl">{{ $t('common.confirm') }}</el-button>
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
      this.$message.success(this.$t('controlModule.control') + this.$t('common.success'))
      this.deviceControlDialogVisible = false

      this.controlLogs.unshift({
        logId: 'LOG' + (this.controlLogs.length + 1).toString().padStart(3, '0'),
        operationType: this.getOperationTypeLabel(this.deviceControlForm.command),
        target: this.deviceControlForm.deviceName,
        operator: 'admin',
        operationTime: new Date().toLocaleString(),
        result: this.$t('controlModule.success'),
        remark: this.deviceControlForm.parameters || this.$t('controlModule.placeholder.inputParams')
      })
    },
    getOperationTypeLabel(type) {
      const map = {
        'start': this.$t('controlModule.start'),
        'stop': this.$t('controlModule.stop'),
        'restart': this.$t('controlModule.restart'),
        'adjust': this.$t('controlModule.adjust')
      }
      return map[type] || type
    },
    controlArea(area) {
      this.areaControls[area] = !this.areaControls[area]
      const status = this.areaControls[area] ? this.$t('controlModule.open') : this.$t('controlModule.close')
      this.$message.success(area + this.$t('controlModule.areaControlSuffix') + this.$t('controlModule.control') + status)
    },
    executeRemoteOperation() {
      if (!this.remoteForm.operationType || !this.remoteForm.deviceId || !this.remoteForm.securityCode) {
        this.$message.error(this.$t('controlModule.placeholder.inputSecurityCode'))
        return
      }

      this.$message.success(this.$t('controlModule.remoteOperation') + this.$t('common.success'))

      const device = this.devices.find(d => d.deviceId === this.remoteForm.deviceId)
      this.controlLogs.unshift({
        logId: 'LOG' + (this.controlLogs.length + 1).toString().padStart(3, '0'),
        operationType: this.getOperationTypeLabel(this.remoteForm.operationType),
        target: device ? device.deviceName : this.remoteForm.deviceId,
        operator: 'admin',
        operationTime: new Date().toLocaleString(),
        result: this.$t('controlModule.success'),
        remark: this.remoteForm.parameters || this.$t('controlModule.placeholder.inputParams')
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
