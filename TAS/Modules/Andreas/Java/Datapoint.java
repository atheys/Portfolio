
/************************************************
 * Datapoint Class. 
 * Main data container for statistical analysis.
 * @author Andreas Theys
 * @version 1.0
 ************************************************/
public class Datapoint {

	/********************
	 * Class attributes.
	 ********************/
	private double t;
	private String ID;
	private double x;
	private double y;
	private double z;
	private double vx;
	private double vy;
	private double vz;
	
	/***********************
	 * Default constructor.
	 ***********************/
	public Datapoint(){}
	
	/*********************
	 * Copy constructor.
	 * @param d
	 *********************/
	public Datapoint(Datapoint d){
		this.t = d.t;
		this.ID = d.ID;
		this.x = d.x;
		this.y = d.y;
		this.z = d.z;
		this.vx = d.vx;
		this.vy = d.vy;
		this.vz = d.vz;
	}
	
	/**********************
	 * Basic constructor.
	 * @param t
	 * @param ID
	 * @param x
	 * @param y
	 * @param z
	 * @param vx
	 * @param vy
	 * @param vz
	 *********************/
	public Datapoint(double t, String ID, 
			double x, double y, double z, 
			double vx, double vy, double vz){
		this.t = t;
		this.ID = ID;
		this.x = x;
		this.y = y;
		this.z = z;
		this.vx = vx;
		this.vy = vy;
		this.vz = vz;
	}
	
	/**********************
	 * t-getter
	 * @param
	 * @return t	time.
	 **********************/
	public double getT() {
		return t;
	}

	/**********************
	 * t-setter
	 * @param t		time.
	 * @return
	 **********************/
	public void setT(double t) {
		this.t = t;
	}

	/**********************
	 * ID-getter
	 * @param
	 * @return ID	ID.
	 **********************/
	public String getID() {
		return ID;
	}

	/**********************
	 * ID-setter
	 * @param ID	ID.
	 * @return
	 **********************/
	public void setID(String iD) {
		ID = iD;
	}

	/***************************
	 * x-getter
	 * @param
	 * @return x	x-position.
	 ***************************/
	public double getX() {
		return x;
	}

	/***************************
	 * x-setter
	 * @param x		x-position.
	 * @return
	 ***************************/
	public void setX(double x) {
		this.x = x;
	}

	/***************************
	 * y-getter
	 * @param
	 * @return y	y-position.
	 ***************************/
	public double getY() {
		return y;
	}

	/***************************
	 * y-setter
	 * @param y		y-position.
	 * @return
	 ***************************/
	public void setY(double y) {
		this.y = y;
	}
	
	/***************************
	 * z-getter
	 * @param
	 * @return z	z-position.
	 ***************************/
	public double getZ() {
		return z;
	}

	/***************************
	 * z-setter
	 * @param z		z-position.
	 * @return
	 ***************************/
	public void setZ(double z) {
		this.z = z;
	}

	/***************************
	 * vx-getter
	 * @param
	 * @return vx	x-velocity.
	 ***************************/
	public double getVx() {
		return vx;
	}

	/*******************************
	 * vx-setter
	 * @param vx		x-velocity.
	 * @return
	 *******************************/
	public void setVx(double vx) {
		this.vx = vx;
	}

	/***************************
	 * y-getter
	 * @param
	 * @return vy	y-velocity.
	 ***************************/
	public double getVy() {
		return vy;
	}

	/*******************************
	 * vy-setter
	 * @param vy		y-velocity.
	 * @return
	 *******************************/
	public void setVy(double vy) {
		this.vy = vy;
	}

	/***************************
	 * vz-getter
	 * @param
	 * @return vz	z-velocity.
	 ***************************/
	public double getVz() {
		return vz;
	}

	/*******************************
	 * vz-setter
	 * @param vz		z-velocity.
	 * @return
	 *******************************/
	public void setVz(double vz) {
		this.vz = vz;
	}
	
	/*****************************************
	 * Look-ahead method.
	 * @param t			time spec.
	 * @return result 	look-ahead Datapoint.
	 *****************************************/
	public Datapoint lookAhead(double t){
		Datapoint result = new Datapoint(this);
		result.setT(t);
		result.setX(this.x+t*this.vx);
		result.setY(this.y+t*this.vy);
		result.setZ(this.z+t*this.vz);
		return result;
	}
	
}
