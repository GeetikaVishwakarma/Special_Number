import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SpecialNumber extends JFrame implements ActionListener {

    // Permanent background color
    private Color backgroundColor = new Color(75, 0, 130); // dark blueish
    private JTextField inputField;
    private JLabel resultLabel;
    private JButton palindromeBtn, armstrongBtn, neonBtn, automorphicBtn, primeBtn, perfectBtn, clearBtn;

    public SpecialNumber() {
        super("Special Number Checker");

        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 480);
        setMinimumSize(new Dimension(700, 420));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(backgroundColor);
        topPanel.setLayout(new GridBagLayout());
        add(topPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Enter a number:");
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        topPanel.add(title, gbc);

        inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        inputField.setHorizontalAlignment(SwingConstants.CENTER);
        inputField.setPreferredSize(new Dimension(260, 46));
        gbc.gridy = 1;
        topPanel.add(inputField, gbc);

        resultLabel = new JLabel(" ");
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setForeground(new Color(200, 230, 201));
        gbc.gridy = 2;
        topPanel.add(resultLabel, gbc);

        // Button panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 12));
        btnPanel.setBackground(new Color(20, 20, 20)); // permanent dark footer
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Buttons
        palindromeBtn = makeButton("Palindrome");
        automorphicBtn = makeButton("Automorphic");
        armstrongBtn = makeButton("Armstrong");
        neonBtn = makeButton("Neon");
        primeBtn = makeButton("Prime");
        perfectBtn = makeButton("Perfect");
        clearBtn = makeButton("Clear");

        btnPanel.add(palindromeBtn);
        btnPanel.add(automorphicBtn);
        btnPanel.add(armstrongBtn);
        btnPanel.add(neonBtn);
        btnPanel.add(primeBtn);
        btnPanel.add(perfectBtn);
        btnPanel.add(clearBtn);

        add(btnPanel, BorderLayout.SOUTH);

        // Add listeners
        palindromeBtn.addActionListener(this);
        automorphicBtn.addActionListener(this);
        armstrongBtn.addActionListener(this);
        neonBtn.addActionListener(this);
        primeBtn.addActionListener(this);
        perfectBtn.addActionListener(this);
        clearBtn.addActionListener(this);

        setVisible(true);
    }

    private JButton makeButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setPreferredSize(new Dimension(135, 42));
        b.setFocusPainted(false);
        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == clearBtn) {
            inputField.setText("");
            resultLabel.setText(" ");
            return;
        }

        Integer n = parseInput();
        if (n == null) return;

        if (src == palindromeBtn) {
            boolean ok = isPalindrome(n);
            showResult(n + (ok ? " is a Palindrome." : " is NOT a Palindrome."), ok);
        } else if (src == automorphicBtn) {
            boolean ok = isAutomorphic(n);
            showResult(n + (ok ? " is Automorphic." : " is NOT Automorphic."), ok);
        } else if (src == armstrongBtn) {
            boolean ok = isArmstrong(n);
            showResult(n + (ok ? " is an Armstrong number." : " is NOT an Armstrong number."), ok);
        } else if (src == neonBtn) {
            boolean ok = isNeon(n);
            showResult(n + (ok ? " is a Neon number." : " is NOT a Neon number."), ok);
        } else if (src == primeBtn) {
            boolean ok = isPrime(n);
            showResult(n + (ok ? " is Prime." : " is NOT Prime."), ok);
        } else if (src == perfectBtn) {
            boolean ok = isPerfect(n);
            showResult(n + (ok ? " is a Perfect number." : " is NOT a Perfect number."), ok);
        }
    }

    private Integer parseInput() {
        String s = inputField.getText().trim();
        if (s.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a non-negative integer.", "Input required", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if (!s.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Only non-negative integers allowed.", "Invalid input", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        try { return Integer.parseInt(s); }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Number too large.", "Invalid input", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void showResult(String text, boolean positive) {
        resultLabel.setText(text);
        resultLabel.setForeground(positive ? new Color(144, 238, 144) : new Color(255, 182, 193));
    }

    // Number checks
    private boolean isPalindrome(int num){
        String s = String.valueOf(num);
        return s.equals(new StringBuilder(s).reverse().toString());
    }

    private boolean isArmstrong(int num) {
        int sum = 0, temp = num, digits = String.valueOf(num).length();
        while (temp > 0) {
            int d = temp % 10;
            sum += Math.pow(d, digits);
            temp /= 10;
        }
        return sum == num;
    }

    private boolean isNeon(int num) {
        int sum = 0, sq = num*num;
        while (sq > 0) {
            sum += sq % 10;
            sq /= 10;
        }
        return sum == num;
    }

    private boolean isAutomorphic(int num) {
        int sq = num * num;
        return String.valueOf(sq).endsWith(String.valueOf(num));
    }

    private boolean isPrime(int num) {
        if (num <= 1) return false;
        if (num <= 3) return true;
        if (num % 2 == 0) return false;
        for (int i = 3; i <= Math.sqrt(num); i += 2)
            if (num % i == 0) return false;
        return true;
    }

    private boolean isPerfect(int num) {
        if (num <= 1) return false;
        int sum = 1;
        for (int i = 2; i <= num / 2; i++)
            if (num % i == 0) sum += i;
        return sum == num;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SpecialNumber::new);
    }
}
