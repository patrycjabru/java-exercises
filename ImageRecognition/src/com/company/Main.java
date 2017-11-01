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
            File dir = new File("D:\\JAVA_PROJEKTY\\ImageRecognition\\foto");
            File[] directoryListing = dir.listFiles();
            List<Map<String, Double>> recognition = new ArrayList<>();
            for (int i = 0; i < directoryListing.length; i++) {
                IndicoResult single = indico.imageRecognition.predict(directoryListing[i]);
                recognition.add(single.getImageRecognition());
            }
            double max=0;
            int indexOfMaxValue=0;
            int indexOfMaxValues[]=new int[directoryListing.length];
            for (int i=0;i<recognition.size();i++) {
                Iterator it=recognition.get(i).values().iterator();
                max=0;
                indexOfMaxValue=0;
                int index=0;
                while (it.hasNext()) {
                    double element = (double)it.next();
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
                Iterator it = allNames.iterator();
                int counter=0;
                while (it.hasNext()) {
                    String element=it.next().toString();
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
                File newDirectory = new File("D:\\JAVA_PROJEKTY\\ImageRecognition\\outputDirectories\\" + namesForAllPictures[i]);
                boolean successful = newDirectory.mkdir();
                if (successful) {
                    System.out.println("directory was created successfully");
                } else {
                    System.out.println("failed trying to create the directory");
                }
            }
            for (int i = 0; i < directoryListing.length; i++) {
                BufferedImage img = ImageIO.read(new File("D:\\JAVA_PROJEKTY\\ImageRecognition\\foto\\"+directoryListing[i].getName()));
                File outputFile = new File("D:\\JAVA_PROJEKTY\\ImageRecognition\\outputDirectories\\"+namesForAllPictures[i]+"\\"+directoryListing[i].getName());
                ImageIO.write(img, "jpg", outputFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

