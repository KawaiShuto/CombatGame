package CombatGame;

import java.awt.*;
import java.awt.event.*;

public class GameMaster extends Canvas implements KeyListener{

    Image buf;
    Graphics buf_gc;
    Dimension d;
    private int imgW, imgH;
    private int mode = 0;

    Charactor p1, p2;
    BackGround bg;

    private double p2atk, p2move;

    private int i, j;

    GameMaster(int imgW, int imgH){
        this.imgW = imgW;
        this.imgH = imgH;
        setSize(imgW, imgH);

        addKeyListener(this);

        setPosition();

        bg = new BackGround(imgW, imgH);
    }

    void setPosition(){
        p1 = new Charactor();
        p2 = new Charactor();
        p1.x = imgW / 4;
        p1.y = imgH;
        p1.direction = 1;
        p2.x = imgW * 3 / 4;
        p2.y = imgH;
        p2.direction = -1;
        p2.color = Color.BLUE;
    }

    public void addNotify(){
        super.addNotify();
        buf = createImage(imgW, imgH);
        buf_gc = buf.getGraphics();
    }

    public void paint(Graphics g){
        buf_gc.setColor(Color.WHITE);
        buf_gc.fillRect(0, 0, imgW, imgH);
        switch(mode){
            case -1:
                setPosition();
                mode++;
                break;
            case 1:
                buf_gc.setColor(Color.RED);
                buf_gc.drawString("== PRESS SPACE KEY TO CONTINUE ==", imgW/2-100, imgH/2);
                if(p1.hp <= 0){
                    buf_gc.drawString("YOU LOSE", imgW/2-50, imgH/2+100);
                }else{
                    buf_gc.drawString("YOU WIN !!!", imgW/2-80, imgH/2 + 100);
                }
                if(p1.Gflag){
                    mode = -1;
                }
                break;
            default:
                bg.draw(buf_gc, p1.hp, p2.hp, p1.Guardstr, p2.Guardstr);

                p1.Move(imgW, imgH);
                p1.Throw(p2);
                p1.Attack(p2);
                p1.MoveCollision(imgW, imgH);
                //p1.drawCollision(buf_gc);
                p1.draw(buf_gc);

                //EnemyMove();
                p2.Move(imgW, imgH);
                p2.Throw(p1);
                p2.Attack(p1);
                p2.MoveCollision(imgW, imgH);
                //p2.drawCollision(buf_gc);
                p2.draw(buf_gc);

                if(p1.hp <= 0 || p2.hp <= 0){
                    mode++;
                }

                //System.out.print(p1.ArmStatus + " , " + p2.hp + " " + p1.img + " " + p1.PunchDelaytime + " " + p1.KickDelaytime + " " + p1.ThrowDelaytime + " " + p1.Acolli[0].atk + " " + p1.Acolli[1].atk + " ");
                //System.out.println(p1.Tflag);
                break;
        }
        g.drawImage(buf, 0, 0, this);
    }

    public void update(Graphics gc){
        paint(gc);
    }

    public void keyTyped(KeyEvent ke) {
    }

    public void keyPressed(KeyEvent ke) {
        int cd = ke.getKeyCode();
        switch(cd){
            case KeyEvent.VK_SPACE:
                p1.Gflag = true;
                p1.fall = true;
                break;
            case KeyEvent.VK_Q:
                if(!p1.TflagHold){
                    p1.Tflag = true;
                }else{
                    p1.Tflag = false;
                }
                p1.TflagHold = true;
                break;
            case KeyEvent.VK_A:
                if(!p1.PflagHold){
                    p1.Pflag = true;
                }else{
                    p1.Pflag = false;
                }
                p1.PflagHold = true;
                break;
            case KeyEvent.VK_D:
                if(!p1.KflagHold){
                    p1.Kflag = true;
                }else{
                    p1.Kflag = false;
                }
                p1.KflagHold = true;
                break;
            case KeyEvent.VK_LEFT:
                p1.Lflag = true;
                break;
            case KeyEvent.VK_RIGHT:
                p1.Rflag = true;
                break;
            case KeyEvent.VK_UP:
                p1.Jflag = true;
                break;
            case KeyEvent.VK_DOWN:
                if(!p1.Sflag){
                    p1.Dflag = true;
                }
                break;
            case KeyEvent.VK_SHIFT:
                if(!p1.Dflag){
                    p1.Sflag = true;
                }
                break;

        }
    }

    public void keyReleased(KeyEvent ke) {
        int cd = ke.getKeyCode();
        switch(cd){
            case KeyEvent.VK_SPACE:
                p1.Gflag = false;
                break;
            case KeyEvent.VK_Q:
                p1.TflagHold = false;
                p1.Tflag = false;
                break;
            case KeyEvent.VK_A:
                p1.PflagHold = false;
                p1.Pflag = false;
                break;
            case KeyEvent.VK_D:
                p1.KflagHold = false;
                p1.Kflag = false;
                break;
            case KeyEvent.VK_LEFT:
                p1.Lflag = false;
                break;
            case KeyEvent.VK_RIGHT:
                p1.Rflag = false;
                break;
            case KeyEvent.VK_UP:
                p1.Jflag = false;
                p1.fall = true;
                break;
            case KeyEvent.VK_DOWN:
                p1.Dflag = false;
                break;
            case KeyEvent.VK_SHIFT:
                p1.Sflag = false;
                break;
        }
    }
    
    void EnemyMove(){
        p2atk = Math.random();
        p2move = Math.random();

        if(p2atk <= 0.1){
            p2.Pflag = false;
            p2.Kflag = false;
            p2.Tflag = false;
        }else if(p2atk <= 0.2){
            p2.Gflag = false;
        }else if(p2atk <= 0.3){
            p2.Gflag = true;
        }else if(p2atk <= 0.6){
            p2.Pflag = true;
            p2.Kflag = false;
            p2.Tflag = false;
            p2.Gflag = false;
        }else if(p2atk <= 0.9){
            p2.Pflag = false;
            p2.Kflag = true;
            p2.Tflag = false;
            p2.Gflag = false;
        }else if(p2atk <= 1.0){
            p2.Pflag = false;
            p2.Kflag = false;
            p2.Tflag = true;
            p2.Gflag = false;
        }

        if(p2move <= 0.4 && p2.ArmStatus > 0){
            if(p2.j == 1 && p2.Acolli[0].x - p2.x < 0){
                p2.Lflag = true;
                p2.Rflag = false;
            }else if(p2.j == 1 && p2.Acolli[0].x - p2.x > 0){
                p2.Lflag = false;
                p2.Rflag = true;
            }else if(p2.k == 1 && p2.Acolli[1].x - p2.x < 0){
                p2.Lflag = true;
                p2.Rflag = false;
            }else if(p2.k == 1 && p2.Acolli[1].x - p2.x > 0){
                p2.Lflag = false;
                p2.Rflag = true;
            }
        }else if(p2move <= 0.7){
            if(p1.x - p2.x < 0){
                p2.Lflag = true;
                p2.Rflag = false;
            }else{
                p2.Lflag = false;
                p2.Rflag = true;
            }
        }else if(p2move <= 0.9){
            if(p1.x - p2.x < 0){
                p2.Lflag = false;
                p2.Rflag = true;
            }else{
                p2.Lflag = true;
                p2.Rflag = false;
            }
        }else{
            p2.Lflag = false;
            p2.Rflag = false;
        }
    }
}
