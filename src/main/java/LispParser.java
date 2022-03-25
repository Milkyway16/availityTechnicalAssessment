import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LispParser {

    public static void main(String[] args) throws FileNotFoundException {
        /* This program will read a String input
        * and check to validate for parentheses correct use
        * and proper nesting */

        /*Variable declarations for Main*/
        boolean output; // end result variable
        /* ---------buffer reader and file reader variables to scan an input file------------- */
        BufferedReader br;
        FileReader fr = new FileReader("codesnipet");
        br = new BufferedReader(fr);
        /*-----------------------*/
        String line;
        String[] tokens;
        StringBuilder builder = new StringBuilder();

        /*Beginning of try/catch block to get the string stream from the test file
        * used to pass the Lisp code snippet to the program*/
        try {

            /*this while loop reads each line of the file
            * and then makes the string that will be passed to the parentheses checker method later on*/
            while ((line = br.readLine()) != null){
                tokens = line.split("\\s");
                builder.append(Arrays.toString(tokens));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                /*closing buffer and file readers*/
                if (br != null){
                    br.close();
                    fr.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }


        String inputText = builder.toString(); // input string of the parentheses checker method


        /*Call for the Checker method*/

        output = lispParenthesesParser(inputText);
        System.out.println(output);
    //    input.close();
    }

//Here is te method that checks for the proper use of parentheses on a Lisp code snippet
//when given the string as an input
    public static boolean lispParenthesesParser(String inputText) {
//Variable declarations
        int i; //counter for various loops in the code
        int count = 0; // counter to measure the amount of open AND close parentheses on the input string
        Object openParen='('; // this count also helps to determine if they are properly nested
        Object closeParen=')'; // this two objects serve the purpose of validation as the Chars the code looks for
        List myList = new ArrayList<>();
        if (inputText.indexOf("(") < inputText.indexOf(")")){  //in here it validates that the first parenthesis is an opener

            for (i=0;i<inputText.length();i++){

                myList.add(inputText.charAt(i)); // converting the string into a list for easier access to the chars
            }

            if (!myList.isEmpty()){ // validation that the string was not an empty one

                for (i=0;i < myList.size(); i++){  //cycling through the list to check each char

/*in this section the code changes our count validator
the logic being that, at any given point, the counter should never go negative
as it would indicate that a closing parentheses has been used without the corresponding opening one */
                    if ( myList.get(i) == openParen ){
                        count++;

                    }
                    if ( myList.get(i) == closeParen){
                        count--;
                    }
                    if (count <0){
                        return false;
                    }
                }
/*And finally it checks to see if all the parentheses are properly closed since the sum of the
opening and closing parentheses should be 0 if we assign the closing parentheses a value of -1
and the opening parentheses a value o +1*/
                if (count == 0){
                    return true;
                }
            }
        }
        return false;
    }
}
