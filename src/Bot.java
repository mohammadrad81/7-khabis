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
//                System.out.println("bot played " + card.toString());
                return card;
            }

        }
        return null;
    }
//
//    public Card playAnyOfAColor(String colorName){
//        for(Card card : getHand()){
//            if(card.getColorName().equals(colorName)){
//                return card;
//            }
//        }
//        return null;
//    }
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
        System.out.println(this.getName() + "  punished  " + punishingPlayer.getName());
        return punishingPlayer;
    }

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
