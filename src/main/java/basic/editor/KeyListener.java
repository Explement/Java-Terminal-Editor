package basic.editor;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class KeyListener implements NativeKeyListener {

    private boolean capsLockIndex = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
    private boolean shiftIndex = false;
    
    public static void main(String[] args) { // Register native hook
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new KeyListener());
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) { // Checks for key presses
        String keyCode = NativeKeyEvent.getKeyText(e.getKeyCode());
        System.out.println(keyCode);
        switch (keyCode) { // Checks for keycodes that do special things (eg. move around the text body)
            case "Up":
                Main.controlKeys(1);
                break;
            case "Down":
                Main.controlKeys(2);
                break;
            case "Left":
                Main.controlKeys(3);
                break;
            case "Right":
                Main.controlKeys(4);
                break;
            case "Home":
                Main.object = 0;
                Main.column = 0;
                Main.updateCursor();
                break;
            case "End":
                Main.object = Main.fileArrayList.size() - 1;
                Main.column = Main.fileArrayList.get(Main.object).length();
                Main.updateCursor();
                break;
            case "Caps Lock":
                capsLockIndex = !capsLockIndex;
                break;
            case "F5":
                Main.exitProgram();
                break;
            case "F6":
                try {
                    Main.saveFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            case "Shift":
                shiftIndex = true;
                break;
            case "Enter":
                Main.controlKeys(5);
                break;
            case "Tab":
                Main.editFile("\t", capsLockIndex);
                break;
            default:
                switch (keyCode) { // Add shift support for every single key
                    case "0":
                        if (shiftIndex) keyCode = ")";
                        break;
                    case "1":
                        if (shiftIndex) keyCode = "!";
                        break;
                    case "2":
                        if (shiftIndex) keyCode = "@";
                        break;
                    case "3":
                        if (shiftIndex) keyCode = "#";
                        break;
                    case "4":
                        if (shiftIndex) keyCode = "$";
                        break;
                    case "5":
                        if (shiftIndex) keyCode = "%";
                        break;
                    case "6":
                        if (shiftIndex) keyCode = "^";
                        break;
                    case "7":
                        if (shiftIndex) keyCode = "&";
                        break;
                    case "8":
                        if (shiftIndex) keyCode = "*";
                        break;
                    case "9":
                        if (shiftIndex) keyCode = "(";
                        break;
                    case "Minus":
                        if (shiftIndex) keyCode = "_";
                        else keyCode = "-";
                        break;
                    case "Equals":
                        if (shiftIndex) keyCode = "+";
                        else keyCode = "=";
                        break;
                    case "Open Bracket":
                        if (shiftIndex) keyCode = "{";
                        else keyCode = "[";
                        break;
                    case "Close Bracket":
                        if (shiftIndex) keyCode = "}";
                        else keyCode = "]";
                        break;
                    case "Back Slash":
                        if (shiftIndex) keyCode = "|";
                        else keyCode = "\\";
                        break;
                    case "Quote":
                        if (shiftIndex) keyCode = "\"";
                        else keyCode = "\'";
                        break;
                    case "Semicolon":
                        if (shiftIndex) keyCode = ":";
                        else keyCode = ";";
                        break;
                    case "Period":
                        if (shiftIndex) keyCode = ">";
                        else keyCode = ".";
                        break;
                    case "Comma":
                        if (shiftIndex) keyCode = "<";
                        else keyCode = ",";
                        break;
                    case "Slash":
                        if (shiftIndex) keyCode = "?";
                        else keyCode = "/";
                        break;
                }
                Main.editFile(keyCode, capsLockIndex);
                break;
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) { // Checks for key releases
        String keyCode = NativeKeyEvent.getKeyText(nke.getKeyCode());
        switch(keyCode) {
            case "Shift":
                shiftIndex = false;
        }
    }

}