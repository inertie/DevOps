#!groovy

def Script_job1Pipeline = new File('/var/jenkins_config/jobs/job1_pipeline.groovy').getText("UTF-8")

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
            description("")
        }
        choix {
            name('type')
            choices(['SNAPSHOT', 'RELEASE'])
            description('quel type ?')
        }
        stringParam{
            name('version')
            defaultValue('1')
            description("la version du projet")
            trim(false)
        }
    }
    definition {
        cps {
            script(Script_job1Pipeline)
            sandbox()
        }
    }
}