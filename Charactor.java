package CombatGame;

import java.awt.*;

public class Charactor extends Object{

    Collision Bcolli;
    Collision[] Acolli = new Collision[2];
    Collision Lcolli;
    //Guard, Head, Body, LeftLeg, RightLeg, LeftArm, RightArm

    boolean Rflag,Lflag,Jflag,Dflag,Pflag,Kflag,Gflag,Sflag,Tflag = false;
    //Move Right,Left,Jump,Down(Squat),Atack,HardAtack,Guard,Spring,Throw
    boolean TflagHold, PflagHold, KflagHold = false;

    int ArmStatus = 0;
    //0:Aarms 1:1Arm 2:NoArm
    int hp;
    int PunchDelaytime, KickDelaytime, ThrowDelaytime= 0;
    int speed;
    int JH, JP;
    boolean fall = false;
    int GuardPower = 0;
    int Guardstr = 100;
    boolean Gbreak = false;
    int direction; //-1:Left 1:Right

    int[] theta = new int[] {0, 0};

    Color color = Color.RED;
    Image img;
    Image imgtest;

    int i, j, k = 0;

    Charactor() {
        hp = 100;
        w = 40;
        h = 160;    //w : harf, h : all
        speed = 20;
        JH = 300;
        JP = 36;
        Bcolli = new Collision(w*2, h/2);
        Acolli[0] = new Collision(w, w);
        Acolli[1] = new Collision(w, w);
        Acolli[0].atk = Acolli[1].atk = 10;
        Lcolli = new Collision(w*2, h/2-w/2);
        Lcolli.atk = 15;
        //imgtest = getToolkit().getImage("image/Punch.png");
    }
    
    void Move(int imgW, int imgH){

        if(Gbreak && Guardstr > 40){
            Gbreak = false;
        }else if(Guardstr == 0 || Gbreak){
            Rflag = false;
            Lflag = false;
            Jflag = false;
            Dflag = false;
            Pflag = false;
            Kflag = false;
            Gflag = false;
            Sflag = false;
            Tflag = false;

            GuardPower = 0;
            
            Gbreak = true;
        }
        JP = 20;

        if(!Sflag && !Dflag){
            speed = 15;
        }
        if(Sflag && !Dflag){
            speed = 30;
        }
        if(Dflag && !Sflag){
            speed = 10;
        }

        if(ArmStatus == 1){
            speed = 30;
            JP = 46;
        }
        if(ArmStatus == 2){
            speed *= 2;
            JP = 56;
        }

        if(Gflag && PunchDelaytime == 0 && KickDelaytime == 0){
            speed = 0;
            GuardPower = 10;
            if(Guardstr > 0){
                Guardstr--;
            }
        }
        if(!Gflag && !Pflag && !Kflag && Guardstr < 300){
            Guardstr += 3;
            GuardPower = 0;
            if(Guardstr > 100){
                Guardstr = 100;
            }
        }

        if(Rflag && !Lflag && x < imgW - w && !Gflag){
            x += speed;
            direction = 1;
        }
        if(Lflag && !Rflag && x > w && !Gflag){
            x -= speed;
            direction = -1;
        }
        if(y == imgH){
            fall = false;
        }
        if(Jflag && y > imgH - JH && !fall && !Gflag){
            y -= JP;
            i++;
            if(y <= imgH - JH){
                fall = true;
            }
        }
        if(y < imgH){
            if(!Jflag || fall){
                y += JP;
                if(y >= imgH){
                    y = imgH;
                    fall = false;
                }
            }
        }
    }

