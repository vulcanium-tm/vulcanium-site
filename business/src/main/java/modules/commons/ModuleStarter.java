package modules.commons;

import java.util.List;

public interface ModuleStarter {

/**
 * configuration (json serialized to String)
 * @return
 */
String getConfigurable();
void setConfigurable(String config);
/**
 * Unique module code
 * @return String
 */
String getUniqueCode();
void setUniqueCode(String uniqueCode);
void setModuleType(ModuleType moduleType);
/**
 * Payment or Shipping
 * @return ModuleType
 */
ModuleType getModuleType();
void setSupportedCountry(List<String> supportedCountry);
/**
 * All country supported by this module
 * @return
 */
List<String> getSupportedCountry();
void setLogo(String logo);
/**
 * Base64 logo of the module
 * @return String
 */
String getLogo();

}