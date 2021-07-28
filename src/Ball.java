import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Ball extends Rectangle{
    Random random;
    int xVelocity;
    int yVelocity;
    int initialSpeed = 2;
    Ball(int x, int y, int width, int height){
        super(x,y,width,height);
        random = new Random();
        int randomxDirection = random.nextInt(2);
        if(randomxDirection == 0){
            randomxDirection--;
        }
        setXdirection(randomxDirection*initialSpeed);
        int randomyDirection = random.nextInt(2);
        if(randomyDirection == 0){
            randomyDirection--;
        }
        setYdirection(randomyDirection*initialSpeed);

    }
    public void setXdirection(int randomxDirection){
        xVelocity = randomxDirection;
    }
    public void setYdirection(int randomyDirection){
        yVelocity = randomyDirection;
    }
    public void move(){
        x += xVelocity;
        y += yVelocity;
    }
    public void draw(Graphics g){
        g.setColor(Color.white);
        g.fillOval(x, y, width, height);
    }
}
