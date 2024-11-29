package chapter.chap3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel_chap3 extends JPanel implements KeyListener {
    private SceneChangeListener sceneChangeListener;

    public interface SceneChangeListener {
        void onSceneChange();
    }

    public void setSceneChangeListener(SceneChangeListener listener) {
        this.sceneChangeListener = listener;
    }

    private BufferedImage backgroundImage, bikeImage, character, character_1;
    private Image bikImage_scaled, backgroundImage_scaled, character_scaled, character_scaled_right;
    private Image character_1_scaled, preImage;
    public int check = 0;
    final int TypeSize = 12;
    final int scale = 10;
    final int tileSize = TypeSize * scale;
    final int maxScreenCol = 8;
    final int maxScreenRow = 7;
    final int ScreenWidth = tileSize * maxScreenCol;
    final int ScreenHeight = tileSize * maxScreenRow;
    private int x = 0 * tileSize;  // Vị trí ban đầu của nhân vật (trục X)
    private int y = 1 * tileSize;  // Vị trí ban đầu của nhân vật (trục Y)
    private int preX = x;
    private int preY = y;
    public int key = -1;
    boolean winCheck = false;

    Rectangle rect1 = new Rectangle(0 * tileSize, 0 * tileSize, 8 * tileSize, tileSize);
    Rectangle rect2 = new Rectangle(5 * tileSize, 1 * tileSize, 3 * tileSize, 1 * tileSize);
    Rectangle rect3 = new Rectangle(7 * tileSize, 2 * tileSize, 1 * tileSize, 1 * tileSize);
    Rectangle rect4 = new Rectangle(0 * tileSize, 5 * tileSize, 1 * tileSize, tileSize);
    Rectangle rect5 = new Rectangle(0 * tileSize, 6 * tileSize, 8 * tileSize, tileSize);
    Rectangle rect6 = new Rectangle(6 * tileSize, 5 * tileSize, 2 * tileSize, 1 * tileSize);
    Rectangle rect7 = new Rectangle(1 * tileSize, 1 * tileSize, 1 * tileSize, 1 * tileSize);
    Rectangle rectA = new Rectangle(x, y, tileSize, tileSize);

    Rock_chap3[] r = {
        new Rock_chap3(0, 3),
        new Rock_chap3(1, 2),
        new Rock_chap3(1, 4),
        new Rock_chap3(2, 3),
        new Rock_chap3(2, 5),
        new Rock_chap3(3, 2),
        new Rock_chap3(3, 4),
        new Rock_chap3(4, 1),
        new Rock_chap3(4, 3),
        new Rock_chap3(4, 5),
        new Rock_chap3(5, 2),
        new Rock_chap3(5, 3),
        new Rock_chap3(5, 4),
        new Rock_chap3(6, 4)
    };

    public GamePanel_chap3() {
        try {
            backgroundImage = ImageIO.read(new File("pic/NEWBG3.png")); 
            bikeImage = ImageIO.read(new File("pic/xe_may.png"));
            character = ImageIO.read(new File("pic/character.png")); 
            character_1 = ImageIO.read(new File("pic/character_1.png")); 

            character_1_scaled = character_1.getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
            character_scaled = character.getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
            character_scaled_right = character.getScaledInstance(-1 * tileSize, tileSize, Image.SCALE_SMOOTH);
            bikImage_scaled = bikeImage.getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);

            backgroundImage_scaled = backgroundImage.getScaledInstance(ScreenWidth, ScreenHeight, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setPreferredSize(new Dimension(tileSize * maxScreenCol, tileSize * maxScreenRow));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(this);
        this.requestFocus();

        Timer timer = new Timer(33, e -> repaint());
        timer.start();
    }

    public boolean chamTuong(Rectangle rectA) {
        if (rect1.intersects(rectA) || rect2.intersects(rectA) || rect3.intersects(rectA) || 
                rect4.intersects(rectA) || rect5.intersects(rectA) || rect6.intersects(rectA) || rect7.intersects(rectA)) {
            return true;
            
        }
        else return false;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage_scaled, 0, 0, null);
        drawCharacter(g, key);
        g.setColor(Color.BLUE);
        g.fillOval(7 * tileSize + tileSize / 4, 3 * tileSize + tileSize / 4, tileSize / 2, tileSize / 2);
        //g.drawImage(character_scaled, x, y, null); // Vẽ nhân vật tại (x, y)
        for (int i = 0; i < 14; i++) r[i].draw(g, bikImage_scaled);
    }
    public void drawCharacter(Graphics g, int key) {
        if (key == -1 || key == KeyEvent.VK_LEFT) {
            g.drawImage(character_scaled, x, y, null);
            preImage = character_scaled;
        } else if (key == KeyEvent.VK_RIGHT) {
            g.drawImage(character_1_scaled, x, y, null);
            preImage = character_1_scaled;
        } else {
            g.drawImage(preImage, x, y, null);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        key = e.getKeyCode();
        preX = x;
        preY = y;

        switch (key) {
            case KeyEvent.VK_R:
                resetGame();
                break;
            case KeyEvent.VK_LEFT:
                if (x > 0) x -= tileSize;
                break;
            case KeyEvent.VK_RIGHT:
                if (x < (tileSize * maxScreenCol) - tileSize) x += tileSize;
                break;
            case KeyEvent.VK_UP:
                if (y > 0) y -= tileSize;
                break;
            case KeyEvent.VK_DOWN:
                if (y < (tileSize * maxScreenRow) - tileSize) y += tileSize;
                break;
        }

        rectA.setLocation(x, y);
        if (chamTuong(rectA)) revertPosition();
        handleRockCollision();
        checkWinCondition();
    }

    private void resetGame() {
        x = 0 * tileSize;
        y = 1 * tileSize;
        preX = x;
        preY = y;
        key = -1;
        for (int i=0; i<14; i++) {
            r[i].x = r[i].DefaultX;
            r[i].y = r[i].DefaultY;
            r[i].rect.setLocation(r[i].x, r[i].y);
        }
    }

    private void revertPosition() {
        x = preX;
        y = preY;
        rectA.setLocation(x, y);
    }

    private void handleRockCollision() {
        for (int i = 0; i < r.length; i++) {
            if (r[i].vaCham(rectA)) {
                revertPosition();
                r[i].diChuyen(key);

                if (chamTuong(r[i].rect)) r[i].luiLai();

                for (int j = 0; j < r.length; j++) {
                    if (i != j && r[i].vaCham(r[j].rect)) {
                        r[i].luiLai();
                        break;
                    }
                }
            }
        }
    }

    private void checkWinCondition() {
        // Kiểm tra nếu tọa độ phù hợp để thắng
        if (x == 7 * tileSize && (y == 3 * tileSize || y == 4 * tileSize)) {
            System.out.println("YOU WIN");
    
            // Gọi chuyển cảnh sang game đua xe nếu có SceneChangeListener
            if (sceneChangeListener != null) {
                sceneChangeListener.onSceneChange();
            } else {
                System.err.println("SceneChangeListener is null! Cannot change scene.");
            }
        }
    }
    

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }
}
