package tp3_progConc;

import javax.swing.JFrame;

public class UI_Thread extends JFrame implements Runnable{
	private Game game;
	public UI_Thread() {
		this.setTitle("Ma fenetre");
		this.setSize(800, 800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		game= new Game();
		this.getContentPane().add(game);
		this.setVisible(true);
		game.setupGame();
	}
	
	public void run() {	while(true) repaint();	}
	public static void main(String[] args) {
		UI_Thread ui= new UI_Thread();
		Thread ui_th= new Thread(ui);	ui_th.start();
	}
}
