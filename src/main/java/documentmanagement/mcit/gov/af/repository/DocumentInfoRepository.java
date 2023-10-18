package documentmanagement.mcit.gov.af.repository;

import documentmanagement.mcit.gov.af.domain.DocumentInfo;

import java.time.LocalDate;
import java.util.List;

import org.ehcache.shadow.org.terracotta.offheapstore.paging.Page;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DocumentInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentInfoRepository extends JpaRepository<DocumentInfo, Long>, JpaSpecificationExecutor<DocumentInfo>{

    List<DocumentInfo> findByNumberContainingIgnoreCaseAndSubjectContainingIgnoreCaseAndOrganizationContainingIgnoreCase(
        String number, String subject, String organization, Sort sort);

    List<DocumentInfo> findByNumberContainingIgnoreCaseAndSubjectContainingIgnoreCase(String number, String subject, Sort sort);

    List<DocumentInfo> findByNumberContainingIgnoreCase(String number, Sort sort);

    List<DocumentInfo> findBySubjectContainingIgnoreCase(String subject, Sort sort);

    List<DocumentInfo> findByOrganizationContainingIgnoreCase(String organization, Sort sort);
}
