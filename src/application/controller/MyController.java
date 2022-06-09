package application.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.event.ChangeListener;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URI;
import java.net.URL;
import java.net.http.*;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import application.classes.Collection;
import application.classes.NftHttpRequest;



public class MyController implements Initializable {
	

	NftHttpRequest request;
	
	int emailThreshold = 10; // default

	public  boolean flagRefresh = false;
	public  boolean flagEmails = false;
	
	 @FXML private Pagination pagination;
	    private ObservableList<Collection> masterData = FXCollections.observableArrayList();
	    private int dataSize;
	    private int rowsPerPage = 5;

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
    
    
    

     public  class RefreshTask extends TimerTask {
   
    	
        public void run() {
        	if(flagRefresh) {
        		CollectionTable.getItems().clear();
            	initializeCollectionTable();
        	}

        	
        }
    }
    
    public class EmailsTask extends TimerTask {
    
        public void run() {
        	if(flagEmails) {
        		boolean isSelected = SaveEmailAdressesBtn.isSelected();
            	String emails= EmailsInput.getText();

            	if(isSelected ){
            	   
            	} else {
            	   
            	}
        	}
        	
        	
        	
        }
    }
     
    
    
    
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
    	request = new NftHttpRequest();
    	
    	initializeCollectionTable();
    	initializeCmxList();
//    	printTestText();
    	
    }
    
    public void checkRefresh(ActionEvent event)  {
    	
    	boolean isSelected = SaveRefreshBtn.isSelected();
    	Integer sec = Integer.valueOf(RefreshSecInput.getText());
    	TimerTask taskRefresh;
    	Timer timerForRefresh = new Timer();
    	

    	if(isSelected ){
    		flagRefresh=true;
    		taskRefresh = new RefreshTask();
    		timerForRefresh.schedule(taskRefresh, sec*1000, sec*1000);

    	} else {
    		flagRefresh=false;
    	}
    }
    
    @SuppressWarnings("null")
	public void checkIfNeedToSendEmail(ActionEvent event)  {
    	boolean isSelected = SaveEmailTimeBtn.isSelected();
    	Integer sec = Integer.valueOf(SendEmailEveryXtimeInput.getText());
    	TimerTask taskEmails = null;
    	Timer timerForSendingEmails = new Timer();

    	if(isSelected ){
    		flagEmails=true;
    		taskEmails = new EmailsTask();
    		timerForSendingEmails.schedule(taskEmails, sec*1000, sec*1000);
    	} else {
    		flagEmails=false;
    	}
        
    }
    
    public void checkEmailThreshold(ActionEvent event)  {
    	boolean isSelected = SaveEmailThersholdBtn.isSelected();

    	if(isSelected ){
    	   emailThreshold = Integer.valueOf(EmailthresholdInput.getText());
    	} else {
    		emailThreshold = 10; // default
    	}
        
    }
    
    /*public void sendEmails(ActionEvent event)  {
    	boolean isSelected = SaveEmailAdressesBtn.isSelected();
    	String emails= EmailsInput.getText();

    	if(isSelected ){
    	   
    	} else {
    	   
    	}
        
    }
    */
    
    public void setShowXentriesCMB(ActionEvent event) throws Exception {
    

	}
    
    public void saveListFunc(ActionEvent event)  {
    	
    
    }
    
    public void uploadListFunc(ActionEvent event) throws Exception {
    	
        
    }
    
    public void searchCollectionFunc(ActionEvent event) throws Exception {
    	
    	ObservableList data =  CollectionTable.getItems();
    	SearchTextInput.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
    	            if (oldValue != null && (newValue.length() < oldValue.length())) {
    	            	CollectionTable.setItems(data);
    	            }
    	            String value = newValue.toLowerCase();
    	            ObservableList<Collection> subentries = FXCollections.observableArrayList();

    	            long count = CollectionTable.getColumns().stream().count();
    	            for (int i = 0; i < CollectionTable.getItems().size(); i++) {
    	                for (int j = 0; j < count; j++) {
    	                    String entry = "" + CollectionTable.getColumns().get(j).getCellData(i);
    	                    if (entry.toLowerCase().contains(value)) {
    	                        subentries.add(CollectionTable.getItems().get(i));
    	                        break;
    	                    }
    	                }
    	            }
    	            CollectionTable.setItems(subentries);
    	        });
    }
    
    public void addCollectionFunc(ActionEvent event) throws Exception {
		
		
	}
    
    private JSONArray getJSONArray(String urlRequest)
    {
		String req = request.getRequest(urlRequest);
		
		if(urlRequest.contains(".magiceden.")) {
			Object obj = JSONValue.parse(req);
		    JSONArray arr = (JSONArray)obj;
		    return arr;
		}
		else
		{
			Object obj = JSONValue.parse(req);
		    JSONArray arr = (JSONArray)(((JSONObject)obj).get("collections"));
		    return arr;
		}
    }
    
    private JSONObject getJSONObject(String urlRequest)
    {
    	
		String req = request.getRequest(urlRequest);
		
		Object obj = JSONValue.parse(req);
		JSONObject jsonObj = (JSONObject)obj;
	    return jsonObj;
    }
	
	
	private void fillTableItems()
	{
		JSONArray arrCollection = getJSONArray("https://api-mainnet.magiceden.dev/v2/collections?offset=0&limit=20");

        for (int i = 0; i < arrCollection.size(); i++) {
            String collName = (String)((JSONObject)arrCollection.get(i)).get("name");
            String collSymbol = (String)((JSONObject)arrCollection.get(i)).get("symbol");
            
            long floorPriceMagiceden = getMagicedenFloorPrice("https://api-mainnet.magiceden.dev/v2/collections/" + collSymbol);
            
    		long floorPriceOpensea = getOpenseaFloorPrice("https://api.opensea.io/api/v1/collection/" + collSymbol.replace('_', '-') + "/stats", collSymbol);
            CollectionTable.getItems().add(new Collection(collName, floorPriceOpensea, floorPriceMagiceden,  0));
            
            System.out.println("-----------------------------------------------------------------------------------");
//            sleep(500);
        }
	}
	
	private long getMagicedenFloorPrice(String urlPath)
	{
        System.out.println(urlPath);
        JSONObject objSymbolMagiceden = getJSONObject(urlPath);
        long floorPrice = 0;
        try {
        	floorPrice = (long)objSymbolMagiceden.get("floorPrice"); 
		} catch (Exception e) {
			floorPrice = -1;
		}
        return floorPrice;
	}
	
	private long getOpenseaFloorPrice(String urlPath, String collSymbol)
	{
        System.out.println(urlPath);
        long floorPrice = 0;
        try {
        	JSONObject joValue = getJSONObject("https://api.opensea.io/api/v1/collection/" + collSymbol.replace('_', '-') + "/stats");
    		JSONObject joStats = (JSONObject)joValue.get("stats");
    		double dPrice = (double)joStats.get("floor_price");
    		floorPrice = (long)(dPrice * 100000);
		} catch (Exception e) {
			floorPrice = -1;
		}
        return floorPrice;
	}
	
	private void sleep(int miliseconds)
	{
		try {
		    Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	}
	
	
	private void printTestText()
	{
//		JSONArray arrCollection = getJSONArray("https://api-mainnet.magiceden.dev/v2/collections?offset=0&limit=10");
//
//        for (int i = 0; i < arrCollection.size(); i++) {
//        	String collSymbol = (String)((JSONObject)arrCollection.get(i)).get("symbol");
//        	System.out.println(collSymbol);
//        }
		
		JSONObject joValue = getJSONObject("https://api-mainnet.magiceden.dev/v2/collections/drc");
		long lPrice = (long)joValue.get("floorPrice");
        System.out.println(lPrice);
        System.out.println("-----------------------------------------------------------------------------------");
        
        
        
		joValue = getJSONObject("https://api.opensea.io/api/v1/collection/drc/stats");
		JSONObject joStats = (JSONObject)joValue.get("stats");
		double fPrice = (double)joStats.get("floor_price");
		System.out.println(fPrice);
		
//		arrCollection = getJSONArray("https://api.opensea.io/api/v1/collections?offset=0&limit=10");
//
//        for (int i = 0; i < arrCollection.size(); i++) {
//        	String collSymbol = (String)((JSONObject)arrCollection.get(i)).get("symbol");
//        	System.out.println(collSymbol);
//        }
	}



}
