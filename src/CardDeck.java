import java.util.ArrayList;
import java.util.Random;

/**
 * a definition of the card deck of the dirty 7 game
 * @author Mohammad Heydari rad
 * @since 2021
 * @see Card
 * @see ConsoleColors
 * @see ColorNames
 *
 */
public class CardDeck {
    private ArrayList<Card> deck;

    public CardDeck(){
        deck = new ArrayList<>();
        initialDeck();
        shuffleCards();
    }

    /**
     * adds the input cards to the card deck
     * @param cards
     */
    public void addCardsToDeck(Card... cards){
        for(Card card : cards){
            deck.add(card);
        }
    }

    /**
     * adds the playing cards to the deck
     */
    private void initialDeck(){
        String sign;

        for(int i = 3 ; i <= 9 ; i++){

            if(i == 7){
                i +=2;
            } // to skip 7 and 8 cards

            sign = Integer.toString(i);
            addNormalCardWithSignWithAllColors(sign);
        }
        Seven blackSeven = new Seven("7" , ColorNames.BLACK_NAME);
        Seven redSeven = new Seven("7" , ColorNames.RED_NAME);
        Seven greenSeven = new Seven("7" , ColorNames.GREEN_NAME);
        Seven blueSeven = new Seven("7" , ColorNames.BLUE_NAME);

        Two blackTwo = new Two("2" , ColorNames.BLACK_NAME);
        Two redTwo = new Two("2" , ColorNames.RED_NAME);
        Two greenTwo = new Two("2" , ColorNames.GREEN_NAME);
        Two blueTwo = new Two("2" , ColorNames.BLUE_NAME);

        Eight blackEight = new Eight("8" , ColorNames.BLACK_NAME);
        Eight redEight = new Eight("8" , ColorNames.RED_NAME);
        Eight greenEight = new Eight("8" , ColorNames.GREEN_NAME);
        Eight blueEight = new Eight("8" , ColorNames.BLUE_NAME);

        Ten blackTen = new Ten("10" , ColorNames.BLACK_NAME);
        Ten redTen = new Ten("10" , ColorNames.RED_NAME);
        Ten greenTen = new Ten("10" , ColorNames.GREEN_NAME);
        Ten blueTen = new Ten("10" , ColorNames.BLUE_NAME);

        A blackA = new A("A" , ColorNames.BLACK_NAME);
        A redA = new A("A" , ColorNames.RED_NAME);
        A greenA = new A("A" , ColorNames.GREEN_NAME);
        A blueA = new A("A" , ColorNames.BLUE_NAME);

        B blackB = new B("B" , ColorNames.BLACK_NAME);
        B redB = new B("B" , ColorNames.RED_NAME);
        B greenB = new B("B" , ColorNames.GREEN_NAME);
        B blueB = new B("B" , ColorNames.BLUE_NAME);

        addNormalCardWithSignWithAllColors("C");
        addNormalCardWithSignWithAllColors("D");

        addCardsToDeck(blackSeven , redSeven , greenSeven , blueSeven ,
                        blackTwo , redTwo , greenTwo , blueTwo ,
                        blackTen , redTen , greenTen , blueTen ,
                        blackEight , redEight , greenEight , blueEight ,
                        blackA , redA , greenA , blueA ,
                        blackB , redB , greenB , blueB);


    }

    /**
     * adds normal cards of the game to the card deck
     * by all colors
     * @param sign is the sign of the wanted card
     */
    private void addNormalCardWithSignWithAllColors(String sign ){
        ArrayList<Card> cards = new ArrayList<>();

             cards.add(new Card(sign , ColorNames.BLACK_NAME));
             cards.add(new Card(sign , ColorNames.GREEN_NAME));
             cards.add(new Card(sign , ColorNames.RED_NAME));
             cards.add(new Card(sign , ColorNames.BLUE_NAME));


        for(Card card : cards){
            deck.add(card);
        }

    }

    /**
     * shuffles the cards
     */
    public void shuffleCards(){
        ArrayList<Card> shufflingCards = deck;
        deck = new ArrayList<>();
        Random random = new Random();
        int index;
        while(shufflingCards.size() > 0){
            index = random.nextInt(shufflingCards.size());
            deck.add(shufflingCards.get(index));
            shufflingCards.remove(index);
        }
    }

    /**
     * gives a card from the start of the deck
     * @return the card
     */
    public Card giveOneCard(){
        if(deck.size() > 0){
            Card givingCard =  deck.get(0);
            deck.remove(0);
            return givingCard;
        }
        return null; // never happens
    }

    /**
     * checks that the card deck is empty or not
     * @return
     */
    public boolean isDeckEmpty(){
        if (deck.size() == 0){
            return true;
        }
        return false;
    }
}
