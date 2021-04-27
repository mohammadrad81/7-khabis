public class Eight extends Card implements Actionable{
    public Eight(String sign, String colorName) {
        super(sign, colorName);
    }

    @Override
    public void action(GameParameters gameParameters) {
        gameParameters.setPrize(true);
    }
}
