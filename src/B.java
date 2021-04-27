import java.util.Scanner;

public class B extends Card implements Actionable {
    public B(String sign, String colorName) {
        super(sign, colorName);
    }

    @Override
    public void action(GameParameters gameParameters) {
        gameParameters.setChooseColorName(true);
    }
}