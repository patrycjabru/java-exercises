public class InvalidFramerate extends Exception {
    public InvalidFramerate() {
        super();
        System.out.println("wyjatek");
    }
    public InvalidFramerate(String m) {
        super(m);
        System.out.println(m);
    }
}
