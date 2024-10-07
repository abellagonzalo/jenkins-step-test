/**
 * Fake step `error`.
 * <p>
 * See https://www.jenkins.io/doc/pipeline/steps/workflow-basic-steps/#error-error-signal for more details.
 */
def call(message) {
    throw new Exception(message.toString())
}