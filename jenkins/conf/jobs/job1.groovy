#!groovy
println('------------------------------------------------------------------Import Job CI/Job1')
def pipelineScript = new File('/var/jenkins_config/jobs/job1-pipeline.groovy').getText("UTF-8")

pipelineJob('CI/Job1') {
    description("Job pipeline 1")
    parameters {
        stringParam {
            name('par1')
            defaultValue('par1')
            description("p'tite description")
            trim(false)
        }
        stringParam {
            name('branch')
            defaultValue('devlop')
            description("la branche de dev")
            trim(false)
        }
        booleanParam{
            name('test')
            defaultValue(true)
            description("sauter les tests")
        }
    }
    definition {
        cps {
            script(pipelineScript)
            sandbox()
        }
    }
}