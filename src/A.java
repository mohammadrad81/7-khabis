/**
 * the card A definition for dirty 7 game
 * @author Mohammad Heydari Rad
 * @since 2021
 * @see Card
 * @see Actionable
 * @see GameParameters
 */

public class A extends Card implements Actionable{
    public A(String sign, String colorName) {
        super(sign, colorName);
    }

    /**
     * the action of the card to the game
     * it sets the setJump of gameParameters true
     * to avoid next player to play
     * @param gameParameters the gameParameter used in Game
     */
    @Override
    public void action(GameParameters gameParameters) {
        gameParameters.setJump(true);
    }
}
