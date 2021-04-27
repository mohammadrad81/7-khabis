public class A extends Card implements Actionable{
    public A(String sign, String colorName) {
        super(sign, colorName);
    }

    @Override
    public void action(GameParameters gameParameters) {
        gameParameters.setJump(true);
    }
}
