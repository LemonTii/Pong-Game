package pong;

/**
 *
 * @author mandar
 */

//Imports for graphics of ball and random movement of ball
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball
{
    //Dimensions of the ball 
    public int x, y, width = 25, height = 25;

    //Possible directions the ball can move
    public int motionX, motionY;

    public Random random;

    private Pong pong;
        
    //Base movement speed of the ball
    public int speedb = 3;

    public int amountOfHits;
        
    //Counter used for powerup duration, checking for collision and
    //to set the speed of the ball
    public int balls = -1;
        
    //Additional counter used to check for collision
    public int numberThatWeNeed = -1;
        
    public int time;

    //Constructor for the ball
    public Ball(Pong pong)
    {
        this.pong = pong;
        this.random = new Random();
        spawn();
    }

    //Method that updates all game to check for collisions between the ball
    //and either of the paddles or a powerup
    public void update(Paddle paddle1, Paddle paddle2, PowerUp power, Ball ball)
    {               
        //Condition for the powerup
        if(power.r == 3)
        {    
            if (ball.balls > 0 && ball.balls <=12)
            {
                speedb = 7;
            }
            else if (ball.balls <= 0){
                speedb = 3;
            }
        }
        else    
        {
            speedb = 3;
        }

        //Actual movement of the ball
        this.x += motionX * speedb;
        this.y += motionY * speedb;
        
        //Y-axis movement of the ball to make sure it is always moving
	if (this.y + height - motionY > pong.height || this.y + motionY < 0)
	{	
            if (this.motionY < 0)
            {
                this.y = 0;
                this.motionY = 1 ;

                if (motionY == 0)
                {
                    motionY = 1;    
                }
            }
            else
            {
                this.motionY = -1;
                this.y = pong.height - height;
                        
                if (motionY == 0)
                {
                    motionY = -1;
                }
            }
        }

        //Next two methods cover the ball bouncing off either of the two
        //paddles in the opposite direction if the two hitboxes collide
	if (checkCollision(paddle1, power) == 1)
	{
            this.motionX = 1 + 1;
            this.motionY = -2 + random.nextInt(4);
            
            if (motionY == 0)
            {
                motionY = 1;
            }

            amountOfHits++;
	}
	else if (checkCollision(paddle2, power) == 1)
	{
            this.motionX = -1 - 1;//(amountOfHits / 5);
            this.motionY = -2 + random.nextInt(4);

            if (motionY == 0)
            {
                motionY = 1;
            }

            amountOfHits++;    
        }

        //If the collision for either of the paddles does not happen then one
        //the players scores a point
        if (checkCollision(paddle1, power) == 2)
	{
            paddle2.score++;
            spawn();
	}
	else if (checkCollision(paddle2, power) == 2)
	{
            paddle1.score++;
            spawn();
	}
    }

    //Method to respawn the ball after one of the players scores a point
    public void spawn()
    {
        this.amountOfHits = 0;
	this.x = pong.width / 2 - this.width / 2;
	this.y = pong.height / 2 - this.height / 2;

	this.motionY = -2 + 3;//random.nextInt(4);

	if (motionY == 0)
	{
            motionY = 1;
	}
	if (random.nextBoolean())
	{
            motionX = 1;
	}
	else
	{
            motionX = -1;
	}
    }

    //Method to check if the ball collides with a powerup while moving. The
    //collision does not change the direction of the ball but is acknowledged
    //as it passses by.
    public int checkCollision(Paddle paddle, PowerUp power)
    {
        if (this.x < paddle.x + paddle.width && this.x + width > paddle.x && this.y < paddle.y + paddle.height && this.y + height > paddle.y)
	{
            numberThatWeNeed -= 1;
                        
            balls -=1;            
		
            //A reaturn of 1 means the ball bounces
            return 1;                
        }
        //if the ball does not touch any of the paddles and leaves the JPanel
        else if ((paddle.x > x && paddle.paddleNumber == 1) || (paddle.x < x - width && paddle.paddleNumber == 2))
	{
            power.spawnPower();
            //A return of 2 means score
            return 2;                      
	}
        //A return of 0 means nothing happens
	return 0;    
    }

    //Renders the graphics for the ball on the JPanel
    public void render(Graphics g)
    {
        g.setColor(Color.WHITE);
	g.fillOval(x, y, width, height);        
    }
}
