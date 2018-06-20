import java.util.ArrayList;

/*************************
 * Class Combination
 * @author Andreas Theys
 * @version 1.0
 *************************/
public class Combination {

	/*********************
	 * Class attributes.
	 *********************/
	private Datapoint d1;
	private Datapoint d2;
	public static final double vMargin = 50.0;
	public static final double hMargin = 250.0;
	
	/***********************
	 * Default constructor.
	 ***********************/
	public Combination(){}
	
	/**********************************
	 * Basic constructor.
	 * @param d1	first Datapoint.
	 * @param d2	second Datapoint.
	 **********************************/
	public Combination(Datapoint d1, Datapoint d2){
		this.d1 = d1;
		this.d2 = d2;
	}
	
	/********************************
	 * First Datapoint getter.
	 * @param
	 * @return d1	first Datapoint.
	 ********************************/
	public Datapoint getD1() {
		return d1;
	}

	/********************************
	 * First Datapoint setter.
	 * @param d1	first Datapoint.
	 * @return
	 ********************************/
	public void setD1(Datapoint d1) {
		this.d1 = d1;
	}

	/********************************
	 * Second Datapoint getter.
	 * @param
	 * @return d2	second Datapoint.
	 ********************************/
	public Datapoint getD2() {
		return d2;
	}

	/********************************
	 * Second Datapoint setter.
	 * @param d2	second Datapoint.
	 * @return
	 ********************************/
	public void setD2(Datapoint d2) {
		this.d2 = d2;
	}
	
	/******************************************
	 * Determines relative position.
	 * @param
	 * @return drel	relative position vector.
	 ******************************************/
	public double[] rDistance(){
		double[] drel = {d2.getX()-d1.getX(),d2.getY()-d1.getY(),d2.getZ()-d1.getZ()};
		return drel;
	}
	
	/******************************************
	 * Determines relative velocity.
	 * @param
	 * @return vrel	relative velocity vector. 
	 ******************************************/
	public double[] rVelocity(){
		double[] vrel = {d2.getVx()-d1.getVx(),d2.getVy()-d1.getVy(),d2.getVz()-d1.getVz()};
		return vrel;
	}

	/****************************************************
	 * Computes precise time step for certain precision.
	 * @param precision		degree of precision.
	 * @return incr			time increment.
	 ****************************************************/
	public double increment(double precision){
		double[] vrel = this.rVelocity();
		double vert = Math.abs(vrel[0]);
		double hor = Math.sqrt(vrel[1]*vrel[1]+vrel[2]*vrel[2]);
		double incr = precision*Math.min(this.vMargin/(2*vert), this.hMargin/(2*hor));
		return incr;
	}
	
	/**************************************************
	 * Determines relative position at given time t.
	 * @param t			time specification.
	 * @return result 	relative position vector at t.
	 **************************************************/
	public double[] position(double t){
		double[] drel = this.rDistance();
		double[] vrel = this.rVelocity();
		double[] result = {drel[0]+t*vrel[0],drel[1]+t*vrel[1],drel[2]+t*vrel[2]};
		return result;
	}
	
	/**********************************************
	 * Computes severity at time t.
	 * @param t
	 * @return sever 	severity number at time t.
	 **********************************************/
	public double severity(double t){
		double[] pos = this.position(t);
		double sever = 0;
			if(	(pos[0]<vMargin)&&(Math.sqrt(pos[1]*pos[1]+pos[2]*pos[2])<hMargin)){
				sever = Math.min(1-(Math.abs(pos[0])/vMargin),1-(Math.sqrt(pos[1]*pos[1]+pos[2]*pos[2])/hMargin));
				return sever;
			}
		return sever;
	}
	
	/********************************************************
	 * Combination generator from list of Datapoint objects.
	 * @param points	list of Datapoint objects.
	 * @return result	list of Datapoint combinations.
	 ********************************************************/
	public static ArrayList<Combination> generate(ArrayList<Datapoint> points){
		ArrayList<Combination> result = new ArrayList<Combination>();
		for(int i=0; i<points.size(); i++){
			for(int j=i+1; j<points.size(); j++){
				result.add(new Combination(points.get(i),points.get(j)));
			}
		}
		return result;
	}
	
}
	