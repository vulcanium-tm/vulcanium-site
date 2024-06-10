package modules.commons.search.request;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Search a word / sentence
 * Has filter
 * 		variants
 * 		brands
 * 		price range
 *
 */

@Setter
@Getter
public class SearchRequest {


private String searchString;
private List<String> filters;


}