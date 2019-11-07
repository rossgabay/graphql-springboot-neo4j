# graphql-springboot-neo4j
Basic GraphQL-Springboot-Neo4j skeleton app 

## pre-requisites:
- java/maven
- neo4j instance running somewhere. code assumes localhost:7687, neo4j/neo credentials, obv this is modifiable
- to get the actual data coming back - create some ```Employee``` nodes in the graph :

``` create (e1:Employee) set e1.name = "Joe" set e1.title = "UFO Operator"```

```create (e2:Employee) set e2.name = "Bob" set e2.title = "Junior UFO Operator"```

## to build/run locally:
- ```mvn clean install```
- ```mvn spring-boot:run```
- navigate to ```http://localhost:8080/graphiql```
- send a request payload in from the Graphiql UI :

``` query { employees{ title name }} ```
