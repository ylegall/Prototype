package gamelib;

import java.io.BufferedInputStream;
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

    //private static final AudioFormat format = new AudioFormat(
            //AudioFormat.Encoding.PCM_SIGNED,
            //AudioSystem.NOT_SPECIFIED,
            //16, 2, 4,
            //AudioSystem.NOT_SPECIFIED,
            //true);

	private static AudioFormat format;
	private static DataLine.Info info;

	static
	{
		try{
			format = AudioSystem.getAudioInputStream(new BufferedInputStream(SoundManager.class.getResourceAsStream("/bin/resources/sounds/beam.wav"))).getFormat();
			info = new DataLine.Info(Clip.class, format);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}


	//private static JarFile jar = new JarFile("/");
    //JarFile jar = new JarFile(path);
    //JarEntry jen;
    //jen = new JarEntry("sounds/" + fileName);
    //audioInputStream = AudioSystem.getAudioInputStream( jar.getInputStream(jen) );

    private SoundManager() {}

    public static Clip getSound(String file) throws Exception
    {
    	// create our clip object
        Clip clip = AudioSystem.getClip();
        //URL url = SoundManager.class.getResource("/resources/sounds/"+ file);
        //AudioInputStream ais = AudioSystem.getAudioInputStream(url);

		AudioInputStream ais = AudioSystem.getAudioInputStream(
			new BufferedInputStream(SoundManager.class.getResourceAsStream("/bin/resources/sounds/" + file)));
        clip.open(ais);
        return clip;
    }

    public static Clip getClip(String file)  throws Exception
    {
        //URL url = SoundManager.class.getResource("/resources/sounds/"+ file);
        //AudioInputStream ais = AudioSystem.getAudioInputStream(url);

		AudioInputStream ais = AudioSystem.getAudioInputStream(
			new BufferedInputStream(SoundManager.class.getResourceAsStream("/bin/resources/sounds/" + file)));
        Clip clip = (Clip)AudioSystem.getLine(info);
        clip.open(ais);
        return clip;
    }

    public static void playSound(Clip clip) {
    	if (clip == null) return;
        if(clip.isRunning())
        {
            clip.flush();
        }
        clip.setFramePosition(0);
        clip.start();
    }

}
