/**
 * Fake step `fileExists`.
 * <p>
 * See https://www.jenkins.io/doc/pipeline/steps/workflow-basic-steps/#fileexists-verify-if-file-exists-in-workspace for more details.
 */
def call(String path) {
    if ('filesystem' !in testContext)
        testContext.put('filesystem', [:])

    return testContext.filesystem.containsKey(path)
}