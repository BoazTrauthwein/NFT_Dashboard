package application.classes;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import application.interfaces.IBuilder;

public class TableDataBuilder implements IBuilder{
	
	private ArrayList<NFTCollection> alNftCollections;
	private NftHttpRequest request;
	JSONObject objSymbolMagiceden;
	
	public TableDataBuilder() {
		request = NftHttpRequest.getInstance();
		alNftCollections = new ArrayList<NFTCollection>();
	}
	
	public ArrayList<NFTCollection> getNftCollection(){ return alNftCollections;	}
	
	public void buildData()
	{
		String collName, collSymbol;
		long floorPriceMagiceden, floorPriceOpensea;
		float calc;
		
		JSONArray arrCollection = getJSONArray("https://api-mainnet.magiceden.dev/v2/collections?offset=0&limit=10");

        for (int i = 0; i < arrCollection.size(); i++) {
            //collName = (String)((JSONObject)arrCollection.get(i)).get("name");
            collSymbol = (String)((JSONObject)arrCollection.get(i)).get("symbol");
            
            getMagicEdenData("https://api-mainnet.magiceden.dev/v2/collections/" + collSymbol);
            collName = getMagicedenColectionName();
            floorPriceMagiceden = getMagicedenFloorPrice();
    		floorPriceOpensea = getOpenseaFloorPrice("https://api.opensea.io/api/v1/collection/" + collSymbol.replace('_', '-') + "/stats", collSymbol);
    		
    		if(floorPriceMagiceden!=-1&&floorPriceOpensea!=-1)
    			calc = 100 - (100*((float)floorPriceMagiceden/floorPriceOpensea));
    		else
    			calc = -1;
    		alNftCollections.add(new NFTCollection(collName, collSymbol, floorPriceOpensea, floorPriceMagiceden,  calc));
            
            System.out.println((i + 1) + ") -----------------------------------------------------------------------------------");
            //sleep(500);
        }
	}
	
	public void addOneCollection(String collSymbol)
	{
		getMagicEdenData("https://api-mainnet.magiceden.dev/v2/collections/" + collSymbol);
		String collName = getMagicedenColectionName();
		long floorPriceMagiceden = getMagicedenFloorPrice();
		long floorPriceOpensea = getOpenseaFloorPrice("https://api.opensea.io/api/v1/collection/" + collSymbol.replace('_', '-') + "/stats", collSymbol);
		
		float calc = 100 - (100*((float)floorPriceMagiceden/floorPriceOpensea));
		alNftCollections.add(0, new NFTCollection(collName, collSymbol, floorPriceOpensea, floorPriceMagiceden,  calc));
	}
	
	public void refreshData()
	{
		ArrayList<String> alNftSymbols = new ArrayList<String>();
		System.out.println("-------- Update --------");
		for (var nftData : alNftCollections){
			alNftSymbols.add(nftData.getSymbol());
		}
		alNftCollections = new ArrayList<NFTCollection>();
//		for (var collSymbol : alNftSymbols){
		for(int i = alNftSymbols.size()-1; i>=0; --i) {
			addOneCollection(alNftSymbols.get(i));
		}
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
    
    private void getMagicEdenData(String urlPath)
    {
    	System.out.println(urlPath);
        objSymbolMagiceden = getJSONObject(urlPath);
    }
	
	private long getMagicedenFloorPrice()
	{
        long floorPrice = 0;
        try {
        	floorPrice = (long)objSymbolMagiceden.get("floorPrice"); 
		} catch (Exception e) {
			floorPrice = -1;
		}
        return floorPrice;
	}
	
	private String getMagicedenColectionName()
	{
		return (String)objSymbolMagiceden.get("name");
	}
	
	private long getOpenseaFloorPrice(String urlPath, String collSymbol)
	{
        System.out.println(urlPath);
        long floorPrice = 0;
        try {
        	JSONObject joValue = getJSONObject("https://api.opensea.io/api/v1/collection/" + collSymbol.replace('_', '-') + "/stats");
    		JSONObject joStats = (JSONObject)joValue.get("stats");
    		double dPrice = (double)joStats.get("floor_price");
    		floorPrice = (long)(dPrice * 100000000000L);
		} catch (Exception e) {
			floorPrice = -1;
		}
        return floorPrice;
	}
	
}
