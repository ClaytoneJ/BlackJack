//Clayton Johnson
//7/9/2023
//CS 145 Deck of Cards lab
//This program will play an interactive blackjack game
import java.util.*;

public class BlackJack {
    
    //Main method that plays one game
    public static void main(String[] args) {
        int dealerVal = 21;
        Scanner input = new Scanner(System.in);
        DeckOfCards deck = new DeckOfCards();
        Stack<Card> playerHand = new Stack<>();
        Stack<Card> dealerHand = new Stack<>();
        deck.shuffle();
        intro();
        //deals the initial 2 cards
        for (int i = 0; i < 2; i++) {
            playerHand.push(deck.dealCard());
            dealerHand.push(deck.dealCard());
            //Shows one of the dealers cards
            if (i == 0) {
                System.out.println("Dealers first card: " + dealerHand);
            }
        }
        //Plays User's turn
        int userVal = userTurn(deck, playerHand, input);
        //Plays dealers turn if user did not bust
        if (userVal < 22) {
            dealerVal = dealerTurn(deck, dealerHand, userVal, input);
        }
        whoWon(userVal, dealerVal);
    }

    //Introduces the game to the user
    public static void intro() {
        System.out.println("This game is called BlackJack.");
        System.out.println("The point of the game is to get as close");
        System.out.println("to 21 as possible without going over.");
        System.out.println("You will be dealt 2 cards each but");
        System.out.println("you will only be able to see 1 dealer card");
        System.out.println("until the end of your turn. You go first!\n");
    }
    
    //Goes through the User's turn and returns the hand's value at the end of turn
    public static int userTurn(DeckOfCards deck, Stack<Card> playerHand, Scanner input) {
        System.out.println("Your turn first!");
        System.out.println("Your hand: " + playerHand);
        int userValue = getHandValue(playerHand, true, input);
        System.out.println("Value: " + userValue);
        //Loops until user busts or stands
        while(true) {
            System.out.println("hit or stand (h/s)");
            String choice = input.next();
            //If user hits deal a new card and ubdate the value
            if (choice.equals("h")) {
                playerHand.push(deck.dealCard());
                System.out.println(playerHand);
                userValue = getHandValue(playerHand, true, input);
                System.out.println("Value: " + userValue);
                    //Return user value if they bust
                    if (userValue > 21) {
                        System.out.println("Bust!");
                        return userValue;
                    }
            } else if (choice.equals("s")) {
                System.out.println("User Stands");
                userValue = getHandValue(playerHand, true, input);
                System.out.println("Value: " + userValue);
                return userValue;
            } else {
                System.out.println("invalid input");
            }
        }
    }
    
    //Determines who won the game
    public static void whoWon(int userValue, int dealerValue) {
        if ((userValue > dealerValue && userValue < 22) || (userValue < 22 && dealerValue > 21)) {
            System.out.println("User Wins!");
        } else if (userValue == dealerValue){    
            System.out.println("Tie!");
        } else {
            System.out.println("Dealer wins :(");
        }
    }
    
    //Goes through the dealers turn and returns the hands value
    public static int dealerTurn(DeckOfCards deck, Stack<Card> dealerHand,
                                  int userValue, Scanner input) {
        int dealerValue = getHandValue(dealerHand, false, input);
        System.out.println("Dealer's turn");
        System.out.println("Dealer hand: " + dealerHand);
        System.out.println("Dealer Value: " + dealerValue); 
        //Deals a new card and updates value if needed
        while ((dealerValue < 17 || dealerValue < userValue) && dealerValue < 21) {            
            dealerHand.push(deck.dealCard());
            System.out.println("Dealer hand: " + dealerHand);
            dealerValue = getHandValue(dealerHand, false, input);
            System.out.println("Value: " + dealerValue);
        }
        dealerValue = getHandValue(dealerHand, false, input);
        //Prints out if dealer busts or stands
        if (dealerValue > 21) {
            System.out.println("Dealer busted");
        } else {
            System.out.println("Dealer Stands");
        }
        return dealerValue;
    }
    
    //Calculates the value of a hand
    public static int getHandValue(Stack<Card> hand, boolean isUser, Scanner input) {
        int aceCount = 0;
        int value = 0;
        //For loop goes through every card in a hand
        for (Card card : hand) {
            int aceValue = 0;
            String face = card.toString().split(" ")[0];//gets face from card
            //adds the cards value to the hands value
            //Extra credit for including switch/case
            switch (face) {
                case "Ace":
                    //If the user is playing this lets them pick 11 or 1
                    if (isUser) {
                        System.out.println(card + " 11 or 1?");
                        aceValue = input.nextInt();
                        if (aceValue == 1) {
                            value += 1;
                        }  else {
                            value += 11;
                        }
                    } else {
                        value += 11;
                        aceCount++;
                    }
                    break;
                case "Deuce":
                    value += 2;
                    break;
                case "Three":
                    value += 3;
                    break;
                case "Four":
                    value += 4;
                    break;
                case "Five":
                    value += 5;
                    break;
                case "Six":
                    value += 6;
                    break;
                case "Seven":
                    value += 7;
                    break;
                case "eight":
                    value += 8;
                    break;
                case "nine":
                    value += 9;
                    break;
                default:
                    value += 10;
                    break;
            }
        }
        //If the dealer is playing aces will become 1's if the hands value is over 21
        while (value > 21 && aceCount > 0) {
            value -= 10;
            aceCount--;
        }
        return value;
    }
}