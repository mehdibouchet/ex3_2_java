package tp3_progConc;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Balle extends JPanel{
	private int  x, y, rayon, directionX, directionY;
	private double speed;
	private Color c;
	
	private int  Ox, Oy;
	private double max_X, max_Y;
	
	
	public Balle(int plateau_x, int plateau_y, double h, double w) {
		int c_r= (int) ( Math.random()*256) ;	int c_g= (int) ( Math.random()*256 );
		int c_b= (int) ( Math.random()*256 );
		
		rayon= 40; speed=1; 
		Ox= (int) plateau_x; Oy= (int) plateau_y;
		max_X= (int) (w-rayon); max_Y= (int) (h-rayon); 
		directionX= (int) Math.pow(-1, (int)(Math.random()*10) );
		directionY= (int) Math.pow(-1, (int)(Math.random()*10) );
		x= (int) ( Math.random()*max_X) + Ox; y= (int) ( Math.random()*max_Y) + Oy;


		c= new Color(c_r, c_g, c_b);
	}
	public void moveBalle() {
		if(directionX==1 && x >= max_X+Ox ) directionX=-1;
		if(directionY==1 && y >= max_Y+Oy) directionY=-1;
		if(directionX==-1 && x <= Ox)	directionX=1;
		if(directionY==-1 && y <= Oy) 	directionY=1;
		
		x+= directionX*speed; y+=directionY*speed;
		}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(c);
		g.fillOval(x,y,rayon,rayon);
	}
	public boolean checkCollision(Balle b) {
		int x1= x, y1= y; int x2= b.getPosX(), y2= b.getPosY();
		int dx= Math.abs( x1-x2 ); int dy= Math.abs( y1-y2 );
		int dist= dx*dx + dy*dy;
		int r= rayon/2 + b.getRayon()/2;
		return (dist <= r*r);
	}
	public void setPosX(int x) {	this.x= x;	}
	public void setPosY(int y) {	this.y= y;	}
	public int getPosX() { return x; }
	public int getPosY() { return y; }
	public int getRayon() { return rayon; }
	
}
