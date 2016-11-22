var neo4j = require('neo4j-driver').v1;
var driver = neo4j.driver("bolt://localhost", neo4j.auth.basic("neo4j", "AllIsGood"));
var session = driver.session();
session.run( "CREATE (a:Person {name:'Arthur', title:'King'})" )
  .then( function() {
    return session.run( "MATCH (a:Person) WHERE a.name = 'Arthur' RETURN a.name AS name, a.title AS title" )
  })
  .then( function( result ) {
    console.log( result.records[0].get("title") + " " + result.records[0].get("name") );
    session.close();
    driver.close();
  });
