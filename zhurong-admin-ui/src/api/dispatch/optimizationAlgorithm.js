import request from '@/utils/request'

export function solveLP(data) {
  return request({ url: '/system/dispatch/algorithm/lp', method: 'post', data: data })
}

export function solveGA(data) {
  return request({ url: '/system/dispatch/algorithm/ga', method: 'post', data: data })
}

export function solvePSO(data) {
  return request({ url: '/system/dispatch/algorithm/pso', method: 'post', data: data })
}

export function solveMILP(data) {
  return request({ url: '/system/dispatch/algorithm/milp', method: 'post', data: data })
}
