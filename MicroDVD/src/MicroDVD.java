public class MicroDVD {
    public void delay(char in[], char out[], int delay, int fps) throws InvalidFramerate, InvalidFormatException, NegativeFramesAfterShift, TooLongOutput{
        if (fps<0) throw new InvalidFramerate("Negative framerate!");
        String stringFrameNumber="";
        int integerFrameNumber=0;
        boolean isNumber=false;
        boolean recalculate=false;
        int j=0;
        int diff=0;
        for (int i=0;i<2048;i++) {
            if (in[i]=='}') {
                isNumber=false;
                recalculate=true;
            }
            if (isNumber) {
                stringFrameNumber+=in[i];
            }
            if (in[i]=='{') {
                isNumber=true;
            }
            if (recalculate){
                for(int k=0;k<stringFrameNumber.length();k++) {
                    if (stringFrameNumber.charAt(k)<48 || stringFrameNumber.charAt(k)>57) throw new InvalidFormatException("Framerate is not a number");
                }
                integerFrameNumber=Integer.parseInt(stringFrameNumber);
                System.out.println(integerFrameNumber);
                integerFrameNumber+=delay*fps/1000; //Nie wiem czy dzielic
                System.out.println(integerFrameNumber);
                if (Integer.toString(integerFrameNumber).length()!=stringFrameNumber.length()) {
                    diff+=Integer.toString(integerFrameNumber).length()-stringFrameNumber.length();
                }
                if (integerFrameNumber<0) throw new NegativeFramesAfterShift("Negative frames after shift!");
                stringFrameNumber=Integer.toString(integerFrameNumber);     //stringFrameNumber after shift
                stringFrameNumber='{'+stringFrameNumber;
                for (int k=0;k<stringFrameNumber.length();k++) {
                    if (k==2048) throw new TooLongOutput();
                    out[j]=stringFrameNumber.charAt(k);
                    j++;
                }
                stringFrameNumber="";
                recalculate=false;
            }
            if (!recalculate && !isNumber) {
                out[j]=in[i];
                j++;
            }

        }
    }
//    public void shiftAllSubtitlesBy(int delay,int fps) {
//
//    }
}
