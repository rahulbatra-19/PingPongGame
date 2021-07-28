import java.awt.*;
import java.awt.event.*;
import java.security.Key;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{
    
    static final int GAME_WIDTH =1000;
    static final int GAME_HEIGHT =(int)(GAME_WIDTH*(0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH  = 25;
    static final int PADDLE_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    boolean GameOn =true;
    Ball ball;
    Score score;
    int won;

    GamePanel(){
        newPAddles();
        newBall();
        score = new Score(GAME_WIDTH,GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);
        gameThread = new Thread(this);
        gameThread.start();

    }
    public void newBall(){
        // optional
        random = new Random();
        ball = new Ball((GAME_WIDTH/2)- (BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT- BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);
        //  Compulsory 
        // ball = new Ball((GAME_WIDTH/2)- (BALL_DIAMETER/2),(GAME_HEIGHT/2)- (BALL_DIAMETER/2),BALL_DIAMETER,BALL_DIAMETER);
    }
    public void newPAddles(){
        paddle1 = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
        paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);



    }
    public void paint(Graphics g){
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0 , 0, this);
    }
    public void draw(Graphics g){
        if(GameOn){
        ball.draw(g);
        score.draw(g);
        paddle1.draw(g);
        paddle2.draw(g);
        }
        else{
            gameOver(g);
        }

    }
    public void move(){
        paddle1.move();
        paddle2.move();
        ball.move();
    }
    public void checkCollision(){
        // stops paddle at window edges 
        if(paddle1.y<=0)
        {
            paddle1.y=0;
        }
        if(paddle1.y>= (GAME_HEIGHT-PADDLE_HEIGHT))
        {
            paddle1.y=GAME_HEIGHT-PADDLE_HEIGHT;
        }
        if(paddle2.y<=0)
        {
            paddle2.y=0;
        }
        if(paddle2.y>= (GAME_HEIGHT-PADDLE_HEIGHT))
        {
            paddle2.y=GAME_HEIGHT-PADDLE_HEIGHT;
        }
        // bounce ball off top and bottom window edges
        if(ball.y <=0)
        {
            ball.setYdirection(-ball.yVelocity);
        }
        if(ball.y>= GAME_HEIGHT-BALL_DIAMETER)
        {
            ball.setYdirection(-ball.yVelocity);
        }
        // this bounces ball of paddles
        if(ball.intersects(paddle1))
        {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; // this is optional for more difficulty
            if(ball.yVelocity>0){
                ball.yVelocity++ ; // optional
            }
            else
            ball.yVelocity--;
            ball.setXdirection(ball.xVelocity);
            ball.setYdirection(ball.yVelocity);
        }
        if(ball.intersects(paddle2)){
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; // this is optional for more difficulty
            if(ball.yVelocity<0){
                ball.yVelocity++ ; // optional
            }
            else
            ball.yVelocity--;
            ball.setXdirection(-ball.xVelocity);
            ball.setYdirection(ball.yVelocity);
        }
    
        //  give a player 1 point and creates new paddles and ball
        
            if(ball.x<=0){
                score.player2++;
                if(score.player2<=10){
                newPAddles();
                newBall();
                }
                else
                {
                    won = 2;
                    GameOn = false;
                }
                // System.out.println("Player 2: " +score.player2);
            }
            if(ball.x >=  GAME_WIDTH - BALL_DIAMETER){
                score.player1++;
                if(score.player1<=10){
                newPAddles();
                newBall();
                }
                else{
                    won =1;
                    GameOn = false;
                }
                // System.out.println("Player 1: " +score.player1);
            }

        
    }
    public void gameOver(Graphics g){
        // Game over text
        g.setColor(Color.black);
        g.fillRect(0, 0, GAME_WIDTH,GAME_HEIGHT);
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (GAME_WIDTH - metrics1.stringWidth("Game Over"))/2, GAME_HEIGHT/2);

        // Who won
        if(won==1){
            g.setColor(new Color(112,128,144));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("Player " + won + " Won!!" ,(GAME_WIDTH-metrics2.stringWidth("Player "+ won + " Won!!"))/2,g.getFont().getSize());
        }
        else{
            g.setColor(new Color(139,69,19));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("Player " + won + " Won!!" ,(GAME_WIDTH-metrics2.stringWidth("Player "+ won + " Won!!"))/2,g.getFont().getSize());
        }
    }
    public void run(){
        // game loop
        long lastTime = System.nanoTime();
        double amountofTicks = 60.0;
        double ns = 1000000000 / amountofTicks;
        double delta =0;
        while(true){
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now ;
            if(delta >=1){
                move();
                checkCollision();
                repaint();
                delta--;
            }
        
        }

    }
    public class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }
        public void keyReleased(KeyEvent e){
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);

        }
    }
}
