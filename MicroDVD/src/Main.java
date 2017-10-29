import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            int lineNumber=0;
            List<char[]> inputLines = new ArrayList<char[]>();
            try
            {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]));
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    char charLine[] = new char[2048];
                    for (int c=0;c<line.length();c++) {
                        charLine[c]=line.charAt(c);
                    }
                    inputLines.add(charLine);
                }
                bufferedReader.close();
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<char[]> outputLines = new ArrayList<char[]>();
            char[] test=new char[2048];
            MicroDVD microDVD = new MicroDVD();
            try {
                for (int i=0;i<inputLines.size()-500;i++) {
                    lineNumber++;
                    microDVD.delay(inputLines.get(i), test, 1000, 60);
                    System.out.println(test);
                  }
            }catch (InvalidFramerate e) {
                System.out.println("Line number: "+lineNumber);
                System.exit(1);
            }catch (InvalidFormatException e){
                System.out.println("Line number: "+lineNumber);
                System.exit(2);
            }catch (NegativeFramesAfterShift e){
                System.out.println("Line number: "+lineNumber);
                System.exit(3);
            }catch (TooLongOutput e) {
                System.out.println("Line number: "+lineNumber);
                System.exit(4);
            }
            outputLines.add(test);
            try
            {
                System.out.println("aaaaa");
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(args[1]));
                System.out.println("abbba");
                for (int i=0;i<outputLines.size();i++) {
                    bufferedWriter.write(outputLines.get(i));
                    bufferedWriter.write("\n");
                }
                bufferedWriter.close();
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {}
    }
}
