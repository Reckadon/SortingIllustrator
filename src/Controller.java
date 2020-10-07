import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

import java.util.Random;


public class Controller {
    public BarChart BCArray;// BC for Barchart
    @FXML public static Button btnSort;
    @FXML public static Slider sizeSlider;
    @FXML public static Button btnGenerate;
    public static void arrayGenerate(){
        sizeSlider.onMouseReleasedProperty();
        int sliderValue=(int) sizeSlider.getValue();
        Random rand =new Random();
        int array[]= new int[sliderValue];
        for(int i=0;i<sliderValue;i++){
            array[i]= rand.nextInt(80);
        }

    }
    @FXML
    public void initialize(){

    }

}
