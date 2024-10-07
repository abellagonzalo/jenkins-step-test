/**
 * Fake step `isUnix`.
 * <p>
 * See https://www.jenkins.io/doc/pipeline/steps/workflow-basic-steps/#isunix-checks-if-running-on-a-unix-like-node for more details.
 */
def call() {
    return File.separator == "/"
}