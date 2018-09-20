import javax.swing.JOptionPane;
import java.util.ArrayList;
/**
 * Contains static methods for parsing
 * 
 * @author Tina Gao
 * @version May 24, 2016
 */
public class Parser //3.1f User Input with String Parsing
{ 
    //3.1b Overloaded Method
    //3.1d Loops
    //3.1e Decisions 
    public static int parse (String choice, boolean isPlayerChoice)//when isPlayerChoice is true, user is inputing number of players
    {
        String ints = "";
        int choiceInt = 0;

        for (int i = 0; i < choice.length(); i++)//runs for length of input
        {
            if (choice.charAt(i) == '1' || choice.charAt(i) == '2' ) 
            {
                ints = ints + choice.substring(i,i+1);
            }
            if (!isPlayerChoice)//if its not player selection, then there are three choice for difficulty selection
            {
                if (choice.charAt(i) == '3')
                {
                    ints = ints + choice.substring(i,i+1);
                }
            }                
        }
        choiceInt = ints.length();
        if (choiceInt != 1)//if the string of the parsed int is long than 1, user did not select a possible choice (ie. they could have entered "12")
        {
            JOptionPane.showMessageDialog(null, "Please choose a numbered option.");
            return choiceInt = 0;
        }

        choiceInt = Integer.parseInt(ints);
        return choiceInt;
    }

    //3.1b Overloaded Method
    //3.1c Array List
    //3.1d Loops
    //3.1e Decisions 
    public static ArrayList<String> parse (ArrayList <String> input, ArrayList <String> possibleCharacters, int index)//parses guesses and user input secret code
    {
        ArrayList <String> parsedString = new ArrayList <String>();
        String get = input.remove (0);
        get = get.toUpperCase();

        if (index == 4) //for easy and medium games
        {
            for ( int i = 0; i < get.length(); i++)//parses for length of input
            {       
                String getChar = get.substring(i, i+1);
                if (getChar.equals("A") || getChar.equals("B") || getChar.equals("C") || getChar.equals("D") || getChar.equals("E"))
                {
                    parsedString.add(getChar);
                }
            }   
        }
        else if (index == 6) //for hard games
        {
            for ( int i = 0; i < get.length(); i++)//parses for length of input
            {       
                String getChar = get.substring(i, i+1);
                if (getChar.equals("A") || getChar.equals("B") || getChar.equals("C") || getChar.equals("D") || getChar.equals("E") || getChar.equals("F") || getChar.equals("G"))
                {
                    parsedString.add(getChar);
                }
            }   
        }
        else //two player games
        {
            int sizeChar = possibleCharacters.size();
            for ( int i = 0; i < get.length(); i++)//loops user input (guess) characters
            {       
                String getChar = get.substring(i, i+1);
                for (int n = 0; n < sizeChar; n++)//this loop compares current user input character to all possible characters the other user has picked
                {
                    if (getChar.equals (possibleCharacters.get(n)))//adds user character to array list if it equals a possible character
                    {
                        parsedString.add(getChar);
                    }
                }
            } 
        }
        parsedString.trimToSize();
        return parsedString;
    }

    //3.1b Overloaded Method
    //3.1c Array List
    //3.1d Loops
    //3.1e Decisions 
    public static ArrayList<String> parse (ArrayList <String> inputPossChars)//for two player game, user input possible characters
    {
        ArrayList <String> parsedString = new ArrayList <String>();
        String get = inputPossChars.remove (0);
        get = get.toUpperCase();
        for ( int i = 0; i < get.length(); i++)//runs for user input's length
        {       
            String getChar = get.substring(i, i+1);
            //user is allowed to pick possible characters from A to J
            if (getChar.equals("A") || getChar.equals("B") || getChar.equals("C") || getChar.equals("D") || getChar.equals("E") || getChar.equals("F") || getChar.equals("G") || getChar.equals("H") || getChar.equals("I") || getChar.equals("J"))
            {
                boolean alreadyContains = false;
                for( int n = 0; n < parsedString.size(); n++)
                {
                    if (getChar.equals( parsedString.get(n)))//sets alreadyContains to true is that character has already been accounted for
                    {
                        alreadyContains = true;                        
                    }
                }
                if(!alreadyContains)//adds character to arraylist if it is not already in the arrayList
                {
                    parsedString.add(getChar);
                }
            }
        }  
        parsedString.trimToSize();
        return parsedString;
    }

    //3.1b Overloaded Method
    //3.1d Loops
    //3.1e Decisions 
    public static int parse (String userInput)//parses user input for number of rounds selection in two player games
    {
        String input = userInput;
        String ints = "";
        int intsLength = 0;

        for (int i = 0; i < input.length(); i++)
        {
            if (input.charAt(i) == '0' || input.charAt(i) == '1' || input.charAt(i) == '2' || input.charAt(i) == '3' || input.charAt(i) == '4' || input.charAt(i) == '5' || input.charAt(i) == '6' || input.charAt(i) == '7' || input.charAt(i) == '8' || input.charAt(i) == '9')
            {
                ints = ints + input.substring(i,i+1);
            }
        }
        intsLength = ints.length();
        if (intsLength == 0)//user didn't eneter any numbers
        {
            JOptionPane.showMessageDialog(null, "Input must be in numerical form.");
            return intsLength;
        }
        int length = Integer.parseInt(ints);
        if(length > 5)//rounds is restricted to less than or equal to 5 so that the game doesnt last too long
        {
            JOptionPane.showMessageDialog(null, "Number of rounds must be less than or equal to 5.");
            return length = 0;
        }
        return length;
    }

}
