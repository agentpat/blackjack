import java.util.*;

class Card{
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
        return value;}
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

public class Game{
    public static void main(String[]args){
        Scanner scan = new Scanner(System.in);
        Card [] Deck = new Card [52];
        int stay,i,j,value,dvalue;
        int stack = 100;
        int bet;
        Card [] Hand;
        makeDeck(Deck);

        while(stack>0){
            stay = 0;
            bet = -1;
            i = 1;
            j = 50;
            value=22;
            dvalue=22;
            shuffle(Deck);

            System.out.println("-----------------------------");
            System.out.println("Your stack: "+stack);

            while(0>=bet || bet > stack){
                System.out.println("Enter your bet: ");
                bet = scan.nextInt();
            }

            System.out.println("Press 0 to HIT or 1 to STAND.");
            System.out.println("DEALER: "+Deck[50].display()+" ?");

            if(blackjack(Arrays.copyOfRange(Deck,0,2))){
                value = 21;
                System.out.print("Your hand is: ");
                display_hand(Arrays.copyOfRange(Deck,0,2));
                System.out.println("("+value+")");                
                System.out.println("BLACKJACK");
            }

            else{
                while(stay!=1){
                    i++;
                    Hand = Arrays.copyOfRange(Deck,0,i);
                    value = hand_value(Hand);
                    System.out.print("Your hand is: ");
                    display_hand(Hand);
                    System.out.println("("+value+")");
                    if(value>21){
                        stack -= bet;
                        System.out.println("YOU BUSTED");
                        break;}
                    stay = scan.nextInt();
                }
            }

            if(stack<=0){
                System.out.println("GAME OVER");
                break;
            }

            else if (value<22 && !blackjack(Arrays.copyOfRange(Deck,0,2))){
                Hand = Arrays.copyOfRange(Deck,j,52);
                dvalue = hand_value(Hand);
                System.out.print("The dealer's hand is: ");
                display_hand(Hand);
                System.out.println("("+dvalue+")");

                while(dvalue<17){
               
                    System.out.println("The dealer HITS.");
                    j--;
                    dvalue+=Deck[j].val();
                    Hand = Arrays.copyOfRange(Deck,j,52);
                    System.out.print("The dealer's hand is: ");
                    display_hand(Hand);
                    System.out.println("("+dvalue+")");
                    if(dvalue>21){
                        System.out.println("THE DEALER BUSTED");
                        System.out.println("YOU WIN");
                        stack += bet;
                        break;}
                     } }

                if(17<= dvalue && dvalue <22 && !blackjack(Arrays.copyOfRange(Deck,j,52))){
                    System.out.println("The dealer STANDS.");}
            
            Hand = Arrays.copyOfRange(Deck,50,52);
            
            if(dvalue<22 && value<dvalue){
                System.out.println("YOU LOSE");
                stack -= bet;
            }

            else if(blackjack(Arrays.copyOfRange(Deck,0,2)) && blackjack(Arrays.copyOfRange(Deck,50,52))){
                    System.out.print("The dealer's hand is: ");
                    display_hand(Hand);
                    System.out.println("("+hand_value(Hand)+")");                
                    System.out.println("DRAW");
            }

            else if (blackjack(Arrays.copyOfRange(Deck,0,2))){
                System.out.print("The dealer's hand is: ");
                display_hand(Hand);
                System.out.println("("+hand_value(Hand)+")");               
                System.out.println("YOU WIN");
                stack += 1.5*bet;
            }

            else if(value < 22 && value>dvalue){
                System.out.println("YOU WIN");
                stack += bet;
            }
            
            else if(value < 22 && value==dvalue){
                System.out.println("DRAW");
            }

            if(stack<=0){
                System.out.println("GAME OVER");
                break;
            }
        }
        scan.close();
    }

    static void makeDeck(Card[] Deck){
        for(int i=0;i<52;i++){
            Deck[i]= new Card();

            if(i%4==0){
                Deck[i].setCard("♠", 1+i%13);
            }
            else if(i%4==1){
                Deck[i].setCard("❤", 1+i%13);
            }
            else if(i%4==2){
                Deck[i].setCard("♣", 1+i%13);
            }
            else if(i%4==3){
                Deck[i].setCard("◆", 1+i%13);
            }
        }
    }

    static void shuffle (Card[] Deck){
        Random rng = new Random();
        for(int i=51;i>0;i--){
            int j = rng.nextInt(i+1);
            Card temp = Deck[i];
            Deck[i] = Deck[j];
            Deck [j] = temp;
        }
    }

    static void display_hand (Card[]Hand){
        for(int i=0;i<Hand.length;i++){
            System.out.print(Hand[i].display()+" ");}}

    static int hand_value (Card[]Hand){
        int sum=0;
        int aces = aces_number(Hand);

        for(int i=0;i<Hand.length;i++){
            sum+=Hand[i].val();
        }

        if(sum>21 && aces>0){
            return optimal_Ace(Hand,aces,sum);
        }

        return sum;
    }

    static int optimal_Ace(Card[]Hand,int aces,int value){
        for(int i=0;i<aces;i++){
            if(value-10*(1+i) >0 && value-10*(1+i)<22){
                value -= 10*(1+i);
            }
        }
        if(value-aces*10>21){
            return value-aces*10;
        }
        return value; }

    static int aces_number(Card[]Hand){
        int aces=0;
        for(int i=0;i<Hand.length;i++){
            if(Hand[i].val() == 11){
                aces++;
            }
        }
        return aces;
    }

    static Boolean blackjack(Card[]Hand){
        if(hand_value(Hand)==21 && Hand.length==2){
            return true;
        }
        return false;
    }
}