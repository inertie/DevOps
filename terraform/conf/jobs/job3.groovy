#!groovy

def ScriptJob3_pipeline = new File('/var/jenkins_config/jobs/job3_pipeline.groovy').getText("UTF-8")

pipelineJob('iac/Job3') {
    definition {
        cps {
            script(ScriptJob3_pipeline)
            sandbox()
        }
    }
}