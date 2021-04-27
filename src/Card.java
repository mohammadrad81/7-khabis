
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

    public String getSign() {
        return sign;
    }
    public String getColorName(){
        return colorName;
    }
    public String getColor() {
        return color;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString(){
        return (" " + sign + " " + colorName);
    }
}
