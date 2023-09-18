package com.example.accountingofgoods.ui;

import com.example.accountingofgoods.HelloApplication;
import com.example.accountingofgoods.da.entity.User;
import com.example.accountingofgoods.dao.tables.UserDAO;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private VBox SIGNUP;

    @FXML
    private VBox SIGNIN;

    @FXML
    private Pane ColorPane;
    private DropShadow shadow ;

    @FXML
    private VBox SignUpInformation;

    @FXML
    private VBox SignInInformation;

    @FXML
    private Pane InformationPane;

    @FXML
    private Button btnSignIn;

    @FXML
    private Button btnChange1;

    @FXML
    private Button btnChange2;

    @FXML
    private Button btnSignUp;

    @FXML
    private TextField txtLogin;

    @FXML
    private TextField txtLoginReg;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPasswordReg;

    private int duration = 250;
    private double x, y;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.shadow = new DropShadow(BlurType.THREE_PASS_BOX, Color.web("#363636"), 0.0, 0.0, 8.0, 0.0);
        shadow.setHeight(0.0);
        shadow.setWidth(55.98);
        ColorPane.requestFocus();
    }

    @FXML
    void btnChangeScene2(ActionEvent event) {
        SIGNIN.setVisible(true);
        SIGNIN.setOpacity(0.0);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(this.duration), new KeyValue(ColorPane.translateXProperty(), 250)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(this.duration), new KeyValue(InformationPane.translateXProperty(), -250)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(this.duration), new KeyValue(ColorPane.styleProperty(), "-fx-background-color:  linear-gradient(to bottom right, #922724, #7b403b); -fx-background-radius : 20 ")));

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), new KeyValue(SIGNUP.opacityProperty(), 0)));
        timeline.setOnFinished(e -> {
            SignUpInformation.setVisible(true);
            SignInInformation.setVisible(false);
            Timeline timeline2 =  new Timeline();
            timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(this.duration), new KeyValue(ColorPane.translateXProperty(), 0)));
            timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(this.duration), new KeyValue(InformationPane.translateXProperty(), 0)));
            timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(this.duration), new KeyValue(ColorPane.styleProperty(), "-fx-background-color:  linear-gradient(to bottom right, #922724, #7b403b); -fx-background-radius : 20 0 0 20")));
            this.shadow.setOffsetX(8.0);
            timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(this.duration), new KeyValue(ColorPane.effectProperty(),  this.shadow )));
            timeline2.setOnFinished(e2 -> {
                SIGNUP.setVisible(false);
                Timeline timeline3 =  new Timeline();
                timeline3.getKeyFrames().add(new KeyFrame(Duration.millis(this.duration), new KeyValue(SIGNIN.opacityProperty(), 1)));
                timeline3.play();
            });
            timeline2.play();
        });
        timeline.play();
    }

    @FXML
    void btnChangeScene(ActionEvent event) {
        SIGNUP.setVisible(true);
        SIGNUP.setOpacity(0.0);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(this.duration), new KeyValue(ColorPane.translateXProperty(), 250)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(this.duration), new KeyValue(InformationPane.translateXProperty(), -250)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(this.duration), new KeyValue(ColorPane.styleProperty(), "-fx-background-color:  linear-gradient(to bottom right, #922724, #7b403b); -fx-background-radius : 20 ")));

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), new KeyValue(SIGNIN.opacityProperty(), 0)));
        timeline.setOnFinished(e -> {
            SignUpInformation.setVisible(false);
            SignInInformation.setVisible(true);
            Timeline timeline2 =  new Timeline();
            timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(this.duration), new KeyValue(ColorPane.translateXProperty(), 500)));
            timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(this.duration), new KeyValue(InformationPane.translateXProperty(), -500)));
            timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(this.duration), new KeyValue(ColorPane.styleProperty(), "-fx-background-color: linear-gradient(to bottom right, #922724, #7b403b); -fx-background-radius :  0 20 20 0 ")));
            this.shadow.setOffsetX(-8.0);
            timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(this.duration), new KeyValue(ColorPane.effectProperty(),  this.shadow )));
            timeline2.setOnFinished(e2 -> {
                SIGNIN.setVisible(false);
                Timeline timeline3 =  new Timeline();
                timeline3.getKeyFrames().add(new KeyFrame(Duration.millis(this.duration), new KeyValue(SIGNUP.opacityProperty(), 1)));
                timeline3.play();
            });
            timeline2.play();
        });
        timeline.play();
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

    @FXML
    public void screenPressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }
    @FXML
    public void screenDragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    UserDAO userDAO = new UserDAO();

    @FXML
    void signIn(ActionEvent event) throws IOException {
        String login = txtLogin.getText().trim();
        String password = txtPassword.getText().trim();
        boolean result = userDAO.singIn(login, password);
        if (result) {
            Stage stg = (Stage) btnSignIn.getScene().getWindow();
            if (userDAO.getRoleByUserId(userDAO.getLastUser()) == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Вітаємо!");
                alert.setHeaderText("Авторизація успішна");
                alert.setContentText("Ви увійшли в адмін панель");
                alert.showAndWait();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin_panel-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1100, 600);
                stg.setScene(scene);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Вітаємо!");
                alert.setHeaderText("Авторизація успішна");
                alert.showAndWait();
                Parent root = FXMLLoader.load(HelloApplication.class.getResource("market.fxml"));
                stg.setTitle("Fruits Marker");
                stg.setScene(new Scene(root));
                stg.show();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Помилка");
            alert.setHeaderText("Логін та/або пароль не вірні!");
            alert.showAndWait();
        }

    }


    @FXML
    void signUp(ActionEvent event) {
        String login = txtLoginReg.getText();
        String password = txtPasswordReg.getText();
        User user = new User(login, password, 2);
        if(login.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Попередження!");
            alert.setHeaderText("Для реєстрації всі поля мають бути заповнені");
            alert.showAndWait();
        }else {
            boolean result = userDAO.addUser(user);
            if(result){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Вітаємо!");
                alert.setHeaderText("Реєстрація успішна");
                alert.showAndWait();
                btnChangeScene(new ActionEvent());
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Попередження!");
                alert.setHeaderText("Дані не вірні");
                alert.showAndWait();
            }
        }
    }

}
