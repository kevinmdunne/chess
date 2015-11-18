package com.kevinmdunne.chess.ui.remote;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.kevinmdunne.chess.controller.GameController;
import com.kevinmdunne.chess.remote.IRemoteGameHandler;
import com.kevinmdunne.chess.remote.RemoteGameHandler;

public class JoinRemoteGameDialog extends JDialog implements ActionListener,KeyListener{

	private static final long serialVersionUID = 35563986381726124L;
	
	private JButton joinButton;
	private JButton cancelButton;
	private JTextField hostText;
	private IRemoteGameHandler handler;
	private GameController controller;
	
	public JoinRemoteGameDialog(Frame owner, GameController controller){
		super(owner, "Join Remote Game...", true);
		this.controller = controller;
		
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout(0,0));
		
		JPanel hostnamePanel = new JPanel();
		hostnamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		hostnamePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
		JLabel label = new JLabel("Enter game host's IP address :");
		this.hostText = new JTextField(17);
		this.hostText.addKeyListener(this);
		hostnamePanel.add(label);
		hostnamePanel.add(this.hostText);
		contentPane.add(hostnamePanel,BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.joinButton = new JButton("Join");
		this.cancelButton  = new JButton("Cancel");
		this.joinButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.joinButton.setEnabled(false);
		buttonPanel.add(this.joinButton);
		buttonPanel.add(this.cancelButton);
		contentPane.add(buttonPanel,BorderLayout.SOUTH);
		
		this.setSize(new Dimension(400,100));
		this.setResizable(false);
		this.setLocationRelativeTo(owner);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.cancelButton)){
			this.setVisible(false);
			this.dispose();
		}else if(e.getSource().equals(this.joinButton)){
			try{
				this.handler = new RemoteGameHandler(controller);
				this.handler.joinGame(this.hostText.getText());
				this.setVisible(false);
				this.dispose();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(!this.hostText.getText().isEmpty()){
			this.joinButton.setEnabled(true);
		}else{
			this.joinButton.setEnabled(false);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
}
