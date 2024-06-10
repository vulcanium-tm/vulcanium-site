package dev.vulcanium.business.model.content;

import java.io.InputStream;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InputContentFile extends StaticContentFile implements Serializable
{

private static final long serialVersionUID = 1L;

private InputStream file;
private String path;


}