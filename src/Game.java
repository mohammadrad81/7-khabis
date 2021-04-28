import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

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


//    public Game(int bots , int humans , ArrayList<String> playersNames){
//        players = new ArrayList<>();
//        gameParameters = new GameParameters();
//        cardDeck = new CardDeck();
//
//        for(String playerName : playersNames){
//            players.add(new Player( playerName));
//        }
//
//        players = initialPlayers(players);
//
//    }

    public Game(int bots , int humans , ArrayList<Player> players){
        this.players = new ArrayList<>();
        this.gameParameters = new GameParameters();
        this.cardDeck = new CardDeck();
        for(Player player : players){
            this.players.add(player);
        }

        this.players = initialPlayers(this.players);
    }

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
                    if(! currentPlayer.has7()){
                        currentPlayer = nextPlayer(currentPlayer , gameParameters.getStep());
                        resetGameParameters();
                        mustPlay7 = false;
                        delay();
                        System.out.println();
                        continue;
                    }
                    else {

                      mustPlay7 = true;
                    }
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

    public void giveEachPlayer7Cards (ArrayList<Player> players){
        for(Player player : players){
            for(int i = 1; i <= 7; i++){
                player.takeACard(cardDeck.giveOneCard());
            }
        }
    }

    public void initialFirstCard(){
        Card card ;

        do{
            card = cardDeck.giveOneCard();
        }while(card instanceof Actionable);

        lastCard = card;
    }
    public int getWinnerIndex(){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).isWinner()){
                return i;
            }
        }

        return -1;
    }
    public void printLastCard(){

        System.out.println("Top card : ");
        for(int i = 1; i <= 6; i++){
            System.out.println(lastCard.fullRowString(i));
        }
        System.out.println();
    }

    public Player nextPlayer(Player currentPlayer , int step){
       int currentIndex = players.indexOf(currentPlayer);
       int nextPlayerIndex = mod(currentIndex + step , players.size());
       return players.get(nextPlayerIndex);
    }

    public int mod(int a , int b){
        while (a < 0){
            a += b;
        }
        while (a >= b){
            a -= b;
        }
        return a;
    }

//    public Card playerChoosesCard(Player currentPlayer) {
//        currentPlayer.printHand();
//        if(currentPlayer instanceof Bot){
//            Bot bot = (Bot) currentPlayer;
//            botChoosesCard(bot);
//
//        }
//        else{
//            System.out.println("Enter the index of the card to play");
//
//        }
//        return null;
//    }
//    public Card chooseCard(Player player){
//        if(player instanceof Bot){
//            return botChoosesCard((Bot) player);
//        }
//        else{
//            return player.chooseFromHand(validSign , validColorName);
//        }
//    }
    public Card botChoosesCard(Bot bot){
        Card botChoice = bot.chooseFromHand(validSign , validColorName);
        if(botChoice == null){
            System.out.println(bot.getName()+ " could not play any card so he picked one up from card deck");
            bot.getHand().add(cardDeck.giveOneCard());

            return null;
        }
        return botChoice;
    }

    public boolean isCardPlayable(String sign , String colorNames, Card playingCard){
        if(playingCard.getColorName().equals(colorNames) ||
            playingCard.getSign().equals(sign)){
            return true;
        }
        else {
            return false;
        }
    }

    public void resetGameParameters(){
        gameParameters.setChooseToPunish(false);
        gameParameters.setPrize(false);
        gameParameters.setPunishNext(0);
        gameParameters.setNextColor("");
        gameParameters.setJump(false);
        gameParameters.setChooseColorName(false);
    }

    public void punishSomeOneForCard2(Player pusher){
        Player punishingPlayer;
        punishingPlayer = pusher.chooseToPunish(players);
        Card punishingCard = pusher.getHand().get(random.nextInt(pusher.getHand().size()));
        pusher.getHand().remove(punishingCard);
        punishingPlayer.getHand().add(punishingCard);
        System.out.println("the pushed card is : " + punishingCard.toString());
    }

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

    public void resetGameParametersButPunishNext(){
        gameParameters.setChooseToPunish(false);
        gameParameters.setPrize(false);
        gameParameters.setNextColor("");
        gameParameters.setJump(false);
    }

    public void printWinner(){
        System.out.println("!!! THE " + players.get(getWinnerIndex()).getName()+ " IS THE WINNER !!!");

    }

    public void printLosersNamesAndScores(){
       int counter = 1;
        for(Player player : players){
            if(players.indexOf(player) != getWinnerIndex()){
                System.out.println(counter + "- "+ player.toString());
                counter++;
            }
        }
        System.out.println();
    }
    public ArrayList<Player> sortPlayersByScore(ArrayList<Player> players){
        ArrayList<Player> sortedPlayers= new ArrayList<>();
        int minScore = 1000000;
        Player minScorePlayer;
        Player player = new Player("no one nobody");

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
            sortedPlayers.add(player);
            players.remove(player);
        }

        return sortedPlayers;
    }



    public void delay() throws InterruptedException {
        Thread.sleep(3500);
    }

    public void printDirection(){
        if(gameParameters.getStep() == 1){
            System.out.println("Direction : Clockwise");
        }
        else {
            System.out.println("Direction : Anticlockwise");
        }
        System.out.println();
    }

    public void printPlayersInClockwiseDirection(){
        System.out.println("players in clockwise direction are :");
        for(Player player : players){
            System.out.println(player.getName());
        }
        System.out.println();
    }
}