    void draw(Graphics g){
        g.setColor(color);
        //g.drawImage(imgtest, 640, 360, this);
        if(!Pflag && !Kflag){
            w = 40;
            h = 160;
            if(Gflag){
                g.drawOval(x-w/2, y-h, w, w);
                g.drawLine(x, y-h+w, x, (y-h+w+y)/2);

                if(ArmStatus < 2){
                    g.drawLine(x, y-h+w, x+w*direction, y-h/2);
                    g.drawLine(x+w*direction, y-h/2, x+w*direction, y-h+w);
                    if(ArmStatus < 1){
                        g.drawLine(x, y-h+w, x+(w-10)*direction, y-h/2);
                        g.drawLine(x+(w-10)*direction, y-h/2, x+(w-10)*direction, y-h+w);
                    }
                }

                g.drawLine(x, (y-h+w+y)/2, x-w*direction, y);
                g.drawLine(x, (y-h+w+y)/2, x+w/2*direction, (y-h+w+y)/2);
                g.drawLine(x+w/2*direction, (y-h+w+y)/2, x+w/2*direction, y);
            }else if(Dflag){
                w = 20;
                h = 80;
                g.drawOval(x-w/2, y-h, w, w);
                g.drawLine(x, y-h+w, x, (y-h+w+y)/2);

                if(ArmStatus < 2){
                    g.drawLine(x, y-h+w, x-w/2*direction, (y-h+w+y)/2);
                    g.drawLine(x-w/2*direction, (y-h+w+y)/2, x+w/2*direction, y-h/2);
                    if(ArmStatus < 1){
                        g.drawLine(x,y-h+w, x+w/4*direction, y-h/2);
                        g.drawLine(x+w/4*direction, y-h/2, x+w*direction, y-h+w);
                    }
                }
                

                g.drawLine(x, (y-h+w+y)/2, x-w*direction, y);
                g.drawLine(x, (y-h+w+y)/2, x+w/2*direction, y);                   
            }
            else if((Lflag && !Rflag) || (!Lflag && Rflag)){
                if(!Sflag){
                    g.drawOval(x-(w/2), y-h,w, w);
                    g.drawLine(x, y-h+w, x, (y-h+w+y)/2);
    
                    if(ArmStatus < 2){
                        g.drawLine(x, y-h+w, x-w*direction, y-h+w+w);
                        if(ArmStatus < 1){
                            g.drawLine(x, y-h+w, x-w*direction, (y-h+w+y)/2);
                        }
                    }
    
                    g.drawLine(x, (y-h+w+y)/2, x-x/5%(w+1), y);
                    g.drawLine(x, (y-h+w+y)/2, x+x/5%(w+1), y);
                }else if(Sflag){
                    g.drawOval(x-(w/2), y-h,w, w);
                    g.drawLine(x, y-h+w, x, (y-h+w+y)/2);

                    if(ArmStatus < 2){
                    g.drawLine(x, y-h+w, x-w*direction, y-h+w+w);
                        if(ArmStatus < 1){    
                            g.drawLine(x, y-h+w, x-w*direction, y-h+w+w/2);
                        }
                    }
                    
                    g.drawLine(x, (y-h+w+y)/2, x+w/2*direction, (y-h+w+y)/2);
                    g.drawLine(x+w/2*direction, (y-h+w+y)/2, x+w/2*direction, y-h/4);
                    g.drawLine(x, (y-h+w+y)/2, x-w*direction, y);
                }
            }else{
                g.drawOval(x-w/2, y-h, w, w);
                g.drawLine(x, y-h+w, x, (y-h+w+y)/2);

                if(ArmStatus < 2){
                    g.drawLine(x, y-h+w, x-w/2*direction, (y-h+w+y)/2);
                    g.drawLine(x-w/2*direction, (y-h+w+y)/2, x+w/2*direction, y-h/2);
                    if(ArmStatus < 1){
                        g.drawLine(x,y-h+w, x+w/4*direction, y-h/2);
                        g.drawLine(x+w/4*direction, y-h/2, x+w*direction, y-h+w);
                    }
                }
                

                g.drawLine(x, (y-h+w+y)/2, x-w*direction, y);
                g.drawLine(x, (y-h+w+y)/2, x+w/2*direction, y);

                //g.setColor(color);
                //g.fillRect(x - w, y-h, w*2, h);
                //g.setColor(Color.BLACK);
                //g.drawOval(x-(w/2), y-h,w, w);
                //g.drawLine(x, y-h+w, x, (y-h+w+y)/2);                    
                //g.drawLine(x, y-h+w, x-w, y-h+w+w);
                //g.drawLine(x, y-h+w, x+w, y-h+w+w);
                //g.drawLine(x, (y-h+w+y)/2, x-w, y);
                //g.drawLine(x, (y-h+w+y)/2, x+w, y);
            }
        }else if(Pflag && !Kflag && !Gflag){
            g.drawImage(img, x+w*direction, y-h, w*direction, h, this);

            g.drawOval(x-w/2, y-h, w, w);
            g.drawLine(x, y-h+w, x, (y-h+w+y)/2);

            if(ArmStatus < 2){
                g.drawLine(x, y-h+w, x+w*2*direction, y-h+w+10);
                if(ArmStatus < 1){
                    g.drawLine(x, y-h+w, x-w/2*direction, y-h+w+w);
                    g.drawLine(x-w/2*direction, y-h+w+w, x+w/2*direction, y-h+w+w);
                }
            }

            g.drawLine(x, (y-h+w+y)/2, x-w*direction, y);
            g.drawLine(x, (y-h+w+y)/2, x+w/2*direction, y-h/4);
            g.drawLine(x+w/2*direction, y-h/4, x+w*3/4*direction, y);
        }else if(!Pflag && Kflag && !Gflag){
            g.drawImage(img, x+w*direction, y-h, w*direction, h, this);

            g.drawOval(x-w/2, y-h, w, w);
            g.drawLine(x, y-h+w, x, (y-h+w+y)/2);

            if(ArmStatus < 2){
                g.drawLine(x, y-h+w, x-w*direction, y-h+w+w/2);
                g.drawLine(x-w*direction, y-h+w+w/2, x+w/2*direction, y-h+w+w/2+5);
                if(ArmStatus < 1){
                    g.drawLine(x, y-h+w, x+w*3/2*direction, y-h+w+w/4);
                }
            }

            g.drawLine(x, (y-h+w+y)/2, x-w*direction, y-w);
            g.drawLine(x-w*direction, y-w, x, y-w/2);
            g.drawLine(x, (y-h+w+y)/2, x+w*2*direction, y-w-10);
        }
        if(ArmStatus > 0){
            for(int l = 0;l<ArmStatus;l++){
                g.drawLine(Acolli[k-l].x + (int)(Acolli[k-l].w*Math.sin(theta[k-l])), Acolli[k-l].y - (int)(Acolli[k-l].h*Math.cos(theta[k-l])), 
                Acolli[k-l].x - (int)(Acolli[k-l].w*Math.sin(theta[k-l])), Acolli[k-l].y + (int)(Acolli[k-l].h*Math.cos(theta[k-l])));
                theta[k-l] += 5 * Math.PI;
            }    
        }
    }

