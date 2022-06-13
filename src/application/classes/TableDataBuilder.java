package application.classes;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class TableDataBuilder {

	private ArrayList<NFTCollection> alNftCollections;
	private NftHttpRequest request;
	
	public TableDataBuilder() {
		request = new NftHttpRequest();
		alNftCollections = new ArrayList<NFTCollection>();
	}
	
	public ArrayList<NFTCollection> getNftCollection(){ return alNftCollections;	}
	
	public void buildData()
	{
		String collName, collSymbol;
		long floorPriceMagiceden, floorPriceOpensea;
		
		JSONArray arrCollection = getJSONArray("https://api-mainnet.magiceden.dev/v2/collections?offset=0&limit=10");

        for (int i = 0; i < arrCollection.size(); i++) {
            collName = (String)((JSONObject)arrCollection.get(i)).get("name");
            collSymbol = (String)((JSONObject)arrCollection.get(i)).get("symbol");
            
            floorPriceMagiceden = getMagicedenFloorPrice("https://api-mainnet.magiceden.dev/v2/collections/" + collSymbol);
    		floorPriceOpensea = getOpenseaFloorPrice("https://api.opensea.io/api/v1/collection/" + collSymbol.replace('_', '-') + "/stats", collSymbol);
    		
    		alNftCollections.add(new NFTCollection(collName, floorPriceOpensea, floorPriceMagiceden,  0));
            
            System.out.println("-----------------------------------------------------------------------------------");
            //sleep(500);
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
    		floorPrice = (long)(dPrice * 1000000000);
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
	
}
