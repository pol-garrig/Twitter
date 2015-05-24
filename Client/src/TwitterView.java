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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import control.MainController;

public class TwitterView extends JFrame {

	private static final long serialVersionUID = 1L;
	private MainController mc;
	@SuppressWarnings("unused")
	private String user;

	public TwitterView(MainController mc,String user) {
		
		super();
		this.user = user;
		this.mc = mc;
		this.setLayout(new BorderLayout());

		JPanel poster = new JPanel();
		JPanel east = new JPanel();
		JPanel flox = new JPanel();
		flox.setLayout(new BoxLayout(flox, BoxLayout.PAGE_AXIS));
		JPanel flox2 = new JPanel();
		flox2.setLayout(new BoxLayout(flox2, BoxLayout.PAGE_AXIS));
		Border b = new EmptyBorder(40, 5, 10 ,5);
		flox2.setBorder(b);
		flox2.setBackground(Color.white);

		
		east.setLayout(new BorderLayout());

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
		Font f = new Font("Password", Font.BOLD, 14);

		
		// Liste de tweet
		JPanel lsitetweetPanel = new JPanel();
		lsitetweetPanel.setBackground(Color.white);
		lsitetweetPanel
				.setBorder(new TitledBorder(new EtchedBorder(), "Tweets"));
		JTextArea listTeewt = new JTextArea();
		listTeewt.setFont(f);
		listTeewt.setLineWrap(true);
		listTeewt.setEditable(false); // set textArea non-editable
		JScrollPane scroll3 = new JScrollPane(listTeewt);
		// scroll.
		scroll3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		Dimension d4 = new Dimension(253, 300);
		scroll3.setPreferredSize(d4);
		lsitetweetPanel.add(scroll3);

		// What's happening
		JPanel userPanel = new JPanel();
		userPanel.setBackground(Color.white);
		userPanel.setBorder(new TitledBorder(new EtchedBorder(), "What's happening "+user+" ?"));
		JTextField user1 = new JTextField();
		user1.setPreferredSize(new Dimension(200, 20));
		user1.setFont(f);
		user1.setBorder(null);
		user1.setEditable(true);
		userPanel.setMinimumSize(new Dimension(250, 60));
		userPanel.setMaximumSize(new Dimension(250, 60));
		userPanel.add(user1);

		// Login Botton
		Tweet tweet = new Tweet();
		
		
		// Button followers
		Followers followers = new Followers();
		
		//Button following
		Following following =  new Following();
		
		
		flox2.add(followers);
		flox2.add(following);
		flox.add(userPanel);
		Border empty = new EmptyBorder(10, 5, 10 ,5);

		flox.add(lsitetweetPanel);
		east.setBorder(empty);
		east.add(tweet,BorderLayout.NORTH);
		east.add(flox2,BorderLayout.CENTER);
		// poster.add(account);

		this.add(flox, BorderLayout.CENTER);
	
		JPanel w = new JPanel();
		w.setLayout(new BoxLayout(w, BoxLayout.PAGE_AXIS));
		w.add(poster);
		JLabel l = new JLabel();
		l.setText("TODO FILTRE");
		Dimension d = new Dimension(100,80);
		l.setPreferredSize(d);
		w.add(l);
		this.add(w, BorderLayout.WEST);
		this.add(east, BorderLayout.EAST);
		// this.add(account,BorderLayout.SOUTH);

		// this.add(userPanel,BorderLayout.SOUTH);

		this.setTitle("MyTwitter");
		this.setSize(700, 380);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);

	}

	@SuppressWarnings("serial")
	private class Tweet extends JButton implements ActionListener {
		private Tweet() {
			super();
			setContentAreaFilled(false);
			setFocusPainted(false);
			Dimension d = new Dimension(50, 35);
			Font f = new Font("VERDANA", Font.BOLD, 20);
			this.setPreferredSize(d);
			this.setMaximumSize(d);
			this.setText("Tweet");
			this.setForeground(Color.BLACK);
			Font f2 = new Font("VERDANA", Font.BOLD, 20);
			this.setFont(f2);
			this.addActionListener(this);

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
			// fc.DeleteFilm();
			System.out.println("Tweet");
		}
	}

	@SuppressWarnings("serial")
	private class Followers extends JButton implements ActionListener {
		private Followers() {
			super();
			setContentAreaFilled(false);
			setFocusPainted(false);

			Dimension d = new Dimension(180, 50);

			Font f = new Font("ITALIC", Font.BOLD, 18);
			this.setPreferredSize(d);
			this.setText("Followers");
			Font f2 = new Font("ITALIC", Font.BOLD, 18);
			this.setFont(f2);
			this.addActionListener(this);
			//this.setBackground(Color.WHITE);
			this.setBorderPainted(false);
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
			mc.TwitterToFollowersView();
			System.out.println("Followers");
		}
	}
	
	@SuppressWarnings("serial")
	private class Following extends JButton implements ActionListener {
		private Following() {
			super();
			setContentAreaFilled(false);
			setFocusPainted(false);

			Dimension d = new Dimension(180, 50);

			Font f = new Font("ITALIC", Font.BOLD, 18);
			this.setPreferredSize(d);
			this.setText("Following");
			Font f2 = new Font("ITALIC", Font.BOLD, 18);
			this.setFont(f2);
			this.addActionListener(this);
			this.setBorderPainted(false);
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
			mc.TwitterToFollowingView();
		}
	}
}
