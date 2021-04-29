import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

/**
 * the game class
 * @author Mohammad Heydari Rad
 * @since 2021
 * @see GameParameters
 * @see Card
 * @see ColorNames
 * @see ConsoleColors
 */
public class Game {
    private ArrayList<Player> players;
    private GameParameters gameParameters;
    private CardDeck cardDeck;
    private Card lastCard;
    private Player currentPlayer;
    private Scanner scanner = new Scanner(System.in);
    private Random random = new Random();

    private String validColorName = null;
    private String validSign = null;


    /**
     * the game with inputs conditions
     * @param bots is the count of bots
     * @param humans is the count of humans ( real players)
     * @param players is the players
     */
    public Game(int bots , int humans , ArrayList<Player> players){
        this.players = new ArrayList<>();
        this.gameParameters = new GameParameters();
        this.cardDeck = new CardDeck();
        for(Player player : players){
            this.players.add(player);
        }

        this.players = initialPlayers(this.players);
    }

    /**
     * it initials the players to be designed randomly
     * and the first player should be a human
     * @param players
     * @return
     */
    private ArrayList<Player> initialPlayers(ArrayList<Player> players){
        ArrayList<Player> initializedPlayers = new ArrayList<>();
        while (players.size() > 0){
            Player player = players.get(random.nextInt(players.size()));
            if(! (player instanceof Bot)){
                initializedPlayers.add(player);
                players.remove(player);
                break;
            }
        }

        while (players.size() > 0){
            Player player = players.get(random.nextInt(players.size()));
            initializedPlayers.add(player);
            players.remove(player);
        }

        return initializedPlayers;
    }
    /**
    * the game and it's logics
     * it is too long and every one that knows the game understands the code , else , i doubt
     */
    public void gameBegins() throws InterruptedException {

        printPlayersInClockwiseDirection();

        boolean mustPlay7 = false;
        Card playingCard = null;
        giveEachPlayer7Cards(players);

        initialFirstCard();
        validColorName = lastCard.getColorName();
        validSign = lastCard.getSign();

        currentPlayer = players.get(0);

       do{
           if(gameParameters.isJump()){
               currentPlayer = nextPlayer(currentPlayer , gameParameters.getStep());
               resetGameParameters();
               delay();
               System.out.println();
               continue;
           }

           if(gameParameters.isPrize()){
               currentPlayer = nextPlayer(currentPlayer , (-1) * gameParameters.getStep());
               resetGameParameters();
               delay();
               System.out.println();
               continue;
           }


           if(gameParameters.isChooseToPunish()){
               currentPlayer = nextPlayer(currentPlayer , (-1) * gameParameters.getStep());
               punishSomeOneForCard2(currentPlayer);
               resetGameParameters();
               currentPlayer = nextPlayer(currentPlayer , gameParameters.getStep());
               delay();
               System.out.println();
               continue;
           }
           if(gameParameters.isChooseColor()){
               currentPlayer = nextPlayer(currentPlayer , (-1) * gameParameters.getStep());
               validColorName = currentPlayer.chooseColorName();
               validSign = lastCard.getSign();
               currentPlayer = nextPlayer(currentPlayer , gameParameters.getStep());
               resetGameParameters();
               delay();

               System.out.println();
               continue;
           }

           System.out.println(currentPlayer.getName());
           System.out.println("Score : " + currentPlayer.getScore());
           System.out.println("valid sign is " + validSign);
           System.out.println("valid color is " + validColorName);
           printDirection();
           printLastCard();

           if(gameParameters.getPunishNext() != 0){
                if(currentPlayer.has7()){
                    mustPlay7 = true;
                }
                else{
                    System.out.println( currentPlayer.getName() + " cards are : ");
                    currentPlayer.printHand();
                    punishPlayerForCard7(gameParameters.getPunishNext());
                    currentPlayer = nextPlayer(currentPlayer , gameParameters.getStep());
                    resetGameParameters();
                    mustPlay7 = false;
                    delay();
                    System.out.println();
                    continue;

                }
           }

           currentPlayer.printHand();

           if(mustPlay7){
               System.out.println("!! player must play card 7 !! ");
                playingCard = currentPlayer.play7();
                resetGameParametersButPunishNext();
           }

           else{
                playingCard = currentPlayer.chooseFromHand(validSign , validColorName);
           }


           if(playingCard == null){
               System.out.println(currentPlayer.getName() + " picks a card from card deck");
               Card addingCard = cardDeck.giveOneCard();
               currentPlayer.takeACard(addingCard);
               currentPlayer.printHand();
               System.out.println(currentPlayer.getName() + " took a card" + addingCard.toString());
               if(isCardPlayable(validSign , validColorName , addingCard)){
                   System.out.print(" and played it ");
                   System.out.println();
                   playingCard = addingCard;
                   cardDeck.addCardsToDeck(addingCard);
                   currentPlayer.getHand().remove(addingCard);
               }
               else {
                   System.out.println( currentPlayer.getName() + " can not play the taken card");
               }

           }

           if(playingCard instanceof Actionable){
               resetGameParametersButPunishNext();
               ((Actionable) playingCard).action(gameParameters);
           }
           else{
               resetGameParameters();
           }
            cardDeck.addCardsToDeck(lastCard);


            if(playingCard != null){
                lastCard = playingCard;
                System.out.println(currentPlayer.getName() + " played " + lastCard.toString());
            }

           currentPlayer = nextPlayer(currentPlayer , gameParameters.getStep());

           validColorName = lastCard.getColorName();
           validSign = lastCard.getSign();

           System.out.println();
           System.out.println();
           delay();
       }while (getWinnerIndex() == -1);

       printWinner();
       players = sortPlayersByScore(players);
       printLosersNamesAndScores();


    }

