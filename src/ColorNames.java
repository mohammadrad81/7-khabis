/**
 * a class that connects the color ansi codes to their names
 * @author Mohammad Heydari Rad
 * @since 2021
 * @see ConsoleColors
 */
public class ColorNames {
    public static final String BLACK_NAME ="black";
    public static final String BLUE_NAME = "blue";
    public static final String RED_NAME = "red";
    public static final String GREEN_NAME = "green";


    /**
     *
     * @param colorName is the name of the color
     * @return the ansi code of the input color name
     */
    public static String colorNameToColorCode(String colorName){
        if(colorName.equals(BLUE_NAME)){
            return ConsoleColors.BLUE;
        }
        else if(colorName.equals(BLACK_NAME)){
            return ConsoleColors.BLACK;
        }
        else if(colorName.equals(RED_NAME)){
            return ConsoleColors.RED;
        }
        else if(colorName.equals(GREEN_NAME)){
            return ConsoleColors.GREEN;
        }
        else{
            return ConsoleColors.WHITE;
        }
    }
}
