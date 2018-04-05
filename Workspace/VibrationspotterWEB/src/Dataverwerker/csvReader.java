package Dataverwerker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class csvReader {
	public ArrayList<Coordinaat> read1(){
		ArrayList<Coordinaat> grafiek1= new ArrayList<Coordinaat>();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = " "; //splitten met spatie
        int j=1; 

        try {
        	
        	//amplitude 
        	String csvFile1 = "A_data.csv"; 
            br = new BufferedReader(new FileReader(csvFile1));
            String[] A_data = null;
            while ((line = br.readLine()) != null) {
            	// enkel zesde lijn van csv lezen
            	if(j==6){
            		A_data = line.split(cvsSplitBy);
            	}
               j++;

            }
            
          //frequentie
            String csvFile2 = "f.csv"; 
            br = new BufferedReader(new FileReader(csvFile2));
            String[] f = null;
            j=1;
            while ((line = br.readLine()) != null) {
            	// enkel zesde lijn van csv lezen
            	if(j==6){
            		f = line.split(cvsSplitBy);
            	}
               j++;

            }
            // datapunten samenstellen
            for(int i=1;i<f.length;i++){
            	double dx=Double.parseDouble(f[i]);
            	double dy=Double.parseDouble(A_data[i]);
            	Coordinaat c=new Coordinaat(dx,dy);
            	grafiek1.add(c);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		return grafiek1;

    }


		
		
	}