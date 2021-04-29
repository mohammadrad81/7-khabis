/**
 * the definition of the cards of the game ( basically normal cards)
 * @author Mohammad Heydari Rad
 * @since 2021
 * @see Game
 * @see ColorNames
 * @see  ConsoleColors
 */
public class Card {
    private String sign;
    private String colorName;
    private String color;
    private int score;


    public Card (String  sign , String colorName){

        this.sign = sign;
        this.colorName = colorName;
        this.color = ColorNames.colorNameToColorCode(colorName);
        this.score = signToScore(sign);
    }

    /**
     *
     * @param sign is the sign of the card
     * @return the score of the card
     */
    private int signToScore(String sign){
        if(sign.equals("3")){
            return 3;
        }
        else if(sign.equals("4")){
            return 4;
        }
        else if(sign.equals("5")){
            return 5;
        }
        else if(sign.equals("6")){
            return 6;
        }
        else if(sign.equals("9")){
            return 9;
        }
        else if(sign.equals("2")){
            return 2;
        }
        else if(sign.equals("7")){
            if(color.equals(ColorNames.BLACK_NAME)){
                return 15;
            }
            else {
                return 10;
            }
        }
        else if(sign.equals("8")){
            return 8;
        }
        else if(sign.equals("10")){
            return 10;
        }
        else if(sign.equals("A")){
            return 11;
        }
        else if(sign.equals("B")){
            return 12;
        }
        else if(sign.equals("C")){
            return 12;
        }
        else if(sign.equals("D")){
            return 13;
        }
        return 0;//never happens
    }

    /**
     * to print the cards in rows
     * we should be able to print a row of the card
     * this method prints left half of the row
     * @param rowNumber is the number of the wanted row of the card
     * @return a string that is half of the wanted row
     */
    public String halfRowString(int rowNumber){
        String row = color;
        if(rowNumber == 1){
            row += "_____";
        }
        else if(rowNumber == 2){
            if(sign.equals("10")){
                row += color + "| " + sign + " ";
            }
            else {
                row += "| " +sign + "  ";
            }
        }
        else if(rowNumber == 3 || rowNumber == 4 || rowNumber == 5){
            row +=  "|    ";
        }
        else{
            row += "_____";
        }
        row += ConsoleColors.WHITE + " ";
        return row;
    }

    /**
     * to print the cards in rows
     * we should be able to print a row of the card
     * this method prints a complete row
     * @param rowNumber is the number of the wanted row of the card
     * @return a string that is half of the wanted row
     */
    public String fullRowString(int rowNumber){
        String row = color;
        if(rowNumber == 1){
            row += "__________";
        }
        else if(rowNumber == 2){
            if(sign.equals("10")){
                row += "| "+sign+"     |";
            }
            else {
                row += "| " +sign+"      |";
            }
        }
        else if(rowNumber == 3 || rowNumber == 4){
            row += "|        |";
        }
        else if(rowNumber == 5){
            if(sign.equals("10")){
                row += "|     " +sign +" |";
            }
            else {
                row += "|      " +sign+" |";
            }
        }
        else if(rowNumber == 6){
            row += "__________";
        }
        row += ConsoleColors.WHITE + " ";
        return row;
    }

    /**
     * card sign getter method
     * @return  sign of the card
     */
    public String getSign() {
        return sign;
    }

    /**
     * color name getter method
     * @return the color name of the card ( color name is the name of the color , not its ansi code)
     */
    public String getColorName(){
        return colorName;
    }

    /**
     * color getter method
     * @return the ansi code of the color of the card
     */
    public String getColor() {
        return color;
    }

    /**
     * score getter method
     * @return the score of the card
     */
    public int getScore() {
        return score;
    }

    /**
     *
     * @return the sign and the color name of the card in a string
     */
    @Override
    public String toString(){
        return (" " + sign + " " + colorName);
    }
}
