package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TebakYuk extends JFrame implements ActionListener {

	JPanel panel1 = new JPanel(new GridLayout(5, 2));
	JPanel panel2;
	JLabel lblNumberOfAttempts, lblAttemptCount;
	JButton[] button = new JButton[10];
	Random rand = new Random();
	String[] numbers = { "1", "2", "3", "4", "5" };
	String[] field = new String[10];
	int rows = 5;
	int columns = 2;
	int numberOfClicks;
	Integer attempt = 10;
	String click1, click2;

	public TebakYuk() {
		setFrame();
		setContents();
		hideTiles();
		setVisible(true);
	}

	private void randomizeNumbers() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				while (true) {
					int randomNumber = rand.nextInt(10);
					if (field[randomNumber] == null) {
						button[randomNumber].setText(numbers[i]);
						field[randomNumber] = numbers[i];
						break;
					}
				}
			}

		}
	}

	private void setContents() {

		for (int i = 0; i < 10; i++) {
			button[i] = new JButton("");
			button[i].addActionListener(this);
			button[i].setEnabled(true);
			panel1.add(button[i]);
		}

		randomizeNumbers();
		
		//Adding try(s) count
		panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		lblNumberOfAttempts = new JLabel("Number of attempt(s) left: ");
		lblAttemptCount = new JLabel("99");
		lblAttemptCount.setText(attempt.toString());
		
		panel2.add(lblNumberOfAttempts);
		panel2.add(lblAttemptCount);
		

		add(panel1, BorderLayout.CENTER);
		add(panel2, BorderLayout.SOUTH);

	}

	private void hideTiles() {
		for (int i = 0; i < 10; i++) {
			button[i].setText("X");
		}
	}
	
	private void reset() {
		for (int i = 0; i < 10; i++) {
			button[i].setText("");
			field[i] = null;
		}
		attempt = 10;
		lblAttemptCount.setText(attempt.toString());
	}
	
	private void reduceAttemptCount() {
		attempt--;
		lblAttemptCount.setText(attempt.toString());
	}

	private boolean validateWinCondition() {
		for (int i = 0; i < 10; i++) {
			if (button[i].getText() == "X")
				return false;
		}
		return true;
	}

	private void setFrame() {
		setTitle("Tebak Yuk");
		setSize(1280, 720);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		for (int j = 0; j < 10; j++) {

			if (e.getSource() == button[j]) {
				if (numberOfClicks == 0) { // Ini jika player belum memulai gamenya
					numberOfClicks++; //
					button[j].setText(field[j]);// Maka pilihan tidak akan salah
					click1 = field[j]; // Pilihan ditampung di dalam String click1
				} else if (numberOfClicks > 0) {
					numberOfClicks = 0;
					click2 = field[j]; // Pilihan ditampung di dalam String click2

					if (click1.equals(click2)) { // click1 akan dibandingkan dengan click2
						button[j].setText(field[j]); //Jika benar, maka button kedua yang ditekan akan berubah
					} else if (!click1.equals(click2)) { //Jika salah,
						hideTiles();						// akan kembali ke "X" dan mengurangi try count -1
						reduceAttemptCount();
					}
					if (validateWinCondition() == true) {
						JOptionPane.showMessageDialog(null, "Selamat Anda Menang!");
						hideTiles();
					}
				}
				
				if(attempt==0) {
					JOptionPane.showMessageDialog(null, "You have ran out of attempts! The game will restart");
					reset();
					randomizeNumbers();
					hideTiles();
				}

			}

		}
	}
}
