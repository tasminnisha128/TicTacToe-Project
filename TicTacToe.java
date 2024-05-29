import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;


public class TicTacToe implements ActionListener {

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel title_panel = new JPanel();
    JPanel button_panel = new JPanel();
    JLabel textfield = new JLabel();
    JLabel gameOverLabel = new JLabel("GAME OVER", JLabel.CENTER);
    JButton[] buttons = new JButton[9];
    boolean player1_turn;
    boolean backgroundMusicIsPlaying = false;
    Timer gameOverTimer;
    


    TicTacToe() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.getContentPane().setBackground(new Color(50,50,50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
       

        textfield.setBackground(new Color(25,25,25));
        textfield.setForeground(new Color(25,255,0));
        textfield.setFont(new Font("Ink Free",Font.BOLD,75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
        textfield.setOpaque(true);

        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0,0,800,100);
        title_panel.add(textfield, BorderLayout.CENTER);

        button_panel.setLayout(new GridLayout(3,3));
        button_panel.setBackground(new Color(150,150,150));

        for(int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli",Font.BOLD,120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }

        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(button_panel, BorderLayout.CENTER);

        // Initialize and add the game over label
        gameOverLabel.setFont(new Font("MV Boli", Font.BOLD, 100));
        gameOverLabel.setForeground(new Color(25,225,0));
        gameOverLabel.setOpaque(true);
        gameOverLabel.setBackground(new Color(0, 0, 0, 250));
        gameOverLabel.setBounds(0, 0, 800, 800);
        gameOverLabel.setVisible(false);

        frame.getLayeredPane().add(gameOverLabel, JLayeredPane.POPUP_LAYER);

         // Initialize the Timer in the constructor
         gameOverTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the game over label after the delay
                gameOverLabel.setVisible(true);
                
                // Stop the Timer to prevent repeated firing
                gameOverTimer.stop();
            }
        });

        firstTurn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for(int i = 0; i < 9; i++) {
            if(e.getSource() == buttons[i]) {
                if(player1_turn) {
                    if(buttons[i].getText().equals("")) {
                        buttons[i].setForeground(new Color(61,74,236));
                        buttons[i].setText("X");
                        player1_turn = false;
                        textfield.setText("O turn");
                        check();
                    }
                } else {
                    if(buttons[i].getText().equals("")) {
                        buttons[i].setForeground(new Color(255,0,114));
                        buttons[i].setText("O");
                        player1_turn = true;
                        textfield.setText("X turn");
                        check();
                    }
                }
            }            
        }
    }

    public void firstTurn() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(random.nextInt(2) == 0) {
            player1_turn = true;
            textfield.setText("X turn");
        } else {
            player1_turn = false;
            textfield.setText("O turn");
        }
    }

    public void playBackgroundMusic() {
    try {
        // Load the audio file
        File audioFile = new File("./videoplayback.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

        // Get a Clip object to play the audio
        Clip clip = AudioSystem.getClip();

        // Open the audio clip with the audio stream
        clip.open(audioStream);

        // Play the background music once
        clip.start();

        // Wait for the clip to finish playing
        clip.addLineListener(new LineListener() {
            public void update(LineEvent event) {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close(); // Close the clip after it stops playing
                }
            }
        });
    } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
        e.printStackTrace();
    }
}

    

    public void check() {
        //check X win conditions
        if(
                (buttons[0].getText().equals("X")) &&
                (buttons[1].getText().equals("X")) &&
                (buttons[2].getText().equals("X"))
        ) {
            xWins(0,1,2);
        }
        if(
                (buttons[3].getText().equals("X")) &&
                (buttons[4].getText().equals("X")) &&
                (buttons[5].getText().equals("X"))
        ) {
            xWins(3,4,5);
        }
        if(
                (buttons[6].getText().equals("X")) &&
                (buttons[7].getText().equals("X")) &&
                (buttons[8].getText().equals("X"))
        ) {
            xWins(6,7,8);
        }
        if(
                (buttons[0].getText().equals("X")) &&
                (buttons[3].getText().equals("X")) &&
                (buttons[6].getText().equals("X"))
        ) {
            xWins(0,3,6);
        }
        if(
                (buttons[1].getText().equals("X")) &&
                (buttons[4].getText().equals("X")) &&
                (buttons[7].getText().equals("X"))
        ) {
            xWins(1,4,7);
        }
        if(
                (buttons[2].getText().equals("X")) &&
                (buttons[5].getText().equals("X")) &&
                (buttons[8].getText().equals("X"))
        ) {
            xWins(2,5,8);
        }
        if(
                (buttons[0].getText().equals("X")) &&
                (buttons[4].getText().equals("X")) &&
                (buttons[8].getText().equals("X"))
        ) {
            xWins(0,4,8);
        }
        if(
                (buttons[2].getText().equals("X")) &&
                (buttons[4].getText().equals("X")) &&
                (buttons[6].getText().equals("X"))
        ) {
            xWins(2,4,6);
        }
        //check O win conditions
        if(
                (buttons[0].getText().equals("O")) &&
                (buttons[1].getText().equals("O")) &&
                (buttons[2].getText().equals("O"))
        ) {
            oWins(0,1,2);
        }
        if(
                (buttons[3].getText().equals("O")) &&
                (buttons[4].getText().equals("O")) &&
                (buttons[5].getText().equals("O"))
        ) {
            oWins(3,4,5);
        }
        if(
                (buttons[6].getText().equals("O")) &&
                (buttons[7].getText().equals("O")) &&
                (buttons[8].getText().equals("O"))
        ) {
            oWins(6,7,8);
        }
        if(
                (buttons[0].getText().equals("O")) &&
                (buttons[3].getText().equals("O")) &&
                (buttons[6].getText().equals("O"))
        ) {
            oWins(0,3,6);
        }
        if(
                (buttons[1].getText().equals("O")) &&
                (buttons[4].getText().equals("O")) &&
                (buttons[7].getText().equals("O"))
        ) {
            oWins(1,4,7);
        }
        if(
                (buttons[2].getText().equals("O")) &&
                (buttons[5].getText().equals("O")) &&
                (buttons[8].getText().equals("O"))
        ) {
            oWins(2,5,8);
        }
        if(
                (buttons[0].getText().equals("O")) &&
                (buttons[4].getText().equals("O")) &&
                (buttons[8].getText().equals("O"))
        ) {
            oWins(0,4,8);
        }
        if(
                (buttons[2].getText().equals("O")) &&
                (buttons[4].getText().equals("O")) &&
                (buttons[6].getText().equals("O"))
        ) {
            oWins(2,4,6);
        }
    }

    public void xWins(int a, int b, int c) {
        buttons[a].setBackground(Color.GREEN);
        buttons[b].setBackground(Color.GREEN);
        buttons[c].setBackground(Color.GREEN);

        for(int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
        textfield.setText("X wins!");
        gameOver();
    }
    public void oWins(int a, int b, int c) {
        buttons[a].setBackground(Color.GREEN);
        buttons[b].setBackground(Color.GREEN);
        buttons[c].setBackground(Color.GREEN);

        for(int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
        textfield.setText("O wins!");
        gameOver();
    }

    public void gameOver() {
        // Start the Timer to delay the appearance of the game over screen
        gameOverTimer.start();
        // Check if background music is already playing
        if (!backgroundMusicIsPlaying) {
            // If not playing, set flag to indicate music is playing
            backgroundMusicIsPlaying = true;
            
            // Call the method to play background music
            playBackgroundMusic();
        }
    }

    
}
