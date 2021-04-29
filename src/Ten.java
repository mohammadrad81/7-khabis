/**
 * definition for card 10 in dirty 7 game
 * @author Mohammad Heydari Rad
 * @since 2021
 * @see Card
 * @see GameParameters
 * @see Actionable
 */

public class Ten extends Card implements Actionable{
    public Ten(String sign, String colorName) {
        super(sign, colorName);
    }

    /**
     * the action of the card 10 is that changes the direction
     * @param gameParameters is the gameParameter used in game
     */
    @Override
    public void action(GameParameters gameParameters) {
        gameParameters.setStep((-1) * gameParameters.getStep());
    }
}
