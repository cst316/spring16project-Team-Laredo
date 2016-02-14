package net.sf.memoranda;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by tyler on 2/12/16.
 */
public class StopwatchTest {
	private static Stopwatch emptyWatch = null;
    private static Stopwatch negativeWatch = null;
    private static Stopwatch dayWatch = null;
    private static Stopwatch zeroWatch = null;
    private static Stopwatch oneWatch = null;
    private static Stopwatch manyWatch = null;
	
    @Before
	public void Setup() {
    	emptyWatch = new Stopwatch();
    	negativeWatch = new Stopwatch(-5, TimeUnit.HOURS);
    	zeroWatch = new Stopwatch(0, TimeUnit.HOURS);
    	oneWatch = new Stopwatch(1, TimeUnit.MINUTES);
    	manyWatch = new Stopwatch(359945000, TimeUnit.MILLISECONDS); //Time == 99:59:05
    	dayWatch = new Stopwatch (1, TimeUnit.DAYS);
	}
	
	@After
	public void Teardown() {
		emptyWatch = null;
	    negativeWatch = null;
	    zeroWatch = null;
	    oneWatch = null;
	    manyWatch = null;
	    dayWatch = null;
	}
	
	@Test
	public void testStartStop() throws InterruptedException{
		//I figure accurate to about a tenth of a second should be more than enough.
		emptyWatch.startStopwatch();
		TimeUnit.MILLISECONDS.sleep(100);
		emptyWatch.stopStopwatch();
		assertTrue(emptyWatch.getTime(TimeUnit.MILLISECONDS) >= 100 
				&& emptyWatch.getTime(TimeUnit.MILLISECONDS) < 200);
		
		negativeWatch.startStopwatch();
		TimeUnit.MILLISECONDS.sleep(100);
		negativeWatch.stopStopwatch();
		assertTrue(negativeWatch.getTime(TimeUnit.MILLISECONDS) >= 100 
				&& negativeWatch.getTime(TimeUnit.MILLISECONDS) < 200);
		
		oneWatch.startStopwatch();
		TimeUnit.MILLISECONDS.sleep(100);
		oneWatch.stopStopwatch();
		assertTrue(oneWatch.getTime(TimeUnit.MILLISECONDS) >= 60100 
				&& oneWatch.getTime(TimeUnit.MILLISECONDS) < 60200);
		
		zeroWatch.startStopwatch();
		TimeUnit.MILLISECONDS.sleep(100);
		zeroWatch.stopStopwatch();
		assertTrue(zeroWatch.getTime(TimeUnit.MILLISECONDS) >= 100 
				&& zeroWatch.getTime(TimeUnit.MILLISECONDS) < 200);
		
		manyWatch.startStopwatch();
		TimeUnit.MILLISECONDS.sleep(100);
		manyWatch.stopStopwatch();
		assertTrue(manyWatch.getTime(TimeUnit.MILLISECONDS) >= 359945100 
				&& manyWatch.getTime(TimeUnit.MILLISECONDS) < 359945200);
		
	}
	
	@Test
	public void testGetTimeString() {
		assertTrue(manyWatch.getTimeString().equals("99:59:05"));
	}
	
	@Test
	public void testGetTime() {
		assertTrue(manyWatch.getTime(TimeUnit.MILLISECONDS) == 359945000);
		assertTrue(manyWatch.getTime(TimeUnit.SECONDS) == 359945);
		assertTrue(manyWatch.getTime(TimeUnit.MINUTES) == 5999);
		assertTrue(manyWatch.getTime(TimeUnit.HOURS) == 99);
		assertTrue(manyWatch.getTime(TimeUnit.DAYS) == 0);
		assertTrue(emptyWatch.getTime(TimeUnit.MILLISECONDS) == 0);
		assertTrue(emptyWatch.getTime(TimeUnit.SECONDS) == 0);
		assertTrue(emptyWatch.getTime(TimeUnit.MINUTES) == 0);
		assertTrue(emptyWatch.getTime(TimeUnit.HOURS) == 0);
		assertTrue(emptyWatch.getTime(TimeUnit.DAYS) == 0);
	}
	
	@Test
	public void testParseTime() {
		assertTrue(Stopwatch.parseTime("99:59:5") == 359945000);
		assertTrue(Stopwatch.parseTime("1:0:0") == 3600000);
		assertTrue(Stopwatch.parseTime("0:1:0") == 60000);
		assertTrue(Stopwatch.parseTime("0:0:1") == 1000);
		assertTrue(Stopwatch.parseTime("Pineapple") == 0);
		assertTrue(Stopwatch.parseTime("0:0:a") == 0);
		assertTrue(Stopwatch.parseTime("1:1:1:1") == 0);
	}
	
	@Test
	public void testRemoveTime() {
		manyWatch.removeTime(1, TimeUnit.HOURS);
		assertTrue(manyWatch.getTime(TimeUnit.MILLISECONDS) == 356345000);
		manyWatch.removeTime(1, TimeUnit.MINUTES);
		assertTrue(manyWatch.getTime(TimeUnit.MILLISECONDS) == 356285000);
		manyWatch.removeTime(1, TimeUnit.SECONDS);
		assertTrue(manyWatch.getTime(TimeUnit.MILLISECONDS) == 356284000);
		manyWatch.removeTime(1, TimeUnit.MILLISECONDS);
		assertTrue(manyWatch.getTime(TimeUnit.MILLISECONDS) == 356283999);
		manyWatch.removeTime(1, TimeUnit.DAYS);
		assertTrue(manyWatch.getTime(TimeUnit.MILLISECONDS) == 356283999);
	}
	
	@Test
	public void testAddTime() {
		zeroWatch.addTime(1, TimeUnit.HOURS);
		assertTrue(zeroWatch.getTime(TimeUnit.MILLISECONDS) == 3600000);
		zeroWatch.addTime(1, TimeUnit.MINUTES);
		assertTrue(zeroWatch.getTime(TimeUnit.MILLISECONDS) == 3660000);
		zeroWatch.addTime(1, TimeUnit.SECONDS);
		assertTrue(zeroWatch.getTime(TimeUnit.MILLISECONDS) == 3661000);
		zeroWatch.addTime(1, TimeUnit.MILLISECONDS);
		assertTrue(zeroWatch.getTime(TimeUnit.MILLISECONDS) == 3661001);
		zeroWatch.addTime(1, TimeUnit.DAYS);
		assertTrue(zeroWatch.getTime(TimeUnit.MILLISECONDS) == 3661001);
	}

}
