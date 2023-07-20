package com.example.semestergroupwork;
/*
    TEAM NAME: AZTECH
    1. BAWAH ABUBA IBRAHIM - 10863084 - PROGRAMMER
    2. BAWAH MANSURA-10911156- DATABASE ADMIN
    3. GYENI PRISCILLA- 10865576-PROJECT MANAGER
    4. HAYIBOR CINDY -10905039 -UI/UX DESIGNER
    5. SAMUEL AMPONSAH - 10922693 - UI/UX DESIGNER

 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;


public class DashboardController implements Initializable {
    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement prepare;
    @FXML
    private TableView<MedicineData> tableView;

    @FXML
    private TableColumn<MedicineData, Integer> medicineID_col;
    @FXML
    private TableColumn<MedicineData, String> brandName_col;
    @FXML
    private TableColumn<MedicineData, String> productName_col;
    @FXML
    private TableColumn<MedicineData, Double> price_col;
    @FXML
    private TableColumn<MedicineData, String> status_col;
    @FXML
    private TableColumn<MedicineData, Date> expiry_date_col;

    @FXML
    private TextField IDTextField;

    @FXML
    private TextField brandNameTextField;

    @FXML
    private TextField productNameTextField;

    @FXML
    private TextField price_field;

    @FXML
    private DatePicker date_field;

    @FXML
    private ComboBox status_options;

    @FXML
    private TextField search_field;

    @FXML
    private Button search_button;

    private String[] addStatusList = {"Available", "Not Available"};

    // This functions handles the status drop down list
    public void setAddStatusList (){
        List<String> listType = new ArrayList<>();

        for (String data: addStatusList){
            listType.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listType);
        status_options.setItems(listData);
    }
    public void setAddButton (ActionEvent event){
        String selectedOption = (String) status_options.getSelectionModel().getSelectedItem();
        String addNewMedicine = "insert into medicine (medicineID, brandName, productName, price, status, expiryDate) values " +
                "('" +IDTextField.getText() + "','" +brandNameTextField.getText()+ "','"+ productNameTextField.getText()+
                "',"+ price_field.getText() +",'"+ selectedOption +"','"+date_field.getValue()+"');";

        try{
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(addNewMedicine);
        } catch (Exception e){
            e.printStackTrace();
        }
        addMedicineShowListData ();
    }

    public void setDeleteButton (ActionEvent event){
        String deletequery = "delete from medicine where medicineID = "+ IDTextField.getText() + ";";
        try{
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(deletequery);
        } catch (Exception e){
            e.printStackTrace();
        }
        addMedicineShowListData ();
    }

    public void setUpdateButton (ActionEvent event){
        String selectedOption = (String) status_options.getSelectionModel().getSelectedItem();
        String updateQuery = "insert into medicine (medicineID, brandName, productName, price, status, expiryDate) values " +
                "('" +IDTextField.getText() + "','" +brandNameTextField.getText()+ "','"+ productNameTextField.getText()+
                "',"+ price_field.getText() +",'"+ selectedOption +"','"+date_field.getValue()+"');";

        String deleteQuery = "delete from medicine where medicineID = "+ IDTextField.getText() + ";";

        String selectQuery = "select * from medicine;";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(selectQuery);
            boolean isFound = false;
            while (queryResult.next() && !isFound){
                if (Objects.equals(IDTextField.getText(), queryResult.getString("medicineID"))){
                    isFound = true;
                }
            }
            if (isFound){
                statement.executeUpdate(deleteQuery);
                statement.executeUpdate(updateQuery);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        addMedicineShowListData ();
    }

    public void setClearButton(ActionEvent event){
        IDTextField.setText("");
        brandNameTextField.setText("");
        price_field.setText("");
        productNameTextField.setText("");
        status_options.getSelectionModel().clearSelection();
        date_field.getEditor().clear();
    }

    // This function creates an observable list that was used to create and show data on table
    public ObservableList<MedicineData> addMedicinesListData(){
        ObservableList<MedicineData> listData = FXCollections.observableArrayList();

        connectDB = connectNow.getConnection();
        String sql = "select * from medicine;";

        try {
            prepare  = connectDB.prepareStatement(sql);
            resultSet = prepare.executeQuery(sql);

            MedicineData medData;

            while (resultSet.next()){
                medData = new MedicineData(resultSet.getInt("medicineID"), resultSet.getString("brandName"),
                        resultSet.getString("productName"),resultSet.getDouble("price"),
                        resultSet.getString("status"),resultSet.getDate("expiryDate"));

                listData.add(medData);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<MedicineData> addMedicineList;

    // This function enables the mysql table data to display
    public void addMedicineShowListData (){
        addMedicineList = addMedicinesListData();

        medicineID_col.setCellValueFactory(new PropertyValueFactory<>("medicineID"));
        brandName_col.setCellValueFactory(new PropertyValueFactory<>("brandName"));
        productName_col.setCellValueFactory(new PropertyValueFactory<>("productName"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        status_col.setCellValueFactory(new PropertyValueFactory<>("status"));
        expiry_date_col.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));

        tableView.setItems(addMedicineList);
    }

    public void addMedicineSearch (){
        FilteredList<MedicineData> filter = new FilteredList<>(addMedicineList, e-> true);

        search_field.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateMedicineData -> {
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }

                String searchKey = newValue.toLowerCase();
                //String medId= String.valueOf(predicateMedicineData.getMedicineID());
                if(String.valueOf(predicateMedicineData.getMedicineID()).contains(searchKey)){
                    return true;
                } else if (predicateMedicineData.getBrandName().toLowerCase().contains(searchKey)) {
                    return true;
                }else if (predicateMedicineData.getProductName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateMedicineData.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                }else if (predicateMedicineData.getPrice().toString().contains(searchKey)) {
                    return true;
                }else return predicateMedicineData.getExpiryDate().toString().contains(searchKey);
            });
        });

        SortedList<MedicineData> sortedList = new SortedList<>(filter);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
        //addMedicineShowListData();

    }


    // This function enable you to select an entry from the table
    public void addMedicineSelect(){
        MedicineData medData = tableView.getSelectionModel().getSelectedItem();
        int num = tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1 ){
            return;
        }

        IDTextField.setText(String.valueOf(medData.getMedicineID()));
        brandNameTextField.setText(String.valueOf(medData.getBrandName()));
        productNameTextField.setText(String.valueOf(medData.getProductName()));
        price_field.setText(String.valueOf(medData.getPrice()));
        date_field.setValue(LocalDate.parse(String.valueOf(medData.getExpiryDate())));
//        status_options.getSelectionModel().select(medData.getStatus());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addMedicineShowListData();
        setAddStatusList ();
        addMedicineSearch ();
    }
}