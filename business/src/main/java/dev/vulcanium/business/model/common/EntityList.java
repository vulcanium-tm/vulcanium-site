package dev.vulcanium.business.model.common;

import java.io.Serializable;

public class EntityList implements Serializable {

private static final long serialVersionUID = 1L;
private long totalCount;
private int totalPages;
public int getTotalPages() {
	return totalPages == 0 ? totalPages+1:totalPages;
}
public void setTotalPages(int totalPage) {
	this.totalPages = totalPage;
}
public long getTotalCount() {
	return totalCount;
}
public void setTotalCount(long totalCount) {
	this.totalCount = totalCount;
}

}
