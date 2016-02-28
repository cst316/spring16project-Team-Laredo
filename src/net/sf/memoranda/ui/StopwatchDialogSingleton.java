package net.sf.memoranda.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
/**
 * Allows only one instance of the stopwatch
 * to exist at a time.
 * @author Benjamin Paothatat
 * @since 2/28/2016
 */
public final class StopwatchDialogSingleton {
    private static StopwatchDialog stopwatch = null;
    private StopwatchDialogSingleton(){}
    
    public static synchronized StopwatchDialog getStopwatch(Frame frame){
    	if(stopwatch == null){
    		
    		stopwatch = new StopwatchDialog(frame);
    	}
    	Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        stopwatch.setLocation((frmSize.width - stopwatch.getSize().width) / 2 + loc.x, 
        		(frmSize.height - stopwatch.getSize().height) / 2 + loc.y);
        stopwatch.setVisible(true);
    	return stopwatch;
    }
}
