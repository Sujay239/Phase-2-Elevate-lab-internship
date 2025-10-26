package ChatApplication;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerGUI extends JFrame {

    private JTextPane messageArea;
    private JTextField inputField;
    private JButton sendButton;
    private JPanel header;

    private BufferedReader br;
    private PrintWriter out;
    private ServerSocket ss;
    private Socket socket;

    public ServerGUI() {
        setTitle("🟢 Server Chat");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#F4F4F4"));

        // 🟩 Header
        header = new JPanel();
        header.setBackground(Color.decode("#2E8B57"));
        header.setPreferredSize(new Dimension(600, 50));
        JLabel title = new JLabel("  Chat Server (Listening...)");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        header.setLayout(new FlowLayout(FlowLayout.LEFT));
        header.add(title);
        add(header, BorderLayout.NORTH);

        // 🟨 Message Display Area
        messageArea = new JTextPane();
        messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        messageArea.setBackground(Color.WHITE);
        messageArea.setEditable(false);
//        messageArea.setLineWrap(true);
//        messageArea.setWrapStyleWord(true);
        messageArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)
        ));

        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // 🟦 Input Area
        inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        sendButton.setBackground(Color.decode("#2E8B57"));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(Color.decode("#F4F4F4"));
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        // 💬 Event Listeners
        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        setVisible(true);
        startServer();
    }

    private void startServer() {
        try {
            ss = new ServerSocket(9999);
           JOptionPane.showMessageDialog(null,"Server started. Waiting for client...");
            socket = ss.accept();
            JOptionPane.showMessageDialog(null,"Client connected.");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            startReading();

        } catch (IOException e) {
         JOptionPane.showMessageDialog(null," Error: " + e.getMessage());
        }
    }

    private void startReading() {
        Runnable r = () -> {
            try {
                String msg;
                while ((msg = br.readLine()) != null) {
                    if (msg.equalsIgnoreCase("exit")) {
                        showMessage(" Client has left the chat.",Color.RED);
                        break;
                    }
                    showMessage("Client: " + msg,Color.green);
                }
            } catch (IOException e) {
                showMessage(" Connection closed.",Color.red);
            }
        };

        new Thread(r).start();
    }

    private void sendMessage() {
        String msg = inputField.getText().trim();
        if (!msg.isEmpty()) {
            showMessage("Me: " + msg,Color.BLACK);
            out.println(msg);
            inputField.setText("");
        }
    }

    private void showMessage(String msg, Color color) {
        SwingUtilities.invokeLater(() -> {
            StyledDocument doc = messageArea.getStyledDocument();
            Style style = messageArea.addStyle("Style", null);
            StyleConstants.setForeground(style, color);
            try {
                doc.insertString(doc.getLength(), msg + "\n", style);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        });
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(ServerGUI::new);
    }
}
