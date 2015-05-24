package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;


import control.MainController;

@SuppressWarnings("serial")
public class ErrorView extends JFrame {

	@SuppressWarnings("unused")
	private MainController mc;

	public ErrorView(MainController mc) {

		super();
		this.mc = mc;
		this.setLayout(new BorderLayout());
		JLabel l = new JLabel();
		l.setText("     Error, please try again!");
		this.add(l);
		this.setTitle("MyTwitter");
		this.setSize(240, 80);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
