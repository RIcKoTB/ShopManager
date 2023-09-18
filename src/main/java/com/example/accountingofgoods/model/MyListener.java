package com.example.accountingofgoods.model;

import com.example.accountingofgoods.da.entity.Goods;

import java.io.IOException;

public interface MyListener {
    public void onClickListener(Goods goods) throws IOException;
}
