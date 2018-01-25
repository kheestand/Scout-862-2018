package com.example.kyle.scout_862_template.BlueAllianceData.BinarySearchTree;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;

/**
 * Created by kyle on 9/8/17.
 */

public class BinarySearchTree {

    public Node root;

    public Node JSONArrayToBST(JSONArray objects, int start, int end) {
        if (start > end)
            return null;

        int mid = ((start + end) / 2);

        Node node = new Node((JSONObject) objects.get(mid));
        node.key = ((JSONObject) objects.get(mid)).get("key").toString();
        node.leftNode = JSONArrayToBST(objects, start, mid - 1);
        node.rightNode = JSONArrayToBST(objects, mid + 1, end);
        return node;
    }

    public Node FileArrayToBST(Object[] objects, int start, int end) {
        if (start > end)
            return null;

        int mid = ((start + end) / 2);
        File[] objectFileArray = (File[]) objects;
        File[] filesInFolder = objectFileArray[mid].listFiles();
        Node node = new Node(filesInFolder);
        node.key = objectFileArray[mid].getName();

        node.leftNode = FileArrayToBST(objects, start, mid - 1);
        node.rightNode = FileArrayToBST(objects, mid + 1, end);
        return node;
    }

    public Object search(String key) {
        return searchRec(root, key);
    }

    private Object searchRec(Node root, String key) {
        if (root == null)
            return null;
        if (root.key.equals(key))
            return root.data;
        int compareValue = root.key.compareTo(key);
        if (compareValue > 0)
            return searchRec(root.leftNode, key);
        if (compareValue < 0)
            return searchRec(root.rightNode, key);
        return null;
    }

    public Object search(int key) {
        return searchRec(root, key);
    }

    private Object searchRec(Node root, int key) {
        if (root == null)
            return null;
        if (Integer.parseInt(root.key) == key)
            return root.data;
        if (key < Integer.parseInt(root.key))
            return searchRec(root.leftNode, key);
        if (key > Integer.parseInt(root.key))
            return searchRec(root.rightNode, key);
        return null;
    }
}
    
