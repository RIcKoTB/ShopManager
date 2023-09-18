package com.example.accountingofgoods.ui;

import com.example.accountingofgoods.HelloApplication;
import com.example.accountingofgoods.Main;
//import com.example.accountingofgoods.api.DeleteBackground;
import com.example.accountingofgoods.da.entity.Goods;
import com.example.accountingofgoods.dao.tables.BasketDAO;
import com.example.accountingofgoods.dao.tables.GoodsDAO;
import com.example.accountingofgoods.dao.tables.UserDAO;
import com.example.accountingofgoods.model.MyListener;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MarketController implements Initializable {
    @FXML
    private VBox chosenFruitCard;

    @FXML
    private Label fruitNameLable;

    @FXML
    private Label fruitPriceLabel;

    @FXML
    private ImageView fruitImg;

    @FXML
    private Label lblPay;

    @FXML
    private ImageView logout;

    @FXML
    private ScrollPane scroll;
    @FXML
    private Button btnAddBasket;

    @FXML
    private Button btnBuy;


    @FXML
    private VBox basketItem;

    @FXML
    private GridPane grid;

    @FXML
    private AnchorPane basketWindow;

    private List<Goods> goodsList = new ArrayList<>();
    private Image image;

    private MyListener myListener;

    //DeleteBackground deleteBackground = new DeleteBackground();

    private void setChosenFruit(Goods goods) throws IOException {
        fruitNameLable.setText(goods.getName());
        fruitPriceLabel.setText(Main.CURRENCY + goods.getPrice());

        Image goodsImage = goodsDAO.getGoodsImage(goods.getId());
        //fruitImg.setImage(deleteBackground.deleteBackground(goodsImage));
        fruitImg.setImage(goodsImage);
        fruitImg.setFitHeight(170);
        fruitImg.setFitWidth(285);

        btnAddBasket.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(goods.getName());
                System.out.println(goods.getPrice());

                addItemBasket(goods);

                animationBasket();


            }
        });

        System.out.println(image);
    }

    @FXML
    void logout(MouseEvent event) throws IOException {
        Stage stg = (Stage) logout.getScene().getWindow();

        Parent root = FXMLLoader.load(HelloApplication.class.getResource("login-view.fxml"));
        stg.setTitle("Fruits Marker");
        stg.setScene(new Scene(root));
        stg.show();
    }

    public List<Goods> goodsListForBasket = new ArrayList<>();

    public void addItemBasket(Goods goods){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(HelloApplication.class.getResource("item-basket.fxml"));

            // Завантаження вмісту FXML та отримання кореневого вузла
            Parent fxmlRoot = fxmlLoader.load();

            // Отримання контролера для доступу до його методів та даних
            ItemBasketController itemController = fxmlLoader.getController();

            itemController.fruitNameLable.setText(goods.getName());
            itemController.fruitPriceLabel.setText(String.valueOf(goods.getPrice()) + "₴");

            itemController.deleteForBasket.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    basketItem.getChildren().remove(fxmlRoot);
                }
            });

            // Додавання кореневого вузла до VBox
            basketItem.getChildren().add(0,fxmlRoot);

            Goods goodsForList = new Goods(goods.getId());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    BasketDAO basketDAO = new BasketDAO();

    UserDAO userDAO = new UserDAO();

    @FXML
    void buyBasketGoods(ActionEvent event) {

        if(basketItem.getChildren().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Помилка!");
            alert.setHeaderText("Корзина товарів пуста.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Вітаємо!");
        alert.setHeaderText("Ви купили товар.");
        alert.showAndWait();

        basketItem.getChildren().clear();

    }

    public void animationBasket(){
        double duration = 1.5;

        Scene scene = chosenFruitCard.getScene();

        double windowWidth = scene.getWindow().getWidth() - 100;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(duration), chosenFruitCard);
        translateTransition.setToX(windowWidth - chosenFruitCard.getWidth());

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(duration), chosenFruitCard);
        scaleTransition.setToX(0.5);
        scaleTransition.setToY(0.5);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duration), chosenFruitCard);
        fadeTransition.setToValue(0.5);

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, scaleTransition, fadeTransition);

        parallelTransition.setOnFinished(event1 -> {

            chosenFruitCard.setTranslateX(0);
            chosenFruitCard.setScaleX(1);
            chosenFruitCard.setScaleY(1);
            chosenFruitCard.setOpacity(1);
        });

        parallelTransition.play();
    }


    @FXML
    void btnClose(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnCollapse(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadFruits();
    }

    GoodsDAO goodsDAO = new GoodsDAO();
    private void loadFruits(){
        goodsList.addAll(goodsDAO.getAllGoods());
        if (goodsList.size() > 0) {
            try {
                setChosenFruit(goodsList.get(0));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            myListener = new MyListener() {
                @Override
                public void onClickListener(Goods goods) throws IOException {
                    setChosenFruit(goods);
                    System.out.println(goods.getName());
                    System.out.println(goods.getPrice());
                }
            };
        }

        int column = 0;
        int row = 1;

        try {
            for (int i = 0; i < goodsList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(HelloApplication.class.getResource("item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(goodsList.get(i), myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);

                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnExitBasket(MouseEvent event) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), basketWindow);

        translateTransition.setFromX(0);

        translateTransition.setToX(basketWindow.getWidth());

        translateTransition.play();

        translateTransition.setOnFinished(event1 -> basketWindow.setVisible(false));


    }

    @FXML
    void clearBasket(ActionEvent event) {
        basketItem.getChildren().clear();
    }

    @FXML
    void basketClick(MouseEvent event) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), basketWindow);

        basketWindow.setTranslateX(basketWindow.getWidth());

        translateTransition.setToX(0);

        basketWindow.setVisible(true);

        translateTransition.play();
    }

}
