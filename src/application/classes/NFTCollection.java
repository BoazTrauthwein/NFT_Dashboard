package application.classes;

import org.json.simple.JSONObject;

// Class for saving an NFT collection data

public class NFTCollection {

	private String name;
	private String symbol;
	private long openseaSol;
	private long magicEdenSol;
	private float diff;
	

	public NFTCollection(String name, String symbol, long openseaSol, long magicEdenSol, float diff) {
		super();
		this.name = name;
		this.symbol = symbol;
		this.openseaSol = openseaSol;
		this.magicEdenSol = magicEdenSol;
		this.diff = diff;
	}	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public long getOpenseaSol() {
		return openseaSol;
	}

	public void setOpenseaSol(long openseaSol) {
		this.openseaSol = openseaSol;
	}

	public long getMagicEdenSol() {
		return magicEdenSol;
	}

	public void setMagicEdenSol(long magicEdenSol) {
		this.magicEdenSol = magicEdenSol;
	}

	public float getDiff() {
		return diff;
	}

	public void setDiff(float diff) {
		this.diff = diff;
	}

	// Uses JSON toString property to get formatted string to put into JSON file
	@Override
	public String toString() {
		return this.getJSONObject().toJSONString(); // Note that this re-arranges order of properties
	}

	// Builds a JSON object out of the Collection (this class)
	public JSONObject getJSONObject()
	{
		JSONObject result = new JSONObject();
		result.put("Name", this.getName());
		result.put("Opensea", this.getOpenseaSol());
		result.put("Magiceden", this.getMagicEdenSol());
		result.put("Diff", this.getDiff());

		return result; // Note that this re-arranges order of properties
	}

}
