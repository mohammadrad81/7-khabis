public class Seven extends Card implements Actionable {

    public Seven(String sign, String colorName) {
        super(sign, colorName);
    }

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
