package dev.vulcanium.business.modules.cms.product;

import dev.vulcanium.business.modules.cms.common.AssetsManager;
import java.io.Serializable;

public interface ProductAssetsManager
    extends AssetsManager, ProductImageGet, ProductImagePut, ProductImageRemove, Serializable {

}
