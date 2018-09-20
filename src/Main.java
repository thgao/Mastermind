import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 * Mastermind 
 * 
 * @author Tina Gao
 * @version May 24, 2016
 */
public class Main
{
    final static int HARD_LENGTH = 5;

    final static int MEDIUM_LENGTH = 5;

    final static int EASY_LENGTH = 4;

    final static int HARD_INDEX = 5;

    final static int MEDIUM_INDEX = 3;

    final static int EASY_INDEX = 3;

    final static String rules = "MASTERMIND \nRules: The goal of this game is to correctly guess a secret code in ten or less turns. \nYou are told which characters the code could be made up of. \nYou can use those characters any number of times to guess the pattern. \nThe size of the pattern is indicated in the underscores when you guess.  \nAfter each guess, you will be told how many exact matches \n(correct characters in the correct positions) and partial matches \n(correct character in the wrong place) were in your guess. Use logic to try and figure \nout which characters are correct to determine the secret code.";
    
    //3.1d Loops
    //3.1e Decisions
    public static void main (String args[])
    {
        int quit = 0;
        JOptionPane.showMessageDialog(null, rules); //displays rules of the game
        while (quit == JOptionPane.YES_OPTION)//menu, while user doesnt select they want to quit
        {
            int roundEnd = 0;
            boolean isPlayerChoice = true;
            int choiceInt = 0;
            int playersInt = 0;
            do//select number of players
            {
                String players = JOptionPane.showInputDialog(null, "Select the number of players: \n1. Single Player\n2. Two-Players");
                if (players == null)
                {
                    quit = JOptionPane.showConfirmDialog(null, "Would you like to quit?", "", JOptionPane.YES_NO_OPTION); 
                    if (quit == JOptionPane.YES_OPTION)
                    {
                        JOptionPane.showMessageDialog(null, "Thank you for playing.");
                        System.exit(0);
                    }
                    else
                    {
                        quit = JOptionPane.YES_OPTION; //for outer loop to keep running
                        continue;
                    }
                }
                playersInt = Parser.parse(players, isPlayerChoice);    //3.1f User Input with String Parsing called           
            }
            while (0 == playersInt);

            isPlayerChoice = false;
            if (playersInt == 1) //single player game
            {
                do//runs while user doesn't select given difficulty 
                {
                    String choice = JOptionPane.showInputDialog(null, "Please select a difficulty level: \n1. Easy\n2. Medium\n3. Hard");
                    if (choice == null)
                    {
                        quit = JOptionPane.showConfirmDialog(null, "Would you like to quit?", "", JOptionPane.YES_NO_OPTION); 
                        if (quit == JOptionPane.YES_OPTION)
                        {
                            JOptionPane.showMessageDialog(null, "Thank you for playing.");
                            System.exit(0);
                        }
                        else
                        {
                            quit = JOptionPane.YES_OPTION; //for outer loop to keep running
                            continue;
                        }
                    }
                    choiceInt = Parser.parse(choice, isPlayerChoice);//3.1f User Input with String Parsing called  
                }
                while( 0 == choiceInt);

                switch (choiceInt)//starts games at various difficulty levels
                {
                    case 1: 
                    Game game1 = new Game(EASY_LENGTH, EASY_INDEX);
                    while (roundEnd == JOptionPane.YES_OPTION)
                    {
                        game1.enterRound();

                        roundEnd = JOptionPane.showConfirmDialog(null, "Would you like to play the next round?", "", JOptionPane.YES_NO_OPTION);                       
                    }              
                    break;

                    case 2:
                    Game game2 = new Game(MEDIUM_LENGTH, MEDIUM_INDEX);
                    while (roundEnd == JOptionPane.YES_OPTION)
                    {
                        game2.enterRound();

                        roundEnd = JOptionPane.showConfirmDialog(null, "Would you like to play the next round?", "", JOptionPane.YES_NO_OPTION);                       
                    } 
                    break;

                    case 3:
                    Game game3 = new Game(HARD_LENGTH, HARD_INDEX);
                    while (roundEnd == JOptionPane.YES_OPTION)
                    {
                        game3.enterRound();

                        roundEnd = JOptionPane.showConfirmDialog(null, "Would you like to play the next round?", "", JOptionPane.YES_NO_OPTION);                       
                    } 
                    break;

                    default:
                    JOptionPane.showMessageDialog(null, "That was not an option, please choose options 1, 2 or 3.");
                }
                quit = JOptionPane.showConfirmDialog(null, "Would you like to start a new game?", "", JOptionPane.YES_NO_OPTION); 
            }
            else//two player game
            {
                isPlayerChoice = false;
                int numRoundsInt = 0;
                while (numRoundsInt == 0)//num rounds would be zero if user doesnt enter any numbers or if user enters a number greater than 5 -->( rounds is limited to 5 becuase otherwise the game would last too long)
                {
                    String numRounds = JOptionPane.showInputDialog(null, "Please enter the number of rounds you would like to play for:");

                    if(numRounds ==  null)//if user presses cancel
                    {
                        quit = JOptionPane.showConfirmDialog(null, "Would you like to quit?", "", JOptionPane.YES_NO_OPTION); 
                        if (quit == JOptionPane.YES_OPTION)
                        {
                            JOptionPane.showMessageDialog(null, "Thank you for playing.");
                            System.exit(0);
                        }
                        else
                        {
                            quit = JOptionPane.YES_OPTION; //for outer loop to keep running
                            continue;
                        }
                    }
                    numRoundsInt = Parser.parse(numRounds);  //3.1f User Input with String Parsing called        
                }

                ArrayList<String> inputPossChars = new ArrayList<String>(); //3.1c Array List
                do//users enter what characters are going to be allowed to use to make the code
                {
                    int endNow = 0;                    
                    String inputPossCharsString = (JOptionPane.showInputDialog(null, "Enter the which characters (A B C D E F G) can be used in this game:  "));

                    if (inputPossCharsString ==  null)//if user presses cancel
                    {
                        endNow = JOptionPane.showConfirmDialog(null, "Would you like to quit?", "", JOptionPane.YES_NO_OPTION);
                        if (endNow == JOptionPane.YES_OPTION)
                        {
                            JOptionPane.showMessageDialog(null, "Thank you for playing.");
                            System.exit(0);
                        }
                        else
                        {
                            continue;
                        }
                    }

                    inputPossChars.add(inputPossCharsString);
                    inputPossChars = Parser.parse (inputPossChars);//3.1f User Input with String Parsing called  
                    if (inputPossChars.size() < 2)//user must enter at least two characters other wise pattern can only be composed of one or less characters, game would be pointless
                    {
                        JOptionPane.showMessageDialog (null, "You must enter at least two characters from the selection."); 
                        while (inputPossChars.size() > 0)//empties out for re-entry 
                        {
                            inputPossChars.remove(0);
                        }
                    }
                }
                while (inputPossChars.size() < 2);//to make sure user enters at least two elements

                Game player1 = new Game(inputPossChars);
                Game player2 = new Game(inputPossChars);
                
                int player1Score = 0;
                int player2Score = 0;
                for (int i = 1; i <= 2 * numRoundsInt; i++)//num rounds is x2 be in loop header because each player goes once in a round
                {                    
                    if (i % 2 != 0) //every odd rounds (player 1 guesses)
                    {                        
                        player2Score = player2.getGameScore2();
                        player1.enterRound(i, player2Score);
                    }
                    else //player 2 guesses
                    {
                        player1Score = player1.getGameScore1();
                        player2.enterRound(i, player1Score);
                    }
                }
                int player1FinalScore = player1.getGameScore1();
                int player2FinalScore = player2.getGameScore2();
                int winner = Math.max(player1FinalScore, player2FinalScore);
                String winnerString = "";
                if (player1FinalScore == player2FinalScore)//scores are the same
                {
                    winnerString = "It's a tie!";
                }
                else if (winner == player1FinalScore)//player one had higher score
                {
                    winnerString = "Player 1 has won!";
                }
                else if (winner == player2FinalScore)//player two had higher score
                {
                    winnerString = "Player 2 has won!";
                }
                JOptionPane.showMessageDialog(null, "Player 1 Score: " + player1FinalScore + "      Player 2 Score: " + player2FinalScore + "\n" + winnerString);
                quit = JOptionPane.showConfirmDialog(null, "Would you like to start a new game?", "", JOptionPane.YES_NO_OPTION);                 
            }
        }
        JOptionPane.showMessageDialog(null, "Thank you for playing.");
        System.exit(0);
    }
}
