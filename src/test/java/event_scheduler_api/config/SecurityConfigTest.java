package event_scheduler_api.config;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
@Import(SecurityConfig.class)
class SecurityConfigTest {

}