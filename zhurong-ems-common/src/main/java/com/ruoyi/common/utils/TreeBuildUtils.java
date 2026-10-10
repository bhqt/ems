package com.ruoyi.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.NodeParser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 扩展 hutool TreeUtil 封装系统树构建
 *
 * @author cpems
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TreeBuildUtils extends TreeUtil {

    /**
     * 根据前端定制差异化字段
     */
    public static final TreeNodeConfig DEFAULT_CONFIG = TreeNodeConfig.DEFAULT_CONFIG.setNameKey("label");

    public static <T, K> List<Tree<K>> build(List<T> list, NodeParser<T, K> nodeParser) {
        if (CollUtil.isEmpty(list)) {
            return null;
        }
        return TreeUtil.build(list, findRootId(list, nodeParser), DEFAULT_CONFIG, nodeParser);
    }

    /**
     * 查找树根节点的父ID。
     * <p>
     * 不能直接取列表首元素的 parentId：列表顺序由 SQL 排序决定（如 update_time desc），
     * 首元素通常是叶子节点而非根节点，会导致以其父ID为根构建出一棵缺少根节点的子树，
     * 前端取 {@code data[0].children} 时得到 undefined。
     * <p>
     * 这里先解析出所有节点，收集全部ID，再取第一个"父ID不在集合内"的节点，
     * 其父ID即为真正的树根父ID；若数据异常找不到，则回退为首元素的父ID。
     */
    private static <T, K> K findRootId(List<T> list, NodeParser<T, K> nodeParser) {
        List<Tree<K>> nodes = new ArrayList<>(list.size());
        Set<K> ids = new HashSet<>(list.size());
        for (T item : list) {
            Tree<K> node = new Tree<>(DEFAULT_CONFIG);
            nodeParser.parse(item, node);
            nodes.add(node);
            ids.add(node.getId());
        }
        for (Tree<K> node : nodes) {
            K parentId = node.getParentId();
            if (parentId != null && !ids.contains(parentId)) {
                return parentId;
            }
        }
        return nodes.get(0).getParentId();
    }

}
