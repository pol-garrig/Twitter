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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import control.MainController;


@SuppressWarnings("serial")
public class AddFollowing extends JFrame {
	private MainController c;
	private JTextField user;

	public AddFollowing(MainController c) {
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

		Font f = new Font("Password", Font.BOLD, 14);

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

		// Add Botton
		Add add = new Add();

		flox.add(userPanel);
		east.add(add);

		this.add(flox, BorderLayout.CENTER);
		this.add(poster, BorderLayout.WEST);
		this.add(east, BorderLayout.EAST);

		this.setTitle("MyTwitter");
		this.setSize(440, 80);
		this.setLocationRelativeTo(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	private class Add extends JButton implements ActionListener {
		private Add() {
			super();
			setContentAreaFilled(false);
			setFocusPainted(false);

			Dimension d = new Dimension(170, 50);

			Font f = new Font("VERDANA", Font.BOLD, 22);
			this.setPreferredSize(d);
			this.setMaximumSize(d);
			this.setText("Add");
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

			if (!user.getText().equals("")) {
				// c.ConnectionToTwitter(user.getText());
				System.out.println("Add");
				dispose();
			} else {
				c.ErrorView();
			}
		}
	}

}
