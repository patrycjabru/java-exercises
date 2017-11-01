package com.company;
import io.indico.Indico;
import io.indico.api.results.IndicoResult;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;


//indicoio.config.api_key = "e4c28af263a4422e0a86c17f7220e3b9";

public class Main {
    public static void main(String[] args) throws Exception{
        try {
            Indico indico = new Indico("e4c28af263a4422e0a86c17f7220e3b9");
            String[] array = new String[16];
            array[0] = "D:\\JAVA_PROJEKTY\\ImageRecognition\\foto\\bocian.jpg";
            array[1] = "D:\\JAVA_PROJEKTY\\ImageRecognition\\foto\\kkott.jpg";
            array[2] = "D:\\JAVA_PROJEKTY\\ImageRecognition\\foto\\kon.jpg";
            array[3] = "D:\\JAVA_PROJEKTY\\ImageRecognition\\foto\\kon2.jpg";
            array[4] = "D:\\JAVA_PROJEKTY\\ImageRecognition\\foto\\kot.jpg";
            List<IndicoResult> results = new ArrayList<>();
            List<Map<String, Double>> recognition = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                IndicoResult single = indico.imageRecognition.predict(array[i]);
                results.add(single);
                recognition.add(single.getImageRecognition());
            }
//            System.out.println(recognition);
            double max=0;
            int indexOfMaxValue=0;
            int indexOfMaxValues[]=new int[16];
            for (int i=0;i<recognition.size();i++) {
//                if (recognition.get(i).values())
                Iterator it=recognition.get(i).values().iterator();
                max=0;
                indexOfMaxValue=0;
                int index=0;
                while (it.hasNext()) {
                    double element = (double)it.next();
//                    System.out.println(element);
//                    System.out.println(element.toString());
                    if (element>max) {
                        max=element;
                        indexOfMaxValue=index;
                    }
                    index++;
                }
                System.out.println(indexOfMaxValue);
                indexOfMaxValues[i]=indexOfMaxValue;
            }
            String namesForAllPictures[]=new String[recognition.size()];
            for (int i=0;i<recognition.size();i++) {
                String name="";
                Set<String> allNames=recognition.get(i).keySet();
//                System.out.println(allNames);
                Iterator it = allNames.iterator();
                int counter=0;
                while (it.hasNext()) {
                    String element=it.next().toString();
//                    System.out.println(" xxx "+element);
                    if (counter==indexOfMaxValues[i]) {
                        name=element;
                    }
                    counter++;
                }
                System.out.println(i+": "+name);
                namesForAllPictures[i]=name;
            }
            for (int i=0;i<namesForAllPictures.length;i++) {
                String oldName=namesForAllPictures[i];
                String newName="";
                for (int c=0;c<oldName.length();c++) {
                    if (oldName.charAt(c)==',') break;
                    newName+=oldName.charAt(c);
                }
                namesForAllPictures[i]=newName;
                System.out.println(newName);
            }
            for (int i=0;i<namesForAllPictures.length;i++) {
                File newDirectory = new File("D:\\JAVA_PROJEKTY\\ImageRecognition\\foto\\outputDirectories\\" + namesForAllPictures[i]);
                boolean successful = newDirectory.mkdir();
                if (successful) {
                    System.out.println("directory was created successfully");
                } else {
                    System.out.println("failed trying to create the directory");
                }
            }
            for (int i = 0; i < 1; i++) {
                BufferedImage img = ImageIO.read(new File("D:\\JAVA_PROJEKTY\\ImageRecognition\\foto\\bocian.jpg"));
                File outputfile = new File("D:\\JAVA_PROJEKTY\\ImageRecognition\\foto\\outputDirectories\\"+namesForAllPictures[i]+"\\"+i+".jpg");
                ImageIO.write(img, "jpg", outputfile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

