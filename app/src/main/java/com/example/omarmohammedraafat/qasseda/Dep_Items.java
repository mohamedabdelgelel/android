package com.example.omarmohammedraafat.qasseda;

/**
 * Created by OmarMohammedRaafat on 11/25/2017.
 */

public class Dep_Items {


    private String text1;
    private int text2;

    public int getDep_id() {
        return dep_id;
    }

    private int dep_id = 0;



    public Dep_Items(String text1, int text2) {
        this.text1 = text1;
        this.text2 = text2;
    }

    public String getText1() {
        return text1;
    }

    public int getText2() {
        return text2;
    }

}
