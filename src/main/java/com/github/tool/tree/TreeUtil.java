package com.github.tool.tree;

import com.github.tool.list.BeanUtils;
import com.github.tool.tree.model.Tree;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 树工具
 */
public class TreeUtil {

    @Getter
    static class Organization {

        private String organizationNo;
    }

    static class OrganizationTree extends Tree<Organization> {

    }

    /**
     * test
     */
    public List<? extends Tree<?>> listTree() {
        // 获取组织数据
        List<Organization> list = new ArrayList<>();
        List<OrganizationTree> trees = new ArrayList<>();
        buildTrees(trees, list);
        TreeUtil.build(trees);
        return trees;
    }

    private void buildTrees(List<OrganizationTree> trees, List<Organization> list) {
        list.forEach(data -> {
            OrganizationTree tree = BeanUtils.copyPropIgnoreNull(data, OrganizationTree.class);
            tree.setId(data.getOrganizationNo());
            trees.add(tree);
        });
    }

    private static final String TOP_NODE_ID = "0";

    private TreeUtil() {
    }
    
    /**
     * 用于构建树
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
