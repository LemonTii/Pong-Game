/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.*;
import java.awt.Color;
import java.awt.Graphics;


/**
 *
 * @author mandar
 */
public class PowerUp {
    int type;
    int x = 500, y = 500;
    private Pong pong;
    
    public int r;
    
    
    
    
    public PowerUp(Pong pong){
        
        this.pong = pong;
        
    }
    
    public void render(Graphics g){
        
        g.setColor(Color.red);
        g.fillOval(x, y, 30, 30);
    }
    
    public int checkCollision(Ball ball){
        
        
        
        if((ball.x >= this.x - 30 && ball.x <= this.x + 30) && (ball.y >= this.y -30 && ball.y <= this.y + 30)){
            
            ball.numberThatWeNeed = 12;
            r = 1;
            
            return 1;
        }
        
        else{
            return 0;
        }
        
    }
    
    public void update(Ball ball){
        if ((checkCollision(ball)) == 1){
            
            x = 900;
            y = 900;
        }
        else{
            
        }
        
    }
    
    
    
}
