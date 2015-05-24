package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import control.MainController;

public class ErrorRemote extends JFrame {

	@SuppressWarnings("unused")
	private MainController mc;

	public ErrorRemote(MainController mc) {

		super();
		this.mc = mc;
		this.setLayout(new BorderLayout());
		JLabel l = new JLabel();
		l.setText("     Error Remote !");
		this.add(l);
		this.setTitle("MyTwitter");
		this.setSize(240, 80);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
