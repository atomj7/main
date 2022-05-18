package com.innowise.contract.tool.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

import com.innowise.contract.tool.IntegrationTest;
import com.innowise.contract.tool.domain.Project;
import com.innowise.contract.tool.repository.EntityManager;
import com.innowise.contract.tool.repository.ProjectRepository;
import com.innowise.contract.tool.service.dto.ProjectDTO;
import com.innowise.contract.tool.service.mapper.ProjectMapper;
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
 * Integration tests for the {@link ProjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ProjectResourceIT {

    private static final Long DEFAULT_CLIENT_ID = 1L;
    private static final Long UPDATED_CLIENT_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FINISH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINISH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_ID = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/projects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Project project;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Project createEntity(EntityManager em) {
        Project project = new Project()
            .clientId(DEFAULT_CLIENT_ID)
            .name(DEFAULT_NAME)
            .startDate(DEFAULT_START_DATE)
            .finishDate(DEFAULT_FINISH_DATE)
            .link(DEFAULT_LINK)
            .statusId(DEFAULT_STATUS_ID);
        return project;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Project createUpdatedEntity(EntityManager em) {
        Project project = new Project()
            .clientId(UPDATED_CLIENT_ID)
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .finishDate(UPDATED_FINISH_DATE)
            .link(UPDATED_LINK)
            .statusId(UPDATED_STATUS_ID);
        return project;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Project.class).block();
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
        project = createEntity(em);
    }

    @Test
    void createProject() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().collectList().block().size();
        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(projectDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate + 1);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testProject.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProject.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testProject.getFinishDate()).isEqualTo(DEFAULT_FINISH_DATE);
        assertThat(testProject.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testProject.getStatusId()).isEqualTo(DEFAULT_STATUS_ID);
    }

    @Test
    void createProjectWithExistingId() throws Exception {
        // Create the Project with an existing ID
        project.setId(1L);
        ProjectDTO projectDTO = projectMapper.toDto(project);

        int databaseSizeBeforeCreate = projectRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(projectDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().collectList().block().size();
        // set the field null
        project.setClientId(null);

        // Create the Project, which fails.
        ProjectDTO projectDTO = projectMapper.toDto(project);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(projectDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().collectList().block().size();
        // set the field null
        project.setName(null);

        // Create the Project, which fails.
        ProjectDTO projectDTO = projectMapper.toDto(project);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(projectDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().collectList().block().size();
        // set the field null
        project.setStartDate(null);

        // Create the Project, which fails.
        ProjectDTO projectDTO = projectMapper.toDto(project);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(projectDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkStatusIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().collectList().block().size();
        // set the field null
        project.setStatusId(null);

        // Create the Project, which fails.
        ProjectDTO projectDTO = projectMapper.toDto(project);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(projectDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllProjectsAsStream() {
        // Initialize the database
        projectRepository.save(project).block();

        List<Project> projectList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(ProjectDTO.class)
            .getResponseBody()
            .map(projectMapper::toEntity)
            .filter(project::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(projectList).isNotNull();
        assertThat(projectList).hasSize(1);
        Project testProject = projectList.get(0);
        assertThat(testProject.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testProject.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProject.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testProject.getFinishDate()).isEqualTo(DEFAULT_FINISH_DATE);
        assertThat(testProject.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testProject.getStatusId()).isEqualTo(DEFAULT_STATUS_ID);
    }

    @Test
    void getAllProjects() {
        // Initialize the database
        projectRepository.save(project).block();

        // Get all the projectList
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
            .value(hasItem(project.getId().intValue()))
            .jsonPath("$.[*].clientId")
            .value(hasItem(DEFAULT_CLIENT_ID.intValue()))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME))
            .jsonPath("$.[*].startDate")
            .value(hasItem(DEFAULT_START_DATE.toString()))
            .jsonPath("$.[*].finishDate")
            .value(hasItem(DEFAULT_FINISH_DATE.toString()))
            .jsonPath("$.[*].link")
            .value(hasItem(DEFAULT_LINK))
            .jsonPath("$.[*].statusId")
            .value(hasItem(DEFAULT_STATUS_ID));
    }

    @Test
    void getProject() {
        // Initialize the database
        projectRepository.save(project).block();

        // Get the project
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, project.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(project.getId().intValue()))
            .jsonPath("$.clientId")
            .value(is(DEFAULT_CLIENT_ID.intValue()))
            .jsonPath("$.name")
            .value(is(DEFAULT_NAME))
            .jsonPath("$.startDate")
            .value(is(DEFAULT_START_DATE.toString()))
            .jsonPath("$.finishDate")
            .value(is(DEFAULT_FINISH_DATE.toString()))
            .jsonPath("$.link")
            .value(is(DEFAULT_LINK))
            .jsonPath("$.statusId")
            .value(is(DEFAULT_STATUS_ID));
    }

    @Test
    void getNonExistingProject() {
        // Get the project
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putNewProject() throws Exception {
        // Initialize the database
        projectRepository.save(project).block();

        int databaseSizeBeforeUpdate = projectRepository.findAll().collectList().block().size();

        // Update the project
        Project updatedProject = projectRepository.findById(project.getId()).block();
        updatedProject
            .clientId(UPDATED_CLIENT_ID)
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .finishDate(UPDATED_FINISH_DATE)
            .link(UPDATED_LINK)
            .statusId(UPDATED_STATUS_ID);
        ProjectDTO projectDTO = projectMapper.toDto(updatedProject);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, projectDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(projectDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testProject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProject.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProject.getFinishDate()).isEqualTo(UPDATED_FINISH_DATE);
        assertThat(testProject.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testProject.getStatusId()).isEqualTo(UPDATED_STATUS_ID);
    }

    @Test
    void putNonExistingProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().collectList().block().size();
        project.setId(count.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, projectDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(projectDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().collectList().block().size();
        project.setId(count.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(projectDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().collectList().block().size();
        project.setId(count.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(projectDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateProjectWithPatch() throws Exception {
        // Initialize the database
        projectRepository.save(project).block();

        int databaseSizeBeforeUpdate = projectRepository.findAll().collectList().block().size();

        // Update the project using partial update
        Project partialUpdatedProject = new Project();
        partialUpdatedProject.setId(project.getId());

        partialUpdatedProject.clientId(UPDATED_CLIENT_ID).finishDate(UPDATED_FINISH_DATE).link(UPDATED_LINK);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedProject.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedProject))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testProject.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProject.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testProject.getFinishDate()).isEqualTo(UPDATED_FINISH_DATE);
        assertThat(testProject.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testProject.getStatusId()).isEqualTo(DEFAULT_STATUS_ID);
    }

    @Test
    void fullUpdateProjectWithPatch() throws Exception {
        // Initialize the database
        projectRepository.save(project).block();

        int databaseSizeBeforeUpdate = projectRepository.findAll().collectList().block().size();

        // Update the project using partial update
        Project partialUpdatedProject = new Project();
        partialUpdatedProject.setId(project.getId());

        partialUpdatedProject
            .clientId(UPDATED_CLIENT_ID)
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .finishDate(UPDATED_FINISH_DATE)
            .link(UPDATED_LINK)
            .statusId(UPDATED_STATUS_ID);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedProject.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedProject))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testProject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProject.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProject.getFinishDate()).isEqualTo(UPDATED_FINISH_DATE);
        assertThat(testProject.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testProject.getStatusId()).isEqualTo(UPDATED_STATUS_ID);
    }

    @Test
    void patchNonExistingProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().collectList().block().size();
        project.setId(count.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, projectDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(projectDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().collectList().block().size();
        project.setId(count.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(projectDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().collectList().block().size();
        project.setId(count.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(projectDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteProject() {
        // Initialize the database
        projectRepository.save(project).block();

        int databaseSizeBeforeDelete = projectRepository.findAll().collectList().block().size();

        // Delete the project
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, project.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Project> projectList = projectRepository.findAll().collectList().block();
        assertThat(projectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
