public class InvalidFormatException extends Exception {
    public InvalidFormatException() {
        super();
    }
    public InvalidFormatException(String m) {
        super(m);
        System.out.println(m);
    }
}
