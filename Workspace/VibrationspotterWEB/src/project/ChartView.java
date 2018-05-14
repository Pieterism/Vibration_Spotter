package project;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpSession;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import Vibrationspotter.MetingManagementEJBLocal;

/**
 * Class to implement a chart on our webpage, fetches datapoints from database
 * and generates a chart.
 * 
 * @author Birgen Vermang, Thomas Bruneel, Pieter-Jan Vanhaverbeke, Pieter
 *         Vanderhaegen
 *
 */
@ManagedBean
public class ChartView implements Serializable {

	private static final long serialVersionUID = 1L;
	private LineChartModel grafiek3;
	private LineChartModel grafiek4;
	private LineChartModel grafiek5;
	private LineChartModel grafiek2;

	@EJB
	private MetingManagementEJBLocal metingejb;

	@PostConstruct
	public void init() {
		createLineModels();
	}

	private void createLineModels() {
		try {
			grafiek3 = initLinearModel3();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		grafiek3.setAnimate(true);
		grafiek3.setZoom(true);
		grafiek3.setLegendPosition("e");
		grafiek3.setSeriesColors("3300FF");
		grafiek3.setTitle("versnelling in functie van tijd");
		Axis xAxis3 = grafiek3.getAxis(AxisType.X);
		xAxis3.setLabel("tijd [s]");
		Axis yAxis3 = grafiek3.getAxis(AxisType.Y);
		yAxis3.setLabel("versnelling [m/s^2]");
		double[] dx=maxXbepalen();
		yAxis3.setMin(dx[0]);
		yAxis3.setMax(dx[1]);

		
		
		try {
			grafiek4 = initLinearModel4();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		grafiek4.setAnimate(true);
		grafiek4.setZoom(true);
		grafiek4.setLegendPosition("e");
		grafiek4.setSeriesColors("FF0000");
		grafiek4.setTitle("versnelling in functie van tijd");
		Axis xAxis4 = grafiek4.getAxis(AxisType.X);
		xAxis4.setLabel("tijd [s]");
		Axis yAxis4 = grafiek4.getAxis(AxisType.Y);
		yAxis4.setLabel("versnelling [m/s^2]");
		double[] dy=maxYbepalen();
		yAxis4.setMin(dy[0]);
		yAxis4.setMax(dy[1]);
		
		
		try {
			grafiek5 = initLinearModel5();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		grafiek5.setAnimate(true);
		grafiek5.setZoom(true);
		grafiek5.setLegendPosition("e");
		grafiek5.setSeriesColors("30a000");
		grafiek5.setTitle("versnelling in functie van tijd");
		Axis xAxis5 = grafiek5.getAxis(AxisType.X);
		xAxis5.setLabel("tijd [s]");
		Axis yAxis5 = grafiek5.getAxis(AxisType.Y);
		yAxis5.setLabel("versnelling [m/s^2]");
		double[] dz=maxZbepalen();
		yAxis5.setMin(dz[0]);
		yAxis5.setMax(dz[1]);
		System.out.println(dz[0]);
		System.out.println(dz[1]);
		
		
		
		
		
		
		
		
		
		
		try {
			grafiek2 = initLinearModel2();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		grafiek2.setAnimate(true);
		grafiek2.setZoom(true);
		grafiek2.setLegendPosition("e");
		grafiek2.setSeriesColors("3300FF,FF0000,30a000");
		grafiek2.setTitle("amplitude in functie van frequentie");
		Axis xAxis2 = grafiek2.getAxis(AxisType.X);
		xAxis2.setLabel("frequentie [Hz]");
		Axis yAxis2 = grafiek2.getAxis(AxisType.Y);
		yAxis2.setLabel("Amplitude [m/s^2/Hz]");
		yAxis2.setMin(0);
		yAxis2.setMax(1);

	}

	private LineChartModel initLinearModel3() throws UnsupportedEncodingException {
		LineChartModel model = new LineChartModel();

		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel("x");
		series1.setSmoothLine(true);
		series1.setShowMarker(false);


		String s = resultatenOphalen();
		String lijnen[] = s.split("\\r?\\n");
		for (int i = 0; i < lijnen.length; i++) {
			String getallen[] = lijnen[i].split(",");
			double t = Double.parseDouble(getallen[0]);
			double x = Double.parseDouble(getallen[1]);
			series1.set(t, x);

		}

		model.addSeries(series1);

		return model;
	}
	private LineChartModel initLinearModel4() throws UnsupportedEncodingException {
		LineChartModel model = new LineChartModel();

		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel("y");
		series1.setSmoothLine(true);
		series1.setShowMarker(false);


		String s = resultatenOphalen();
		String lijnen[] = s.split("\\r?\\n");
		for (int i = 0; i < lijnen.length; i++) {
			String getallen[] = lijnen[i].split(",");
			double t = Double.parseDouble(getallen[0]);
			double y = Double.parseDouble(getallen[2]);
			series1.set(t, y);

		}

		model.addSeries(series1);

		return model;
	}

	private String dataset11Ophalen() throws UnsupportedEncodingException {
		HttpSession session = SessionUtils.getSession();
		int idmeting = (int) session.getAttribute("idMeting");
		String x1 = new String(metingejb.zoekDataset1(idmeting));
		return x1;
	}

	private LineChartModel initLinearModel2() throws UnsupportedEncodingException {
		LineChartModel model = new LineChartModel();

		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel("x");
		series1.setSmoothLine(true);
		series1.setShowMarker(false);

		LineChartSeries series2 = new LineChartSeries();
		series2.setLabel("y");
		series2.setSmoothLine(true);
		series2.setShowMarker(false);
		
		LineChartSeries series3 = new LineChartSeries();
		series3.setLabel("z");
		series3.setSmoothLine(true);
		series3.setShowMarker(false);

		String s = dataset2Ophalen();
		String lijnen[] = s.split("\\r?\\n");
		for (int i = 0; i < lijnen.length; i++) {
			String getallen[] = lijnen[i].split(",");
			double f = Double.parseDouble(getallen[0]);
			double x = Double.parseDouble(getallen[1]);
			double y = Double.parseDouble(getallen[2]);
			double z = Double.parseDouble(getallen[3]);
			series1.set(f, x);
			series2.set(f, y);
			series3.set(f, z);

		}

		model.addSeries(series1);
		model.addSeries(series2);
		model.addSeries(series3);

		return model;
	}
	
	private LineChartModel initLinearModel5() throws UnsupportedEncodingException {
		LineChartModel model = new LineChartModel();

		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel("z");
		series1.setSmoothLine(true);
		series1.setShowMarker(false);


		String s = resultatenOphalen();
		String lijnen[] = s.split("\\r?\\n");
		for (int i = 0; i < lijnen.length; i++) {
			String getallen[] = lijnen[i].split(",");
			double t = Double.parseDouble(getallen[0]);

			double z = Double.parseDouble(getallen[3]);
			series1.set(t, z);

		}

		model.addSeries(series1);

		return model;
	}
	private String resultatenOphalen() throws UnsupportedEncodingException {
		HttpSession session = SessionUtils.getSession();
		int idmeting = (int) session.getAttribute("idMeting");
		String res= new String(metingejb.zoekDataset1(idmeting));
		return res;
	}

	private String dataset2Ophalen() throws UnsupportedEncodingException {
		HttpSession session = SessionUtils.getSession();
		int idmeting = (int) session.getAttribute("idMeting");
		String x2 = new String(metingejb.zoekDataset2(idmeting));
		return x2;
	}
	
	private double[] maxXbepalen() {
		double max=-1000;
		double min=1000;
		HttpSession session = SessionUtils.getSession();
		int idmeting = (int) session.getAttribute("idMeting");
		String res= new String(metingejb.zoekDataset1(idmeting));
		String lijnen[] = res.split("\\r?\\n");
		for (int i = 0; i < lijnen.length; i++) {
			String getallen[] = lijnen[i].split(",");
			double x = Double.parseDouble(getallen[1]);
			if(x<min){
				min=x;
			}
			if(x>max){
				max=x;
			}

		}
		double[]array=new double[2];
		array[0]=min;
		array[1]=max;
		return array;
		
		
	}
	
	private double[] maxYbepalen() {
		double max=-1000;
		double min=1000;
		HttpSession session = SessionUtils.getSession();
		int idmeting = (int) session.getAttribute("idMeting");
		String res= new String(metingejb.zoekDataset1(idmeting));
		String lijnen[] = res.split("\\r?\\n");
		for (int i = 0; i < lijnen.length; i++) {
			String getallen[] = lijnen[i].split(",");
			double y = Double.parseDouble(getallen[2]);
			if(y<min){
				min=y;
			}
			if(y>max){
				max=y;
			}

		}
		double[]array=new double[2];
		array[0]=min;
		array[1]=max;
		return array;
		
		
	}
	
	private double[] maxZbepalen() {
		double max=-1000;
		double min=1000;
		HttpSession session = SessionUtils.getSession();
		int idmeting = (int) session.getAttribute("idMeting");
		String res= new String(metingejb.zoekDataset1(idmeting));
		String lijnen[] = res.split("\\r?\\n");
		for (int i = 0; i < lijnen.length; i++) {
			String getallen[] = lijnen[i].split(",");
			double z = Double.parseDouble(getallen[3]);
			if(z<min){
				min=z;
			}
			if(z>max){
				max=z;
			}

		}
		double[]array=new double[2];
		array[0]=min;
		array[1]=max;
		return array;
		
		
	}

	public LineChartModel getGrafiek3() {
		return grafiek3;
	}

	public void setGrafiek3(LineChartModel grafiek3) {
		this.grafiek3 = grafiek3;
	}
	
	public LineChartModel getGrafiek4() {
		return grafiek4;
	}

	public void setGrafiek4(LineChartModel grafiek4) {
		this.grafiek4 = grafiek4;
	}

	public LineChartModel getGrafiek5() {
		return grafiek5;
	}

	public void setGrafiek5(LineChartModel grafiek5) {
		this.grafiek5 = grafiek5;
	}

	public LineChartModel getGrafiek2() {
		return grafiek2;
	}

	public void setGrafiek2(LineChartModel grafiek2) {
		this.grafiek2 = grafiek2;
	}

}