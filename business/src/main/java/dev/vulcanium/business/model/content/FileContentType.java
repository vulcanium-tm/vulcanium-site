package dev.vulcanium.business.model.content;

/**
 * Enum defining type of static content.
 * Currently following type of static content can be store and managed within
 * Shop CMS system
 * <pre>
 * 1. Static content like JS, CSS file etc
 * 2. Digital Data (audio,video)
 * </pre>
 *
 * StaticContentType will be used to distinguish between Digital data and other type of static data
 * stored with in the system.
 */
public enum FileContentType
{
	STATIC_FILE, IMAGE, LOGO, PRODUCT, PRODUCTLG, PROPERTY, VARIANT, MANUFACTURER, PRODUCT_DIGITAL, API_IMAGE, API_FILE
}
