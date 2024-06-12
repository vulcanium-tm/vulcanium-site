package modules.commons.search.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchConfiguration {

private List<String> languages = new ArrayList<>();
private String clusterName;

/**
 * When using a test ssl certficate
 */
private String jksAbsolutePath;

private List<SearchHost> hosts = new ArrayList<>();

private Credentials credentials = null;

/**
 * name:text
 * description:text
 * ...
 */
private Map<String, String> productMappings = new HashMap<>();
private Map<String, String> keywordsMappings = new HashMap<>();
private Map<String, String> settings = new HashMap<>();


}