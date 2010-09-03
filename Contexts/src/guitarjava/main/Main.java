/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guitarjava.main;

import guitarjava.graphics.Graphics2DContext;
import guitarjava.graphics.GraphicsInterface;

/**
 *
 * @author brunojadami
 */
public class Main
{
    /** Program's main entry point.
     * @param args command line arguments.
     */
    public static void main(String[] args)
    {
        GraphicsInterface graphics = new Graphics2DContext();
        graphics.init();
    }
    
}
