package application.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
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
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import application.classes.NFTCollection;
import application.classes.NftHttpRequest;
import application.classes.TableDataBuilder;



public class MyController implements Initializable {
	
	
	private JSONArray uploadedCollection; // just in case we need this file for future use
	
	int emailThreshold = 10; // default

	public  boolean flagRefresh = false;
	public  boolean flagEmails = false;
	
	 @FXML private Pagination pagination;
	    private ObservableList<NFTCollection> masterData = FXCollections.observableArrayList();
	    private int dataSize;
	    private int rowsPerPage = 5;

    @FXML
    private TableView<NFTCollection> CollectionTable;

    @FXML
    private TableColumn<NFTCollection, String> CollectionNames;

    @FXML
    private TableColumn<NFTCollection, String> Opensea;

    @FXML
    private TableColumn<NFTCollection, String> MagicEden;

    @FXML
    private TableColumn<NFTCollection, String> Diff;

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
            		String RecipientList[] = {EmailsInput.getText()};
           		 	JavaEmail.sendMailNow(CollectionTable.getItems().toString(), RecipientList);
            	   
            	} else {
            	   
            	}
        	}
        	
        	
        	
        }
    }
     
    
    
    
    public void initializeCollectionTable()
    {
        CollectionNames.setCellValueFactory(new PropertyValueFactory<NFTCollection, String>("name"));
        Opensea.setCellValueFactory(new PropertyValueFactory<NFTCollection, String>("openseaSol"));
        MagicEden.setCellValueFactory(new PropertyValueFactory<NFTCollection, String>("magicEdenSol"));
        Diff.setCellValueFactory(new PropertyValueFactory<NFTCollection, String>("diff"));
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
    // Save a JSON file out of table of collections
    public void saveListFunc(ActionEvent event)  {  
    	
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
    	
    	 try {
 			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
 		} catch (ClassNotFoundException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		} catch (InstantiationException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		} catch (IllegalAccessException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		} catch (UnsupportedLookAndFeelException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    	
    	final JFileChooser fc = new JFileChooser();
    	
    	fc.setFileFilter(new JSONFilter());
    	
    	fc.showOpenDialog(new JFrame());
    	
    	File res = fc.getSelectedFile();	// save selected file
    	JSONArray collectionList = new JSONArray();	// initialize 
    	
    	
    	JSONParser jsonParser = new JSONParser();	
        
        try (FileReader reader = new FileReader(res.getAbsolutePath()))	// get file by absolute path
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader); 	// if this is not a json file the software will crash probably
 
            collectionList = (JSONArray) obj;
 
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
            
            CollectionTable.getItems().add(new NFTCollection(collName, opensea, magiceden, diff.floatValue()));
            //System.out.println(collName);
        }
    	
        
    }
    
    public void searchCollectionFunc(ActionEvent event) throws Exception {
    	String str = "HAAALLLOOO";
    	ObservableList data =  CollectionTable.getItems();
    	SearchTextInput.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
    	            if (oldValue != null && (newValue.length() < oldValue.length())) {
    	            	CollectionTable.setItems(data);
    	            }
    	            String value = newValue.toLowerCase();
    	            ObservableList<NFTCollection> subentries = FXCollections.observableArrayList();

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
    
	
	// Fill the table view with collections from server
	private void fillTableItems()
	{
		TableDataBuilder dataBuilder = new TableDataBuilder();
		dataBuilder.buildData();
		ArrayList<NFTCollection> alNftData = dataBuilder.getNftCollection();
		for (var nftData : alNftData)
			CollectionTable.getItems().add(new NFTCollection(nftData.getName(), nftData.getOpenseaSol(), nftData.getMagicEdenSol(),  0));
	}

}
