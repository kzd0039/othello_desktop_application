package othello;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;



/*
the main frame of othello game
 */
public class StartOthelloGame extends JFrame{
	  private Board board;
	  private JPanel toolbar;
	  private JButton startButton, backButton, exitButton, predictButton;
	  
	  private JMenuBar menuBar;
	  private JMenu sysMenu;
	  private JMenuItem startMenuItem, exitMenuItem, backMenuItem, buildBoardMenuItem, predictMenuItem;
	  
	  public StartOthelloGame() {
		  setTitle("Othello Game");
		  board = new Board();
		  
		  //create contentPane for the chess board
		  Container contentPane = getContentPane();
		  contentPane.add(board);
		  board.setOpaque(true);
		  
		  //new the menu items and add them to the menu bar
		  menuBar = new JMenuBar();
		  sysMenu = new JMenu("Options");
		  startMenuItem = new JMenuItem("restart");
		  exitMenuItem = new JMenuItem("exit"); 
		  backMenuItem = new JMenuItem("goback");
		  buildBoardMenuItem = new JMenuItem("bulid board");
		  predictMenuItem = new JMenuItem("predict");
		  
		  sysMenu.add(startMenuItem);
		  sysMenu.add(backMenuItem);
		  sysMenu.add(predictMenuItem);
		  sysMenu.add(buildBoardMenuItem);
		  sysMenu.add(exitMenuItem);
	
		  // register the menu items on the action listener
		  MyItemListener lis = new MyItemListener();
		  startMenuItem.addActionListener(lis);
		  exitMenuItem.addActionListener(lis);
		  backMenuItem.addActionListener(lis);
		  buildBoardMenuItem.addActionListener(lis);
		  predictMenuItem.addActionListener(lis);
		  
		  
		  // new buttons and tool bar, then add the buttons on tool bar
		  startButton = new JButton("Start");
		  backButton = new JButton("Back");
		  exitButton = new JButton("Exit");
		  predictButton= new JButton("Predict");
		  
		  
		  menuBar.add(sysMenu);
		  setJMenuBar(menuBar);
		  
		  
		  toolbar = new JPanel();
		  toolbar.add(startButton);
		  toolbar.add(backButton);
		  toolbar.add(exitButton);
		  toolbar.add(predictButton);
		  
		  toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
		  // register the buttons on action listener
		  startButton.addActionListener(lis);
		  backButton.addActionListener(lis);
		  exitButton.addActionListener(lis);
		  predictButton.addActionListener(lis);
		  
		  // set the tool bar at the bottom of the window
		  add(toolbar,BorderLayout.SOUTH);
//		  add(board);
		  
		  add(toolbar,BorderLayout.SOUTH);
		  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  //setSize(800,800);
		  pack();
	  }
	  
	  private class MyItemListener implements ActionListener {
		  public void actionPerformed(ActionEvent e){
			  Object obj = e.getSource();//get the event
			  if(obj == StartOthelloGame.this.startMenuItem || obj == startButton) {
				  //restart
				  System.out.println("restart");
				  board.reStart();
			  }
			  else if (obj == exitMenuItem || obj == exitButton)
				  System.exit(0);

			  else if (obj == predictMenuItem || obj == predictButton) {
				  System.out.println("predict the next step");
//				  board.predict();
			  }
			  else if (obj == buildBoardMenuItem) {
				  System.out.println("build your own board");
			  }
		  }
	  }
	  
	  
	  public static void main(String[] args) {
		  StartOthelloGame f = new StartOthelloGame();
		  f.setVisible(true);
	  }
}
