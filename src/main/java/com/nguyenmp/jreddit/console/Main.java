package com.nguyenmp.jreddit.console;

import com.googlecode.lanterna.gui.*;
import com.googlecode.lanterna.gui.component.*;
import com.googlecode.lanterna.gui.layout.LinearLayout;
import com.googlecode.lanterna.screen.DefaultScreen;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.ansi.UnixTerminal;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Terminal terminal = new UnixTerminal();
        terminal.setCursorVisible(false);
        Screen screen = new DefaultScreen(terminal);
        screen.startScreen();
        GUIScreen guiScreen = new GUIScreen(screen, "Jreddit Console");


//        String login = TextInputDialog.showTextInputBox(guiScreen, "Login", null, null, 20);
//        String password = TextInputDialog.showPasswordInputBox(guiScreen, "Password", null, null, 20);
//        WaitingDialog waiting = new WaitingDialog("Logging In", "Please wait..." + login + password);
        guiScreen.showWindow(new LoginWindow(), GUIScreen.Position.CENTER);
    }

    private static final class LoginWindow extends Window {
        private static final String TITLE = "Sign in to Reddit";
        private final TextBox loginBox;
        private final TextBox passwordBox;
        private String result;

        private LoginWindow() {
            super(TITLE);
            Label usernameLabel = new Label("Username");
            loginBox = new NormalTextBoxFactory().createTextBox("Username", TITLE.length());
            addComponent(usernameLabel);
            addComponent(loginBox);

            // Vertical spacer
            addComponent(new EmptySpace(1, 1));

            Label passwordLabel = new Label("Password");
            passwordBox = new PasswordTextBoxFactory().createTextBox("Password", TITLE.length());
            addComponent(passwordLabel);
            addComponent(passwordBox);
            addComponent(new EmptySpace(1, 1));

            Panel okCancelPanel = new Panel(new Border.Invisible(), Panel.Orientation.HORISONTAL);
            Button okButton = new Button("Login", new Action() {
                @Override
                public void doAction() {
                    result = loginBox.getText();
                    close();
                }
            });
            okButton.setAlignment(Component.Alignment.RIGHT_CENTER);
            okCancelPanel.addComponent(okButton, LinearLayout.GROWS_HORIZONTALLY);
            Button cancelButton = new Button("Cancel", new Action() {
                @Override
                public void doAction() {
                    close();
                }
            });
            okCancelPanel.addComponent(cancelButton);
            addComponent(okCancelPanel, LinearLayout.GROWS_HORIZONTALLY);
        }

        private static interface TextBoxFactory
        {
            public TextBox createTextBox(String initialContent, int width);
        }

        private static class NormalTextBoxFactory implements TextBoxFactory {
            @Override
            public TextBox createTextBox(String initialContent, int width)
            {
                return new TextBox(initialContent, width);
            }
        }

        private static class PasswordTextBoxFactory implements TextBoxFactory {
            @Override
            public TextBox createTextBox(String initialContent, int width)
            {
                return new PasswordBox(initialContent, width);
            }
        }
    }

}