import java.util.Dictionary;

/*
 * A version of the Scrabble game.
 */
public class Scrabble {
    // Note: "Class variables", like the following five class-level variables,
    // are global variables that can be accessed by all the functions in the class.
    // It is customary to name class variables using capital letters and underline
    // characters. If a variable is "final", it means that it is treated as a
    // constant value which is declared once and cannot changed later.

    // Name of the dictionary file:
    static final String WORDS_FILE = "dictionary.txt";

    // Number of words in the dictionary file (assumed to be at most 100,000):
    static int NUM_OF_WORDS;

    // The dictionary array (will be read from the dictionary file)
    static String[] DICTIONARY = new String[100000];

    // The "Scrabble value" of each letter in the English alphabet
    static final int[] SCRABBLE_LETTER_VALUES = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3,
            1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};

    // The hand size (number of random letters dealt at each round of the Scrabble game):
    static int HAND_SIZE = 10;

    /**
     * Initializes the game by performing the following SIDE EFFECTS:
     * 1. Populates the DICTIONARY array with all the words found in the WORDS_FILE.
     * Each word is stored in its lowercase version.
     * 2. Sets NUM_OF_WORDS to the number of words found in the file.
     * 3. Sets standard input to the keyboard.
     */
    public static void init() {
        // Sets the standard input stream to the given word file
        StdIn.setInput(WORDS_FILE);
        System.out.println("Loading word list from file...");
        // Reads all the words from the file into the DICTIONARY array
        DICTIONARY = StdIn.readAllLines();
        for(int i = 0;i < DICTIONARY.length; i++){
            if (!DICTIONARY[i].equals(DICTIONARY[i].toLowerCase())) {
                DICTIONARY[i] = DICTIONARY[i].toLowerCase();
            }
        }
        NUM_OF_WORDS = DICTIONARY.length;
        System.out.println(NUM_OF_WORDS + " words loaded.");
        // Sets the standard input stream to the keyboard, to allow interaction with the user.
        StdIn.setInput("keyboard");
    }

    /**
     * Returns the Scrabble score of a given word.
     * The score of a word is the sum of the points of the letters in the word,
     * multiplied by the length of the word, plus 50 points if the length of the word is n.
     *
     * @param word - a lowercase string of letters
     * @param n    - a given integer
     * @return - the Scrabble value of the word
     */
    public static int getWordScore(String word, int n) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            count += SCRABBLE_LETTER_VALUES[letter - 'a'];
        }

        int score = count*word.length();
        if (word.length() == n) {
            score = score + 50;
        }
        return score;
    }

    /**
     * Runs a single hand in a Scrabble game. The hand starts with n letters.
     *
     * @param hand - the hand
     * @param n    - the initial size of the hand
     **/
    public static void playHand(String hand) {
        int n = hand.length();
        int score = 0;
        while (n > 0) {
            System.out.println(hand);
            System.out.println("Enter a word, or '.' to finish this hand:");
            String input = StdIn.readString();
            input = input.toLowerCase();
            if (input.indexOf(".") == 0) {
                break;
            } else if (subsetOf(input, hand) == true) {
                System.out.println("Invalid word. Try again");
            } else if (isWordInDictionary(input, DICTIONARY) == false) {
                System.out.println("No such word in the dictionary. Try again");
            } else {
                System.out.println("the input is a valid word");
                int handScore = getWordScore(input, hand.length());
                score = score + handScore;
                System.out.println(input + " earned " + score + " points. Total: " + score + " pointsscore");
                hand = remove(hand, input);
                System.out.println();
                if (hand == "") {
                    System.out.println("Ran out of letters” and the score of the hand");
                } else {
                    System.out.println("End of hand. Total score:" + score + " points");
                }

            }
        }
    }

    /**
     * Initializes the game, and then allows the user to play an arbitrary number of hands.
     * <p>
     * 1) Asks the user to input 'n' or 'r' or 'e'.
     * - If the user inputs 'n', lets the user play a new (random) hand.
     * - If the user inputs 'r', lets the user play the last hand again
     * (works only if this is not the first hand).
     * - If the user inputs 'e', exits the game.
     * - If the user inputs anything else, writes that the input is invalid.
     * <p>
     * 2) When the user is done playing the hand, repeats from step 1.
     */
    public static void playGame() {
        char next = StdIn.readChar();
        init();
        while (true) {
            if (next == 'n') {
                String newHand = StdIn.readString();
                playHand(newHand);
            } else if (next == 'r') {
                String oldHand = StdIn.readString();
                playHand(oldHand);
            } else if (next == 'e') {
                break;
            } else {
                System.out.println("input is Invalid");
            }
            // Put your code here
        }
    }

    // Checks is the given word is in the given dictionary.
    private static boolean isWordInDictionary(String word, String[] dictionary) {
        boolean check = true;
        for (int i = 0; i < dictionary.length; i++) {
            if(dictionary[i] != null){
                if (dictionary[i].toLowerCase().equals( word.toLowerCase())) {
                    check = true;
                }
                else{
                    check = false;
                }
            }
        }
        return check;
    }


    public static void main(String[] args) {
        //playGame();
        testPlayHand();

    }

    public static void testPlayHand() {
        playHand("pzuttto");
        playHand("aqwffip");
        playHand("aretiin");
    }


    public static int countChar(String str, char c) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns true if str1 is a subset string str2, false otherwise.
     * For example, "spa" is a subset of "space", and "pass" is not
     * a subset of "space".
     *
     * @param str1 - a string
     * @param str2 - a string
     * @return true is str1 is a subset of str2, false otherwise
     */
    public static boolean subsetOf(String str1, String str2) {
        boolean check = true;
        for (int i = 0; i < str2.length() - str1.length() + 1; i++) {
            if (str2.charAt(i) == str1.charAt(0)) {
                check = true;
                for (int j = 0; j < str1.length(); j++) {
                    if (str2.charAt(j + i) != str1.charAt(j)) {
                        check = false;
                        break;
                    }
                }
                if (check) {
                    return true;
                }
            }
        }
        return false;
    }



    /**
     * Returns a string which is the same as the given string, with a space
     * character inserted after each character in the given string, except
     * for last character of the string, that has no space after it.
     * Example: if str is "silent", returns "s i l e n t".
     *
     * @param str - a string
     * @return a string consisting of the characters of str, separated by spaces.
     */
    public static String spacedString(String str) {
        String spaced = "";
        for (int i = 0; i < str.length() - 1; i++) {
            spaced = spaced + str.charAt(i) + " ";
        }
        spaced = spaced + str.charAt(str.length() - 1);
        return spaced;
    }


    /**
     * Returns a string of n lowercase letters, selected randomly from
     * the English alphabet 'a', 'b', 'c', ..., 'z'. Note that the same
     * letter can be selected more than once.
     *
     * @param n - the number of letter to select
     * @return a randomly generated string, consisting of 'n' lowercase letters
     */
    public static String randomStringOfLetters(int n) {
        String output = "";
        for (int i = 0; i < n; i++) {
            char randomLetter = (char) ('a' + Math.random() * ('z' - 'a' + 1));
            output = output + randomLetter;
        }
        return output;
    }

    /**
     * Returns a string consisting of the string str1, minus all the characters in the
     * string str2. Assumes (without checking) that str2 is a subset of str1.
     * Example: "committee" minus "meet" returns "comit".
     *
     * @param str1 - a string
     * @param str2 - a string
     * @return a string consisting of str1 minus all the characters of str2
     */
    public static String remove(String str1, String str2) {

        String output = "";
        boolean check = true;
        char[] stringTwo = str2.toCharArray();
        for (int i = 0; i < str1.length(); i++) {
            check = true;
            for (int j = 0; j < stringTwo.length; j++) {
                if (str1.charAt(i) == stringTwo[j]) {
                    stringTwo[j] = 0;
                    check = false;
                    break;
                }

            }
            if (check) {
                output = output + str1.charAt(i);
            }
        }
        return output;
    }
}

