package deepinthelight;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

public class Obstacle extends Element {

    private Image img;
    private int size;
    private float scale = 1;

    private static final String[] obstacleImgRes = { "images/obstacles/bigstone1_SMALL.png", "images/obstacles/bigstone2_SMALL.png",
                                                     "images/obstacles/bigstone3_SMALL.png", "images/obstacles/bigstone4_SMALL.png",
                                                     "images/obstacles/smallstone1_SMALL.png", "images/obstacles/smallstone2_SMALL.png",
                                                     "images/obstacles/smallstone3_SMALL.png", "images/obstacles/smallstone4_SMALL.png" };
    public Obstacle(int type, float posX, float posY)
                    throws SlickException {
        switch (type) {
            case 0:
                this.img = new Image("images/obstacles/bigstone1_SMALL.png");
                float[] b = {129,31, 204,30, 241,121, 304,79, 315,180, 394,239, 579,180, 650,593, 467,729, 306,725, 280,764, 160,766, 133,687, 23,628, 22,541, 84,489, 99,401, 27,306, 80,197, 89,78};
                this.box = new Polygon(addOffset(b, posX, posY));
                this.box.setX(posX);
                this.box.setY(posY);
                size = 20;
                scale = 0.614f;
                break;
            case 1:
                this.img = new Image("images/obstacles/bigstone2_SMALL.png");
                float[] b1 = {29,192, 335,25, 403,123, 473,121, 534,432, 465,447, 502,489, 344,611, 226,600, 155,624, 133,542, 57,520, 97,470, 109,353};
                this.box = new Polygon(addOffset(b1, posX, posY));
                this.box.setX(posX);
                this.box.setY(posY);
                size = 20;
                scale = 0.572f;
                break;
            case 2:
                this.img = new Image("images/obstacles/bigstone3_SMALL.png");
                float[] b2 = {16,173, 133,98, 218,129, 291,45, 405,199, 474,244, 605,227, 678,308, 667,432, 611,393, 483,388, 393,499, 254,436, 213,446, 135,288, 55,279};
                this.box = new Polygon(addOffset(b2, posX, posY));
                this.boxOffsetX = 0;
                this.boxOffsetY = 20;
                this.box.setX(posX);
                this.box.setY(posY);
                size = 20;
                scale = 0.388f;
                break;
            default:
                this.img = new Image("images/obstacles/bigstone4_SMALL.png");
                float[] b3 = {162,66, 258,137, 407,73, 532,135, 675,77, 901,242, 872,407, 675,476, 554,449, 423,584, 336,523, 68,777, 76,270};
                this.box = new Polygon(addOffset(b3, posX, posY));
                this.boxOffsetX = 70;
                this.boxOffsetY = 70;
                this.box.setX(posX);
                this.box.setY(posY);
                size = 20;
                scale = 0.6f;
                break;
        }
        
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
        img.draw(this.box.getX() - offsetX - boxOffsetX, this.box.getY() - offsetY - boxOffsetY, scale);
    }

    @Override
    public int getSize() {
        return size;
    }

}
