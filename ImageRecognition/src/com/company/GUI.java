package com.company;
import io.indico.Indico;
import io.indico.api.results.IndicoResult;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import static com.company.Main.createDirList;

public class GUI extends Application {
    ObservableList<PieChart.Data> pieChart;
    ImageView myImageView;
    PieChart chart;
//    PieChart chart = new PieChart(pieChart);
    double maxValues[] = new double[10];
    String names[] = new String[10];
    public static String removeWordsAfterComma(String words) {
        String output="";
        for (int i=0;i<words.length();i++) {
            if (words.charAt(i)==',') {
                return output;
            }
            output+=words.charAt(i);
        }
        return output;
    }
    public static void findGreatestValues(Map<String, Double> recognition, double[] maxValues, String[] names) {
        for (int i = 0; i < maxValues.length; i++) {
            for (int j = 0; j < recognition.size(); j++) {
                Iterator it = recognition.values().iterator();
                Iterator nameIterator = recognition.keySet().iterator();
                double max = 0;
                boolean doSave=true;
//                int indexOfMaxValue = 0;
                String name = "";
                String maxName= "";
                while (it.hasNext()) {
                    double element = (double) it.next();
                    name =nameIterator.next().toString();
                    if (element > max) {
                        for (int z=0;z<i;z++) {
                            if (names[z].equals(removeWordsAfterComma(maxName)))
                                doSave=false;
                        }
                        if (doSave) {
                            max = element;
                            maxName = name;
                        }
                    }
                    doSave=true;
                }
                it.remove();
                nameIterator.remove();
                System.out.println(max);
                maxValues[i] = max;
                maxName=removeWordsAfterComma(maxName);
                System.out.println(maxName);
                names[i] = maxName;
            }
        }
    }

    public static String chooseDir(Stage stage) {
        DirectoryChooser chooser = new DirectoryChooser();
        String fileAsString = "";
        File file = chooser.showDialog(stage);
        if (file != null) {
            fileAsString = file.toString();
        }
        return fileAsString;
    }

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Image Recognition");
        Button btn = new Button();
        BorderPane borderPane = new BorderPane();
        btn.setText("Choose directory");
        String dirPath;
        ListView<String> listView = new ListView<>();
        myImageView = new ImageView();
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String dirPath = chooseDir(primaryStage);
                System.out.println(dirPath);
                File[] listOfFiles = {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null};
                try {
                    listOfFiles = createDirList(dirPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ObservableList<String> items = FXCollections.observableArrayList(
                        listOfFiles[0].getName(), listOfFiles[1].getName(), listOfFiles[2].getName(), listOfFiles[3].getName(), listOfFiles[4].getName(), listOfFiles[5].getName(), listOfFiles[6].getName(), listOfFiles[7].getName(), listOfFiles[8].getName(), listOfFiles[9].getName(), listOfFiles[10].getName(), listOfFiles[11].getName(), listOfFiles[12].getName(), listOfFiles[13].getName(), listOfFiles[14].getName(), listOfFiles[15].getName());
                listView.setItems(items);
                listView.setEditable(true);
                listView.setOnEditStart(new EventHandler<ListView.EditEvent<String>>() {
                    @Override
                    public void handle(ListView.EditEvent event) {
                        System.out.println("dziala");
                        Map<String, Double> recognition = null;
                        try {
                            Indico indico = new Indico("e4c28af263a4422e0a86c17f7220e3b9");
                            String almostPath=listView.getSelectionModel().getSelectedItems().toString();
                            String fileName="";
                            System.out.println(almostPath.length());
                            for (int i=1;i<almostPath.length()-1;i++) {
                                fileName+=almostPath.charAt(i);
                            }
                            System.out.println(almostPath);
                            System.out.println();
                            File file = new File(dirPath+"\\"+fileName);

                            try {
                                BufferedImage bufferedImage = ImageIO.read(file);
                                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                                myImageView.setImage(image);
                            } catch (IOException ex) { }



                            IndicoResult single = indico.imageRecognition.predict(file);
                            recognition = (single.getImageRecognition());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        System.out.println("test");
                        findGreatestValues(recognition,maxValues,names);
                        System.out.println(maxValues[0]);
                        System.out.println(names[0]);

                        pieChart = FXCollections.observableArrayList(
                             new PieChart.Data(names[0],maxValues[0]), new PieChart.Data(names[1],maxValues[1]), new PieChart.Data(names[2],maxValues[2]), new PieChart.Data(names[3],maxValues[3]), new PieChart.Data(names[4],maxValues[4]), new PieChart.Data(names[5],maxValues[5]), new PieChart.Data(names[6],maxValues[6]), new PieChart.Data(names[7],maxValues[7]), new PieChart.Data(names[8],maxValues[8]), new PieChart.Data(names[9],maxValues[9]));
                        chart.setData(pieChart);
//                        PieChart chart = new PieChart(pieChart);
                    }
                });
//        StackPane root = new StackPane();
//        root.getChildren().add(btn);
//        primaryStage.setScene(new Scene(root, 300, 250));
//        primaryStage.show();
            }
        });
        borderPane.setRight(btn);
        borderPane.setLeft(listView);
        borderPane.setBottom(myImageView);
//        pieChart = FXCollections.observableArrayList(
//                new PieChart.Data(names[0],maxValues[0]), new PieChart.Data(names[1],maxValues[1])
//        );
        chart = new PieChart(pieChart);
        borderPane.setCenter(chart);
//        PieChart chart = new PieChart();
//        borderPane.setCenter(chart);
        Scene scene = new Scene(borderPane, 1000, 700);
//        ((Group) scene.getRoot()).getChildren().add(chart);
        chart.setTitle("What does this photo show?");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}