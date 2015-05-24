
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import model.Client;

import control.MainController;

import java.io.File;

@SuppressWarnings("serial")
public class ConnectionFrame extends JFrame {

	private MainController c;
	private JTextField pssd;
	private JTextField user;

	public ConnectionFrame(MainController c) {
		super();
		this.c = c;
		this.setLayout(new BorderLayout());

		JPanel poster = new JPanel();
		JPanel east = new JPanel();
		JPanel flox = new JPanel();
		flox.setLayout(new BoxLayout(flox, BoxLayout.PAGE_AXIS));
		east.setLayout(new BoxLayout(east, BoxLayout.PAGE_AXIS));

		flox.setBackground(Color.white);
		east.setBackground(Color.white);
		poster.setBackground(Color.white);

		JLabel pho = new JLabel();

		try {
			ImageIcon p = new ImageIcon(ImageIO.read(new File("pl.jpg")));
			pho.setIcon(p);
			poster.add(pho);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Password
		JPanel passwordPanel = new JPanel();
		passwordPanel.setBackground(Color.white);
		passwordPanel
				.setBorder(new TitledBorder(new EtchedBorder(), "Password"));
		pssd = new JTextField();
		pssd.setPreferredSize(new Dimension(200, 20));
		Font f = new Font("Password", Font.BOLD, 14);
		passwordPanel.setMaximumSize(new Dimension(250, 60));
		pssd.setFont(f);
		pssd.setBorder(null);
		pssd.setEditable(true);
		passwordPanel.add(pssd);

		// User
		JPanel userPanel = new JPanel();
		userPanel.setBackground(Color.white);
		userPanel.setBorder(new TitledBorder(new EtchedBorder(), "User"));
		user = new JTextField();
		user.setPreferredSize(new Dimension(200, 20));
		user.setFont(f);
		user.setBorder(null);
		user.setEditable(true);
		userPanel.setMinimumSize(new Dimension(250, 60));
		userPanel.setMaximumSize(new Dimension(250, 60));
		userPanel.add(user);

		// Login Botton
		Login login = new Login();
		// Login new account
		NewAcount account = new NewAcount();

		flox.add(userPanel);
		flox.add(passwordPanel);
		east.add(login);
		east.add(account);

		this.add(flox, BorderLayout.CENTER);
		this.add(poster, BorderLayout.WEST);
		this.add(east, BorderLayout.EAST);

		this.setTitle("MyTwitter");
		this.setSize(640, 240);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);

	}

	private class Login extends JButton implements ActionListener {
		private Login() {
			super();
			setContentAreaFilled(false);
			setFocusPainted(false);

			Dimension d = new Dimension(170, 50);

			Font f = new Font("VERDANA", Font.BOLD, 22);
			this.setPreferredSize(d);
			this.setMaximumSize(d);
			this.setText("Login");
			this.setForeground(Color.BLACK);
			Font f2 = new Font("VERDANA", Font.BOLD, 22);
			this.setFont(f2);
			this.addActionListener(this);
			// this.setForeground(Color.white);
			this.setFont(f);
			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setPaint(new GradientPaint(new Point(0, 0), Color.white,
					new Point(0, getHeight()), Color.WHITE.darker()));
			g2.fillRect(0, 0, getWidth(), getHeight());
			g2.dispose();
			super.paintComponent(g);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if (!pssd.getText().equals("") && !user.getText().equals("")) {
				c.ConnectionToTwitter(user.getText(), pssd.getText());
				System.out.println("Login");
				dispose();
			} else {
				c.ErrorView();
			}
		}
	}

	private class NewAcount extends JButton implements ActionListener {
		private NewAcount() {
			super();
			setContentAreaFilled(false);
			setFocusPainted(false);
			Dimension d = new Dimension(180, 50);
			Font f = new Font("ITALIC", Font.BOLD, 18);
			this.setPreferredSize(d);
			this.setText("New Acount");
			Font f2 = new Font("ITALIC", Font.BOLD, 18);
			this.setFont(f2);
			this.addActionListener(this);
			this.setBackground(Color.WHITE);
			this.setBorderPainted(false);
			this.setFont(f);
			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			c.ConnectionToNewAccount();
			System.out.println("New Acount");
			dispose();
		}
	}

	public static void main(String[] args) {
		Client c = new Client("localhost", 1099);
		MainController crt = new MainController(c);
		@SuppressWarnings("unused")
		ConnectionFrame c2 = new ConnectionFrame(crt);
	}

}
