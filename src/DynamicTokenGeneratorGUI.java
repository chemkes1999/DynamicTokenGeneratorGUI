import javax.swing.*;
import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Timer;
import java.util.TimerTask;

public class DynamicTokenGeneratorGUI {

    private static final String SECRET_KEY = "YourSecretKey";  // Hacer la clave configurable
    private static final long TOKEN_INTERVAL_MS = 5000;  // Intervalo de generación de tokens

    private JFrame frame;
    private JLabel tokenLabel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                DynamicTokenGeneratorGUI window = new DynamicTokenGeneratorGUI();
                window.frame.setVisible(true);
                window.startTokenGeneration();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public DynamicTokenGeneratorGUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        tokenLabel = new JLabel("Token will appear here", SwingConstants.CENTER);
        tokenLabel.setFont(new Font("Serif", Font.BOLD, 16));  // Mejorar la apariencia del texto
        frame.getContentPane().add(tokenLabel, BorderLayout.CENTER);
    }

    private void startTokenGeneration() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String token = TokenGenerator.generateToken(SECRET_KEY);
                SwingUtilities.invokeLater(() -> tokenLabel.setText(token));
            }
        }, 0, TOKEN_INTERVAL_MS);
    }

    static class TokenGenerator {
        public static String generateToken(String secretKey) {
            long currentInterval = Instant.now().getEpochSecond() / (TOKEN_INTERVAL_MS / 1000);
            String rawData = secretKey + currentInterval;
            System.out.println(rawData);

            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(rawData.getBytes());
                return Base64.getEncoder().encodeToString(hash);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return "Error generating token";
            }
        }
    }
}
