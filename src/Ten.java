public class Ten extends Card implements Actionable{
    public Ten(String sign, String colorName) {
        super(sign, colorName);
    }

    @Override
    public void action(GameParameters gameParameters) {
        gameParameters.setStep((-1) * gameParameters.getStep());
    }
}
