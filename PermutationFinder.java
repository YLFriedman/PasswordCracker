import java.util.ArrayList;
/**
*This class is used to find all the permutations of a given string, using the mutation
*rules set out in the assignment. The main idea behind this class is treating permutations 
*as binary numbers, since relevant characters can be either swapped or not swapped (0 or 1).
*For a particular permutation we generate a mask based on it's binary value, which tells us 
*which characters to swap and which to ignore.
*
*@author Yehuda Friedman, 7450466
**/
public class PermutationFinder {

    /**
    *This method takes a String input and returns an ArrayList of all the permutations of that String, 
    *not including the input String itself
    *
    *@param input the String whose permutations are to be found
    *@return an ArrayList of all permutations
    */
    public static ArrayList<String> findPermutations(String input) {
        ArrayList<String> output = new ArrayList<>();
        ArrayList<Integer> indexList = findCharacterLocations(input);
        output.add(input + "2018");
        //Deal with special case of capitalization
        if(Character.isAlphabetic(input.charAt(0))) {
            String capitalized = capitalize(input);
            output.add(capitalized);
            output.add(capitalized + "2018");
        }
        //generate a list of all important character locations
        indexList = findCharacterLocations(input);

        //The number of permutations will be 2^(number of swappable characters)
        int permutations = (int) Math.pow(2, indexList.size());

        //for each permutation
        for(int i = 1 ; i < permutations; i ++ ) {
            //generate a mask
            ArrayList<Integer> mask = createMask(i);
            
            //apply that mask to the list of character locations
            ArrayList<Integer> maskedIndexList = applyMask(indexList, mask);
            
            //used the remaining locations to mutate the input String
            String generated = applyChanges(input, maskedIndexList);
            
            //add mutated string to list of permutations
            output.add(generated);
            output.add(generated + "2018");
            //deal with special case of capitalization
            if(Character.isAlphabetic(generated.charAt(0))) {
                output.add(capitalize(generated));
                output.add(capitalize(generated) + "2018");
            }

        }
        return output;
    }

    /**
    *This private method finds all the index locations of significant characters
    *in a String. For the purposes of this assignment, significant characters are
    *'a', 'e', and 'i'.
    *
    *@param input the string you wish to obtain a list of relevant character indices
    *@return an ArrayList of Integers, where each Integer is equal to an index pointing to 
    *a relevant character
    */
   private static ArrayList<Integer> findCharacterLocations(String input) {
        ArrayList<Integer> indexList = new ArrayList<>();
        for(int i = 0; i < input.length(); i ++  ) {
            if(isKeyCharacter(input.charAt(i))){
                indexList.add(i);
            }
       }
        return indexList;
   }

   private static String applyChanges(String input, ArrayList<Integer> indexList) {
        char[] inputArray = input.toCharArray();
        for(int i = 0; i < indexList.size(); i ++ ) {
            int index = indexList.get(i);
            char c = inputArray[index];
            char replacement = swapCharacter(c);
            inputArray[index] = replacement;
        }
        return new String(inputArray);
   }
   /**
   *Takes a list of all the relevant character indexes in a particular String and applys a mask to them, returning an 
   *ArrayList of the remaing locations.
   *
   *@param indexList a list of all the locations of relevant characters
   *@param mask a list specifying which indexes of indexList should be used for a particular permutation
   *@return a list containing only the indexes that will be swapped for this permutation
   */
   private static ArrayList<Integer> applyMask(ArrayList<Integer> indexList, ArrayList<Integer> mask) {
        ArrayList<Integer> maskedIndexList = new ArrayList<>();
        for(int i = 0; i < mask.size(); i ++ ) {
            int location = mask.get(i);
            maskedIndexList.add(indexList.get(location));
        }
        return maskedIndexList;
   }

   /**
   *Creates a binary mask based on an integer value. This mask will be used to determine which 
   *'important' character locations to swap out and which ones to ignore. For example, an input of 
   *5 will result in [0,2] (i.e. binary number 101 = 5) which tells me that for this permutation only 
   *swap the first and third relevant characters.  So, applying that mask to the String "bananas" will 
   *result in the String "b@nan@s". The idea here is that for a given string, for each permutation we 
   *will generate and apply a mask to obtain all possible versions of that String.
   *
   *@input binaryVal the integer value used to generate a mask
   *@return a list representing the binary number which corresonds to a permutation
   */
   private static ArrayList<Integer> createMask(int binaryVal) {
        ArrayList<Integer> mask = new ArrayList<>();
        if(binaryVal % 2 != 0 ) {
            mask.add(0);
            binaryVal --;
        }
        while(binaryVal > 0 ) {
            int power = 1;
            while(Math.pow(2, power) <= binaryVal) {
                power ++;
            }
            power --;
            mask.add(power);
            binaryVal = (int) (binaryVal - Math.pow(2, power));
        }
        return mask;
   }

   /**
   *A simple method for swapping a character out for its' mutated version
   *
   *@param c the character to be swapped
   *@return the replacement character
   */
   private static char swapCharacter(char c) {
        switch(c) {
            case 'a' : return '@';
            case 'e' : return '3';
            case 'i' : return '1';
            default : return '?';
        }
   }

   /**
   *Private method that determines if a character is 'key' or relevant
   *
   *@param c the character you wish to check
   *@return boolean, whether the character is swappable or not
   */
    private static boolean isKeyCharacter(char c) {
        if(c == 'a' || c == 'e' || c == 'i') {
            return true;
        }
        return false;
    }

    /**
    *Private method to capitalize a given String. Useful when generating permutations.
    *
    *@param input the string to be capitalized
    *@return the capitalized version of the input
    */
    private static String capitalize(String input) {
        if (!Character.isAlphabetic(input.charAt(0))) {
            throw new IllegalArgumentException("String does not start with a letter");
        }
        else {
            return Character.toUpperCase(input.charAt(0)) + input.substring(1);
        }
    }

}
