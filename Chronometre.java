package tp3_progConc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Chronometre extends JPanel implements Runnable{
	int t;
	boolean flag,state;
	Font f1,f2;
	Game g;
	public Chronometre(Game g, int t, int score) {
		this.t=0;  this.state= false; flag=false; this.g= g;
		setBackground(Color.GRAY);
		f1= new Font("Helvetica Neue",  Font.PLAIN, 30);
		f2= new Font("Helvetica Neue",  Font.PLAIN, 15);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.setFont(f1);	g.drawString("Score: "+this.g.getScore(), 275, 35);
		g.setFont(f2);	g.drawString("Temps: "+t+"s", 600, 35);
	}	
	public void run() {
		int i=0;
		try {
			while(true) {
				if(g.getState()){
					if(i==0)	t++;
					Thread.sleep(1); i= (i+1)%1000;
				}
				repaint();
			}
		}catch (InterruptedException e){}
	}
}
