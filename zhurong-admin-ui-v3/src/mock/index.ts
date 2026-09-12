import type { MockMethod } from 'vite-plugin-mock'

const mockMethods: MockMethod[] = [
  // 登录
  {
    url: '/api/auth/login',
    method: 'post',
    response: ({ body }) => {
      const { username, password } = body
      if (username === 'admin' && password === '123456') {
        return {
          code: 200,
          message: '登录成功',
          data: {
            accessToken: 'mock-access-token-' + Date.now(),
            refreshToken: 'mock-refresh-token-' + Date.now(),
            expiresIn: 7200,
          },
        }
      }
      return {
        code: 401,
        message: '用户名或密码错误',
      }
    },
  },

  // 刷新Token
  {
    url: '/api/auth/refresh',
    method: 'post',
    response: () => {
      return {
        code: 200,
        message: '刷新成功',
        data: {
          accessToken: 'mock-access-token-' + Date.now(),
          refreshToken: 'mock-refresh-token-' + Date.now(),
          expiresIn: 7200,
        },
      }
    },
  },

  // 获取用户信息
  {
    url: '/api/auth/info',
    method: 'get',
    response: () => {
      return {
        code: 200,
        message: '获取成功',
        data: {
          userId: 1,
          userName: 'admin',
          nickName: '系统管理员',
          email: 'admin@zhurong.com',
          phone: '13800138000',
          sex: '0',
          avatar: '',
          roles: ['SUPER_ADMIN'],
          permissions: ['*:*:*'],
          deptId: 1,
          deptName: '总公司',
          lastLoginTime: new Date().toISOString(),
          lastLoginIp: '127.0.0.1',
          loginCount: 999,
        },
      }
    },
  },

  // 登出
  {
    url: '/api/auth/logout',
    method: 'post',
    response: () => {
      return {
        code: 200,
        message: '登出成功',
      }
    },
  },

  // 菜单路由
  {
    url: '/api/auth/routes',
    method: 'get',
    response: () => {
      return {
        code: 200,
        message: '获取成功',
        data: [],
      }
    },
  },

  // 字典数据
  {
    url: '/api/system/dict/data/type/:type',
    method: 'get',
    response: ({ params }) => {
      const dictData: Record<string, any[]> = {
        sys_user_sex: [
          { dictLabel: '男', dictValue: '0', dictType: 'sys_user_sex' },
          { dictLabel: '女', dictValue: '1', dictType: 'sys_user_sex' },
          { dictLabel: '未知', dictValue: '2', dictType: 'sys_user_sex' },
        ],
        sys_show_hide: [
          { dictLabel: '显示', dictValue: '0', dictType: 'sys_show_hide' },
          { dictLabel: '隐藏', dictValue: '1', dictType: 'sys_show_hide' },
        ],
        sys_normal_disable: [
          { dictLabel: '正常', dictValue: '0', dictType: 'sys_normal_disable' },
          { dictLabel: '停用', dictValue: '1', dictType: 'sys_normal_disable' },
        ],
        sys_job_status: [
          { dictLabel: '正常', dictValue: '0', dictType: 'sys_job_status' },
          { dictLabel: '暂停', dictValue: '1', dictType: 'sys_job_status' },
        ],
      }
      return {
        code: 200,
        message: '获取成功',
        data: dictData[params.type] || [],
      }
    },
  },

  // 用户列表
  {
    url: '/api/system/user/list',
    method: 'get',
    response: ({ query }) => {
      const { pageNum = 1, pageSize = 10 } = query
      const total = 50
      const list = Array.from({ length: total }, (_, i) => ({
        userId: i + 1,
        deptId: 1,
        userName: `user${String(i + 1).padStart(3, '0')}`,
        nickName: `用户${i + 1}`,
        email: `user${i + 1}@example.com`,
        phonenumber: `138${String(i).padStart(8, '0')}`,
        sex: ['0', '1', '2'][i % 3],
        avatar: '',
        status: ['0', '1'][i % 2],
        deptName: '技术部',
        leader: '0',
        createTime: new Date(Date.now() - i * 86400000).toISOString(),
        remark: '测试用户',
      }))

      return {
        code: 200,
        message: '获取成功',
        data: {
          rows: list.slice((pageNum - 1) * pageSize, pageNum * pageSize),
          total,
        },
      }
    },
  },

  // 角色列表
  {
    url: '/api/system/role/list',
    method: 'get',
    response: ({ query }) => {
      const { pageNum = 1, pageSize = 10 } = query
      const list = [
        { roleId: 1, roleName: '超级管理员', roleKey: 'SUPER_ADMIN', roleSort: 1, dataScope: '1', status: '0', createTime: '2024-01-01' },
        { roleId: 2, roleName: '普通管理员', roleKey: 'ADMIN', roleSort: 2, dataScope: '2', status: '0', createTime: '2024-01-02' },
        { roleId: 3, roleName: '普通用户', roleKey: 'USER', roleSort: 3, dataScope: '3', status: '0', createTime: '2024-01-03' },
      ]
      return {
        code: 200,
        data: { rows: list, total: list.length },
      }
    },
  },

  // 菜单树
  {
    url: '/api/system/menu/tree',
    method: 'get',
    response: () => {
      return {
        code: 200,
        data: [],
      }
    },
  },

  // 部门树
  {
    url: '/api/system/dept/tree',
    method: 'get',
    response: () => {
      return {
        code: 200,
        data: [],
      }
    },
  },
]

export default mockMethods