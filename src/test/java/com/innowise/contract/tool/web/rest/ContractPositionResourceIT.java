package com.innowise.contract.tool.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import com.innowise.contract.tool.IntegrationTest;
import com.innowise.contract.tool.domain.ContractPosition;
import com.innowise.contract.tool.repository.ContractPositionRepository;
import com.innowise.contract.tool.repository.EntityManager;
import com.innowise.contract.tool.service.dto.ContractPositionDTO;
import com.innowise.contract.tool.service.mapper.ContractPositionMapper;
import java.time.Duration;
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
 * Integration tests for the {@link ContractPositionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ContractPositionResourceIT {

    private static final String DEFAULT_CONTRACT_POSITION_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_POSITION_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_EMPLOYEE_ID = 1L;
    private static final Long UPDATED_EMPLOYEE_ID = 2L;

    private static final Long DEFAULT_SUBCONTRACT_ID = 1L;
    private static final Long UPDATED_SUBCONTRACT_ID = 2L;

    private static final Integer DEFAULT_NUMBER_CONTRACT_POSITION = 1;
    private static final Integer UPDATED_NUMBER_CONTRACT_POSITION = 2;

    private static final String DEFAULT_RESTRICTION_TYPE_ID = "AAAAAAAAAA";
    private static final String UPDATED_RESTRICTION_TYPE_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_RESTRICTION = 1;
    private static final Integer UPDATED_RESTRICTION = 2;

    private static final String DEFAULT_CURRENCY_ID = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_RATE_AN_HOUR = 1F;
    private static final Float UPDATED_RATE_AN_HOUR = 2F;

    private static final String DEFAULT_STATUS_ID = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/contract-positions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContractPositionRepository contractPositionRepository;

    @Autowired
    private ContractPositionMapper contractPositionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private ContractPosition contractPosition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContractPosition createEntity(EntityManager em) {
        ContractPosition contractPosition = new ContractPosition()
            .contractPositionId(DEFAULT_CONTRACT_POSITION_ID)
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .subcontractId(DEFAULT_SUBCONTRACT_ID)
            .numberContractPosition(DEFAULT_NUMBER_CONTRACT_POSITION)
            .restrictionTypeId(DEFAULT_RESTRICTION_TYPE_ID)
            .restriction(DEFAULT_RESTRICTION)
            .currencyId(DEFAULT_CURRENCY_ID)
            .rateAnHour(DEFAULT_RATE_AN_HOUR)
            .statusId(DEFAULT_STATUS_ID);
        return contractPosition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContractPosition createUpdatedEntity(EntityManager em) {
        ContractPosition contractPosition = new ContractPosition()
            .contractPositionId(UPDATED_CONTRACT_POSITION_ID)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .subcontractId(UPDATED_SUBCONTRACT_ID)
            .numberContractPosition(UPDATED_NUMBER_CONTRACT_POSITION)
            .restrictionTypeId(UPDATED_RESTRICTION_TYPE_ID)
            .restriction(UPDATED_RESTRICTION)
            .currencyId(UPDATED_CURRENCY_ID)
            .rateAnHour(UPDATED_RATE_AN_HOUR)
            .statusId(UPDATED_STATUS_ID);
        return contractPosition;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(ContractPosition.class).block();
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
        contractPosition = createEntity(em);
    }

    @Test
    void createContractPosition() throws Exception {
        int databaseSizeBeforeCreate = contractPositionRepository.findAll().collectList().block().size();
        // Create the ContractPosition
        ContractPositionDTO contractPositionDTO = contractPositionMapper.toDto(contractPosition);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractPositionDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the ContractPosition in the database
        List<ContractPosition> contractPositionList = contractPositionRepository.findAll().collectList().block();
        assertThat(contractPositionList).hasSize(databaseSizeBeforeCreate + 1);
        ContractPosition testContractPosition = contractPositionList.get(contractPositionList.size() - 1);
        assertThat(testContractPosition.getContractPositionId()).isEqualTo(DEFAULT_CONTRACT_POSITION_ID);
        assertThat(testContractPosition.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testContractPosition.getSubcontractId()).isEqualTo(DEFAULT_SUBCONTRACT_ID);
        assertThat(testContractPosition.getNumberContractPosition()).isEqualTo(DEFAULT_NUMBER_CONTRACT_POSITION);
        assertThat(testContractPosition.getRestrictionTypeId()).isEqualTo(DEFAULT_RESTRICTION_TYPE_ID);
        assertThat(testContractPosition.getRestriction()).isEqualTo(DEFAULT_RESTRICTION);
        assertThat(testContractPosition.getCurrencyId()).isEqualTo(DEFAULT_CURRENCY_ID);
        assertThat(testContractPosition.getRateAnHour()).isEqualTo(DEFAULT_RATE_AN_HOUR);
        assertThat(testContractPosition.getStatusId()).isEqualTo(DEFAULT_STATUS_ID);
    }

    @Test
    void createContractPositionWithExistingId() throws Exception {
        // Create the ContractPosition with an existing ID
        contractPosition.setId(1L);
        ContractPositionDTO contractPositionDTO = contractPositionMapper.toDto(contractPosition);

        int databaseSizeBeforeCreate = contractPositionRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractPositionDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ContractPosition in the database
        List<ContractPosition> contractPositionList = contractPositionRepository.findAll().collectList().block();
        assertThat(contractPositionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkContractPositionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractPositionRepository.findAll().collectList().block().size();
        // set the field null
        contractPosition.setContractPositionId(null);

        // Create the ContractPosition, which fails.
        ContractPositionDTO contractPositionDTO = contractPositionMapper.toDto(contractPosition);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractPositionDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<ContractPosition> contractPositionList = contractPositionRepository.findAll().collectList().block();
        assertThat(contractPositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkSubcontractIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractPositionRepository.findAll().collectList().block().size();
        // set the field null
        contractPosition.setSubcontractId(null);

        // Create the ContractPosition, which fails.
        ContractPositionDTO contractPositionDTO = contractPositionMapper.toDto(contractPosition);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractPositionDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<ContractPosition> contractPositionList = contractPositionRepository.findAll().collectList().block();
        assertThat(contractPositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNumberContractPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractPositionRepository.findAll().collectList().block().size();
        // set the field null
        contractPosition.setNumberContractPosition(null);

        // Create the ContractPosition, which fails.
        ContractPositionDTO contractPositionDTO = contractPositionMapper.toDto(contractPosition);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractPositionDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<ContractPosition> contractPositionList = contractPositionRepository.findAll().collectList().block();
        assertThat(contractPositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkRestrictionTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractPositionRepository.findAll().collectList().block().size();
        // set the field null
        contractPosition.setRestrictionTypeId(null);

        // Create the ContractPosition, which fails.
        ContractPositionDTO contractPositionDTO = contractPositionMapper.toDto(contractPosition);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractPositionDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<ContractPosition> contractPositionList = contractPositionRepository.findAll().collectList().block();
        assertThat(contractPositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkRestrictionIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractPositionRepository.findAll().collectList().block().size();
        // set the field null
        contractPosition.setRestriction(null);

        // Create the ContractPosition, which fails.
        ContractPositionDTO contractPositionDTO = contractPositionMapper.toDto(contractPosition);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractPositionDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<ContractPosition> contractPositionList = contractPositionRepository.findAll().collectList().block();
        assertThat(contractPositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCurrencyIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractPositionRepository.findAll().collectList().block().size();
        // set the field null
        contractPosition.setCurrencyId(null);

        // Create the ContractPosition, which fails.
        ContractPositionDTO contractPositionDTO = contractPositionMapper.toDto(contractPosition);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractPositionDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<ContractPosition> contractPositionList = contractPositionRepository.findAll().collectList().block();
        assertThat(contractPositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkRateAnHourIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractPositionRepository.findAll().collectList().block().size();
        // set the field null
        contractPosition.setRateAnHour(null);

        // Create the ContractPosition, which fails.
        ContractPositionDTO contractPositionDTO = contractPositionMapper.toDto(contractPosition);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractPositionDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<ContractPosition> contractPositionList = contractPositionRepository.findAll().collectList().block();
        assertThat(contractPositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkStatusIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractPositionRepository.findAll().collectList().block().size();
        // set the field null
        contractPosition.setStatusId(null);

        // Create the ContractPosition, which fails.
        ContractPositionDTO contractPositionDTO = contractPositionMapper.toDto(contractPosition);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractPositionDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<ContractPosition> contractPositionList = contractPositionRepository.findAll().collectList().block();
        assertThat(contractPositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllContractPositions() {
        // Initialize the database
        contractPositionRepository.save(contractPosition).block();

        // Get all the contractPositionList
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
            .value(hasItem(contractPosition.getId().intValue()))
            .jsonPath("$.[*].contractPositionId")
            .value(hasItem(DEFAULT_CONTRACT_POSITION_ID))
            .jsonPath("$.[*].employeeId")
            .value(hasItem(DEFAULT_EMPLOYEE_ID.intValue()))
            .jsonPath("$.[*].subcontractId")
            .value(hasItem(DEFAULT_SUBCONTRACT_ID.intValue()))
            .jsonPath("$.[*].numberContractPosition")
            .value(hasItem(DEFAULT_NUMBER_CONTRACT_POSITION))
            .jsonPath("$.[*].restrictionTypeId")
            .value(hasItem(DEFAULT_RESTRICTION_TYPE_ID))
            .jsonPath("$.[*].restriction")
            .value(hasItem(DEFAULT_RESTRICTION))
            .jsonPath("$.[*].currencyId")
            .value(hasItem(DEFAULT_CURRENCY_ID))
            .jsonPath("$.[*].rateAnHour")
            .value(hasItem(DEFAULT_RATE_AN_HOUR.doubleValue()))
            .jsonPath("$.[*].statusId")
            .value(hasItem(DEFAULT_STATUS_ID));
    }

    @Test
    void getContractPosition() {
        // Initialize the database
        contractPositionRepository.save(contractPosition).block();

        // Get the contractPosition
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, contractPosition.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(contractPosition.getId().intValue()))
            .jsonPath("$.contractPositionId")
            .value(is(DEFAULT_CONTRACT_POSITION_ID))
            .jsonPath("$.employeeId")
            .value(is(DEFAULT_EMPLOYEE_ID.intValue()))
            .jsonPath("$.subcontractId")
            .value(is(DEFAULT_SUBCONTRACT_ID.intValue()))
            .jsonPath("$.numberContractPosition")
            .value(is(DEFAULT_NUMBER_CONTRACT_POSITION))
            .jsonPath("$.restrictionTypeId")
            .value(is(DEFAULT_RESTRICTION_TYPE_ID))
            .jsonPath("$.restriction")
            .value(is(DEFAULT_RESTRICTION))
            .jsonPath("$.currencyId")
            .value(is(DEFAULT_CURRENCY_ID))
            .jsonPath("$.rateAnHour")
            .value(is(DEFAULT_RATE_AN_HOUR.doubleValue()))
            .jsonPath("$.statusId")
            .value(is(DEFAULT_STATUS_ID));
    }

    @Test
    void getNonExistingContractPosition() {
        // Get the contractPosition
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putNewContractPosition() throws Exception {
        // Initialize the database
        contractPositionRepository.save(contractPosition).block();

        int databaseSizeBeforeUpdate = contractPositionRepository.findAll().collectList().block().size();

        // Update the contractPosition
        ContractPosition updatedContractPosition = contractPositionRepository.findById(contractPosition.getId()).block();
        updatedContractPosition
            .contractPositionId(UPDATED_CONTRACT_POSITION_ID)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .subcontractId(UPDATED_SUBCONTRACT_ID)
            .numberContractPosition(UPDATED_NUMBER_CONTRACT_POSITION)
            .restrictionTypeId(UPDATED_RESTRICTION_TYPE_ID)
            .restriction(UPDATED_RESTRICTION)
            .currencyId(UPDATED_CURRENCY_ID)
            .rateAnHour(UPDATED_RATE_AN_HOUR)
            .statusId(UPDATED_STATUS_ID);
        ContractPositionDTO contractPositionDTO = contractPositionMapper.toDto(updatedContractPosition);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, contractPositionDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractPositionDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ContractPosition in the database
        List<ContractPosition> contractPositionList = contractPositionRepository.findAll().collectList().block();
        assertThat(contractPositionList).hasSize(databaseSizeBeforeUpdate);
        ContractPosition testContractPosition = contractPositionList.get(contractPositionList.size() - 1);
        assertThat(testContractPosition.getContractPositionId()).isEqualTo(UPDATED_CONTRACT_POSITION_ID);
        assertThat(testContractPosition.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testContractPosition.getSubcontractId()).isEqualTo(UPDATED_SUBCONTRACT_ID);
        assertThat(testContractPosition.getNumberContractPosition()).isEqualTo(UPDATED_NUMBER_CONTRACT_POSITION);
        assertThat(testContractPosition.getRestrictionTypeId()).isEqualTo(UPDATED_RESTRICTION_TYPE_ID);
        assertThat(testContractPosition.getRestriction()).isEqualTo(UPDATED_RESTRICTION);
        assertThat(testContractPosition.getCurrencyId()).isEqualTo(UPDATED_CURRENCY_ID);
        assertThat(testContractPosition.getRateAnHour()).isEqualTo(UPDATED_RATE_AN_HOUR);
        assertThat(testContractPosition.getStatusId()).isEqualTo(UPDATED_STATUS_ID);
    }

    @Test
    void putNonExistingContractPosition() throws Exception {
        int databaseSizeBeforeUpdate = contractPositionRepository.findAll().collectList().block().size();
        contractPosition.setId(count.incrementAndGet());

        // Create the ContractPosition
        ContractPositionDTO contractPositionDTO = contractPositionMapper.toDto(contractPosition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, contractPositionDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractPositionDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ContractPosition in the database
        List<ContractPosition> contractPositionList = contractPositionRepository.findAll().collectList().block();
        assertThat(contractPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchContractPosition() throws Exception {
        int databaseSizeBeforeUpdate = contractPositionRepository.findAll().collectList().block().size();
        contractPosition.setId(count.incrementAndGet());

        // Create the ContractPosition
        ContractPositionDTO contractPositionDTO = contractPositionMapper.toDto(contractPosition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractPositionDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ContractPosition in the database
        List<ContractPosition> contractPositionList = contractPositionRepository.findAll().collectList().block();
        assertThat(contractPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamContractPosition() throws Exception {
        int databaseSizeBeforeUpdate = contractPositionRepository.findAll().collectList().block().size();
        contractPosition.setId(count.incrementAndGet());

        // Create the ContractPosition
        ContractPositionDTO contractPositionDTO = contractPositionMapper.toDto(contractPosition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractPositionDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ContractPosition in the database
        List<ContractPosition> contractPositionList = contractPositionRepository.findAll().collectList().block();
        assertThat(contractPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateContractPositionWithPatch() throws Exception {
        // Initialize the database
        contractPositionRepository.save(contractPosition).block();

        int databaseSizeBeforeUpdate = contractPositionRepository.findAll().collectList().block().size();

        // Update the contractPosition using partial update
        ContractPosition partialUpdatedContractPosition = new ContractPosition();
        partialUpdatedContractPosition.setId(contractPosition.getId());

        partialUpdatedContractPosition
            .subcontractId(UPDATED_SUBCONTRACT_ID)
            .numberContractPosition(UPDATED_NUMBER_CONTRACT_POSITION)
            .restrictionTypeId(UPDATED_RESTRICTION_TYPE_ID)
            .currencyId(UPDATED_CURRENCY_ID)
            .rateAnHour(UPDATED_RATE_AN_HOUR)
            .statusId(UPDATED_STATUS_ID);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedContractPosition.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedContractPosition))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ContractPosition in the database
        List<ContractPosition> contractPositionList = contractPositionRepository.findAll().collectList().block();
        assertThat(contractPositionList).hasSize(databaseSizeBeforeUpdate);
        ContractPosition testContractPosition = contractPositionList.get(contractPositionList.size() - 1);
        assertThat(testContractPosition.getContractPositionId()).isEqualTo(DEFAULT_CONTRACT_POSITION_ID);
        assertThat(testContractPosition.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testContractPosition.getSubcontractId()).isEqualTo(UPDATED_SUBCONTRACT_ID);
        assertThat(testContractPosition.getNumberContractPosition()).isEqualTo(UPDATED_NUMBER_CONTRACT_POSITION);
        assertThat(testContractPosition.getRestrictionTypeId()).isEqualTo(UPDATED_RESTRICTION_TYPE_ID);
        assertThat(testContractPosition.getRestriction()).isEqualTo(DEFAULT_RESTRICTION);
        assertThat(testContractPosition.getCurrencyId()).isEqualTo(UPDATED_CURRENCY_ID);
        assertThat(testContractPosition.getRateAnHour()).isEqualTo(UPDATED_RATE_AN_HOUR);
        assertThat(testContractPosition.getStatusId()).isEqualTo(UPDATED_STATUS_ID);
    }

    @Test
    void fullUpdateContractPositionWithPatch() throws Exception {
        // Initialize the database
        contractPositionRepository.save(contractPosition).block();

        int databaseSizeBeforeUpdate = contractPositionRepository.findAll().collectList().block().size();

        // Update the contractPosition using partial update
        ContractPosition partialUpdatedContractPosition = new ContractPosition();
        partialUpdatedContractPosition.setId(contractPosition.getId());

        partialUpdatedContractPosition
            .contractPositionId(UPDATED_CONTRACT_POSITION_ID)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .subcontractId(UPDATED_SUBCONTRACT_ID)
            .numberContractPosition(UPDATED_NUMBER_CONTRACT_POSITION)
            .restrictionTypeId(UPDATED_RESTRICTION_TYPE_ID)
            .restriction(UPDATED_RESTRICTION)
            .currencyId(UPDATED_CURRENCY_ID)
            .rateAnHour(UPDATED_RATE_AN_HOUR)
            .statusId(UPDATED_STATUS_ID);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedContractPosition.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedContractPosition))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the ContractPosition in the database
        List<ContractPosition> contractPositionList = contractPositionRepository.findAll().collectList().block();
        assertThat(contractPositionList).hasSize(databaseSizeBeforeUpdate);
        ContractPosition testContractPosition = contractPositionList.get(contractPositionList.size() - 1);
        assertThat(testContractPosition.getContractPositionId()).isEqualTo(UPDATED_CONTRACT_POSITION_ID);
        assertThat(testContractPosition.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testContractPosition.getSubcontractId()).isEqualTo(UPDATED_SUBCONTRACT_ID);
        assertThat(testContractPosition.getNumberContractPosition()).isEqualTo(UPDATED_NUMBER_CONTRACT_POSITION);
        assertThat(testContractPosition.getRestrictionTypeId()).isEqualTo(UPDATED_RESTRICTION_TYPE_ID);
        assertThat(testContractPosition.getRestriction()).isEqualTo(UPDATED_RESTRICTION);
        assertThat(testContractPosition.getCurrencyId()).isEqualTo(UPDATED_CURRENCY_ID);
        assertThat(testContractPosition.getRateAnHour()).isEqualTo(UPDATED_RATE_AN_HOUR);
        assertThat(testContractPosition.getStatusId()).isEqualTo(UPDATED_STATUS_ID);
    }

    @Test
    void patchNonExistingContractPosition() throws Exception {
        int databaseSizeBeforeUpdate = contractPositionRepository.findAll().collectList().block().size();
        contractPosition.setId(count.incrementAndGet());

        // Create the ContractPosition
        ContractPositionDTO contractPositionDTO = contractPositionMapper.toDto(contractPosition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, contractPositionDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractPositionDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ContractPosition in the database
        List<ContractPosition> contractPositionList = contractPositionRepository.findAll().collectList().block();
        assertThat(contractPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchContractPosition() throws Exception {
        int databaseSizeBeforeUpdate = contractPositionRepository.findAll().collectList().block().size();
        contractPosition.setId(count.incrementAndGet());

        // Create the ContractPosition
        ContractPositionDTO contractPositionDTO = contractPositionMapper.toDto(contractPosition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractPositionDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the ContractPosition in the database
        List<ContractPosition> contractPositionList = contractPositionRepository.findAll().collectList().block();
        assertThat(contractPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamContractPosition() throws Exception {
        int databaseSizeBeforeUpdate = contractPositionRepository.findAll().collectList().block().size();
        contractPosition.setId(count.incrementAndGet());

        // Create the ContractPosition
        ContractPositionDTO contractPositionDTO = contractPositionMapper.toDto(contractPosition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractPositionDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the ContractPosition in the database
        List<ContractPosition> contractPositionList = contractPositionRepository.findAll().collectList().block();
        assertThat(contractPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteContractPosition() {
        // Initialize the database
        contractPositionRepository.save(contractPosition).block();

        int databaseSizeBeforeDelete = contractPositionRepository.findAll().collectList().block().size();

        // Delete the contractPosition
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, contractPosition.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<ContractPosition> contractPositionList = contractPositionRepository.findAll().collectList().block();
        assertThat(contractPositionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
