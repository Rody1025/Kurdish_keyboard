# Java Keystroke Listener Application

The Java Keystroke Listener Application is a simple Java program that listens for specific key combinations and performs actions based on the keystrokes. It is built using the JNativeHook library, which allows the application to capture global keyboard events.

## Features

- Captures key combinations involving the Control key and the numeric keys 1, 2, 3, 4, and 5.
- Supports both uppercase and lowercase special characters based on the state of the Caps Lock key.
- Displays the keystrokes and the corresponding printed letters in a user interface using a JFrame and JLabel components.

## Requirements

- Java Development Kit (JDK) 8 or higher.
- JNativeHook library.

## Usage

1. Compile the Java source files or create an executable JAR file.
2. Run the application, and the JFrame window will open.
3. Press the desired key combinations involving the Control key and the numeric keys 1, 2, 3, 4, or 5.
4. The application will capture the keystrokes and display them in the user interface.
5. The corresponding special character based on the Caps Lock state will be typed or simulated.

## Configuration

- No additional configuration is required. The application listens for the specific key combinations automatically.

## License

This project is licensed under the [MIT License](LICENSE).

## Acknowledgments

- The Java Keystroke Listener Application utilizes the JNativeHook library by [Kraust](https://github.com/kraust/JNativeHook).

