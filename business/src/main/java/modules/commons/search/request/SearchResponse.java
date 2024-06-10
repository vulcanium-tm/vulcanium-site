package modules.commons.search.request;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchResponse {


private int count;
private int page;
private int start;
private List<SearchItem> items = new ArrayList<>();
private List<Aggregation> aggregations = new ArrayList<>();

}