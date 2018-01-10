import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 * Object for full game (contains multiple rounds)
 * 
 * @author Tina Gao 
 * @version May 24, 2016
 */
public class Game
{
    private ArrayList <String> possibleCharacters = new ArrayList <String>();
    private int length = 0;//number of elements in the secret code
    private int index = 0;//index related to gettign the correct number of possible characters from the set selection
    final private int TURNS = 10;//ten tries to guess in a round
    private int gameScore1 = 0;//game score for player one, continueing score from multiple rounds
    private int gameScore2 = 0;//game score for player two, continueing score from multiple rounds
    private int n = 0; //used in having numbering before each guess is printed so user can see how many guesses they have taken already ie)1.________, 2. __________
    private int playerRound;
    private boolean isWon;
    
    //3.1b Constructor
    //3.1c Array List --> fillPossCharacters(index) fills an array list
    //3.1h Calling Methods in Same Class --> fillPossCharacters(index)
    public Game (int l, int i)//single player game
    {      
        length = l;
        index = i;
        fillPossCharacters(index);
    }
    
    //3.1b Constructor
    //3.1c Array List --> parameter of an array list gets saved
    public Game (ArrayList<String> userPossChars) //two players
    {
        possibleCharacters = userPossChars;
    }
    
    //3.1b Overloaded Method
    //3.1c Array List
    //3.1d Loops
    //3.1e Decisions
    //3.1h Calling Methods in Same Class --> arrayToString(   ) 
                                       //--> allGuessesToString(   )
    public void enterRound ()// Single player- runs for one round of a game (ten guesses)
    {
        ArrayList<String> allGuesses = new ArrayList<String>(); //stores every guess in a round
        Round round = new Round(possibleCharacters, length);//creates a round object for this game
        String possibleCharsString = arrayToString(possibleCharacters);
        String spaces =  "";
        for (int i = 0; i < length; i++) //loops to save a string that visually displays with underscore how many elements are in the secret code
        {
            spaces = spaces + "_ ";
        }
        String allGuessesString = "Guess the Code\nUsable Characters: " + possibleCharsString + "\n" + spaces + "\n";
        //this string stores everything that needs to be printed, ie) useable characters, numbering, each guess, number of matches

        for (int i = 0; i < TURNS && isWon == false ; i++)//runs until ten turns are used up or the player correctly guesses the code
        {
            ArrayList<String> inputGuess = new ArrayList<String>();
            isWon = false;
            do//user enters a guess
            {      
                String input = (JOptionPane.showInputDialog(null, allGuessesString)); //user inputs a guess            
                if (input == null)//if user presses cancel
                {
                    int quit = JOptionPane.showConfirmDialog(null, "Would you like to quit?", "", JOptionPane.YES_NO_OPTION); 
                    if (quit == JOptionPane.YES_OPTION)
                    {
                        JOptionPane.showMessageDialog(null, "Thank you for playing.");
                        System.exit(0);
                    }
                    else
                    {                    
                        continue;
                    }
                }
                inputGuess.add(input);
                inputGuess = Parser.parse (inputGuess, possibleCharacters, index);//3.1f User Input with String Parsing called  

                if (inputGuess.size() != round.getSecretCode().size()) // if they dont enter a guess that is the same length as the secret code
                {
                    while (inputGuess.size () > 0)//empty out the array list
                    {
                        inputGuess.remove(0);
                    }
                    JOptionPane.showMessageDialog(null, "Your guess must have " + round.getSecretCode().size() + " elements.");                    
                    continue;
                }
                String inputString = arrayToString (inputGuess);
                allGuesses.add(inputString); // adds the guess to allGuesses array
            }           
            while (inputGuess.size() != round.getSecretCode().size());//to make sure guess has right number of charactesr

            ArrayList<String> matches = new ArrayList<String>();
            matches = round.printMatches(inputGuess); //calcs matches from this guess
            allGuesses.add(matches.get(0));//adds to array list to be converted into string

            allGuessesString = allGuessesToString (allGuesses, allGuessesString);//adds guess with numbering to string to be printed and matches ie) 1. A B B C \nExact Matches: 1    Partial Matches : 0

            if (matches.size() == 2) //if matches arrayList returns two elements, then player has guessed the code correctly
            {
                gameScore1 = gameScore1 + round.getScore(); //updates ongoing game score
                isWon = true;

                allGuessesString = allGuessesString + "Congrats, you won in " + round.getTurns() + " turns! Current Score: " + gameScore1;//adds winning message to printing String            

                n = 0;//resets instance variable for numbering guesses at end of round

                JOptionPane.showMessageDialog(null,  allGuessesString);

            }
        }
        if (isWon == false) //if player reaches this point, they have not guessed the code. 
        {
            ArrayList<String> secretCode = new ArrayList<String>();
            secretCode = round.getSecretCode(); //gets what the secret code was
            String secretCodeString = arrayToString(secretCode);
            gameScore1 = gameScore1 + round.getScore();      //updates score     
            allGuessesString = allGuessesString + "You could not guess the code. Secret Code: " + secretCodeString +"\nCurrent Score: " + gameScore1;//adds losing message to printing string           
            JOptionPane.showMessageDialog(null,  allGuessesString);
        }
        isWon = false; //resets
    }
    
