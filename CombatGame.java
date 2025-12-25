package CombatGame;

import java.awt.*;

public class CombatGame extends Frame implements Runnable{

    Thread th;
    GameMaster gm;
    public static void main(String[] args){
        new CombatGame();
    }

    CombatGame(){
        super("Combat Game");
        int cW = 1280;
        int cH = 700;
        this.setSize(cW+20, cH+60);
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        gm = new GameMaster(cW, cH);
        this.add(gm);
        this.setVisible(true);

        th = new Thread(this);
        th.start();

        requestFocusInWindow();
    }
    
    public void run(){
        try{
            while(true){
                Thread.sleep(20);
                gm.repaint();
            }
        }
        catch (Exception e){
            System.out.println("Exception: " + e);
        }
    }
}
