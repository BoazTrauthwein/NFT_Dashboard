package application.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import application.classes.Collection;
import application.classes.HttpRequest;



public class MyController implements Initializable {

	HttpRequest request;
	private JSONArray uploadedCollection; // just in case we need this file for future use
	

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
    // Save a JSON file out of table of collections
    public void saveListFunc(ActionEvent event)  {    	
    	 JFileChooser chooser = new JFileChooser();
    	 chooser.setCurrentDirectory(new File("/home/me/Documents"));	// the default folder that will show when opened 
    	 int retrival = chooser.showSaveDialog(null);	// only save if approved saving by user
    	 if (retrival == JFileChooser.APPROVE_OPTION) {
    		 try(FileWriter fw = new FileWriter(chooser.getSelectedFile()+".json")) { //add .json to define json file type
    			    fw.write(CollectionTable.getItems().toString());	// save table data in the file
    			} catch (IOException ex) {}
    	 }
    }
    
    // Upload to table from an existing JSON file from system explorer, fills the tableview with file data
    public void uploadListFunc(ActionEvent event) throws Exception {
    	//FileChooserSample ChooseJSON = new FileChooserSample();
    	//ChooseJSON.launch(null);
    	
    	final JFileChooser fc = new JFileChooser();
    	fc.showOpenDialog(new JFrame());

//    	try {
//    	    // Open an input stream
//    	    Scanner reader = new Scanner(fc.getSelectedFile());
//    	}
    	
    	File res = fc.getSelectedFile();	// save selected file
    	JSONArray collectionList = new JSONArray();	// initialize 
    	
    	
    	JSONParser jsonParser = new JSONParser();	
        
        try (FileReader reader = new FileReader(res.getAbsolutePath()))	// get file by absolute path
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader); 	// if this is not a json file the software will crash probably
 
            collectionList = (JSONArray) obj;
         
             
            //Iterate over employee array
            //employeeList.forEach( emp -> parseCollectionObject( (JSONObject) emp ) );
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    	
    	
    	this.uploadedCollection = collectionList; // save json array to class, just in case we need it 
    	
    	int max = 100;
	    int min = 1;
	    
	    CollectionTable.getItems().clear();	// clear the table before inserting new data from file

        for (int i = 0; i < this.uploadedCollection.size(); i++) { // loop on collection list to get items for display
        	JSONObject currentCollection = ((JSONObject)this.uploadedCollection.get(i));
            String collName = (String)currentCollection.get("Name");
            Long opensea = (Long)currentCollection.get("Opensea");
            Long magiceden = (Long)currentCollection.get("Magiceden");
            Double diff = (Double)currentCollection.get("Diff");
            
            CollectionTable.getItems().add(new Collection(collName, opensea, magiceden, diff.floatValue()));
            //System.out.println(collName);
        }
    	
        
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
	
	// Fill the table view with collections from server
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