    //3.1b Overloaded Method
    //3.1c Array List
    //3.1d Loops
    //3.1e Decisions
    //3.1h Calling Methods in Same Class --> arrayToString(   ) 
                                       //--> allGuessesToString(   )
    public void enterRound (int roundNum, int otherPlayerScore)//two players - runs for one round of a game (ten guesses) //int n is only for two players, make is overloaded later
    {
        ArrayList<String> allGuesses = new ArrayList<String>(); //stores every guess in a round

        String possibleCharactersString = arrayToString(possibleCharacters);
        Round round = new Round(possibleCharacters, possibleCharactersString, roundNum);//creates a round object for this game

        String possibleCharsString = arrayToString(possibleCharacters);
        String spaces =  "";
        length = round.getSecretCode().size();
        for (int i = 0; i < length; i++) //loops to save a string that visually displays with underscore how many elements are in the secret code
        {
            spaces = spaces + "_ ";
        }
        String guesser = "";

        int thisRound;
        if (roundNum % 2 != 0) // every odd run through is player 1's turn to guess and the start of a new round
        {
            guesser = "Player 1 Guesses";
            thisRound = (roundNum + 1) / 2;
        }
        else//even runs are player 2's turn to guess
        {
            guesser = "Player 2 Guesses";
            thisRound = roundNum / 2;
        }

        String allGuessesString = "Round " +  thisRound + "\n" + guesser + "\nUsable Characters: " + possibleCharsString + "\n" + spaces + "\n";
        //this string stores everything that needs to be printed, ie) useable characters, numbering, each guess, number of matches

        for (int i = 0; i < TURNS && isWon == false ; i++)//runs until ten turns are used up or the player correctly guesses the code
        {
            ArrayList<String> inputGuess = new ArrayList<String>();
            isWon = false;
            do//user enters guess
            {              
                String input = (JOptionPane.showInputDialog(null, allGuessesString)); //user inputs a guess            
                if (input == null)//if user presses cancel
                {
                    int quit = JOptionPane.showConfirmDialog(null, "Would you like to quit?", "", JOptionPane.YES_NO_OPTION); 
                    if (quit == JOptionPane.YES_OPTION)
                    {
                        JOptionPane.showMessageDialog(null, "Thank you for playing.");
                        System.exit(0);
                    }
                    else
                    {                    
                        continue;
                    }
                }
                inputGuess.add(input);
                inputGuess = Parser.parse (inputGuess, possibleCharacters, index);//3.1f User Input with String Parsing called  

                if (inputGuess.size() != round.getSecretCode().size())//if user enters a guess that is not the same number of characters as the secret code
                {
                    while (inputGuess.size () > 0)//empty out the array list
                    {
                        inputGuess.remove(0);
                    }
                    JOptionPane.showMessageDialog(null, "Your guess must have " + round.getSecretCode().size() + " elements.");                   
                    continue;
                }
                String inputString = arrayToString (inputGuess);
                allGuesses.add(inputString); // adds the guess to allGuesses array
            }           
            while (inputGuess.size() != round.getSecretCode().size()); //to make sure input is right amount of characters

            ArrayList<String> matches = new ArrayList<String>();
            matches = round.printMatches(inputGuess); //calcs matches from this guess
            allGuesses.add(matches.get(0));//adds to array list to be converted into string

            allGuessesString = allGuessesToString (allGuesses, allGuessesString);//adds guess with numbering to string to be printed and matches ie) 1. A B B C \nExact Matches: 1    Partial Matches : 0

            if (matches.size() == 2) //if matches arrayList returns two elements, then player has guessed the code correctly
            {
                if (guesser.equals( "Player 1 Guesses"))
                {
                    gameScore1 = gameScore1 + round.getScore();//updates player one score
                    gameScore2 = otherPlayerScore;//saves player 2 score from input into method (input was player 2's game score 2)
                }
                if (guesser.equals( "Player 2 Guesses"))
                {
                    gameScore2 = gameScore2 + round.getScore();//updates player 2 score
                    gameScore1 = otherPlayerScore;//saves player 1 score from input into method (input was player 1's game score 1)
                }
                //updates ongoing game score
                isWon = true;
                //two player winning message
                allGuessesString = allGuessesString + "Congrats, you won in " + round.getTurns() + " turns! \nPlayer 1 Current Score: " + gameScore1 + "      Player 2 Current Score: " + gameScore2;//adds winning message to printing String, adds both players current scores            

                n = 0;//resets instance variable for numbering guesses at end of round

                JOptionPane.showMessageDialog(null,  allGuessesString);

            }
        }
        if (isWon == false) //if player reaches this point, they have not guessed the code. 
        {
            if (guesser.equals("Player 1 Guesses"))
            {
                gameScore1 = gameScore1 + round.getScore();//updates player one score
                gameScore2 = otherPlayerScore;//saves player 2 score from input into method (input was player 2's game score 2)
            }
            if (guesser.equals("Player 2 Guesses"))
            {
                gameScore2 = gameScore2 + round.getScore();//updates player 2 score
                gameScore1 = otherPlayerScore;//saves player 1 score from input into method (input was player 1's game score 1)
            }
            
            //two player losing message
            ArrayList<String> secretCode = new ArrayList<String>();
            secretCode = round.getSecretCode();
            String secretCodeString = arrayToString(secretCode);

            allGuessesString = allGuessesString + "You could not guess the code. Secret Code: " + secretCodeString + "\nPlayer 1 Current Score: " + gameScore1 + "      Player 2 Current Score: " + gameScore2;////adds losing message to printing string, adds both players current scores            

            JOptionPane.showMessageDialog(null,  allGuessesString);
        }
        isWon = false; //resets
    }
    
