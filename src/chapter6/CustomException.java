package chapter6;

public class CustomException extends Exception {

    public CustomException(String arg0, Throwable arg1) {
        super("Custom: " + arg0, arg1);
    }

    public CustomException(String arg0) {
        super("Custom: " + arg0);
    }
    
    static void throwACustom() throws CustomException {
        throw new CustomException("Ahaha! a customized Exception!");
    }

    public static void main(String[] args) {
        try {
            throwACustom();
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }
}
