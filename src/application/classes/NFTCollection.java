package application.classes;

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





}
