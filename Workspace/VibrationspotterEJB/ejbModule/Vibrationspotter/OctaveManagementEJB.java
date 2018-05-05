package Vibrationspotter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Interface to handle the octave file for data processing
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
@Named
@Stateless
public class OctaveManagementEJB implements OctaveManagementEJBLocal {

	@PersistenceContext(unitName = "demodb")
	private EntityManager em;

	@EJB
	private OctaveManagementEJBLocal octaveEJB;

	@Resource
	private SessionContext ctx;

	public OctaveManagementEJB() {
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Vibrationspotter.OctaveManagementEJBLocal#createdata(java.lang.String)
	 */
	public String[] createdata(String s) {

		String s1 = null;
		String s2 = null;

		byte[] by;
		ByteArrayOutputStream bOutput = new ByteArrayOutputStream();

		String[] resultaten = new String[2];
		try {
			String lijnen[] = s.split("\\r?\\n");
			StringBuilder sb1 = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();
			StringBuilder sb3 = new StringBuilder();
			StringBuilder sb4 = new StringBuilder();
			for (int i = 0; i < lijnen.length; i++) {
				String getallen[] = lijnen[i].split(",");
				String t = getallen[0];
				String x = getallen[1];
				String y = getallen[2];
				String z = getallen[3];

				sb1.append(t + " ");
				sb2.append(x + " ");
				sb3.append(y + " ");
				sb4.append(z + " ");
			}
			String tijd = sb1.toString();
			String xWaarde = sb2.toString();
			String yWaarde = sb3.toString();
			String zWaarde = sb4.toString();

			String[] command = { "octave-cli", };
			Process p = Runtime.getRuntime().exec(command);
			SyncPipe sync = new SyncPipe(p.getInputStream(), System.out);
			new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
			// new Thread(new SyncPipe(p.getInputStream(), bOutput)).start();
			new Thread(sync).start();
			PrintWriter stdin = new PrintWriter(p.getOutputStream());
			InputStreamReader stdout = new InputStreamReader(p.getInputStream());

			// Load package
			stdin.println("pkg load signal");
			// ----------------------------------------x
			// waarde----------------------------------------
			// Generate data
			stdin.println("t = [" + tijd + "];");
			stdin.println("a = -1; b = 1;");
			stdin.println("t = t + (a + (b-a).*rand(1,length(t))).*1e-3;");
			stdin.println("f1 = 2; f2 = 8; % two frequencies within the signal");
			stdin.println("data = [" + xWaarde + "];");

			// Stap1
			stdin.println("Fs = 100.; % desired (fixed) sample rate");
			stdin.println("t_resampled = t(1):1/Fs:t(end);");
			stdin.println("data_resampled = interp1(t, data, t_resampled, 'spline');");
			stdin.println("t_resampled = t_resampled - t_resampled(1); ");

			// tijd en versnelling bijhouden
			stdin.println("tijd=t_resampled");
			stdin.println("versnelling=data_resampled");

			// Stap2
			stdin.println("L = length(data_resampled);");
			stdin.println("f = Fs*(0:(L/2))/L;");
			stdin.println("A2_data = fft(data_resampled); A2 = abs(A2_data/L);");
			stdin.println("A_data = A2(1:L/2+1); A_data(2:end-1) = 2*A_data(2:end-1);");

			// frequentie en amplitude bijhouden
			stdin.println("frequentie=f");
			stdin.println("amplitude=A_data");

			// Stap3
			stdin.println("f1 = 1/Fs*2; f2 = 4/Fs*2;");
			stdin.println("filter_order = 4;");
			stdin.println("[b,a] = butter(filter_order,[f1 f2]);");
			stdin.println("data_filtered = filtfilt(b,a,data_resampled);");
			stdin.println("A2_data = fft(data_filtered); A2 = abs(A2_data/L);");
			stdin.println("A_data = A2(1:L/2+1); A_data(2:end-1) = 2*A_data(2:end-1);");

			// 2 output files uitprinten
			/*
			 * stdin.println("x1 = [tijd(:),versnelling(:),data_filtered(:)];");
			 * stdin.println("csvwrite ('x1.txt', x1)");
			 * 
			 * stdin.println("x2 = [frequentie(:),amplitude(:),A_data(:)];");
			 * stdin.println("csvwrite ('x2.txt', x2)");
			 */
			stdin.println(
					"versnellingx=versnelling; data_filteredx=data_filtered; frequentiex=frequentie; amplitudex=amplitude; A_datax=A_data; ");
			// ----------------------------------------y
			// waarde----------------------------------------

			// Generate data
			stdin.println("t = [" + tijd + "];");
			stdin.println("a = -1; b = 1;");
			stdin.println("t = t + (a + (b-a).*rand(1,length(t))).*1e-3;");
			stdin.println("f1 = 2; f2 = 8; % two frequencies within the signal");
			stdin.println("data = [" + yWaarde + "];");

			// Stap1
			stdin.println("Fs = 100.; % desired (fixed) sample rate");
			stdin.println("t_resampled = t(1):1/Fs:t(end);");
			stdin.println("data_resampled = interp1(t, data, t_resampled, 'spline');");
			stdin.println("t_resampled = t_resampled - t_resampled(1); ");

			// tijd en versnelling bijhouden
			stdin.println("tijd=t_resampled");
			stdin.println("versnelling=data_resampled");

			// Stap2
			stdin.println("L = length(data_resampled);");
			stdin.println("f = Fs*(0:(L/2))/L;");
			stdin.println("A2_data = fft(data_resampled); A2 = abs(A2_data/L);");
			stdin.println("A_data = A2(1:L/2+1); A_data(2:end-1) = 2*A_data(2:end-1);");

			// frequentie en amplitude bijhouden
			stdin.println("frequentie=f;");
			stdin.println("amplitude=A_data;");

			// Stap3
			stdin.println("f1 = 1/Fs*2; f2 = 4/Fs*2;");
			stdin.println("filter_order = 4;");
			stdin.println("[b,a] = butter(filter_order,[f1 f2]);");
			stdin.println("data_filtered = filtfilt(b,a,data_resampled);");
			stdin.println("A2_data = fft(data_filtered); A2 = abs(A2_data/L);");
			stdin.println("A_data = A2(1:L/2+1); A_data(2:end-1) = 2*A_data(2:end-1);");

			// 2 output files uitprinten
			/*
			 * stdin.println("x1 = [tijd(:),versnelling(:),data_filtered(:)];");
			 * stdin.println("csvwrite ('x1.txt', x1)");
			 * 
			 * stdin.println("x2 = [frequentie(:),amplitude(:),A_data(:)];");
			 * stdin.println("csvwrite ('x2.txt', x2)");
			 */
			stdin.println(
					"versnellingy=versnelling; data_filteredy=data_filtered; frequentiey=frequentie; amplitudey=amplitude; A_datay=A_data; ");

			// ----------------------------------------z
			// waarde----------------------------------------

			// Generate data
			stdin.println("t = [" + tijd + "];");
			stdin.println("a = -1; b = 1;");
			stdin.println("t = t + (a + (b-a).*rand(1,length(t))).*1e-3;");
			stdin.println("f1 = 2; f2 = 8; % two frequencies within the signal;");
			stdin.println("data = [" + zWaarde + "];");

			// Stap1
			stdin.println("Fs = 100.; % desired (fixed) sample rate;");
			stdin.println("t_resampled = t(1):1/Fs:t(end);");
			stdin.println("data_resampled = interp1(t, data, t_resampled, 'spline');");
			stdin.println("t_resampled = t_resampled - t_resampled(1); ");

			// tijd en versnelling bijhouden
			stdin.println("tijd=t_resampled;");
			stdin.println("versnelling=data_resampled;");

			// Stap2
			stdin.println("L = length(data_resampled);");
			stdin.println("f = Fs*(0:(L/2))/L;");
			stdin.println("A2_data = fft(data_resampled); A2 = abs(A2_data/L);");
			stdin.println("A_data = A2(1:L/2+1); A_data(2:end-1) = 2*A_data(2:end-1);");

			// frequentie en amplitude bijhouden
			stdin.println("frequentie=f;");
			stdin.println("amplitude=A_data;");

			// Stap3
			stdin.println("f1 = 1/Fs*2; f2 = 4/Fs*2;");
			stdin.println("filter_order = 4;");
			stdin.println("[b,a] = butter(filter_order,[f1 f2]);");
			stdin.println("data_filtered = filtfilt(b,a,data_resampled);");
			stdin.println("A2_data = fft(data_filtered); A2 = abs(A2_data/L);");
			stdin.println("A_data = A2(1:L/2+1); A_data(2:end-1) = 2*A_data(2:end-1);");

			stdin.println(
					"versnellingz=versnelling; data_filteredz=data_filtered; frequentiez=frequentie; amplitudez=amplitude; A_dataz=A_data; ");

			// grootheden in datasets steken

			// tijd in functie van versnelling
			stdin.println("x1 = [tijd(:),data_filteredx(:),data_filteredy(:),data_filteredz(:)];");

			// frequentie in functie van amplitude
			stdin.println("x2 = [frequentiex(:),A_datax(:),A_datay(:),A_dataz(:)];");

			stdin.close();

			/*
			 * ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			 * 
			 * int nRead; byte[] data = new byte[16384];
			 * 
			 * while ((nRead = sync.getInputStream().read(data, 0, data.length))
			 * != -1) { buffer.write(data, 0, nRead); }
			 * 
			 * buffer.flush();
			 */

			by = sync.getBuffer();

			// by = buffer.toByteArray();
			s1 = new String(by);

			// int index = s1.lastIndexOf("x1 =");
			// s1.substring(index);

			int returnCode = 0;
			try {
				returnCode = p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Return code = " + returnCode);

		} catch (IOException e) {
			e.printStackTrace();
		}

		resultaten[0] = s1;
		resultaten[1] = s2;

		return resultaten;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Vibrationspotter.OctaveManagementEJBLocal#verwerkstringdata(java.lang.
	 * String)
	 */
	public String verwerkstringdata(String invoer) {
		String nieuwe = "";
		while (invoer.contains("x")) {
			// invoer.replaceFirst(regex, replacement)
			int xx = invoer.indexOf("x");
			int yy = invoer.indexOf("y");
			int zz = invoer.indexOf("z");
			int tt = invoer.indexOf("tijd");
			int haakje = invoer.indexOf("}");

			String x = invoer.substring(xx + 3, yy - 2);
			String y = invoer.substring(yy + 3, zz - 2);
			String z = invoer.substring(zz + 3, tt - 2);
			String t = invoer.substring(tt + 6, haakje - 1);

			nieuwe = nieuwe + t + "," + x + "," + y + "," + z + '\n';
			invoer = invoer.substring(haakje + 1);
		}

		return nieuwe;
	}
}
