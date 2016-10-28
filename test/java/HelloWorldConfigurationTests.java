/**
 * Created by mironr on 10/28/2016.
 */
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bitcharts.Greeting;
import com.github.bitcharts.HelloWorldConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * Basic integration tests for service demo application.
 *
 * @author Dave Syer
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class HelloWorldConfigurationTests {

    @LocalServerPort
    private int port;

    @Value("${local.management.port}")
    private int mgt;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldReturn200WhenSendingRequestToController() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/hello-world", Map.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void checkCounterValue() throws Exception {
        ResponseEntity<String> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/hello-world", String.class);
        Greeting greeting = new ObjectMapper().readValue(entity.getBody().toString(), Greeting.class);
        System.out.println("id=" + greeting.getId() + ", content= " + greeting.getContent());
    }

    @Test
    public void shouldReturn200WhenSendingRequestToManagementEndpoint() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.mgt + "/info", Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
