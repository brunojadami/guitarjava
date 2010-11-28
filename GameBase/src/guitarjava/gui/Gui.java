package guitarjava.gui;

import guitarjava.game.GameEngine;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javazoom.jl.decoder.JavaLayerException;

/**
 * This class controls all the gui of the game.
 * @author lucasjadami
 */
public class Gui extends WindowAdapter
{
    private MenuLoading menuLoading; // Menu loading gui.
    private GameLoading gameLoading; // Game loading gui.
    private Menu menu; // Menu gui.

    /**
     * Create the gui, starts the menu loading gui
     */
    public Gui()
    {
        createMenuLoading();
    }

    /**
     * Closed window event.
     * @param e Window event.
     */
    @Override
    public void windowClosed(WindowEvent e)
    {
        if (ErrorWindow.hasError())
            return;
        
        if (e.getSource() == menuLoading)
        {
            menuLoading.setVisible(false);
            createMenu();
        }
        else if (e.getSource() == menu)
        {
            if (e.getID() == Menu.CLOSED_TO_REQUEST_REFRESH)
            {
                menu.setVisible(false);
                createMenuLoading();
            }
            else if (e.getID() == Menu.CLOSED_TO_PLAY)
            {
                menu.setVisible(false);
                createGameLoading();
            }
        }
        else if (e.getSource() == gameLoading)
        {
            gameLoading.setVisible(false);
            createGame();
        }
    }

    /**
     * Window closing event. Called when the game is closed.
     * @param e Window event.
     */
    @Override
    public void windowClosing(WindowEvent e)
    {
        if (gameLoading != null && e.getSource() == gameLoading.getGameWindow() && !ErrorWindow.hasError())
        {
            GameEngine gameEngine = gameLoading.getGameEngine();
            gameEngine.stop();
            if (gameEngine.hasHighScore())
            {
                String player = JOptionPane.showInputDialog
			("You beat this music highscore! Enter your name!");

                if (player != null)
                {
                    boolean success = gameEngine.saveHighScore(player);

                    if (!success)
                        JOptionPane.showMessageDialog(menuLoading, "Could not save the highscore!");
                }
            }
            createMenuLoading();
        }
    }

    /**
     * Creates the menu loading.
     */
    private void createMenuLoading()
    {
        menuLoading = new MenuLoading();
        menuLoading.addWindowListener(this);
        menuLoading.setVisible(true);
        menuLoading.load();
    }

    /**
     * Creates the menu to choose the musics.
     */
    private void createMenu()
    {      
        menu = new Menu();
        menu.addWindowListener(this);    
        menu.translateToMusicsList(menuLoading.getMusics());
        menu.setVisible(true);
    }

    /**
     * Creates the game loading.
     */
    private void createGameLoading()
    {
        gameLoading = new GameLoading(menu.getMusicToPlay());
        gameLoading.addWindowListener(this);
        gameLoading.setVisible(true);
        gameLoading.load();
    }

    /**
     * Creates the game.
     */
    private void createGame()
    {
        gameLoading.getGameEngine().start();
    }
}
