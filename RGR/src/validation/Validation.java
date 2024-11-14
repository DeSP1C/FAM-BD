package validation;

public class Validation {
    private static final String regexName = "^[a-zA-Z]+$";
    private static final String regexEmail = "^[a-zA-Z0-9.]+@[a-zA-Z]+\\.[a-zA-Z]+$";

    public static boolean isName(String name){
        if(name == null)
            return false;
        String lowerInput = name.toLowerCase();
        return lowerInput.matches(regexName);
    }

    public static boolean isEmail(String email){
        if(email == null)
            return false;
        boolean result = email.matches(regexEmail);
        return result;
    }

    public static boolean isView(int view){
        return view >= 0;
    }

    public static boolean isID(int ID){
        return ID > 0;
    }
}