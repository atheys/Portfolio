/******************************
 * Class ID (for intrusions).
 * @author Andreas Theys
 * @version 1.0
 ******************************/
public class ID{

	/********************
	 * Class attributes.
	 ********************/
	public double t;
	public String ID1;
	public String ID2;
	public double sever;
	
	/***********************
	 * Default constructor.
	 ***********************/
	public ID(){}
	
	/********************
	 * Copy constructor.
	 ********************/
	public ID(ID id){
		this.t = id.t;
		this.ID1 = id.ID1;
		this.ID2 = id.ID2;
		this.sever = id.sever;
	}
	
	/*********************
	 * Basic constructor.
	 *********************/
	public ID(double t, String ID1, String ID2, double sever){
		this.t = t;
		this.ID1 = ID1;
		this.ID2 = ID2;
		this.sever = sever;
	}
	
	/******************************
	 * equals-method
	 * @param id
	 * @return eval	boolean value
	 ******************************/
	public boolean equals(Object obj){
		if (obj instanceof ID){
			ID id = (ID) obj;
			boolean eval1 = this.ID1.equals(id.ID1) && this.ID2.equals(id.ID2);
			boolean eval2 = this.ID1.equals(id.ID2) && this.ID2.equals(id.ID1);
			return eval1||eval2;
		}
		return false;
	}
	
	/******************************
	 * update-method
	 * @param id
	 * @return eval	boolean value
	 ******************************/
	public void update(ID id){
		if(this.sever<id.sever){
			this.t = id.t;
			this.sever = id.sever;
		}
	}
	
}
