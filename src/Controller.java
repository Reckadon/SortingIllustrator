import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

import java.util.Random;
import java.util.Arrays;


public class Controller {
    public BarChart BCArray;// BC for Barchart
    @FXML public Button btnSort;
    @FXML public Slider sizeSlider;
    @FXML public Button btnGenerate;
    int array[];
    int sliderValue;
    public void arrayGenerate(){
        sizeSlider.onMouseReleasedProperty();
        sliderValue=(int) sizeSlider.getValue();
        Random rand =new Random();
        array= new int[sliderValue];
        for(int i=0;i<sliderValue;i++){
            array[i]= rand.nextInt(80);
        }
        System.out.println(Arrays.toString(array));
    }
    public void arraySort(){
        for(int i =0;i<sliderValue;i++){
            for(int j=0;j<(sliderValue-1);j++){
                if(array[j]>array[j+1]) {
                    int b=array[j+1];
                    array[j+1]=array[j];
                    array[j]=b;
                }
            }
        }
        System.out.println(Arrays.toString(array));
    }
    @FXML
    public void initialize(){
        System.out.println(sizeSlider.getValue());
    }

}
