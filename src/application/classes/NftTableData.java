package application.classes;

import org.json.simple.JSONObject;

public class NftTableData {
	private String name;
	private String openseaSol;
	private String magicEdenSol;
	private String diff;
	public NftTableData(String name, String openseaSol, String magicEdenSol, String diff) {
		super();
		this.name = name;
		this.openseaSol = openseaSol;
		this.magicEdenSol = magicEdenSol;
		this.diff = diff;
	}

	public String getName() {
		return name;
	}
	public String getOpenseaSol() {
		return openseaSol;
	}
	public String getMagicEdenSol() {
		return magicEdenSol;
	}
	public String getDiff() {
		return diff;
	}
	
	// Uses JSON toString property to get formatted string to put into JSON file
	@Override
	public String toString() {
		return this.getJSONObject().toJSONString(); // Note that this re-arranges order of properties
	}
	
	// Builds a JSON object out of the Collection (this class)
	@SuppressWarnings("unchecked")
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
