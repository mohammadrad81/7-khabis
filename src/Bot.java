import java.util.ArrayList;
import java.util.Random;

public class Bot extends Player{

    public Bot(String name){
        super(name);
    }

    public Card chooseFromHand(String sign , String colorName){
        ArrayList<Card> hand = super.getHand();
        for(int i = 0; i < hand.size() ; i++){
            if(hand.get(i).getColorName().equals(colorName) ||
                hand.get(i).getSign().equals(sign)){
                Card card = hand.get(i);
                hand.remove(card);
                return card;
            }

        }
        return null;
    }
    @Override
    public Card playAnyOfAColor(String colorName){
        for(Card card : getHand()){
            if(card.getColorName().equals(colorName)){
                return card;
            }
        }
        return null;
    }
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
    @Override
    public Player chooseToPunish(ArrayList<Player> players){
        Random random = new Random();
        Player punishingPlayer;
        do{
            punishingPlayer = players.get(random.nextInt(players.size()));
        }while (punishingPlayer == this);

        return punishingPlayer;
    }

    @Override
    public String chooseColorName(){
            Random random = new Random();
            int nextColorIndex = random.nextInt(4) + 1;
        if (nextColorIndex == 1) {
            return (ColorNames.BLACK_NAME);
        } else if (nextColorIndex == 2) {
            return (ColorNames.RED_NAME);
        } else if (nextColorIndex == 3) {
            return (ColorNames.GREEN_NAME);
        }
        else {
            return (ColorNames.BLUE_NAME);
        }
    }
}
