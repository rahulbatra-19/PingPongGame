import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Score extends Rectangle{
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int player1;
    int player2;
    Score(int GAME_WIDTH, int GAME_HEIGHT){
        Score.GAME_HEIGHT = GAME_HEIGHT;
        Score.GAME_WIDTH = GAME_WIDTH;
    }
    public void draw(Graphics g){
        g.setColor(Color.white);
        g.setFont(new Font("Consolas",Font.PLAIN,60));
        g.drawLine((GAME_WIDTH/2), 0, GAME_WIDTH/2, GAME_HEIGHT);
        g.drawLine(25,GAME_HEIGHT/2, GAME_WIDTH-25, GAME_HEIGHT/2);
        g.setColor(new Color(0,255,0));
        g.fillRect(0, 0,25 , GAME_HEIGHT);
        g.fillRect(GAME_WIDTH-25, 0,GAME_WIDTH ,GAME_HEIGHT);
        g.setColor(Color.white);
        g.drawString(String.valueOf(player1/10)+String.valueOf(player1%10), GAME_WIDTH/2-85, 50 );
        g.drawString(String.valueOf(player2/10) +String.valueOf(player2%10), GAME_WIDTH/2+20, 50 );



    }
}
