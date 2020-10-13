package othello;

import java.util.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
 
import javax.swing.*;


/**
 * class for the board
 */
 

public class Board extends JPanel implements MouseListener {
	//define the margin, grid span and number of rows as well as columns
   public static final int MARGIN = 30;
   public static final int GRID_SPAN = 35;
   public static final int ROWS = 8;
   public static final int COLS = 8;
   public static final int[][] DIRECTIONS = {{1,0},{-1,0},{0,1},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}};
   public static final Color[] COLORS = {Color.white, Color.black};
   //initial the chess (point) objects list
   Point[] chessList=new Point[(ROWS) * (COLS)];
   
   // By default, black is the first to place chess
   boolean isBlack=true;
   // last chess placed on the board
   int last = -1;
   //initialize the game state
   boolean gameOver=false;
 
   
   Image img;
   Image shadows;

   
   public Board(){
	   //fill the board with point object(color = 0)
	   for (int r = 0; r < ROWS; r++) {
		   for (int c = 0; c < COLS; c++) {
			   int index = getIndex(r,c);
			   chessList[index] = new Point(r, c, -1);
		   }
	   }
	   chessList[getIndex(3,3)].setColor(1);
	   chessList[getIndex(4,4)].setColor(1);
	   chessList[getIndex(3,4)].setColor(0);
	   chessList[getIndex(4,3)].setColor(0);
	   
	   setBackground(Color.white);
	   img=Toolkit.getDefaultToolkit().getImage("board.jpg");
	   shadows=Toolkit.getDefaultToolkit().getImage("shadows.jpg");
	   addMouseListener(this);
	   addMouseMotionListener(new MouseMotionListener(){
		   public void mouseDragged(MouseEvent e){
			   
		   }
		   
		   public void mouseMoved(MouseEvent e){
			 //convert the mouse position to r and c of the points
		     int r = (e.getX()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
		     int c = (e.getY()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
		     
		     int index = getIndex(r,c);
		     if(index == -1 ||gameOver|| chessList[index].getColor() == 0) {
		    	 setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		     }
		     else setCursor(new Cursor(Cursor.HAND_CURSOR));
		     
		   }
	   });
   } 
   
  
 
   //paint the grid
   public void paintComponent(Graphics g){
	 
	   super.paintComponent(g);
	   
	   // get the width and height of the picture
	   int imgWidth= img.getWidth(this);
	   int imgHeight=img.getHeight(this);
	   //get the width and height of the window
	   int FWidth=getWidth();
	   int FHeight=getHeight();
	   int r = (FWidth-imgWidth)/2;
	   int c = (FHeight-imgHeight)/2; 
	   g.drawImage(img, r, c, null);
	
	   // draw horizontal line
	   for(int i=0; i<ROWS;i++){
		   g.drawLine(MARGIN, MARGIN+i*GRID_SPAN, MARGIN+(COLS-1)*GRID_SPAN, MARGIN+i*GRID_SPAN);
	   }
	   // draw vertical line
	   for(int i=0; i<COLS;i++){
		   g.drawLine(MARGIN+i*GRID_SPAN, MARGIN, MARGIN+i*GRID_SPAN, MARGIN+(ROWS-1)*GRID_SPAN);
		   
	   }
	   
	   //draw the points
	   for(int i=0; i< chessList.length;i++){
		   int color = chessList[i].getColor();
		   if (color == -1) {
			   continue;
		   }
		   //get position on the window
		   int xPos=chessList[i].getX()*GRID_SPAN+MARGIN;
		   int yPos=chessList[i].getY()*GRID_SPAN+MARGIN;
		   //set color
		   g.setColor(COLORS[color]);
		
		   if(color == 1){
			   RadialGradientPaint paint = new RadialGradientPaint(xPos-Point.DIAMETER/2+25, yPos-Point.DIAMETER/2+10, 20, new float[]{0f, 1f}
               , new Color[]{Color.WHITE, Color.BLACK});
               ((Graphics2D) g).setPaint(paint);
               ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
               ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
 
		   }
		   else {
			   RadialGradientPaint paint = new RadialGradientPaint(xPos-Point.DIAMETER/2+25, yPos-Point.DIAMETER/2+10, 70, new float[]{0f, 1f}
               , new Color[]{Color.WHITE, Color.BLACK});
               ((Graphics2D) g).setPaint(paint);
               ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
               ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
 
		   }
		 
		   Ellipse2D e = new Ellipse2D.Float(xPos-Point.DIAMETER/2, yPos-Point.DIAMETER/2, 34, 35);
		   ((Graphics2D) g).fill(e);
	      
		   
//		   if it is the last point put on the board, add red rectangular
		   if(i == last){
			   g.setColor(Color.red);
			   g.drawRect(xPos-Point.DIAMETER/2, yPos-Point.DIAMETER/2,
				           34, 35);
		   }
	   }
   }
   
   // return the index in chess list
   public int getIndex(int r, int c) {
	   //if out of bounds return -1
	   if (r < 0||r >= ROWS||c < 0||c >= COLS) {
		   return -1;
	   }
	   return c + r * ROWS;
   }
   
   // when press the mouse
   public void mousePressed(MouseEvent e){
	   
	   //if game is over, return
	   int index;
	   ArrayList<ArrayList<Integer>> p = predict();
	   if(p.get(0).size() == 0 && p.get(1).size() == 0){
		   int white = 0;
		   int black = 0;
		   for (int i = 0; i < ROWS; i++) {
			   for (int j = 0; j < COLS; j++) {
				   index = getIndex(i,j);
				   if (chessList[index].getColor() == 1) {
					   black++;
				   }
				   else {
					   white++;
				   }
			   }
		   }
		   String msg=String.format("Congratulations，%s Win！", black > white ? "black" : "white");
		   JOptionPane.showMessageDialog(this, msg);
		   gameOver=true;
		   return;
	   }
	   int color = isBlack ? 1 : 0;
	   if (p.get(color).size() == 0) {
		   isBlack = !isBlack;
		   return;
	   }
	   
	   //convert the index, where mouse points, to the index of chess on the grid
	   int r = (e.getX()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
	   int c = (e.getY()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
	   index = getIndex(r, c);
	   //if out of bounds or the position is occupied, return
	   if(index == -1 || chessList[index].getColor() != -1) {
		   return;
	   }
	   boolean changed = false;
	   for (int[] direction: DIRECTIONS) {
		  if (shouldFlip(r,c,direction,color)) {
			  flip(r,c,direction);
			  changed = true;
		  }
	   }
	   if (!changed) {
		   return;
	   }
	   chessList[index].setColor(color);
	   last = index;
	   repaint();
	   
	   isBlack=!isBlack;
   }
   
   public boolean shouldFlip(int r, int c, int[] direction, int color) {
	   r += direction[0];
	   c += direction[1];
	   int path = 1 - color;
	   int end = color;
	   int index = getIndex(r,c);
	   while(index != -1 && chessList[index].getColor() == path) {
		   r += direction[0];
		   c += direction[1];
		   index = getIndex(r,c);
	   }
	   if (index != -1 && chessList[index].getColor() == end) {
		   return true;
	   }
	   return false;
   }

   public void flip(int r, int c, int[] direction) {
	   r += direction[0];
	   c += direction[1];
	   int end = isBlack ? 1 : 0;
	   int index = getIndex(r,c);
	   while(index != -1 && chessList[index].getColor() != end) {
		   chessList[index].setColor(end);
		   r += direction[0];
		   c += direction[1];
		   index = getIndex(r,c);
	   }
   }
   //Override the  mouseListener method
   public void mouseClicked(MouseEvent e){
	   //
   }
   //Override the  mouseListener method
   public void mouseEntered(MouseEvent e){
	   //
   }
   //Override the  mouseListener method
   public void mouseExited(MouseEvent e){
	   //
   }
   //Override the  mouseListener method
   public void mouseReleased(MouseEvent e){
	   //
   }


   public void reStart(){
	   //reset the board
	   for (int r = 0; r < ROWS; r++) {
		   for (int c = 0; c < COLS; c++) {
			   int index = getIndex(r,c);
			   chessList[index] = new Point(r, c, -1);
		   }
	   }
	   chessList[getIndex(3,3)].setColor(1);
	   chessList[getIndex(4,4)].setColor(1);
	   chessList[getIndex(3,4)].setColor(0);
	   chessList[getIndex(4,3)].setColor(0);
	   //reset the parameter
	   isBlack=true;
	   gameOver=false;
	   repaint();
   }
   // predict the next step
   public ArrayList<ArrayList<Integer>> predict() {
	   ArrayList<ArrayList<Integer>> ans = new  ArrayList<ArrayList<Integer>>();
	   ans.add(new ArrayList<Integer>());
	   ans.add(new ArrayList<Integer>());
	   
	   for (int r = 0; r < ROWS; r++) {
		   for (int c = 0; c < COLS; c++) {
			   if (chessList[getIndex(r,c)].getColor() != -1) {
				   continue;
			   }
			   for (int[] direction: DIRECTIONS) {
					  if (shouldFlip(r,c,direction,0)) {
						  ans.get(0).add(getIndex(r,c));
					  }
					  if (shouldFlip(r,c,direction,1)) {
						  ans.get(1).add(getIndex(r,c));
					  }
				   }
		   }
	   }
	   return ans;
   }
  
   //Dimension
   public Dimension getPreferredSize(){
	   return new Dimension(MARGIN*2+GRID_SPAN*COLS,MARGIN*2
	                        +GRID_SPAN*ROWS);
   }
   
}
	   
