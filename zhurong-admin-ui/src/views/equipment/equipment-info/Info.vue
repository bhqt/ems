<template>
  <div>
    <el-form label-width="100px">
      <el-form-item label="名称" prop="name">
          <el-input v-model="equipmentInfo.name" :placeholder="$t('common.pleaseInput')" :disabled="true" />
        </el-form-item>
        <el-form-item label="设备SN" prop="sn">
          <el-input v-model="equipmentInfo.sn" :placeholder="$t('common.pleaseInput')" :disabled="true" />
        </el-form-item>
        <el-form-item label="设备型号" prop="model">
          <el-input v-model="equipmentInfo.model" :placeholder="$t('common.pleaseInput')" :disabled="true" />
        </el-form-item>
        <el-form-item label="设备类型" prop="type">
          <el-select v-model="equipmentInfo.type" :placeholder="$t('common.pleaseSelect')" :disabled="true">
            <el-option
              v-for="item in dict.type.energy_type"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备描述" prop="description">
          <el-input v-model="equipmentInfo.description" :placeholder="$t('common.pleaseInput')" :disabled="true" />
        </el-form-item>
        <el-form-item label="设备图片" prop="img">
          <!-- <el-input v-model="form.img" :placeholder="$t('common.pleaseInput')" /> -->
          <imageUpload v-if="equipmentInfo.img" v-model="equipmentInfo.img" :limit="2" :values="equipmentInfo.img" :disable="true"/>
          <span v-else style="color: var(--base-color-3)">暂无内容</span>
        </el-form-item>
        <el-form-item label="设备二维码" prop="qrCode">
          <!-- <el-input v-model="equipmentInfo.qrCode" :placeholder="$t('common.pleaseInput')" :disabled="true" /> -->
          <imageUpload v-if="equipmentInfo.qrCode" v-model="equipmentInfo.qrCode" :limit="2" :values="equipmentInfo.qrCode" :disable="true"/>
          <span v-else style="color: var(--base-color-3)">暂无内容</span>
        </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  dicts: ['energy_type'],
  props: {
    equipmentInfo: {
      type: Object,
      required: true
    }
  },
  data() {
    return {

    }
  },
  methods: {

  }
}
</script>

<style scoped>

</style>
