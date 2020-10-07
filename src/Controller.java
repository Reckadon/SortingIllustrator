import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

import java.util.Random;


public class Controller {
    public BarChart BCArray;// BC for Barchart
    @FXML public Button btnSort;
    @FXML public Slider sizeSlider;
    @FXML public Button btnGenerate;
    int array[];
    int sliderValue;
    public void arrayGenerate() throws InterruptedException {
        sliderValue=(int) sizeSlider.getValue();
        Random rand =new Random();
        array= new int[sliderValue];
        for(int i=0;i<sliderValue;i++){
            array[i]= rand.nextInt(80);
        }
        arraySort();
        updateChart(array);
    }
    public void arraySort() throws InterruptedException {
        for(int i =0;i<array.length;i++){
            for(int j=0;j<(array.length-1);j++){
                if(array[j]>array[j+1]) {
                    int b=array[j+1];
                    array[j+1]=array[j];
                    array[j]=b;
                }
            }
        }
        Thread.sleep(1000);
        updateChart(array);
    }

    public void updateChart(int[] Tarray) {                    //use this method to put data on the chart
        BCArray.getData().clear();
        XYChart.Series series=new XYChart.Series();
        for (int n:Tarray) {
            series.getData().add(new XYChart.Data<>(String.valueOf(n),n));
        }
        series.setName("Numbers");
        BCArray.getData().setAll(series);
        BCArray.setTitle("Random Array of Size "+Tarray.length+" elements");
        BCArray.setLegendVisible(false);
    }

    @FXML
    public void initialize() throws InterruptedException {
        arrayGenerate();
        arraySort();
    }

}
