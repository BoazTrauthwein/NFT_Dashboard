package application.controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;



public class MyController {

    @FXML
    private TableView<?> CollectionTable;

    @FXML
    private TableColumn<?, ?> CollectionNames;

    @FXML
    private TableColumn<?, ?> Opensea;

    @FXML
    private TableColumn<?, ?> MagicEden;

    @FXML
    private TableColumn<?, ?> Diff;

    @FXML
    private Button AddCollectionBtn;

    @FXML
    private CheckBox SaveEmailTimeBtn;

    @FXML
    private CheckBox SaveEmailThersholdBtn;

    @FXML
    private CheckBox SaveEmailAdressesBtn;

    @FXML
    private CheckBox SaveRefreshBtn;

    @FXML
    private TextField EmailsInput;

    @FXML
    private TextField RefreshSecInput;

    @FXML
    private TextField SendEmailEveryXtimeInput;

    @FXML
    private TextField EmailthresholdInput;

    @FXML
    private TextField SearchTextInput;
    
    @FXML
    private Button SaveListBtn;

    @FXML
    private Button UploadListBtn;

   
    
    @FXML
    private ComboBox<String> ShowXentriesCMB;
    public ObservableList<String> list;
    
    
    public void setShowXentriesCMB(ActionEvent event) throws Exception {
    
		ArrayList<String> cmxList = new ArrayList<String>();	
		cmxList.add("10");
		cmxList.add("25");
		cmxList.add("50");
		cmxList.add("100");

		
		list = FXCollections.observableArrayList(cmxList);
		ShowXentriesCMB.setItems(list);
	}
    
    public void saveListFunc(ActionEvent event)  {
    	
    
    }
    
    public void uploadListFunc(ActionEvent event) throws Exception {
    	
        
    }
    
    public void searchCollectionFunc(ActionEvent event) throws Exception {
    	
        
    }
    
    public void addCollectionFunc(ActionEvent event) throws Exception {
    	
        
    }

	public void initialize() {
    	
    }
    

}
