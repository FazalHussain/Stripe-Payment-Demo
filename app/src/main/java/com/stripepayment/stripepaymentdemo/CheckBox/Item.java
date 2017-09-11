package com.stripepayment.stripepaymentdemo.CheckBox;

/**
 * Created by fazal on 9/11/2017.
 */

public class Item {
    int id;
    String text;

    public Item(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
