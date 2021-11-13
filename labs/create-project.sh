mvn io.quarkus.platform:quarkus-maven-plugin:2.4.2.Final:create \
    -DprojectGroupId=at.htl \
    -DprojectArtifactId=security-openid-connect-quickstart \
    -Dextensions="resteasy,oidc,resteasy-jackson" \
    -DnoExamples
