package abellagonzalo


import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertThrows
import static org.mockito.Mockito.*

class StepExecutorTest {

    @Test
    void mocksHavePrecedenceOverFakeSteps() {
        def mocks = [
                echo: mock(FakeStep.class)
        ]

        StepExecutor.testStep(mocks) {
            echo 'a message'
        }

        verify(mocks.echo, times(1)).call(any())
    }

    @Test
    void mocksHavePrecedenceOverVarsSteps() {
        def mocks = [
                checkStatus: mock(FakeStep.class)
        ]

        StepExecutor.testStep(mocks) {
            checkStatus 'a parameter'
        }

        verify(mocks.checkStatus, times(1)).call(any())
    }

    @Test
    void testStepThrowsFailsIfNoException() {
        assertThrows(Exception.class) {
            StepExecutor.testStepThrows() { }
        }
    }
}
