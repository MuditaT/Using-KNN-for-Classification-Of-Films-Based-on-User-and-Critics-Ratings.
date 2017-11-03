import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.util.List;



import com.datastax.driver.core.ResultSet;
public class NearestNeighbour{

	private static Cluster cluster;
	private static Session session;

	public static Cluster connect(String node){
		
		return Cluster.builder().addContactPoint(node).build();
	}
	
	public static void main(String[] args){
		ArrayList<NearestNeighbour.DataEntry> data = new ArrayList<NearestNeighbour.DataEntry>();

		cluster = connect("localhost");
		session = cluster.connect();
		System.out.println("Connected");
		session.execute("USE hello");
		ResultSet results =  session.execute("SELECT * FROM film");
		for (Row row : results){
			data.add(new DataEntry(new double[]{(row.getDouble("usrrating")),(row.getDouble("ctsrating"))}, row.getString("result")));
		}
			

		
		NearestNeighbour nn4=null;
		for(int i=1;i<41;i++){
			nn4 = new NearestNeighbour(data, i);
			//System.out.println("Classified as: "+nn4.classify(new DataEntry(new double[]{170, 60},"Ignore")));
		}
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter User Rating Out off 100/: ");
		double i = scan.nextDouble();
		System.out.println("Enter Critics Rating out of 100/");
		double j = scan.nextDouble();
		
		System.out.println("Classified as: "+nn4.classify(new DataEntry(new double[]{j, i},"Ignore")));
		cluster.close();
	
	}
	
	
	private int k;
	private ArrayList<Object> classes;
	private ArrayList<DataEntry> dataSet;
	/**
	 * 
	 * @param dataSet The set
	 * @param k The number of neighbours to use
	 */
	public NearestNeighbour(ArrayList<DataEntry> dataSet, int k){
		this.classes = new ArrayList<Object>();
		this.k = k;
		this.dataSet = dataSet;
		
		//Load different classes
		for(DataEntry entry : dataSet){
			if(!classes.contains(entry.getY())) classes.add(entry.getY());
		}
	}
	
	private DataEntry[] getNearestNeighbourType(DataEntry x){
		DataEntry[] retur = new DataEntry[this.k];
		double fjernest = Double.MIN_VALUE;
		int index = 0;
		for(DataEntry tse : this.dataSet){
			double distance = distance(x,tse);
			if(retur[retur.length-1] == null){ 
				int j = 0;
				while(j < retur.length){
					if(retur[j] == null){
						retur[j] = tse; break;
					}
					j++;
				}
				if(distance > fjernest){
					index = j;
					fjernest = distance;
				}
			}
			else{
				if(distance < fjernest){
					retur[index] = tse;
					double f = 0.0;
					int ind = 0;
					for(int j = 0; j < retur.length; j++){
						double dt = distance(retur[j],x);
						if(dt > f){
							f = dt;
							ind = j;
						}
					}
					fjernest = f;
					index = ind;
				}
			}
		}
		return retur;
	}
	
	private static double convertDistance(double d){
		return 1.0/d;
	}

	/**
	 * Computes Euclidean distance
	 * @param a From
	 * @param b To
	 * @return Distance 
	 */
	public static double distance(DataEntry a, DataEntry b){
		double distance = 0.0;
		int length = a.getX().length;
		for(int i = 0; i < length; i++){
			double t = a.getX()[i]-b.getX()[i];
			distance = distance+t*t;
		}
		return Math.sqrt(distance);
	}
	/**
	 * 
	 * @param e Entry to be classifies
	 * @return The class of the most probable class
	 */
	public Object classify(DataEntry e){
		HashMap<Object,Double> classcount = new HashMap<Object,Double>();
		DataEntry[] de = this.getNearestNeighbourType(e);
		for(int i = 0; i < de.length; i++){
			double distance = NearestNeighbour.convertDistance(NearestNeighbour.distance(de[i], e));
			if(!classcount.containsKey(de[i].getY())){
				classcount.put(de[i].getY(), distance);
			}
			else{
				classcount.put(de[i].getY(), classcount.get(de[i].getY())+distance);
			}
		}
		//Find right choice
		Object o = null;
		double max = 0;
		for(Object ob : classcount.keySet()){
			if(classcount.get(ob) > max){
				max = classcount.get(ob);
				o = ob;
			}
		}
		
		return o;
	}

public static class DataEntry{
	private double[] x;
	private Object y;
	
	public DataEntry(double[] x, Object y){
		this.x = x;
		this.y = y;
	}
	
		public double[] getX(){
			return this.x;
		}
	
		public Object getY(){
			return this.y;
		}
	}
}	
