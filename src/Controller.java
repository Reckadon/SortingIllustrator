import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

import java.util.Arrays;
import java.util.Random;


public class Controller {
    public BarChart BCArray;// BC for Barchart
    @FXML public Button btnSort;
    @FXML public Slider sizeSlider;
    @FXML public Button btnGenerate;
    
    @FXML
    public void arrayGenerate(){
        int sliderValue=(int) sizeSlider.getValue();
        Random rand =new Random();
        int array[]= new int[sliderValue];
        for(int i=0;i<sliderValue;i++){
            array[i]= rand.nextInt(80);
        }
        System.out.println(Arrays.toString(array));

    }
    @FXML
    public void initialize(){
        System.out.println(sizeSlider.getValue()); // timepass(see output-first initialize hota)
    }

}
