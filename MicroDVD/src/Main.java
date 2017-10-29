import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int lineNumber=0;
        try {
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
                for (int i=0;i<inputLines.size();i++) {
                    lineNumber++;
                    microDVD.delay(inputLines.get(i), test, 100, 60);
                    System.out.println(test);
                    outputLines.add(test);
                    System.out.println(outputLines.get(0));
                  }
                  System.out.println(outputLines.size());
            }catch (InvalidFramerate e) {
                System.out.println("Line number: "+lineNumber);
                for (int i=0;i<inputLines.get(lineNumber-1).length;i++)
                {
                    System.out.print(inputLines.get(lineNumber-1)[i]);
                }
                System.exit(1);
            }catch (InvalidFormatException e){
                System.out.println("Line number: "+lineNumber);
                for (int i=0;i<inputLines.get(lineNumber-1).length;i++)
                {
                    System.out.print(inputLines.get(lineNumber-1)[i]);
                }
                System.exit(2);
            }catch (NegativeFramesAfterShift e){
                System.out.println("Line number: "+lineNumber);
                for (int i=0;i<inputLines.get(lineNumber-1).length;i++)
                {
                    System.out.print(inputLines.get(lineNumber-1)[i]);
                }
                System.exit(3);
            }catch (TooLongOutput e) {
                System.out.println("Line number: "+lineNumber);
                for (int i=0;i<inputLines.get(lineNumber-1).length;i++)
                {
                    System.out.print(inputLines.get(lineNumber-1)[i]);
                }
                System.exit(4);
            }catch (Exception e) {
                System.out.println("Unknown exception.");
                System.out.println("Line number: "+lineNumber);
                for (int i=0;i<inputLines.get(lineNumber-1).length;i++)
                {
                    System.out.print(inputLines.get(lineNumber-1)[i]);
                }
                System.exit(5);
            }

            try
            {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(args[1]));
                System.out.println(outputLines.size());
                for (int z=0;z<outputLines.size();z++) {
                    System.out.println(outputLines.get(z));
                    bufferedWriter.write(outputLines.get(z));
                    bufferedWriter.write("\n");
                }
                bufferedWriter.close();
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) { }
    }
}
