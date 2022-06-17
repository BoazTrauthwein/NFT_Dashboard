package application.controller;




import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.classes.NftTableData;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TestingController implements Initializable {
	
	private final static int dataSize = 100;
	private final static int rowsPerPage = 25;
	
	private final List<NftTableData> data = createData();

	@FXML
    private TableView<NftTableData> tblView;
	
    @FXML
    private TableColumn<NftTableData, String> tblCol1;

    @FXML
    private TableColumn<NftTableData, String> tblCol2;

    @FXML
    private TableColumn<NftTableData, String> tblCol3;

    @FXML
    private TableColumn<NftTableData, String> tblCol4;

    @FXML
    private Pagination tblPagination;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		tblCol1.setCellValueFactory(new PropertyValueFactory<NftTableData, String>("name"));
		tblCol2.setCellValueFactory(new PropertyValueFactory<NftTableData, String>("openseaSol"));
		tblCol3.setCellValueFactory(new PropertyValueFactory<NftTableData, String>("magicEdenSol"));
        tblCol4.setCellValueFactory(new PropertyValueFactory<NftTableData, String>("diff"));
        tblView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        tblView.setItems(FXCollections.observableArrayList(data.subList(0, 5)));
        
        tblPagination.setPageCount(data.size() / rowsPerPage + 1);
        tblPagination.setPageFactory(this::createPage);
	}
	
    private List<NftTableData> createData() {
        List<NftTableData> data = new ArrayList<>(dataSize);

        for (int i = 0; i < dataSize; i++) {
            data.add(new NftTableData("hii " + i, "foo " + i + 1, "bar " + i + 2,"-"));
        }

        return data;
    }
    
    private Node createPage(int pageIndex) {

        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, data.size());
        tblView.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));

        return tblView;
    }

    





























//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.ResourceBundle;
//
//import application.classes.NftTableData;
//import javafx.collections.FXCollections;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.control.Button;
//import javafx.scene.control.CheckBox;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.Pagination;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.input.MouseDragEvent;
//
//public class TestingController implements Initializable {
//	private final static int dataSize = 555;
//	private final static int rowsPerPage = 50;
//	
//	private final List<NftTableData> data = createData();
//
//    @FXML
//    private Button AddCollectionBtn;
//
//    @FXML
//    private TableView<NftTableData> CollectionTable;
//
//    @FXML
//    private TableColumn<NftTableData, String> CollectionNames;
//
//    @FXML
//    private TableColumn<NftTableData, String> Opensea;
//
//    @FXML
//    private TableColumn<NftTableData, String> MagicEden;
//
//    @FXML
//    private TableColumn<NftTableData, String> Diff;
//
//    @FXML
//    private TextField EmailsInput;
//
//    @FXML
//    private TextField EmailthresholdInput;
//
//    @FXML
//    private TextField RefreshSecInput;
//
//    @FXML
//    private CheckBox SaveEmailAdressesBtn;
//
//    @FXML
//    private CheckBox SaveEmailThersholdBtn;
//
//    @FXML
//    private CheckBox SaveEmailTimeBtn;
//
//    @FXML
//    private Button SaveListBtn;
//
//    @FXML
//    private CheckBox SaveRefreshBtn;
//
//    @FXML
//    private TextField SearchTextInput;
//
//    @FXML
//    private TextField SendEmailEveryXtimeInput;
//
//    @FXML
//    private ComboBox<?> ShowXentriesCMB;
//
//    @FXML
//    private Button UploadListBtn;
//
//    @FXML
//    private Button btnSearch;
//
//    @FXML
//    private Pagination pagination;
//
//    @FXML
//    private TextField txtAddCollection;
//
//    @FXML
//    void addCollectionFunc(ActionEvent event) {
//
//    }
//
//    @FXML
//    void checkIfNeedToSendEmail(ActionEvent event) {
//
//    }
//
//    @FXML
//    void checkRefresh(ActionEvent event) {
//
//    }
//
//    @FXML
//    void saveListFunc(ActionEvent event) {
//
//    }
//
//    @FXML
//    void searchCollectionFunc(ActionEvent event) {
//
//    }
//
//    @FXML
//    void setShowXentriesCMB(MouseDragEvent event) {
//
//    }
//
//    @FXML
//    void uploadListFunc(ActionEvent event) {
//
//    }
//
//	@Override
//	public void initialize(URL arg0, ResourceBundle arg1) {
//		// TODO Auto-generated method stub
//		
//		CollectionNames.setCellValueFactory(new PropertyValueFactory<NftTableData, String>("name"));
//        Opensea.setCellValueFactory(new PropertyValueFactory<NftTableData, String>("openseaSol"));
//        MagicEden.setCellValueFactory(new PropertyValueFactory<NftTableData, String>("magicEdenSol"));
//        Diff.setCellValueFactory(new PropertyValueFactory<NftTableData, String>("diff"));
//        CollectionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        
//        CollectionTable.setItems(FXCollections.observableArrayList(data.subList(0, 5)));
//        
////        pagination.setPageCount(data.size() / rowsPerPage + 1);
////		pagination.setPageFactory(this::createPage);
//	}
//	
//    private List<NftTableData> createData() {
//        List<NftTableData> data = new ArrayList<>(dataSize);
//
//        for (int i = 0; i < dataSize; i++) {
//            data.add(new NftTableData("hii " + i, "foo " + i + 1, "bar " + i + 2,"-"));
//        }
//
//        return data;
//    }
//    
//    private Node createPage(int pageIndex) {
//
//        int fromIndex = pageIndex * rowsPerPage;
//        int toIndex = Math.min(fromIndex + rowsPerPage, data.size());
//        CollectionTable.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));
//
//        return CollectionTable;
//    }
}