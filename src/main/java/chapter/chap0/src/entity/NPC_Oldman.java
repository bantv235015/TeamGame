package chapter.chap0.src.entity;

import chapter.chap0.src.main.GamePanel;
import chapter.chap0.src.main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class NPC_Oldman extends Entity{

    public NPC_Oldman(GamePanel gp) {

        super(gp);

        direction = "down";

        speed = 1;

        getImage();
        setDialogue();

    }

    public void getImage() {

        up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);

        down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);

        left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);

        right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);

    }

    public void setDialogue() {
        dialogues[0] = "Hello";
        dialogues[1] = "Hellooooo fdgsfg ds fds fds ";
        dialogues[2] = "Hellooooooo fds f sd fsd fsd f ";
        dialogues[3] = "Hellooooooooo jsaf oaf si jf sao";
    }

    public void setAction() {

        actionLockCounter++;

        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) +1; // pick up a number from 1 to 100
            if(i<=25) {
                direction = "up";
            }

            if(i>25 && i<=50) {
                direction = "down";
            }

            if(i>50 && i<=75) {
                direction = "left";
            }

            if(i>75 && i<=100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    public void speak() {
        super.speak();
    }


}