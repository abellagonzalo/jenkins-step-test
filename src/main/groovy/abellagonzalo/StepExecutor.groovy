package abellagonzalo

/**
 * Test steps locally as part of unit or integration tests.
 * Fake steps are located under `fakes` directory.
 * <p>
 * Mocks and scripts are loaded following the order below:
 * <ul>
 *     <li> mock </li>
 *     <li> fakes script</li>
 *     <li> vars script</li>
 * </ul>
 */
class StepExecutor {

    private HashMap<String, String> env = System.getenv()
    private def mocks = [:]
    private GroovyClassLoader loader = new GroovyClassLoader()

    static Throwable testStepThrows(Map<String, FakeStep> mocks = [:], Closure closure) {
        try {
            testStep(mocks, closure)
        } catch (Throwable ex) {
            return ex
        }
        throw new Exception('Expected exception but not found.')
    }

    static def testStep(Map<String, FakeStep> mocks = [:], Closure closure) {
        return new StepExecutor(mocks)(closure)
    }

    private StepExecutor(Map<String, FakeStep> mocks = [:]) {
        this.mocks = mocks
    }

    def call(Closure definition) {
        definition.delegate = this
        definition.resolveStrategy = Closure.DELEGATE_FIRST
        definition()
    }

    def methodMissing(String name, def args) {
        if (canInvokeMock(name))
            return invokeMock(name, args)

        def fake = new File("fakes/${name}.groovy")
        if (canInvokeScript(fake))
            return invokeScript(fake, args)

        def vars = new File("vars/${name}.groovy")
        if (canInvokeScript(vars))
            return invokeScript(vars, args)

        throw new Exception("Failed to find a mock or a script in fakes/vars directory: $name $args")
    }

    private boolean canInvokeMock(String name) {
        return mocks.containsKey(name)
    }

    private def invokeMock(String name, def args) {
        mocks[name].metaClass.env = env
        return mocks[name].call(args)
    }

    private boolean canInvokeScript(File path) {
        return path.exists()
    }

    private def invokeScript(File path, def args) {
        Class scriptClass = loader.parseClass(path)
        def scriptInstance = scriptClass.getDeclaredConstructor().newInstance()

        scriptInstance.metaClass.env = env
        scriptInstance.metaClass.methodMissing = { String n, def a -> methodMissing(n, a) }

        return scriptInstance.invokeMethod('call', args)
    }
}