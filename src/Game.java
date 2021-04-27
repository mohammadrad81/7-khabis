import java.util.ArrayList;
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

    public void gameBegins(){
        boolean mustPlay7 = false;
        Card playingCard = null;
        giveEachPlayer7Cards(players);

        initialFirstCard();
        validColorName = lastCard.getColorName();
        validSign = lastCard.getSign();

        currentPlayer = players.get(0);

       do{
           validColorName = lastCard.getColorName();
           validSign = lastCard.getSign();
           if(gameParameters.isJump()){
               currentPlayer = nextPlayer(currentPlayer , gameParameters.getStep());
               resetGameParameters();
           }

           if(gameParameters.isPrize()){
               currentPlayer = nextPlayer(currentPlayer , (-1) * gameParameters.getStep());
               resetGameParameters();
           }

           System.out.println(currentPlayer.getName());
           System.out.println("Score : " + currentPlayer.getScore());


           if(gameParameters.isChooseToPunish()){
               currentPlayer = nextPlayer(currentPlayer , (-1) * gameParameters.getStep());
               punishSomeOneForCard2(currentPlayer);
               resetGameParameters();
               currentPlayer = nextPlayer(currentPlayer , gameParameters.getStep());
               continue;
           }
           if(gameParameters.isChooseColor()){
               currentPlayer = nextPlayer(currentPlayer , (-1) * gameParameters.getStep());
               validColorName = currentPlayer.chooseColorName();
               currentPlayer = nextPlayer(currentPlayer , gameParameters.getStep());
           }
           if(! gameParameters.getNextColor().equals("")){
               validColorName = gameParameters.getNextColor();
           }
           if(gameParameters.getPunishNext() != 0){
                if(currentPlayer.has7()){
                    mustPlay7 = true;
                }
                else{
                    currentPlayer.printHand();
                    punishPlayerForCard7(gameParameters.getPunishNext());
                    if(! currentPlayer.has7()){
                        currentPlayer = nextPlayer(currentPlayer , gameParameters.getStep());
                        resetGameParameters();
                        continue;
                    }
                }
           }


           printLastCard();
           currentPlayer.printHand();
           if(mustPlay7){
               System.out.println("!! player must play card 7 !! ");
                playingCard = currentPlayer.play7();
           }

           else{
                playingCard = currentPlayer.chooseFromHand(validSign , validColorName);
           }

           if(!(gameParameters.getNextColor().equals(""))){
               if(currentPlayer.hasCardsOfTheColor(gameParameters.getNextColor())){
                   currentPlayer.playAnyOfAColor(gameParameters.getNextColor());
               }
               else{
                   Card addingCard = cardDeck.giveOneCard();
                   System.out.println(currentPlayer.getName() + " took a card from card deck");
                   currentPlayer.takeACard(addingCard);
                   System.out.println("now hand of " + currentPlayer.getName() + "is : ");
                   currentPlayer.printHand();
                   if(addingCard.getColorName().equals(gameParameters.getNextColor())){
                       System.out.println(currentPlayer.getName() + " took a playable card" + addingCard.toString() + "and played it");
                       playingCard = addingCard;
                       currentPlayer.getHand().remove(addingCard);
                       cardDeck.addCardsToDeck(addingCard);
                   }
                   else {
                       System.out.println( currentPlayer.getName() + " can not play the taken card");
                   }
               }
           }

           if(playingCard == null && gameParameters.getNextColor().equals("")){
               System.out.println("player picks a card from card deck");
               Card addingCard = cardDeck.giveOneCard();
               currentPlayer.takeACard(addingCard);
               currentPlayer.printHand();
               System.out.println("player took a playable card" + addingCard.toString());
               if(isCardPlayable(validSign , validColorName , addingCard)){
                   System.out.print(" and played it ");
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
            cardDeck.shuffleCards();

            if(playingCard != null){
                lastCard = playingCard;
            }

           currentPlayer = nextPlayer(currentPlayer , gameParameters.getStep());

       }while (getWinnerIndex() == -1);

       printWinner();
       sortPlayersByTheirScore();



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
    public Card chooseCard(Player player){
        if(player instanceof Bot){
            return botChoosesCard((Bot) player);
        }
        else{
            return player.chooseFromHand(validSign , validColorName);
        }
    }
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
        if(playingCard.getColor().equals(colorNames) ||
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
    }

    public void punishSomeOneForCard2(Player pusher){
        Player punishingPlayer;
        punishingPlayer = pusher.chooseToPunish(players);
        Card punishingCard = pusher.getHand().get(random.nextInt(pusher.getHand().size()));
        pusher.getHand().remove(punishingCard);
        punishingPlayer.getHand().add(punishingCard);
    }

    public void punishPlayerForCard7(int howManyCards){

            System.out.println("you are punished because of card 7 and you take " + howManyCards + " from the card deck");
            addCardsToPlayerHandFromCardDeck(howManyCards);

    }

    public void addCardsToPlayerHandFromCardDeck(int howMany){
        for(int i = 1; i <= howMany; i++){
            currentPlayer.takeACard(cardDeck.giveOneCard());
        }
    }

    public void resetGameParametersButPunishNext(){
        gameParameters.setChooseToPunish(false);
        gameParameters.setPrize(false);
        gameParameters.setNextColor("");
        gameParameters.setJump(false);
    }

    public void printWinner(){
        System.out.println("!!! THE " + players.get(getWinnerIndex()).getName()+ "IS THE WINNER !!!");

    }

    public void printLosersNamesAndScores(){
        for(int i = 1; i <= players.size() ;){
            if(i-1 != getWinnerIndex()){
                System.out.println( i + "- " + players.get(i-1) + "WITH SCORE : " + players.get(i-1).getScore());
                i++;
            }
        }
        System.out.println();
    }

    public void sortPlayersByTheirScore(){
        ArrayList<Player> sortedPlayers = new ArrayList<>();
        Player temp;
        for(int i = 0; i < players.size() ; i++){
            for(int j = i; j < players.size() ; j++){
                if(players.get(j).getScore() > players.get(i).getScore()){
                    temp = players.get(i);
                    players.add(i , players.get(j));
                    players.add(j , temp);
                }
            }
        }
    }
}
