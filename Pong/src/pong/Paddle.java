/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.awt.Color;
import java.awt.Graphics;


public class Paddle
{

	public int paddleNumber;

	public int x, y, width = 45, height = 150; //size and position of paddle

	public int score;
        
        
        
        int speed1 = 25;
        int speed2 = 25;

	public Paddle(Pong pong, int paddleNumber)
	{
		this.paddleNumber = paddleNumber;

		if (paddleNumber == 1)
		{
			this.x = 0;
                        
                        
		}

		if (paddleNumber == 2)
		{
			this.x = pong.width - width;
                        
		}

		this.y = pong.height / 2 - this.height / 2;
	}

	public void render(Graphics g) //graphics
	{
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);
	}

	public void move(boolean up, PowerUp power, Ball ball) //paddle movement
	{
		
                
               if (power.r == 1){ //powerup conditions to slow both paddles
                   if(ball.numberThatWeNeed <=12 && ball.numberThatWeNeed >= 0){
                    speed1 = 12;
                    speed2 = 12;
                
                   }
                   else{
                    speed1 = 25;
                    speed2 = 25; 
                   }
                }
               else if (power.r == 2){ //one paddle slow
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
               
		
                //boolean conitions allowing the paddle to move
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
                    //defining borders for the paddle's motion
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

