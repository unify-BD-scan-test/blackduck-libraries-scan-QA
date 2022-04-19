package com.elastisys.javaapp;

public class Test {
    public int i ;
    public int j; 

    public Test(int i, int j) {
        this.i = i; 
        this.j = j;
    }
    public int HelloWorld() { 
        int s = this.j/this.i;
        return s;
    } 

}