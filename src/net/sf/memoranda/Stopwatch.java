package net.sf.memoranda;

import java.util.concurrent.TimeUnit;


/**
 * Created by tyler on 2/10/16.
 */
public class Stopwatch {

	private long m_elapsed = 0;
	private long m_startTime;
	private boolean m_isRunning = false;
	
	/**
	 * Default constructor. Can be used to restart Stopwatch
	 */
	public Stopwatch() {
	}
	
	/**
	 * Creates a Stopwatch with elapsed time. Will convert appropriately. Can be used to restart a Stopwatch. Must be positive.
	 * @param elapsed The preset amount of time elapsed.
	 * @param u The TimeUnit the given time is in. 
	 */
	public Stopwatch(long elapsed, TimeUnit u) {
		if(elapsed < 0) {
			System.out.println("[Error]: Invalid Duration");
			elapsed = 0;
		}
		switch(u) {
			case HOURS:
				m_elapsed = elapsed * 3600000;
				break;
			case MINUTES:
				m_elapsed = elapsed * 60000;
				break;
			case SECONDS:
				m_elapsed = elapsed * 1000;
				break;
			case MILLISECONDS:
				m_elapsed = elapsed;
				break;
			default:
				m_elapsed = 0;
				break;
		}
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
			switch(u) {
			case HOURS:
				return (m_elapsed + (System.currentTimeMillis() - m_startTime)) / 3600000;
			case MINUTES:
				return (m_elapsed + (System.currentTimeMillis() - m_startTime)) / 60000;
			case SECONDS:
				return (m_elapsed + (System.currentTimeMillis() - m_startTime)) / 1000;
			case MILLISECONDS:
				return (m_elapsed + (System.currentTimeMillis() - m_startTime));
			default:
				return 0;
			}
		}
		else {
			switch(u) {
				case HOURS:
					return m_elapsed / 3600000;
				case MINUTES:
					return m_elapsed / 60000;
				case SECONDS:
					return m_elapsed / 1000;
				case MILLISECONDS:
					return m_elapsed;
				default:
					return 0;
			}
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
		int hours = total / 3600;
		int hold = total % 3600;
		int minutes = hold / 60;
		int sec = hold % 60;
		return "" + String.format("%02d", hours) + ":" 
				+ String.format("%02d", minutes) + ":" 
				+ String.format("%02d", sec) + "";
	}
	
	/**
	 * Removes an amount of time from the elapsed time. Must be positive.
	 * @param interupt The amount of time to remove.
	 * @param u The TimeUnit the given time is in.
	 */
	public void removeTime(int interupt, TimeUnit u) {
		if(interupt <= 0) {
			System.out.println("[Error]: Invalid Duration");
			interupt = 0;
		}
		switch(u) {
		case HOURS:
			interupt = interupt * 3600000;
			break;
		case MINUTES:
			interupt = interupt * 60000;
			break;
		case SECONDS:
			interupt = interupt * 1000;
			break;
		case MILLISECONDS:
			break;
		default:
			interupt = 0;
			break;
		}
		if(m_isRunning) {
			m_elapsed = m_elapsed + (System.currentTimeMillis() - m_startTime);
		}
		if(interupt <= m_elapsed) {
			m_elapsed = m_elapsed - interupt;
		}
	}
	
	/**
	 * Adds an amount of time from the elapsed time. Must be positive.
	 * @param interupt The amount of time to add.
	 * @param u The TimeUnit the given time is in.
	 */
	public void addTime(int interupt, TimeUnit u) {
		if(interupt <= 0) {
			System.out.println("[Error]: Invalid Duration");
			interupt = 0;
		}
		switch(u) {
		case HOURS:
			interupt = interupt * 3600000;
			break;
		case MINUTES:
			interupt = interupt * 60000;
			break;
		case SECONDS:
			interupt = interupt * 1000;
			break;
		case MILLISECONDS:
			break;
		default:
			interupt = 0;
			break;
		}
		if(m_isRunning) {
			m_elapsed = m_elapsed + (System.currentTimeMillis() - m_startTime);
		}
		m_elapsed = m_elapsed + interupt;
	}
	
	/**
	 * Parses a String representation of time into milliseconds.
	 * @param sTime A string representation of time in format H:M:S
	 * @return The given time in milliseconds
	 */
	public static int parseTime(String sTime) {
		int iTime = 0;
		String delims = "[:]";
		String[] strings = sTime.split(delims);
		if (strings.length > 3) {
			return 0;
		}
		int[] numbers = new int[3];
		try {
			for (int i = 0; i < 3; i++) {
				numbers[i] = Integer.parseInt(strings[i]);
			}
		}
		catch (NumberFormatException n){
			return 0;
		}
		iTime = (numbers[0]*3600000) + (numbers[1]*60000) + (numbers[2]*1000);
		return iTime;
	}
}