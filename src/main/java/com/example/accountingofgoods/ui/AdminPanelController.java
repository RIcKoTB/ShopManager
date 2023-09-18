package com.example.accountingofgoods.ui;


import com.example.accountingofgoods.HelloApplication;
import com.example.accountingofgoods.da.entity.Goods;
import com.example.accountingofgoods.dao.MainDao;
import com.example.accountingofgoods.dao.tables.CategoryDAO;
import com.example.accountingofgoods.dao.tables.GoodsDAO;
import com.example.accountingofgoods.dao.tables.HistoryBuyDAO;
import com.example.accountingofgoods.db.functional.GenerateColum;
import com.example.accountingofgoods.db.functional.Request;
import com.example.accountingofgoods.db.functional.StructureTable;
import com.example.accountingofgoods.db.functional.TableData;
import com.example.accountingofgoods.emums.Tables;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Клас контроллера адмін панелі
 */
public class AdminPanelController {
    @FXML
    private BarChart<?, ?> Schedule;

    @FXML
    private Button addEmployee_btn;

    @FXML
    private AnchorPane addEmployee_form;

    @FXML
    private Button btnAddRequest;

    @FXML
    private Button btnChangePrice;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnClearField;

    @FXML
    private Button btnDeleteRequest;

    @FXML
    private Button btnUpdateRequest;

    @FXML
    private ChoiceBox<String> chbSelectDb;

    @FXML
    private ChoiceBox<String> chbSelectSearchColum;

    @FXML
    private Button home_btn;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label lblCountBuy;

    @FXML
    private Label lblCountDelivery;

    @FXML
    private Label lblEarnings;

    @FXML
    private Label lblGoodsId;

    @FXML
    private Label lblGoodsPhoto;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button salary_btn;

    @FXML
    private AnchorPane salary_form;

    @FXML
    private TableView<ObservableList> tableViewData;

    @FXML
    private TableView<ObservableList> tableViewСhangePrice;

    @FXML
    private TextField txtCategoryId;

    @FXML
    private TextField txtGoodsName;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtSearch;

    @FXML
    private Label username;

    @FXML
    public VBox vbFirst;

    @FXML
    public VBox vbSecond;

    Request r = new Request();

    /**
     * Метод пошуку даних
     * @param event подія
     */
    @FXML
    void searchData(ActionEvent event) {
        String search = txtSearch.getText();

        if(chbSelectSearchColum.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Помилка!");
            alert.setHeaderText("Не вибрана колонка");
            alert.setContentText("Для здійснення пошуку потрібно вибрати рядок таблиці за допомогою поля з права він пошуку");
            alert.showAndWait();
        }else {
            r.search(chbSelectDb.getValue(), chbSelectSearchColum.getValue(), search, dataFrmLable());
            fillTableSearch(chbSelectDb.getValue());

        }
    }

