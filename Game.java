import java.util.*;

public class Game{
    static Card [] Deck = new Card [52];
    static int action,i,j,value,dvalue;
    static int stack = 100; // the player starts with $100
    static int bet;
    static Card [] Hand;

    public static void main(String[]args){
        Scanner scan = new Scanner(System.in);
        makeDeck(Deck); // the deck of cards is represented by an array of Cards, where a Card is an object that has a suit and a value

        while(stack>0){

            shuffle(Deck); // every new round, the deck must be shuffled
            init();

            while(0>=bet || bet > stack){ // forces the player to enter a valid bet
                System.out.println("Enter your bet: ");
                bet = scan.nextInt();
            }

            System.out.println("Press 0 to HIT or 1 to STAND.");
            System.out.println("DEALER: "+Deck[50].display()+" ?");

            if(blackjack(Arrays.copyOfRange(Deck,0,2))){ // a blackjack is a type of hand that must be checked independently
                value = 21;
                System.out.print("Your hand is: ");
                display_hand(Arrays.copyOfRange(Deck,0,2));
                System.out.println("("+value+")");                
                System.out.println("BLACKJACK");
            }

            else{
                while(action!=1){
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
                    action = scan.nextInt();
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

                while(dvalue<17 || dvalue < value){
               
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
                    } 
            }

            if(17<= dvalue && dvalue <22 && !blackjack(Arrays.copyOfRange(Deck,j,52))){
                    System.out.println("The dealer STANDS.");
            }
            
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

    static void makeDeck(Card[] Deck){ //generates an array of Cards with 52 spaces, each containing a unique playing card
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

    static void shuffle (Card[] Deck){ // randomly shuffles an array of Cards of size 52
        Random rng = new Random();
        for(int i=51;i>0;i--){
            int j = rng.nextInt(i+1);
            Card temp = Deck[i];
            Deck[i] = Deck[j];
            Deck [j] = temp;
        }
    }

    static void init(){
        action = 0;
        bet = -1;
        i = 1; // the player's hand is represented by indexes 0 to i of the shuffled deck
        j = 50; // // the dealer's hand is represented by indexes j to 51 of the shuffled deck
        value=22;
        dvalue=22;
        System.out.println("-----------------------------------------");
        System.out.println("Your stack: "+stack);
    }

    static Boolean blackjack(Card[]Hand){ // checks if a hand is a blackjack
        if(hand_value(Hand)==21 && Hand.length==2){
            return true;
        }
        return false;
    }    
    
    static void display_hand (Card[]Hand){ // displays a hand
        for(int i=0;i<Hand.length;i++){
            System.out.print(Hand[i].display()+" ");
        }
    }

    static int hand_value (Card[]Hand){ // returns the optimal value of a hand
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

    static int optimal_Ace(Card[]Hand,int aces,int value){ // returns the optimal value of a hand (if there are aces)
        for(int i=0;i<aces;i++){
            if(value-10*(1+i) >0 && value-10*(1+i)<22){
                value -= 10*(1+i);
            }
        }
        if(value-aces*10>21){
            return value-aces*10;
        }
        return value; }

    static int aces_number(Card[]Hand){ // counts the number of aces in a hand
        int aces=0;
        for(int i=0;i<Hand.length;i++){
            if(Hand[i].val() == 11){
                aces++;
            }
        }
        return aces;
    }
}