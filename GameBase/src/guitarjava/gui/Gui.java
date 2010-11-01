/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package guitarjava.gui;

import guitarjava.game.GameEngine;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javazoom.jl.decoder.JavaLayerException;

/**
 *
 * @author Lucas
 */
public class Gui extends WindowAdapter
{
    private MenuLoading menuLoading;
    private GameLoading gameLoading;
    private Menu menu;

    public Gui()
    {
        createMenuLoading();
    }

    @Override
    public void windowClosed(WindowEvent e)
    {
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

    @Override
    public void windowClosing(WindowEvent e)
    {
        if (gameLoading != null && e.getSource() == gameLoading.getGameWindow())
        {
            GameEngine gameEngine = gameLoading.getGameEngine();
            gameEngine.stop();
            createMenuLoading();
        }
    }

    private void createMenuLoading()
    {
        menuLoading = new MenuLoading();
        menuLoading.addWindowListener(this);
        menuLoading.setVisible(true);
        menuLoading.load();
    }

    private void createMenu()
    {      
        menu = new Menu();
        menu.addWindowListener(this);    
        menu.translateToMusicsList(menuLoading.getMusics());
        menu.setVisible(true);
    }

    private void createGameLoading()
    {
        gameLoading = new GameLoading(menu.getMusicToPlay());
        gameLoading.addWindowListener(this);
        gameLoading.setVisible(true);
        gameLoading.load();
    }
    
    private void createGame()
    {
        try
        {
            gameLoading.getGameEngine().start();
        }
        catch (JavaLayerException ex)
        {
            // TODO
        }
    }
}
