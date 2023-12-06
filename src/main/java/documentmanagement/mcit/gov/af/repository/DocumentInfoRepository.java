package documentmanagement.mcit.gov.af.repository;

import documentmanagement.mcit.gov.af.domain.DocumentInfo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DocumentInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentInfoRepository extends JpaRepository<DocumentInfo, Long>, JpaSpecificationExecutor<DocumentInfo> {
    Page<DocumentInfo> findByNumberContainingIgnoreCaseAndSubjectContainingIgnoreCaseAndOrganizationContainingIgnoreCase(
        String number,
        String subject,
        String organization,
        Pageable pageable
    );

    Page<DocumentInfo> findByNumberContainingIgnoreCaseAndSubjectContainingIgnoreCase(String number, String subject, Pageable pageable);

    Page<DocumentInfo> findByNumberContainingIgnoreCase(String number, Pageable pageable);

    Page<DocumentInfo> findBySubjectContainingIgnoreCase(String subject, Pageable pageable);

    Page<DocumentInfo> findByOrganizationContainingIgnoreCase(String organization, Pageable pageable);
    Page<DocumentInfo> findByOrderByIssuedateDesc(Pageable pageable);

    @Query("SELECT d FROM DocumentInfo d WHERE d.issuedate = :date ORDER BY d.id DESC")
    List<DocumentInfo> findLast10RecordsByDate(@Param("date") LocalDate date, Pageable pageable);
}
