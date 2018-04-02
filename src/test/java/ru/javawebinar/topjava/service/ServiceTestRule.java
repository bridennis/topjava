package ru.javawebinar.topjava.service;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ServiceTestRule implements TestRule {

    List map;

    public ServiceTestRule(List map) {
        this.map = map;
    }

    @Override
    public Statement apply(Statement base, Description description) {

        return new Statement() {

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.A");;

            @Override
            public void evaluate() throws Throwable {

                LocalDateTime startTime = LocalDateTime.now();

                base.evaluate();

                LocalDateTime stopTime = LocalDateTime.now();
                System.out.println(
                        String.format("\nTest [%s]\n\tstart at [%s] stop at [%s]\n\tExecution time is [%d] (ms)\n",
                                description.getMethodName(),
                                startTime.format(dateTimeFormatter),
                                stopTime.format(dateTimeFormatter),
                                Duration.between(startTime, stopTime).toMillis()
                        )
                );

                map.add(
                        String.format("Test [%s] - %d (ms)",
                                description.getMethodName(),
                                Duration.between(startTime, stopTime).toMillis()
                        ));
            }
        };
    }
}
