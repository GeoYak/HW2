package org.example.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tree {
    private TreeNode root;

    public Tree(TreeNode root) {
        this.root = root;
    }

    public TreeNode getRoot() {
        return root;
    }

    public List<TreeNode> getAllNodes() {
        List<TreeNode> allNodes = new ArrayList<>();
        findNodes(root, allNodes);
        return allNodes;
    }

    public List<TreeNode> getAllLeaves() {
        List<TreeNode> leaves = new ArrayList<>();
        findLeaves(root, leaves);
        return leaves;
    }

    private void findNodes(TreeNode node, List<TreeNode> nodeList) {
        nodeList.add(node);
        for (TreeNode child : node.getChildren()) {
            findNodes(child, nodeList);
        }
    }

    private void findLeaves(TreeNode node, List<TreeNode> leaves) {
        if (node.isLeaf()) {
            leaves.add(node);
        } else {
            for (TreeNode child : node.getChildren()) {
                findLeaves(child, leaves);
            }
        }
    }

    
    public static List<Tree> buildTreesCSV(String filename) {
        List<Tree> treeList = new ArrayList<>();
        Map<Integer, TreeNode> nodesMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int nodeId = Integer.parseInt(data[0]);
                int parentId = Integer.parseInt(data[1]);

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

        } catch (IOException e) {
            e.printStackTrace();
        }

        return treeList;
    }


    public static Tree findTreeMaxLeaves(List<Tree> treeList) {
        if (treeList.isEmpty()) {
            throw new IllegalArgumentException("Список деревьев пуст.");
        }

        Tree maxLeavesTree = null;
        int maxLeaves = 0;

        for (Tree tree : treeList) {
            int leavesCount = countLeaves(tree.getRoot());
            if (leavesCount > maxLeaves) {
                maxLeaves = leavesCount;
                maxLeavesTree = tree;
            } else if (leavesCount == maxLeaves) {
                throw new RuntimeException("Несколько деревьев имеют одинаковое максимальное количество листьев.");
            }
        }

        return maxLeavesTree;
    }

    static int countLeaves(TreeNode node) {
        if (node.isLeaf()) {
            return 1;
        }

        int leafCount = 0;
        for (TreeNode child : node.getChildren()) {
            leafCount += countLeaves(child);
        }

        return leafCount;
    }
}

