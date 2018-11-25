/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

/**
 *
 * @author mandar
 */
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball
{

	public int x, y, width = 25, height = 25;

	public int motionX, motionY;

	public Random random;

	private Pong pong;
        
        public int speedb = 3;

	public int amountOfHits;
        
        public int balls = -1;
        
        public int numberThatWeNeed = -1;
        
        public int time;

	public Ball(Pong pong)
	{
		this.pong = pong;

		this.random = new Random();

		spawn();
	}

	public void update(Paddle paddle1, Paddle paddle2, PowerUp power, Ball ball)
	{
		
                /*if (power.r == 3 && (power.checkCollision(ball, paddle2) == 1 )|| power.checkCollision(ball, paddle1) == 1){
                    if(ball.numberThatWeNeed <=12 && ball.numberThatWeNeed > 0){
                        speedb = 8;
                    }
                    else{
                        speedb = 3;
                    }
                }
                else{
                    speedb = 3;
                }*/
                
                if(power.r == 3){
                    if (ball.balls > 0 && ball.balls <=12){
                        speedb = 7;
                    }
                    else if (ball.balls <= 0){
                        speedb = 3;
                    }
                }
                else{
                    speedb = 3;
                }
                    
                           
		this.x += motionX * speedb;
		this.y += motionY * speedb;

		if (this.y + height - motionY > pong.height || this.y + motionY < 0)
		{
			
                    if (this.motionY < 0)
			{
				this.y = 0;
				this.motionY = 1 ;//random.nextInt(4);

				if (motionY == 0)
				{
					motionY = 1;
				}
			}
			else
			{
				this.motionY = -1;//-random.nextInt(4);
				this.y = pong.height - height;

				if (motionY == 0)
				{
					motionY = -1;
				}
			}
		}

		if (checkCollision(paddle1, power) == 1)
		{
			this.motionX = 1 + 1;//(amountOfHits / 5);
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

	public int checkCollision(Paddle paddle, PowerUp power)
	{
		if (this.x < paddle.x + paddle.width && this.x + width > paddle.x && this.y < paddle.y + paddle.height && this.y + height > paddle.y)
		{
                        numberThatWeNeed -= 1;
                        
                        balls -=1;
                        
                        
			return 1; //bounce
                        
		}
		else if ((paddle.x > x && paddle.paddleNumber == 1) || (paddle.x < x - width && paddle.paddleNumber == 2))
		{
                        
                        power.spawnPower();
			return 2; //score
                        
                        
		}

		return 0; //nothing
	}

	public void render(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillOval(x, y, width, height);
                
	}

}