    //3.1b Mutators
    //3.1c Array List --> fills an array list
    //3.1d Loops
    private void fillPossCharacters(int index)
    {
        String abc = "ABCDEFGHIJ"; 
        for (int i = 0; i <= index; i++)//fills possibleCharacters based on index based on the difficulty selected
        {
            possibleCharacters.add(abc.substring(i, i + 1));
        }
    }

    //3.1b Mutators
    //3.1c Array List --> turns the array list to string
    //3.1d Loops
    private String arrayToString (ArrayList<String> inputArray)
    {
        String inputArrayString = "";
        for (int i = 0; i < inputArray.size(); i++)
        {
            String get = inputArray.get(i);
            inputArrayString = inputArrayString + get + " "; //adds character and a space after so it looks like this: A B C instead of: ABC
        }
        return inputArrayString;
    }

    //3.1b Mutators
    //3.1c Array List --> maniputlates an array list
    //3.1d Loops
    //3.1e Decisions
    private String allGuessesToString (ArrayList<String> allGuesses, String allGuessesString)
    {
        String tempGuess = "";      
        n = n + 1; //ie) 1.___ 2. ____ (to list each guess)
        allGuessesString = allGuessesString + n + ". ";
        while (allGuesses.size() > 0)
        {      
            tempGuess = allGuesses.remove(0);
            allGuessesString = allGuessesString + tempGuess + "\n"; //enter between lines eg) 1. A A A A 
                                                                                            //2. B B B B 
        }
        if (n == TURNS)
        {
            n = 0; //resets instance variable at end of round for next game
        }
        return allGuessesString;
    }
    
    //3.1b Accessor
    public int getGameScore1 ()//player 1/singleplayer
    {
        return gameScore1;
    }
    
    //3.1b Accessor
    public int getGameScore2 ()//player 2
    {
        return gameScore2;
    }
}
