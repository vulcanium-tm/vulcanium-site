package dev.vulcanium.site.tech.data;

import java.util.Observable;

public abstract class VisitorsAnalyticsData{
public abstract Observable getInnerLineChartData();

public abstract Observable getOutlineLineChartData();

public abstract Observable getPieChartData();
}


