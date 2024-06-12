package dev.vulcanium.site.tech.store.api.system;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.business.services.reference.language.LanguageService;
import dev.vulcanium.business.utils.EmailTemplatesUtils;
import dev.vulcanium.site.tech.model.shop.ContactForm;
import io.swagger.annotations.*;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Locale;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api")
@Api(tags = {"Contact form api"})
@SwaggerDefinition(tags = {
		@Tag(name = "Contact store resource", description = "Contact form")
})
public class ContactApi {


@Inject private LanguageService languageService;

@Inject private EmailTemplatesUtils emailTemplatesUtils;

@PostMapping("/contact")
@ApiOperation(
		httpMethod = "POST",
		value = "Sends an email contact us to store owner",
		notes = "",
		produces = "application/json")
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
})
public ResponseEntity<Void> contact(
		@Valid @RequestBody ContactForm contact,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language,
		HttpServletRequest request) {
	Locale locale = languageService.toLocale(language, merchantStore);
	emailTemplatesUtils.sendContactEmail(contact, merchantStore, locale, request.getContextPath());
	return new ResponseEntity<Void>(HttpStatus.OK);
}
}
