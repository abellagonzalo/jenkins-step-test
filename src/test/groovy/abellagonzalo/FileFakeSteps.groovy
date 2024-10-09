package abellagonzalo


import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertTrue

class FileFakeSteps {

    @Test
    void fileExists() {
        StepExecutor.testStep() {
            assertFalse(fileExists 'a-file.txt')

            writeFile file: 'a-file.txt', text: 'hello world!'

            assertTrue(fileExists 'a-file.txt')
        }
    }
}
