package com.rgabay.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.rgabay.graphql.pojo.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.v1.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Slf4j
public class Query implements GraphQLQueryResolver, AutoCloseable {

    private static final String neo_url = "bolt://localhost:7687";
    private static final String neo_user = "neo4j";
    private static final String neo_password = "neo";

    private final Driver driver = GraphDatabase.driver(neo_url,
            AuthTokens.basic("neo4j", "neo"));

    public List<Employee> employees() {

        final String query = "MATCH (e:Employee) return e";
        List<Employee> response;

        try (Session session = driver.session()) {
            response = session.readTransaction(tx -> {
                StatementResult result = tx.run(query);

                return buildTalkList(result);
            });
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            e.printStackTrace();
            response = Collections.emptyList();
        }
        return response;
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }

    private List<Employee> buildTalkList(StatementResult result) {

        List<Employee> employeeList = new ArrayList<>();
        while (result.hasNext()) {
            Record row = result.next();
            for (String key : result.keys()) {
                employeeList.add(
                        new Employee(
                                row.get(key).asNode().get("title").toString().replace("\"", ""),
                                row.get(key).asNode().get("name").toString().replace("\"", "")
                        ));
            }
        }
        return employeeList;
    }
}
