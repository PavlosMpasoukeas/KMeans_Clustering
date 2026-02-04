import java.util.*;
import java.io.*;

public class DatasetO{
	public static void main(String[] args){
		Random rnd  = new Random(1234); //stable seed
		List<DataPoint> data = new ArrayList<>(); //LISTA ME SIMEIA 
		
		//Cluster 1
		for(int i = 0; i<150; i++){
			double x1 = 0.75 + rnd.nextDouble() * (1.25 - 0.75);//einai tipos wste na paraxthei omoiomorfi katanomi se opoiodhpote diasthma [a,b]
			double x2 = 0.75 + rnd.nextDouble() * (1.25 - 0.75); //TO nextDouble einai pou epilegei tixaia times apto [0,1]
			data.add(new DataPoint(x1, x2, 1)); //label = 1 for cluster 1
		}
		
		//Cluster 2
		for(int i = 0; i<150; i++){
			double x1 = 0.0 + rnd.nextDouble() * (0.5 - 0.0);
			double x2 = 0.0 + rnd.nextDouble() * (0.5 - 0.0);
			data.add(new DataPoint(x1, x2, 2));
		}
		
		//Cluster 3
		for(int i = 0; i<150; i++){
			double x1 = 0.0 + rnd.nextDouble() * (0.5 - 0.0);
			double x2 = 1.5 + rnd.nextDouble() * (2.0 - 1.5);
			data.add(new DataPoint(x1, x2, 3));
		}
		
		//Cluster 4
		for(int i = 0; i<150; i++){
			double x1 = 1.5 + rnd.nextDouble() * (2.0 - 1.5);
			double x2 = 0.0 + rnd.nextDouble() * (0.5 - 0.0);
			data.add(new DataPoint(x1, x2, 4));
		}
		
		//Cluster 5
		for(int i = 0; i<150; i++){
			double x1 = 1.5 + rnd.nextDouble() * (2.0 - 1.5);
			double x2 = 1.5 + rnd.nextDouble() * (2.0 - 1.5);
			data.add(new DataPoint(x1, x2, 5));
		}
		
		//Cluster 6
		for(int i = 0; i<75; i++){
			double x1 = 0.6 + rnd.nextDouble() * (0.8 - 0.6);
			double x2 = 0.0 + rnd.nextDouble() * (0.4 - 0.0);
			data.add(new DataPoint(x1, x2, 6));
		}
		
		//Cluster 7
		for(int i = 0; i<75; i++){
			double x1 = 0.6 + rnd.nextDouble() * (0.8 - 0.6);
			double x2 = 1.6 + rnd.nextDouble() * (2.0 - 1.6);
			data.add(new DataPoint(x1, x2, 7));
		}
		
		//Cluster 8
		for(int i = 0; i<75; i++){
			double x1 = 1.2 + rnd.nextDouble() * (1.4 - 1.2);
			double x2 = 0.0 + rnd.nextDouble() * (0.4 - 0.0);
			data.add(new DataPoint(x1, x2, 8));
		}
		
		//Cluster 9
		for(int i = 0; i<75; i++){
			double x1 = 1.2 + rnd.nextDouble() * (1.4 - 1.2);
			double x2 = 1.6 + rnd.nextDouble() * (2.0 - 1.6);
			data.add(new DataPoint(x1, x2, 9));
		}
		
		//Cluster 10
		for(int i = 0; i<150; i++){
			double x1 = 0.0 + rnd.nextDouble() * (2.0 - 0.0);
			double x2 = 0.0 + rnd.nextDouble() * (2.0 - 0.0);
			data.add(new DataPoint(x1, x2, 10));
		}
		
		try{
			PrintWriter out = new PrintWriter("dataset_O.csv");
			out.println("x1,x2,label");
			
			for(DataPoint p : data){
				out.println(p.x1 + "," + p.x2 + "," + p.label);
			}
			out.close();
			
			System.out.println("File dataset_O.csv has been created. Ammount of points: " + data.size());
		} catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}
	}
}	