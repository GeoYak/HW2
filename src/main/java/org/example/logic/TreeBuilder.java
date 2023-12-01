package org.example.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeBuilder {
    public List<Tree> buildTreesFromDB(Connection databaseConnection, String tableName) {
        List<Tree> treeList = new ArrayList<>();
        try {
            String query = "SELECT id, parent_id FROM " + tableName; // Запрос для чтения данных из таблицы

            PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            Map<Integer, TreeNode> nodesMap = new HashMap<>();

            while (resultSet.next()) {
                int nodeId = resultSet.getInt("id");
                int parentId = resultSet.getInt("parent_id");

                if (nodesMap.containsKey(nodeId)){
                    throw new IllegalArgumentException("Такое Id уже существует, проверьте input.csv.");
                }

                TreeNode currentNode = nodesMap.getOrDefault(nodeId, new TreeNode(nodeId));
                nodesMap.put(nodeId, currentNode);

                if (parentId == nodeId) {
                    // Если узел является корневым узлом
                    if (!nodesMap.containsKey(parentId)) {
                        nodesMap.put(parentId, new TreeNode(parentId));
                    }
                } else {
                    TreeNode parentNode = nodesMap.getOrDefault(parentId, new TreeNode(parentId));
                    nodesMap.put(parentId, parentNode);
                    parentNode.addChild(currentNode);
                }
            }

            for (TreeNode node : nodesMap.values()) {
                if (node.isRoot()) {
                    Tree tree = new Tree(node);
                    treeList.add(tree);
                }
            }

            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return treeList;
    }
    public int getTotalLeaves(List<Tree> treeList) {
        int totalLeaves = 0;

        for (Tree tree : treeList) {
            List<TreeNode> allLeaves = tree.getAllLeaves();
            totalLeaves += allLeaves.size();
        }

        return totalLeaves;
    }
}