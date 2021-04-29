/**
 * a class that handles the conditions of the game
 * @author Mohammad Heydari Rad
 * @since 2021
 * @see Game
 */

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

    /**
     * step getter method
     * @return step of the game
     */
    public int getStep() {
        return step;
    }

    /**
     * sets the step
     * @param step is the new step of the game
     */
    public void setStep(int step) {
        this.step = step;
    }

    /**
     * punish next getter method
     * @return the punishNext ( the count of the cards of the punishment of next player)
     */
    public int getPunishNext() {
        return punishNext;
    }

    /**
     * sets the punishNext
     * @param punishNext is the new punishNext
     */
    public void setPunishNext(int punishNext) {
        this.punishNext = punishNext;
    }

    /**
     * the get next color getter method
     * ( i handled it better later )
     * @return the next color of the game
     */
    public String getNextColor() {
        return nextColor;
    }

    /**
     * sets the next color of th e
     * @param nextColor
     */
    public void setNextColor(String nextColor) {
        this.nextColor = nextColor;
    }

    /**
     *
     * @return true if the next player can not play because of card B of the current player
     */
    public boolean isJump() {
        return jump;
    }

    /**
     * sets the jump
     * @param jump is the new jump
     */
    public void setJump(boolean jump) {
        this.jump = jump;
    }

    /**
     * if the player plays 8 , should play another card
     * @return  true if the player has another turn to play , else , false
     */
    public boolean isPrize() {
        return prize;
    }

    /**
     * sets the prize
     * @param prize
     */
    public void setPrize(boolean prize) {
        this.prize = prize;
    }

    /**
     * if card plays B , can change the next valid color of cards
     * @return true if the next valid color should be initialized by current player
     */
    public boolean isChooseToPunish() {
        return chooseToPunish;
    }

    /**
     * if the player plays card 2
     * can punish some one by a random card of himself/herself/itself
     * @param chooseToPunish
     */
    public void setChooseToPunish(boolean chooseToPunish) {
        this.chooseToPunish = chooseToPunish;
    }
    /**
     * if card plays B , can change the next valid color of cards
     * @return true if the next valid color should be initialized by current player
     */
    public boolean isChooseColor() {
        return chooseColorName;
    }

    /**
     * sets the boolean chooseColor
     * @param chooseColorName
     */
    public void setChooseColorName(boolean chooseColorName) {
        this.chooseColorName = chooseColorName;
    }
}
