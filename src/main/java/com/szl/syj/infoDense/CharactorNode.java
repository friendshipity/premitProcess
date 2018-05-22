package com.szl.syj.infoDense;

/**
 * Created by Administrator on 2017/8/21.
 */
public class CharactorNode extends BasicNode implements Cloneable {

    private String charactor = null;
    private CharactorNode nextNode = null;

    CharactorNode(String charactor){
        this.charactor = charactor;

    }
    CharactorNode(){
    }


    @Override
    public String getCharactor() {
        return charactor;
    }

    @Override
    public void setCharactor(String charactor) {
        this.charactor = charactor;
    }

    public void setNextNode(CharactorNode node) {
        nextNode = node;
    }
    public CharactorNode getNext() {
        return nextNode;
    }
    public Object clo() throws CloneNotSupportedException{
        return this.clone();
    }

}
