package com.github.tool.example.tree;

import cn.hutool.core.bean.BeanUtil;
import com.github.tool.core.json.JsonUtils;
import com.github.tool.core.tree.TreeUtil;
import com.github.tool.core.tree.node.Tree;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class TreeUtilDemo {

    /**
     * test
     */
    public static void main(String[] args) {
        TreeUtilDemo demo = new TreeUtilDemo();
        List<OrganizationTree> trees = demo.listTree();
        System.out.println(JsonUtils.toStr(trees));
    }

    public List<OrganizationTree> listTree() {
        // 获取组织数据
        List<Organization> list = new ArrayList<>();
        Organization organization = new Organization();
        organization.setOrganizationNo("root");
        organization.setDesc("公司");
        list.add(organization);

        Organization organization1 = new Organization();
        organization1.setOrganizationNo("1");
        organization1.setParentId("root");
        organization1.setDesc("订单团队");
        list.add(organization1);

        Organization organization2 = new Organization();
        organization2.setOrganizationNo("2");
        organization2.setParentId("root");
        organization2.setDesc("仓储团队");
        list.add(organization2);

        Organization organization3 = new Organization();
        organization3.setOrganizationNo("zs");
        organization3.setParentId("1");
        organization3.setDesc("张三");
        list.add(organization3);

        List<OrganizationTree> trees = new ArrayList<>();
        covert(trees, list);
        return (List<OrganizationTree>) TreeUtil.build(trees);
    }

    /**
     * 信息转换
     */
    private void covert(List<OrganizationTree> trees, List<Organization> list) {
        list.forEach(data -> {
            OrganizationTree tree = BeanUtil.copyProperties(data, OrganizationTree.class);
            tree.setId(data.getOrganizationNo());
            trees.add(tree);
        });
    }

    /**
     * 组织信息
     */
    @Data
    static class Organization {

        private String parentId;

        private String organizationNo;

        private String desc;

    }

    /**
     * 组织树
     */
    static class OrganizationTree extends Tree<OrganizationTree> {

    }
}
