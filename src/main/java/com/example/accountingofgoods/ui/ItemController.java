package com.example.accountingofgoods.ui;

import com.example.accountingofgoods.Main;
//import com.example.accountingofgoods.api.DeleteBackground;
import com.example.accountingofgoods.da.entity.Goods;
import com.example.accountingofgoods.dao.tables.GoodsDAO;
import com.example.accountingofgoods.model.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;


public class ItemController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;

    @FXML
    private ImageView img;

    private Goods goods;

    @FXML
    private void click(MouseEvent mouseEvent) throws IOException {
        myListener.onClickListener(goods);
    }
    private MyListener myListener;

    GoodsDAO goodsDAO = new GoodsDAO();


    //DeleteBackground deleteBackground = new DeleteBackground();
    public void setData(Goods goods, MyListener myListener) throws IOException {
        this.goods = goods;
        this.myListener = myListener;
        nameLabel.setText(goods.getName());
        priceLable.setText(Main.CURRENCY + goods.getPrice());
        Image goodsImage = goodsDAO.getGoodsImage(goods.getId());
        //img.setImage(deleteBackground.deleteBackground(goodsImage));
        img.setImage(goodsImage);
    }
}
