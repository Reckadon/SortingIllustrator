import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

import javax.xml.transform.Source;
import java.util.Arrays;
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
        for(int i=0;i<sliderValue;i++){
            for(int j=0;j<(sliderValue-1);j++) {
                if (array[j] == array[j + 1]) {
                    array[j] = rand.nextInt(80);
                }
            }
        }
        Thread.sleep(100);
        updateChart(array);
        System.out.println(Arrays.toString(array));
    }
    public void arraySort() throws InterruptedException {
        for(int i =0;i<sliderValue;i++){
            for(int j=0;j<(sliderValue-1);j++){
                if(array[j]>array[j+1]) {
                    int b=array[j+1];
                    array[j+1]=array[j];
                    array[j]=b;
                }
                updateChart(array);
                Thread.sleep(50);
                System.out.println(Arrays.toString(array));
            }
        }

    }

    public void updateChart(int[] Tarray) {                    //use this method to put data on the chart
        BCArray.getData().clear();
        BCArray.layout();
        XYChart.Series series=new XYChart.Series();
        for (int n =0;n<Tarray.length;n++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(Tarray[n]),Tarray[n]));
        }
        series.setName("Numbers");
        BCArray.getData().setAll(series);
        BCArray.setTitle("Random Array of Size "+sliderValue+" elements");
        BCArray.setLegendVisible(false);
    }


    @FXML
    public void initialize() throws InterruptedException {
         arrayGenerate();
    }

}
