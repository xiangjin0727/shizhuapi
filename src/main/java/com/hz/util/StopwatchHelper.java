package com.hz.util;

/*
 * @(#)StopWatchHelper.java 2011-7-4
 * 
 * jeaw 版权所有2006~2015。
 */


/**
 * 描述：提供一组方法和属性，可用于准确地测量运行时间。
 *
 * @author lch
 * @version 1.0 2011-7-4
 */
public class StopwatchHelper {
	private Long watchStartTime;
	private Long watchStopTime;
	
	public StopwatchHelper() {
		watchStartTime = -1L;
		watchStopTime = -1L;
	}
	/**
	 * 描述:开始或继续测量某个时间间隔的运行时间
	 * @author lch 2011-7-4
	 */
	public void start() {
		watchStartTime = System.currentTimeMillis();
	}
	/**
	 * 描述:停止测量某个时间间隔的运行时间
	 * @author lch 2011-7-4
	 */
	public void stop() {
		watchStopTime = System.currentTimeMillis();
	}
	/**
	 * 描述:停止时间间隔测量，并将运行时间重置为零
	 * @author lch 2011-7-4
	 */
	public void reset() {
		watchStartTime = -1L;
		watchStopTime = -1L;
	}
	/**
	 * 描述:开始或继续测量某个时间间隔的运行时间
	 * @author lch 2011-7-4
	 */
	public void reStart() {
		this.reset();
		this.start();
	}
	/**
	 * 描述:获取当前实例测量得出的总运行时间（以毫秒为单位）
	 * @return
	 * @author lch 2011-7-4
	 */
	public Long elapsedMilliseconds() {
		if (watchStopTime.equals(-1L)) {
			this.stop();
		}
		return watchStopTime - watchStartTime;
	}
}
