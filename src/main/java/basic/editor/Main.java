package basic.editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    
    // ANSI escape codes for colors
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String ORANGE = "\u001B[38;2;255;165;0m";

    // File array list where everything is appended
    public static ArrayList<String> fileArrayList;

    // Object and column to store where the cursor is
    public static int object = 0;
    public static int column = 0;

    // Cursor key for easy customization
    private static char cursorKey = '│';

    // Save arguements for calling other classes
    private static String[] arguments;

    // Save file path to use freely
    private static String globalFilePath;

    // Cursor status for blinking
    //private static boolean cursorStatus = true;

    public static void main(String[] args) throws IOException {
        run();

        arguments = args;

        initializeKeyListener();
    }
    
    private static void run() throws IOException { // Main code
        Scanner scanner = new Scanner(System.in);
        System.out.println("ENTER FILE PATH");
        String filePath = scanner.nextLine();

        if (filePath != null) {
            globalFilePath = filePath;

            // Check if file exists
            File file = new File(filePath);

            if (file.exists() && !file.isDirectory()) {
                System.out.println("FILE EXISTS");
                readFile(file);
            } else {
                System.out.println("FILE DOESN'T EXIST");

                // Ask whether or not to create a new file
                System.out.println("WOULD YOU LIKE TO CREATE THIS FILE?");
                String boolCheck = scanner.nextLine();

                if (boolCheck.equalsIgnoreCase("yes")) {
                    file.createNewFile();
                    System.out.println("FILE CREATED AT " + filePath);

                    readFile(file);
                } else {
                    System.out.println("EXITING PROGRAM");
                    System.exit(0);
                }
            }
        }

        scanner.close();
    }

    private static void readFile(File file) { // Reads the file
        try {
            System.out.println("-".repeat(60));
            Scanner scanner = new Scanner(file);
    
            fileArrayList = new ArrayList<>(); // Initialize the ArrayList
            while (scanner.hasNextLine()) { 
                String line = scanner.nextLine();
                fileArrayList.add(line);
            }
    
            System.out.println("File Contents:");
            for (String line : fileArrayList) { 
                System.out.println(line);
            }
            
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    

    public static void controlKeys(int index) {
        switch (index) {
            case 1: // Up
                if (object > 0) object--;
                break;
            case 2: // Down
                if (object < fileArrayList.size() - 1) {
                    object++; // Move to the next line
                    column = Math.min(column, fileArrayList.get(object).length());
                }
                break;
            case 3: // Left
                if (column > 0) {
                    column--;
                } else if (object > 0) { // Move to the end of the previous line
                    object--;
                    column = fileArrayList.get(object).length();
                }
                break;
            case 4: // Right
                if (column < fileArrayList.get(object).length() - 1) {
                    column++;
                } else if (object < fileArrayList.size() - 1) { // Move to the start of the next line
                    object++;
                    column = 0;
                }
                break;
            case 5: // Enter
                if (object < fileArrayList.size() - 1) {
                    fileArrayList.add(object + 1, "");
                    object++;
                    column = 0; // Move to the beginning of the new line
                } else {
                    fileArrayList.add("");
                    object++; 
                    column = 0; // Move the the beginning of the new line
                }
                break;
        }
        updateCursor();
    }
    
    
    public static void updateCursor() { // Update cursor
        removeCursor();
    
        String line = fileArrayList.get(object);
        column = Math.min(column, line.length());
    
        String modifiedLine = line.substring(0, column) + cursorKey + line.substring(column);
        fileArrayList.set(object, modifiedLine);
        
        displayArrayList();
    }
    
    private static void removeCursor() { // Remove cursor
        for (int i = 0; i < fileArrayList.size(); i++) {
            String line = fileArrayList.get(i);
            int cursorIndex = line.indexOf(cursorKey);
    
            if (cursorIndex != -1) {
                String modifiedLine = line.substring(0, cursorIndex) + line.substring(cursorIndex + 1); 
                fileArrayList.set(i, modifiedLine);
            }
        }
    }
    
    private static void displayArrayList() { // Display ArrayList properly
        clearConsole();
        System.out.println("[F5 = Close] [F6 = Save]");
        System.out.println("-".repeat(60));
        for (String line : fileArrayList) {
            String modifiedLine  = line;
            System.out.println(syntaxHighlighting(modifiedLine));
        }
    }

    public static void editFile(String key, boolean isCaps) { // Edits file
        removeCursor();
    
        String line = fileArrayList.get(object);
        column = Math.min(column, line.length());
    
        if (key.length() == 1) { // Normal character
            if (isCaps) key = key.toUpperCase();
            else key = key.toLowerCase();

            String modifiedLine = line.substring(0, column) + key + line.substring(column);
            fileArrayList.set(object, modifiedLine);
            column++; 
        } else if (key.equals("Space")) { // Spacebar
            String modifiedLine = line.substring(0, column) + " " + line.substring(column);
            fileArrayList.set(object, modifiedLine);
            column++; 
        } else if (key.equals("Backspace") || key.equals("Delete")) { // Delete
            if (column > 0) {
                String modifiedLine = line.substring(0, column - 1) + line.substring(column);
                fileArrayList.set(object, modifiedLine);
                column--; 
            } else if (object > 0) { // Move to end of previous line
                if (fileArrayList.get(object).length() == 0) {
                    fileArrayList.remove(object);
                }
                object--;
                column = fileArrayList.get(object).length();
            }
        }

        updateCursor(); 
    }

    public static void saveFile() throws IOException { // Saves the file
        FileWriter fileWriter = new FileWriter(globalFilePath);
        for (int i = 0; i < fileArrayList.size(); i++) {
            String line = fileArrayList.get(i);
            if (i == object) {
                line = line.substring(0, column) + line.substring(column + 1);
            }
            fileWriter.write(line + System.lineSeparator());
        }
        fileWriter.close();
    }

    public static void exitProgram() { // Exit program
        System.exit(0);
    }
    
    public static String syntaxHighlighting(String line) { // Syntax highlighting
        Map<String, String> keywordColors = new HashMap<>();

        keywordColors.put("System", RED);
        keywordColors.put("Runtime", RED);
        keywordColors.put("Math", RED);
        keywordColors.put("String", RED);
        keywordColors.put("Integer", RED);
        keywordColors.put("Double", RED);
        keywordColors.put("Object", RED);

        keywordColors.put("private", PURPLE);
        keywordColors.put("public", PURPLE);
        keywordColors.put("protected", PURPLE);
        keywordColors.put("static", PURPLE);
        keywordColors.put("final", PURPLE);
        keywordColors.put("abstract", PURPLE);
        keywordColors.put("synchronized", PURPLE);
        keywordColors.put("volatile", PURPLE);
        keywordColors.put("transient", PURPLE);

        keywordColors.put("char", YELLOW);
        keywordColors.put("boolean", YELLOW);
        keywordColors.put("int", YELLOW);
        keywordColors.put("short", YELLOW);
        keywordColors.put("byte", YELLOW);
        keywordColors.put("long", YELLOW);
        keywordColors.put("float", YELLOW);
        keywordColors.put("double", YELLOW);

        keywordColors.put("if", GREEN);
        keywordColors.put("else", GREEN);
        keywordColors.put("for", GREEN);
        keywordColors.put("while", GREEN);
        keywordColors.put("do", GREEN);
        keywordColors.put("switch", GREEN);
        keywordColors.put("case", GREEN);
        keywordColors.put("default", GREEN);

        keywordColors.put("null", BLUE);
        keywordColors.put("true", BLUE);
        keywordColors.put("false", BLUE);

        keywordColors.put("try", ORANGE);
        keywordColors.put("catch", ORANGE);
        keywordColors.put("throw", ORANGE);
        keywordColors.put("throws", ORANGE);
        keywordColors.put("return", ORANGE);
        keywordColors.put("new", ORANGE);

        keywordColors.put("import", CYAN);
        keywordColors.put("package", CYAN);

        for (Map.Entry<String, String> entry : keywordColors.entrySet()) {
            String keyword = entry.getKey();
            String color = entry.getValue();
            line = line.replaceAll("\\b" + keyword + "\\b", color + keyword + RESET);
        }

        if (line.contains(String.valueOf(cursorKey))) {
            line = line.replace(String.valueOf(cursorKey), BLUE + cursorKey + RESET);
        }

        return line;
    }

    public static void clearConsole() { // Clears console
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }  


    private static void initializeKeyListener() { // Initialize 'KeyListener.java'
        KeyListener.main(arguments);
    }
}   