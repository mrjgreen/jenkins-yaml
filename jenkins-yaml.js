const yaml = require("js-yaml");
const fs = require("fs");
const Handlebars = require("handlebars");

Handlebars.registerHelper("podTemplate", file => fs.readFileSync(file, "utf8"));

const configFile = "./Jenkinsfile.yml";
const templateFile = "./Jenkinsfile.tpl.groovy";

const config = yaml.safeLoad(fs.readFileSync(configFile, "utf8"));
const template = Handlebars.compile(fs.readFileSync(templateFile, "utf8"));

console.log(template(config));
