package com.example.kyle.scout_862_template.BlueAllianceData.BinarySearchTree;

import org.json.simple.JSONObject;

import java.io.File;

/**
 * Created by kyle on 9/8/17.
 */

public class Node {
    public Object data;
    Node leftNode;
    Node rightNode;
    String key;


    Node(JSONObject data) {
        this.data = data;
    }

    Node(File[] data) {
        this.data = data;
    }
}
