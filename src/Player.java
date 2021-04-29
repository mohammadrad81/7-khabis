import java.util.ArrayList;
import java.util.Scanner;

/**
 * class of the dirty 7  game players
 * @author Mohammad Heydari Rad
 * @since 2021
 * @see Card
 */
public class Player {
        private String name;
        private int score;
        private ArrayList<Card> hand;

        public Player (String name){
            this.name = name;
            score = 0;
            hand = new ArrayList<>();
        }

    /**
     * player takes a card
     * @param card taking card
     */
    public void takeACard(Card card){
            hand.add(card);
    }

    /**
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * sets the player name
     * @param name is the players name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the score of the player
     */
    public int getScore() {
            score = calculateScore();
            return score;
    }

    /**
     * calculates the score of the players hand
     * @return
     */
    private int calculateScore(){
            int score = 0;
            for(Card card : hand){
                score += card.getScore();
            }
            return score;
    }

    /**
     * sets the score of the player
     * @param score is the input score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     *
     * @return the hand of the player
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * sets the hand of the player
     * @param hand
     */
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    /**
     *
     * @return true if the player has no cards , else false
     */
    public boolean isWinner(){
            if(hand.size() == 0){
                return true;
            }
            return false;
    }

    /**
     * prints the hand of the player
     */
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

    /**
     * player chooses to punish some one for card 2
     * @param players other players
     * @return the chosen player
     */
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
                System.out.println((i+1) + "- " +  allPlayersButThis.get(i).name);
            }
            chosenPlayerIndex = scanner.nextInt();
//            scanner.nextLine();
            if(chosenPlayerIndex <=0 || chosenPlayerIndex > allPlayersButThis.size()){
                System.out.println("Not valid input!");
                System.out.println("please try again :");
                return chooseToPunish(players);
            }
            return allPlayersButThis.get(chosenPlayerIndex - 1);
    }

    /**
     *
     * @return true if player has card 7 , else false
     */
    public boolean has7(){
            for(Card card : hand){
                if(card instanceof Seven){
                    return true;
                }
            }
            return false;
    }

    /**
     * player chooses a playable card on a normal card
     * @param sign is the valid sign to play on
     * @param colorName is the valid color name to play on
     * @return
     */
    public Card  chooseFromHand(String sign ,  String colorName){
            Scanner scanner = new Scanner(System.in);
            Card chosenCard = null;
            int cardIndex;
            do{
                System.out.println("which cart ? (-1 to pick up card from cardDeck) ?");
                 cardIndex =scanner.nextInt();
//                 scanner.nextLine();
                 if(cardIndex == -1){
                     ArrayList<Card> playableCards = playableNormalCards(sign , colorName);
                     if(playableCards.size() == 0){
                         return null;
                     }
                     else {
                         System.out.println("you still can play : ");
                         for(Card card : playableCards){
                             System.out.print(card + " ");
                         }
                         System.out.println();
                     }
                 }
                 else if(cardIndex<=0 ||cardIndex > hand.size()){
                     System.out.println("not valid input");
                     System.out.println("please try again : ");
                 }
                 else if(hand.get(cardIndex-1).getSign().equals("B")){
                     chosenCard = hand.get(cardIndex -1);
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

    /**
     * if player has 7 and has to play 7
     * @return the card 7 the player has
     */
    public Card play7(){
        Scanner scanner = new Scanner(System.in);
        Card chosenCard = null;
        int cardIndex;
        do{
            System.out.println("which card ? (it should be 7 )");
            cardIndex = scanner.nextInt();
//            scanner.nextLine();
            if(cardIndex<=0 || cardIndex > hand.size()){
                System.out.println("not valid input");
                System.out.println("please try again : ");
            }
            else if((!(hand.get(cardIndex-1).getSign().equals("7")))){
                System.out.println("! it is not a seven card !");
                System.out.println("please try again : ");
            }
            else{
                chosenCard = hand.get(cardIndex - 1);
            }

        }while(cardIndex <= 0 || cardIndex > hand.size() || chosenCard == null);


        hand.remove(chosenCard);
        return chosenCard;


    }

    /**
     * player changes the valid color of the game
     * @return the chosen color name
     */
    public String chooseColorName(){
        Scanner scanner = new Scanner(System.in);
        int nextColorIndex;
        System.out.println("what color ? :");
        System.out.println("1-black");
        System.out.println("2-red");
        System.out.println("3-green");
        System.out.println("4-blue");
        nextColorIndex = scanner.nextInt();
//        scanner.nextLine();
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

    /**
     * shows the playable cards
     * @param sign is the valid sign to play
     * @param colorName the valid color name to play
     * @return the array list of playable cards
     */
    public ArrayList<Card> playableNormalCards(String sign , String colorName){
            ArrayList<Card> playableNormalCardArrayList= new ArrayList<>();
            for(Card card : hand){
                if(card.getSign().equals(sign) ||
                    card.getColorName().equals(colorName) ||
                        (card instanceof B)){
                    playableNormalCardArrayList.add(card);
                }
            }
            return playableNormalCardArrayList;
    }

    /**
     * returns the name and score of the player in a string
     * @return
     */
    @Override
    public String toString(){
            return (this.name + " " + this.score);
    }

}
