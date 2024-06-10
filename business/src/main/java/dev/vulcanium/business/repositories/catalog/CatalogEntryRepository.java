package dev.vulcanium.business.repositories.catalog;

import dev.vulcanium.business.model.catalog.CatalogCategoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogEntryRepository extends JpaRepository<CatalogCategoryEntry, Long> {

}