    void Attack(Charactor enemy){
        if(ArmStatus < 2){
            if(this.PunchDelaytime == 0){
                if(this.Pflag && !this.Kflag && !this.Gflag && this.ArmStatus != 2){
                    this.img = getToolkit().getImage("image/Punch.png");
                    PunchDelaytime = 5;
                    if(this.Acolli[1-k].CollisionCheck(enemy.Bcolli) && this.Acolli[1-k].atk - enemy.GuardPower > 0){
                        enemy.hp -= this.Acolli[1-k].atk - enemy.GuardPower;
                    }
                }
            }else{
                PunchDelaytime--;
            }
        }

        if(this.KickDelaytime == 0){
            if(this.Kflag && !this.Pflag && !this.Gflag){
                this.img = getToolkit().getImage("image/Kick.png");
                KickDelaytime = 15;
                if(this.Lcolli.CollisionCheck(enemy.Bcolli) && this.Lcolli.atk - enemy.GuardPower > 0){
                    enemy.hp -= this.Lcolli.atk - enemy.GuardPower;
                }
            }
        }else{
            KickDelaytime--;
        }    
    }

    void Throw(Charactor enemy){
        if(ThrowDelaytime <= 0){
            if(Tflag && !Gflag && ArmStatus < 2){
                ArmStatus++;
                ThrowDelaytime = 5;
                //System.out.println("Throw!");
            }
        }else{
            ThrowDelaytime--;
        }

        if(ArmStatus == 1 && j == 0){
            Acolli[0].w = 40;
            Acolli[0].h = 40;
            this.Acolli[0].dx = (10 + speed) * direction;
            this.Acolli[0].dy = 5;
            this.Acolli[0].x = x + 2 * this.Acolli[0].w * direction;
            j++;

            Acolli[1].w = w/2;
        }

        if(ArmStatus == 2 && k == 0){
            Acolli[1].w = 40;
            Acolli[1].h = 40;
            this.Acolli[1].dx = (10 + speed) * direction;
            this.Acolli[1].dy = 5;
            this.Acolli[1].x = x + 2* this.Acolli[1].w * direction;

            k++;
        }

        for(int l = 0;l < ArmStatus;l++){
            if(this.Acolli[k-l].CollisionCheck(enemy.Bcolli)){
                if(Acolli[k-l].atk - enemy.GuardPower > 0){
                    enemy.hp -= Acolli[k-l].atk - enemy.GuardPower;
                }
                Acolli[k-l].atk = 0;
                Acolli[k-l].dx *= -1;
                Acolli[k-l].dy = 10;
            }
        }
    }

