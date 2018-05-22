package com.szl.syj.utils;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/14.
 */
public class Triple<T,V,S> implements Serializable {
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

    public S getS() {
        return s;
    }

    public void setS(S s) {
        this.s = s;
    }

    private S s ;
    public Triple(T t,V v,S s){
        this.t=t;
        this.v=v;
        this.s=s;
    }
    Triple(){

    }
}