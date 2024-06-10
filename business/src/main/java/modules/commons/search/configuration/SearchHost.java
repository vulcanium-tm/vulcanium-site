package modules.commons.search.configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchHost {

private String host;
private int port;
private String scheme;

}