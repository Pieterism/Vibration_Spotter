package project;


 
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpSession;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;

import Vibrationspotter.MetingManagementEJBLocal;
import model.Meting;
 
@ManagedBean
public class ChartView implements Serializable {
 
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
        grafiek1.setSeriesColors("3300FF,FF0000");
        grafiek1.setTitle("versnelling in functie van tijd");
        Axis xAxis1=grafiek1.getAxis(AxisType.X);
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
        grafiek2.setSeriesColors("3300FF,FF0000");
        grafiek2.setTitle("amplitude in functie van frequentie");
        Axis xAxis2=grafiek2.getAxis(AxisType.X);
        xAxis2.setLabel("frequentie [Hz]");
        Axis yAxis2 = grafiek2.getAxis(AxisType.Y);
        yAxis2.setLabel("Amplitude [m/s^2/Hz]");
        yAxis2.setMin(0);
        yAxis2.setMax(1);
         

    }
     
    private LineChartModel initLinearModel1() throws UnsupportedEncodingException {
        LineChartModel model = new LineChartModel();
 
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("resampled data");
        series1.setSmoothLine(true);
        series1.setShowMarker(false);
        
        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("filtered data");
        series2.setSmoothLine(true);
        series2.setShowMarker(false);
        
   	  	String s=dataset11Ophalen();
   	  	String lijnen[] = s.split("\\r?\\n");
   	  	for(int i=0;i<lijnen.length;i++){
   	  		String getallen[]=lijnen[i].split(",");
   	  		double x=Double.parseDouble(getallen[0]);
   	  		double y1=Double.parseDouble(getallen[1]);
   	  		double y2=Double.parseDouble(getallen[2]);
   	  		series1.set(x,y1);
   	  		series2.set(x, y2);

   	  	}
        

        model.addSeries(series1);
        model.addSeries(series2);
         
        return model;
    } 
    
    private String dataset11Ophalen() throws UnsupportedEncodingException {
    	HttpSession session = SessionUtils.getSession();
   	  	int idmeting=(int) session.getAttribute("idMeting");
   	  	String x1=new String(metingejb.zoekDataset1(idmeting));
		return x1;
	}

	private LineChartModel initLinearModel2() throws UnsupportedEncodingException {
        LineChartModel model = new LineChartModel();
        
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("resampled data");
        series1.setSmoothLine(true);
        series1.setShowMarker(false);
        
        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("filtered data");
        series2.setSmoothLine(true);
        series2.setShowMarker(false);
        
   	  	String s=dataset2Ophalen();
   	  	String lijnen[] = s.split("\\r?\\n");
   	  	for(int i=0;i<lijnen.length;i++){
   	  		String getallen[]=lijnen[i].split(",");
   	  		double x=Double.parseDouble(getallen[0]);
   	  		double y1=Double.parseDouble(getallen[1]);
   	  		double y2=Double.parseDouble(getallen[2]);
   	  		series1.set(x,y1);
   	  		series2.set(x, y2);

   	  	}
        

        model.addSeries(series1);
        model.addSeries(series2);
         
        return model;
    }
    
    
    private String dataset2Ophalen() throws UnsupportedEncodingException {
    	HttpSession session = SessionUtils.getSession();
   	  	int idmeting=(int) session.getAttribute("idMeting");
   	  	String x2=new String(metingejb.zoekDataset2(idmeting));
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