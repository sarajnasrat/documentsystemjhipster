package documentmanagement.mcit.gov.af.web.rest;

import documentmanagement.mcit.gov.af.domain.DocumentInfo;
import documentmanagement.mcit.gov.af.repository.DocumentInfoRepository;
import documentmanagement.mcit.gov.af.service.DocumentInfoQueryService;
import documentmanagement.mcit.gov.af.service.DocumentInfoService;
import documentmanagement.mcit.gov.af.service.criteria.DocumentInfoCriteria;
import documentmanagement.mcit.gov.af.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing
 * {@link documentmanagement.mcit.gov.af.domain.DocumentInfo}.
 */
@RestController
@RequestMapping("/api")
public class DocumentInfoResource {

    private final Logger log = LoggerFactory.getLogger(DocumentInfoResource.class);

    private static final String ENTITY_NAME = "documentInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentInfoService documentInfoService;

    private final DocumentInfoRepository documentInfoRepository;

    private final DocumentInfoQueryService documentInfoQueryService;

    public DocumentInfoResource(
        DocumentInfoService documentInfoService,
        DocumentInfoRepository documentInfoRepository,
        DocumentInfoQueryService documentInfoQueryService
    ) {
        this.documentInfoService = documentInfoService;
        this.documentInfoRepository = documentInfoRepository;
        this.documentInfoQueryService = documentInfoQueryService;
    }

    /**
     * {@code POST  /document-infos} : Create a new documentInfo.
     *
     * @param documentInfo the documentInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new documentInfo, or with status {@code 400 (Bad Request)}
     *         if the documentInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/document-infos")
    public ResponseEntity<DocumentInfo> createDocumentInfo(@Valid @RequestBody DocumentInfo documentInfo) throws URISyntaxException {
        log.debug("REST request to save DocumentInfo : {}", documentInfo);
        if (documentInfo.getId() != null) {
            throw new BadRequestAlertException("A new documentInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentInfo result = documentInfoService.save(documentInfo);
        return ResponseEntity
            .created(new URI("/api/document-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /document-infos/:id} : Updates an existing documentInfo.
     *
     * @param id           the id of the documentInfo to save.
     * @param documentInfo the documentInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated documentInfo,
     *         or with status {@code 400 (Bad Request)} if the documentInfo is not
     *         valid,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         documentInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/document-infos/{id}")
    public ResponseEntity<DocumentInfo> updateDocumentInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DocumentInfo documentInfo
    ) throws URISyntaxException {
        log.debug("REST request to update DocumentInfo : {}, {}", id, documentInfo);
        if (documentInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DocumentInfo result = documentInfoService.update(documentInfo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /document-infos/:id} : Partial updates given fields of an
     * existing documentInfo, field will ignore if it is null
     *
     * @param id           the id of the documentInfo to save.
     * @param documentInfo the documentInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated documentInfo,
     *         or with status {@code 400 (Bad Request)} if the documentInfo is not
     *         valid,
     *         or with status {@code 404 (Not Found)} if the documentInfo is not
     *         found,
     *         or with status {@code 500 (Internal Server Error)} if the
     *         documentInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/document-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DocumentInfo> partialUpdateDocumentInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DocumentInfo documentInfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update DocumentInfo partially : {}, {}", id, documentInfo);
        if (documentInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DocumentInfo> result = documentInfoService.partialUpdate(documentInfo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentInfo.getId().toString())
        );
    }

    /**
     * {@code GET  /document-infos} : get all the documentInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of documentInfos in body.
     */
    @GetMapping("/document-infos")
    public ResponseEntity<List<DocumentInfo>> getAllDocumentInfos(
        DocumentInfoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DocumentInfos by criteria: {}", criteria);
        Page<DocumentInfo> page = documentInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /document-infos/count} : count all the documentInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/document-infos/count")
    public ResponseEntity<Long> countDocumentInfos(DocumentInfoCriteria criteria) {
        log.debug("REST request to count DocumentInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(documentInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /document-infos/:id} : get the "id" documentInfo.
     *
     * @param id the id of the documentInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the documentInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/document-infos/{id}")
    public ResponseEntity<DocumentInfo> getDocumentInfo(@PathVariable Long id) {
        log.debug("REST request to get DocumentInfo : {}", id);
        Optional<DocumentInfo> documentInfo = documentInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentInfo);
    }

    /**
     * {@code DELETE  /document-infos/:id} : delete the "id" documentInfo.
     *
     * @param id the id of the documentInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/document-infos/{id}")
    public ResponseEntity<Void> deleteDocumentInfo(@PathVariable Long id) {
        log.debug("REST request to delete DocumentInfo : {}", id);
        documentInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/searchdocument")
    public ResponseEntity<Page<DocumentInfo>> searchDocumentByNumberAndSubject(
        @RequestParam(name = "number", required = false) String number,
        @RequestParam(name = "subject", required = false) String subject,
        @RequestParam(name = "organization", required = false) String organization,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "5") int size,
        @RequestParam(name = "sortField", defaultValue = "issuedate") String sortField,
        @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
            Page<DocumentInfo> documentInfos;

            if (
                number != null &&
                !number.isEmpty() &&
                subject != null &&
                !subject.isEmpty() &&
                organization != null &&
                !organization.isEmpty()
            ) {
                documentInfos =
                    documentInfoRepository.findByNumberContainingIgnoreCaseAndSubjectContainingIgnoreCaseAndOrganizationContainingIgnoreCase(
                        number,
                        subject,
                        organization,
                        pageable
                    );
            } else if (number != null && !number.isEmpty() && subject != null && !subject.isEmpty()) {
                documentInfos =
                    documentInfoRepository.findByNumberContainingIgnoreCaseAndSubjectContainingIgnoreCase(number, subject, pageable);
            } else if (number != null && !number.isEmpty()) {
                documentInfos = documentInfoRepository.findByNumberContainingIgnoreCase(number, pageable);
            } else if (subject != null && !subject.isEmpty()) {
                documentInfos = documentInfoRepository.findBySubjectContainingIgnoreCase(subject, pageable);
            } else if (organization != null && !organization.isEmpty()) {
                documentInfos = documentInfoRepository.findByOrganizationContainingIgnoreCase(organization, pageable);
            } else if (
                number == null || number.isEmpty() && subject == null || subject.isEmpty() && organization == null || organization.isEmpty()
            ) {
                documentInfos = documentInfoRepository.findByOrderByIssuedateDesc(pageable);
            } else {
                String message = "No matching documents found.";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Page.empty());
            }

            if (documentInfos.isEmpty()) {
                String message = "No matching documents found.";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Page.empty());
            } else {
                return ResponseEntity.ok(documentInfos);
            }
        } catch (Exception e) {
            e.printStackTrace(); // You can log the exception for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
