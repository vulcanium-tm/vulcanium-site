package dev.vulcanium.business.utils;

import dev.vulcanium.site.tech.model.entity.ReadableEntityList;
import java.util.List;
import org.springframework.data.domain.Page;

public final class ReadableEntityUtil {

private ReadableEntityUtil() {}

public static  <T,R> ReadableEntityList<R> createReadableList(Page<T> entityList, List<R> items) {
	ReadableEntityList<R> readableList = new ReadableEntityList<>();
	readableList.setItems(items);
	readableList.setTotalPages(entityList.getTotalPages());
	readableList.setRecordsTotal(entityList.getTotalElements());
	readableList.setNumber(items.size());
	return readableList;
}
}
