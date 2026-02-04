import java.util.*;
import java.io.*;
import java.util.Random;


public class PO_KMeans{
    static final int M = 13;  //number of teams
    static final int MAX_ITERS = 300; //max iterations of k-means
    static final int RUNS = 50;
    static final double THRESHOLD = 1e-6;

    static List<DataPoint> data = new ArrayList<>();
    static int N; //ammount of data

    static double[][] centers; //M x 2 (x1,x2)
    static int[] assignment;   // assignment[i] = team of point i

    static void loadData(String filename){ //DIAVAZEI TO datatset_O.csv,dimiourgei kai apothikevei Datapoint sthn lisa data
        data.clear();

        try{
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = br.readLine(); //readheader

            while((line = br.readLine()) != null){
                String[] parts = line.split(",");
                if(parts.length < 3) continue;

                double x1 = Double.parseDouble(parts[0].trim());
                double x2 = Double.parseDouble(parts[1].trim());
                int label = Integer.parseInt(parts[2].trim());
                data.add(new DataPoint(x1, x2, label));
            }

            br.close();
        }catch(Exception e){
            System.out.println("Error loading data: " + e.getMessage());
        }
        N = data.size();
        System.out.println("Loaded " + N + " points.");
    }

    static void initRandomCenters(){ //EPILEGEI TIHAIA M DIAFORETIKA SIMEIA WS ARXIKA KENDRA
        centers = new double[M][2]; 
        assignment = new int[N];   //for every point, the cluster it belongs

        Random rnd = new Random();

        //To mark which points have already been chosen as centers
        boolean[] used = new boolean[N];

        int count = 0;
        while(count<M){
            int idx = rnd.nextInt(N); //random index 0..N-1

            if(used[idx]){
                //already used as center
                continue;
            }
            used[idx] = true;

            DataPoint p = data.get(idx);

            //assign center to coordinates of point
            centers[count][0] = p.x1;
            centers[count][1] = p.x2;
            count++;
        }
    }

    static void assignPoints(){ //ANATHESI SIMEIWN SE OMADES
    //GIA KATHE SIMEIO IPOLOGIZETAI H EFKLIDIA APOSTASH APOKATHE KEDRO
	//KAI TO SIMEIO ANATITHETAI STHN OMADA ME TO PLISIESTERO KEDRO
        //for every point i
        for(int i=0; i<N; i++){
            DataPoint p = data.get(i);
            double x1 = p.x1;
            double x2 = p.x2;

            //initialize with distance from first center
            double bestDist = Double.MAX_VALUE;
            int bestCluster = 0;

            //check all centers 0..M-1
            for(int k=0; k<M; k++){
                double cx = centers[k][0];
                double cy = centers[k][1];

                double dx = x1 - cx;
                double dy = x2 - cy;
                double dist2 = dx*dx + dy*dy;

                if(dist2<bestDist){
                    bestDist = dist2;
                    bestCluster = k;
                } 
            }
            //assign point i to closest center
            assignment[i] = bestCluster;
        }
    }

    static void updateCenters(){ //IPOLOGIZEI TO NEO KEDRO KATHE OMADAS WS MESO ORO TWN SIMEIWN TOU
        double[] sumX1 = new double[M];
        double[] sumX2 = new double[M];
        int[] count = new int[M];

        //Calculating sums
        for(int i=0; i<N; i++){
            int k = assignment[i];
            DataPoint p = data.get(i);

            sumX1[k] += p.x1;
            sumX2[k] += p.x2;
            count[k]++;
        }

        Random rnd = new Random();

        //Calculating new centers
        for(int k=0; k<M; k++){
            if(count[k]>0){
                //calculating average
                centers[k][0] = sumX1[k] / count[k];
                centers[k][1] = sumX2[k] / count[k];
            }else{
                //AN OMADA EINAI ADEIA BAZEI TO KEDRO SE TIHAIO SIMEIO
                int idx = rnd.nextInt(N);
                DataPoint p = data.get(idx);
                centers[k][0] = p.x1;
                centers[k][1] = p.x2;
            }
        }
    }

    static double computeError(){
		//IPOLOGIZEI TO SINOLIKO SFALMA
        double sum = 0.0;

        for(int i=0; i<N; i++){
            DataPoint p = data.get(i);
            int k = assignment[i];

            double cx = centers[k][0];
            double cy = centers[k][1];

            double dx = p.x1 - cx;
            double dy = p.x2 - cy;

            double dist2 = dx*dx + dy*dy; //||xi - μk||^2
            sum += dist2;
        }
        return sum;
    }

    static double runKMeans(){
        initRandomCenters(); //arxikopoiei tihaia ta M kedra

        double[][] oldCenters = new double[M][2];

        for(int iter=0; iter<MAX_ITERS; iter++){
            //krataw ta palia kedra giana dw meta to updateCenters poso metakinithikan
            for(int k=0; k<M; k++){
                oldCenters[k][0] = centers[k][0];
                oldCenters[k][1] = centers[k][1];
            }
            
            assignPoints();
            updateCenters();

            //H MEGISTH METAKINHSH ENOS KEDROU
            double maxShift = 0.0;

            for(int k=0; k<M; k++){
                double dx = centers[k][0] - oldCenters[k][0];
                double dy = centers[k][1] - oldCenters[k][1];
                double dist = Math.sqrt(dx*dx + dy*dy);
                
                if(dist>maxShift){
                    maxShift = dist;
                }
            }
			//ELEGXOS SIGKLISIS ALGORITHMOU
            if(maxShift<THRESHOLD){
                System.out.println("Converged in " + iter + " iterarions");
                break;
            }
        }

        //IPOLOGIZETAI TO TELIKO SFALMA
        double error = computeError();
        
        return error;
    }
    
    static void multiRunAndSaveBest(String saveFile){
		//EKTELEI THN METHODO KMEANS 50 FORES KAI KRATA TO ELAXISTON SINOLIKO SFALMA APTA 50RUNS
        double bestError = Double.MAX_VALUE;
        double[][] bestCenters = new double[M][2]; //saving best solution

        for(int r=1; r<=RUNS; r++){
            double err = runKMeans();

            System.out.printf("Run %2d: error = %.6f\n", r, err);

            if(err<bestError){
                bestError = err;
                
                //APOTHIKEVEI TA ADISTOIXA KEDRA SE ARXEIO
                for(int k=0; k<M; k++){
                    bestCenters[k][0] = centers[k][0];
                    bestCenters[k][1] = centers[k][1];
                }
            }
        }

        System.out.println("Best error = " + bestError);
        
        //save best centers
        try{
            PrintWriter out = new PrintWriter(saveFile);
            out.println("center_x,center_y");

            for(int k=0; k<M; k++){
                out.println(bestCenters[k][0] + "," + bestCenters[k][1]);
            }

            out.println("Error = " + bestError);
            out.close();
            System.out.println("Saved best centers to: " + saveFile);
        
        }catch(Exception e){
            System.out.println("Error saving centers: " + e.getMessage());
        }
    }

    public static void main(String[] args){
        loadData("dataset_O.csv");
        multiRunAndSaveBest("kmeans_best_centers_Μ13.csv");
        System.out.println("Finished");
    }
}