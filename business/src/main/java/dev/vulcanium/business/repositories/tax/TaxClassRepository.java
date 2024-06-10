package dev.vulcanium.business.repositories.tax;

import dev.vulcanium.business.model.tax.taxclass.TaxClass;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaxClassRepository extends JpaRepository<TaxClass, Long> {
	
	@Query("select t from TaxClass t left join fetch t.merchantStore tm where tm.id=?1")
	List<TaxClass> findByStore(Integer id);
	
	@Query("select t from TaxClass t left join fetch t.merchantStore tm where t.code=?1")
	TaxClass findByCode(String code);
	
	@Query("select t from TaxClass t left join fetch t.merchantStore tm where tm.id=?1 and t.code=?2")
	TaxClass findByStoreAndCode(Integer id, String code);


}
