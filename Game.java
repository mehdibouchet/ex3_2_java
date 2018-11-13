package tp3_progConc;

import java.util.ArrayList;
import java.util.List;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Game extends Container implements ActionListener{
	private Chronometre timer;
	private Thread timer_th;
	private boolean state;
	private JButton stop_b, plus_b, moins_b;
	private List<Balle> b;
	private final int MAX_BALLE=10;
	private double gameWidth, gameHeight;
	public JPanel div_btns, gameContainer;
	private int width, score;
	private int i;
	public Game() {
		i=0;
		setPreferredSize(new Dimension(800, 800));
		width= (int) getPreferredSize().getWidth();
		this.setLayout(new BorderLayout());

		timer= new Chronometre(this, 0, 0);	timer.setPreferredSize(new Dimension(width, 50));
		timer_th= new Thread( timer );	timer_th.setDaemon(true);
		
		state=true;	score= 0;
		b= new ArrayList<Balle>();
		
		
		stop_b= new JButton("START");	stop_b.addActionListener(this);
		plus_b= new JButton("+");		plus_b.addActionListener(this); plus_b.setEnabled(false);
		moins_b=new JButton("-");		moins_b.addActionListener(this);moins_b.setEnabled(false);
		
		
		div_btns= new JPanel();
		gameContainer= new JPanel();
		
		gameContainer.setBackground(Color.WHITE);
		div_btns.setLayout(new FlowLayout());
		div_btns.add(stop_b); div_btns.add(plus_b); div_btns.add(moins_b);
		
		add(timer, BorderLayout.NORTH);
		add(div_btns, BorderLayout.SOUTH); 
		add(gameContainer, BorderLayout.CENTER); 
		
		
	}
	public void setupGame(){
		state= true;
		gameWidth= gameContainer.getWidth();
		gameHeight= gameContainer.getHeight();
		addBalle();
		timer_th.start();

		plus_b.setEnabled(true); moins_b.setEnabled(true);
		stop_b.setText("STOP");
	}
	
	public void stop() throws InterruptedException {
		plus_b.setEnabled(false); moins_b.setEnabled(false);
		stop_b.setText("START");
		state= false;
	}
	
	public void start() throws InterruptedException {	
		state= true;
		plus_b.setEnabled(true); moins_b.setEnabled(true);
		stop_b.setText("STOP");
	}
	
	public void addBalle() {
		if(b.size()< MAX_BALLE){ 
			Balle ball= new Balle(
				gameContainer.getX(), gameContainer.getY(),
				getGameHeight(), getGameWidth());
			b.add( ball ); repaint();
		}
		if(b.size()==1) moins_b.setEnabled(true);
		if(b.size()== MAX_BALLE-1) plus_b.setEnabled(false);

	}
	public void removeBalle() {
		if(b.size()>0) 
			{ int i= (int) (Math.random()*b.size()); b.remove( i ); repaint(); }
		if(b.size()==0) moins_b.setEnabled(false);
		if(b.size()< MAX_BALLE-1) plus_b.setEnabled(true);
	}
	public void removeBalle(Balle b1) {
		b.remove(b1); repaint();
		if(b.size()==0) moins_b.setEnabled(false);
		if(b.size()< MAX_BALLE-1) plus_b.setEnabled(true);
	}
	public void moveBalles() {
		for(int i=0; i<b.size(); i++)
			b.get(i).moveBalle();
	}
	public void drawBalles(Graphics g) {
		for(int i=0; i<b.size(); i++)
			b.get(i).paintComponent(g);
	}
	public void checkCollision() {
		for(int i=0; i<b.size(); i++) {
			for(int j=i+1; j<b.size(); j++) {
				Balle b1= b.get(i); Balle b2= b.get(j);
				if( b.get(i).checkCollision(b.get(j))) {
					removeBalle(b1); removeBalle(b2); score++;
				}
			}
		}
	}
	
	public boolean getState() { return state; }
	public int getScore() { return score; }
	public double getGameWidth() { return gameWidth; }
	public double getGameHeight() { return gameHeight; }

	public void paint(Graphics g) {
		super.paint(g);
		if(state && i==1) {
			moveBalles(); 
			checkCollision();
		}
		drawBalles(g);
		i= (i+1)%2;
	}

	public void actionPerformed(ActionEvent arg) {
		try {	
			if(arg.getSource() == stop_b) {
				if(!state)	start();	
				else 		stop();
			}
		} catch (InterruptedException e) {}
		if(arg.getSource() == plus_b)  { addBalle(); }
		if(arg.getSource() == moins_b) { removeBalle(); }
	}
}
