import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Controller {

    public BarChart BCArray;// BC for Barchart
    @FXML public Button btnSort;
    @FXML public Slider sizeSlider;
    @FXML public Button btnGenerate;
    int array[];
    int sliderValue;
    int delay=1000;
    private Random rand =new Random();

    public void arrayGenerate() {
        sliderValue=(int) sizeSlider.getValue();
        array= new int[sliderValue];
        for(int i=0;i<array.length;i++){
            array[i]= rand.nextInt(496)+5;
        }
        updateChart(array);
    }

    private int i=0,j=0;
    public void arraySort() {
        btnSort.setDisable(true);
        btnGenerate.setDisable(true);
        sizeSlider.setDisable(true);
        Timer outerTimer =new Timer();
        outerTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Timer innerTimer =new Timer();
                innerTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if (array[j]>array[j+1]){
                            int t=array[j];
                            array[j]=array[j+1];
                            array[j+1]=t;
                        }

                        Platform.runLater(new Runnable() {
                            public void run() {
                                updateChart(array);
                            }
                        });
                        j++;
                        System.out.println(j);
                        if (j==array.length-1){
                            innerTimer.cancel();
                            j=0;
                        }
                    }
                },0,delay);

                i++;
                System.out.println(i+"st Timer");
                if (i==array.length-1){
                    outerTimer.cancel();
                    i=0;
                    btnSort.setDisable(false);
                    btnGenerate.setDisable(false);
                    sizeSlider.setDisable(false);
                }
            }
        },0,array.length *delay);
    }


    public void updateChart(int[] Tarray) {                    //use this method to put data on the chart
        BCArray.getData().clear();
        BCArray.layout();
        XYChart.Series series=new XYChart.Series();
        for (int n:Tarray) {
            series.getData().add(new XYChart.Data<>(String.valueOf(n),n));
        }
        series.setName("Numbers");
        BCArray.getData().setAll(series);
        BCArray.setTitle("Random Array of Size "+sliderValue+" elements");
        BCArray.setLegendVisible(true);
        System.out.println("from update");
    }


    @FXML
    public void initialize() {
         arrayGenerate();
    }

}
