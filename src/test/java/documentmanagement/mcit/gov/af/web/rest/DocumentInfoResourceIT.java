package documentmanagement.mcit.gov.af.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import documentmanagement.mcit.gov.af.IntegrationTest;
import documentmanagement.mcit.gov.af.domain.DocumentInfo;
import documentmanagement.mcit.gov.af.repository.DocumentInfoRepository;
import documentmanagement.mcit.gov.af.service.criteria.DocumentInfoCriteria;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link DocumentInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumentInfoResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTERED_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REGISTERED_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ISSUEDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ISSUEDATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ISSUEDATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_DPRIORITY = "AAAAAAAAAA";
    private static final String UPDATED_DPRIORITY = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SCAN_PATH = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SCAN_PATH = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SCAN_PATH_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SCAN_PATH_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZATION = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/document-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DocumentInfoRepository documentInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentInfoMockMvc;

    private DocumentInfo documentInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentInfo createEntity(EntityManager em) {
        DocumentInfo documentInfo = new DocumentInfo()
            .number(DEFAULT_NUMBER)
            .registeredNumber(DEFAULT_REGISTERED_NUMBER)
            .issuedate(DEFAULT_ISSUEDATE)
            .subject(DEFAULT_SUBJECT)
            .dpriority(DEFAULT_DPRIORITY)
            .scanPath(DEFAULT_SCAN_PATH)
            .scanPathContentType(DEFAULT_SCAN_PATH_CONTENT_TYPE)
            .content(DEFAULT_CONTENT)
            .organization(DEFAULT_ORGANIZATION);
        return documentInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentInfo createUpdatedEntity(EntityManager em) {
        DocumentInfo documentInfo = new DocumentInfo()
            .number(UPDATED_NUMBER)
            .registeredNumber(UPDATED_REGISTERED_NUMBER)
            .issuedate(UPDATED_ISSUEDATE)
            .subject(UPDATED_SUBJECT)
            .dpriority(UPDATED_DPRIORITY)
            .scanPath(UPDATED_SCAN_PATH)
            .scanPathContentType(UPDATED_SCAN_PATH_CONTENT_TYPE)
            .content(UPDATED_CONTENT)
            .organization(UPDATED_ORGANIZATION);
        return documentInfo;
    }

    @BeforeEach
    public void initTest() {
        documentInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createDocumentInfo() throws Exception {
        int databaseSizeBeforeCreate = documentInfoRepository.findAll().size();
        // Create the DocumentInfo
        restDocumentInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentInfo)))
            .andExpect(status().isCreated());

        // Validate the DocumentInfo in the database
        List<DocumentInfo> documentInfoList = documentInfoRepository.findAll();
        assertThat(documentInfoList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentInfo testDocumentInfo = documentInfoList.get(documentInfoList.size() - 1);
        assertThat(testDocumentInfo.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testDocumentInfo.getRegisteredNumber()).isEqualTo(DEFAULT_REGISTERED_NUMBER);
        assertThat(testDocumentInfo.getIssuedate()).isEqualTo(DEFAULT_ISSUEDATE);
        assertThat(testDocumentInfo.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testDocumentInfo.getDpriority()).isEqualTo(DEFAULT_DPRIORITY);
        assertThat(testDocumentInfo.getScanPath()).isEqualTo(DEFAULT_SCAN_PATH);
        assertThat(testDocumentInfo.getScanPathContentType()).isEqualTo(DEFAULT_SCAN_PATH_CONTENT_TYPE);
        assertThat(testDocumentInfo.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testDocumentInfo.getOrganization()).isEqualTo(DEFAULT_ORGANIZATION);
    }

    @Test
    @Transactional
    void createDocumentInfoWithExistingId() throws Exception {
        // Create the DocumentInfo with an existing ID
        documentInfo.setId(1L);

        int databaseSizeBeforeCreate = documentInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentInfo)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentInfo in the database
        List<DocumentInfo> documentInfoList = documentInfoRepository.findAll();
        assertThat(documentInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentInfoRepository.findAll().size();
        // set the field null
        documentInfo.setNumber(null);

        // Create the DocumentInfo, which fails.

        restDocumentInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentInfo)))
            .andExpect(status().isBadRequest());

        List<DocumentInfo> documentInfoList = documentInfoRepository.findAll();
        assertThat(documentInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDocumentInfos() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList
        restDocumentInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].registeredNumber").value(hasItem(DEFAULT_REGISTERED_NUMBER)))
            .andExpect(jsonPath("$.[*].issuedate").value(hasItem(DEFAULT_ISSUEDATE.toString())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].dpriority").value(hasItem(DEFAULT_DPRIORITY)))
            .andExpect(jsonPath("$.[*].scanPathContentType").value(hasItem(DEFAULT_SCAN_PATH_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].scanPath").value(hasItem(Base64Utils.encodeToString(DEFAULT_SCAN_PATH))))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION)));
    }

    @Test
    @Transactional
    void getDocumentInfo() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get the documentInfo
        restDocumentInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, documentInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentInfo.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.registeredNumber").value(DEFAULT_REGISTERED_NUMBER))
            .andExpect(jsonPath("$.issuedate").value(DEFAULT_ISSUEDATE.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.dpriority").value(DEFAULT_DPRIORITY))
            .andExpect(jsonPath("$.scanPathContentType").value(DEFAULT_SCAN_PATH_CONTENT_TYPE))
            .andExpect(jsonPath("$.scanPath").value(Base64Utils.encodeToString(DEFAULT_SCAN_PATH)))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.organization").value(DEFAULT_ORGANIZATION));
    }

    @Test
    @Transactional
    void getDocumentInfosByIdFiltering() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        Long id = documentInfo.getId();

        defaultDocumentInfoShouldBeFound("id.equals=" + id);
        defaultDocumentInfoShouldNotBeFound("id.notEquals=" + id);

        defaultDocumentInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDocumentInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultDocumentInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDocumentInfoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where number equals to DEFAULT_NUMBER
        defaultDocumentInfoShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the documentInfoList where number equals to UPDATED_NUMBER
        defaultDocumentInfoShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultDocumentInfoShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the documentInfoList where number equals to UPDATED_NUMBER
        defaultDocumentInfoShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where number is not null
        defaultDocumentInfoShouldBeFound("number.specified=true");

        // Get all the documentInfoList where number is null
        defaultDocumentInfoShouldNotBeFound("number.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentInfosByNumberContainsSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where number contains DEFAULT_NUMBER
        defaultDocumentInfoShouldBeFound("number.contains=" + DEFAULT_NUMBER);

        // Get all the documentInfoList where number contains UPDATED_NUMBER
        defaultDocumentInfoShouldNotBeFound("number.contains=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByNumberNotContainsSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where number does not contain DEFAULT_NUMBER
        defaultDocumentInfoShouldNotBeFound("number.doesNotContain=" + DEFAULT_NUMBER);

        // Get all the documentInfoList where number does not contain UPDATED_NUMBER
        defaultDocumentInfoShouldBeFound("number.doesNotContain=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByRegisteredNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where registeredNumber equals to DEFAULT_REGISTERED_NUMBER
        defaultDocumentInfoShouldBeFound("registeredNumber.equals=" + DEFAULT_REGISTERED_NUMBER);

        // Get all the documentInfoList where registeredNumber equals to UPDATED_REGISTERED_NUMBER
        defaultDocumentInfoShouldNotBeFound("registeredNumber.equals=" + UPDATED_REGISTERED_NUMBER);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByRegisteredNumberIsInShouldWork() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where registeredNumber in DEFAULT_REGISTERED_NUMBER or UPDATED_REGISTERED_NUMBER
        defaultDocumentInfoShouldBeFound("registeredNumber.in=" + DEFAULT_REGISTERED_NUMBER + "," + UPDATED_REGISTERED_NUMBER);

        // Get all the documentInfoList where registeredNumber equals to UPDATED_REGISTERED_NUMBER
        defaultDocumentInfoShouldNotBeFound("registeredNumber.in=" + UPDATED_REGISTERED_NUMBER);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByRegisteredNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where registeredNumber is not null
        defaultDocumentInfoShouldBeFound("registeredNumber.specified=true");

        // Get all the documentInfoList where registeredNumber is null
        defaultDocumentInfoShouldNotBeFound("registeredNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentInfosByRegisteredNumberContainsSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where registeredNumber contains DEFAULT_REGISTERED_NUMBER
        defaultDocumentInfoShouldBeFound("registeredNumber.contains=" + DEFAULT_REGISTERED_NUMBER);

        // Get all the documentInfoList where registeredNumber contains UPDATED_REGISTERED_NUMBER
        defaultDocumentInfoShouldNotBeFound("registeredNumber.contains=" + UPDATED_REGISTERED_NUMBER);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByRegisteredNumberNotContainsSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where registeredNumber does not contain DEFAULT_REGISTERED_NUMBER
        defaultDocumentInfoShouldNotBeFound("registeredNumber.doesNotContain=" + DEFAULT_REGISTERED_NUMBER);

        // Get all the documentInfoList where registeredNumber does not contain UPDATED_REGISTERED_NUMBER
        defaultDocumentInfoShouldBeFound("registeredNumber.doesNotContain=" + UPDATED_REGISTERED_NUMBER);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByIssuedateIsEqualToSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where issuedate equals to DEFAULT_ISSUEDATE
        defaultDocumentInfoShouldBeFound("issuedate.equals=" + DEFAULT_ISSUEDATE);

        // Get all the documentInfoList where issuedate equals to UPDATED_ISSUEDATE
        defaultDocumentInfoShouldNotBeFound("issuedate.equals=" + UPDATED_ISSUEDATE);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByIssuedateIsInShouldWork() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where issuedate in DEFAULT_ISSUEDATE or UPDATED_ISSUEDATE
        defaultDocumentInfoShouldBeFound("issuedate.in=" + DEFAULT_ISSUEDATE + "," + UPDATED_ISSUEDATE);

        // Get all the documentInfoList where issuedate equals to UPDATED_ISSUEDATE
        defaultDocumentInfoShouldNotBeFound("issuedate.in=" + UPDATED_ISSUEDATE);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByIssuedateIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where issuedate is not null
        defaultDocumentInfoShouldBeFound("issuedate.specified=true");

        // Get all the documentInfoList where issuedate is null
        defaultDocumentInfoShouldNotBeFound("issuedate.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentInfosByIssuedateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where issuedate is greater than or equal to DEFAULT_ISSUEDATE
        defaultDocumentInfoShouldBeFound("issuedate.greaterThanOrEqual=" + DEFAULT_ISSUEDATE);

        // Get all the documentInfoList where issuedate is greater than or equal to UPDATED_ISSUEDATE
        defaultDocumentInfoShouldNotBeFound("issuedate.greaterThanOrEqual=" + UPDATED_ISSUEDATE);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByIssuedateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where issuedate is less than or equal to DEFAULT_ISSUEDATE
        defaultDocumentInfoShouldBeFound("issuedate.lessThanOrEqual=" + DEFAULT_ISSUEDATE);

        // Get all the documentInfoList where issuedate is less than or equal to SMALLER_ISSUEDATE
        defaultDocumentInfoShouldNotBeFound("issuedate.lessThanOrEqual=" + SMALLER_ISSUEDATE);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByIssuedateIsLessThanSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where issuedate is less than DEFAULT_ISSUEDATE
        defaultDocumentInfoShouldNotBeFound("issuedate.lessThan=" + DEFAULT_ISSUEDATE);

        // Get all the documentInfoList where issuedate is less than UPDATED_ISSUEDATE
        defaultDocumentInfoShouldBeFound("issuedate.lessThan=" + UPDATED_ISSUEDATE);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByIssuedateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where issuedate is greater than DEFAULT_ISSUEDATE
        defaultDocumentInfoShouldNotBeFound("issuedate.greaterThan=" + DEFAULT_ISSUEDATE);

        // Get all the documentInfoList where issuedate is greater than SMALLER_ISSUEDATE
        defaultDocumentInfoShouldBeFound("issuedate.greaterThan=" + SMALLER_ISSUEDATE);
    }

    @Test
    @Transactional
    void getAllDocumentInfosBySubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where subject equals to DEFAULT_SUBJECT
        defaultDocumentInfoShouldBeFound("subject.equals=" + DEFAULT_SUBJECT);

        // Get all the documentInfoList where subject equals to UPDATED_SUBJECT
        defaultDocumentInfoShouldNotBeFound("subject.equals=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void getAllDocumentInfosBySubjectIsInShouldWork() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where subject in DEFAULT_SUBJECT or UPDATED_SUBJECT
        defaultDocumentInfoShouldBeFound("subject.in=" + DEFAULT_SUBJECT + "," + UPDATED_SUBJECT);

        // Get all the documentInfoList where subject equals to UPDATED_SUBJECT
        defaultDocumentInfoShouldNotBeFound("subject.in=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void getAllDocumentInfosBySubjectIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where subject is not null
        defaultDocumentInfoShouldBeFound("subject.specified=true");

        // Get all the documentInfoList where subject is null
        defaultDocumentInfoShouldNotBeFound("subject.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentInfosBySubjectContainsSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where subject contains DEFAULT_SUBJECT
        defaultDocumentInfoShouldBeFound("subject.contains=" + DEFAULT_SUBJECT);

        // Get all the documentInfoList where subject contains UPDATED_SUBJECT
        defaultDocumentInfoShouldNotBeFound("subject.contains=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void getAllDocumentInfosBySubjectNotContainsSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where subject does not contain DEFAULT_SUBJECT
        defaultDocumentInfoShouldNotBeFound("subject.doesNotContain=" + DEFAULT_SUBJECT);

        // Get all the documentInfoList where subject does not contain UPDATED_SUBJECT
        defaultDocumentInfoShouldBeFound("subject.doesNotContain=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByDpriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where dpriority equals to DEFAULT_DPRIORITY
        defaultDocumentInfoShouldBeFound("dpriority.equals=" + DEFAULT_DPRIORITY);

        // Get all the documentInfoList where dpriority equals to UPDATED_DPRIORITY
        defaultDocumentInfoShouldNotBeFound("dpriority.equals=" + UPDATED_DPRIORITY);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByDpriorityIsInShouldWork() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where dpriority in DEFAULT_DPRIORITY or UPDATED_DPRIORITY
        defaultDocumentInfoShouldBeFound("dpriority.in=" + DEFAULT_DPRIORITY + "," + UPDATED_DPRIORITY);

        // Get all the documentInfoList where dpriority equals to UPDATED_DPRIORITY
        defaultDocumentInfoShouldNotBeFound("dpriority.in=" + UPDATED_DPRIORITY);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByDpriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where dpriority is not null
        defaultDocumentInfoShouldBeFound("dpriority.specified=true");

        // Get all the documentInfoList where dpriority is null
        defaultDocumentInfoShouldNotBeFound("dpriority.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentInfosByDpriorityContainsSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where dpriority contains DEFAULT_DPRIORITY
        defaultDocumentInfoShouldBeFound("dpriority.contains=" + DEFAULT_DPRIORITY);

        // Get all the documentInfoList where dpriority contains UPDATED_DPRIORITY
        defaultDocumentInfoShouldNotBeFound("dpriority.contains=" + UPDATED_DPRIORITY);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByDpriorityNotContainsSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where dpriority does not contain DEFAULT_DPRIORITY
        defaultDocumentInfoShouldNotBeFound("dpriority.doesNotContain=" + DEFAULT_DPRIORITY);

        // Get all the documentInfoList where dpriority does not contain UPDATED_DPRIORITY
        defaultDocumentInfoShouldBeFound("dpriority.doesNotContain=" + UPDATED_DPRIORITY);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByContentIsEqualToSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where content equals to DEFAULT_CONTENT
        defaultDocumentInfoShouldBeFound("content.equals=" + DEFAULT_CONTENT);

        // Get all the documentInfoList where content equals to UPDATED_CONTENT
        defaultDocumentInfoShouldNotBeFound("content.equals=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByContentIsInShouldWork() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where content in DEFAULT_CONTENT or UPDATED_CONTENT
        defaultDocumentInfoShouldBeFound("content.in=" + DEFAULT_CONTENT + "," + UPDATED_CONTENT);

        // Get all the documentInfoList where content equals to UPDATED_CONTENT
        defaultDocumentInfoShouldNotBeFound("content.in=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByContentIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where content is not null
        defaultDocumentInfoShouldBeFound("content.specified=true");

        // Get all the documentInfoList where content is null
        defaultDocumentInfoShouldNotBeFound("content.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentInfosByContentContainsSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where content contains DEFAULT_CONTENT
        defaultDocumentInfoShouldBeFound("content.contains=" + DEFAULT_CONTENT);

        // Get all the documentInfoList where content contains UPDATED_CONTENT
        defaultDocumentInfoShouldNotBeFound("content.contains=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByContentNotContainsSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where content does not contain DEFAULT_CONTENT
        defaultDocumentInfoShouldNotBeFound("content.doesNotContain=" + DEFAULT_CONTENT);

        // Get all the documentInfoList where content does not contain UPDATED_CONTENT
        defaultDocumentInfoShouldBeFound("content.doesNotContain=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByOrganizationIsEqualToSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where organization equals to DEFAULT_ORGANIZATION
        defaultDocumentInfoShouldBeFound("organization.equals=" + DEFAULT_ORGANIZATION);

        // Get all the documentInfoList where organization equals to UPDATED_ORGANIZATION
        defaultDocumentInfoShouldNotBeFound("organization.equals=" + UPDATED_ORGANIZATION);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByOrganizationIsInShouldWork() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where organization in DEFAULT_ORGANIZATION or UPDATED_ORGANIZATION
        defaultDocumentInfoShouldBeFound("organization.in=" + DEFAULT_ORGANIZATION + "," + UPDATED_ORGANIZATION);

        // Get all the documentInfoList where organization equals to UPDATED_ORGANIZATION
        defaultDocumentInfoShouldNotBeFound("organization.in=" + UPDATED_ORGANIZATION);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByOrganizationIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where organization is not null
        defaultDocumentInfoShouldBeFound("organization.specified=true");

        // Get all the documentInfoList where organization is null
        defaultDocumentInfoShouldNotBeFound("organization.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentInfosByOrganizationContainsSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where organization contains DEFAULT_ORGANIZATION
        defaultDocumentInfoShouldBeFound("organization.contains=" + DEFAULT_ORGANIZATION);

        // Get all the documentInfoList where organization contains UPDATED_ORGANIZATION
        defaultDocumentInfoShouldNotBeFound("organization.contains=" + UPDATED_ORGANIZATION);
    }

    @Test
    @Transactional
    void getAllDocumentInfosByOrganizationNotContainsSomething() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        // Get all the documentInfoList where organization does not contain DEFAULT_ORGANIZATION
        defaultDocumentInfoShouldNotBeFound("organization.doesNotContain=" + DEFAULT_ORGANIZATION);

        // Get all the documentInfoList where organization does not contain UPDATED_ORGANIZATION
        defaultDocumentInfoShouldBeFound("organization.doesNotContain=" + UPDATED_ORGANIZATION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDocumentInfoShouldBeFound(String filter) throws Exception {
        restDocumentInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].registeredNumber").value(hasItem(DEFAULT_REGISTERED_NUMBER)))
            .andExpect(jsonPath("$.[*].issuedate").value(hasItem(DEFAULT_ISSUEDATE.toString())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].dpriority").value(hasItem(DEFAULT_DPRIORITY)))
            .andExpect(jsonPath("$.[*].scanPathContentType").value(hasItem(DEFAULT_SCAN_PATH_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].scanPath").value(hasItem(Base64Utils.encodeToString(DEFAULT_SCAN_PATH))))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION)));

        // Check, that the count call also returns 1
        restDocumentInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDocumentInfoShouldNotBeFound(String filter) throws Exception {
        restDocumentInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDocumentInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDocumentInfo() throws Exception {
        // Get the documentInfo
        restDocumentInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDocumentInfo() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        int databaseSizeBeforeUpdate = documentInfoRepository.findAll().size();

        // Update the documentInfo
        DocumentInfo updatedDocumentInfo = documentInfoRepository.findById(documentInfo.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentInfo are not directly saved in db
        em.detach(updatedDocumentInfo);
        updatedDocumentInfo
            .number(UPDATED_NUMBER)
            .registeredNumber(UPDATED_REGISTERED_NUMBER)
            .issuedate(UPDATED_ISSUEDATE)
            .subject(UPDATED_SUBJECT)
            .dpriority(UPDATED_DPRIORITY)
            .scanPath(UPDATED_SCAN_PATH)
            .scanPathContentType(UPDATED_SCAN_PATH_CONTENT_TYPE)
            .content(UPDATED_CONTENT)
            .organization(UPDATED_ORGANIZATION);

        restDocumentInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDocumentInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDocumentInfo))
            )
            .andExpect(status().isOk());

        // Validate the DocumentInfo in the database
        List<DocumentInfo> documentInfoList = documentInfoRepository.findAll();
        assertThat(documentInfoList).hasSize(databaseSizeBeforeUpdate);
        DocumentInfo testDocumentInfo = documentInfoList.get(documentInfoList.size() - 1);
        assertThat(testDocumentInfo.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testDocumentInfo.getRegisteredNumber()).isEqualTo(UPDATED_REGISTERED_NUMBER);
        assertThat(testDocumentInfo.getIssuedate()).isEqualTo(UPDATED_ISSUEDATE);
        assertThat(testDocumentInfo.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testDocumentInfo.getDpriority()).isEqualTo(UPDATED_DPRIORITY);
        assertThat(testDocumentInfo.getScanPath()).isEqualTo(UPDATED_SCAN_PATH);
        assertThat(testDocumentInfo.getScanPathContentType()).isEqualTo(UPDATED_SCAN_PATH_CONTENT_TYPE);
        assertThat(testDocumentInfo.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testDocumentInfo.getOrganization()).isEqualTo(UPDATED_ORGANIZATION);
    }

    @Test
    @Transactional
    void putNonExistingDocumentInfo() throws Exception {
        int databaseSizeBeforeUpdate = documentInfoRepository.findAll().size();
        documentInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentInfo in the database
        List<DocumentInfo> documentInfoList = documentInfoRepository.findAll();
        assertThat(documentInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocumentInfo() throws Exception {
        int databaseSizeBeforeUpdate = documentInfoRepository.findAll().size();
        documentInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentInfo in the database
        List<DocumentInfo> documentInfoList = documentInfoRepository.findAll();
        assertThat(documentInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocumentInfo() throws Exception {
        int databaseSizeBeforeUpdate = documentInfoRepository.findAll().size();
        documentInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentInfo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentInfo in the database
        List<DocumentInfo> documentInfoList = documentInfoRepository.findAll();
        assertThat(documentInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentInfoWithPatch() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        int databaseSizeBeforeUpdate = documentInfoRepository.findAll().size();

        // Update the documentInfo using partial update
        DocumentInfo partialUpdatedDocumentInfo = new DocumentInfo();
        partialUpdatedDocumentInfo.setId(documentInfo.getId());

        partialUpdatedDocumentInfo
            .registeredNumber(UPDATED_REGISTERED_NUMBER)
            .issuedate(UPDATED_ISSUEDATE)
            .dpriority(UPDATED_DPRIORITY)
            .scanPath(UPDATED_SCAN_PATH)
            .scanPathContentType(UPDATED_SCAN_PATH_CONTENT_TYPE)
            .content(UPDATED_CONTENT);

        restDocumentInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocumentInfo))
            )
            .andExpect(status().isOk());

        // Validate the DocumentInfo in the database
        List<DocumentInfo> documentInfoList = documentInfoRepository.findAll();
        assertThat(documentInfoList).hasSize(databaseSizeBeforeUpdate);
        DocumentInfo testDocumentInfo = documentInfoList.get(documentInfoList.size() - 1);
        assertThat(testDocumentInfo.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testDocumentInfo.getRegisteredNumber()).isEqualTo(UPDATED_REGISTERED_NUMBER);
        assertThat(testDocumentInfo.getIssuedate()).isEqualTo(UPDATED_ISSUEDATE);
        assertThat(testDocumentInfo.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testDocumentInfo.getDpriority()).isEqualTo(UPDATED_DPRIORITY);
        assertThat(testDocumentInfo.getScanPath()).isEqualTo(UPDATED_SCAN_PATH);
        assertThat(testDocumentInfo.getScanPathContentType()).isEqualTo(UPDATED_SCAN_PATH_CONTENT_TYPE);
        assertThat(testDocumentInfo.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testDocumentInfo.getOrganization()).isEqualTo(DEFAULT_ORGANIZATION);
    }

    @Test
    @Transactional
    void fullUpdateDocumentInfoWithPatch() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        int databaseSizeBeforeUpdate = documentInfoRepository.findAll().size();

        // Update the documentInfo using partial update
        DocumentInfo partialUpdatedDocumentInfo = new DocumentInfo();
        partialUpdatedDocumentInfo.setId(documentInfo.getId());

        partialUpdatedDocumentInfo
            .number(UPDATED_NUMBER)
            .registeredNumber(UPDATED_REGISTERED_NUMBER)
            .issuedate(UPDATED_ISSUEDATE)
            .subject(UPDATED_SUBJECT)
            .dpriority(UPDATED_DPRIORITY)
            .scanPath(UPDATED_SCAN_PATH)
            .scanPathContentType(UPDATED_SCAN_PATH_CONTENT_TYPE)
            .content(UPDATED_CONTENT)
            .organization(UPDATED_ORGANIZATION);

        restDocumentInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocumentInfo))
            )
            .andExpect(status().isOk());

        // Validate the DocumentInfo in the database
        List<DocumentInfo> documentInfoList = documentInfoRepository.findAll();
        assertThat(documentInfoList).hasSize(databaseSizeBeforeUpdate);
        DocumentInfo testDocumentInfo = documentInfoList.get(documentInfoList.size() - 1);
        assertThat(testDocumentInfo.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testDocumentInfo.getRegisteredNumber()).isEqualTo(UPDATED_REGISTERED_NUMBER);
        assertThat(testDocumentInfo.getIssuedate()).isEqualTo(UPDATED_ISSUEDATE);
        assertThat(testDocumentInfo.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testDocumentInfo.getDpriority()).isEqualTo(UPDATED_DPRIORITY);
        assertThat(testDocumentInfo.getScanPath()).isEqualTo(UPDATED_SCAN_PATH);
        assertThat(testDocumentInfo.getScanPathContentType()).isEqualTo(UPDATED_SCAN_PATH_CONTENT_TYPE);
        assertThat(testDocumentInfo.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testDocumentInfo.getOrganization()).isEqualTo(UPDATED_ORGANIZATION);
    }

    @Test
    @Transactional
    void patchNonExistingDocumentInfo() throws Exception {
        int databaseSizeBeforeUpdate = documentInfoRepository.findAll().size();
        documentInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documentInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentInfo in the database
        List<DocumentInfo> documentInfoList = documentInfoRepository.findAll();
        assertThat(documentInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocumentInfo() throws Exception {
        int databaseSizeBeforeUpdate = documentInfoRepository.findAll().size();
        documentInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentInfo in the database
        List<DocumentInfo> documentInfoList = documentInfoRepository.findAll();
        assertThat(documentInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocumentInfo() throws Exception {
        int databaseSizeBeforeUpdate = documentInfoRepository.findAll().size();
        documentInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(documentInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentInfo in the database
        List<DocumentInfo> documentInfoList = documentInfoRepository.findAll();
        assertThat(documentInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocumentInfo() throws Exception {
        // Initialize the database
        documentInfoRepository.saveAndFlush(documentInfo);

        int databaseSizeBeforeDelete = documentInfoRepository.findAll().size();

        // Delete the documentInfo
        restDocumentInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, documentInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentInfo> documentInfoList = documentInfoRepository.findAll();
        assertThat(documentInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
