/**
 * Fake step `withEnv`.
 * <p>
 * See https://www.jenkins.io/doc/pipeline/steps/workflow-basic-steps/#withenv-set-environment-variables for more details.
 */
def call(List<String> list, Closure body) {

    def temporaryEnvVars = list
            .collect { it.split('=', 2) }
            .collectEntries {[it[0], it[1]]}

    def original = [:] + env
    try {
        env.putAll(temporaryEnvVars)
        body()
    } finally {
        env.clear()
        env.putAll(original)
    }
}

/**
 * Environment variables passed from the caller.
 */
//def env;
