package application.controller;

import java.util.ArrayList;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;
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
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import application.classes.NFTCollection;
import application.classes.NftTableData;
import application.classes.TableDataBuilder;
import application.interfaces.*;


public class DashboardController implements Initializable {
	
	ArrayList<NFTCollection> alNftData;
	ArrayList<NftTableData> alNftTableData;
	TableDataBuilder dataBuilder;
	private JSONArray uploadedCollection; // just in case we need this file for future use
	
	int emailThreshold = 10; // default
	int from = 0, to = 0, itemPerPage;

	public  boolean flagRefresh = false;
	public  boolean flagEmails = false;
	
	//TimerTask taskRefresh;
	//Timer timerForRefresh;
	TimerTask taskEmails  = new EmailsTask();
	Timer timerForSendingEmails = new Timer();
	Timer timerForRefresh = new Timer();
	TimerTask taskRefresh = new RefreshTask();
    public TaskFactory taskFactory = new TaskFactory();

	@FXML 
	private Pagination pagination;

    @FXML
    private TableView<NftTableData> CollectionTable;

    @FXML
    private TableColumn<NftTableData, String> CollectionNames;

    @FXML
    private TableColumn<NftTableData, String> Opensea;

    @FXML
    private TableColumn<NftTableData, String> MagicEden;

    @FXML
    private TableColumn<NftTableData, String> Diff;

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
    private Button btnSearch;
    
    @FXML
    private TextField txtAddCollection;
    
    @FXML
    private Label lblSearchError;

   
    
    @FXML
    private ComboBox<String> ShowXentriesCMB;
    public ObservableList<String> list;
    
    
    @FXML
    void searchCollectionFunc(ActionEvent event) {
    	
    	CollectionTable.getItems().clear();
    	for (var nftData : alNftData) {
    		if(nftData.getName().contains(SearchTextInput.getText()))
    			CollectionTable.getItems().add(getNftTableDataFromNftCollection(nftData));
    	}
    	
    }
    

    
    public String createMessage()
    {
    	// boaztrauthwein@gmail.com
    	StringBuilder msg = new StringBuilder();
    	//String strNum = EmailthresholdInput.getText();
    	checkEmailThreshold();
    	int  threshold = emailThreshold;
    	int nftSize = alNftData.size();
    	int len = (threshold*nftSize)/100;

    	msg.append("Collection Name" +"\t\t\t"+"OpenSea [sol]"+"\t\t\t"+"Magic Eden [sol]"+"\t\t\t"+"Diff[%]"+ "\n");
    	msg.append("\n");
    	for (int i = 0; i < len; i++) {
			NftTableData ntd = getNftTableDataFromNftCollection(alNftData.get(i));
			msg.append("\n");
			msg.append(ntd.getName() +"\t\t\t"+ ntd.getOpenseaSol()+"\t\t\t"+ntd.getMagicEdenSol()+"\t\t\t"+ntd.getDiff()+"\n");
    	}
    	
    	return msg.toString();
    }
    
    public void initializeCollectionTable()
    {
        CollectionNames.setCellValueFactory(new PropertyValueFactory<NftTableData, String>("name"));
        Opensea.setCellValueFactory(new PropertyValueFactory<NftTableData, String>("openseaSol"));
        MagicEden.setCellValueFactory(new PropertyValueFactory<NftTableData, String>("magicEdenSol"));
        Diff.setCellValueFactory(new PropertyValueFactory<NftTableData, String>("diff"));
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
		itemPerPage = Integer.parseInt(list.get(0).toString());
		
		
    }
    public void initializePagination(){
    	int numOfPages;
    	if( alNftData.size() % Integer.parseInt(list.get(0).toString()) == 0)
    		numOfPages = alNftData.size() / Integer.parseInt(list.get(0).toString());
    	else
    		numOfPages = alNftData.size() / Integer.parseInt(list.get(0).toString())+1;
		pagination.setPageCount(numOfPages);
		pagination.setPageFactory(this::createPage);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	
    	initializeCollectionTable();
    	initializeCmxList();
    	initializePagination();

    }
    
    public void checkRefresh(ActionEvent event)  {
    	
    	boolean isSelected = SaveRefreshBtn.isSelected();
    	Integer sec = Integer.valueOf(RefreshSecInput.getText());
    	
    	try {
    	if(isSelected ){
    		//timerForRefresh = new Timer();
    		taskRefresh = taskFactory.getTask("RefreshTask");
    		timerForRefresh.schedule(taskRefresh, sec*1000, sec*1000);

    	} else {
    		timerForRefresh.cancel();
    		timerForRefresh.purge();
    		}
    }
    
    catch (Exception e)
    	{
    	
    	}
    	
    }
    

	public void checkIfNeedToSendEmail(ActionEvent event)  {
    	boolean isSelected = SaveEmailTimeBtn.isSelected();
    	Integer sec = Integer.valueOf(SendEmailEveryXtimeInput.getText());
    	//TimerTask taskEmails = null;
    	//Timer timerForSendingEmails = new Timer();

    	if(isSelected ){
    		//flagEmails=true;
    		taskEmails = taskFactory.getTask("EmailsTask");
    		timerForSendingEmails = new Timer();
    		timerForSendingEmails.schedule(taskEmails, sec*1000, sec*1000);
    	} else {
    		timerForSendingEmails.cancel();
    		//timerForSendingEmails.purge();
    	}
        
    }
    
