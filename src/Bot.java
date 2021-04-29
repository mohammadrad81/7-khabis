import java.util.ArrayList;
import java.util.Random;

/**
 * a player that can play automatically ( this is obvious by the name)
 * @author Mohammad Heydari Rad
 * @since 2021
 * @see Game
 */
public class Bot extends Player{

    public Bot(String name){
        super(name);
    }

    /**
     * in normal cases the bot can play a card that has the valid sign to play
     * or the valid color to play
     * @param sign is the valid sign to play
     * @param colorName is the valid color to play
     * @return the playing card ( if had no playable cards , null)
     */
    public Card chooseFromHand(String sign , String colorName){
        ArrayList<Card> hand = super.getHand();
        for(int i = 0; i < hand.size() ; i++){
            if(hand.get(i).getColorName().equals(colorName) ||
                hand.get(i).getSign().equals(sign) ||
                hand.get(i).getSign().equals("B")){
                Card card = hand.get(i);
                hand.remove(card);
//                System.out.println("bot played " + card.toString());
                return card;
            }

        }
        return null;
    }

    /**
     * the bot has to play a 7 card
     *
     * @return the playing 7 card , if has not , null (never happens ,it is handled in class Game)
     */
    @Override
    public Card play7(){
        ArrayList<Card> hand = super.getHand();
        for(Card card : hand){
            if(card instanceof Seven){
                return card;
            }
        }
        return null;
    }

    /**
     * the bot chooses a random player from the players ArrayList to get punished for card 2
     * @param players is the arrayList of the players
     * @return the chosen player
     */
    @Override
    public Player chooseToPunish(ArrayList<Player> players){
        Random random = new Random();
        Player punishingPlayer;
        do{
            punishingPlayer = players.get(random.nextInt(players.size()));
        }while (punishingPlayer == this);
        System.out.println(this.getName() + "  punished  " + punishingPlayer.getName());
        return punishingPlayer;
    }

    /**
     * if the bot plays card B
     * can choose the next valid color
     * it is random choice
     * @return the name of the next card valid color
     */
    @Override
    public String chooseColorName(){
            Random random = new Random();
            int nextColorIndex = random.nextInt(4) + 1;
        if (nextColorIndex == 1) {
            System.out.println("bot choice is color black " );
            return (ColorNames.BLACK_NAME);
        } else if (nextColorIndex == 2) {
            System.out.println("bot choice is color red ");
            return (ColorNames.RED_NAME);
        } else if (nextColorIndex == 3) {
            System.out.println("bot choice is color green ");
            return (ColorNames.GREEN_NAME);
        }
        else {
            System.out.println("bot choice is color blue ");
            return (ColorNames.BLUE_NAME);
        }
    }
}
