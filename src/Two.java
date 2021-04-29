/**
 * definition of card 2 in dirty 7 game
 * @author Mohammad Heydari Rad
 * @since 2021
 * @see Card
 * @see Actionable
 * @see GameParameters
 */

public class Two extends Card implements Actionable{
    public Two(String sign, String colorName) {
        super(sign, colorName);
    }

    /**
     * the action of the card 2 is that the current player punishes another player
     * @param gameParameters is the gameParameter used in game
     */
    @Override
    public void action(GameParameters gameParameters) {
        gameParameters.setChooseToPunish(true);
    }
}
