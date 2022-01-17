#!groovy
def ScriptJob2_pipeline = new File('/var/jenkins_config/jobs/job2_pipeline.groovy').getText("UTF-8")

pipelineJob('iac/Job2') {
    definition {
        cps {
            script(ScriptJob2_pipeline)
            sandbox()
        }
    }
}