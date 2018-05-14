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
	private LineChartModel grafiek1;
	private LineChartModel grafiek2;

	@EJB
	private MetingManagementEJBLocal metingejb;

	@PostConstruct
	public void init() {
		createLineModels();
	}

	private void createLineModels() {
		try {
			grafiek1 = initLinearModel1();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		grafiek1.setAnimate(true);
		grafiek1.setZoom(true);
		grafiek1.setLegendPosition("e");
		grafiek1.setSeriesColors("3300FF,FF0000,30a000");
		grafiek1.setTitle("versnelling in functie van tijd");
		Axis xAxis1 = grafiek1.getAxis(AxisType.X);
		xAxis1.setLabel("tijd [s]");
		Axis yAxis1 = grafiek1.getAxis(AxisType.Y);
		yAxis1.setLabel("versnelling [m/s^2]");
		yAxis1.setMin(-2);
		yAxis1.setMax(2);

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

	private LineChartModel initLinearModel1() throws UnsupportedEncodingException {
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

		String s = dataset11Ophalen();
		String lijnen[] = s.split("\\r?\\n");
		for (int i = 0; i < lijnen.length; i++) {
			String getallen[] = lijnen[i].split(",");
			double t = Double.parseDouble(getallen[0]);
			double x = Double.parseDouble(getallen[1]);
			double y = Double.parseDouble(getallen[2]);
			double z = Double.parseDouble(getallen[3]);
			series1.set(t, x);
			series2.set(t, y);
			series3.set(t, z);

		}

		model.addSeries(series1);
		model.addSeries(series2);
		model.addSeries(series3);

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

	private String dataset2Ophalen() throws UnsupportedEncodingException {
		HttpSession session = SessionUtils.getSession();
		int idmeting = (int) session.getAttribute("idMeting");
		String x2 = new String(metingejb.zoekDataset2(idmeting));
		return x2;
	}

	public LineChartModel getGrafiek1() {
		return grafiek1;
	}

	public void setGrafiek1(LineChartModel grafiek1) {
		this.grafiek1 = grafiek1;
	}

	public LineChartModel getGrafiek2() {
		return grafiek2;
	}

	public void setGrafiek2(LineChartModel grafiek2) {
		this.grafiek2 = grafiek2;
	}

}