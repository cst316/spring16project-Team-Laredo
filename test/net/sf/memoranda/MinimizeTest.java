package net.sf.memoranda;

import java.awt.Robot;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import net.sf.memoranda.ui.*;


/**
 * Created by Christopher on 2/10/16.
 */
public class MinimizeTest {
	
	private static App testapp = null;
    
    @Before
    public void setUp() throws Exception {
    	testapp = new App(true);
    }



    @Test
    public void testIconifiedState() throws Exception {
    	App.minimizeWindow();
    	assertTrue(App.getFrame().getState()==JFrame.ICONIFIED);
    	
    	App.getFrame().setState(JFrame.NORMAL);
    	assertTrue(App.getFrame().getState()==JFrame.NORMAL);
    }
    
    @Test
    public void testCloseWindow() throws Exception {
    	
    	
    }
}