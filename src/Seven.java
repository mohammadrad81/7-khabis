/**
 * definition of the card 7
 * @author Mohammad Heydari Rad
 * @since 2021
 * @see Card
 * @see Actionable
 * @see GameParameters
 */

public class Seven extends Card implements Actionable {

    public Seven(String sign, String colorName) {
        super(sign, colorName);
    }

    /**
     * the action of the card 7 is that increases the  punishment
     * @param gameParameters is the gameParameter used in game
     */
    @Override
    public void action(GameParameters gameParameters) {
        if(super.getColorName().equals(ColorNames.BLACK_NAME)){
            gameParameters.setPunishNext(gameParameters.getPunishNext() + 4);
        }
        else{
            gameParameters.setPunishNext(gameParameters.getPunishNext() + 2);
        }
    }
}
