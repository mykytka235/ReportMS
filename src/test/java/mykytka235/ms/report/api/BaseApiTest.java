package mykytka235.ms.report.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import mykytka235.ms.report.db.repository.LastExportDataRepository;
import mykytka235.ms.report.integration.uri.MyAccountUriBuilder;
import mykytka235.ms.report.integration.uri.NotificationUriBuilder;
import mykytka235.ms.report.util.TestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;
import java.time.Clock;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
public abstract class BaseApiTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected RestTemplate myAccountRestTemplate;
    @Autowired
    protected RestTemplate notificationRestTemplate;

    @Autowired
    protected MyAccountUriBuilder myAccountUriBuilder;
    @Autowired
    protected NotificationUriBuilder notificationUriBuilder;

    @MockBean
    protected LastExportDataRepository lastExportDataRepository;
    @MockBean
    protected Clock utcClock;

    protected MockRestServiceServer myAccountServiceServer;
    protected MockRestServiceServer notificationServiceServer;

    private MockMvc mockMvc;

    protected void setUp() {
        when(utcClock.millis()).thenReturn(TestUtil.CURRENT_TIME_MILLIS_DEFAULT);
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.myAccountServiceServer = MockRestServiceServer.bindTo(myAccountRestTemplate).build();
        this.notificationServiceServer = MockRestServiceServer.bindTo(notificationRestTemplate).build();
    }

    protected ResultActions performPostInteraction(URI uri, Object dataRequest) throws Exception {
        return this.mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dataRequest))
                .accept(MediaType.APPLICATION_JSON_VALUE));
    }

    protected ResultActions performPostInteractionWithMultipartData(URI uri, Object dataRequest) throws Exception {
        return this.mockMvc.perform(post(uri)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .content(objectMapper.writeValueAsString(dataRequest))
                .accept(MediaType.APPLICATION_JSON_VALUE));
    }

    protected ResultActions performGetInteraction(URI uri) throws Exception {
        return this.mockMvc.perform(get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE));
    }

    protected ResultActions performPatchInteraction(URI uri) throws Exception {
        return this.mockMvc.perform(patch(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE));
    }

    protected ResultActions performGetInteraction(URI uri, HttpHeaders httpHeaders) throws Exception {
        return this.mockMvc.perform(get(uri)
                .headers(httpHeaders)
                .accept(MediaType.APPLICATION_JSON_VALUE));
    }

    void mockResponseWithSuccess(MockRestServiceServer mockRestServiceServer, HttpMethod httpMethod, String uri, Object dataResponse) throws Exception {
        String stringifiedDataResponse = objectMapper.writeValueAsString(dataResponse);
        mockRestServiceServer.expect(ExpectedCount.manyTimes(), requestTo(uri))
                .andExpect(method(httpMethod))
                .andRespond(withSuccess(stringifiedDataResponse, MediaType.APPLICATION_JSON));
    }
}
