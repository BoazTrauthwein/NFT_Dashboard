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

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import application.classes.Collection;
import application.classes.HttpRequest;



public class MyController implements Initializable {

	HttpRequest request;
	

    @FXML
    private TableView<Collection> CollectionTable;

    @FXML
    private TableColumn<Collection, String> CollectionNames;

    @FXML
    private TableColumn<Collection, String> Opensea;

    @FXML
    private TableColumn<Collection, String> MagicEden;

    @FXML
    private TableColumn<Collection, String> Diff;

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
    
    public void initializeCollectionTable()
    {
        CollectionNames.setCellValueFactory(new PropertyValueFactory<Collection, String>("name"));
        Opensea.setCellValueFactory(new PropertyValueFactory<Collection, String>("openseaSol"));
        MagicEden.setCellValueFactory(new PropertyValueFactory<Collection, String>("magicEdenSol"));
        Diff.setCellValueFactory(new PropertyValueFactory<Collection, String>("diff"));
        CollectionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
		fillTableItems();
    }
    
    public void initializeCmxList()
    {
		ArrayList<String> cmxList = new ArrayList<String>();	
		cmxList.add("10");
		cmxList.add("25");
		cmxList.add("50");
		cmxList.add("100");

		
		list = FXCollections.observableArrayList(cmxList);
		ShowXentriesCMB.setItems(list);
		ShowXentriesCMB.setPromptText(list.get(0).toString());
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	initializeCollectionTable();
    	initializeCmxList();
    }
    
    
    public void setShowXentriesCMB(ActionEvent event) throws Exception {
    

	}
    
    public void saveListFunc(ActionEvent event)  {
    	
    
    }
    
    public void uploadListFunc(ActionEvent event) throws Exception {
    	
        
    }
    
    public void searchCollectionFunc(ActionEvent event) throws Exception {
    	
        
    }
    
    public void addCollectionFunc(ActionEvent event) throws Exception {
		
		
	}
    
    private JSONArray getJSONArray(String urlRequest)
    {
    	request = new HttpRequest();
		String req = request.getRequest(urlRequest);
		
		Object obj = JSONValue.parse(req);
	    JSONArray arr = (JSONArray)obj;
	    return arr;
    }
    
    private JSONObject getJSONObject(String urlRequest)
    {
    	request = new HttpRequest();
		String req = request.getRequest(urlRequest);
		
		Object obj = JSONValue.parse(req);
		JSONObject jsonObj = (JSONObject)obj;
	    return jsonObj;
    }
	
	
	private void fillTableItems()
	{
		JSONArray arrCollection = getJSONArray("https://api-mainnet.magiceden.dev/v2/collections?offset=0&limit=50");
	    
	    int max = 100;
	    int min = 1;

        for (int i = 0; i < arrCollection.size(); i++) {
            String collName = (String)((JSONObject)arrCollection.get(i)).get("name");
            String collSymbol = (String)((JSONObject)arrCollection.get(i)).get("symbol");
            
            System.out.println("https://api-mainnet.magiceden.dev/v2/collections/" + collSymbol);
            String tmp = "https://api-mainnet.magiceden.dev/v2/collections/" + collSymbol;
            JSONObject objSymbol = getJSONObject(tmp);
            
            long floorPrice = 0;
            try {
            	floorPrice = (long)objSymbol.get("floorPrice"); 
			} catch (Exception e) {
				floorPrice = 0;
			}
            
            int num2 = (int)(Math.random()*(max-min+1)+min);  
            CollectionTable.getItems().add(new Collection(collName, num2, floorPrice,  0));
            //System.out.println(collName);
        }
	}
	


	public void initialize() {
    	
    }
    

}
