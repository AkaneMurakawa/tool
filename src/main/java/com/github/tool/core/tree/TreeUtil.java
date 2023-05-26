package com.github.tool.core.tree;

import com.github.tool.core.tree.node.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 树工具
 */
public class TreeUtil {

    private static final String TOP_NODE_ID = "0";

    private TreeUtil() {
    }

    /**
     * 构建树
     *
     * @param nodes 节点
     */
    public static <T> List<? extends Tree> build(List<? extends Tree<T>> nodes) {
        if (nodes == null || 0 == nodes.size()) {
            return new ArrayList<>();
        }
        List<Tree<T>> topNodes = new ArrayList<>();
        nodes.forEach(node -> {
            String pid = node.getParentId();
            if (pid == null || TOP_NODE_ID.equals(pid)) {
                topNodes.add(node);
                return;
            }
            for (Tree<T> n : nodes) {
                String id = n.getId();
                if (id != null && id.equals(pid)) {
                    if (n.getChildren() == null) {
                        n.initChildren();
                    }
                    n.getChildren().add(node);
                    node.setHasParent(true);
                    n.setHasChildren(true);
                    n.setHasParent(true);
                    return;
                }
            }
            if (topNodes.isEmpty()) {
                topNodes.add(node);
            }
        });
        return topNodes;
    }
}
