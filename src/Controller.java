import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    public ComboBox<String> algorithmList;
    private int array[];
    private int sliderValue, delayMultiplier =8;
    private String algorithm="Bubble Sort";
    private int delay=30;
    private boolean bubbleSorting,move;
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
    public void bubbleSort() {
        disableAll();
        setDelay();
        bubbleSorting =true;

        Timer outerTimer =new Timer();
        outerTimer.scheduleAtFixedRate(new TimerTask() {                       //outer for loop
            @Override
            public void run() {
                count=0;
                Timer innerTimer =new Timer();
                innerTimer.scheduleAtFixedRate(new TimerTask() {               //inner for loop
                    @Override
                    public void run() {
                        move=false;
                        if (array[j]>array[j+1]){                              //bubble sort algorithm
                            int t=array[j];
                            array[j]=array[j+1];
                            array[j+1]=t;
                            move=true;
                        }else count++;

                        Platform.runLater(() -> updateChart(array));

                        if (count==array.length-1){
                            innerTimer.cancel();
                            outerTimer.cancel();
                            enableAll();
                            bubbleSorting =false;
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
                    enableAll();
                    bubbleSorting =false;
                }
            }
        },0,array.length *delay+10);
    }

    private void enableAll() {
        btnSort.setDisable(false);
        btnGenerate.setDisable(false);
        sizeSlider.setDisable(false);
        speedSlider.setDisable(false);
        algorithmList.setDisable(false);
    }

    private void disableAll() {
        btnSort.setDisable(true);
        btnGenerate.setDisable(true);
        sizeSlider.setDisable(true);
        speedSlider.setDisable(true);
        algorithmList.setDisable(true);
    }

    private boolean selectionSorting;
    private void selectionSort() {
        selectionSorting=true;
        System.out.println(Arrays.toString(array));
        setDelay();
        disableAll();
        outerTimer();
    }

    private int currentPos=0, currentMinIndex =0, startingPos = 0;
    private void outerTimer() {
        //not a timer but named it for understanding

        currentMinIndex = startingPos;
        currentPos = startingPos;

        Timer innerTimer = new Timer();
        innerTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentPos++;
                if (startingPos==array.length-1){
                    innerTimer.cancel();
                    System.out.println("Sorted " +Arrays.toString(array));
                    enableAll();
                    startingPos = 0;
                    selectionSorting=false;
                    Platform.runLater(() -> updateChart(array));
                    return;
                }
                if (array[currentPos]< array[currentMinIndex]){
                    currentMinIndex = currentPos;
                }

                Platform.runLater(() -> updateChart(array));

                if (currentPos==array.length-1){
                    int t=array[startingPos];
                    array[startingPos]= array[currentMinIndex];
                    array[currentMinIndex]=t;

                    startingPos++;
                    innerTimer.cancel();
                    outerTimer();
                }
            }
        },0,delay);

    }

    private Node n;
    public void updateChart(int[] Tarray) {                    //use this method to put data on the chart
        BCArray.getData().clear();
        BCArray.layout();
        XYChart.Series series=new XYChart.Series();
        for (int n:Tarray) {
            series.getData().add(new XYChart.Data<>(String.valueOf(n),n));
        }
        series.setName("Numbers");
        BCArray.getData().setAll(series);
        BCArray.setLegendVisible(false);

        if (bubbleSorting){
            BCArray.setTitle("Bubble Sorting Array");                        //nice
            BCArray.lookupAll(".default-color0.chart-bar")
                    .forEach(n -> n.setStyle("-fx-bar-fill: #202020;"));                              //all bars
            BCArray.lookup(".chart-plot-background").setStyle("-fx-background-color: #ccebff;");   //chart bg
            BCArray.setStyle("-fx-background-color: #ccebff;");
            pane.setStyle("-fx-background-color: #ccebff");                                          //application bg
            n = BCArray.lookup(".data"+j+".chart-bar");                                            // J and i
            if(move){
                n.setStyle("-fx-bar-fill: #ff0000");
            }else
                n.setStyle("-fx-bar-fill: green");

            n = BCArray.lookup(".data"+(j+1)+".chart-bar");
            n.setStyle("-fx-bar-fill: green");
        }
        else if (selectionSorting){
            BCArray.setTitle("Selection Sorting Array");                        //nice
            BCArray.lookupAll(".default-color0.chart-bar")
                    .forEach(n -> n.setStyle("-fx-bar-fill: #202020;"));                              //all bars
            BCArray.lookup(".chart-plot-background").setStyle("-fx-background-color: #ccebff;");   //chart bg
            BCArray.setStyle("-fx-background-color: #ccebff;");
            pane.setStyle("-fx-background-color: #ccebff");                                          //application bg
            for (int k = 0; k <startingPos ; k++) {
                n = BCArray.lookup(".data"+k+".chart-bar");
                n.setStyle("-fx-bar-fill: #00ff00");              //sorted part
            }
            n = BCArray.lookup(".data"+currentPos+".chart-bar");
            n.setStyle("-fx-bar-fill: #cc00cc");
            n = BCArray.lookup(".data"+currentMinIndex+".chart-bar");
            n.setStyle("-fx-bar-fill: red");
            n = BCArray.lookup(".data"+startingPos+".chart-bar");
            n.setStyle("-fx-bar-fill: green");
        }
        else {
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

        algorithmList.getItems().add("Bubble Sort");
        algorithmList.getItems().add("Selection Sort");
        algorithmList.getSelectionModel().select(0);

        algorithmList.getSelectionModel().selectedItemProperty().addListener(
                (options, oldValue, newValue) ->
                {
                    if (newValue.equals(oldValue))
                        return;
                    algorithm= newValue;
                    arrayGenerate();
                });

    }

    public void sort() {                                   //button press pe
        switch (algorithm){
            case "Bubble Sort":
                bubbleSort();
                break;
            case "Selection Sort":
                selectionSort();
                break;

        }
    }
}