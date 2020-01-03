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