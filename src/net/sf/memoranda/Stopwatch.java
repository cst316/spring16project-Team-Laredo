package net.sf.memoranda;

import java.util.concurrent.TimeUnit;


/**
 * Created by tyler on 2/10/16.
 */
public class Stopwatch {
	private long m_elapsed;
	private long m_startTime;
	private boolean m_isRunning = false;
	
	/**
	 * Default constructor. Can be used to restart Stopwatch
	 */
	public Stopwatch() {
		m_elapsed = 0;
		m_isRunning = false;
	}
	
	/**
	 * Creates a Stopwatch with elapsed time. Will convert appropriately. Can be used to restart a Stopwatch. Must be positive.
	 * @param elapsed The preset amount of time elapsed.
	 * @param u The TimeUnit the given time is in. 
	 */
	public Stopwatch(long elapsed, TimeUnit u) throws NumberFormatException{
		if(elapsed < 0) {
			throw new NumberFormatException("Number is negative");
		}
		m_elapsed = convertToMillis(elapsed,u);
	}
	
	/**
	 * Starts the Stopwatch.
	 */
	public void startStopwatch() {
		m_startTime = System.currentTimeMillis();
		m_isRunning = true;
	}
	
	/**
	 * Stops the Stopwatch and stores the elapsed time.
	 */
	public void stopStopwatch() {
		m_elapsed = m_elapsed + (System.currentTimeMillis() - m_startTime);
		m_isRunning = false;
	}
	
	/**
	 * Returns the time in the requested units that has passed. Returns accurate time when running and when stopped. Will round down for larger units of time.
	 * @param u The TimeUnit to return the answer in.
	 * @return Elapsed The time in the requested unit.
	 */
	public long getTime(TimeUnit u) {
		if(m_isRunning) {
			return convertFromMillis(m_elapsed + (System.currentTimeMillis() - m_startTime),u);
		} else {
			return convertFromMillis(m_elapsed,u);
		}
	}
	
	/**
	 * Creates and returns a String in the form HH:MM:SS 
	 * @return A String representation of the time in HH:MM:SS
	 */
	public String getTimeString() {
		int total = 0;
		if(m_isRunning) {
			total = (int)(m_elapsed + (System.currentTimeMillis() - m_startTime)) / 1000;
		}
		else {
			total = (int)m_elapsed / 1000;
		}
		long hours = total / 3600;
		long hold = total % 3600;
		long minutes = hold / 60;
		long sec = hold % 60;
		if(hours >= 0 && minutes >= 0 && sec >= 0){
			return "" + String.format("%02d", hours) + ":" 
				+ String.format("%02d", minutes) + ":" 
				+ String.format("%02d", sec) + "";
		} else {
			m_elapsed = 0;
			if(m_isRunning) {
				startStopwatch();
			}
			return "00:00:00";
		}
	}
	
	/**
	 * Removes an amount of time from the elapsed time. Must be positive.
	 * @param time The amount of time to remove.
	 * @param u The TimeUnit the given time is in.
	 */
	public void removeTime(long time, TimeUnit u) {
		if(time < 0) {
			throw new NumberFormatException("Number is negative");
		}
		long interupt = convertToMillis(time,u);
		if(m_isRunning) {
			m_elapsed = m_elapsed + (System.currentTimeMillis() - m_startTime);
		}
		if(time <= m_elapsed && interupt > 0) {
			m_elapsed = m_elapsed - interupt;
		}
	}
	
	/**
	 * Adds an amount of time from the elapsed time. Must be positive.
	 * @param time The amount of time to add.
	 * @param u The TimeUnit the given time is in.
	 */
	public void addTime(long time, TimeUnit u){
		if(time < 0) {
			throw new NumberFormatException("Number is negative");
		}
		long interupt = convertToMillis(time,u);
		if(m_isRunning) {
			m_elapsed = m_elapsed + (System.currentTimeMillis() - m_startTime);
		}
		if(interupt > 0) {
			m_elapsed = m_elapsed + interupt;
		}
	}
	
	/**
	 * Parses a String representation of time into milliseconds.
	 * @param sTime A string representation of time in format H:M:S
	 * @return The given time in milliseconds
	 */
	public static long parseTime(String sTime) throws NumberFormatException{
		long iTime = 0;
		String delims = "[:]";
		String[] strings = sTime.split(delims);
		if (strings.length > 3) {
			return 0;
		}
		long[] numbers = new long[3];
		try {
			for (int i = 0; i < 3; i++) {
				numbers[i] = Long.parseLong(strings[i]);
			}
		}
		catch (NumberFormatException n){
			throw new NumberFormatException("Cannot parse String");
		}
		iTime = convertToMillis(numbers[0],TimeUnit.HOURS) + convertToMillis(numbers[1],TimeUnit.MINUTES) + convertToMillis(numbers[2],TimeUnit.SECONDS);
		if(iTime < 0) {
			throw new NumberFormatException("Time is too large to be expressed in " + TimeUnit.MILLISECONDS.toString());
		}
		return iTime;
	}
	
	/**
	 * Converts a given input into milliseconds based on the passed TimeUnit
	 * @param time The time to be converted.
	 * @param u The unit to convert from.
	 * @return The given time in milliseconds.
	 */
	public static long convertToMillis(long time, TimeUnit u) {
		switch(u) {
		case HOURS:
			time =  time * 3600000;
			break;
		case MINUTES:
			time =  time * 60000;
			break;
		case SECONDS:
			time =  time * 1000;
			break;
		case MILLISECONDS:
			break;
		default:
			time = 0;
			break;
		}
		if(time >= 0) {
			return time;
		} else {
			return 0;
		}
	}
	
	/**
	 * Converts the given time in milliseconds to the given TimeUnit
	 * @param time The time, in milliseconds, to be converted.
	 * @param u The TimeUnit to convert to.
	 * @return The time in the given TimeUnit.
	 */
	public static long convertFromMillis(long time, TimeUnit u) {
		switch(u) {
		case HOURS:
			time = time / 3600000;
			break;
		case MINUTES:
			time = time / 60000;
			break;
		case SECONDS:
			time = time / 1000;
			break;
		case MILLISECONDS:
			break;
		default:
			time = 0;
			break;
		}
		if(time >= 0) {
			return time;
		} else {
			return 0;
		}
	}
}