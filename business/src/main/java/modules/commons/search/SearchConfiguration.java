package modules.commons.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import modules.commons.search.configuration.Credentials;

@Setter
@Getter
public class SearchConfiguration {

private List<String> merchants = new ArrayList<>();
private List<String> languages = new ArrayList<>();


private List<String> hosts = new ArrayList<>();

private Credentials credentials = null;

private Map<String,String> mappings = new HashMap<>();
private Map<String,String> settings = new HashMap<>();

}