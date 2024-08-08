#!/bin/sh

# Initialize PostgreSQL
/usr/bin/pg_ctl -D /var/lib/postgresql/data -l /var/log/postgresql.log start

# Wait for PostgreSQL to be ready
until pg_isready -h localhost -p 5432; do
    echo "Waiting for PostgreSQL to be ready..."
    sleep 2
done

# Run the Spring Boot application
java -jar /app/Internship-2024-java-green-0.0.1-SNAPSHOT.jar
