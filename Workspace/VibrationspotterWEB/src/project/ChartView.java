package project;


 
import javax.annotation.PostConstruct;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;
 
@ManagedBean
public class ChartView implements Serializable {
 
    private LineChartModel grafiek1;
    private LineChartModel grafiek2;
     
    @PostConstruct
    public void init() {
        createLineModels();
    }
 
	private void createLineModels() {
        grafiek1 = initLinearModel1();
        grafiek1.setAnimate(true);
        grafiek1.setLegendPosition("e");
        grafiek1.setTitle("versnelling in functie van tijd");
        grafiek1.getAxes().put(AxisType.X, new CategoryAxis("tijd [s]"));
        Axis yAxis1 = grafiek1.getAxis(AxisType.Y);
        yAxis1.setLabel("versnelling [m/s^2]");
        yAxis1.setMin(0);
        yAxis1.setMax(10);
        
        grafiek2 = initLinearModel2();
        grafiek2.setAnimate(true);
        grafiek2.setLegendPosition("e");
        grafiek2.setTitle("amplitude in functie van frequentie");
        grafiek2.getAxes().put(AxisType.X, new CategoryAxis("frequentie [Hz]"));
        Axis yAxis2 = grafiek2.getAxis(AxisType.Y);
        yAxis2.setLabel("Amplitude [m/s^2/Hz]");
        yAxis2.setMin(0);
        yAxis2.setMax(10);
         

    }
     
    private LineChartModel initLinearModel1() {
        LineChartModel model = new LineChartModel();
 
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("resampled data");
        series1.setSmoothLine(true);
       // series1.setShowMarker(false);
 
        series1.set(1, 2);
        series1.set(2, 1);
        series1.set(3, 3);
        series1.set(4, 6);
        series1.set(5, 8);
 
        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("filtered data");
        series2.setSmoothLine(true);
 
        
        series2.set(1, 6);
        series2.set(2, 3);
        series2.set(3, 2);
        series2.set(4, 7);
        series2.set(5, 9);
 
        model.addSeries(series1);
        model.addSeries(series2);
         
        return model;
    }
    
    private LineChartModel initLinearModel2() {
        LineChartModel model = new LineChartModel();
 
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("resampled data");
        series1.setSmoothLine(true);
       // series1.setShowMarker(false);
 
        series1.set(1, 2);
        series1.set(2, 1);
        series1.set(4, 6);
        series1.set(3, 10);
 
        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("filtered data");
        series2.setSmoothLine(true);
 
       
        series2.set(1, 6);
        series2.set(2, 3);
        series2.set(3, 2);
        series2.set(4, 7);
        series2.set(5, 9);
 
        model.addSeries(series1);
        model.addSeries(series2);
         
        return model;
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