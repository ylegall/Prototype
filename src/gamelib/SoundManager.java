package gamelib;

import java.net.URL;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 *  @author Yann Le Gall
 *  ylegall@gmail.com
 *  Nov 13, 2009 12:33:48 AM
 */
public class SoundManager {

    private static final AudioFormat format = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED,
            AudioSystem.NOT_SPECIFIED,
            16, 2, 4,
            AudioSystem.NOT_SPECIFIED,
            true);
    
    private static DataLine.Info info = new DataLine.Info(Clip.class, format);

    private SoundManager() {}

    public static Clip getSound(String file) throws Exception
    {
        /* Create our clip object */
        Clip clip = AudioSystem.getClip();
        URL url = SoundManager.class.getResource("/resources/sounds/"+ file);
        AudioInputStream ais = AudioSystem.getAudioInputStream(url);
        clip.open(ais);
        return clip;
    }

    public static Clip getClip(String file)  throws Exception
    {
        URL url = SoundManager.class.getResource("/resources/sounds/"+ file);
        AudioInputStream ais = AudioSystem.getAudioInputStream(url);
        Clip clip = (Clip)AudioSystem.getLine(info);
        clip.open(ais);
        return clip;
    }

    public static void playSound(Clip clip) {
        if(clip.isRunning())
        {
            clip.flush();
        }
        clip.setFramePosition(0);
        clip.start();
    }

}
