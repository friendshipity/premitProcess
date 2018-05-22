package com.szl.syj.utils;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/11.
 */
public class Pair<T,V> implements Serializable{
    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public V getV() {
        return v;
    }

    public void setV(V v) {
        this.v = v;
    }

    private T t ;
    private V v ;
    public Pair(T t,V v){
        this.t=t;
        this.v=v;
    }
    Pair(){

    }
}
