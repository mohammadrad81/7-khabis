/**
 * definition of card 8
 * @author Mohammad Heydari Rad
 * @since 2021
 * @see Card
 * @see ColorNames
 * @see Game
 * @see GameParameters
 */

public class Eight extends Card implements Actionable{
    public Eight(String sign, String colorName) {
        super(sign, colorName);
    }

    /**
     * this card action is that the current player can player another card
     * @param gameParameters is the gameParameter used in game
     */
    @Override
    public void action(GameParameters gameParameters) {
        gameParameters.setPrize(true);
    }
}
