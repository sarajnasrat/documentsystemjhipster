package documentmanagement.mcit.gov.af.service;

import documentmanagement.mcit.gov.af.domain.DocumentInfo;
import documentmanagement.mcit.gov.af.repository.DocumentInfoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DocumentInfo}.
 */
@Service
@Transactional
public class DocumentInfoService {

    private final Logger log = LoggerFactory.getLogger(DocumentInfoService.class);

    private final DocumentInfoRepository documentInfoRepository;

    public DocumentInfoService(DocumentInfoRepository documentInfoRepository) {
        this.documentInfoRepository = documentInfoRepository;
    }

    /**
     * Save a documentInfo.
     *
     * @param documentInfo the entity to save.
     * @return the persisted entity.
     */
    public DocumentInfo save(DocumentInfo documentInfo) {
        log.debug("Request to save DocumentInfo : {}", documentInfo);
        return documentInfoRepository.save(documentInfo);
    }

    /**
     * Update a documentInfo.
     *
     * @param documentInfo the entity to save.
     * @return the persisted entity.
     */
    public DocumentInfo update(DocumentInfo documentInfo) {
        log.debug("Request to update DocumentInfo : {}", documentInfo);
        return documentInfoRepository.save(documentInfo);
    }

    /**
     * Partially update a documentInfo.
     *
     * @param documentInfo the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DocumentInfo> partialUpdate(DocumentInfo documentInfo) {
        log.debug("Request to partially update DocumentInfo : {}", documentInfo);

        return documentInfoRepository
            .findById(documentInfo.getId())
            .map(existingDocumentInfo -> {
                if (documentInfo.getNumber() != null) {
                    existingDocumentInfo.setNumber(documentInfo.getNumber());
                }
                if (documentInfo.getRegisteredNumber() != null) {
                    existingDocumentInfo.setRegisteredNumber(documentInfo.getRegisteredNumber());
                }
                if (documentInfo.getIssuedate() != null) {
                    existingDocumentInfo.setIssuedate(documentInfo.getIssuedate());
                }
                if (documentInfo.getSubject() != null) {
                    existingDocumentInfo.setSubject(documentInfo.getSubject());
                }
                if (documentInfo.getDpriority() != null) {
                    existingDocumentInfo.setDpriority(documentInfo.getDpriority());
                }
                if (documentInfo.getScanPath() != null) {
                    existingDocumentInfo.setScanPath(documentInfo.getScanPath());
                }
                if (documentInfo.getScanPathContentType() != null) {
                    existingDocumentInfo.setScanPathContentType(documentInfo.getScanPathContentType());
                }
                if (documentInfo.getContent() != null) {
                    existingDocumentInfo.setContent(documentInfo.getContent());
                }
                if (documentInfo.getOrganization() != null) {
                    existingDocumentInfo.setOrganization(documentInfo.getOrganization());
                }

                return existingDocumentInfo;
            })
            .map(documentInfoRepository::save);
    }

    /**
     * Get all the documentInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentInfo> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentInfos");
        return documentInfoRepository.findAll(pageable);
    }

    /**
     * Get one documentInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentInfo> findOne(Long id) {
        log.debug("Request to get DocumentInfo : {}", id);
        return documentInfoRepository.findById(id);
    }

    /**
     * Delete the documentInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DocumentInfo : {}", id);
        documentInfoRepository.deleteById(id);
    }
}
