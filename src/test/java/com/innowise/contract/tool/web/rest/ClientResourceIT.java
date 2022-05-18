package com.innowise.contract.tool.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import com.innowise.contract.tool.IntegrationTest;
import com.innowise.contract.tool.domain.Client;
import com.innowise.contract.tool.repository.ClientRepository;
import com.innowise.contract.tool.repository.EntityManager;
import com.innowise.contract.tool.service.dto.ClientDTO;
import com.innowise.contract.tool.service.mapper.ClientMapper;
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
 * Integration tests for the {@link ClientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ClientResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_SALES_MANAGER_ID = 1L;
    private static final Long UPDATED_SALES_MANAGER_ID = 2L;

    private static final String DEFAULT_COUNTRY_ID = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PARTNER_STATUS = false;
    private static final Boolean UPDATED_PARTNER_STATUS = true;

    private static final String DEFAULT_CONTACT_PERSON = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PERSON = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_INDUSTRY_ID = "AAAAAAAAAA";
    private static final String UPDATED_INDUSTRY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SIGNER = "AAAAAAAAAA";
    private static final String UPDATED_SIGNER = "BBBBBBBBBB";

    private static final String DEFAULT_SIGNER_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_SIGNER_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_LEGAL_ADRESS = "AAAAAAAAAA";
    private static final String UPDATED_LEGAL_ADRESS = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTRATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATION_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BASE_OF_ACTIVITY_ID = "AAAAAAAAAA";
    private static final String UPDATED_BASE_OF_ACTIVITY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_VAT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VAT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ADRESS = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ADRESS = "BBBBBBBBBB";

    private static final String DEFAULT_SWIFT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SWIFT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_IBAN_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IBAN_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/clients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Client client;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createEntity(EntityManager em) {
        Client client = new Client()
            .name(DEFAULT_NAME)
            .salesManagerId(DEFAULT_SALES_MANAGER_ID)
            .countryId(DEFAULT_COUNTRY_ID)
            .partnerStatus(DEFAULT_PARTNER_STATUS)
            .contactPerson(DEFAULT_CONTACT_PERSON)
            .contractEmail(DEFAULT_CONTRACT_EMAIL)
            .industryId(DEFAULT_INDUSTRY_ID)
            .signer(DEFAULT_SIGNER)
            .signerPosition(DEFAULT_SIGNER_POSITION)
            .legalAdress(DEFAULT_LEGAL_ADRESS)
            .registrationNumber(DEFAULT_REGISTRATION_NUMBER)
            .baseOfActivityId(DEFAULT_BASE_OF_ACTIVITY_ID)
            .vatNumber(DEFAULT_VAT_NUMBER)
            .bankName(DEFAULT_BANK_NAME)
            .bankAdress(DEFAULT_BANK_ADRESS)
            .swiftCode(DEFAULT_SWIFT_CODE)
            .ibanCode(DEFAULT_IBAN_CODE);
        return client;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createUpdatedEntity(EntityManager em) {
        Client client = new Client()
            .name(UPDATED_NAME)
            .salesManagerId(UPDATED_SALES_MANAGER_ID)
            .countryId(UPDATED_COUNTRY_ID)
            .partnerStatus(UPDATED_PARTNER_STATUS)
            .contactPerson(UPDATED_CONTACT_PERSON)
            .contractEmail(UPDATED_CONTRACT_EMAIL)
            .industryId(UPDATED_INDUSTRY_ID)
            .signer(UPDATED_SIGNER)
            .signerPosition(UPDATED_SIGNER_POSITION)
            .legalAdress(UPDATED_LEGAL_ADRESS)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .baseOfActivityId(UPDATED_BASE_OF_ACTIVITY_ID)
            .vatNumber(UPDATED_VAT_NUMBER)
            .bankName(UPDATED_BANK_NAME)
            .bankAdress(UPDATED_BANK_ADRESS)
            .swiftCode(UPDATED_SWIFT_CODE)
            .ibanCode(UPDATED_IBAN_CODE);
        return client;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Client.class).block();
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
        client = createEntity(em);
    }

    @Test
    void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().collectList().block().size();
        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClient.getSalesManagerId()).isEqualTo(DEFAULT_SALES_MANAGER_ID);
        assertThat(testClient.getCountryId()).isEqualTo(DEFAULT_COUNTRY_ID);
        assertThat(testClient.getPartnerStatus()).isEqualTo(DEFAULT_PARTNER_STATUS);
        assertThat(testClient.getContactPerson()).isEqualTo(DEFAULT_CONTACT_PERSON);
        assertThat(testClient.getContractEmail()).isEqualTo(DEFAULT_CONTRACT_EMAIL);
        assertThat(testClient.getIndustryId()).isEqualTo(DEFAULT_INDUSTRY_ID);
        assertThat(testClient.getSigner()).isEqualTo(DEFAULT_SIGNER);
        assertThat(testClient.getSignerPosition()).isEqualTo(DEFAULT_SIGNER_POSITION);
        assertThat(testClient.getLegalAdress()).isEqualTo(DEFAULT_LEGAL_ADRESS);
        assertThat(testClient.getRegistrationNumber()).isEqualTo(DEFAULT_REGISTRATION_NUMBER);
        assertThat(testClient.getBaseOfActivityId()).isEqualTo(DEFAULT_BASE_OF_ACTIVITY_ID);
        assertThat(testClient.getVatNumber()).isEqualTo(DEFAULT_VAT_NUMBER);
        assertThat(testClient.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testClient.getBankAdress()).isEqualTo(DEFAULT_BANK_ADRESS);
        assertThat(testClient.getSwiftCode()).isEqualTo(DEFAULT_SWIFT_CODE);
        assertThat(testClient.getIbanCode()).isEqualTo(DEFAULT_IBAN_CODE);
    }

    @Test
    void createClientWithExistingId() throws Exception {
        // Create the Client with an existing ID
        client.setId(1L);
        ClientDTO clientDTO = clientMapper.toDto(client);

        int databaseSizeBeforeCreate = clientRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().collectList().block().size();
        // set the field null
        client.setName(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkSalesManagerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().collectList().block().size();
        // set the field null
        client.setSalesManagerId(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCountryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().collectList().block().size();
        // set the field null
        client.setCountryId(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPartnerStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().collectList().block().size();
        // set the field null
        client.setPartnerStatus(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkContactPersonIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().collectList().block().size();
        // set the field null
        client.setContactPerson(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkContractEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().collectList().block().size();
        // set the field null
        client.setContractEmail(null);

        // Create the Client, which fails.
        ClientDTO clientDTO = clientMapper.toDto(client);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllClients() {
        // Initialize the database
        clientRepository.save(client).block();

        // Get all the clientList
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
            .value(hasItem(client.getId().intValue()))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME))
            .jsonPath("$.[*].salesManagerId")
            .value(hasItem(DEFAULT_SALES_MANAGER_ID.intValue()))
            .jsonPath("$.[*].countryId")
            .value(hasItem(DEFAULT_COUNTRY_ID))
            .jsonPath("$.[*].partnerStatus")
            .value(hasItem(DEFAULT_PARTNER_STATUS.booleanValue()))
            .jsonPath("$.[*].contactPerson")
            .value(hasItem(DEFAULT_CONTACT_PERSON))
            .jsonPath("$.[*].contractEmail")
            .value(hasItem(DEFAULT_CONTRACT_EMAIL))
            .jsonPath("$.[*].industryId")
            .value(hasItem(DEFAULT_INDUSTRY_ID))
            .jsonPath("$.[*].signer")
            .value(hasItem(DEFAULT_SIGNER))
            .jsonPath("$.[*].signerPosition")
            .value(hasItem(DEFAULT_SIGNER_POSITION))
            .jsonPath("$.[*].legalAdress")
            .value(hasItem(DEFAULT_LEGAL_ADRESS))
            .jsonPath("$.[*].registrationNumber")
            .value(hasItem(DEFAULT_REGISTRATION_NUMBER))
            .jsonPath("$.[*].baseOfActivityId")
            .value(hasItem(DEFAULT_BASE_OF_ACTIVITY_ID))
            .jsonPath("$.[*].vatNumber")
            .value(hasItem(DEFAULT_VAT_NUMBER))
            .jsonPath("$.[*].bankName")
            .value(hasItem(DEFAULT_BANK_NAME))
            .jsonPath("$.[*].bankAdress")
            .value(hasItem(DEFAULT_BANK_ADRESS))
            .jsonPath("$.[*].swiftCode")
            .value(hasItem(DEFAULT_SWIFT_CODE))
            .jsonPath("$.[*].ibanCode")
            .value(hasItem(DEFAULT_IBAN_CODE));
    }

    @Test
    void getClient() {
        // Initialize the database
        clientRepository.save(client).block();

        // Get the client
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, client.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(client.getId().intValue()))
            .jsonPath("$.name")
            .value(is(DEFAULT_NAME))
            .jsonPath("$.salesManagerId")
            .value(is(DEFAULT_SALES_MANAGER_ID.intValue()))
            .jsonPath("$.countryId")
            .value(is(DEFAULT_COUNTRY_ID))
            .jsonPath("$.partnerStatus")
            .value(is(DEFAULT_PARTNER_STATUS.booleanValue()))
            .jsonPath("$.contactPerson")
            .value(is(DEFAULT_CONTACT_PERSON))
            .jsonPath("$.contractEmail")
            .value(is(DEFAULT_CONTRACT_EMAIL))
            .jsonPath("$.industryId")
            .value(is(DEFAULT_INDUSTRY_ID))
            .jsonPath("$.signer")
            .value(is(DEFAULT_SIGNER))
            .jsonPath("$.signerPosition")
            .value(is(DEFAULT_SIGNER_POSITION))
            .jsonPath("$.legalAdress")
            .value(is(DEFAULT_LEGAL_ADRESS))
            .jsonPath("$.registrationNumber")
            .value(is(DEFAULT_REGISTRATION_NUMBER))
            .jsonPath("$.baseOfActivityId")
            .value(is(DEFAULT_BASE_OF_ACTIVITY_ID))
            .jsonPath("$.vatNumber")
            .value(is(DEFAULT_VAT_NUMBER))
            .jsonPath("$.bankName")
            .value(is(DEFAULT_BANK_NAME))
            .jsonPath("$.bankAdress")
            .value(is(DEFAULT_BANK_ADRESS))
            .jsonPath("$.swiftCode")
            .value(is(DEFAULT_SWIFT_CODE))
            .jsonPath("$.ibanCode")
            .value(is(DEFAULT_IBAN_CODE));
    }

    @Test
    void getNonExistingClient() {
        // Get the client
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putNewClient() throws Exception {
        // Initialize the database
        clientRepository.save(client).block();

        int databaseSizeBeforeUpdate = clientRepository.findAll().collectList().block().size();

        // Update the client
        Client updatedClient = clientRepository.findById(client.getId()).block();
        updatedClient
            .name(UPDATED_NAME)
            .salesManagerId(UPDATED_SALES_MANAGER_ID)
            .countryId(UPDATED_COUNTRY_ID)
            .partnerStatus(UPDATED_PARTNER_STATUS)
            .contactPerson(UPDATED_CONTACT_PERSON)
            .contractEmail(UPDATED_CONTRACT_EMAIL)
            .industryId(UPDATED_INDUSTRY_ID)
            .signer(UPDATED_SIGNER)
            .signerPosition(UPDATED_SIGNER_POSITION)
            .legalAdress(UPDATED_LEGAL_ADRESS)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .baseOfActivityId(UPDATED_BASE_OF_ACTIVITY_ID)
            .vatNumber(UPDATED_VAT_NUMBER)
            .bankName(UPDATED_BANK_NAME)
            .bankAdress(UPDATED_BANK_ADRESS)
            .swiftCode(UPDATED_SWIFT_CODE)
            .ibanCode(UPDATED_IBAN_CODE);
        ClientDTO clientDTO = clientMapper.toDto(updatedClient);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, clientDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClient.getSalesManagerId()).isEqualTo(UPDATED_SALES_MANAGER_ID);
        assertThat(testClient.getCountryId()).isEqualTo(UPDATED_COUNTRY_ID);
        assertThat(testClient.getPartnerStatus()).isEqualTo(UPDATED_PARTNER_STATUS);
        assertThat(testClient.getContactPerson()).isEqualTo(UPDATED_CONTACT_PERSON);
        assertThat(testClient.getContractEmail()).isEqualTo(UPDATED_CONTRACT_EMAIL);
        assertThat(testClient.getIndustryId()).isEqualTo(UPDATED_INDUSTRY_ID);
        assertThat(testClient.getSigner()).isEqualTo(UPDATED_SIGNER);
        assertThat(testClient.getSignerPosition()).isEqualTo(UPDATED_SIGNER_POSITION);
        assertThat(testClient.getLegalAdress()).isEqualTo(UPDATED_LEGAL_ADRESS);
        assertThat(testClient.getRegistrationNumber()).isEqualTo(UPDATED_REGISTRATION_NUMBER);
        assertThat(testClient.getBaseOfActivityId()).isEqualTo(UPDATED_BASE_OF_ACTIVITY_ID);
        assertThat(testClient.getVatNumber()).isEqualTo(UPDATED_VAT_NUMBER);
        assertThat(testClient.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testClient.getBankAdress()).isEqualTo(UPDATED_BANK_ADRESS);
        assertThat(testClient.getSwiftCode()).isEqualTo(UPDATED_SWIFT_CODE);
        assertThat(testClient.getIbanCode()).isEqualTo(UPDATED_IBAN_CODE);
    }

    @Test
    void putNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().collectList().block().size();
        client.setId(count.incrementAndGet());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, clientDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().collectList().block().size();
        client.setId(count.incrementAndGet());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().collectList().block().size();
        client.setId(count.incrementAndGet());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateClientWithPatch() throws Exception {
        // Initialize the database
        clientRepository.save(client).block();

        int databaseSizeBeforeUpdate = clientRepository.findAll().collectList().block().size();

        // Update the client using partial update
        Client partialUpdatedClient = new Client();
        partialUpdatedClient.setId(client.getId());

        partialUpdatedClient
            .name(UPDATED_NAME)
            .salesManagerId(UPDATED_SALES_MANAGER_ID)
            .countryId(UPDATED_COUNTRY_ID)
            .industryId(UPDATED_INDUSTRY_ID)
            .signerPosition(UPDATED_SIGNER_POSITION)
            .legalAdress(UPDATED_LEGAL_ADRESS)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .baseOfActivityId(UPDATED_BASE_OF_ACTIVITY_ID)
            .vatNumber(UPDATED_VAT_NUMBER)
            .bankName(UPDATED_BANK_NAME)
            .ibanCode(UPDATED_IBAN_CODE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedClient.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedClient))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClient.getSalesManagerId()).isEqualTo(UPDATED_SALES_MANAGER_ID);
        assertThat(testClient.getCountryId()).isEqualTo(UPDATED_COUNTRY_ID);
        assertThat(testClient.getPartnerStatus()).isEqualTo(DEFAULT_PARTNER_STATUS);
        assertThat(testClient.getContactPerson()).isEqualTo(DEFAULT_CONTACT_PERSON);
        assertThat(testClient.getContractEmail()).isEqualTo(DEFAULT_CONTRACT_EMAIL);
        assertThat(testClient.getIndustryId()).isEqualTo(UPDATED_INDUSTRY_ID);
        assertThat(testClient.getSigner()).isEqualTo(DEFAULT_SIGNER);
        assertThat(testClient.getSignerPosition()).isEqualTo(UPDATED_SIGNER_POSITION);
        assertThat(testClient.getLegalAdress()).isEqualTo(UPDATED_LEGAL_ADRESS);
        assertThat(testClient.getRegistrationNumber()).isEqualTo(UPDATED_REGISTRATION_NUMBER);
        assertThat(testClient.getBaseOfActivityId()).isEqualTo(UPDATED_BASE_OF_ACTIVITY_ID);
        assertThat(testClient.getVatNumber()).isEqualTo(UPDATED_VAT_NUMBER);
        assertThat(testClient.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testClient.getBankAdress()).isEqualTo(DEFAULT_BANK_ADRESS);
        assertThat(testClient.getSwiftCode()).isEqualTo(DEFAULT_SWIFT_CODE);
        assertThat(testClient.getIbanCode()).isEqualTo(UPDATED_IBAN_CODE);
    }

    @Test
    void fullUpdateClientWithPatch() throws Exception {
        // Initialize the database
        clientRepository.save(client).block();

        int databaseSizeBeforeUpdate = clientRepository.findAll().collectList().block().size();

        // Update the client using partial update
        Client partialUpdatedClient = new Client();
        partialUpdatedClient.setId(client.getId());

        partialUpdatedClient
            .name(UPDATED_NAME)
            .salesManagerId(UPDATED_SALES_MANAGER_ID)
            .countryId(UPDATED_COUNTRY_ID)
            .partnerStatus(UPDATED_PARTNER_STATUS)
            .contactPerson(UPDATED_CONTACT_PERSON)
            .contractEmail(UPDATED_CONTRACT_EMAIL)
            .industryId(UPDATED_INDUSTRY_ID)
            .signer(UPDATED_SIGNER)
            .signerPosition(UPDATED_SIGNER_POSITION)
            .legalAdress(UPDATED_LEGAL_ADRESS)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .baseOfActivityId(UPDATED_BASE_OF_ACTIVITY_ID)
            .vatNumber(UPDATED_VAT_NUMBER)
            .bankName(UPDATED_BANK_NAME)
            .bankAdress(UPDATED_BANK_ADRESS)
            .swiftCode(UPDATED_SWIFT_CODE)
            .ibanCode(UPDATED_IBAN_CODE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedClient.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedClient))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClient.getSalesManagerId()).isEqualTo(UPDATED_SALES_MANAGER_ID);
        assertThat(testClient.getCountryId()).isEqualTo(UPDATED_COUNTRY_ID);
        assertThat(testClient.getPartnerStatus()).isEqualTo(UPDATED_PARTNER_STATUS);
        assertThat(testClient.getContactPerson()).isEqualTo(UPDATED_CONTACT_PERSON);
        assertThat(testClient.getContractEmail()).isEqualTo(UPDATED_CONTRACT_EMAIL);
        assertThat(testClient.getIndustryId()).isEqualTo(UPDATED_INDUSTRY_ID);
        assertThat(testClient.getSigner()).isEqualTo(UPDATED_SIGNER);
        assertThat(testClient.getSignerPosition()).isEqualTo(UPDATED_SIGNER_POSITION);
        assertThat(testClient.getLegalAdress()).isEqualTo(UPDATED_LEGAL_ADRESS);
        assertThat(testClient.getRegistrationNumber()).isEqualTo(UPDATED_REGISTRATION_NUMBER);
        assertThat(testClient.getBaseOfActivityId()).isEqualTo(UPDATED_BASE_OF_ACTIVITY_ID);
        assertThat(testClient.getVatNumber()).isEqualTo(UPDATED_VAT_NUMBER);
        assertThat(testClient.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testClient.getBankAdress()).isEqualTo(UPDATED_BANK_ADRESS);
        assertThat(testClient.getSwiftCode()).isEqualTo(UPDATED_SWIFT_CODE);
        assertThat(testClient.getIbanCode()).isEqualTo(UPDATED_IBAN_CODE);
    }

    @Test
    void patchNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().collectList().block().size();
        client.setId(count.incrementAndGet());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, clientDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().collectList().block().size();
        client.setId(count.incrementAndGet());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().collectList().block().size();
        client.setId(count.incrementAndGet());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(clientDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteClient() {
        // Initialize the database
        clientRepository.save(client).block();

        int databaseSizeBeforeDelete = clientRepository.findAll().collectList().block().size();

        // Delete the client
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, client.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Client> clientList = clientRepository.findAll().collectList().block();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
