package guitarjava.gui;

import guitarjava.game.Music;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The gui class that loads the musics from the disk.
 * @author lucasjadami
 */
public class MenuLoading extends Loading
{
    Map<String, List<Music>> musics; // Map containing all musics.

    /**
     * Contructor.
     */
    public MenuLoading()
    {
        
    }

    /**
     * Loads (async) all the musics from the disk.
     */
    @Override
    public void load()
    {
        loadThread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    loadMusics();
                }
                catch (Exception e)
                {
                    ErrorWindow error = new ErrorWindow(e, MenuLoading.this, "http://www.google.com");
                    error.showWindow();
                }

                for (WindowListener gui : getWindowListeners())
                    gui.windowClosed(new WindowEvent(MenuLoading.this, 0));
            }

        };

        super.load();
    }

    /**
     * @return The map containing all the musics.
     */
    public Map<String, List<Music>> getMusics()
    {
        return musics;
    }

    /**
     * Reads the musics from the disk and creates the music map.
     */
    private void loadMusics() throws Exception
    {
        File folder = new File("musics/xml/");

        File[] files = folder.listFiles(new FilenameFilter()
        {
            @Override
            public boolean accept(File dir, String name)
            {
                return name.endsWith(".xml");
            }
        });

        List<File> filesList = Arrays.asList(files);
        // Sorts the files.
        Collections.sort(filesList);

        int total = files.length;
        int loaded = 0;

        musics = new HashMap<String, List<Music>>();

        for (File file : filesList)
        {
            setState("Loading music file: " + file.getName());

            try
            {
                Music music = new Music(file.getPath(), "musics/mp3/" + file.getName().substring
			(0, file.getName().length() - 6) + ".mp3", "musics/score/" + file.getName().substring
			(0, file.getName().length() - 6) + ".sco");

                String key = music.getName();
                
                List<Music> musicsList = musics.get(key);

                if (musicsList == null)
                {
                    musicsList = new ArrayList<Music>();
                    musics.put(key, musicsList);
                }

                musicsList.add(music);       
            }
            catch (Exception e)
            {
                ErrorWindow error = new ErrorWindow(e, this, "http://www.google.com");
                error.showWindow();
            }

            loaded++;
            setProgress(loaded * 100 / total);
        }
    }
}
