package pong;

//Imports to make the game colourful and beautiful
import java.awt.Color;
import java.awt.Graphics;

//Import to make the ball angle reflection random (lets the ball move in 2 dimensions
import java.util.Random;


/**
 *
 * @author mandar
 */
public class PowerUp {
    int type; //type of power up
    int x,y;
    private Pong pong;
    
    int slow;
    
    public int r;
    
    Random rand  = new Random();
    
    
    
    public PowerUp(Pong pong, int kind){
        
        type = kind;
        
        this.pong = pong;
        
        x = rand.nextInt(600);
        y = rand.nextInt(600);
        
    }
    
    //Method to render the powerup as a certain colour depending on which
    //powerup is spawning
    public void render(Graphics g){
        
        if (type == 1){
            g.setColor(Color.red);
            g.fillOval(x, y, 30, 30);
        }
        else if(type == 2){
            g.setColor(Color.BLUE);
            g.fillOval(x, y, 30, 30);
        }
        else{
            g.setColor(Color.MAGENTA);
            g.fillOval(x, y, 30, 30);
        }
    }
    
    //Method to check if the ball comes in contact with the powerup using
    //dimensions of the JPanel
    public int checkCollision(Ball ball, Paddle paddle){
        
        //Slows the correct paddle depending on who hit the ball last
        if(paddle.paddleNumber == 1){
            slow = 1;
        }
        else{
            slow = 2;
        }
        
        if((ball.x >= this.x - 30 && ball.x <= this.x + 30) && (ball.y >= this.y -30 && ball.y <= this.y + 30)){
            
            //sets the number of bounces before the powerup wears off
            ball.numberThatWeNeed = 12;
            
            ball.balls = 12;
            
            if (type == 1){
                r = 1;
            }
            else if(type == 2) {
                r = 2;
            }
            else{
                r = 3;
            }
            return 1; 
        }
        else{
            return 0;
        }  
    }
    
    //Method to move powerup off the board once there is collision
    public void update(Ball ball, Paddle paddle){
        if ((checkCollision(ball, paddle)) == 1){
            x = 900;
            y = 900;  
        }
    }
    
    //Spawns the powerup on the board (moves it back onto the visible part
    //the screen)
    public void spawnPower(){
            x = rand.nextInt(600);
            y = rand.nextInt(600);  
    }
}
