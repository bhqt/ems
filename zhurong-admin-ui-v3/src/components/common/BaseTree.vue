<template>
  <a-tree
    v-model:selected-keys="selectedKeys"
    v-model:checked-keys="checkedKeys"
    v-model:expanded-keys="expandedKeys"
    v-model:loaded-keys="loadedKeys"
    v-model:half-checked-keys="halfCheckedKeys"
    :tree-data="treeData"
    :field-names="fieldNames"
    :block-node="blockNode"
    :checkable="checkable"
    :check-strictly="checkStrictly"
    :default-expand-all="defaultExpandAll"
    :default-expanded-keys="defaultExpandedKeys"
    :default-selected-keys="defaultSelectedKeys"
    :default-checked-keys="defaultCheckedKeys"
    :auto-expand-parent="autoExpandParent"
    :draggable="draggable"
    :show-icon="showIcon"
    :show-line="showLine"
    :switcher-icon="switcherIcon"
    :load-data="loadData"
    :filter-tree-node="filterTreeNode"
    :tree-node-filter-prop="treeNodeFilterProp"
    :virtual="virtual"
    :height="height"
    :item-height="itemHeight"
    @select="handleSelect"
    @check="handleCheck"
    @expand="handleExpand"
    @load="handleLoad"
    @right-click="handleRightClick"
    @drag-start="handleDragStart"
    @drag-enter="handleDragEnter"
    @drag-over="handleDragOver"
    @drag-leave="handleDragLeave"
    @drop="handleDrop"
    @drag-end="handleDragEnd"
  >
    <template v-for="(slot, name) in $slots" #[name]="slot">
      <slot :name="name" v-bind="slotProps" />
    </template>
  </a-tree>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { TreeProps, DataNode } from 'ant-design-vue'

const props = defineProps<TreeProps & {
  /** 树数据 */
  treeData?: DataNode[]
  /** 字段名映射 */
  fieldNames?: TreeProps['fieldNames']
  /** 节点占据一行 */
  blockNode?: boolean
  /** 可选中 */
  checkable?: boolean
  /** 父子不关联 */
  checkStrictly?: boolean
  /** 默认展开所有 */
  defaultExpandAll?: boolean
  /** 默认展开键 */
  defaultExpandedKeys?: (string | number)[]
  /** 默认选中键 */
  defaultSelectedKeys?: (string | number)[]
  /** 默认勾选键 */
  defaultCheckedKeys?: (string | number)[]
  /** 自动展开父节点 */
  autoExpandParent?: boolean
  /** 可拖拽 */
  draggable?: boolean
  /** 显示图标 */
  showIcon?: boolean
  /** 显示连接线 */
  showLine?: boolean
  /** 自定义展开图标 */
  switcherIcon?: any
  /** 异步加载数据 */
  loadData?: (node: DataNode) => Promise<void>
  /** 筛选函数 */
  filterTreeNode?: (node: DataNode) => boolean
  /** 筛选字段 */
  treeNodeFilterProp?: string
  /** 虚拟滚动 */
  virtual?: boolean
  /** 高度 */
  height?: number
  /** 项高度 */
  itemHeight?: number
}>()

const emit = defineEmits<{
  'update:selectedKeys': [keys: (string | number)[]]
  'update:checkedKeys': [keys: (string | number)[] | { checked: (string | number)[]; halfChecked: (string | number)[] }]
  'update:expandedKeys': [keys: (string | number)[]]
  'update:loadedKeys': [keys: (string | number)[]]
  'update:halfCheckedKeys': [keys: (string | number)[]]
  select: [keys: (string | number)[], event: { node: DataNode; selected: boolean; nativeEvent: MouseEvent }]
  check: [keys: (string | number)[], event: { node: DataNode; checked: boolean; halfChecked: boolean }]
  expand: [keys: (string | number)[], event: { node: DataNode; expanded: boolean }]
  load: [loadedKeys: (string | number)[], event: { node: DataNode }]
  rightClick: [event: { node: DataNode; event: MouseEvent }]
  dragStart: [event: { node: DataNode; event: DragEvent }]
  dragEnter: [event: { node: DataNode; event: DragEvent }]
  dragOver: [event: { node: DataNode; event: DragEvent }]
  dragLeave: [event: { node: DataNode; event: DragEvent }]
  drop: [event: { node: DataNode; event: DragEvent }]
  dragEnd: [event: { node: DataNode; event: DragEvent }]
}>()

const slotProps = ref({})

function handleSelect(keys: (string | number)[], event: any) {
  emit('update:selectedKeys', keys)
  emit('select', keys, event)
}

function handleCheck(keys: (string | number)[], event: any) {
  emit('update:checkedKeys', keys)
  emit('check', keys, event)
}

function handleExpand(keys: (string | number)[], event: any) {
  emit('update:expandedKeys', keys)
  emit('expand', keys, event)
}

function handleLoad(loadedKeys: (string | number)[], event: any) {
  emit('update:loadedKeys', loadedKeys)
  emit('load', loadedKeys, event)
}

function handleRightClick(event: any) {
  emit('rightClick', event)
}

function handleDragStart(event: any) {
  emit('dragStart', event)
}

function handleDragEnter(event: any) {
  emit('dragEnter', event)
}

function handleDragOver(event: any) {
  emit('dragOver', event)
}

function handleDragLeave(event: any) {
  emit('dragLeave', event)
}

function handleDrop(event: any) {
  emit('drop', event)
}

function handleDragEnd(event: any) {
  emit('dragEnd', event)
}
</script>