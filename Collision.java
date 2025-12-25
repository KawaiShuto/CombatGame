package CombatGame;

import java.awt.*;
public class Collision extends Object{
    int w,h;
    int atk = 0;
    int dx, dy = 0;

    Collision(int Width, int Height){
        w = Width;
        h = Height;
    }

    public boolean CollisionCheck(Collision a){
        if(Math.abs(this.x - a.x) < this.w + a.w && Math.abs(this.y - a.y) < this.h + a.h){
            return true;
        }else{
            return false;
        }
    }

    void draw(Graphics g){
        g.setColor(Color.GREEN);
        g.drawRect(x-w, y-h, w*2, h*2);
    }
}