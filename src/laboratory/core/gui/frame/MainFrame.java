package laboratory.core.gui.frame;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.URL;
import java.io.IOException;

import laboratory.core.InterfaceConfig;

import laboratory.util.Parser;

public class MainFrame extends JFrame {

    public MainFrame(String title, URL logo, URL description, final URL example) throws IOException {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final Parser properties = InterfaceConfig.MAIN_FRAME_PROPERTIES;

        final JPanel buttonPanel = new JPanel();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(getHTMLViewer(logo), BorderLayout.NORTH);
        mainPanel.add(getHTMLViewer(description), BorderLayout.CENTER);

        JButton exampleButton = new JButton(properties.getString("button-example"));
        exampleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showHelp(properties.getString("example-title"),
                        properties.getInt("example-width"), properties.getInt("example-height"),
                        properties.getInt("example-location-delta-x"), properties.getInt("example-location-delta-y"),
                        example, MainFrame.this);
            }
        });
        buttonPanel.add(exampleButton);

        final JButton nextButton = new JButton(properties.getString("button-next"));
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                JFrame mf = new SelectionFrame();
                mf.setVisible(true);
            }
        });
        buttonPanel.add(nextButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        pack();
    }

    private static JScrollPane getHTMLViewer(URL url) throws IOException {
        final JEditorPane textField = new JEditorPane(url);
        textField.setContentType("text/html; charset=utf8");
        textField.setEditable(false);
        textField.revalidate();
        return new JScrollPane(textField);
    }

    public static void showHelp(String title, int width, int height, int deltaX, int deltaY, URL url, JFrame parent) {
        JFrame f = new JFrame(title);
        try {
            f.getContentPane().add(getHTMLViewer(url));
        } catch (Exception z) {
            throw new RuntimeException(z);
        }
        f.pack();
        f.setSize(width, height);
        f.setLocation(parent.getLocation().x + deltaX, parent.getLocation().y + deltaY);
        f.setVisible(true);
    }
}