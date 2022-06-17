package application.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.classes.NftTableData;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class DashboardController implements Initializable{
	private final static int dataSize = 100;
	private final static int rowsPerPage = 25;
	
	private final List<NftTableData> data = createData();
	
    @FXML
    private TableView<NftTableData> tblCollection;

    @FXML
    private TableColumn<NftTableData, String> tcCollectionName;

    @FXML
    private TableColumn<NftTableData, String> tcOpensea;

    @FXML
    private TableColumn<NftTableData, String> tcMagicEden;

    @FXML
    private TableColumn<NftTableData, String> tcDiff;

    @FXML
    private Button btnAddCollection;

    @FXML
    private Button btnSearch;

    @FXML
    private ComboBox<?> cbRowsPerPage;

    @FXML
    private Pagination pagTableView;

    @FXML
    private TextField tfAddCollection;

    @FXML
    private TextField tfSearchCollection;

    @FXML
    void addCollectionToTableView(ActionEvent event) {

    }

    @FXML
    void searchCollectionInTableView(ActionEvent event) {

    }

    @FXML
    void setRowsPerPage(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		tcCollectionName.setCellValueFactory(new PropertyValueFactory<NftTableData, String>("name"));
		tcOpensea.setCellValueFactory(new PropertyValueFactory<NftTableData, String>("openseaSol"));
		tcMagicEden.setCellValueFactory(new PropertyValueFactory<NftTableData, String>("magicEdenSol"));
        tcDiff.setCellValueFactory(new PropertyValueFactory<NftTableData, String>("diff"));
        tblCollection.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        tblCollection.setItems(FXCollections.observableArrayList(data.subList(0, 5)));
        
        pagTableView.setPageCount(data.size() / rowsPerPage + 1);
        pagTableView.setPageFactory(this::createPage);
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
        tblCollection.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));

        return tblCollection;
    }
}
