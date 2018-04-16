package Vibrationspotter;

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

	public void createdata() {
		try {

			String[] command = { "octave-cli", };
			Process p = Runtime.getRuntime().exec(command);
			new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
			new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
			PrintWriter stdin = new PrintWriter(p.getOutputStream());
			InputStreamReader stdout = new InputStreamReader(p.getInputStream());

			// Load package
			stdin.println("pkg load signal");

			// Generate data
			stdin.println("t = 0:0.01:20; %[s]");
			stdin.println("a = -1; b = 1;");
			stdin.println("t = t + (a + (b-a).*rand(1,length(t))).*1e-3;");
			stdin.println("f1 = 2; f2 = 8; % two frequencies within the signal");
			stdin.println("data = sin(2*pi*f1.*t)+0.5.*sin(2*pi*f2.*t);");

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
			stdin.println("x1 = [tijd(:),versnelling(:),data_filtered(:)];");
			stdin.println("csvwrite ('x1.txt', x1)");

			stdin.println("x2 = [frequentie(:),amplitude(:),A_data(:)];");
			stdin.println("csvwrite ('x2.txt', x2)");

			stdin.close();

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

	}
}
