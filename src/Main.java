import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String args[]) throws IOException, InterruptedException {
        int processBuilder = new ProcessBuilder("cmd" , "/c" , "color" , "00").inheritIO().start().waitFor();
        System.out.println(ConsoleColors.CYAN + "!!! WELCOME TO RAD EVIL 7 GAME !!!" + ConsoleColors.RESET);
        mainMenu();
    }

    public static void mainMenu(){


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

    public static void printNotValidInput(){
        System.out.println("Not valid input !");
        System.out.println("try again : ");
    }

    public static void playWithBots(){
        int botCount;
        int humanCount;
        System.out.println("Enter the number of bots (less than 5) (Enter -1 back to menu) : ");
        botCount = scanner.nextInt();
        scanner.nextLine();
        if(botCount == -1) {
            return;
        }
        else if(botCount <1 || botCount >= 5){
            printNotValidInput();
            playWithBots();
        }

        else {
            System.out.println("Enter the number of players (less than " + (5 - botCount + 1) +")" + "(Enter -1 back to menu )" );
            humanCount  = scanner.nextInt();
            scanner.nextLine();
            if(humanCount == -1){
                return;
            }

            else if(humanCount <= 0 || humanCount+botCount > 5){
                printNotValidInput();
                playWithBots();
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

    public static void playTogether(){
        int humanCount;
        System.out.println("Enter the count of the players (Enter -1 to back to menu) :");
        humanCount = scanner.nextInt();
        scanner.nextLine();
        if(humanCount == -1){
            return;
        }
        else if(humanCount <= 0 || humanCount >5){
            printNotValidInput();
            playTogether();
            return;
        }
        Game game = new Game(0 , humanCount , humanPlayerArrayList(setHumansNames(humanCount)));
        game.gameBegins();
        return;
    }

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

    public static ArrayList<String> setBotsNames(int count){
        ArrayList<String> botsNames = new ArrayList<>();
        String name;
        for(int i = 1; i <= count; i++){
            name = "bot number " + i;
            botsNames.add(name);
        }
        return botsNames;
    }

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

    public static ArrayList<Player> humanPlayerArrayList(ArrayList<String> humansNames){
        ArrayList<Player> humans = new ArrayList<>();
        for(String humanName : humansNames){
            humans.add(new Player(humanName));
        }
        return humans;
    }

    public static ArrayList<Player> botsPlayerArrayList(ArrayList<String> botsNames) {
        ArrayList<Player> bots = new ArrayList<>();
        for(String botName : botsNames){
            bots.add(new Bot(botName));
        }
        return bots;
    }

}
