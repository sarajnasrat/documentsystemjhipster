package documentmanagement.mcit.gov.af.service;

import documentmanagement.mcit.gov.af.domain.*; // for static metamodels
import documentmanagement.mcit.gov.af.domain.DocumentInfo;
import documentmanagement.mcit.gov.af.repository.DocumentInfoRepository;
import documentmanagement.mcit.gov.af.service.criteria.DocumentInfoCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link DocumentInfo} entities in the database.
 * The main input is a {@link DocumentInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DocumentInfo} or a {@link Page} of {@link DocumentInfo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DocumentInfoQueryService extends QueryService<DocumentInfo> {

    private final Logger log = LoggerFactory.getLogger(DocumentInfoQueryService.class);

    private final DocumentInfoRepository documentInfoRepository;

    public DocumentInfoQueryService(DocumentInfoRepository documentInfoRepository) {
        this.documentInfoRepository = documentInfoRepository;
    }

    /**
     * Return a {@link List} of {@link DocumentInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DocumentInfo> findByCriteria(DocumentInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DocumentInfo> specification = createSpecification(criteria);
        return documentInfoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DocumentInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentInfo> findByCriteria(DocumentInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DocumentInfo> specification = createSpecification(criteria);
        return documentInfoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DocumentInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DocumentInfo> specification = createSpecification(criteria);
        return documentInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link DocumentInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DocumentInfo> createSpecification(DocumentInfoCriteria criteria) {
        Specification<DocumentInfo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DocumentInfo_.id));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumber(), DocumentInfo_.number));
            }
            if (criteria.getRegisteredNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRegisteredNumber(), DocumentInfo_.registeredNumber));
            }
            if (criteria.getIssuedate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIssuedate(), DocumentInfo_.issuedate));
            }
            if (criteria.getSubject() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubject(), DocumentInfo_.subject));
            }
            if (criteria.getDpriority() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDpriority(), DocumentInfo_.dpriority));
            }
            if (criteria.getContent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContent(), DocumentInfo_.content));
            }
            if (criteria.getOrganization() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganization(), DocumentInfo_.organization));
            }
        }
        return specification;
    }
}