    void MoveCollision(int imgW, int imgH){

        Bcolli.w = this.w;
        Bcolli.h = this.h/2;
        Bcolli.x = this.x;
        Bcolli.y = this.y - this.h/2;

        if(ArmStatus == 0){
            Acolli[0].w = this.w;
            Acolli[0].h = this.h/4;
            Acolli[0].x = this.x + Acolli[0].w*direction;
            Acolli[0].y = this.y - Acolli[0].h*3;

            Acolli[1].w = this.w;
            Acolli[1].h = this.h/4;
            Acolli[1].x = this.x + Acolli[1].w*direction;
            Acolli[1].y = this.y - Acolli[1].h*3;
        }else if(ArmStatus == 1){
            if(Acolli[k].y < imgH - Acolli[k].h){
                Acolli[k].x += Acolli[k].dx;
                Acolli[k].y += Acolli[k].dy;
            }else{
                Acolli[k].y = imgH - Acolli[k].h;
                Acolli[k].dx = 0;
                Acolli[k].dy = 0;
                theta[k] = 0;
                Acolli[k].atk = 0;
            }
            if(Acolli[k].x <= w || Acolli[k].x >= imgW - w){
                Acolli[k].dx *= -1;
                Acolli[k].dy = 100;
            }

            Acolli[1-k].x = this.x + Acolli[1-k].w*direction;
            Acolli[1-k].y = this.y - Acolli[1-k].h/2;
            Acolli[1-k].atk = 5;

        }else if(ArmStatus == 2){
            for(int a = 0;a < 2;a++){
                if(Acolli[a].y < imgH - Acolli[a].h){
                    Acolli[a].x += Acolli[a].dx;
                    Acolli[a].y += Acolli[a].dy;
                }else{
                    Acolli[a].y = imgH - Acolli[a].h;
                    Acolli[a].dx = 0;
                    Acolli[a].dy = 0;
                    Acolli[a].atk = 0;
                    theta[a] = 0;
                }
                if(Acolli[a].x <= w || Acolli[a].x >= imgW - w){
                    Acolli[a].dx *= -1;
                    Acolli[a].dy = 100;
                }
            }
        }

        Lcolli.w = this.w;
        Lcolli.h = this.h/4 - this.w/4;
        Lcolli.x = this.x + this.w*direction;
        Lcolli.y = y - this.h/4 + this.w/4;

        for(i = 0;i < ArmStatus;i++){
            if(Acolli[k-i].CollisionCheck(Bcolli)){
                ArmStatus--;
                Acolli[k-i].atk = 10;
                if(k-i == 0 && j == 1){
                    j = 0;
                }
                if(k-i == 1 && k == 1){
                    k = 0;
                }
            }
        }
    }

    void drawCollision(Graphics g){
        Bcolli.draw(g);
        Acolli[0].draw(g);
        Acolli[1].draw(g);
        Lcolli.draw(g);
    }
}