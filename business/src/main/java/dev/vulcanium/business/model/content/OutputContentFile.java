package dev.vulcanium.business.model.content;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * Data class responsible for carrying out static content data from Infispan cache to
 * service layer.
 */
@Setter
@Getter
public class OutputContentFile extends StaticContentFile implements Serializable
{
private static final long serialVersionUID = 1L;
private ByteArrayOutputStream file;

}