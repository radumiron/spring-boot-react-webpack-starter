import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.bitcharts.spring_boot.MarketsController;

/**
 * Created by mironr on 11/1/2016.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = MarketsController.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MarketsControllerTest {

  @Mock
  private MarketsController controller;


}
