package pong;

/**
 *
 * @author mandar
 */

//Imports for live graphics, randomizing the bounce angle of the ball
//and having the porgram respond to the keyboard
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Pong implements ActionListener, KeyListener
{
    //Global variables for the class
    public static Pong pong;
    public int width = 1000, height = 710; //size of the frame
    public Renderer renderer; //new renderer
    public Paddle player1; //player 1's paddle
    public Paddle player2; //player 2's paddle
    public Ball ball; //the ball
    public PowerUp Boom; //Power Up 1
    public PowerUp power1; //Power Up 2
    public PowerUp power2; //Power Up 3
    public boolean bot = false, selectingDifficulty;
    public boolean w, s, up, down; //keypress booleans
    public int gameStatus = 0, scoreLimit = 7, playerWon; //0 = Menu, 1 = Paused, 2 = Playing, 3 = Over
    public int botDifficulty, botMoves, botCooldown = 0;
    public Random random;
    public JFrame jframe;

    public Pong() //start up method
    {
        Timer timer = new Timer(20, this); //timer 
	random = new Random();

	jframe = new JFrame("Pong"); //new jframe for graphics

	renderer = new Renderer(); //contructions of renderer

        //creation of jframe
                
	jframe.setSize(width + 35, height +35); 
	jframe.setVisible(true);
	jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	jframe.add(renderer);
	jframe.addKeyListener(this);

        //starts timer
	timer.start();
    }

    public void start()
    {
    //initialzing the ball, the paddles and the powerups
         
    gameStatus = 2;
    player1 = new Paddle(this, 1);
    player2 = new Paddle(this, 2);
    ball = new Ball(this);
    Boom = new PowerUp(this, 1);
    power1 = new PowerUp(this, 2);
    power2 = new PowerUp(this, 3);
    }

        //Conditions that are updated throughout the game to determine if a
        //player has won and when a player scores. Also deals with player movement
        //because each one requires constant refreshing
    public void update()
    {
        if (player1.score >= scoreLimit)
	{
            playerWon = 1;
            gameStatus = 3;
	}

	if (player2.score >= scoreLimit)
	{
            gameStatus = 3;
            playerWon = 2;
	}

	if (w) //if w is pressed
	{
            player1.move(true, Boom, ball);
            player1.move(true, power1, ball);
                        
                        
	}
	
        if (s) // if s is pressed
	{
            player1.move(false, Boom, ball);
            player1.move(false, power1, ball);
                        
	}

	if (!bot) //if the mode is 2 player
	{
            if (up) //if up arrow key is pressed
            {
                player2.move(true, Boom, ball);
                player2.move(true, power1, ball);
            }
            
            if (down) //if down arrowkey is pressed
            {
                player2.move(false, Boom, ball);
                player2.move(false, power1, ball);
            }
	}
        
        else //if the bot mode is selected
        {
            if (botCooldown > 0)
            {
                botCooldown--;
                
                if (botCooldown == 0)
		{
                    botMoves = 0;
		}
            }
            
            if (botMoves < 10) //tracks the balls movement
            {
            
                if (player2.y + player2.height / 2 < ball.y)
		{
                    player2.move(false, Boom, ball);
                    player2.move(false, power1, ball);
                                        
                    botMoves++;
		}

		if (player2.y + player2.height / 2 > ball.y)
		{
                    player2.move(true, Boom, ball);
                    player2.move(true, power1, ball);
                                       
                    botMoves++;
		}
                                
                //changes tracking speed based on difficulty

		if (botDifficulty == 0)
		{
                    botCooldown = 25;
		}
		
                if (botDifficulty == 1)
		{
                    botCooldown = 20;
		}
		
                if (botDifficulty == 2)
		{
                    botCooldown = 15;
		}
            }
	}
        
        //update every object

        ball.update(player1, player2, Boom, ball);
                
        ball.update(player1, player2, power1, ball);
                
        ball.update(player1, player2, power2, ball);
                
        Boom.update(ball, player1);
        power1.update(ball, player1);
        power2.update(ball, player1);
                
        Boom.update(ball, player2);
        power1.update(ball, player2);
        power2.update(ball, player1);
    }

    public void render(Graphics2D g) //render the the graphics of the frame and the objects
    {
        g.setColor(Color.BLACK);
	g.fillRect(0, 0, width, height);
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
        //start screen

	if (gameStatus == 0)
	{
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));

            g.drawString("PONG", width / 2 - 75, 50);

            if (!selectingDifficulty)
            {
                g.setFont(new Font("Arial", 1, 30));
                
		g.drawString("Press Space to Play", width / 2 - 150, height / 2 - 25);
		g.drawString("Press Shift to Play with Bot", width / 2 - 200, height / 2 + 25);
		g.drawString("<< Score Limit: " + scoreLimit + " >>", width / 2 - 150, height / 2 + 75);
            }
        }

	if (selectingDifficulty) //when selecting the difficulty using the arrowkeys
	{
            String string = botDifficulty == 0 ? "Easy" : (botDifficulty == 1 ? "Medium" : "Hard");

            g.setFont(new Font("Arial", 1, 30));

            g.drawString("<< Bot Difficulty: " + string + " >>", width / 2 - 180, height / 2 - 25);
            g.drawString("Press Space to Play", width / 2 - 150, height / 2 + 25);
	}

	if (gameStatus == 1) //pausing the game
	{
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("PAUSED", width / 2 - 103, height / 2 - 25);
	}

	if (gameStatus == 1 || gameStatus == 2) //updates the score
	{
            g.setColor(Color.WHITE);

            g.setStroke(new BasicStroke(5f));

            g.drawLine(width / 2, 0, width / 2, height);

            g.setStroke(new BasicStroke(2f));

            g.drawOval(width / 2 - 150, height / 2 - 150, 300, 300);

            g.setFont(new Font("Arial", 1, 50));

            g.drawString(String.valueOf(player1.score), width / 2 - 90, 50);
	
            g.drawString(String.valueOf(player2.score), width / 2 + 65, 50);
                        
            //renders the graphics for all the objects
                        
            player1.render(g);
            player2.render(g);
            ball.render(g);
            Boom.render(g);
            power1.render(g);
            power2.render(g);
        }

	if (gameStatus == 3) //win screen
	{
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));

            g.drawString("PONG", width / 2 - 75, 50);
            //test for who wins
            if (bot && playerWon == 2)
            {
                g.drawString("The Bot Wins!", width / 2 - 170, 200);
            }
	
            else
            {
                g.drawString("Player " + playerWon + " Wins!", width / 2 - 165, 200);
            }

            g.setFont(new Font("Arial", 1, 30));

            g.drawString("Press Space to Play 2 Player", width / 2 - 185, height / 2 - 25);
            g.drawString("Press ESC for Menu and bot play", width / 2 - 220, height / 2 + 25);
                        
	}
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (gameStatus == 2) //updates everything throughout the game
	{
            update();
	}

	renderer.repaint();
    }

    public static void main(String[] args)
    {
        pong = new Pong(); //creats a new instance of the pong game
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
      //key presses
        int id = e.getKeyCode();

        if (id == KeyEvent.VK_W) //w
        {
            w = true;
        }
        
        else if (id == KeyEvent.VK_S) //s
        {
            s = true;
        }

        else if (id == KeyEvent.VK_UP) //up
        {
            up = true;
        }

        else if (id == KeyEvent.VK_DOWN) //down
        {
            down = true;
        }

        else if (id == KeyEvent.VK_RIGHT) //right
        {
            if (selectingDifficulty) //changes difficulty shown on screen
    	{
                if (botDifficulty < 2)
                {
                    botDifficulty++;
                }
                else
                {
                    botDifficulty = 0;
                }
            }
            else if (gameStatus == 0) //increases score limit
            {
                scoreLimit++;
            }
        }
        
        else if (id == KeyEvent.VK_LEFT) //left
        {
            if (selectingDifficulty) //changes difficulty the other way
            {
                if (botDifficulty > 0)
                {
                    botDifficulty--;
                }
                else
                {
                    botDifficulty = 2;
                }
            }
            
            else if (gameStatus == 0 && scoreLimit > 1) //lowers the score limit
            {
                scoreLimit--;
            }
        }
        
        else if (id == KeyEvent.VK_ESCAPE && (gameStatus == 2 || gameStatus == 3)) //escape
        {
            gameStatus = 0;
        }
        
        else if (id == KeyEvent.VK_SHIFT && gameStatus == 0) //shift
        {
            bot = true; //bot mode initiated
            selectingDifficulty = true;
        }
        
        else if (id == KeyEvent.VK_SPACE) //space
        {
            if (gameStatus == 0 || gameStatus == 3) //starts game
            {
                if (!selectingDifficulty)
                {
                    bot = false;
                }
                else
                {
                    selectingDifficulty = false;
                }
                
                start();
            }
            else if (gameStatus == 1)
            {
                gameStatus = 2;
            }
            else if (gameStatus == 2)
            {
                gameStatus = 1;
            }
        }
    }   

    @Override
    public void keyReleased(KeyEvent e)
    {
        //when keys are released
        int id = e.getKeyCode();
        
        if (id == KeyEvent.VK_W)
        {
            w = false;
        }
	
        else if (id == KeyEvent.VK_S)
        {
            s = false;
        }
            
        else if (id == KeyEvent.VK_UP)
        {
            up = false;
        }
	
        else if (id == KeyEvent.VK_DOWN)
        {
            down = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }
}
    
    

