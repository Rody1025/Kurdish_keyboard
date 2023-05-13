package org.example;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * A Java class that implements a global key listener using JNativeHook library.
 * The class listens for specific key combinations (Ctrl+1, Ctrl+2, Ctrl+3, Ctrl+4, and Ctrl+5)
 * and types corresponding special characters based on the Caps Lock state.
 */
public class Main implements NativeKeyListener {

    private boolean ctrlPressed = false;
    private boolean keyProcessed = false;
    private int lastKeyCode;
    private static boolean isUpperCase = false; // Flag to track the desired case

    /**
     * Main method to start the global key listener.
     * Registers the native hook and adds the listener to the global screen.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            isUpperCase = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
            JFrame frame = new JFrame("Kurdish letters");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Set the preferred size, layout, and other properties of the frame as needed.

            Main listener = new Main();
            GlobalScreen.addNativeKeyListener(listener);

            frame.pack();
            frame.setVisible(true);
            startListening();
        });
    }

    private static void startListening() {
        try {
            GlobalScreen.registerNativeHook();

            Main listener = new Main();
            GlobalScreen.addNativeKeyListener(listener);

        } catch (NativeHookException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Invoked when a key is pressed.
     * Checks for the Control key and the specific keys 1, 2, 3, 4, 5 to be pressed.
     * Sets the necessary flags for key processing.
     */
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_SHIFT) {
            isUpperCase = !isUpperCase; // Toggle the case flag
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            ctrlPressed = true;
            // We're initializing the keys to the Ctrl + 1 to Ctrl + 5
        } else if (ctrlPressed && e.getKeyCode() >= NativeKeyEvent.VC_1 && e.getKeyCode() <= NativeKeyEvent.VC_5) {
            keyProcessed = true;
            lastKeyCode = e.getKeyCode(); // Getting the last pressed key
        }
    }

    /**
     * Invoked when a key is released.
     * Checks for the release of the Control key and the specific key that was processed.
     * Triggers the typing of the corresponding special character based on the Caps Lock state.
     */
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            ctrlPressed = false;
        } else if (keyProcessed && e.getKeyCode() == lastKeyCode) {
            keyProcessed = false;
            typeCharacter(lastKeyCode);
            lastKeyCode = 0;
        }
    }

    /**
     * Invoked when a key is typed.
     * Does nothing in this implementation.
     */
    public void nativeKeyTyped(NativeKeyEvent e) {
        // Do nothing
    }

    /**
     * Types the special character corresponding to the given key code.
     * Determines the character based on the Caps Lock state.
     *
     * @param keyCode the key code representing the specific key pressed
     */
    public void typeCharacter(int keyCode) {
        String stringToType = switch (keyCode) {
            case NativeKeyEvent.VC_1 -> isUpperCase  ? "Ç" : "ç";
            case NativeKeyEvent.VC_2 -> isUpperCase  ? "Ê" : "ê";
            case NativeKeyEvent.VC_3 -> isUpperCase  ? "Î" : "î";
            case NativeKeyEvent.VC_4 -> isUpperCase  ? "Ş" : "ş";
            case NativeKeyEvent.VC_5 -> isUpperCase  ? "Û" : "û";
            default -> null;
        };

        if (stringToType != null) {
            typeString(stringToType);
        }
    }

    /**
     * Types the given string by simulating key presses for Ctrl+V (paste operation).
     *
     * @param s the string to be typed
     */
    public void typeString(String s) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(s);
        clipboard.setContents(stringSelection, stringSelection);

        try {
            Robot robot = new Robot();

            // Ctrl+V to paste
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
