package chapter.chap0.src.monster;

import chapter.chap0.src.entity.Entity;
import chapter.chap0.src.main.GamePanel;

import java.util.Random;

public class MON_GreenSlime2 extends Entity {

    GamePanel gp;

    public MON_GreenSlime2 (GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Bat";
        speed = 2;
        maxLife = 2;
        life = maxLife;

        attack = 5;
        defense = 0;
        exp = 2;


        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {


        up1 = setup("/monster/mt_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/mt_down_2", gp.tileSize, gp.tileSize);

        down1 = setup("/monster/mt_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/mt_down_2", gp.tileSize, gp.tileSize);

        left1 = setup("/monster/mt_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/mt_down_2", gp.tileSize, gp.tileSize);

        right1 = setup("/monster/mt_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/mt_down_2", gp.tileSize, gp.tileSize);
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

/*        int i = new Random().nextInt(100) + 1;
        if(i>99 && projectile.alive == false && shotAvailableCounter == 30) {
            projectile.set(worldX,worldY,direction,true,this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
        }*/

        if(shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }

    }

    public void damageReaction() {

        actionLockCounter = 0;
        direction = gp.player.direction;

    }

}
