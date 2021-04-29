import java.io.IOException;

/**
 * the definition of the colors ansi code
 * i took it from stackOverFlow website
 */
public class ConsoleColors {

    int processBuilder = new ProcessBuilder("cmd" , "/c" , "color" , "07").inheritIO().start().waitFor();

    /**
     * as you know the black color is not printable in cmd
     * and if you want to be able to see , you  should change the background of the cmd
     * which is annoying
     * so the black color and white color ansi codes , are the same
     */
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";


    /**
     * i don't know what it is , my idea made it itself
     * @throws IOException
     * @throws InterruptedException
     */
    public ConsoleColors() throws IOException, InterruptedException {
    }
}