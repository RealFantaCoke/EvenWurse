package tk.wurst_client.analytics;

import tk.wurst_client.WurstClient;
import tk.wurst_client.analytics.JGoogleAnalyticsTracker.GoogleAnalyticsVersion;
import tk.wurst_client.bot.WurstBot;

public class GoogleAnalyticsManagerImpl implements AnalyticsManager {
	private final JGoogleAnalyticsTracker tracker;
	
	public final String ANALYTICS_CODE;
	public final String HOSTNAME;
	public long lastRequest;
	
	public GoogleAnalyticsManagerImpl(String analyticsCode, String hostName)
	{
		tracker =
			new JGoogleAnalyticsTracker(new AnalyticsConfigData(analyticsCode),
				GoogleAnalyticsVersion.V_4_7_2);
		ANALYTICS_CODE = analyticsCode;
		HOSTNAME = hostName;
		lastRequest = System.currentTimeMillis();
		JGoogleAnalyticsTracker.setProxy(System.getenv("http_proxy"));
	}
	
	public boolean shouldTrack()
	{
		return WurstClient.INSTANCE.options.google_analytics.enabled
			&& !WurstBot.isEnabled();
	}
	
	public void trackPageView(String url, String title)
	{
		if(!shouldTrack())
			return;
		tracker.trackPageView(url, title, HOSTNAME);
		lastRequest = System.currentTimeMillis();
	}
	
	public void trackPageViewFromReferrer(String url, String title,
		String referrerSite, String referrerPage)
	{
		if(!shouldTrack())
			return;
		tracker.trackPageViewFromReferrer(url, title, HOSTNAME, referrerSite,
			referrerPage);
		lastRequest = System.currentTimeMillis();
	}
	
	public void trackPageViewFromSearch(String url, String title,
		String searchSite, String keywords)
	{
		if(!shouldTrack())
			return;
		tracker.trackPageViewFromSearch(url, title, HOSTNAME, searchSite,
			keywords);
		lastRequest = System.currentTimeMillis();
	}
	
	public void trackEvent(String category, String action)
	{
		if(!shouldTrack())
			return;
		tracker.trackEvent(category, action);
		lastRequest = System.currentTimeMillis();
	}
	
	public void trackEvent(String category, String action, String label)
	{
		if(!shouldTrack())
			return;
		tracker.trackEvent(category, action, label);
		lastRequest = System.currentTimeMillis();
	}
	
	public void trackEvent(String category, String action, String label,
		int value)
	{
		if(!shouldTrack())
			return;
		tracker.trackEvent(category, action, label, value);
		lastRequest = System.currentTimeMillis();
	}
	
	public void makeCustomRequest(AnalyticsRequestData data)
	{
		if(!shouldTrack())
			return;
		tracker.makeCustomRequest(data);
		lastRequest = System.currentTimeMillis();
	}
}