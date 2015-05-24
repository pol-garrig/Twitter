
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import control.MainController;


@SuppressWarnings("serial")
public class FollowersView extends JFrame {

	@SuppressWarnings("unused")
	private MainController mc;

	public FollowersView(MainController mc) {
		super();
		this.mc = mc;

		this.setLayout(new BorderLayout());

		// Liste de followers
		JPanel followersPanel = new JPanel();
		followersPanel.setBackground(Color.white);
		followersPanel.setBorder(new TitledBorder(new EtchedBorder(),
				"Followers"));
		JTextArea listTeewt = new JTextArea();
		Font f = new Font("Password", Font.BOLD, 14);
		listTeewt.setFont(f);
		listTeewt.setLineWrap(true);
		listTeewt.setEditable(false); // set textArea non-editable
		JScrollPane scroll3 = new JScrollPane(listTeewt);
		// scroll.
		scroll3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		Dimension d4 = new Dimension(260, 350);
		scroll3.setPreferredSize(d4);
		followersPanel.add(scroll3);

		
		//AddNewFollower add = new AddNewFollower();
		//JPanel p1 = new JPanel();
		
		this.add(followersPanel,BorderLayout.CENTER);
		//this.add(add,BorderLayout.EAST);
		
		this.setTitle("MyTwitter");
		this.setSize(320 ,380);
		this.setLocationRelativeTo(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);

	}

	@SuppressWarnings("unused")
	private class AddNewFollower extends JButton implements ActionListener {
		private AddNewFollower() {
			super();
			setContentAreaFilled(false);
			setFocusPainted(false);

			Dimension d = new Dimension(160, 40);

			Font f = new Font("VERDANA", Font.BOLD, 17);
			this.setPreferredSize(d);
			this.setMaximumSize(d);
			this.setText("New Follower");
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
			System.out.println("new Followers");
		}
	}
}
