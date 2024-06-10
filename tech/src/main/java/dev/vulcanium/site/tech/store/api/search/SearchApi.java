package dev.vulcanium.site.tech.store.api.search;

import java.util.List;

import jakarta.inject.Inject;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.vulcanium.business.model.merchant.MerchantStore;
import dev.vulcanium.business.model.reference.language.Language;
import dev.vulcanium.site.tech.model.catalog.SearchProductRequest;
import dev.vulcanium.site.tech.model.entity.ValueList;
import dev.vulcanium.site.tech.store.controller.search.facade.SearchFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import modules.commons.search.request.SearchItem;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Api for searching shop catalog based on search term when filtering products based on product
 * attribute is required, see /api/product
 */
@RestController
@RequestMapping("/api")
@Api(tags = {"Search products and search word/sentence completion functionality (Search Api)"})
@SwaggerDefinition(tags = {
		@Tag(name = "Search products resource", description = "Search products and search term completion functionality")
})
public class SearchApi {

@Inject private SearchFacade searchFacade;


/**
 * Search products from underlying elastic search
 */
@PostMapping("/search")
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
})

public @ResponseBody List<SearchItem> search(
		@RequestBody SearchProductRequest searchRequest,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	
	return searchFacade.search(merchantStore, language, searchRequest);
}

@PostMapping("/search/autocomplete")
@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
})
public @ResponseBody ValueList autocomplete(
		@RequestBody SearchProductRequest searchRequest,
		@ApiIgnore MerchantStore merchantStore,
		@ApiIgnore Language language) {
	return searchFacade.autocompleteRequest(searchRequest.getQuery(), merchantStore, language);
}
}
