package application.classes;

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

	@Override
	public String toString() {
		return "Collection [name=" + name + ", openseaSol=" + openseaSol + ", magicEdenSol=" + magicEdenSol + ", diff="
				+ diff + "]";
	}
	
	
	
	
	
}
