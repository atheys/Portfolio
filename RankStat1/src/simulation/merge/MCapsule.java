package simulation.merge;

import simulation.analyze.selection.Capsule;

public class MCapsule extends Capsule {

	public String sn;
	
	public MCapsule(Capsule c, String sn){
		super(c);
		this.sn = sn;
	}
	
}
