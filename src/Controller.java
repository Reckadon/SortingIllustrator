import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Controller {

    public BarChart BCArray;// BC for Barchart
    @FXML public Button btnSort;
    @FXML public Slider sizeSlider;
    @FXML public Button btnGenerate;
    public AnchorPane pane;
    public Slider speedSlider;
    int array[];
    int sliderValue, delayMultiplier =1;
    private int delay=30;
    boolean sorting;
    private Random rand =new Random();

    public void arrayGenerate() {
        sliderValue=(int) sizeSlider.getValue();
        array= new int[sliderValue];
        for(int i=0;i<array.length;i++){
            array[i]= rand.nextInt(496)+5;
        }
        // Sorting array
        Arrays.sort(array);
        // Checking for duplicates
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array.length-1;j++){
                if(array[j]==array[j+1]){              //if duplicates aside, then increment the number(like 169 169 will become 169 170)
                    array[j+1]=array[j+1]+1;
                }
            }
        }
        //Shuffling array
        for(int i=0;i<array.length;i++){
            for(int k=0;k<array.length;k++){
                for (int j = 0; j <array.length-1 ; j++) {           //3rd for loop cause right side me hi larger numbers aare the on average
                    if(rand.nextBoolean()){                          //ab nai aare
                        int t=array[j];
                        array[j]=array[j+1];
                        array[j+1]=t;
                    }
                }
            }
        }
        updateChart(array);
    }

    private void setDelay(){
        if(array.length ==0 || array.length <=15) delay=40* delayMultiplier;           //delay manager
        else if(array.length <=30) delay = 18* delayMultiplier;
        else if(array.length <=40) delay = 13* delayMultiplier;
        else if(array.length <=60) delay = 10* delayMultiplier;
        else if(array.length <=75) delay = 8* delayMultiplier;
        else delay = 5* delayMultiplier;//iske aage mera gpu marjaata lol
    }

    private int i=0,j=0,count=0;
    private Node n;
    public void arraySort() {
        setDelay();
        sorting=true;
        btnSort.setDisable(true);
        btnGenerate.setDisable(true);
        sizeSlider.setDisable(true);
        speedSlider.setDisable(true);

        Timer outerTimer =new Timer();
        outerTimer.scheduleAtFixedRate(new TimerTask() {                       //outer for loop
            @Override
            public void run() {
                count=0;
                Timer innerTimer =new Timer();
                innerTimer.scheduleAtFixedRate(new TimerTask() {               //inner for loop
                    @Override
                    public void run() {
                        if (array[j]>array[j+1]){                              //bubble sort algorithm
                            int t=array[j];
                            array[j]=array[j+1];
                            array[j+1]=t;
                        }else count++;

                        Platform.runLater(() -> updateChart(array));

                        if (count==array.length-1){
                            innerTimer.cancel();
                            outerTimer.cancel();
                            btnSort.setDisable(false);
                            btnGenerate.setDisable(false);
                            sizeSlider.setDisable(false);
                            speedSlider.setDisable(false);
                            sorting=false;
                            j=0;
                            i=0;
                        }
                        j++;
                        if (j==array.length-1){
                            innerTimer.cancel();
                            j=0;
                            i=0;
                        }
                    }
                },0,delay);
                i++;
                if (i==array.length-1){
                    outerTimer.cancel();
                    i=0;
                    j=0;
                    btnSort.setDisable(false);
                    btnGenerate.setDisable(false);
                    sizeSlider.setDisable(false);
                    speedSlider.setDisable(false);
                    sorting=false;
                }
            }
        },0,array.length *delay+10);
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
        BCArray.setLegendVisible(true);

        if (sorting){
            BCArray.setTitle("Bubble Sorting Array");                        //nice
            BCArray.lookupAll(".default-color0.chart-bar")
                    .forEach(n -> n.setStyle("-fx-bar-fill: #202020;"));                              //all bars
            BCArray.lookup(".chart-plot-background").setStyle("-fx-background-color: #ccebff;");   //chart bg
            BCArray.setStyle("-fx-background-color: #ccebff;");
            pane.setStyle("-fx-background-color: #ccebff");                                          //application bg
            n = BCArray.lookup(".data"+j+".chart-bar");                                            // J and i
            n.setStyle("-fx-bar-fill: #ff0000");
            n = BCArray.lookup(".data"+(j+1)+".chart-bar");
            n.setStyle("-fx-bar-fill: #ff0000");
        }else {
            BCArray.setTitle("Random Array  of "+array.length+" elements");
            BCArray.lookup(".chart-plot-background").setStyle("-fx-background-color: light-grey;");//chart bg
            BCArray.setStyle("-fx-background-color: light-grey;");
            pane.setStyle("-fx-background-color: light-grey;");
        }
        System.out.println("from update");
    }


    @FXML
    public void initialize() {
        arrayGenerate();
        sizeSlider.valueProperty().addListener(
                (options, oldValue, newValue) ->
                {
                    if (newValue.equals(oldValue))
                        return;
                    sliderValue=newValue.intValue();
                    arrayGenerate();
                }
        );
        sizeSlider.setShowTickLabels(true);               //ye kya kiya hai??? slider pe numbers
        speedSlider.valueProperty().addListener(
                (options, oldValue, newValue) ->
                {
                    if (newValue.equals(oldValue))
                        return;
                    delayMultiplier =newValue.intValue();       //1 to 10
                    delayMultiplier =11- delayMultiplier;       //inverting
                    System.out.println(delayMultiplier);
                    setDelay();
                }
        );
    }

}