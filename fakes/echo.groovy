/**
 * Fake step `echo`.
 * <p>
 * See https://www.jenkins.io/doc/pipeline/steps/workflow-basic-steps/#echo-print-message for more details.
 */
def call(message) {
    println message.toString()
}