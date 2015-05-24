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
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

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

public class TwitterView extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private MainController mc;
	private JTextField user1;
	@SuppressWarnings("unused")
	private JTextArea listTeewt;
	private String user;
	private JPanel userPanel;
	private JTextField hashtag;
	private JTextField userSearche;

	
	public TwitterView(MainController mc, String user) {

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
		Border b = new EmptyBorder(40, 5, 10, 5);
		flox2.setBorder(b);
		flox2.setBackground(Color.white);

		east.setLayout(new BorderLayout());

		flox.setBackground(Color.white);
		east.setBackground(Color.white);
		poster.setBackground(Color.white);

		JLabel pho = new JLabel();

		try {
			ImageIcon p = new ImageIcon(ImageIO.read(new File("Client/src/pl.jpg")));
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
		listTeewt = new JTextArea();
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
		userPanel = new JPanel();
		userPanel.setBackground(Color.white);
		userPanel.setBorder(new TitledBorder(new EtchedBorder(),
				"What's happening " + user + " ?"));
		user1 = new JTextField();
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

		// Button following
		Following following = new Following();

		// Button Hashtag
		Hashtag h = new Hashtag();

		flox2.add(followers);
		flox2.add(following);
		flox2.add(h);
		flox.add(userPanel);
		Border empty = new EmptyBorder(10, 5, 10, 5);

		flox.add(lsitetweetPanel);
		east.setBorder(empty);
		east.add(tweet, BorderLayout.NORTH);
		east.add(flox2, BorderLayout.CENTER);
		// poster.add(account);

		this.add(flox, BorderLayout.CENTER);

		// Hashtag
		JPanel hashtagPanel = new JPanel();
		hashtagPanel.setBackground(Color.white);
		hashtagPanel
				.setBorder(new TitledBorder(new EtchedBorder(), "Hashtag ?"));
		hashtag = new JTextField();
		hashtag.setPreferredSize(new Dimension(90, 27));
		hashtag.setFont(f);
		hashtag.setBorder(null);
		hashtag.setEditable(true);
		hashtagPanel.setMinimumSize(new Dimension(150, 60));
		hashtagPanel.setMaximumSize(new Dimension(150, 60));
		hashtagPanel.add(hashtag);

		// User
		JPanel userSearchePanel = new JPanel();
		userSearchePanel.setBackground(Color.white);
		userSearchePanel
				.setBorder(new TitledBorder(new EtchedBorder(), "User ?"));
		userSearche = new JTextField();
		userSearche.setPreferredSize(new Dimension(90, 27));
		userSearche.setFont(f);
		userSearche.setBorder(null);
		userSearche.setEditable(true);
		userSearchePanel.setMinimumSize(new Dimension(150, 60));
		userSearchePanel.setMaximumSize(new Dimension(150, 60));
		userSearchePanel.add(userSearche);

		JPanel w = new JPanel();
		
		// botton follow hashtag
		FollowHashtag fh = new FollowHashtag();
		
		// botton follow user
		FollowUser fu =  new FollowUser();

		flox2.add(hashtagPanel);
		flox2.add(fh);
		flox2.add(userSearchePanel);
		flox2.add(fu);
		w.setLayout(new BoxLayout(w, BoxLayout.PAGE_AXIS));
		w.add(poster);

		w.setBackground(Color.white);
		Dimension d = new Dimension(100, 80);
		this.add(w, BorderLayout.WEST);
		this.add(east, BorderLayout.EAST);
		// this.add(account,BorderLayout.SOUTH);

		// this.add(userPanel,BorderLayout.SOUTH);

		this.setTitle("MyTwitter");
		this.setSize(760, 400);
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
			if (!user1.getText().equals("")) {
				mc.postMessage(user1.getText());
				System.out.println("Tweet");
			}
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
			// this.setBackground(Color.WHITE);
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
	private class Hashtag extends JButton implements ActionListener {
		private Hashtag() {
			super();
			setContentAreaFilled(false);
			setFocusPainted(false);

			Dimension d = new Dimension(180, 50);

			Font f = new Font("ITALIC", Font.BOLD, 18);
			this.setPreferredSize(d);
			this.setText(" Hashtag ");
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
			mc.TwitterToHashtagView();
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

	@SuppressWarnings("serial")
	private class FollowHashtag extends JButton implements ActionListener {
		private FollowHashtag() {
			super();
			setContentAreaFilled(false);
			setFocusPainted(false);

			Dimension d = new Dimension(100, 50);

			Font f = new Font("ITALIC", Font.BOLD, 18);
			this.setPreferredSize(d);
			this.setText("Follow #");
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
			mc.followHashtag(hashtag.getText());
			System.out.println("follo hashtag");
		}
	}
	
	@SuppressWarnings("serial")
	private class FollowUser extends JButton implements ActionListener {
		private FollowUser() {
			super();
			setContentAreaFilled(false);
			setFocusPainted(false);

			Dimension d = new Dimension(100, 50);

			Font f = new Font("ITALIC", Font.BOLD, 18);
			this.setPreferredSize(d);
			this.setText("Follow @");
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
			mc.followUser(userSearche.getText());
			userSearche.setText("");
		}
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println("holaaa");
		user1.setText("");
		@SuppressWarnings("unchecked")
		ArrayList<String> temp = (ArrayList<String>) arg1;
		String temps2 = "";
		for (int i = 0; i < temp.size(); i++) {
			temps2 += temp.get(i) + "\n\n";
		}
		listTeewt.setText(temps2);
		validate();

	}
}
