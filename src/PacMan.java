import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Rectangle;
import java.awt.Font;


import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class PacMan implements ActionListener, KeyListener
{   public static PacMan pacman;

    public final int WIDTH = 605, HEIGHT = 550, SPACE = 3, PMW = 20, PMH = 20;

    public PRenderer renderer;

    public Rectangle pm, blinky, pinky, inky, clyde;

    public int velX = 0, velY = 0;

    public int score;

    public boolean gameOver, started;

    public Random rand;

    public ArrayList<Rectangle> dots;
    public ArrayList<Rectangle> bigDots;
    public ArrayList<Rectangle> walls;

    public Color bg = new Color(19, 13, 99);
    public Color dot = new Color(255, 255, 142);

    public PacMan()
    {
        JFrame jframe = new JFrame();
        Timer timer = new Timer(20, this);

        renderer = new PRenderer();
        rand = new Random();

        jframe.add(renderer);
        jframe.setTitle("Pac Man");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(WIDTH, HEIGHT);
        jframe.addKeyListener(this);
        //jframe.setResizable(false);
        jframe.setVisible(true);

        pm = new Rectangle(287, 293, PMW, PMH); 
        //blinky = new Rectangle(

        dots = new ArrayList<Rectangle>();
        bigDots = new ArrayList<Rectangle>();
        walls = new ArrayList<Rectangle>();

        addDot();
        addCourse();
        timer.start();
    }

    public void addCourse()
    {
        // upper course
        walls.add(new Rectangle(130, 80, 45, 30));
        walls.add(new Rectangle(205, 80, 60, 30));
        walls.add(new Rectangle(295, 50, 15, 60));
        walls.add(new Rectangle(340, 80, 60, 30));
        walls.add(new Rectangle(430, 80, 45, 30));
        // 2 small rect on upper
        walls.add(new Rectangle(130, 140, 45, 15));
        walls.add(new Rectangle(430, 140, 45, 15));
        // walls on side
        walls.add(new Rectangle(100, 185, 75, 60));
        walls.add(new Rectangle(100, 275, 75, 60));
        walls.add(new Rectangle(430, 185, 75, 60));
        walls.add(new Rectangle(430, 275, 75, 60));
        // 3Ts in middle
        walls.add(new Rectangle(205, 140, 15, 105));
        walls.add(new Rectangle(220, 185, 45, 15));
        walls.add(new Rectangle(250, 140, 105, 15));
        walls.add(new Rectangle(295, 155, 15, 45));
        walls.add(new Rectangle(385, 140, 15, 105));
        walls.add(new Rectangle(340, 185, 45, 15));
        // middle box
        walls.add(new Rectangle(250, 235, 105, 50));
        // 2 lines and T on middle
        walls.add(new Rectangle(205, 275, 15, 60));
        walls.add(new Rectangle(385, 275, 15, 60));
        walls.add(new Rectangle(250, 320, 105, 15));
        walls.add(new Rectangle(295, 335, 15, 45));
        // 2 Ls, 2 lines on bottom top
        walls.add(new Rectangle(130, 365, 45, 15));
        walls.add(new Rectangle(160, 380, 15, 45));
        walls.add(new Rectangle(205, 365, 60, 15));
        walls.add(new Rectangle(340, 365, 60, 15));
        walls.add(new Rectangle(430, 365, 45, 15));
        walls.add(new Rectangle(430, 380, 15, 45));
        // 2 lines and T on bottom mid
        walls.add(new Rectangle(100, 410, 30, 15));
        walls.add(new Rectangle(250, 410, 105, 15));
        walls.add(new Rectangle(295, 425, 15, 45));
        walls.add(new Rectangle(475, 410, 30, 15));
        // 2 Ts on bottom
        walls.add(new Rectangle(130, 455, 135, 15));
        walls.add(new Rectangle(205, 410, 15, 45));
        walls.add(new Rectangle(340, 455, 135, 15));
        walls.add(new Rectangle(385, 410, 15, 45));
    }

    public void course(Graphics g)
    {
        g.setColor(Color.blue);
        g.drawRoundRect(100, 50, WIDTH - 200, HEIGHT - 100, 20 ,20);
        for(Rectangle wall : walls)
        {
            g.fillRect(wall.x, wall.y, wall.width, wall.height);
        }
    }

    public void addDot()
    {
        dots.add(new Rectangle(110, 60, 5, 5));
        dots.add(new Rectangle(130, 60, 5, 5));
        dots.add(new Rectangle(150, 60, 5, 5));
        dots.add(new Rectangle(170, 60, 5, 5));
        dots.add(new Rectangle(190, 60, 5, 5));
        dots.add(new Rectangle(210, 60, 5, 5));
        dots.add(new Rectangle(230, 60, 5, 5));
        dots.add(new Rectangle(250, 60, 5, 5));
        dots.add(new Rectangle(270, 60, 5, 5));
        dots.add(new Rectangle(110, 80, 5, 5));
        dots.add(new Rectangle(110, 100, 5, 5));
        dots.add(new Rectangle(110, 130, 5, 5));
        dots.add(new Rectangle(110, 150, 5, 5));
        dots.add(new Rectangle(110, 170, 5, 5));
    }

    public void addBigDot()
    {

    }

    public void paintDot(Graphics g)
    {
        g.setColor(dot);
        for(int i = 0; i < dots.size(); i++)
        {
            g.fillRect(dots.get(i).x, dots.get(i).y, 5, 5);
        }
    }

    public void deleteDot(Graphics g, Rectangle r)
    {
        g.setColor(Color.black);
        g.fillRect(r.x, r.y, 5, 5);
    }

    public void ghost()
    {

    }

    public void repaint(Graphics g)
    {
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.yellow);
        g.fillOval(pm.x, pm.y, pm.width, pm.height);

        course(g);
        paintDot(g);
        
        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 35));
        g.drawString(String.valueOf(score), 288, 30);

        for(int i = 0; i < dots.size(); i++)
        {
            if(pm.intersects(dots.get(i)))
            {
                score += 10;
                deleteDot(g, dots.get(i));
                dots.remove(i);
                i--;
            }
        }
    }
    
    public void actionPerformed(ActionEvent e)
    { 
        if(pm.x < 100)
        {
            velX = 0;
            pm.x = 100;
        }
        if(pm.x > WIDTH - 100 - PMW)
        {
            velX = 0;
            pm.x = WIDTH - 100 - PMW;
        }
        if(pm.y < 50)
        {
            velY = 0;
            pm.y = 50;
        }   
        if(pm.y > HEIGHT - 50 - PMH)
        {
            velY = 0;
            pm.y = HEIGHT - 50 - PMH;
        }
        for(Rectangle wall : walls)
        {
            if(velX < 0)
            {
                // check if it intersects on the next frame
                if(wall.intersects(new Rectangle(pm.x - SPACE, pm.y, PMW, PMH)))
                {
                    velX = 0;
                }
            }
            if(velX > 0)
            {
                if(wall.intersects(new Rectangle(pm.x + SPACE, pm.y, PMW, PMH)))
                {
                    velX = 0;
                }
            }
            if(velY < 0)
            {
                if(wall.intersects(new Rectangle(pm.x, pm.y - SPACE, PMW, PMH)))
                {
                    velY = 0;
                }
            }
            if(velY > 0)
            {
                if(wall.intersects(pm.x, pm.y + SPACE, PMW, PMH))
                {
                    velY = 0;
                }
            }
        }
        pm.x += velX;
        pm.y += velY;
        renderer.repaint();
    }

    
    public void keyPressed(KeyEvent e)
    {
        int c = e.getKeyCode();
        if(c == KeyEvent.VK_LEFT)
        {
            // check if not against a wall
            for(Rectangle wall : walls)
            {     
                if(!(wall.intersects(new Rectangle(pm.x - SPACE, pm.y, 22,22))))
                {
                    velX = -3;
                    velY = 0;
                }
            }
        }
        if(c == KeyEvent.VK_RIGHT)
        {
            for(Rectangle wall : walls)
            {     
                if(!(wall.intersects(new Rectangle(pm.x + SPACE, pm.y, 22,22))))
                {
                    velX = 3;
                    velY = 0;
                }
            }
        }
        if(c == KeyEvent.VK_UP)
        {
            for(Rectangle wall : walls)
            {     
                if(!(wall.intersects(new Rectangle(pm.x, pm.y - SPACE, 22,22))))
                {
                    velX = 0;
                    velY = -3;
                }
            }
        }
        if(c == KeyEvent.VK_DOWN)
        {
            for(Rectangle wall : walls)
            {     
                if(!(wall.intersects(new Rectangle(pm.x, pm.y + SPACE, 22,22))))
                {
                    velX = 0;
                    velY = 3;
                }
            }
        }
    }
    
    public void keyTyped(KeyEvent e){}
    
    public void keyReleased(KeyEvent e){}
   
    public static void main(String[] args)
    {
        pacman = new PacMan();
    }
}




