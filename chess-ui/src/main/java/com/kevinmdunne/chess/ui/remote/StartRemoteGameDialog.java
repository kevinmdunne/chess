package com.kevinmdunne.chess.ui.remote;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.kevinmdunne.chess.controller.GameController;
import com.kevinmdunne.chess.remote.IRemoteGameHandler;
import com.kevinmdunne.chess.remote.RemoteGameHandler;

public class StartRemoteGameDialog extends JDialog implements ActionListener,Runnable{

	private static final long serialVersionUID = -1846345981717510903L;
	
	private JButton cancelButton;
	private IRemoteGameHandler handler;
	
	public StartRemoteGameDialog(Frame owner, GameController controller){
		super(owner, "Waiting for other player to connect...", true);
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout(0,0));
		
		JPanel progressPanel = new JPanel();
		progressPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		progressPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
		JProgressBar progressBar = new JProgressBar(0, 100);
		progressBar.setIndeterminate(true);
		progressBar.setString("");
		progressBar.setSize(300, 10);
		progressBar.setPreferredSize(new Dimension(300,10));
		progressPanel.add(progressBar);
		contentPane.add(progressPanel,BorderLayout.CENTER);
		
		JPanel cancelPanel = new JPanel();
		cancelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.cancelButton = new JButton("Cancel");
		this.cancelButton.addActionListener(this);
		cancelPanel.add(this.cancelButton);
		contentPane.add(cancelPanel,BorderLayout.SOUTH);
		
		this.setSize(new Dimension(400,100));
		this.setResizable(false);
		this.setLocationRelativeTo(owner);

		this.addWindowListener(new WindowAdapter() {
	        @Override 
	        public void windowClosing(WindowEvent e) {
	        	handler.finishGame();
	    		setVisible(false);
	    		dispose();
	        }
	    });

		this.startGame(controller);
	}
	
	private void startGame(GameController controller){
		this.handler = new RemoteGameHandler(controller);
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		try{
			handler.startGame();
			this.setVisible(false);
			this.dispose();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.handler.finishGame();
		this.setVisible(false);
		this.dispose();
	}
}
