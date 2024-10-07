package abellagonzalo

/**
 * Interface to mock steps with Mockito.
 * <p>
 * Ex:
 * <pre>
 * mock(FakeStep.class)
 * </pre>
 */
interface FakeStep {
    def call(args)
}