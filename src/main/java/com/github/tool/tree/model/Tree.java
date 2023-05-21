package com.github.tool.tree.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 树
 */
@Data
public class Tree<T> {

    private String id;

    private List<Tree<T>> children;

    private String parentId;

    private boolean hasParent = false;

    private boolean hasChildren = false;

    public void initChildren() {
        this.children = new ArrayList<>();
    }
}
