import java.util.ArrayList;
import java.util.Scanner;

public class Player {
        private String name;
        private int score;
        private ArrayList<Card> hand;

        public Player (String name){
            this.name = name;
            score = 0;
            hand = new ArrayList<>();
        }
    public void takeACard(Card card){
            hand.add(card);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
            score = calculateScore();
            return score;
    }

    private int calculateScore(){
            int score = 0;
            for(Card card : hand){
                score += card.getScore();
            }
            return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public boolean isWinner(){
            if(hand.size() == 0){
                return true;
            }
            return false;
    }

    public void printHand(){
        System.out.println();
            for(int i = 1 ; i <= 6; i++){
                for(int j = 0 ; j < hand.size(); j++){
                    if(j == hand.size()-1){
                        System.out.print(hand.get(j).fullRowString(i));
                    }
                    else{
                        System.out.print(hand.get(j).halfRowString(i));
                    }
                }
                System.out.println();
            }
        System.out.println();
            for(int k = 0; k < hand.size() ; k++){
                if(k >= 10){
                    System.out.print(" " + (k+1) + "   ");
                }
                else{
                    System.out.print(" " + (k+1) + "    ");
                }
            }
        System.out.println();
    }

    public Player chooseToPunish(ArrayList<Player> players){
            Scanner scanner = new Scanner(System.in);
            ArrayList<Player> allPlayersButThis = new ArrayList<>();
            for(Player player : players){
                if(player != this){
                    allPlayersButThis.add(player);
                }
            }
            int chosenPlayerIndex;
            System.out.println("choose a player to punish : ");
            for(int i = 0; i < allPlayersButThis.size(); i++){
                System.out.println((i+1) + allPlayersButThis.get(i).name);
            }
            chosenPlayerIndex = scanner.nextInt();
            scanner.nextLine();
            if(chosenPlayerIndex <=0 || chosenPlayerIndex > allPlayersButThis.size()){
                System.out.println("Not valid input!");
                System.out.println("please try again :");
                return chooseToPunish(players);
            }
            return allPlayersButThis.get(chosenPlayerIndex - 1);
    }
    public boolean has7(){
            for(Card card : hand){
                if(card instanceof Seven){
                    return true;
                }
            }
            return false;
    }

    public Card  chooseFromHand(String sign ,  String colorName){
            Scanner scanner = new Scanner(System.in);
            Card chosenCard = null;
            int cardIndex;
            do{
                System.out.println("which cart ? (-1 to pick up a card from cardDeck) ?");
                 cardIndex =scanner.nextInt();
                 scanner.nextLine();
                 if(cardIndex == -1){
                     return null;
                 }
                 else if(cardIndex<=0 ||cardIndex > hand.size()){
                     System.out.println("not valid input");
                     System.out.println("please try again : ");
                 }
                 else if((!(hand.get(cardIndex-1).getSign().equals(sign))) &&
                         (!(hand.get(cardIndex-1).getColorName().equals(colorName)))){
                     System.out.println("not playable card");
                     System.out.println("please try again : ");
                 }
                 else{
                     chosenCard = hand.get(cardIndex - 1);
                 }

            }while(cardIndex <= 0 || cardIndex > hand.size() || chosenCard == null);


            hand.remove(chosenCard);
            return chosenCard;
    }
    public Card playAnyOfAColor(String colorName){

        Scanner scanner = new Scanner(System.in);
        Card chosenCard = null;
        int chosenCardIndex;

        if(! hasCardsOfTheColor(colorName)){
            return null;
        }

        do{
            System.out.println("play a card of color : "+ colorName );
            System.out.println("which card ? :");
            chosenCardIndex = scanner.nextInt();
            scanner.nextLine();
            if(chosenCardIndex > hand.size() || chosenCardIndex <= 0){
                System.out.println("not valid input");
                System.out.println("please try again ");
            }
            else if(!(hand.get(chosenCardIndex-1).getColorName().equals(colorName))){
                System.out.println("this card is not a card of color " + colorName);
            }
        }while (!(hand.get(chosenCardIndex-1).getColorName().equals(colorName)));

        chosenCard = hand.get(chosenCardIndex-1);
        return chosenCard;

    }
    public Card play7(){
            Card sevenCard = chooseFromHand("7" , "only 7 is valid");
            if(! (sevenCard instanceof Seven)){
                return null;
            }
            return sevenCard;
    }

    public boolean hasCardsOfTheColor(String colorName){
            for(Card card : hand){
                if (card.getColorName().equals(colorName)){
                    return true;
                }

            }
            return false;
    }

    public String chooseColorName(){
        Scanner scanner = new Scanner(System.in);
        int nextColorIndex;
        System.out.println("what color ? :");
        System.out.println("1-black");
        System.out.println("2-red");
        System.out.println("3-green");
        System.out.println("4-blue");
        nextColorIndex = scanner.nextInt();
        scanner.nextLine();
        if (nextColorIndex > 4 || nextColorIndex < 1) {
            System.out.println("not valid input");
           return chooseColorName();
        } else {
            if (nextColorIndex == 1) {
                return (ColorNames.BLACK_NAME);
            }
            else if (nextColorIndex == 2) {
                return (ColorNames.RED_NAME);
            }
            else if (nextColorIndex == 3) {
                return (ColorNames.GREEN_NAME);
            }
            else {
                return (ColorNames.BLUE_NAME);
            }

        }

    }



}
