import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main Class for the dirty 7 game
 * @author Mohammad Heydari rad
 * @since 2021
 * @see Game
 * @see Card
 */
public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String args[]) throws IOException, InterruptedException {
        int processBuilder = new ProcessBuilder("cmd" , "/c" , "color" , "00").inheritIO().start().waitFor();
        // i don't know how but it fixes the problem of colors in cmd
        System.out.println(ConsoleColors.CYAN + "!!! WELCOME TO RAD EVIL 7 GAME !!!" + ConsoleColors.RESET);
        mainMenu();
    }

    /**
     * the main menu of the game
     * @throws InterruptedException idk what it is
     */
    public static void mainMenu() throws InterruptedException {


        int input = 0;
        while (input != 3){
            System.out.println("1.Play with bots");
            System.out.println("2.Play together");
            System.out.println("3.Exit");
            input = scanner.nextInt();
            if (input == 1){
                playWithBots();
            }
            else if(input == 2){
                playTogether();
            }
            else if(input == 3){
                return;
            }
            else {
                printNotValidInput();
            }

        }
    }

    /**
     * prints that the input is not valid
     */
    public static void printNotValidInput(){
        System.out.println("Not valid input !");
        System.out.println("try again : ");
    }

    /**
     * playing with bots
     * @throws InterruptedException idk
     */
    public static void playWithBots() throws InterruptedException {
        int botCount;
        int humanCount;
        System.out.println("Enter the number of bots (less than 5) (Enter -1 back to menu) : ");
        botCount = scanner.nextInt();
        scanner.nextLine();
        if(botCount == -1) {
            return;
        }
        else if(botCount < 1 || botCount >= 5){
            printNotValidInput();
            playWithBots();
            return;
        }

        else {
            System.out.println("Enter the number of players (less than " + (6 - botCount ) +")" + "(Enter -1 back to menu )" );
            humanCount  = scanner.nextInt();
            scanner.nextLine();
            if(humanCount == -1){
                return;
            }

            else if(humanCount <= 0 || humanCount+botCount > 5 ){
                printNotValidInput();
                playWithBots();
                return;
            }else if(humanCount + botCount <3){
                System.out.println("there should be at least 3 players");
                playWithBots();
                return;
            }
            else {

//                    ArrayList<String> playersNames = mergeTwoNameArrayLists(setHumansNames(humanCount) , setBotsNames(botCount));

                Game game = new Game(botCount,
                                humanCount,
                                mergeTwoPlayersArrayLists(humanPlayerArrayList(setHumansNames(humanCount)) ,
                                botsPlayerArrayList(setBotsNames(botCount))));

                    game.gameBegins();
            }
        }
    }

    /**
     * playing all real players
     * @throws InterruptedException idk
     */
    public static void playTogether() throws InterruptedException {
        int humanCount;
        System.out.println("Enter the count of the players (at least 3 and most 5) (Enter -1 to back to menu) :");
        humanCount = scanner.nextInt();
        scanner.nextLine();
        if(humanCount == -1){
            return;
        }
        else if(humanCount < 3 || humanCount >5){
            printNotValidInput();
            playTogether();
            return;
        }

        Game game = new Game(0 , humanCount , humanPlayerArrayList(setHumansNames(humanCount)));
        game.gameBegins();
        return;
    }

    /**
     * sets the humans names return an arrayList of the names
     * @param count the count of the human players
     * @return  an arrayList of the names
     */
    public static ArrayList<String> setHumansNames(int count){
        ArrayList<String> names = new ArrayList<>();
        String name;
        for(int i = 1; i <= count ; i++){
            System.out.println("Enter the name of player number " + i +" : ");
            name = scanner.nextLine();
            names.add(name);
        }
        return names;
    }

    /**
     * sets the name of the bots names
     * @param count the bots count
     * @return the arrayList of the bots' names
     */
    public static ArrayList<String> setBotsNames(int count){
        ArrayList<String> botsNames = new ArrayList<>();
        String name;
        for(int i = 1; i <= count; i++){
            name = "bot number " + i;
            botsNames.add(name);
        }
        return botsNames;
    }

    /**
     * merges Two arrayList of the players
     * @param list1
     * @param list2
     * @return the merged arrayList
     */
    public static ArrayList<Player> mergeTwoPlayersArrayLists(ArrayList<Player> list1 , ArrayList<Player> list2){
        ArrayList<Player> mergedList = new ArrayList<>();
        for(int i = 0; i<list1.size(); i++){
            mergedList.add(list1.get(i));
        }
        for(int i = 0; i < list2.size(); i++){
            mergedList.add(list2.get(i));
        }
        return mergedList;
    }

    /**
     * creates an arrayList of human players by their nameds
     * @param humansNames is the list of players names
     * @return the player arrayList
     */
    public static ArrayList<Player> humanPlayerArrayList(ArrayList<String> humansNames){
        ArrayList<Player> humans = new ArrayList<>();
        for(String humanName : humansNames){
            humans.add(new Player(humanName));
        }
        return humans;
    }

    /**
     * creates an arrayList of bots players by their names
     * @param botsNames is the list of bots names
     * @return the arrayList of bots
     */
    public static ArrayList<Player> botsPlayerArrayList(ArrayList<String> botsNames) {
        ArrayList<Player> bots = new ArrayList<>();
        for(String botName : botsNames){
            bots.add(new Bot(botName));
        }
        return bots;
    }

}
