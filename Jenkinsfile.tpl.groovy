def jobId = "p-${UUID.randomUUID().toString()}"
{{#each pods}}
def pod_template_{{@key}}_yaml = """{{podTemplate file}}"""
def pod_template_{{@key}}_checkout = {{#if checkout}}true{{else}}false{{/if}}
{{/each}}

{{#each stages}}
{{#if pod}}
podTemplate(label:"${jobId}-{{pod}}-{{@key}}", yaml: pod_template_{{pod}}_yaml) {
  node("${jobId}-{{pod}}-{{@key}}") {
    if(pod_template_{{pod}}_checkout) {
      checkout scm
    }
{{else}}
  node {
{{/if}}
  {{#each steps}}
    stage("{{name}}"){
    {{#if container}}
      container("{{container}}") {
    {{/if}}
      {{#if environment}}
        withEnv([ {{#each environment}} "{{{.}}}" {{#unless @last}},{{/unless}} {{/each}} ]) {
      {{/if}}
        {{#if directory}}
          dir("{{directory}}") {
        {{/if}}
          {{#if raw}}{{{raw}}}{{/if}}
          {{#each commands}}
            sh "{{{.}}}"
          {{/each}}
        {{#if directory}}
          }
        {{/if}}
      {{#if environment}}
        }
      {{/if}}
    {{#if container}}
      }
    {{/if}}
    }
    {{/each}}
  }
{{#if pod}}
}
{{/if}}
{{/each}}
