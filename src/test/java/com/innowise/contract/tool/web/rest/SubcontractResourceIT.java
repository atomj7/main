package com.innowise.contract.tool.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import com.innowise.contract.tool.IntegrationTest;
import com.innowise.contract.tool.domain.Subcontract;
import com.innowise.contract.tool.repository.EntityManager;
import com.innowise.contract.tool.repository.SubcontractRepository;
import com.innowise.contract.tool.service.dto.SubcontractDTO;
import com.innowise.contract.tool.service.mapper.SubcontractMapper;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link SubcontractResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class SubcontractResourceIT {

    private static final Long DEFAULT_CONTRACT_ID = 1L;
    private static final Long UPDATED_CONTRACT_ID = 2L;

    private static final Long DEFAULT_PROJECT_ID = 1L;
    private static final Long UPDATED_PROJECT_ID = 2L;

    private static final String DEFAULT_SUBCONTRACT_CIPHER = "AAAAAAAAAA";
    private static final String UPDATED_SUBCONTRACT_CIPHER = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_ID = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_COOPERATION_TYPE_ID = "AAAAAAAAAA";
    private static final String UPDATED_COOPERATION_TYPE_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_SUM = 1F;
    private static final Float UPDATED_SUM = 2F;

    private static final Integer DEFAULT_POSITIONS = 1;
    private static final Integer UPDATED_POSITIONS = 2;

    private static final String DEFAULT_CURRENCY_ID = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_TERM_TYPE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_TERM_TYPE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_TERM_ID = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_TERM_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FINISH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINISH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TYPE_ID = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TASKT_TYPE_ID = "AAAAAAAAAA";
    private static final String UPDATED_TASKT_TYPE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNOLOGY_ID = "AAAAAAAAAA";
    private static final String UPDATED_TECHNOLOGY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DOMEN_ID = "AAAAAAAAAA";
    private static final String UPDATED_DOMEN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/subcontracts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubcontractRepository subcontractRepository;

    @Autowired
    private SubcontractMapper subcontractMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Subcontract subcontract;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subcontract createEntity(EntityManager em) {
        Subcontract subcontract = new Subcontract()
            .contractId(DEFAULT_CONTRACT_ID)
            .projectId(DEFAULT_PROJECT_ID)
            .subcontractCipher(DEFAULT_SUBCONTRACT_CIPHER)
            .statusId(DEFAULT_STATUS_ID)
            .cooperationTypeId(DEFAULT_COOPERATION_TYPE_ID)
            .sum(DEFAULT_SUM)
            .positions(DEFAULT_POSITIONS)
            .currencyId(DEFAULT_CURRENCY_ID)
            .paymentTermTypeId(DEFAULT_PAYMENT_TERM_TYPE_ID)
            .paymentTermId(DEFAULT_PAYMENT_TERM_ID)
            .startDate(DEFAULT_START_DATE)
            .finishDate(DEFAULT_FINISH_DATE)
            .typeId(DEFAULT_TYPE_ID)
            .tasktTypeId(DEFAULT_TASKT_TYPE_ID)
            .technologyId(DEFAULT_TECHNOLOGY_ID)
            .domenId(DEFAULT_DOMEN_ID)
            .link(DEFAULT_LINK);
        return subcontract;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subcontract createUpdatedEntity(EntityManager em) {
        Subcontract subcontract = new Subcontract()
            .contractId(UPDATED_CONTRACT_ID)
            .projectId(UPDATED_PROJECT_ID)
            .subcontractCipher(UPDATED_SUBCONTRACT_CIPHER)
            .statusId(UPDATED_STATUS_ID)
            .cooperationTypeId(UPDATED_COOPERATION_TYPE_ID)
            .sum(UPDATED_SUM)
            .positions(UPDATED_POSITIONS)
            .currencyId(UPDATED_CURRENCY_ID)
            .paymentTermTypeId(UPDATED_PAYMENT_TERM_TYPE_ID)
            .paymentTermId(UPDATED_PAYMENT_TERM_ID)
            .startDate(UPDATED_START_DATE)
            .finishDate(UPDATED_FINISH_DATE)
            .typeId(UPDATED_TYPE_ID)
            .tasktTypeId(UPDATED_TASKT_TYPE_ID)
            .technologyId(UPDATED_TECHNOLOGY_ID)
            .domenId(UPDATED_DOMEN_ID)
            .link(UPDATED_LINK);
        return subcontract;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Subcontract.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void setupCsrf() {
        webTestClient = webTestClient.mutateWith(csrf());
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        subcontract = createEntity(em);
    }

    @Test
    void createSubcontract() throws Exception {
        int databaseSizeBeforeCreate = subcontractRepository.findAll().collectList().block().size();
        // Create the Subcontract
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(subcontract);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Subcontract in the database
        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeCreate + 1);
        Subcontract testSubcontract = subcontractList.get(subcontractList.size() - 1);
        assertThat(testSubcontract.getContractId()).isEqualTo(DEFAULT_CONTRACT_ID);
        assertThat(testSubcontract.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testSubcontract.getSubcontractCipher()).isEqualTo(DEFAULT_SUBCONTRACT_CIPHER);
        assertThat(testSubcontract.getStatusId()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testSubcontract.getCooperationTypeId()).isEqualTo(DEFAULT_COOPERATION_TYPE_ID);
        assertThat(testSubcontract.getSum()).isEqualTo(DEFAULT_SUM);
        assertThat(testSubcontract.getPositions()).isEqualTo(DEFAULT_POSITIONS);
        assertThat(testSubcontract.getCurrencyId()).isEqualTo(DEFAULT_CURRENCY_ID);
        assertThat(testSubcontract.getPaymentTermTypeId()).isEqualTo(DEFAULT_PAYMENT_TERM_TYPE_ID);
        assertThat(testSubcontract.getPaymentTermId()).isEqualTo(DEFAULT_PAYMENT_TERM_ID);
        assertThat(testSubcontract.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testSubcontract.getFinishDate()).isEqualTo(DEFAULT_FINISH_DATE);
        assertThat(testSubcontract.getTypeId()).isEqualTo(DEFAULT_TYPE_ID);
        assertThat(testSubcontract.getTasktTypeId()).isEqualTo(DEFAULT_TASKT_TYPE_ID);
        assertThat(testSubcontract.getTechnologyId()).isEqualTo(DEFAULT_TECHNOLOGY_ID);
        assertThat(testSubcontract.getDomenId()).isEqualTo(DEFAULT_DOMEN_ID);
        assertThat(testSubcontract.getLink()).isEqualTo(DEFAULT_LINK);
    }

    @Test
    void createSubcontractWithExistingId() throws Exception {
        // Create the Subcontract with an existing ID
        subcontract.setId(1L);
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(subcontract);

        int databaseSizeBeforeCreate = subcontractRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Subcontract in the database
        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkContractIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcontractRepository.findAll().collectList().block().size();
        // set the field null
        subcontract.setContractId(null);

        // Create the Subcontract, which fails.
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(subcontract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkProjectIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcontractRepository.findAll().collectList().block().size();
        // set the field null
        subcontract.setProjectId(null);

        // Create the Subcontract, which fails.
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(subcontract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkSubcontractCipherIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcontractRepository.findAll().collectList().block().size();
        // set the field null
        subcontract.setSubcontractCipher(null);

        // Create the Subcontract, which fails.
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(subcontract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkStatusIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcontractRepository.findAll().collectList().block().size();
        // set the field null
        subcontract.setStatusId(null);

        // Create the Subcontract, which fails.
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(subcontract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCooperationTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcontractRepository.findAll().collectList().block().size();
        // set the field null
        subcontract.setCooperationTypeId(null);

        // Create the Subcontract, which fails.
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(subcontract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPositionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcontractRepository.findAll().collectList().block().size();
        // set the field null
        subcontract.setPositions(null);

        // Create the Subcontract, which fails.
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(subcontract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCurrencyIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcontractRepository.findAll().collectList().block().size();
        // set the field null
        subcontract.setCurrencyId(null);

        // Create the Subcontract, which fails.
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(subcontract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPaymentTermTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcontractRepository.findAll().collectList().block().size();
        // set the field null
        subcontract.setPaymentTermTypeId(null);

        // Create the Subcontract, which fails.
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(subcontract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPaymentTermIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcontractRepository.findAll().collectList().block().size();
        // set the field null
        subcontract.setPaymentTermId(null);

        // Create the Subcontract, which fails.
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(subcontract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcontractRepository.findAll().collectList().block().size();
        // set the field null
        subcontract.setStartDate(null);

        // Create the Subcontract, which fails.
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(subcontract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkFinishDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcontractRepository.findAll().collectList().block().size();
        // set the field null
        subcontract.setFinishDate(null);

        // Create the Subcontract, which fails.
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(subcontract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkTasktTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcontractRepository.findAll().collectList().block().size();
        // set the field null
        subcontract.setTasktTypeId(null);

        // Create the Subcontract, which fails.
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(subcontract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkTechnologyIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcontractRepository.findAll().collectList().block().size();
        // set the field null
        subcontract.setTechnologyId(null);

        // Create the Subcontract, which fails.
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(subcontract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDomenIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcontractRepository.findAll().collectList().block().size();
        // set the field null
        subcontract.setDomenId(null);

        // Create the Subcontract, which fails.
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(subcontract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllSubcontracts() {
        // Initialize the database
        subcontractRepository.save(subcontract).block();

        // Get all the subcontractList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(subcontract.getId().intValue()))
            .jsonPath("$.[*].contractId")
            .value(hasItem(DEFAULT_CONTRACT_ID.intValue()))
            .jsonPath("$.[*].projectId")
            .value(hasItem(DEFAULT_PROJECT_ID.intValue()))
            .jsonPath("$.[*].subcontractCipher")
            .value(hasItem(DEFAULT_SUBCONTRACT_CIPHER))
            .jsonPath("$.[*].statusId")
            .value(hasItem(DEFAULT_STATUS_ID))
            .jsonPath("$.[*].cooperationTypeId")
            .value(hasItem(DEFAULT_COOPERATION_TYPE_ID))
            .jsonPath("$.[*].sum")
            .value(hasItem(DEFAULT_SUM.doubleValue()))
            .jsonPath("$.[*].positions")
            .value(hasItem(DEFAULT_POSITIONS))
            .jsonPath("$.[*].currencyId")
            .value(hasItem(DEFAULT_CURRENCY_ID))
            .jsonPath("$.[*].paymentTermTypeId")
            .value(hasItem(DEFAULT_PAYMENT_TERM_TYPE_ID))
            .jsonPath("$.[*].paymentTermId")
            .value(hasItem(DEFAULT_PAYMENT_TERM_ID))
            .jsonPath("$.[*].startDate")
            .value(hasItem(DEFAULT_START_DATE.toString()))
            .jsonPath("$.[*].finishDate")
            .value(hasItem(DEFAULT_FINISH_DATE.toString()))
            .jsonPath("$.[*].typeId")
            .value(hasItem(DEFAULT_TYPE_ID))
            .jsonPath("$.[*].tasktTypeId")
            .value(hasItem(DEFAULT_TASKT_TYPE_ID))
            .jsonPath("$.[*].technologyId")
            .value(hasItem(DEFAULT_TECHNOLOGY_ID))
            .jsonPath("$.[*].domenId")
            .value(hasItem(DEFAULT_DOMEN_ID))
            .jsonPath("$.[*].link")
            .value(hasItem(DEFAULT_LINK));
    }

    @Test
    void getSubcontract() {
        // Initialize the database
        subcontractRepository.save(subcontract).block();

        // Get the subcontract
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, subcontract.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(subcontract.getId().intValue()))
            .jsonPath("$.contractId")
            .value(is(DEFAULT_CONTRACT_ID.intValue()))
            .jsonPath("$.projectId")
            .value(is(DEFAULT_PROJECT_ID.intValue()))
            .jsonPath("$.subcontractCipher")
            .value(is(DEFAULT_SUBCONTRACT_CIPHER))
            .jsonPath("$.statusId")
            .value(is(DEFAULT_STATUS_ID))
            .jsonPath("$.cooperationTypeId")
            .value(is(DEFAULT_COOPERATION_TYPE_ID))
            .jsonPath("$.sum")
            .value(is(DEFAULT_SUM.doubleValue()))
            .jsonPath("$.positions")
            .value(is(DEFAULT_POSITIONS))
            .jsonPath("$.currencyId")
            .value(is(DEFAULT_CURRENCY_ID))
            .jsonPath("$.paymentTermTypeId")
            .value(is(DEFAULT_PAYMENT_TERM_TYPE_ID))
            .jsonPath("$.paymentTermId")
            .value(is(DEFAULT_PAYMENT_TERM_ID))
            .jsonPath("$.startDate")
            .value(is(DEFAULT_START_DATE.toString()))
            .jsonPath("$.finishDate")
            .value(is(DEFAULT_FINISH_DATE.toString()))
            .jsonPath("$.typeId")
            .value(is(DEFAULT_TYPE_ID))
            .jsonPath("$.tasktTypeId")
            .value(is(DEFAULT_TASKT_TYPE_ID))
            .jsonPath("$.technologyId")
            .value(is(DEFAULT_TECHNOLOGY_ID))
            .jsonPath("$.domenId")
            .value(is(DEFAULT_DOMEN_ID))
            .jsonPath("$.link")
            .value(is(DEFAULT_LINK));
    }

    @Test
    void getNonExistingSubcontract() {
        // Get the subcontract
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putNewSubcontract() throws Exception {
        // Initialize the database
        subcontractRepository.save(subcontract).block();

        int databaseSizeBeforeUpdate = subcontractRepository.findAll().collectList().block().size();

        // Update the subcontract
        Subcontract updatedSubcontract = subcontractRepository.findById(subcontract.getId()).block();
        updatedSubcontract
            .contractId(UPDATED_CONTRACT_ID)
            .projectId(UPDATED_PROJECT_ID)
            .subcontractCipher(UPDATED_SUBCONTRACT_CIPHER)
            .statusId(UPDATED_STATUS_ID)
            .cooperationTypeId(UPDATED_COOPERATION_TYPE_ID)
            .sum(UPDATED_SUM)
            .positions(UPDATED_POSITIONS)
            .currencyId(UPDATED_CURRENCY_ID)
            .paymentTermTypeId(UPDATED_PAYMENT_TERM_TYPE_ID)
            .paymentTermId(UPDATED_PAYMENT_TERM_ID)
            .startDate(UPDATED_START_DATE)
            .finishDate(UPDATED_FINISH_DATE)
            .typeId(UPDATED_TYPE_ID)
            .tasktTypeId(UPDATED_TASKT_TYPE_ID)
            .technologyId(UPDATED_TECHNOLOGY_ID)
            .domenId(UPDATED_DOMEN_ID)
            .link(UPDATED_LINK);
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(updatedSubcontract);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, subcontractDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Subcontract in the database
        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeUpdate);
        Subcontract testSubcontract = subcontractList.get(subcontractList.size() - 1);
        assertThat(testSubcontract.getContractId()).isEqualTo(UPDATED_CONTRACT_ID);
        assertThat(testSubcontract.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testSubcontract.getSubcontractCipher()).isEqualTo(UPDATED_SUBCONTRACT_CIPHER);
        assertThat(testSubcontract.getStatusId()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testSubcontract.getCooperationTypeId()).isEqualTo(UPDATED_COOPERATION_TYPE_ID);
        assertThat(testSubcontract.getSum()).isEqualTo(UPDATED_SUM);
        assertThat(testSubcontract.getPositions()).isEqualTo(UPDATED_POSITIONS);
        assertThat(testSubcontract.getCurrencyId()).isEqualTo(UPDATED_CURRENCY_ID);
        assertThat(testSubcontract.getPaymentTermTypeId()).isEqualTo(UPDATED_PAYMENT_TERM_TYPE_ID);
        assertThat(testSubcontract.getPaymentTermId()).isEqualTo(UPDATED_PAYMENT_TERM_ID);
        assertThat(testSubcontract.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSubcontract.getFinishDate()).isEqualTo(UPDATED_FINISH_DATE);
        assertThat(testSubcontract.getTypeId()).isEqualTo(UPDATED_TYPE_ID);
        assertThat(testSubcontract.getTasktTypeId()).isEqualTo(UPDATED_TASKT_TYPE_ID);
        assertThat(testSubcontract.getTechnologyId()).isEqualTo(UPDATED_TECHNOLOGY_ID);
        assertThat(testSubcontract.getDomenId()).isEqualTo(UPDATED_DOMEN_ID);
        assertThat(testSubcontract.getLink()).isEqualTo(UPDATED_LINK);
    }

    @Test
    void putNonExistingSubcontract() throws Exception {
        int databaseSizeBeforeUpdate = subcontractRepository.findAll().collectList().block().size();
        subcontract.setId(count.incrementAndGet());

        // Create the Subcontract
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(subcontract);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, subcontractDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Subcontract in the database
        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSubcontract() throws Exception {
        int databaseSizeBeforeUpdate = subcontractRepository.findAll().collectList().block().size();
        subcontract.setId(count.incrementAndGet());

        // Create the Subcontract
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(subcontract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Subcontract in the database
        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSubcontract() throws Exception {
        int databaseSizeBeforeUpdate = subcontractRepository.findAll().collectList().block().size();
        subcontract.setId(count.incrementAndGet());

        // Create the Subcontract
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(subcontract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Subcontract in the database
        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSubcontractWithPatch() throws Exception {
        // Initialize the database
        subcontractRepository.save(subcontract).block();

        int databaseSizeBeforeUpdate = subcontractRepository.findAll().collectList().block().size();

        // Update the subcontract using partial update
        Subcontract partialUpdatedSubcontract = new Subcontract();
        partialUpdatedSubcontract.setId(subcontract.getId());

        partialUpdatedSubcontract
            .projectId(UPDATED_PROJECT_ID)
            .paymentTermId(UPDATED_PAYMENT_TERM_ID)
            .finishDate(UPDATED_FINISH_DATE)
            .tasktTypeId(UPDATED_TASKT_TYPE_ID)
            .link(UPDATED_LINK);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSubcontract.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSubcontract))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Subcontract in the database
        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeUpdate);
        Subcontract testSubcontract = subcontractList.get(subcontractList.size() - 1);
        assertThat(testSubcontract.getContractId()).isEqualTo(DEFAULT_CONTRACT_ID);
        assertThat(testSubcontract.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testSubcontract.getSubcontractCipher()).isEqualTo(DEFAULT_SUBCONTRACT_CIPHER);
        assertThat(testSubcontract.getStatusId()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testSubcontract.getCooperationTypeId()).isEqualTo(DEFAULT_COOPERATION_TYPE_ID);
        assertThat(testSubcontract.getSum()).isEqualTo(DEFAULT_SUM);
        assertThat(testSubcontract.getPositions()).isEqualTo(DEFAULT_POSITIONS);
        assertThat(testSubcontract.getCurrencyId()).isEqualTo(DEFAULT_CURRENCY_ID);
        assertThat(testSubcontract.getPaymentTermTypeId()).isEqualTo(DEFAULT_PAYMENT_TERM_TYPE_ID);
        assertThat(testSubcontract.getPaymentTermId()).isEqualTo(UPDATED_PAYMENT_TERM_ID);
        assertThat(testSubcontract.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testSubcontract.getFinishDate()).isEqualTo(UPDATED_FINISH_DATE);
        assertThat(testSubcontract.getTypeId()).isEqualTo(DEFAULT_TYPE_ID);
        assertThat(testSubcontract.getTasktTypeId()).isEqualTo(UPDATED_TASKT_TYPE_ID);
        assertThat(testSubcontract.getTechnologyId()).isEqualTo(DEFAULT_TECHNOLOGY_ID);
        assertThat(testSubcontract.getDomenId()).isEqualTo(DEFAULT_DOMEN_ID);
        assertThat(testSubcontract.getLink()).isEqualTo(UPDATED_LINK);
    }

    @Test
    void fullUpdateSubcontractWithPatch() throws Exception {
        // Initialize the database
        subcontractRepository.save(subcontract).block();

        int databaseSizeBeforeUpdate = subcontractRepository.findAll().collectList().block().size();

        // Update the subcontract using partial update
        Subcontract partialUpdatedSubcontract = new Subcontract();
        partialUpdatedSubcontract.setId(subcontract.getId());

        partialUpdatedSubcontract
            .contractId(UPDATED_CONTRACT_ID)
            .projectId(UPDATED_PROJECT_ID)
            .subcontractCipher(UPDATED_SUBCONTRACT_CIPHER)
            .statusId(UPDATED_STATUS_ID)
            .cooperationTypeId(UPDATED_COOPERATION_TYPE_ID)
            .sum(UPDATED_SUM)
            .positions(UPDATED_POSITIONS)
            .currencyId(UPDATED_CURRENCY_ID)
            .paymentTermTypeId(UPDATED_PAYMENT_TERM_TYPE_ID)
            .paymentTermId(UPDATED_PAYMENT_TERM_ID)
            .startDate(UPDATED_START_DATE)
            .finishDate(UPDATED_FINISH_DATE)
            .typeId(UPDATED_TYPE_ID)
            .tasktTypeId(UPDATED_TASKT_TYPE_ID)
            .technologyId(UPDATED_TECHNOLOGY_ID)
            .domenId(UPDATED_DOMEN_ID)
            .link(UPDATED_LINK);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSubcontract.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSubcontract))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Subcontract in the database
        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeUpdate);
        Subcontract testSubcontract = subcontractList.get(subcontractList.size() - 1);
        assertThat(testSubcontract.getContractId()).isEqualTo(UPDATED_CONTRACT_ID);
        assertThat(testSubcontract.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testSubcontract.getSubcontractCipher()).isEqualTo(UPDATED_SUBCONTRACT_CIPHER);
        assertThat(testSubcontract.getStatusId()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testSubcontract.getCooperationTypeId()).isEqualTo(UPDATED_COOPERATION_TYPE_ID);
        assertThat(testSubcontract.getSum()).isEqualTo(UPDATED_SUM);
        assertThat(testSubcontract.getPositions()).isEqualTo(UPDATED_POSITIONS);
        assertThat(testSubcontract.getCurrencyId()).isEqualTo(UPDATED_CURRENCY_ID);
        assertThat(testSubcontract.getPaymentTermTypeId()).isEqualTo(UPDATED_PAYMENT_TERM_TYPE_ID);
        assertThat(testSubcontract.getPaymentTermId()).isEqualTo(UPDATED_PAYMENT_TERM_ID);
        assertThat(testSubcontract.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSubcontract.getFinishDate()).isEqualTo(UPDATED_FINISH_DATE);
        assertThat(testSubcontract.getTypeId()).isEqualTo(UPDATED_TYPE_ID);
        assertThat(testSubcontract.getTasktTypeId()).isEqualTo(UPDATED_TASKT_TYPE_ID);
        assertThat(testSubcontract.getTechnologyId()).isEqualTo(UPDATED_TECHNOLOGY_ID);
        assertThat(testSubcontract.getDomenId()).isEqualTo(UPDATED_DOMEN_ID);
        assertThat(testSubcontract.getLink()).isEqualTo(UPDATED_LINK);
    }

    @Test
    void patchNonExistingSubcontract() throws Exception {
        int databaseSizeBeforeUpdate = subcontractRepository.findAll().collectList().block().size();
        subcontract.setId(count.incrementAndGet());

        // Create the Subcontract
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(subcontract);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, subcontractDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Subcontract in the database
        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSubcontract() throws Exception {
        int databaseSizeBeforeUpdate = subcontractRepository.findAll().collectList().block().size();
        subcontract.setId(count.incrementAndGet());

        // Create the Subcontract
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(subcontract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Subcontract in the database
        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSubcontract() throws Exception {
        int databaseSizeBeforeUpdate = subcontractRepository.findAll().collectList().block().size();
        subcontract.setId(count.incrementAndGet());

        // Create the Subcontract
        SubcontractDTO subcontractDTO = subcontractMapper.toDto(subcontract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(subcontractDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Subcontract in the database
        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSubcontract() {
        // Initialize the database
        subcontractRepository.save(subcontract).block();

        int databaseSizeBeforeDelete = subcontractRepository.findAll().collectList().block().size();

        // Delete the subcontract
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, subcontract.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Subcontract> subcontractList = subcontractRepository.findAll().collectList().block();
        assertThat(subcontractList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
