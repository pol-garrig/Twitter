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
import java.io.File;
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

import control.MainController;

@SuppressWarnings("serial")
public class NewAccount extends JFrame {

	private MainController mc;
	private JTextField firstName;
	private JTextField lastName;
	private JTextField pssd;
	private JTextField user;

	public NewAccount(MainController mc) {
		super();

		this.mc = mc;
		this.setLayout(new BorderLayout());

		JPanel poster = new JPanel();
		JPanel east = new JPanel();
		// JPanel centre = new JPanel();
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

		// FirstName
		JPanel firstNamePanel = new JPanel();
		firstNamePanel.setBackground(Color.white);
		firstNamePanel.setBorder(new TitledBorder(new EtchedBorder(),
				"Firstname"));
		firstName = new JTextField();
		firstName.setPreferredSize(new Dimension(200, 20));
		Font f = new Font("FirstName", Font.BOLD, 14);
		firstNamePanel.setMaximumSize(new Dimension(250, 60));
		firstName.setFont(f);
		firstName.setBorder(null);
		firstName.setEditable(true);
		firstNamePanel.add(firstName);

		// User
		JPanel lastNamePanel = new JPanel();
		lastNamePanel.setBackground(Color.white);
		lastNamePanel
				.setBorder(new TitledBorder(new EtchedBorder(), "Lastname"));
		lastName = new JTextField();
		lastName.setPreferredSize(new Dimension(200, 20));
		lastName.setFont(f);
		lastName.setBorder(null);
		lastName.setEditable(true);
		lastNamePanel.setMinimumSize(new Dimension(250, 60));
		lastNamePanel.setMaximumSize(new Dimension(250, 60));
		lastNamePanel.add(lastName);

		// Password
		JPanel passwordPanel = new JPanel();
		passwordPanel.setBackground(Color.white);
		passwordPanel
				.setBorder(new TitledBorder(new EtchedBorder(), "Password"));
		pssd = new JTextField();
		pssd.setPreferredSize(new Dimension(200, 20));
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

		ButtonNewAccount account = new ButtonNewAccount();
		flox.add(lastNamePanel);
		flox.add(firstNamePanel);
		flox.add(userPanel);
		flox.add(passwordPanel);
		east.add(account);

		this.add(flox, BorderLayout.CENTER);
		this.add(poster, BorderLayout.WEST);
		this.add(east, BorderLayout.EAST);
		// this.add(account,BorderLayout.SOUTH);

		// this.add(userPanel,BorderLayout.SOUTH);

		this.setTitle("MyTwitter");
		this.setSize(640, 240);
		this.setLocationRelativeTo(null);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);

	}

	private class ButtonNewAccount extends JButton implements ActionListener {
		private ButtonNewAccount() {
			super();
			setContentAreaFilled(false);
			setFocusPainted(false);

			Dimension d = new Dimension(160, 40);

			Font f = new Font("VERDANA", Font.BOLD, 17);
			this.setPreferredSize(d);
			this.setMaximumSize(d);
			this.setText("New Acount");
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
			if (!lastName.getText().equals("")
					&& !firstName.getText().equals("")
					&& !pssd.getText().equals("") && !user.getText().equals("")) {
				mc.createNewUser(lastName.getText(), firstName.getText(),
						pssd.getText(), user.getText());
				mc.NewAccountToConnection(user.getText(), pssd.getText());
				dispose();
			} else {
				mc.ErrorView();
			}
		}
	}

}
