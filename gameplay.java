import java.util.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.random.*;
import javax.swing.JPanel;
import java.lang.Math;
import java.util.Random.*;
import java.awt.Graphics;
//four vlocity -> vx,-vx vy,-vy

// redraw the panel ->so we have to  create the game-loop,we need a timer
// this is version ig jpanel with more feature
public class gameplay extends JPanel implements ActionListener, KeyListener {
    Random random;

    private class tile {
        int x;
        int y;

        tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int width;
    int height;
    int tilesize = 25;
    // this is a snake
    tile snake_head;
    ArrayList<tile> snake_body;
    // this is a food
    tile food;

    // diff btw them
    // (food->randomly spawn in grid )
    // snake has movement and lengthing property
    // intiialise with constructor

    // time
    javax.swing.Timer gameloop;

    int vx;
    int vy;

    // game over
    boolean game_over = false;

    gameplay(int width, int height) {
        this.height = height;
        this.width = width;
        setPreferredSize(new Dimension(this.width, this.height));
        setBackground(Color.black);
        snake_head = new tile(5, 5);// this will be the default starting state
        snake_body = new ArrayList<tile>();
        // to start my snake with sokme initial body
        // snake_body.add(new tile(4, 5));
        // snake_body.add(new tile(3, 5));
        // you can use math.random(range)// but actually who caressssssssssssss
        addKeyListener(this); // this is line to take the input
        setFocusable(true);
        // random is a object of Random class so ,we cannnot use it wihtout crating //
        // object random

        // array body

        random = new Random();
        food = new tile(random.nextInt(width / tilesize), random.nextInt(height / tilesize));

        vx = 0;
        vx = 1;
        // let's create a place food fucntion
        place_food();
        gameloop = new javax.swing.Timer(100, this);// (1/10 th of a second ,what actually it has to do)
        gameloop.start();
    }

    public void paint(Graphics g) {
        super.paint(g);
        // let's define a draw functionn 1st
        draw(g);
    }

    public void draw(Graphics g) {
        // just get rid of the grid lines
        /*
         * for (int i = 0; i < width / tilesize; i++) {
         * // (x1,y1,x2,y2)
         * g.drawLine(i * tilesize, 0, i * tilesize, height);
         * g.drawLine(0, i * tilesize, width, i * tilesize);
         * }
         */
        // snake head

        // Draw the four boundary walls
        g.setColor(Color.YELLOW);
        g.drawRect(1, 1, width - 1, height - 1);
        // food
        g.setColor(Color.red);
        g.fillRect(
                food.x * tilesize,
                food.y * tilesize,
                tilesize,
                tilesize);

        g.fill3DRect(
                food.x * tilesize,
                food.y * tilesize,
                tilesize,
                tilesize, true);

        g.setColor(Color.green);

        // x corrdintate*tile size==> so it loooks ggggggggoooood
        g.fillRect(
                snake_head.x * tilesize,
                snake_head.y * tilesize,
                tilesize,
                tilesize);

        // snake body
        for (int i = 0; i < snake_body.size(); i++) {
            tile snakepart = snake_body.get(i);
            g.fillRect(snakepart.x * tilesize, snakepart.y * tilesize, tilesize, tilesize);
        }

        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (game_over) {
            g.setColor(Color.red);
            g.drawString("GAME OVER= " + String.valueOf(snake_body.size()), tilesize - 16, tilesize);
        } else {// just display the current score
            g.drawString("SCORE= " + String.valueOf(snake_body.size()), tilesize - 16, tilesize);

        }
    }

    public void place_food() {
        food.x = random.nextInt(width / tilesize);
        food.y = random.nextInt(height / tilesize);
    }

    public void move() {
        // this would be called every 100 hundred second

        // if colliosn happends ,then we will add a new tile
        if (collision(snake_head, food)) {
            snake_body.add(new tile(food.x, food.y));
            place_food();
        }

        /*
         * for (int i = snake_body.size() - 1; i > 0; i--) {
         * snake_body.get(i).x = snake_body.get(i - 1).x;
         * snake_body.get(i).y = snake_body.get(i - 1).x;
         * }
         */

        for (int i = snake_body.size() - 1; i >= 0; i--) {
            tile snakepart = snake_body.get(i);
            if (i == 0) {
                snakepart.x = snake_head.x;
                snakepart.y = snake_head.y;
            } else {
                tile prev = snake_body.get(i - 1);
                snakepart.x = prev.x;
                snakepart.y = prev.y;
            }
        }

        if (!snake_body.isEmpty()) {
            snake_body.get(0).x = snake_head.x;
            snake_body.get(0).y = snake_head.y;
        }

        snake_head.x += vx;
        snake_head.y += vy;
        // Check collision with the four walls
        if (snake_head.x < 0 || snake_head.x >= width / tilesize - 1 ||
                snake_head.y < 0 || snake_head.y >= height / tilesize - 1) {

            game_over = true;
            return;
        }

        for (int i = 0; i < snake_body.size(); i++) {
            tile snakepart = snake_body.get(i);
            if (collision(snakepart, snake_head)) {
                game_over = true;
            }
            /*
             * if ((snake_head.x < 0 || snake_head.x > width - 1) || (snake_head.y < 0 ||
             * snake_head.y > height - 1)) {
             * game_over = true;
             * }
             */
        }

        /// ->>score- we will now display the scores

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();// this will redraw the panel over and over again

        // if game is over ,then "it's done bro "
        if (game_over == true) {
            gameloop.stop();
            // i want to display a pop here
            /*
             * it will show
             * {game over }
             * { restart the game }
             * 
             */
        }
    }

    // this will take input for the kypressesssss
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && vy != -1) {
            vx = 0;
            vy = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && vy != 1) {
            vx = 0;
            vy = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && vx != -1) {
            vx = -1;
            vy = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && vx != 1) {
            vx = +1;
            vy = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    // now our sanke is mvoin

    // for collisoin ,we will define a new function
    public boolean collision(tile t1, tile t2) {
        return (t1.x == t2.x && t1.y == t2.y);

    }

    // let's apply the game over condition

}
