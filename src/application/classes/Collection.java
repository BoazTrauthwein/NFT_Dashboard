package application.classes;

public class Collection {

	private String name;
	private float openseaSol;
	private float magicEdenSol;
	private float diff;
	
	public Collection(String name, float openseaSol, float magicEdenSol, float diff) {
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

	public float getOpenseaSol() {
		return openseaSol;
	}

	public void setOpenseaSol(float openseaSol) {
		this.openseaSol = openseaSol;
	}

	public float getMagicEdenSol() {
		return magicEdenSol;
	}

	public void setMagicEdenSol(float magicEdenSol) {
		this.magicEdenSol = magicEdenSol;
	}

	public float getDiff() {
		return diff;
	}

	public void setDiff(float diff) {
		this.diff = diff;
	}
	
	
	
}
