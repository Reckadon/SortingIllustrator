import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

import java.util.Random;


public class Controller {
    public BarChart BCArray;// BC for Barchart
    @FXML public Button btnSort;
    @FXML public Slider sizeSlider;
    @FXML public Button btnGenerate;
    public static void arrayGenerate(){
        Random rand =new Random();
        int arraySize=rand.nextInt(95);
        int array[]= new int[arraySize];
        for(int i=0;i<arraySize;i++){
            array[i]= rand.nextInt(80);
        }

    }
    @FXML
    public void initialize(){

    }

}
