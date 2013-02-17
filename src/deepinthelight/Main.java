package deepinthelight;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import java.lang.reflect.Field;

public class Main extends StateBasedGame {

    public static final int height = 720;
    public static final int width = 1280;
    public static final boolean fullscreen = false;
    public static final boolean showFPS = true;
    public static final String title = "Deep In The Light"; //thks AnisB
    public static final int fpslimit = 60;
    public static final int MAINMENU = 0;
    public static final int GAMEPLAY = 1;
    public static final int GAMEOVER = 2;

    public static void main(String[] args) throws NoSuchFieldException, IllegalArgumentException, SlickException, IllegalAccessException {
        //Hack for set the library path
        System.setProperty( "java.library.path", "./slick/lib" );
        Field fieldSysPath = ClassLoader.class.getDeclaredField( "sys_paths" );
        fieldSysPath.setAccessible( true );
        fieldSysPath.set( null, null );

        Music intro = new Music("music/ditlIntro.wav");
        intro.addListener(new MusicListener() {
            public void musicEnded(Music intro) {
                System.out.println("Swithching to loop");
                try {
                    Music music = new Music("music/ditlBoucle.wav");
                    music.loop();
                } catch (SlickException ex) {
                    ex.printStackTrace();
                }
            }

            public void musicSwapped(Music music, Music newMusic) {
                return;
            }
        });

        intro.play();
        AppGameContainer app = new AppGameContainer(new Main());
        app.setDisplayMode(Main.width, Main.height, Main.fullscreen);
        app.setSmoothDeltas(true);
        app.setTargetFrameRate(Main.fpslimit);
        app.setShowFPS(showFPS);
        app.start();
    }

    public Main() {
        super(title);
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
//        this.addState(new MainMenu(MAINMENU));
        this.addState(new GamePlay(GAMEPLAY));
//        this.addState(new GameOver(GAMEOVER));
    }

}
