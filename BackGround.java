package CombatGame;

import java.awt.*;

public class BackGround extends Canvas{
    Color color;

    private int imgW, imgH;

    private int[][][] Frameposition = new int[2][2][6];
    //PlayerNum, XorY, Position

    private int[][][] HPposition = new int[2][2][4];
    //PlayerNum, XorY, Position

    private int[][][] Guardposition = new int [2][2][4];
    //Playernum, XorY, Position

    private int per;

    BackGround(int imgW, int imgH){
        this.imgW = imgW; this.imgH = imgH;
        //(50,50)(10,120)(90,120)(101,100)(551,100)(580,50)
        Frameposition[0][0] = new int[] {50, 10, 90, 101, 551, 580};
        Frameposition[0][1] = new int[] {50, 120, 120, 100, 100, 50};

        for(int i = 0;i < 6;i++){
            Frameposition[1][0][i] = imgW - Frameposition[0][0][i];
            Frameposition[1][1][i] = Frameposition[0][1][i];
        }
    }

    public void draw(Graphics g, int p1hp, int p2hp, int p1Gstr, int p2Gstr){
        //Frame
        g.setColor(new Color(220, 190, 80));
        g.fillPolygon(Frameposition[0][0], Frameposition[0][1], 6);
        g.fillPolygon(Frameposition[1][0], Frameposition[1][1], 6);

        //HP
        //maxHP(P1) : (128,55)(113,80)(558,80)(572,55)

        g.setColor(Color.RED);
        g.fillPolygon(new int[] {128, 113, 558, 572}, new int[] {55, 80, 80, 55}, 4);
        g.fillPolygon(new int[] {imgW-128,imgW-128,imgW-558,imgW-572}, new int[] {55, 80, 80, 55}, 4);

        per = (int)(445 * p1hp / 100);
        HPposition[0][0] = new int[] {128, 113, 113 + per, 128 + per};
        HPposition[0][1] = new int[] {55, 80, 80, 55};
        per = (int)(445 * p2hp / 100);
        HPposition[1][0] = new int[] {imgW - 128,imgW - 113, imgW - 113 - per, imgW - 128 - per};
        HPposition[1][1] = new int[] {55, 80, 80, 55};

        g.setColor(Color.GREEN);
        g.fillPolygon(HPposition[0][0], HPposition[0][1], 4);
        g.fillPolygon(HPposition[1][0], HPposition[1][1], 4);

        //Guardstr
        //maxGuardstr(P1) : (110,85)(104,95)(549,95)(555,85)
        g.setColor(Color.RED);
        g.fillPolygon(new int[]{110, 104,549, 555}, new int[] {85, 95, 95, 85}, 4);
        g.fillPolygon(new int[] {imgW-110, imgW-104, imgW-549, imgW-555}, new int[] {85, 95, 95, 85}, 4);

        per = (int)(445 * p1Gstr / 100);
        Guardposition[0][0] = new int[] {110, 104, 104+per, 110+per};
        Guardposition[0][1] = new int[] {85, 95, 95, 85};
        per = (int)(445 * p2Gstr / 100);
        Guardposition[1][0] = new int[] {imgW-110, imgW-104, imgW-104-per, imgW-110-per};
        Guardposition[1][1] = new int[] {85, 95, 95, 85};

        g.setColor(Color.CYAN);
        g.fillPolygon(Guardposition[0][0], Guardposition[0][1], 4);
        g.fillPolygon(Guardposition[1][0], Guardposition[1][1], 4);
    }
}