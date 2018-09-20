import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
/**
 * Round class is object for a round (one ten turn run, one secret code per Round object)
 * 
 * @author Tina Gao
 * @version May 24, 2016
 */
public class Round
{
    private Random rand = new Random ();
    private ArrayList <String> secretCode = new ArrayList <String>(); 
    private int score;
    private ArrayList <String> allGuesses = new ArrayList <String>();
    private int turns = 0;
    private boolean isWin = false;
    
    //3.1b Constructor
    //3.1c Array List
    //3.1d Loops
    //3.1g Randomzation
    public Round (ArrayList <String> possibleCharacters, int length)//single player game
    {
        for (int i = 0; i < length; i++) //randomly generates secret code
        {
            int random = rand.nextInt(possibleCharacters.size());
            String randomGet = possibleCharacters.get(random);
            secretCode.add(randomGet);
        }
    }
    
    //3.1b Constructor
    //3.1c Array List
    //3.1d Loops
    //3.1e Decisions
    public Round (ArrayList <String> possibleCharactersArray, String possibleCharacters, int playerRound)//two players
    {
        int playerToEnterCode;
        if (playerRound % 2 != 0)//player 1 guesses, player 2 enter code
        {
            playerToEnterCode = 2;
        }
        else//player 2 guesses, player 1 enters code
        {
            playerToEnterCode = 1;
        }
        JOptionPane.showMessageDialog(null, "Player " + playerToEnterCode + " will create the secret code.");
        int correct = JOptionPane.NO_OPTION; //initializes
        while(correct == JOptionPane.NO_OPTION) //while user doesnt select yes to code input being correct
        {
            String input = (JOptionPane.showInputDialog(null, "Characters to choose from: " + possibleCharacters));

            if (input == null) //if user presses cancel
            {
                int quit = JOptionPane.showConfirmDialog(null, "Would you like to quit?", "", JOptionPane.YES_NO_OPTION); 
                if (quit == JOptionPane.YES_OPTION)
                {
                    JOptionPane.showMessageDialog(null, "Thank you for playing.");
                    System.exit(0);
                }
                else
                {                    
                    correct = JOptionPane.NO_OPTION;
                    continue;
                }
            }
            secretCode.add(input);//adds user input to array

            secretCode = Parser.parse(secretCode, possibleCharactersArray, 0);//parses array //3.1f User Input with String Parsing called  
            if (secretCode.size() == 0)//no usable characters were entered thus, the returned array is empty
            {
                JOptionPane.showMessageDialog(null, "Secret code can only contain the useable characters.");
                continue;
            }
            correct = JOptionPane.showConfirmDialog(null, "Is " + secretCode + " correct?", "", JOptionPane.YES_NO_OPTION);
            if (correct == JOptionPane.NO_OPTION)//'no' was selected
            {
                while( secretCode.size() > 0)//empties array list out for re entering
                    secretCode.remove(0);
            }
        }       
    }
    
    //3.1b Mutator
    //3.1c Array List
    //3.1d Loops
    //3.1e Decisions
    private ArrayList<Integer> matches (ArrayList<String> guess)
    {
        ArrayList <String> nonMatchesCode = new ArrayList <String>();
        ArrayList <String> nonMatchesGuess = new ArrayList <String>();
        ArrayList <Integer> returnValues = new ArrayList <Integer>();
        int exactMatches = 0;
        int partialMatches = 0; //these made more sense as local variables than instance like on the original UML
        for (int i = 0; i < guess.size(); i++)
        {
            if (guess.get(i).equals(secretCode.get (i)))
            {
                exactMatches++; //exact matches, right character, right place
            }
            else //makes array list of potential partial matches
            {
                nonMatchesCode.add(secretCode.get(i));//remaing characters from guess
                nonMatchesGuess.add(guess.get(i));
            }
        }
        int index;
        int nonMatchesSize = nonMatchesGuess.size();
        while (nonMatchesGuess.size() > 0)// to look for partial matches to the pattern
        {
            index = 0;
            String character = nonMatchesGuess.get(0);
            int numCharactersGuess = 0;
            int numCharactersCode = 0;

            while (nonMatchesGuess.contains (character))//while the ramaining guess characters still have the current one being parsed
            {            
                if (character.equals(nonMatchesGuess.get (index)))
                {
                    numCharactersGuess++; //number of instances of a character in the remaining guess characters
                    nonMatchesGuess.remove(index);
                }
                else
                {
                    index++;
                }
            }
            for (int c = 0; c < nonMatchesCode.size(); c++)//number of times that character appears in the remaing secret code characters
            {            
                if (character.equals(nonMatchesCode.get (c)))
                {
                    numCharactersCode++; 
                }
            }
            partialMatches = partialMatches + Math.min(numCharactersGuess, numCharactersCode); //the lesser of the two is the number of partial matches of that character
        }
        returnValues.add(exactMatches);
        returnValues.add(partialMatches);
        turns ++;
        if (exactMatches == secretCode.size())//when number of exact matches equals length of secret code, the guess was correct
        {
            isWin = true;
            return returnValues;
        }

        return returnValues;
    }
    
    //3.1b Mutator
    //3.1e Decisions
    private void calcScore()
    {
        if (!isWin) 
        {
            score = score + 5; //if the player did not guess the code, they get 5 points for trying
        }
        else
        {
            int amountAdd = 30 - turns; //if the player guessed correctly, this takes into account in how many turns they did it (arbitriary choice of 30 - turns, the faster, the less it will subtract from 30)
            score = score + amountAdd;
        }
    }
    
    //3.1b Accessor 
    public ArrayList <String> getSecretCode()
    {
        return secretCode;
    }
    
    //3.1b Accessor 
    //3.1h Calling Methods in Same Class --> calcScore()
    public int getScore ()
    {
        calcScore();
        isWin = false;
        return score;
    }
    
    //3.1b Mutator
    //3.1b Accessor 
    //3.1c Array List
    //3.1e Decisions
    //3.1h Calling Methods in Same Class --> matches(guess)
    public ArrayList<String> printMatches(ArrayList<String> guess)
    {
        ArrayList <String> returnMatches = new ArrayList <String>();
        ArrayList <Integer> returnValues = new ArrayList <Integer>();
        returnValues = matches (guess);

        if (isWin != true)  //if the code was not guessed
        {
            int exactMatches = returnValues.get(0);
            int partialMatches = returnValues.get(1);
            returnMatches.add ("Exact Matches: " + exactMatches + "    Partial Matches: " + partialMatches);
        }
        else //if the code was guessed
        {            
            int exactMatches = returnValues.get(0);
            int partialMatches = returnValues.get(1);
            returnMatches.add ("Exact Matches: " + exactMatches + "    Partial Matches: " + partialMatches);
            returnMatches.add("won");
        }
        return returnMatches;
    }
    
    //3.1b Accessor 
    public int getTurns()
    {
        return turns;
    }
}
