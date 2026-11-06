import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class Main {
    private static final Color BG = new Color(18, 24, 38);
    private static final Color PANEL = new Color(29, 38, 56);
    private static final Color ACCENT = new Color(92, 124, 250);
    private static final Color TEXT = new Color(238, 242, 255);
    private static final Color MUTED = new Color(165, 176, 199);

    private JFrame frame;
    private JPasswordField passwordField;
    private JCheckBox showPassword;
    private JLabel strengthLabel;
    private JProgressBar strengthBar;
    private JTextArea checklist;
    private JTextArea suggestions;
    private JSlider lengthSlider;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().createAndShow());
    }

    private void createAndShow() {
        frame = new JFrame("Password Checker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(760, 760);
        frame.setMinimumSize(new Dimension(650, 650));
        frame.setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(0, 18));
        root.setBackground(BG);
        root.setBorder(new EmptyBorder(24, 28, 24, 28));

        JLabel title = new JLabel("Password Checker");
        title.setForeground(TEXT);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        JLabel subtitle = new JLabel("Check password strength and create a stronger one.");
        subtitle.setForeground(MUTED);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        heading.add(title);
        heading.add(Box.createVerticalStrut(6));
        heading.add(subtitle);
        root.add(heading, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JPanel checkPanel = panel("CHECK YOUR PASSWORD");
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        passwordField.setBackground(new Color(15, 20, 32));
        passwordField.setForeground(TEXT);
        passwordField.setCaretColor(TEXT);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(67, 80, 108)),
            new EmptyBorder(12, 12, 12, 12)));

        showPassword = new JCheckBox("Show password");
        showPassword.setOpaque(false);
        showPassword.setForeground(MUTED);
        showPassword.addActionListener(e -> passwordField.setEchoChar(showPassword.isSelected() ? (char) 0 : '•'));

        JButton checkButton = button("Check Password");
        checkButton.addActionListener(e -> checkCurrentPassword());
        passwordField.addActionListener(e -> checkCurrentPassword());

        strengthLabel = new JLabel("Strength: Not checked");
        strengthLabel.setForeground(TEXT);
        strengthLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        strengthBar = new JProgressBar(0, 5);
        strengthBar.setValue(0);
        strengthBar.setStringPainted(true);
        strengthBar.setString("0 / 5");
        strengthBar.setForeground(ACCENT);
        strengthBar.setBackground(new Color(15, 20, 32));
        strengthBar.setPreferredSize(new Dimension(100, 22));

        checklist = textArea();
        suggestions = textArea();

        checkPanel.add(passwordField);
        checkPanel.add(showPassword);
        checkPanel.add(checkButton);
        checkPanel.add(Box.createVerticalStrut(8));
        checkPanel.add(strengthLabel);
        checkPanel.add(strengthBar);
        checkPanel.add(sectionLabel("Requirements"));
        checkPanel.add(checklist);
        checkPanel.add(sectionLabel("Suggestions"));
        checkPanel.add(suggestions);

        JPanel generatorPanel = panel("PASSWORD GENERATOR");
        JLabel lengthLabel = new JLabel("Password length: 16");
        lengthLabel.setForeground(TEXT);
        lengthSlider = new JSlider(8, 32, 16);
        lengthSlider.setBackground(PANEL);
        lengthSlider.setForeground(TEXT);
        lengthSlider.setMajorTickSpacing(8);
        lengthSlider.setMinorTickSpacing(1);
        lengthSlider.setPaintTicks(true);
        lengthSlider.setPaintLabels(true);
        lengthSlider.addChangeListener(e -> lengthLabel.setText("Password length: " + lengthSlider.getValue()));

        JTextField generatedField = new JTextField();
        generatedField.setEditable(false);
        generatedField.setFont(new Font("Monospaced", Font.PLAIN, 15));
        generatedField.setBackground(new Color(15, 20, 32));
        generatedField.setForeground(TEXT);
        generatedField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(67, 80, 108)),
            new EmptyBorder(10, 10, 10, 10)));

        JButton generateButton = button("Generate Password");
        JButton copyButton = button("Copy");
        generateButton.addActionListener(e -> generatedField.setText(PasswordGenerator.generate(lengthSlider.getValue())));
        copyButton.addActionListener(e -> {
            if (!generatedField.getText().isEmpty()) {
                Toolkit.getDefaultToolkit().getSystemClipboard()
                    .setContents(new StringSelection(generatedField.getText()), null);
                JOptionPane.showMessageDialog(frame, "Generated password copied.");
            }
        });

        JPanel generatorButtons = new JPanel(new GridLayout(1, 2, 10, 0));
        generatorButtons.setOpaque(false);
        generatorButtons.add(generateButton);
        generatorButtons.add(copyButton);

        generatorPanel.add(lengthLabel);
        generatorPanel.add(lengthSlider);
        generatorPanel.add(generatedField);
        generatorPanel.add(generatorButtons);

        content.add(checkPanel);
        content.add(Box.createVerticalStrut(16));
        content.add(generatorPanel);

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        root.add(scroll, BorderLayout.CENTER);

        JLabel footer = new JLabel("Your password is checked locally in this application. Do not reuse passwords.");
        footer.setForeground(MUTED);
        footer.setFont(new Font("SansSerif", Font.PLAIN, 11));
        root.add(footer, BorderLayout.SOUTH);

        frame.setContentPane(root);
        frame.setVisible(true);
    }

    private JPanel panel(String heading) {
        JPanel p = new JPanel();
        p.setBackground(PANEL);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(49, 61, 85)),
            new EmptyBorder(16, 16, 16, 16)));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        JLabel h = new JLabel(heading);
        h.setForeground(new Color(140, 164, 255));
        h.setFont(new Font("SansSerif", Font.BOLD, 12));
        h.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(h);
        p.add(Box.createVerticalStrut(12));
        return p;
    }

    private JLabel sectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        label.setBorder(new EmptyBorder(10, 0, 5, 0));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextArea textArea() {
        JTextArea area = new JTextArea(5, 30);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("SansSerif", Font.PLAIN, 13));
        area.setBackground(PANEL);
        area.setForeground(TEXT);
        area.setBorder(new EmptyBorder(4, 0, 4, 0));
        area.setAlignmentX(Component.LEFT_ALIGNMENT);
        return area;
    }

    private JButton button(String label) {
        JButton b = new JButton(label);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setForeground(Color.WHITE);
        b.setBackground(ACCENT);
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(10, 14, 10, 14));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        return b;
    }

    private void checkCurrentPassword() {
        String password = new String(passwordField.getPassword());
        PasswordChecker.Result result = PasswordChecker.checkPassword(password);
        strengthLabel.setText("Strength: " + result.strength);
        strengthBar.setValue(result.score);
        strengthBar.setString(result.score + " / 5");
        strengthBar.setForeground(result.score <= 2 ? new Color(239, 83, 80)
            : result.score <= 3 ? new Color(255, 183, 77) : new Color(102, 187, 106));
        checklist.setText(String.join("\n", result.checks));
        suggestions.setText(String.join("\n", result.suggestions));
    }
}
