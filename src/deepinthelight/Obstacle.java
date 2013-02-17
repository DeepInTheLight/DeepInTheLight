package deepinthelight;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

public class Obstacle extends Element {

    private Image img;
    private int size;
    private float scale = 1;

    private static final String[] obstacleImgRes = { "images/obstacles/bigstone1.png", "images/obstacles/bigstone2.png",
                                                     "images/obstacles/bigstone3.png", "images/obstacles/bigstone4.png",
                                                     "images/obstacles/smallstone1.png", "images/obstacles/smallstone2.png",
                                                     "images/obstacles/smallstone3.png", "images/obstacles/smallstone4.png" };
    public Obstacle(int type, float posX, float posY)
                    throws SlickException {
//        switch (type) {
//            case 0:
                this.img = new Image("images/obstacles/bigstone1.png");
                float[] b = {129,31, 204,30, 241,121, 304,79, 315,180, 394,239, 579,180, 650,593, 467,729, 306,725, 280,764, 160,766, 133,687, 23,628, 22,541, 84,489, 99,401, 27,306, 80,197, 89,78};
                this.box = new Polygon(addOffset(b, posX, posY));
                this.boxOffsetX = 2;
                this.boxOffsetY = 12;
                this.box.setX(posX);
                this.box.setY(posY);
                size = 20;
                scale = 0.307f;
//                break;
//            case 1:
//                this.img = new Image("images/malus/danger2.png");
//                this.box = new Circle(posX, posY, 25);
//                this.boxOffsetX = 10;
//                this.boxOffsetY = 10;
//                break;
//            case 2:
//                this.img = new Image("images/malus/danger3.png");
//                this.box = new Circle(posX, posY, 25);
//                this.boxOffsetX = 4;
//                this.boxOffsetY = 7;
//                break;
//        }

        //img = new Image(obstacleImgRes[type%8]);
//        img = new Image("images/psc.png");
//        box = new Circle(centerX, centerY, 100);
        
    }

    private float[] addOffset(float[] tab, float offsetX, float offsetY) {
        float[] res = tab.clone();
//        for(int i = 0; i < res.length; i++) {
//            if(i / 2 == 0) {
//                res[i] += offsetX;
//            } else {
//                res[i] += offsetY;
//            }
//        }

        return res;
    }

    public boolean collide() {
        return true; // obstacles block Gunther
    }
    
    public void update() {
    }

    @Override
    public void render(float offsetX, float offsetY) {
        img.draw(this.box.getX() - offsetX, this.box.getY() - offsetY, scale);
    }

    @Override
    public int getSize() {
        return size;
    }

}
