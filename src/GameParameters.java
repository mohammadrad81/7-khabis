public class GameParameters {
    private int step;
    private int punishNext;//punish for card 7
    private String nextColor;
    private boolean jump;
    private boolean prize;
    private boolean chooseToPunish;//punish for card 2
    private boolean chooseColorName;

    public GameParameters(){
        step = 1;
        punishNext = 0;
        nextColor = "";
        jump = false;
        prize = false;
        chooseToPunish = false;
        chooseColorName = false;
    }
    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getPunishNext() {
        return punishNext;
    }

    public void setPunishNext(int punishNext) {
        this.punishNext = punishNext;
    }

    public String getNextColor() {
        return nextColor;
    }

    public void setNextColor(String nextColor) {
        this.nextColor = nextColor;
    }

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public boolean isPrize() {
        return prize;
    }

    public void setPrize(boolean prize) {
        this.prize = prize;
    }

    public boolean isChooseToPunish() {
        return chooseToPunish;
    }

    public void setChooseToPunish(boolean chooseToPunish) {
        this.chooseToPunish = chooseToPunish;
    }

    public boolean isChooseColor() {
        return chooseColorName;
    }

    public void setChooseColorName(boolean chooseColorName) {
        this.chooseColorName = chooseColorName;
    }
}