    /**
     * Метод заповнення таблиці даними які знайшли
     * @param nameTable Назва таблиці в якій відбувається пошук
     */
    public void fillTableSearch(String nameTable){
        clearTable();

        String search = txtSearch.getText();

        List<String> columns = StructureTable.getColumns(nameTable);

        List<String> additionalData = r.search(chbSelectDb.getValue(), chbSelectSearchColum.getValue(), search, dataFrmLable());
        ObservableList<ObservableList> data2 = FXCollections.observableArrayList();

        for (String row : additionalData) {
            String[] values = row.split(" ");
            data2.add(FXCollections.observableArrayList(Arrays.asList(values)));
        }

        System.out.println(columns.size());

        for (int i = 0; i < columns.size(); i++) {
            final int colIndex = i;
            TableColumn<ObservableList, String> col = new TableColumn<>(columns.get(i));
            col.setCellValueFactory(cellData -> {
                ObservableList<String> rowData = cellData.getValue();
                String cellValue = rowData.get(colIndex);
                return new SimpleStringProperty(cellValue);
            });
            tableViewData.getColumns().add(col);
        }

        tableViewData.setItems(data2);
        tableViewData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private ImageView imageView;

    CategoryDAO categoryDAO = new CategoryDAO();

    private void generationColumGoods() {
        if (chbSelectDb.getValue().equals("Goods")) {
            Label label1 = new Label("ID:");
            TextField textField1 = new TextField();
            textField1.setPrefWidth(50);

            Label label2 = new Label("Name:");
            TextField textField2 = new TextField();
            textField2.setPrefWidth(50);

            Label label3 = new Label("Price:");
            TextField textField3 = new TextField();
            textField3.setPrefWidth(50);

            vbFirst.setSpacing(5);
            vbFirst.getChildren().addAll(label1, textField1, label2, textField2, label3, textField3);

            try {
                List<Integer> category = categoryDAO.getAllCategoryIds();

                ChoiceBox<String> choiceBox = new ChoiceBox<>();
                choiceBox.getItems().addAll(category.stream().map(Object::toString).collect(Collectors.toList()));
                Label choiceLabel = new Label("Choice Category:");

                imageView = new ImageView();
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
                Button chooseImageButton = new Button("Choose Image");
                chooseImageButton.setOnAction(e -> chooseImage());

                vbSecond.setPadding(new Insets(0, 0, 0, 50));
                vbSecond.getChildren().addAll(choiceLabel, choiceBox, imageView, chooseImageButton);

                btnAddRequest.setOnAction(event -> {
                    GoodsDAO goodsDAO = new GoodsDAO();

                    String name = textField2.getText();
                    Image image = imageView.getImage();
                    int categoryValue = Integer.parseInt(choiceBox.getValue());
                    double price = Double.parseDouble(textField3.getText());

                    Goods goods = new Goods(name, image, categoryValue, price);

                    goodsDAO.addGoods(goods);

                    fillTableData(chbSelectDb.getValue());

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Вітаємо!");
                    alert.setHeaderText("Ви додали запис");
                    alert.showAndWait();
                });

                btnUpdateRequest.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        GoodsDAO goodsDAO = new GoodsDAO();
                        int id = Integer.parseInt(textField1.getText());
                        String name = textField2.getText();
                        Image image = imageView.getImage();
                        int categoryValue = Integer.parseInt(choiceBox.getValue());
                        double price = Double.parseDouble(textField3.getText());

                        Goods goods = new Goods(id, name, image, categoryValue, price);

                        goodsDAO.updateGoods(goods);

                        fillTableData(chbSelectDb.getValue());

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Вітаємо!");
                        alert.setHeaderText("Ви оновили запис");
                        alert.showAndWait();
                    }
                });

                btnDeleteRequest.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        GoodsDAO goodsDAO = new GoodsDAO();
                        int id = Integer.parseInt(textField1.getText());

                        goodsDAO.deleteGoods(id);

                        fillTableData(chbSelectDb.getValue());

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Вітаємо!");
                        alert.setHeaderText("Ви видалили запис");
                        alert.showAndWait();
                    }
                });

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }


    @FXML
    void addRequest(ActionEvent event) throws SQLException, IOException {
        r.insert(chbSelectDb.getValue(), dataFrmTextField());
        fillTableData(chbSelectDb.getValue());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Вітаємо!");
        alert.setHeaderText("Ви додали запис");
        alert.showAndWait();
    }

    @FXML
    void updateRequest(ActionEvent event) {
        r.update(chbSelectDb.getValue(), dataFrmTextField(), dataFrmLable());

        fillTableData(chbSelectDb.getValue());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Вітаємо!");
        alert.setHeaderText("Ви оновили запис");
        alert.showAndWait();
    }

    @FXML
    void clearAll(ActionEvent event) {
        clearTable();
        clearManagementElement();
    }

    @FXML
    void deleteRequest(ActionEvent event) {

        String firstTextFieldValue = null;

        for (Node node : vbFirst.getChildren()) {
            if (node instanceof HBox) {
                HBox hBox = (HBox) node;
                for (Node node1 : hBox.getChildren()) {
                    if (node1 instanceof TextField) {
                        TextField textField = (TextField) node1;
                        firstTextFieldValue = textField.getText();
                        r.delete(chbSelectDb.getValue(), firstTextFieldValue);
                        break;
                    }
                }
            }
        }
        fillTableData(chbSelectDb.getValue());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Вітаємо!");
        alert.setHeaderText("Ви видалили запис");
        alert.showAndWait();
    }

    /**
     * Метод для отримання назв полів для роботи з бд
     * @return Колекція назв полів
     */
    private List dataFrmLable(){
        List listLabel = new ArrayList<>();

        for (Node node : vbFirst.getChildren()) {
            if (node instanceof HBox) {
                HBox hBox = (HBox) node;
                for (Node node1 : hBox.getChildren()) {
                    if (node1 instanceof Label) {
                        Label lbl = (Label) node1;
                        String lableName = String.valueOf(lbl.getText());
                        try {
                            listLabel.add(Integer.parseInt(lableName));

                        } catch (Exception e) {
                            listLabel.add(lableName);
                        }
                    }
                }
            }
        }

        for (Node node : vbSecond.getChildren()) {
            if (node instanceof HBox) {
                HBox hBox = (HBox) node;
                for (Node node1 : hBox.getChildren()) {
                    if (node1 instanceof Label) {
                        Label lbl = (Label) node1;
                        String lableName = String.valueOf(lbl.getText());
                        try {
                            listLabel.add(Integer.parseInt(lableName));

                        } catch (Exception e) {
                            listLabel.add(lableName);
                        }
                    }
                }
            }
        }
        return listLabel;
    }

    /**
     * Метод для отримання текстових полів в бд
     * @return Колекція текстових полів
     */
    private List<Object> dataFrmTextField() {
        List<Object> fieldValues = new ArrayList<>();

        // Додаємо поля з першого vbFirst
        for (Node node : vbFirst.getChildren()) {
            if (node instanceof HBox) {
                HBox hBox = (HBox) node;
                for (Node node1 : hBox.getChildren()) {
                    if (node1 instanceof TextField) {
                        TextField textField = (TextField) node1;
                        String textFieldText = textField.getText();
                        try {
                            int intValue = Integer.parseInt(textFieldText);
                            fieldValues.add(intValue);
                        } catch (NumberFormatException e) {
                            fieldValues.add(textFieldText);
                        }
                    } else if (node1 instanceof ImageView) {
                        ImageView imageView = (ImageView) node1;
                        Image image = imageView.getImage();
                        fieldValues.add(image);
                    }
                }
            }
        }

        // Додаємо поля з vbSecond
        for (Node node : vbSecond.getChildren()) {
            if (node instanceof HBox) {
                HBox hBox = (HBox) node;
                for (Node node1 : hBox.getChildren()) {
                    if (node1 instanceof TextField) {
                        TextField textField = (TextField) node1;
                        String textFieldText = textField.getText();
                        try {
                            int intValue = Integer.parseInt(textFieldText);
                            fieldValues.add(intValue);
                        } catch (NumberFormatException e) {
                            fieldValues.add(textFieldText);
                        }
                    } else if (node1 instanceof ImageView) {
                        ImageView imageView = (ImageView) node1;
                        Image image = imageView.getImage();
                        fieldValues.add(image);
                    }
                }
            }
        }

        return fieldValues;
    }



    @FXML
    void addEmployeeSearch(KeyEvent event) {

    }

    @FXML
    void addEmployeeSelect(MouseEvent event) {

    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        Stage stg = (Stage) logout.getScene().getWindow();

        Parent root = FXMLLoader.load(HelloApplication.class.getResource("login-view.fxml"));
        stg.setTitle("Fruits Marker");
        stg.setScene(new Scene(root));
        stg.show();
    }

    @FXML
    void salarySelect(MouseEvent event) {

    }

    GenerateColum generateColum = new GenerateColum();

    /**
     * Метод перемикання на головну сторінку
     */
    public void homePage() {
        home_form.setVisible(true);
        addEmployee_form.setVisible(false);
        salary_form.setVisible(false);

        home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
        addEmployee_btn.setStyle("-fx-background-color:transparent");
        salary_btn.setStyle("-fx-background-color:transparent");
    }

    /**
     * Метод перемикання на сторінку з роботою з базою даних
     */
    public void tablePage() {
        home_form.setVisible(false);
        addEmployee_form.setVisible(true);
        salary_form.setVisible(false);

        addEmployee_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
        home_btn.setStyle("-fx-background-color:transparent");
        salary_btn.setStyle("-fx-background-color:transparent");
    }

    /**
     * Метод перемикання на сторінку зміни цін, та характеристик товарів
     */
    public void addPage() {
        home_form.setVisible(false);
        addEmployee_form.setVisible(false);
        salary_form.setVisible(true);

        salary_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
        addEmployee_btn.setStyle("-fx-background-color:transparent");
        home_btn.setStyle("-fx-background-color:transparent");
    }

    @FXML
    public void initialize() {
        chbSelectDb.setOnAction(this::updateData);
        generateDataFromMainWindow();
    }

    HistoryBuyDAO historyBuyDAO = new HistoryBuyDAO();

    GoodsDAO goodsDAO = new GoodsDAO();

    public void generateDataFromMainWindow(){
        int totalCount = historyBuyDAO.countTotalHistoryBuys();
        lblCountBuy.setText(String.valueOf(totalCount));
        lblEarnings.setText("341₴");
        int total = goodsDAO.getTotalGoodsCount();
        lblCountDelivery.setText(String.valueOf(total));

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        List<Goods> products = goodsDAO.getAllGoods();

// Додавання даних до серії
        for (int i = 0; i < products.size(); i++) {
            String product = products.get(i).getName();
            Double purchaseCount = products.get(i).getPrice();
            series.getData().add(new XYChart.Data<>(product, purchaseCount));
        }

        barChart.getData().add(series);

    }

    /**
     * Метод оновлення таблиці
     * @param actionEvent Подія
     */
    private void updateData(ActionEvent actionEvent) {
        generalGenerate();
        generationColumGoods();
    }

    @FXML
    void switchForm(ActionEvent event) {
        if (event.getSource() == home_btn) {
            homePage();

        } else if (event.getSource() == addEmployee_btn) {
            tablePage();

            generateDataInChoiceBox();
            generalGenerate();
            generationColumGoods();

        } else if (event.getSource() == salary_btn) {
            addPage();

            clearTableGoods();
            fillTableGoods();
        }
    }

    /**
     * Генерація всіх елементів
     */
    public void generalGenerate() {
        generateElements();
        fillTableData(chbSelectDb.getValue());
    }

    /**
     * Заповнення таблиці товарів
     */
    private void fillTableGoods(){
        clearTableGoods();

        List columns = StructureTable.getColumns("goods");
        MainDao dao = new MainDao();

        List<List> tableData = dao.readAll("goods");
        ObservableList<ObservableList> data = FXCollections.observableArrayList();

        for (List row : tableData) {
            data.add(FXCollections.observableArrayList(row));
        }

        for (int i = 0; i < columns.size(); i++) {
            final int colIndex = i;
            TableColumn col = new TableColumn<>(columns.get(i).toString());
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(colIndex).toString());
                }
            });
            tableViewСhangePrice.getColumns().add(col);
        }

        tableViewСhangePrice.setItems(data);
        tableViewСhangePrice.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableViewСhangePrice.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ObservableList<String> selectedRow = (ObservableList<String>) newSelection;

                String value = selectedRow.get(0).toString();
                String value2 = selectedRow.get(1).toString();
                String value3 = selectedRow.get(2).toString();
                String value4 = selectedRow.get(3).toString();
                String value5 = selectedRow.get(4).toString();

                lblGoodsId.setText(value);
                txtGoodsName.setText(value2);
                lblGoodsPhoto.setText(value3);
                txtCategoryId.setText(value4);
                txtSalary.setText(value5);
            }
        });
    }

    private int i = 0;

    /**
     * Заповнення випадаючого списку базами даних
     */
    public void generateDataInChoiceBox(){
        if(i == 0){
            ObservableList<String> items = (ObservableList<String>) chbSelectDb.getItems();
            chbSelectDb.setValue("Basket");

            items.add(Tables.Basket.name());
            items.add(Tables.History_Distributor.name());
            items.add(Tables.History_Buy.name());
            items.add(Tables.Departments.name());
            items.add(Tables.Goods.name());
            items.add(Tables.Distributors.name());
            items.add(Tables.Category.name());
            items.add(Tables.Store.name());
        }
        i++;
    }

    /**
     * Генерація текстових полів
     */
    public void generateElements() {
        clearManagementElement();

        String selectedValue = chbSelectDb.getValue();

        if (selectedValue != null && !selectedValue.equals("Goods")) {
            List<String> tableColumns = TableData.getTableColumns(selectedValue);

            int totalElements = tableColumns.size();
            int elementsPerContainer = totalElements / 2;
            int remainingElements = totalElements % 2;

            for (int i = 0; i < totalElements; i++) {
                String columnName = tableColumns.get(i);
                HBox generatedBox;

                if (TableData.isBlobColumn(selectedValue, columnName)) {
                    generatedBox = generateColum.generateBlob(columnName);
                } else {
                    generatedBox = generateColum.generate(columnName);
                }

                if (i < elementsPerContainer) {
                    vbFirst.getChildren().add(generatedBox);
                } else if (i < elementsPerContainer * 2) {
                    vbSecond.getChildren().add(generatedBox);
                } else if (remainingElements > 0) {
                    vbFirst.getChildren().add(generatedBox);
                    remainingElements--;
                }
            }

            chbSelectSearchColum.getItems().clear();

            ObservableList<String> itemsForColum = (ObservableList<String>) chbSelectSearchColum.getItems();

            List colums = dataFrmLable();
            for(int i = 0; i < colums.size(); i++){
                itemsForColum.add(colums.get(i).toString());
            }
        }
    }


    /**
     * Заповнення таблиці
     * @param nameTable База даних з якої відбувається заповнення
     */
    public void fillTableData(String nameTable) {
        clearTable();

        List columns = StructureTable.getColumns(nameTable);
        MainDao dao = new MainDao();

        List<List> tableData = dao.readAll(nameTable);
        ObservableList<ObservableList> data = FXCollections.observableArrayList();

        for (List row : tableData) {
            data.add(FXCollections.observableArrayList(row));
        }

        for (int i = 0; i < columns.size(); i++) {
            final int colIndex = i;
            TableColumn col = new TableColumn<>(columns.get(i).toString());
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(colIndex).toString());
                }
            });
            tableViewData.getColumns().add(col);
        }

        tableViewData.setItems(data);
        tableViewData.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Очищення таблиці
     */
    public void clearTable() {
        tableViewData.getItems().clear();
        tableViewData.getColumns().clear();
    }

    /**
     * Очищення таблиці товарів
     */
    public void clearTableGoods() {
        tableViewСhangePrice.getItems().clear();
        tableViewСhangePrice.getColumns().clear();
    }

    public void clearManagementElement() {
        vbFirst.getChildren().clear();
        vbSecond.getChildren().clear();
    }

    @FXML
    void btnClose(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnCollaps(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void changePrice(ActionEvent event) {
        r.updateGoods(lblGoodsId.getText(), txtGoodsName.getText(), lblGoodsPhoto.getText(), txtCategoryId.getText(), txtSalary.getText());

        fillTableGoods();
    }

    @FXML
    void clearField(ActionEvent event) {
        lblGoodsPhoto.setText(null);
        txtGoodsName.setText(null);
        lblGoodsId.setText(null);
        txtCategoryId.setText(null);
        txtSalary.setText(null);
    }
}
