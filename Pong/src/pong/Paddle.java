package pong;

//Impots to make the shape and colour of the game pieces
import java.awt.Color;
import java.awt.Graphics;

public class Paddle
{
	public int paddleNumber;

        //Dimensions of the paddle on screen
	public int x, y, width = 45, height = 150;

	public int score;
        
        //movement speed of the paddles
        int speed1 = 25;
        int speed2 = 25;

        //Method that sets the positions of the paddles on the JPanel
	public Paddle(Pong pong, int paddleNumber)
	{
		this.paddleNumber = paddleNumber;

                //Paddle for player 1
		if (paddleNumber == 1)
		{
			this.x = 0;       
		}

                //Paddle for player 2 or bot
		if (paddleNumber == 2)
		{
			this.x = pong.width - width;  
		}

		this.y = pong.height / 2 - this.height / 2;
	}

        //Graphical representation of the paddles (colour and dimensions)
	public void render(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);
	}

        //Method that lets the paddles move when the player presses on the keyboard
	public void move(boolean up, PowerUp power, Ball ball)
	{
		
               //One of the powerups slows down the speed of the so there is an
               //Extra check to see if the paddle needs to move at half speed
               if (power.r == 1){ //powerup conditions to slow both paddles
                   if(ball.numberThatWeNeed <=12 && ball.numberThatWeNeed >= 0){
                    //Slow speed of the paddle
                    speed1 = 12;
                    speed2 = 12;
                
                   }
                   else{
                    //Base speed of the paddle
                    speed1 = 25;
                    speed2 = 25; 
                   }
                }
               //Slows down the player that did not get the powerup based on which
               //player hit the ball last
               else if (power.r == 2){ 
                   if(ball.numberThatWeNeed <=12 && ball.numberThatWeNeed >= 0){
                           if(power.slow == 1){
                               speed2 = 12;
                           }
                           else{
                               speed1 = 12;
                           }
                       }
                   else{
                       speed1 = 25;
                       speed2 = 25;
                   }
                   
               }
               
		
                //boolean conitions allowing the paddle to move when keys are
                //pressed on the keyboard ("w" "s" "up arrow" "down arrow")
                if (up)
		{
                    if (paddleNumber == 1){
			if (y - speed1 > 0)
			{
				y -= speed1;
			}
			else
			{
				y = 0;
			}
                    }
                    else{
                        if (y - speed2 > 0)
			{
				y -= speed2;
			}
			else
			{
				y = 0;
			}
                    }
		}
		else
		{
                    //defining borders for the paddles so the can't move off of
                    //the JPanel and get stuck
                    if(paddleNumber == 1){
                        if (y + height + speed1 < Pong.pong.height)
			{
				y += speed1;
			}
                        else
			{
				y = Pong.pong.height - height - 25;   
			}
                    }
                    else{
                        if (y + height + speed2 < Pong.pong.height)
			{
				y += speed2;
			}
                        else
			{
				y = Pong.pong.height - height - 25; 
			}
                    
                    }
                        
		}
	}   
}

