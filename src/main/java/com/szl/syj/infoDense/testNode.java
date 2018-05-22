package com.szl.syj.infoDense;

/**
 * Created by Administrator on 2017/8/21.
 */
public class testNode {
    public static void main(String[] args){
        CharactorNode node =new CharactorNode();
        node.setCharactor("1");
        node.setNextNode(new CharactorNode("2"));
        node.getNext().setNextNode(new CharactorNode("3"));
        System.out.println("123");

    }
}