    public void checkEmailThreshold()  {
    	boolean isSelected = SaveEmailThersholdBtn.isSelected();

    	if(isSelected ){
    	   emailThreshold = Integer.valueOf(EmailthresholdInput.getText());
    	} else {
    		emailThreshold = 10; // default
    	}
        
    }
    
    public void setShowXentriesCMB(ActionEvent event) throws Exception {
    	int numOfPages;
    	itemPerPage = Integer.parseInt(ShowXentriesCMB.getValue());
    	if(alNftData.size() % itemPerPage == 0)
    		numOfPages = alNftData.size() / itemPerPage;
    	else
    		numOfPages = alNftData.size() / itemPerPage + 1;
		pagination.setPageCount(numOfPages);
    	System.out.println(ShowXentriesCMB.getValue());
    	
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

	    
	    CollectionTable.getItems().clear();	// clear the table before inserting new data from file

        for (int i = 0; i < this.uploadedCollection.size(); i++) { // loop on collection list to get items for display
        	JSONObject currentCollection = ((JSONObject)this.uploadedCollection.get(i));
            String collName = (String)currentCollection.get("Name");
            String opensea = (String)currentCollection.get("Opensea");
            String magiceden = (String)currentCollection.get("Magiceden");
            String diff = (String)currentCollection.get("Diff");
            
            
            CollectionTable.getItems().add(new NftTableData(collName, opensea, magiceden, diff));
            //System.out.println(collName);
        }
    	
        
    }
    
    @FXML
    public void addCollectionFunc(ActionEvent event) throws Exception {
		CollectionTable.getItems().clear();
		lblSearchError.setText("");
		try {
			dataBuilder.addOneCollection(txtAddCollection.getText());
		}catch (Exception e) {
			lblSearchError.setText("Collection doesn't exists!");
		}
		alNftData = dataBuilder.getNftCollection();
		convertNftCollectionListToNftTableDataList();
		for (var nftData : alNftTableData){
			CollectionTable.getItems().add(new NftTableData(nftData.getName(), nftData.getOpenseaSol(), nftData.getMagicEdenSol(),  nftData.getDiff()));
		}
		initializePagination();
	}
    
	
	// Fill the table view with collections from server
	private void fillTableItems()
	{
		dataBuilder = new TableDataBuilder();
		dataBuilder.buildData();
		alNftData = dataBuilder.getNftCollection();
		alNftTableData = new ArrayList<NftTableData>();
	}
	
	private void convertNftCollectionListToNftTableDataList(){
		alNftTableData = new ArrayList<NftTableData>();
		for (var nftData : alNftData) {
			alNftTableData.add(getNftTableDataFromNftCollection(nftData));
		}
	}
	
	private NftTableData getNftTableDataFromNftCollection(NFTCollection nc)
	{
		String magicPrice, openseaPrice, diff; 
		magicPrice = nc.getMagicEdenSol() == -1 ? "N/A" : Long.toString(nc.getMagicEdenSol());
		openseaPrice = nc.getOpenseaSol() == -1 ? "N/A" : Long.toString(nc.getOpenseaSol());
		if(nc.getMagicEdenSol() == -1 || nc.getOpenseaSol() == -1)
			diff = "-";
		else 
			diff = Double.toString(nc.getDiff()); 
		return new NftTableData(nc.getName(), openseaPrice, magicPrice,  diff);
	}
	


	private Node createPage(int pageIndex) {
		from = pageIndex * itemPerPage;
		convertNftCollectionListToNftTableDataList();
		to = Math.min((from + itemPerPage), alNftTableData.size());
		
		CollectionTable.setItems(FXCollections.observableArrayList(alNftTableData.subList(from, to)));
		return CollectionTable;
	}
	
	//*********************************** Factory Design Pattern ***********************************

	

	   public class TaskFactory {
		
		public TimerTask getTask(String taskType){
		      if(taskType == null){
		         return null;
		      }		
		      
		      
		      if(taskType.equalsIgnoreCase("EmailsTask")){
		         return  new EmailsTask();
		         
		      } else if(taskType.equalsIgnoreCase("RefreshTask")){
		         return  new RefreshTask();
		         
		      } 
		      
		      return null;
		   }
	}
	    

	     public  class RefreshTask extends TimerTask implements ITask {
	   
	    	
	        public void run() {

	        		CollectionTable.getItems().clear();
	        		dataBuilder.refreshData();
	        		alNftData = dataBuilder.getNftCollection();
	        		convertNftCollectionListToNftTableDataList();
	        		CollectionTable.setItems(FXCollections.observableArrayList(alNftTableData));
	        		
	        }
	    }
	    
	    public class EmailsTask extends TimerTask {
	    
	        public void run() {
	        	
	        		boolean isSelected = SaveEmailAdressesBtn.isSelected();
	            	if(isSelected ){
	            		String RecipientList[] = EmailsInput.getText().split(";", -1);
	           		 	JavaEmail.sendMailNow(createMessage(), RecipientList);	
	           		 	
	            	}

	        }
	    }
	     


}