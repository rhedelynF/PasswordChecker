# Password Checker 🔐

A Java desktop application that checks password strength and generates random passwords. Built with Java Swing, so it runs as a desktop window rather than only in the terminal.

## Features

- Password strength score from 0 to 5
- Strength labels: Very Weak, Weak, Fair, Strong, Very Strong
- Requirements checklist for length, uppercase, lowercase, number, and special character
- Password improvement suggestions
- Show/hide password option
- Random password generator (8–32 characters in the GUI)
- Copy generated password to clipboard
- Simple dark interface
- Basic tests for the password scoring logic

## Requirements

- Java JDK 8 or newer
- No external libraries required

## Run the application

Open a terminal in the project folder and run:

```bash
javac -d out src/PasswordChecker.java src/PasswordGenerator.java src/Main.java
java -cp out Main
```

On Windows PowerShell, the same commands work when run from the project folder.

## Run the tests

```bash
javac -d out src/PasswordChecker.java tests/PasswordCheckerTest.java
java -cp out PasswordCheckerTest
```

If the test command cannot find the class, compile the test into the same output folder:

```bash
javac -d out src/PasswordChecker.java tests/PasswordCheckerTest.java
java -cp out PasswordCheckerTest
```

## Project structure

```text
Password-Checker/
├── src/
│   ├── Main.java
│   ├── PasswordChecker.java
│   └── PasswordGenerator.java
├── tests/
│   └── PasswordCheckerTest.java
├── README.md
├── .gitignore
└── LICENSE
```

## Privacy note

The app checks the entered password locally and does not send it to a server or save it to a file. Avoid entering your real, currently used password into demos or screenshots.

## Limitations

This is an educational password-strength checker, not a guarantee that a password is secure. It checks basic character rules and does not detect breached passwords or estimate real-world cracking time.

## License

Choose a license before publishing or sharing the project. If you select MIT, include the full MIT license text and your preferred copyright holder.
