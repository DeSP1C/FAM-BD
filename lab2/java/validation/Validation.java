package validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static final String regexName = "^[a-zA-Z]+$";
    private static final String regexCatalogueName = "^[a-zA-Z0-9]+$";
    private static final String regexEmail = "^[a-zA-Z0-9.]+@[a-zA-Z]+\\.[a-zA-Z]+$";
    private static final Pattern patternName = Pattern.compile(regexName);
    private static final Pattern patternCatalogueName = Pattern.compile(regexCatalogueName);
    private static final Pattern patternEmail = Pattern.compile(regexEmail);

    public static boolean isName(String name){
        if(name == null)
            return false;
        Matcher matcher = patternName.matcher(name);
        return matcher.matches();
    }

    public static boolean isCatalogueName(String name){
        if(name == null)
            return false;
        Matcher matcher = patternCatalogueName.matcher(name);
        return matcher.matches();
    }

    public static boolean isEmail(String email){
        if(email == null)
            return false;
        Matcher matcher = patternEmail.matcher(email);
        return matcher.matches();
    }

    public static boolean isView(int view){
        return view >= 0;
    }

    public static boolean isID(int ID){
        return ID > 0;
    }
}