import { ref, reactive, computed, unref } from 'vue'
import type { FormInstance, FormRules, RuleObject } from 'ant-design-vue'
import type { ProFormSchema } from '@/components/pro/ProForm/types'

export interface UseFormOptions<T = any> {
  /** 初始值 */
  initialValues?: Partial<T>
  /** 校验规则 */
  rules?: FormRules<T>
  /** 表单 schema */
  schemas?: ProFormSchema<T>[]
  /** 提交函数 */
  submit?: (values: T) => Promise<any>
  /** 重置后回调 */
  onReset?: () => void
  /** 提交成功回调 */
  onSuccess?: (data: any) => void
  /** 提交失败回调 */
  onError?: (error: Error) => void
  /** 防抖延迟 */
  debounce?: number
}

export interface UseFormReturn<T = any> {
  /** 表单实例 */
  formRef: FormInstance | null
  /** 表单数据 */
  formData: T
  /** 校验规则 */
  rules: FormRules<T>
  /** Schema */
  schemas: ProFormSchema<T>[]
  /** 加载状态 */
  loading: boolean
  /** 设置表单实例 */
  setFormRef: (ref: FormInstance) => void
  /** 设置字段值 */
  setFieldValue: <K extends keyof T>(name: K, value: T[K]) => void
  /** 设置多个字段值 */
  setFieldsValue: (values: Partial<T>) => void
  /** 获取字段值 */
  getFieldValue: <K extends keyof T>(name: K) => T[K] | undefined
  /** 获取所有字段值 */
  getFieldsValue: (names?: (keyof T)[]) => Partial<T>
  /** 校验字段 */
  validateFields: (names?: (keyof T)[]) => Promise<Partial<T>>
  /** 重置字段 */
  resetFields: (names?: (keyof T)[]) => void
  /** 清除校验 */
  clearValidate: (names?: (keyof T)[]) => void
  /** 提交表单 */
  submit: () => Promise<void>
  /** 重置表单 */
  reset: () => void
  /** 校验单个字段 */
  validateField: <K extends keyof T>(name: K) => Promise<void>
  /** 滚动到字段 */
  scrollToField: (name: keyof T) => void
}

export function useForm<T = any>(options: UseFormOptions<T> = {}): UseFormReturn<T> {
  const {
    initialValues = {},
    rules = {},
    schemas = [],
    submit: submitFn,
    onReset,
    onSuccess,
    onError,
  } = options

  const formRef = ref<FormInstance | null>(null)
  const formData = reactive<T>({ ...initialValues } as T)
  const loading = ref(false)

  function setFormRef(ref: FormInstance) {
    formRef.value = ref
  }

  function setFieldValue<K extends keyof T>(name: K, value: T[K]) {
    formRef.value?.setFieldsValue({ [name]: value } as any)
    ;(formData as any)[name] = value
  }

  function setFieldsValue(values: Partial<T>) {
    formRef.value?.setFieldsValue(values as any)
    Object.assign(formData, values)
  }

  function getFieldValue<K extends keyof T>(name: K) {
    return formRef.value?.getFieldValue(name as string) as T[K] | undefined
  }

  function getFieldsValue(names?: (keyof T)[]) {
    return formRef.value?.getFieldsValue(names as string[]) as Partial<T>
  }

  async function validateFields(names?: (keyof T)[]) {
    try {
      const values = await formRef.value?.validateFields(names as string[])
      Object.assign(formData, values)
      return values as Partial<T>
    } catch (error) {
      throw error
    }
  }

  function resetFields(names?: (keyof T)[]) {
    formRef.value?.resetFields(names as string[])
    if (names) {
      names.forEach(name => {
        ;(formData as any)[name] = (initialValues as any)[name]
      })
    } else {
      Object.assign(formData, initialValues)
    }
  }

  function clearValidate(names?: (keyof T)[]) {
    formRef.value?.clearValidate(names as string[])
  }

  async function handleSubmit() {
    if (!formRef.value) return

    loading.value = true

    try {
      const values = await formRef.value.validateFields()
      Object.assign(formData, values)

      if (submitFn) {
        const result = await submitFn(values as T)
        onSuccess?.(result)
      }
    } catch (error) {
      onError?.(error as Error)
      throw error
    } finally {
      loading.value = false
    }
  }

  function reset() {
    formRef.value?.resetFields()
    Object.assign(formData, initialValues)
    onReset?.()
  }

  async function validateField<K extends keyof T>(name: K) {
    await formRef.value?.validateFields([name as string])
  }

  function scrollToField(name: keyof T) {
    formRef.value?.scrollToField(name as string)
  }

  return {
    formRef,
    formData,
    rules,
    schemas,
    loading,
    setFormRef,
    setFieldValue,
    setFieldsValue,
    getFieldValue,
    getFieldsValue,
    validateFields,
    resetFields,
    clearValidate,
    submit: handleSubmit,
    reset,
    validateField,
    scrollToField,
  }
}