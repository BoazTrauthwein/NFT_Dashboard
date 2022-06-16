package application.classes;

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
	
	
}
