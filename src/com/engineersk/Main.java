package com.engineersk;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        aboutGame();
        String text = generateRandomArticle();
        String[] paragraphTexts = text.split("\n");
        startGame(paragraphTexts);
    }

    private static String generateRandomArticle() {
        
        String[] words={"game theory","software","hardware","engineer", "architecture", "application",
                "design patterns", "programmers", "object-oriented programming", "networking",
                "communication","technology", "programming language", "assembly language", "engineering",
                "interface", "electronics", "control", "robotics", "data structures", "algorithms",
                "artificial intelligence", "HacktoberFest2020", "Open-Source", "Contribution"};

        StringBuilder text= new StringBuilder();

        for(int i=0; i<8; ++i){
            String word;
            for(int j=0; j<100; ++j){
                Random rnd = new Random();
                word=words[rnd.nextInt(words.length)];
                word+=" ";
                text.append(word);
            }
            text.append("\n");
        }
        return text.toString();
    }

    public static void startGame(String[] paragraphTexts){ 
        int menuOption = selectGameMode();
        String extractedParagraph = extractParagraphFromText(menuOption, paragraphTexts);
        String encryptedText = encryptParagraph(extractedParagraph);
        decryptParagraph(encryptedText, extractedParagraph);
    }

    private static int selectGameMode() {
        int menuOption = 0;
        boolean isValidMenuOption = false;

        do {
            System.out.println("Please select a menu option as stated below");
            System.out.println("1 - Normal mode");
            System.out.println("2 - Test mode");
            Scanner scanner = new Scanner(System.in);
            try {
                menuOption = scanner.nextInt();

                if (menuOption == 1 || menuOption == 2){
                    isValidMenuOption = true;
                }else{
                    System.out.println("Invalid entry!!!");
                    System.out.println();
                }
            }
            catch(IllegalArgumentException | InputMismatchException illegalArgumentEx){
                System.err.println("An integer value is expected!!!");
            }

        } while (!isValidMenuOption);
        
        return menuOption;
    }

    private static String extractParagraphFromText(int menuOption, String[] paragraphTexts) {
        String extractedParagraph;
        if(menuOption == 1){
            System.out.println("NORMAL MODE");
            Random rnd = new Random();
            extractedParagraph = paragraphTexts[rnd.nextInt(paragraphTexts.length)];
        }else{
            System.out.println("TEST MODE");
            String[] truncatedTexts = new String[paragraphTexts.length];

            for(int i=0; i<paragraphTexts.length; ++i)
                truncatedTexts[i]=paragraphTexts[i].substring(0,50);

            extractedParagraph = chooseTruncatedParagraph(truncatedTexts);
        }
        return extractedParagraph;
    }

    private static String chooseTruncatedParagraph(String[] truncatedTexts) {

        int choice=0;
        boolean isValidChoice = false;
        do {
            System.out.println("choose a paragraph text from 1 - 8 as displayed below");
            for(int i=0; i<truncatedTexts.length; ++i)
                System.out.println((i+1)+" - "+truncatedTexts[i]);

            Scanner scanner = new Scanner(System.in);
            try {
                choice = scanner.nextInt();

                if (choice > 0 && choice <= 8){
                    isValidChoice = true;
                }else{
                    System.out.println("Invalid entry!!!");
                    System.out.println();
                }
            }
            catch(IllegalArgumentException | InputMismatchException illegalArgumentEx){
                System.err.println("An integer value is expected!!!");
            }

        } while (!isValidChoice);

        return truncatedTexts[choice-1];
    }

    private static String encryptParagraph(String rawString) {
        final int alphabetLength = 26;

        char[] alphabets = new char[alphabetLength];
        char[] randomAlphabets = new char[alphabetLength];

        generateRandomAlphabets(alphabets, randomAlphabets);

        String rawStringToUpper = rawString.toUpperCase();
        System.out.println("*****PARAGRAPH TEXT*************");
        System.out.println(rawStringToUpper);
        System.out.println('\n');
        String encryptedParagraph="";

        for(int i=0; i<rawStringToUpper.length(); ++i){
            boolean isEncryptedParagraph=true;
            if(Character.isLetter(rawStringToUpper.charAt(i))&&Character.isUpperCase(rawStringToUpper.charAt(i))){
                for(int j=0; j<alphabetLength; ++j){

                    if(rawStringToUpper.charAt(i)==alphabets[j]){

                        encryptedParagraph = replaceText(rawStringToUpper, String.valueOf(rawStringToUpper.charAt(i)),
                                String.valueOf(randomAlphabets[j]).toLowerCase());
                        rawStringToUpper = encryptedParagraph;
                    }
                }
                for(int k=0; k<encryptedParagraph.length(); ++k){
                    if(Character.isLetter(encryptedParagraph.charAt(k))
                            &&Character.isUpperCase(encryptedParagraph.charAt(k))) {
                        isEncryptedParagraph=false;
                        break;
                    }
                    else
                        isEncryptedParagraph=true;
                }
                if(isEncryptedParagraph)
                    break;
            }
        }
        System.out.println("****ENCRYPTED PARAGRAPH****");
        System.out.println(encryptedParagraph);
        System.out.println();
        return encryptedParagraph;
    }

    private static void generateRandomAlphabets(char[] alphabets, char[] randomAlphabets) {
        for(int i=0; i<alphabets.length; ++i){
            int alphabetAsciiInteger = (int)'A' + i;
            alphabets[i] = (char)alphabetAsciiInteger;
        }
        System.out.println("****RANDOMLY GENERATED ALPHABETS****");
        for(char alphabet : alphabets)
            System.out.print(alphabet + " ");
        System.out.println();

        Random rnd = new Random();

        for(int i=0; i<randomAlphabets.length; ++i){
            int sameAlphabetIndex;
            do{
                sameAlphabetIndex=-1;
                int randomAlphabetAsciiInteger = (int)'A' + rnd.nextInt(26);

                randomAlphabets[i] = (char)randomAlphabetAsciiInteger;

                for(int j=0; j<=i; ++j){

                    if(((i!=j)&&(randomAlphabets[i]==randomAlphabets[j]))
                            ||((i==j)&&(randomAlphabets[i]==alphabets[j]))){
                        sameAlphabetIndex = j;
                        break;
                    }
                }
            }while(sameAlphabetIndex>=0 && sameAlphabetIndex<=i);
        }

        for(int i = 0; i< 26; ++i)
            System.out.printf("%s ", randomAlphabets[i]);
        System.out.println("\n");
    }

    private static void decryptParagraph(String encryptedText, String rawString){

        boolean isValidUserInput=false;
        int helpCounter = 0;
        char[] helpCharacter = new char[5];
        String decryptedText = resetParagraph(encryptedText, helpCharacter);

        char answer = 'y';
        do{
            boolean isHelpCharacter = false;
            displayGameInstruction(encryptedText, decryptedText);
            Scanner scanner = new Scanner(System.in);
            try {
                String userEntry = scanner.nextLine();
                String entry=userEntry.trim();

                if(entry.length()>2)
                    entry = entry.toUpperCase();

                if((!entry.isEmpty() && entry.length() <= 2) || entry.equals("HELP")
                        ||entry.equals("RESET") || entry.equals("ABOUT") || entry.equals("QUIT")){

                    isValidUserInput = true;
                    char mainLetter = '0';
                    char replacementLetter = '0';

                    if(entry.length()<=2){

                        if((entry.length()==2 && !String.valueOf(entry.charAt(0)).toLowerCase()
                                .equals(String.valueOf(entry.charAt(1)).toLowerCase())
                                && Character.isLetter(entry.charAt(0))
                                && Character.isLetter(entry.charAt(1)))
                                ||(entry.length()==1 && Character.isLetter(entry.charAt(0)))){

                            if(entry.length()==2){
                                mainLetter = entry.charAt(0);
                                replacementLetter = entry.charAt(1);
                            }

                            if(entry.length()==1)
                                mainLetter = entry.charAt(0);

                            for(int index = 0; index<helpCharacter.length; ++index){
                                if((entry.length()==2)
                                        && (helpCharacter[index] == Character.toUpperCase(replacementLetter))
                                        || ((entry.length() == 1)
                                        &&(helpCharacter[index] == Character.toUpperCase(mainLetter)))) {
                                    isHelpCharacter = true;
                                    break;
                                }
                            }

                            boolean lowerCaseExists = false;
                            boolean upperCaseExists = false;

                            for(int i=0; i<decryptedText.length();++i){
                                if(entry.length()==2){

                                    if(String.valueOf(mainLetter).toLowerCase()
                                            .equals(String.valueOf(encryptedText.charAt(i)))
                                            && !isHelpCharacter){

                                        lowerCaseExists = true;

                                        if(Character.isLowerCase(decryptedText.charAt(i))
                                                && Character.toLowerCase(mainLetter) == decryptedText.charAt(i)){

                                            for(int j=0; j < decryptedText.length(); ++j){

                                                if(decryptedText.charAt(j)
                                                        == Character.toUpperCase(replacementLetter)){

                                                    decryptedText = replaceText(decryptedText,
                                                            String.valueOf(decryptedText.charAt(j)),
                                                            String.valueOf(encryptedText.charAt(j)));
                                                    break;
                                                }
                                            }

                                            decryptedText = replaceText(decryptedText,
                                                    String.valueOf(mainLetter).toLowerCase(),
                                                    String.valueOf(replacementLetter).toUpperCase());

                                            printDecryptedText(decryptedText);
                                            break;
                                        }

                                        if(Character.isUpperCase(decryptedText.charAt(i))
                                                && Character.toUpperCase(replacementLetter)
                                                != decryptedText.charAt(i)){

                                            upperCaseExists=true;

                                            for(int index = 0; index < helpCharacter.length; ++index){

                                                if(helpCharacter[index] == decryptedText.charAt(i)){
                                                    isHelpCharacter = true;
                                                    break;
                                                }
                                            }

                                            if(!isHelpCharacter){

                                                for(int j = 0; j < decryptedText.length(); ++j) {

                                                    if(decryptedText.charAt(j)
                                                            == Character.toUpperCase(replacementLetter)){
                                                        upperCaseExists = true;
                                                        decryptedText = replaceText(decryptedText,
                                                                String.valueOf(decryptedText.charAt(j)),
                                                                String.valueOf(encryptedText.charAt(j)));

                                                        decryptedText = replaceText(decryptedText,
                                                                String.valueOf(decryptedText.charAt(i)),
                                                                String.valueOf(replacementLetter)
                                                                        .toUpperCase());

                                                        printDecryptedText(decryptedText);

                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                else {
                                    if(String.valueOf(mainLetter).toUpperCase()
                                            .equals(String.valueOf(decryptedText.charAt(i)))
                                            &&Character.isUpperCase(decryptedText.charAt(i))&&!isHelpCharacter){

                                        upperCaseExists = true;
                                        decryptedText = replaceText(decryptedText,
                                                String.valueOf(mainLetter).toUpperCase(),
                                                String.valueOf(encryptedText.charAt(i)));

                                        printDecryptedText(decryptedText);
                                        break;
                                    }
                                }
                            }

                            if((!lowerCaseExists &&entry.length()==2)
                                    || (!upperCaseExists &&entry.length()==1)){
                                if(entry.length()==1){
                                    System.out.println("\'"+String.valueOf(mainLetter).toUpperCase()+"\'"
                                            + " does not exist in "+decryptedText +
                                            " therefore, it cannot be reversed.");
                                }else{
                                    System.out.println("\'"+mainLetter+"\'" + " does not exist in "
                                            +decryptedText + " therefore, it cannot be replaced.");
                                }
                            }

                            if(entry.length()==2&&isHelpCharacter){
                                System.out.println("All occurrences of "+String.valueOf(replacementLetter).toUpperCase()
                                        + " are decrypted");
                            }
                            if(entry.length()==1&&isHelpCharacter) {
                                System.out.println("All occurrences of " + String.valueOf(mainLetter).toUpperCase()
                                        + " are decrypted");
                            }
                        }
                    }

                    else if(entry.equals("HELP")){
                        ++helpCounter;
                        decryptedText = gameHelp(helpCounter, helpCharacter, encryptedText, decryptedText, rawString);
                    }
                    else if(entry.equals("RESET")) {
                        decryptedText = resetParagraph(encryptedText, helpCharacter);
                        helpCounter=0;
                        System.out.println("Paragraph successfully reset");
                        System.out.println("encrypted text: "+decryptedText);
                    }

                    else if(entry.equals("ABOUT"))
                        aboutGame();
                    else {
                        System.out.println("***GAME OVER!!! You Quit***");
                        break;
                    }


                    boolean isValidAnswer = false;

                    do{
                        System.out.println("\n\n");
                        System.out.println("Do you want to continue?");
                        System.out.println("y - yes");
                        System.out.println("n - no");
                        Scanner userInput =new Scanner(System.in);
                        try {
                            answer = userInput.nextLine().charAt(0);

                            if (answer == 'y' || answer == 'Y'|| answer == 'N'|| answer == 'n'){
                                isValidAnswer = true;
                                isValidUserInput = true;
                            }else
                                throw new InputMismatchException();

                        }
                        catch(InputMismatchException inputMismatchEx){
                            System.err.println("Please enter y - yes or n - no");
                        }

                    }while (!isValidAnswer);

                }else
                    throw new InputMismatchException();

            }
            catch (InputMismatchException inputMismatchEx){
                System.err.println("Please enter 2 alphabets");
            }
            if(rawString.toUpperCase().equals(decryptedText))
            {
                System.out.println("**DECRYPTION COMPLETE***");
                System.out.println("Encrypted Text: "+encryptedText);
                System.out.println("Decrypted Text: "+decryptedText);
                System.out.println();
            }
            if(answer == 'n'|| answer == 'N' || rawString.toUpperCase().equals(decryptedText))
                break;

        }while(isValidUserInput);
    }

    private static void aboutGame() {
        System.out.println();
        System.out.println();
        System.out.println("WELCOME TO TEXT DECRYPTION CIPHER");
        System.out.println("_________________________________");
        System.out.println("This program is designed for players to decrypt a string of encrypted text in random ");
        System.out.println("lower case alphabets into the original text in upper case");
        System.out.println();
        System.out.println("The game has two modes for which one is to be selected for decryption");
        System.out.println("1. NORMAL MODE");
        System.out.println("2. TEST MODE");
        System.out.println("In the \'NORMAL MODE\', an encrypted paragraph is randomly selected ");
        System.out.println("from 8 paragraphs automatically.");
        System.out.println("In the \'TEST MODE\', a truncated encrypted paragraph limited to the first ");
        System.out.println("50 characters is selected from 8 encrypted texts displayed.");
        System.out.println();
        System.out.println("For example,");
        System.out.println("if the encrypted text to be decrypted is \'ipuum\'");
        System.out.println("and user enters two letters such as \'ih\',");
        System.out.println("all occurrences of \'i\' in \'ipuum\' will be replaced with \'H\' on decryption");
        System.out.println("generating a decrypted text Hpuum corresponding to i>H");
        System.out.println("Similarly, if user enters \'uL\', all occurrences of \'u\' will be replaced with ");
        System.out.println("\'L\' updating the decrypted text with \'i>H\' and \'u>L\' pairs as \'HpLLm\'");
        System.out.println("The game would display both encrypted text and player's decrypted version of ");
        System.out.println("the text as long as the player continues playing.");
        System.out.println();
        System.out.println("if the player wants to reverse all occurrences of decrypted character \'H\' to ");
        System.out.println("the encrypted lower case in \'HpLLm\', the player will simply type \'h\' and ");
        System.out.println("player's decrypted version of the text becomes \'ipLLm\'");
        System.out.println();
        System.out.println("if the player wants to decrypt all occurrences of lower case \'p\' to decrypted ");
        System.out.println("upper case \'L\' in \'ipLLm\', the player will enter two letters \'pL\' and ");
        System.out.println("player's decrypted version of the text becomes \'iLuum\', this means \'L\' is ");
        System.out.println("reversed to \'u\' from \'u>L\' pair and \'p>L\' becomes new pair");
        System.out.println();
        System.out.println("if the player enters a pair \'ur\', the player's decrypted version of the ");
        System.out.println("text becomes \'iLRRm\'.");
        System.out.println("If the player wants to replace \'L\' with \'R\', the player will type in \'pr\'");
        System.out.println("and the player's decrypted version of the text becomes \'iRuum\'.");
        System.out.println(" where \'p\' is the encrypted pair of \'L\' and \'R\' is the upper case character ");
        System.out.println("for which all occurrences of \'p\' will be decrypted to \'R\' causing \'R\' to be ");
        System.out.println("automatically encrypted back to \'u\' which was previous \'u>R\' pair");
        System.out.println();
        System.out.println("When player enters \'HELP\', all occurrences of a random encrypted character is ");
        System.out.println("automatically decrypted permanently, if the player does not reset the game");
        System.out.println();
        System.out.println("When player enters \'RESET\', the decrypted text is completely reversed ");
        System.out.println("to encrypted version and help counter is reset to 0, to decrypt the text again ");
        System.out.println();
        System.out.println("When player enters \'QUIT\', THE GAME IS OVER!!!");
        System.out.println();
        System.out.println("Enjoy!!!\n");
    }

    private static void displayGameInstruction(String encryptedText, String decryptedText) {
        
        System.out.println("Please enter two letters as follow \"ij\" for example, where:");
        System.out.println("\'i\', the first letter represents the character to be replaced in the" +
                " encrypted text");
        System.out.println("\'j\', the second letter represents the replacing character for " +
                "all occurrences of \'i\', the first letter of the encrypted text entered");

        System.out.println();

        System.out.println("Enter any capital letter in decrypted string to reverse choice to previous " +
                "lower case alphabet in encrypted string");
        System.out.println();
        System.out.println("type \"HELP\" to decrypt encrypted character randomly");
        System.out.println();
        System.out.println("type \"RESET\" to reset decrypted string to encrypted string to try again");
        System.out.println();
        System.out.println("type \"ABOUT\" for information on game play");
        System.out.println();
        System.out.println("type \"QUIT\" to quit game");
        System.out.println("\n");
        System.out.println("GAME HINT: help can be used only 5 times");
        System.out.println();
    }

    private static void printDecryptedText(String decryptedText){
        System.out.println("current decrypted text "+ decryptedText);
    }

    private static String resetParagraph(String encryptedText, char[] helpCharacter) {
        helpCharacter=new char[5];
        return encryptedText;
    }

    private static String gameHelp(int helpCounter, char[] helpCharacter,
                                 String encryptedText, String decryptedText, String rawString) {
        Random rnd = new Random();
        if(helpCounter<=5)
        {
            int randomCharacterIndex;
            boolean isNotLetter;
            boolean isHelpCharacter;
            do{
                isNotLetter=false;
                isHelpCharacter=false;

                randomCharacterIndex = rnd.nextInt(encryptedText.length());
                if(Character.isLetter(rawString.charAt(randomCharacterIndex))){
                    for(int i=0; i<helpCharacter.length; ++i){
                        if(helpCharacter[i] == Character.toUpperCase(rawString.charAt(randomCharacterIndex))) {
                            isHelpCharacter=true;
                            break;
                        }
                    }
                }
                else
                    isNotLetter = true;

            }while(isHelpCharacter||isNotLetter);

            if(Character.isUpperCase(decryptedText.charAt(randomCharacterIndex))){
                decryptedText = replaceText(decryptedText,
                        String.valueOf(decryptedText.charAt(randomCharacterIndex)),
                        String.valueOf(encryptedText.charAt(randomCharacterIndex)));
            }

            for(int charIndex = 0; charIndex<decryptedText.length(); ++charIndex){

                if(charIndex == randomCharacterIndex)
                    continue;

                if(String.valueOf(rawString.charAt(randomCharacterIndex))
                        .toUpperCase()
                        .equals(String.valueOf(decryptedText.charAt(charIndex)))
                        && Character.isUpperCase(decryptedText.charAt(charIndex))){

                    decryptedText = replaceText(decryptedText,
                            String.valueOf(decryptedText.charAt(charIndex)),
                            String.valueOf(encryptedText.charAt(charIndex)));
                    break;
                }
            }
            helpCharacter[helpCounter-1]
                    = Character.toUpperCase(rawString.charAt(randomCharacterIndex));

            decryptedText = replaceText(decryptedText,
                    String.valueOf(decryptedText.charAt(randomCharacterIndex)),
                    String.valueOf(helpCharacter[helpCounter-1]));
            
            printDecryptedText(decryptedText);
        }else
            System.out.println("SORRY!!! You are out of hints");

        return decryptedText;
    }

    private static String replaceText(String decryptedText, String mainLetter, String replacementLetter){
        return decryptedText.replaceAll(mainLetter,replacementLetter);
    }
}