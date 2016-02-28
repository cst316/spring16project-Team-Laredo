package net.sf.memoranda.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;

public class StopwatchDialogSingleton {
    private static StopwatchDialog stopwatch = null;
    private StopwatchDialogSingleton(){}
    
    public static StopwatchDialog getStopwatch(Frame frame){
    	System.out.println("test");
    	if(stopwatch == null){
    		
    		stopwatch = new StopwatchDialog(frame);
    	}
    	Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        stopwatch.setLocation((frmSize.width - stopwatch.getSize().width) / 2 + loc.x, (frmSize.height - stopwatch.getSize().height) / 2 + loc.y);
        stopwatch.setVisible(true);
    	return stopwatch;
    }
}
