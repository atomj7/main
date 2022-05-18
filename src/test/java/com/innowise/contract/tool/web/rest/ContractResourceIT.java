package com.innowise.contract.tool.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import com.innowise.contract.tool.IntegrationTest;
import com.innowise.contract.tool.domain.Contract;
import com.innowise.contract.tool.repository.ContractRepository;
import com.innowise.contract.tool.repository.EntityManager;
import com.innowise.contract.tool.service.dto.ContractDTO;
import com.innowise.contract.tool.service.mapper.ContractMapper;
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
 * Integration tests for the {@link ContractResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ContractResourceIT {

    private static final String DEFAULT_CIPHER = "AAAAAAAAAA";
    private static final String UPDATED_CIPHER = "BBBBBBBBBB";

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;

    private static final String DEFAULT_PROVIDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROVIDER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_ID = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_SUM = 1F;
    private static final Float UPDATED_SUM = 2F;

    private static final Integer DEFAULT_POSITION_COUNT = 1;
    private static final Integer UPDATED_POSITION_COUNT = 2;

    private static final String DEFAULT_CURRENCY_ID = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAYMENT_TERM = 1;
    private static final Integer UPDATED_PAYMENT_TERM = 2;

    private static final String DEFAULT_PAYMENT_TERM_TYPE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_TERM_TYPE_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FINISH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINISH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STATUS_ID = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/contracts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Contract contract;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contract createEntity(EntityManager em) {
        Contract contract = new Contract()
            .cipher(DEFAULT_CIPHER)
            .clientId(DEFAULT_CLIENT_ID)
            .providerId(DEFAULT_PROVIDER_ID)
            .typeId(DEFAULT_TYPE_ID)
            .sum(DEFAULT_SUM)
            .positionCount(DEFAULT_POSITION_COUNT)
            .currencyId(DEFAULT_CURRENCY_ID)
            .paymentTerm(DEFAULT_PAYMENT_TERM)
            .paymentTermTypeId(DEFAULT_PAYMENT_TERM_TYPE_ID)
            .startDate(DEFAULT_START_DATE)
            .finishDate(DEFAULT_FINISH_DATE)
            .statusId(DEFAULT_STATUS_ID)
            .link(DEFAULT_LINK);
        return contract;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contract createUpdatedEntity(EntityManager em) {
        Contract contract = new Contract()
            .cipher(UPDATED_CIPHER)
            .clientId(UPDATED_CLIENT_ID)
            .providerId(UPDATED_PROVIDER_ID)
            .typeId(UPDATED_TYPE_ID)
            .sum(UPDATED_SUM)
            .positionCount(UPDATED_POSITION_COUNT)
            .currencyId(UPDATED_CURRENCY_ID)
            .paymentTerm(UPDATED_PAYMENT_TERM)
            .paymentTermTypeId(UPDATED_PAYMENT_TERM_TYPE_ID)
            .startDate(UPDATED_START_DATE)
            .finishDate(UPDATED_FINISH_DATE)
            .statusId(UPDATED_STATUS_ID)
            .link(UPDATED_LINK);
        return contract;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Contract.class).block();
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
        contract = createEntity(em);
    }

    @Test
    void createContract() throws Exception {
        int databaseSizeBeforeCreate = contractRepository.findAll().collectList().block().size();
        // Create the Contract
        ContractDTO contractDTO = contractMapper.toDto(contract);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Contract in the database
        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeCreate + 1);
        Contract testContract = contractList.get(contractList.size() - 1);
        assertThat(testContract.getCipher()).isEqualTo(DEFAULT_CIPHER);
        assertThat(testContract.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testContract.getProviderId()).isEqualTo(DEFAULT_PROVIDER_ID);
        assertThat(testContract.getTypeId()).isEqualTo(DEFAULT_TYPE_ID);
        assertThat(testContract.getSum()).isEqualTo(DEFAULT_SUM);
        assertThat(testContract.getPositionCount()).isEqualTo(DEFAULT_POSITION_COUNT);
        assertThat(testContract.getCurrencyId()).isEqualTo(DEFAULT_CURRENCY_ID);
        assertThat(testContract.getPaymentTerm()).isEqualTo(DEFAULT_PAYMENT_TERM);
        assertThat(testContract.getPaymentTermTypeId()).isEqualTo(DEFAULT_PAYMENT_TERM_TYPE_ID);
        assertThat(testContract.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testContract.getFinishDate()).isEqualTo(DEFAULT_FINISH_DATE);
        assertThat(testContract.getStatusId()).isEqualTo(DEFAULT_STATUS_ID);
        assertThat(testContract.getLink()).isEqualTo(DEFAULT_LINK);
    }

    @Test
    void createContractWithExistingId() throws Exception {
        // Create the Contract with an existing ID
        contract.setId(1L);
        ContractDTO contractDTO = contractMapper.toDto(contract);

        int databaseSizeBeforeCreate = contractRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Contract in the database
        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkCipherIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractRepository.findAll().collectList().block().size();
        // set the field null
        contract.setCipher(null);

        // Create the Contract, which fails.
        ContractDTO contractDTO = contractMapper.toDto(contract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractRepository.findAll().collectList().block().size();
        // set the field null
        contract.setClientId(null);

        // Create the Contract, which fails.
        ContractDTO contractDTO = contractMapper.toDto(contract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkProviderIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractRepository.findAll().collectList().block().size();
        // set the field null
        contract.setProviderId(null);

        // Create the Contract, which fails.
        ContractDTO contractDTO = contractMapper.toDto(contract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkSumIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractRepository.findAll().collectList().block().size();
        // set the field null
        contract.setSum(null);

        // Create the Contract, which fails.
        ContractDTO contractDTO = contractMapper.toDto(contract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPositionCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractRepository.findAll().collectList().block().size();
        // set the field null
        contract.setPositionCount(null);

        // Create the Contract, which fails.
        ContractDTO contractDTO = contractMapper.toDto(contract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCurrencyIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractRepository.findAll().collectList().block().size();
        // set the field null
        contract.setCurrencyId(null);

        // Create the Contract, which fails.
        ContractDTO contractDTO = contractMapper.toDto(contract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPaymentTermIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractRepository.findAll().collectList().block().size();
        // set the field null
        contract.setPaymentTerm(null);

        // Create the Contract, which fails.
        ContractDTO contractDTO = contractMapper.toDto(contract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPaymentTermTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractRepository.findAll().collectList().block().size();
        // set the field null
        contract.setPaymentTermTypeId(null);

        // Create the Contract, which fails.
        ContractDTO contractDTO = contractMapper.toDto(contract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractRepository.findAll().collectList().block().size();
        // set the field null
        contract.setStartDate(null);

        // Create the Contract, which fails.
        ContractDTO contractDTO = contractMapper.toDto(contract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkFinishDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractRepository.findAll().collectList().block().size();
        // set the field null
        contract.setFinishDate(null);

        // Create the Contract, which fails.
        ContractDTO contractDTO = contractMapper.toDto(contract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkStatusIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractRepository.findAll().collectList().block().size();
        // set the field null
        contract.setStatusId(null);

        // Create the Contract, which fails.
        ContractDTO contractDTO = contractMapper.toDto(contract);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllContracts() {
        // Initialize the database
        contractRepository.save(contract).block();

        // Get all the contractList
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
            .value(hasItem(contract.getId().intValue()))
            .jsonPath("$.[*].cipher")
            .value(hasItem(DEFAULT_CIPHER))
            .jsonPath("$.[*].clientId")
            .value(hasItem(DEFAULT_CLIENT_ID.intValue()))
            .jsonPath("$.[*].providerId")
            .value(hasItem(DEFAULT_PROVIDER_ID))
            .jsonPath("$.[*].typeId")
            .value(hasItem(DEFAULT_TYPE_ID))
            .jsonPath("$.[*].sum")
            .value(hasItem(DEFAULT_SUM.doubleValue()))
            .jsonPath("$.[*].positionCount")
            .value(hasItem(DEFAULT_POSITION_COUNT))
            .jsonPath("$.[*].currencyId")
            .value(hasItem(DEFAULT_CURRENCY_ID))
            .jsonPath("$.[*].paymentTerm")
            .value(hasItem(DEFAULT_PAYMENT_TERM))
            .jsonPath("$.[*].paymentTermTypeId")
            .value(hasItem(DEFAULT_PAYMENT_TERM_TYPE_ID))
            .jsonPath("$.[*].startDate")
            .value(hasItem(DEFAULT_START_DATE.toString()))
            .jsonPath("$.[*].finishDate")
            .value(hasItem(DEFAULT_FINISH_DATE.toString()))
            .jsonPath("$.[*].statusId")
            .value(hasItem(DEFAULT_STATUS_ID))
            .jsonPath("$.[*].link")
            .value(hasItem(DEFAULT_LINK));
    }

    @Test
    void getContract() {
        // Initialize the database
        contractRepository.save(contract).block();

        // Get the contract
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, contract.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(contract.getId().intValue()))
            .jsonPath("$.cipher")
            .value(is(DEFAULT_CIPHER))
            .jsonPath("$.clientId")
            .value(is(DEFAULT_CLIENT_ID.intValue()))
            .jsonPath("$.providerId")
            .value(is(DEFAULT_PROVIDER_ID))
            .jsonPath("$.typeId")
            .value(is(DEFAULT_TYPE_ID))
            .jsonPath("$.sum")
            .value(is(DEFAULT_SUM.doubleValue()))
            .jsonPath("$.positionCount")
            .value(is(DEFAULT_POSITION_COUNT))
            .jsonPath("$.currencyId")
            .value(is(DEFAULT_CURRENCY_ID))
            .jsonPath("$.paymentTerm")
            .value(is(DEFAULT_PAYMENT_TERM))
            .jsonPath("$.paymentTermTypeId")
            .value(is(DEFAULT_PAYMENT_TERM_TYPE_ID))
            .jsonPath("$.startDate")
            .value(is(DEFAULT_START_DATE.toString()))
            .jsonPath("$.finishDate")
            .value(is(DEFAULT_FINISH_DATE.toString()))
            .jsonPath("$.statusId")
            .value(is(DEFAULT_STATUS_ID))
            .jsonPath("$.link")
            .value(is(DEFAULT_LINK));
    }

    @Test
    void getNonExistingContract() {
        // Get the contract
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putNewContract() throws Exception {
        // Initialize the database
        contractRepository.save(contract).block();

        int databaseSizeBeforeUpdate = contractRepository.findAll().collectList().block().size();

        // Update the contract
        Contract updatedContract = contractRepository.findById(contract.getId()).block();
        updatedContract
            .cipher(UPDATED_CIPHER)
            .clientId(UPDATED_CLIENT_ID)
            .providerId(UPDATED_PROVIDER_ID)
            .typeId(UPDATED_TYPE_ID)
            .sum(UPDATED_SUM)
            .positionCount(UPDATED_POSITION_COUNT)
            .currencyId(UPDATED_CURRENCY_ID)
            .paymentTerm(UPDATED_PAYMENT_TERM)
            .paymentTermTypeId(UPDATED_PAYMENT_TERM_TYPE_ID)
            .startDate(UPDATED_START_DATE)
            .finishDate(UPDATED_FINISH_DATE)
            .statusId(UPDATED_STATUS_ID)
            .link(UPDATED_LINK);
        ContractDTO contractDTO = contractMapper.toDto(updatedContract);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, contractDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Contract in the database
        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeUpdate);
        Contract testContract = contractList.get(contractList.size() - 1);
        assertThat(testContract.getCipher()).isEqualTo(UPDATED_CIPHER);
        assertThat(testContract.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testContract.getProviderId()).isEqualTo(UPDATED_PROVIDER_ID);
        assertThat(testContract.getTypeId()).isEqualTo(UPDATED_TYPE_ID);
        assertThat(testContract.getSum()).isEqualTo(UPDATED_SUM);
        assertThat(testContract.getPositionCount()).isEqualTo(UPDATED_POSITION_COUNT);
        assertThat(testContract.getCurrencyId()).isEqualTo(UPDATED_CURRENCY_ID);
        assertThat(testContract.getPaymentTerm()).isEqualTo(UPDATED_PAYMENT_TERM);
        assertThat(testContract.getPaymentTermTypeId()).isEqualTo(UPDATED_PAYMENT_TERM_TYPE_ID);
        assertThat(testContract.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testContract.getFinishDate()).isEqualTo(UPDATED_FINISH_DATE);
        assertThat(testContract.getStatusId()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testContract.getLink()).isEqualTo(UPDATED_LINK);
    }

    @Test
    void putNonExistingContract() throws Exception {
        int databaseSizeBeforeUpdate = contractRepository.findAll().collectList().block().size();
        contract.setId(count.incrementAndGet());

        // Create the Contract
        ContractDTO contractDTO = contractMapper.toDto(contract);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, contractDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Contract in the database
        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchContract() throws Exception {
        int databaseSizeBeforeUpdate = contractRepository.findAll().collectList().block().size();
        contract.setId(count.incrementAndGet());

        // Create the Contract
        ContractDTO contractDTO = contractMapper.toDto(contract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Contract in the database
        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamContract() throws Exception {
        int databaseSizeBeforeUpdate = contractRepository.findAll().collectList().block().size();
        contract.setId(count.incrementAndGet());

        // Create the Contract
        ContractDTO contractDTO = contractMapper.toDto(contract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Contract in the database
        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateContractWithPatch() throws Exception {
        // Initialize the database
        contractRepository.save(contract).block();

        int databaseSizeBeforeUpdate = contractRepository.findAll().collectList().block().size();

        // Update the contract using partial update
        Contract partialUpdatedContract = new Contract();
        partialUpdatedContract.setId(contract.getId());

        partialUpdatedContract
            .clientId(UPDATED_CLIENT_ID)
            .providerId(UPDATED_PROVIDER_ID)
            .positionCount(UPDATED_POSITION_COUNT)
            .paymentTermTypeId(UPDATED_PAYMENT_TERM_TYPE_ID)
            .statusId(UPDATED_STATUS_ID)
            .link(UPDATED_LINK);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedContract.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedContract))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Contract in the database
        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeUpdate);
        Contract testContract = contractList.get(contractList.size() - 1);
        assertThat(testContract.getCipher()).isEqualTo(DEFAULT_CIPHER);
        assertThat(testContract.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testContract.getProviderId()).isEqualTo(UPDATED_PROVIDER_ID);
        assertThat(testContract.getTypeId()).isEqualTo(DEFAULT_TYPE_ID);
        assertThat(testContract.getSum()).isEqualTo(DEFAULT_SUM);
        assertThat(testContract.getPositionCount()).isEqualTo(UPDATED_POSITION_COUNT);
        assertThat(testContract.getCurrencyId()).isEqualTo(DEFAULT_CURRENCY_ID);
        assertThat(testContract.getPaymentTerm()).isEqualTo(DEFAULT_PAYMENT_TERM);
        assertThat(testContract.getPaymentTermTypeId()).isEqualTo(UPDATED_PAYMENT_TERM_TYPE_ID);
        assertThat(testContract.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testContract.getFinishDate()).isEqualTo(DEFAULT_FINISH_DATE);
        assertThat(testContract.getStatusId()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testContract.getLink()).isEqualTo(UPDATED_LINK);
    }

    @Test
    void fullUpdateContractWithPatch() throws Exception {
        // Initialize the database
        contractRepository.save(contract).block();

        int databaseSizeBeforeUpdate = contractRepository.findAll().collectList().block().size();

        // Update the contract using partial update
        Contract partialUpdatedContract = new Contract();
        partialUpdatedContract.setId(contract.getId());

        partialUpdatedContract
            .cipher(UPDATED_CIPHER)
            .clientId(UPDATED_CLIENT_ID)
            .providerId(UPDATED_PROVIDER_ID)
            .typeId(UPDATED_TYPE_ID)
            .sum(UPDATED_SUM)
            .positionCount(UPDATED_POSITION_COUNT)
            .currencyId(UPDATED_CURRENCY_ID)
            .paymentTerm(UPDATED_PAYMENT_TERM)
            .paymentTermTypeId(UPDATED_PAYMENT_TERM_TYPE_ID)
            .startDate(UPDATED_START_DATE)
            .finishDate(UPDATED_FINISH_DATE)
            .statusId(UPDATED_STATUS_ID)
            .link(UPDATED_LINK);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedContract.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedContract))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Contract in the database
        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeUpdate);
        Contract testContract = contractList.get(contractList.size() - 1);
        assertThat(testContract.getCipher()).isEqualTo(UPDATED_CIPHER);
        assertThat(testContract.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testContract.getProviderId()).isEqualTo(UPDATED_PROVIDER_ID);
        assertThat(testContract.getTypeId()).isEqualTo(UPDATED_TYPE_ID);
        assertThat(testContract.getSum()).isEqualTo(UPDATED_SUM);
        assertThat(testContract.getPositionCount()).isEqualTo(UPDATED_POSITION_COUNT);
        assertThat(testContract.getCurrencyId()).isEqualTo(UPDATED_CURRENCY_ID);
        assertThat(testContract.getPaymentTerm()).isEqualTo(UPDATED_PAYMENT_TERM);
        assertThat(testContract.getPaymentTermTypeId()).isEqualTo(UPDATED_PAYMENT_TERM_TYPE_ID);
        assertThat(testContract.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testContract.getFinishDate()).isEqualTo(UPDATED_FINISH_DATE);
        assertThat(testContract.getStatusId()).isEqualTo(UPDATED_STATUS_ID);
        assertThat(testContract.getLink()).isEqualTo(UPDATED_LINK);
    }

    @Test
    void patchNonExistingContract() throws Exception {
        int databaseSizeBeforeUpdate = contractRepository.findAll().collectList().block().size();
        contract.setId(count.incrementAndGet());

        // Create the Contract
        ContractDTO contractDTO = contractMapper.toDto(contract);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, contractDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Contract in the database
        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchContract() throws Exception {
        int databaseSizeBeforeUpdate = contractRepository.findAll().collectList().block().size();
        contract.setId(count.incrementAndGet());

        // Create the Contract
        ContractDTO contractDTO = contractMapper.toDto(contract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Contract in the database
        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamContract() throws Exception {
        int databaseSizeBeforeUpdate = contractRepository.findAll().collectList().block().size();
        contract.setId(count.incrementAndGet());

        // Create the Contract
        ContractDTO contractDTO = contractMapper.toDto(contract);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(contractDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Contract in the database
        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteContract() {
        // Initialize the database
        contractRepository.save(contract).block();

        int databaseSizeBeforeDelete = contractRepository.findAll().collectList().block().size();

        // Delete the contract
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, contract.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Contract> contractList = contractRepository.findAll().collectList().block();
        assertThat(contractList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
