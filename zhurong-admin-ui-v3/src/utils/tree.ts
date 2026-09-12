/** 树形数据转换 */
export interface TreeNode<T = any> {
  id: string | number
  parentId?: string | number
  children?: TreeNode<T>[]
  [key: string]: any
}

/** 列表转树 */
export function listToTree<T extends TreeNode>(
  list: T[],
  options: {
    idKey?: string
    parentKey?: string
    childrenKey?: string
    rootValue?: string | number
  } = {}
): T[] {
  const { idKey = 'id', parentKey = 'parentId', childrenKey = 'children', rootValue = 0 } = options
  
  const map = new Map<string | number, T>()
  list.forEach(item => map.set(item[idKey], item))
  
  const tree: T[] = []
  
  list.forEach(item => {
    const parentId = item[parentKey]
    if (parentId === rootValue || parentId === undefined || parentId === null) {
      tree.push(item)
    } else {
      const parent = map.get(parentId)
      if (parent) {
        if (!parent[childrenKey]) parent[childrenKey] = []
        parent[childrenKey].push(item)
      } else {
        tree.push(item)
      }
    }
  })
  
  return tree
}

/** 树转列表 */
export function treeToList<T extends TreeNode>(
  tree: T[],
  options: {
    childrenKey?: string
  } = {}
): T[] {
  const { childrenKey = 'children' } = options
  const list: T[] = []
  
  function traverse(nodes: T[]) {
    nodes.forEach(node => {
      const { [childrenKey]: children, ...rest } = node
      list.push(rest as T)
      if (children && children.length > 0) {
        traverse(children)
      }
    })
  }
  
  traverse(tree)
  return list
}

/** 查找节点 */
export function findTreeNode<T extends TreeNode>(
  tree: T[],
  predicate: (node: T) => boolean,
  options: { childrenKey?: string } = {}
): T | null {
  const { childrenKey = 'children' } = options
  
  for (const node of tree) {
    if (predicate(node)) return node
    if (node[childrenKey] && node[childrenKey].length > 0) {
      const found = findTreeNode(node[childrenKey] as any[], predicate, options)
      if (found) return found
    }
  }
  return null
}

/** 查找节点路径 */
export function findTreePath<T extends TreeNode>(
  tree: T[],
  predicate: (node: T) => boolean,
  options: { childrenKey?: string } = {}
): T[] | null {
  const { childrenKey = 'children' } = options
  
  function traverse(nodes: T[], path: T[]): T[] | null {
    for (const node of nodes) {
      const newPath = [...path, node]
      if (predicate(node)) return newPath
      if (node[childrenKey] && node[childrenKey].length > 0) {
        const found = traverse(node[childrenKey] as any[], newPath)
        if (found) return found
      }
    }
    return null
  }
  
  return traverse(tree, [])
}

/** 获取所有父节点 */
export function getAncestors<T extends TreeNode>(
  tree: T[],
  targetId: string | number,
  options: { idKey?: string; childrenKey?: string } = {}
): T[] {
  const path = findTreePath(tree, node => node.id === targetId, options)
  return path ? path.slice(0, -1) : []
}

/** 获取所有子节点 */
export function getDescendants<T extends TreeNode>(
  tree: T[],
  targetId: string | number,
  options: { idKey?: string; childrenKey?: string } = {}
): T[] {
  const node = findTreePath(tree, node => node.id === targetId, options)
  if (!node) return []
  
  const target = node[node.length - 1]
  const result: T[] = []
  
  function collectChildren(node: T) {
    if (node.children && node.children.length > 0) {
      node.children.forEach((child: any) => {
        result.push(child)
        if (child.children) collectChildren(child)
      })
    }
  }
  
  collectChildren(target)
  return result
}

/** 扁平化树 (用于表格展示) */
export function flattenTree<T extends TreeNode>(
  tree: T[],
  options: { childrenKey?: string; levelKey?: string; parentKey?: string } = {}
): (T & { level: number; parentId?: string | number })[] {
  const { childrenKey = 'children', levelKey = 'level', parentKey = 'parentId' } = options
  const result: any[] = []
  
  function traverse(nodes: T[], level = 0, parentId?: string | number) {
    nodes.forEach(node => {
      const { [childrenKey]: children, ...rest } = node
      result.push({
        ...rest,
        [levelKey]: level,
        [parentKey]: parentId,
      })
      if (children && children.length > 0) {
        traverse(children as any[], level + 1, node.id)
      }
    })
  }
  
  traverse(tree)
  return result
}

/** 过滤树 */
export function filterTree<T extends TreeNode>(
  tree: T[],
  predicate: (node: T) => boolean,
  options: { childrenKey?: string } = {}
): T[] {
  const { childrenKey = 'children' } = options
  
  function filter(nodes: T[]): T[] {
    return nodes
      .filter(node => predicate(node))
      .map(node => {
        const { [childrenKey]: children, ...rest } = node
        const filteredChildren = children ? filter(children as any[]) : []
        return {
          ...rest,
          [childrenKey]: filteredChildren,
        }
      })
      .filter(node => predicate(node) || (node[childrenKey] && node[childrenKey].length > 0))
  }
  
  return filter(tree)
}

/** 排序树 */
export function sortTree<T extends TreeNode>(
  tree: T[],
  compareFn: (a: T, b: T) => number,
  options: { childrenKey?: string } = {}
): T[] {
  const { childrenKey = 'children' } = options
  
  return tree
    .map(node => {
      const { [childrenKey]: children, ...rest } = node
      const sortedChildren = children ? sortTree(children as any[], compareFn, options) : []
      return { ...rest, [childrenKey]: sortedChildren }
    })
    .sort(compareFn)
}

/** 获取树深度 */
export function getTreeDepth<T extends TreeNode>(
  tree: T[],
  options: { childrenKey?: string } = {}
): number {
  const { childrenKey = 'children' } = options
  
  if (!tree || tree.length === 0) return 0
  
  let maxDepth = 0
  
  function traverse(nodes: T[], depth: number) {
    nodes.forEach(node => {
      maxDepth = Math.max(maxDepth, depth)
      if (node[childrenKey] && (node[childrenKey] as any[]).length > 0) {
        traverse(node[childrenKey] as any[], depth + 1)
      }
    })
  }
  
  traverse(tree, 1)
  return maxDepth
}

/** 获取所有叶子节点 */
export function getLeafNodes<T extends TreeNode>(
  tree: T[],
  options: { childrenKey?: string } = {}
): T[] {
  const { childrenKey = 'children' } = options
  const leaves: T[] = []
  
  function traverse(nodes: T[]) {
    nodes.forEach(node => {
      if (!node[childrenKey] || (node[childrenKey] as any[]).length === 0) {
        leaves.push(node)
      } else {
        traverse(node[childrenKey] as any[])
      }
    })
  }
  
  traverse(tree)
  return leaves
}

/** 统计节点总数 */
export function countTreeNodes<T extends TreeNode>(
  tree: T[],
  options: { childrenKey?: string } = {}
): number {
  const { childrenKey = 'children' } = options
  let count = 0
  
  function traverse(nodes: T[]) {
    nodes.forEach(node => {
      count++
      if (node[childrenKey] && (node[childrenKey] as any[]).length > 0) {
        traverse(node[childrenKey] as any[])
      }
    })
  }
  
  traverse(tree)
  return count
}