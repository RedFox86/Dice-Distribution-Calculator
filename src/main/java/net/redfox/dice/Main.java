package net.redfox.dice;

public class Main {
  private static final int[] diceUnweightedProbabilities = new int[12];
  private static final int[] diceWeightedProbabilities = new int[12];
  private static final float[] doubleRollChances = new float[12];
  private static final float[] dicePercentages = new float[12];
  private static final float[] diceAverages = new float[12];

  public static void main(String[] args) {
    System.out.println("| Each Number |  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  | 10  | 11  | 12  | Each dice number.");
    System.out.println("| Dice Combos "+getDiceProbabilities() + " Amount of possible ways to trigger the card at least once.");
    System.out.println("| Percentages "+getDicePercentages() + " Percentage chance of having at least 1 of that number.");
    System.out.println("| Double Roll "+getDoubleRollChances() + " Percentage chance of rolling two of that number.");
    System.out.println("| Activations "+getDiceActivations() + " Your card activates this many times on average in ten turns.");
  }

  private static String getDiceProbabilities() {
    //Loop through 1-6 for the first dice
    for (int i = 1; i <= 6; i++) {
      //Nested loop through 1-6 for the second dice
      for (int j = 1; j <= 6; j++) {
        //Keep track of the weighted and unweighted average (doesn't count if you role two of the same number)
        diceUnweightedProbabilities[i-1]++;
        diceWeightedProbabilities[i-1]++;
        if (i != j) diceUnweightedProbabilities[j-1]++;
        diceWeightedProbabilities[j-1]++;
        diceUnweightedProbabilities[i+j-1]++;
        diceWeightedProbabilities[i+j-1]++;
      }
    }
    //Turn into a formatted String and return
    String result = "|";
    for (int i : diceUnweightedProbabilities) {
      String number = ""+i;
      if (number.length()==1) result+="  "+number+"  |";
      else result+="  "+number+" |";
    }
    return result;
  }

  private static String getDicePercentages() {
    //Fill the percentage array by doing combinations divided by sum
    for (int i = 1; i <= 12; i++) {
      dicePercentages[i-1] = (float)diceUnweightedProbabilities[i-1]/36.0f;
    }
    //Turn into a formatted String and return
    String result = "|";
    for (float f : dicePercentages) {
      String number = ""+(float)((int)(f * 10000))/100.0f;
      if (number.length() == 5) result+=number+"|";
      else {
        while (number.length()!=5) number+="0";
        result+=number+"|";
      }
    }
    return result;
  }

  private static String getDoubleRollChances() {
    //Fill the array with 1/36 each time a role is detected where each number is the same
    for (int i = 1; i <= 6; i++) {
      for (int j = 1; j <= 6; j++) {
        if (i == j) doubleRollChances[i-1]+=1.0f/36.0f;
      }
    }
    //Turn into a formatted String and return
    String result = "|";
    for (float f : doubleRollChances) {
      String number = ""+(float)((int)(f * 10000))/100.0f;
      if (number.length() == 5) result+=number+"|";
      else {
        while (number.length()!=5) number+="0";
        result+=number+"|";
      }
    }
    return result;
  }

  private static String getDiceActivations() {
    //Fill the percentage array by doing combinations divided by sum, but this time with weighted average instead of unweighted
    for (int i = 1; i <= 12; i++) {
      diceAverages[i-1] = (float)diceWeightedProbabilities[i-1]/36.0f;
    }
    //Turn into a formatted String and return
    String result = "|";
    for (float f : diceAverages) {
      //Multiply by ten to simulate ten roles
      String number = ""+(float)((int)(f * 10000))/1000.0f;
      if (number.length() == 5) result+=number+"|";
      else {
        while (number.length()!=5) number+="0";
        result+=number+"|";
      }
    }
    return result;
  }
}