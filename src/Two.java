public class Two extends Card implements Actionable{
    public Two(String sign, String colorName) {
        super(sign, colorName);
    }

    @Override
    public void action(GameParameters gameParameters) {
        gameParameters.setChooseToPunish(true);
    }
}
