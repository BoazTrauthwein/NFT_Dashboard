package application.classes;

import org.json.simple.JSONObject;

// Class for saving an NFT collection data

public class Collection {

	private String name;
	private long openseaSol;
	private long magicEdenSol;
	private float diff;
	
	public Collection(String name, long openseaSol, long magicEdenSol, float diff) {
		super();
		this.name = name;
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
