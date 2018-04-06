package Dataverwerker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class csvWriter {
	
	public void write(){
         BufferedReader br = null;
         String line = "";
         String cvsSplitBy = ","; //splitten met komma

         try {
         	
         	String csvFile1 = "A_data.txt"; //amplitude 
             br = new BufferedReader(new FileReader(csvFile1));
             String[] A_data = null;
             while ((line = br.readLine()) != null) {
             		A_data = line.split(cvsSplitBy);
             }

             String csvFile2 = "f.txt"; //frequentie
             br = new BufferedReader(new FileReader(csvFile2));
             String[] f = null;
             while ((line = br.readLine()) != null) {
             		f = line.split(cvsSplitBy);
             }
             
             String csvFile3 = "data_resampled.txt"; //versnelling
             br = new BufferedReader(new FileReader(csvFile3));
             String[] data_resampled = null;
             while ((line = br.readLine()) != null) {
             		data_resampled = line.split(cvsSplitBy);
             }
             String csvFile4 = "t_resampled.txt"; //tijd
             br = new BufferedReader(new FileReader(csvFile4));
             String[] t_resampled = null;
             while ((line = br.readLine()) != null) {
             		t_resampled = line.split(cvsSplitBy);
             }
             
            //frequentie en amplitude printen naar csv
     		FileWriter fw = new FileWriter("output.csv");
     		PrintWriter pw = new PrintWriter(fw);
     		pw.println((f.length)+";"+(A_data.length));
     		for(int i=0;i<f.length;i++){
     			pw.println(f[i]+";"+A_data[i]);
     		}

     		pw.close();
     		
     		// tijd en versnelling printen naar csv
     		FileWriter fw2 = new FileWriter("output2.csv");
     		PrintWriter pw2 = new PrintWriter(fw2);
     		pw2.println((t_resampled.length)+";"+(data_resampled.length));
     		for(int i=0;i<t_resampled.length;i++){
     			pw2.println(t_resampled[i]+";"+data_resampled[i]);
     		}

     		pw2.close();
             
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
	}

}
