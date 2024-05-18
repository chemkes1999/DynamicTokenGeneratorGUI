import javax.swing.*;
import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Timer;
import java.util.TimerTask;

public class DynamicTokenGeneratorGUI {

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
        tokenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(tokenLabel, BorderLayout.CENTER);
    }

    private void startTokenGeneration() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String token = generateToken();
                SwingUtilities.invokeLater(() -> tokenLabel.setText(token));
            }
        }, 0, 5000);
    }

    private String generateToken() {
        long currentTimeMillis = System.currentTimeMillis();
        String rawData = "YourSecretKey" + currentTimeMillis / 5000;
        System.out.println(rawData);

        try {
            // Crear el hash SHA-256 del rawData
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawData.getBytes());
            // Codificar en Base64 para que sea más legible
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
