package dev.vulcanium.site.tech.store.api.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorEntity {
private String errorCode;
private String message;

}
