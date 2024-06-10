package dev.vulcanium.site.tech.application.config;

import dev.vulcanium.site.tech.utils.LanguageUtils;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import dev.vulcanium.business.model.reference.language.Language;

@Component
public class LanguageArgumentResolver implements HandlerMethodArgumentResolver {


@Autowired
private LanguageUtils languageUtils;

@Override
public boolean supportsParameter(MethodParameter parameter) {
	return parameter.getParameterType().equals(Language.class);
}

@Override
public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                              NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
	
	HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
	
	return languageUtils.getRESTLanguage(request, webRequest);
}

}
