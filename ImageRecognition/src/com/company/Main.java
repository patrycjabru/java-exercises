package com.company;
import io.indico.Indico;
import io.indico.api.results.IndicoResult;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;


//indicoio.config.api_key = "e4c28af263a4422e0a86c17f7220e3b9";

public class Main {
    public static File[] createDirList(String path) throws Exception{
        File[] directoryListing=null;
        try {
            File dir = new File(path);
            if (!dir.exists()) throw new DirectoryDoesNotExist();
            directoryListing = dir.listFiles();
            for (int i = 0; i < directoryListing.length; i++) {
                if (ImageIO.read(directoryListing[i]) == null) throw new FileIsNotAnImage();
            }
        }catch (Exception e) {
            throw e;
        }
        return directoryListing;
    }

    public static List<Map<String, Double>> createRecognitionList (File[] directoryListing) throws Exception{
        List<Map<String, Double>> recognition = new ArrayList<>();
        try {
            Indico indico = new Indico("e4c28af263a4422e0a86c17f7220e3b9");
            for (int i = 0; i < directoryListing.length; i++) {
                IndicoResult single = indico.imageRecognition.predict(directoryListing[i]);
                recognition.add(single.getImageRecognition());
            }
        } catch (Exception e) {
            throw e;
//            System.out.println("Could not resolve host");
//            System.out.println("Invalid api key");
        }
        return recognition;
    }
    public static int[] calculateNumbersOfIndexes(File[] directoryListing, List<Map<String, Double>> recognition) {
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
        return indexOfMaxValues;
    }
    public static String[] calculateNames(int[] indexOfMaxValues,List<Map<String, Double>> recognition) {

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
        return namesForAllPictures;
    }
    public static void createDirectories(String[] namesForAllPictures) {
        for (int i=0;i<namesForAllPictures.length;i++) {
            File newDirectory = new File("D:\\JAVA_PROJEKTY\\ImageRecognition\\outputDirectories\\" + namesForAllPictures[i]);
            boolean successful = newDirectory.mkdir();
            if (successful) {
                System.out.println("directory was created successfully");
            } else {
                System.out.println("failed trying to create the directory");
            }
        }
    }
    public static void saveFiles(File[] directoryListing, String[] namesForAllPictures) {
        try {
            for (int i = 0; i < directoryListing.length; i++) {
                BufferedImage img = ImageIO.read(new File("D:\\JAVA_PROJEKTY\\ImageRecognition\\foto\\" + directoryListing[i].getName()));
                File outputFile = new File("D:\\JAVA_PROJEKTY\\ImageRecognition\\outputDirectories\\" + namesForAllPictures[i] + "\\" + directoryListing[i].getName());
                ImageIO.write(img, "jpg", outputFile);
            }
        } catch (Exception e) {System.out.println("Something went wrong in function saveFiles");}
    }
    public static void main(String[] args) throws Exception{
        try {
            File[] directoryListing=null;
            try {
                directoryListing = createDirList("D:\\JAVA_PROJEKTY\\ImageRecognition\\foto");
            } catch (DirectoryDoesNotExist |FileIsNotAnImage e) {
                System.exit(1);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(2);
            }
            List<Map<String, Double>> recognition=null;
            try {
                recognition = createRecognitionList(directoryListing);
            } catch (java.net.UnknownHostException e) {
                System.out.println("Could not resolve host");
                System.exit(3);
            } catch (io.indico.api.utils.IndicoException e) {
                System.out.println("invalid api key");
                System.exit(4);
            } catch (Exception e) {
                System.out.println("Something went wrong in function createRecognitionList");
                e.printStackTrace();
                System.exit(5);
            }
            int indexOfMaxValues[]= calculateNumbersOfIndexes(directoryListing,recognition);
            String[] namesForAllPictures=calculateNames(indexOfMaxValues,recognition);
            createDirectories(namesForAllPictures);
            saveFiles(directoryListing,namesForAllPictures);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


