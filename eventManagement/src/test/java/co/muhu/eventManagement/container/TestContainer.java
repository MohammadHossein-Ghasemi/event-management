package co.muhu.eventManagement.container;

import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class TestContainer extends AbstractTestContainer {

    @Test
    void canStartContainer(){
        assertThat(psqlContainer.isCreated()).isTrue();
        assertThat(psqlContainer.isRunning()).isTrue();
    }

}
