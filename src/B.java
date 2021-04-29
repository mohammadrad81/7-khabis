import java.util.Scanner;

/**
 * a definition of card B in dirty 7
 * @author Mohammad Heydari Rad
 * @since 2021
 * @see Card
 * @see Actionable
 * @see GameParameters
 */
public class B extends Card implements Actionable {
    public B(String sign, String colorName) {
        super(sign, colorName);
    }

    /**
     * the action is that the player should choose the next valid color
     * @param gameParameters is the gameParameter used in game
     */
    @Override
    public void action(GameParameters gameParameters) {
        gameParameters.setChooseColorName(true);
    }
}