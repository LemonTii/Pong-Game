/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.*;
import java.awt.Color;
import java.awt.Graphics;

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
    
    public void render(Graphics g){ //create power ups
        
        
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
    
    public int checkCollision(Ball ball, Paddle paddle){ //chekc collision between ball and power
        
        if(paddle.paddleNumber == 1){
            slow = 1;
        }
        else{
            slow = 2;
        }
        
        if((ball.x >= this.x - 30 && ball.x <= this.x + 30) && (ball.y >= this.y -30 && ball.y <= this.y + 30)){
            
     
            
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
    
    public void update(Ball ball, Paddle paddle){ //when power up is hit
        if ((checkCollision(ball, paddle)) == 1){
            
            x = 900;
            y = 900;
            
        }
        
        
        
        
    }
    
    public void spawnPower(){ //spawns powerup 
        
        
        
            x = rand.nextInt(600);
            y = rand.nextInt(600);
        
        
    }
    
}