    /**
     * at the start of the game , each player has 7 cards
     * @param players
     */
    public void giveEachPlayer7Cards (ArrayList<Player> players){
        for(Player player : players){
            for(int i = 1; i <= 7; i++){
                player.takeACard(cardDeck.giveOneCard());
            }
        }
    }

    /**
     * initials the first card , it should not be an actionable card
     */
    public void initialFirstCard(){
        Card card ;

        do{
            card = cardDeck.giveOneCard();
        }while(card instanceof Actionable);

        lastCard = card;
    }

    /**
     * the index of the player that has no card in his/her/its hand
     * @return  the index , else -1
     */
    public int getWinnerIndex(){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).isWinner()){
                return i;
            }
        }

        return -1;
    }

    /**
     * prints the last card , ( in the start of the game , first card)
     */
    public void printLastCard(){

        System.out.println("Top card : ");
        for(int i = 1; i <= 6; i++){
            System.out.println(lastCard.fullRowString(i));
        }
        System.out.println();
    }

    /**
     * returns the next player of the current player , by the step
     * @param currentPlayer
     * @param step
     * @return the next player
     */
    public Player nextPlayer(Player currentPlayer , int step){
       int currentIndex = players.indexOf(currentPlayer);
       int nextPlayerIndex = mod(currentIndex + step , players.size());
       return players.get(nextPlayerIndex);
    }

    /**
     * modular operator in mathematics
     * @param a
     * @param b
     * @return a mod b
     */
    public int mod(int a , int b){
        while (a < 0){
            a += b;
        }
        while (a >= b){
            a -= b;
        }
        return a;
    }


    /**
     * if the
     * @param sign
     * @param colorNames
     * @param playingCard
     * @return
     */

    public boolean isCardPlayable(String sign , String colorNames, Card playingCard){
        if(playingCard.getColorName().equals(colorNames) ||
            playingCard.getSign().equals(sign) ||
            playingCard.getSign().equals("B")){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * resets the parameters of the game
     */
    public void resetGameParameters(){
        gameParameters.setChooseToPunish(false);
        gameParameters.setPrize(false);
        gameParameters.setPunishNext(0);
        gameParameters.setNextColor("");
        gameParameters.setJump(false);
        gameParameters.setChooseColorName(false);
    }

    /**
     * pusher gives a random card to the chosen player / bot
     * @param pusher
     */
    public void punishSomeOneForCard2(Player pusher){
        Player punishingPlayer;
        punishingPlayer = pusher.chooseToPunish(players);
        Card punishingCard = pusher.getHand().get(random.nextInt(pusher.getHand().size()));
        pusher.getHand().remove(punishingCard);
        punishingPlayer.getHand().add(punishingCard);
        System.out.println("the pushed card is : " + punishingCard.toString());
    }

    /**
     * punishes some one for card 7
     * @param howManyCards is the punishment cards count
     */
    public void punishPlayerForCard7(int howManyCards){
            ArrayList<Card> addingCards ;
            System.out.println( currentPlayer.getName() +" is punished because of card 7 takes " + howManyCards + " from the card deck");

            addingCards=addCardsToPlayerHandFromCardDeck(howManyCards);
            System.out.println("adding cards are : ");
            for(Card card : addingCards){
                System.out.print(" " + card.toString()+ " ");
            }
            System.out.println();
            System.out.println();

    }

    /**
     * adds some card to the hand of the player
     * @param howMany is the count of the cards
     * @return the arrayList of the adding cards
     */
    public ArrayList<Card> addCardsToPlayerHandFromCardDeck(int howMany){
        ArrayList<Card> addingCards = new ArrayList<>();
        Card addingCard;
        for(int i = 1; (i <= howMany) && (cardDeck.isDeckEmpty()==false); i++){
            addingCard = cardDeck.giveOneCard();
            currentPlayer.takeACard(addingCard);
            addingCards.add(addingCard);
        }
        return addingCards;
    }

    /**
     * resets the game parameters but the count of the punishment cards for card 7 (punishNext)
     */
    public void resetGameParametersButPunishNext(){
        gameParameters.setChooseToPunish(false);
        gameParameters.setPrize(false);
        gameParameters.setNextColor("");
        gameParameters.setJump(false);
    }

    /**
     * prints the winner's name
     */
    public void printWinner(){
        System.out.println("!!! THE " + players.get(getWinnerIndex()).getName()+ " IS THE WINNER !!!");

    }

    /**
     * prints the losers' names and their score
     * sorted increasingly by scores
     */
    public void printLosersNamesAndScores(){
       int counter = 1;
        for(Player player : players){
            if(players.indexOf(player) != getWinnerIndex()){
                System.out.println(counter + "- "+ player.getName() + " score : " + player.getScore());
                counter++;
            }
        }
        System.out.println();
    }

    /**
     * sorts the players by their score increasingly
     * @param players the input players arrayList
     * @return the sorted players arrayList
     */
    public ArrayList<Player> sortPlayersByScore(ArrayList<Player> players){
        ArrayList<Player> sortedPlayers= new ArrayList<>();
        int minScore = 1000000;
        Player minScorePlayer =  null;
        Player player = null;

        while (players.size() > 0){
            Iterator<Player> it = players.iterator();
            while ((it.hasNext())){
                player = it.next();
                if(player.getScore() < minScore){
                    minScore = player.getScore();
                    minScorePlayer = player;
                }
            }
            minScore = 100000;
            sortedPlayers.add(minScorePlayer);
            players.remove(minScorePlayer);
        }

        return sortedPlayers;
    }


    /**
     * a method that stops the game for a few seconds
     * so that players can see what happens
     * @throws InterruptedException
     */
    public void delay() throws InterruptedException {
        Thread.sleep(3500);
    }

    /**
     * prints the direction of the game
     */
    public void printDirection(){
        if(gameParameters.getStep() == 1){
            System.out.println("Direction : Clockwise");
        }
        else {
            System.out.println("Direction : Anticlockwise");
        }
        System.out.println();
    }

    /**
     * prints the players in clockwise direction
     */
    public void printPlayersInClockwiseDirection(){
        System.out.println("players in clockwise direction are :");
        for(Player player : players){
            System.out.println(player.getName());
        }
        System.out.println();
    }
}
