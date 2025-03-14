import java.awt.*;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class OnlineExamination {
    private static HashMap<String, String> users = new HashMap<>(); 
    private static String currentUser;
    private static JFrame frame;
    private static int timeLeft = 30; 
    private static Timer quizTimer;
    
    public static void main(String[] args) {
        users.put("admin", "1234"); 
        showLoginScreen();
    }

    // Login Screen
    private static void showLoginScreen() {
        frame = new JFrame("Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 2));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (users.containsKey(username) && users.get(username).equals(password)) {
                currentUser = username;
                JOptionPane.showMessageDialog(frame, "Login Successful!");
                frame.dispose();
                showMenuScreen();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Username or Password!");
            }
        });

        frame.add(userLabel);
        frame.add(userField);
        frame.add(passLabel);
        frame.add(passField);
        frame.add(new JLabel()); 
        frame.add(loginButton);

        frame.setVisible(true);
    }

    // Main Menu
    private static void showMenuScreen() {
        frame = new JFrame("Main Menu");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 1));

        JButton quizButton = new JButton("Start Quiz");
        JButton profileButton = new JButton("Update Profile");
        JButton logoutButton = new JButton("Logout");

        quizButton.addActionListener(e -> {
            frame.dispose();
            startQuiz();
        });

        profileButton.addActionListener(e -> updateProfile());

        logoutButton.addActionListener(e -> {
            currentUser = null;
            JOptionPane.showMessageDialog(frame, "Logged out successfully!");
            frame.dispose();
            showLoginScreen();
        });

        frame.add(quizButton);
        frame.add(profileButton);
        frame.add(logoutButton);
        frame.setVisible(true);
    }


    private static void updateProfile() {
        String newPassword = JOptionPane.showInputDialog(frame, "Enter new password:");
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            users.put(currentUser, newPassword);
            JOptionPane.showMessageDialog(frame, "Password Updated Successfully!");
        }
    }


    private static void startQuiz() {
        frame = new JFrame("MCQ Quiz");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 1));

        JLabel questionLabel = new JLabel("Q1: What is the capital of France?");
        JRadioButton option1 = new JRadioButton("Berlin");
        JRadioButton option2 = new JRadioButton("Madrid");
        JRadioButton option3 = new JRadioButton("Paris");
        JRadioButton option4 = new JRadioButton("Rome");
        JButton submitButton = new JButton("Submit");

        ButtonGroup group = new ButtonGroup();
        group.add(option1);
        group.add(option2);
        group.add(option3);
        group.add(option4);

        JLabel timerLabel = new JLabel("Time Left: " + timeLeft + "s", SwingConstants.CENTER);
        

        quizTimer = new Timer();
        quizTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                timeLeft--;
                timerLabel.setText("Time Left: " + timeLeft + "s");

                if (timeLeft <= 0) {
                    quizTimer.cancel();
                    JOptionPane.showMessageDialog(frame, "Time is up! Auto-submitting...");
                    checkAnswer(option3); 
                }
            }
        }, 1000, 1000);

        submitButton.addActionListener(e -> {
            quizTimer.cancel();
            checkAnswer(option3);
        });

        frame.add(questionLabel);
        frame.add(option1);
        frame.add(option2);
        frame.add(option3);
        frame.add(option4);
        frame.add(timerLabel);
        frame.add(submitButton);

        frame.setVisible(true);
    }


    private static void checkAnswer(JRadioButton correctOption) {
        if (correctOption.isSelected()) {
            JOptionPane.showMessageDialog(frame, "Correct Answer! 🎉");
        } else {
            JOptionPane.showMessageDialog(frame, "Wrong Answer. The correct answer is Paris.");
        }
        frame.dispose();
        showMenuScreen();
    }
}
