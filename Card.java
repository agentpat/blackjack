public class Card{
    private String Suit;
    private int value;

    public void setCard(String s,int a){
        Suit = s;
        value = a;
    }

    public String suit(){ return Suit;}

    public int val(){
        if(value == 1){
            return 11;
        }
        else if(value>10){
            return 10;
        }
        return value;
    }

    public String display(){
        if(value==1){
            return "A"+Suit;
        }
        else if(value==11){
            return "J"+Suit;
        }
        else if(value==12){
            return "Q"+Suit;
        }
        else if(value==13){
            return "K"+Suit;
        }
        return value+Suit;
    }
}