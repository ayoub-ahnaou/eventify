# Eventify - Event Management System

A Spring Boot application for managing events, users, and registrations with PostgreSQL database.

## 📋 Prerequisites

Before running this project, ensure you have the following installed:

- **Docker** (version 20.10 or higher)
- **Docker Compose** (version 2.0 or higher)
- **Java 21** (OpenJDK or Oracle JDK)
- **Maven 3.8+** (optional, if not using Docker)
- **Make** (optional, for using Makefile commands)

### Check Prerequisites

```bash
# Check Docker
docker --version
docker-compose --version

# Check Java
java -version

# Check Maven (optional)
mvn -version

# Check Make (optional)
make --version
```

## 🚀 Quick Start with Docker

### 1. Clone the Repository

```bash
git clone <your-repo-url>
cd eventify
```

### 2. Configure Environment Variables

Create a `.env` file in the project root:

```bash
# Database Configuration
DB_HOST=localhost
DB_PORT=5432
DB_NAME=eventify_db
DB_USERNAME=eventify_user
DB_PASSWORD=your_secure_password

# Application Configuration
SPRING_PROFILES_ACTIVE=dev
```

### 3. Run with Docker Compose

```bash
# Start all services (PostgreSQL + Application)
docker-compose up -d

# Check logs
docker-compose logs -f

# Stop all services
docker-compose down

# Stop and remove volumes (clean database)
docker-compose down -v
```

### 4. Access the Application

- **Application URL**: http://localhost:8081
- **Database**: localhost:5432
- **Health Check**: http://localhost:8081/actuator/health (if actuator is enabled)

## 🛠️ Using Makefile (Recommended)

We provide a Makefile for easy command execution:

```bash
# Build the project
make build

# Run the application
make run

# Run with Docker
make docker-up

# Stop Docker containers
make docker-down

# Clean and rebuild
make clean

# View logs
make logs

# Run tests
make test

# Full restart (clean + build + run)
make restart

# Check application status
make status

# Access database CLI
make db-shell
```

## 📦 Manual Setup (Without Docker)

### 1. Install PostgreSQL

```bash
# Ubuntu/Debian
sudo apt update
sudo apt install postgresql postgresql-contrib

# macOS
brew install postgresql@14

# Start PostgreSQL
sudo systemctl start postgresql  # Linux
brew services start postgresql   # macOS
```

### 2. Create Database

```bash
# Access PostgreSQL
sudo -u postgres psql

# Create database and user
CREATE DATABASE eventify_db;
CREATE USER eventify_user WITH PASSWORD 'your_secure_password';
GRANT ALL PRIVILEGES ON DATABASE eventify_db TO eventify_user;
\q
```

### 3. Configure Application

Edit `src/main/resources/application.properties` or set environment variables:

```bash
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=eventify_db
export DB_USERNAME=eventify_user
export DB_PASSWORD=your_secure_password
```

### 4. Build and Run

```bash
# Set Java 21 as active version
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

# Build the project
mvn clean install -DskipTests

# Run the application
mvn spring-boot:run

# Or run the JAR directly
java -jar target/eventify-0.0.1-SNAPSHOT.jar
```

## 🏗️ Project Structure

```
eventify/
├── src/
│   ├── main/
│   │   ├── java/com/security/eventify/
│   │   │   ├── controller/       # REST Controllers
│   │   │   ├── service/          # Business Logic
│   │   │   ├── repository/       # Data Access Layer
│   │   │   ├── model/entity/     # JPA Entities
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   ├── mapper/           # MapStruct Mappers
│   │   │   └── EventifyApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/changelog/     # Liquibase migrations
│   └── test/                     # Unit & Integration Tests
├── docker-compose.yml
├── Dockerfile
├── Makefile
├── pom.xml
└── README.md
```

## 🔧 Configuration

### Application Properties

| Property | Description | Default |
|----------|-------------|---------|
| `server.port` | Application port | 8081 |
| `spring.datasource.url` | Database connection URL | jdbc:postgresql://localhost:5432/eventify_db |
| `spring.jpa.hibernate.ddl-auto` | Hibernate DDL mode | update |
| `spring.liquibase.enabled` | Enable Liquibase migrations | true |

### Environment Variables

All configuration can be overridden using environment variables:

```bash
DB_HOST=database_host
DB_PORT=5432
DB_NAME=database_name
DB_USERNAME=db_user
DB_PASSWORD=db_password
```

## 🧪 Testing

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=EventServiceTest

# Run with coverage
mvn clean test jacoco:report

# Skip tests during build
mvn clean install -DskipTests
```

## 📊 Database Management

### Access PostgreSQL CLI

```bash
# Using Docker
docker exec -it eventify_postgres psql -U eventify_user -d eventify_db

# Local installation
psql -U eventify_user -d eventify_db
```

### Liquibase Commands

```bash
# Update database to latest version
mvn liquibase:update

# Rollback last changeset
mvn liquibase:rollback -Dliquibase.rollbackCount=1

# Generate SQL for changes
mvn liquibase:updateSQL

# Clear checksums
mvn liquibase:clearCheckSums
```

## 🐛 Troubleshooting

### Port Already in Use

```bash
# Check what's using port 8081
sudo lsof -i :8081

# Kill the process
kill -9 <PID>

# Or change port in application.properties
server.port=8082
```

### Database Connection Issues

```bash
# Check PostgreSQL is running
docker ps | grep postgres

# Check database logs
docker logs eventify_postgres

# Restart database
docker-compose restart postgres
```

### Java Version Issues

```bash
# Check Java version
java -version

# Set correct JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64

# Update alternatives (Linux)
sudo update-alternatives --config java
```

### Maven Compilation Issues

```bash
# Clean Maven cache
mvn clean

# Force update dependencies
mvn clean install -U

# Remove local repository cache
rm -rf ~/.m2/repository
```

## 📝 API Documentation

### Event Endpoints

- `POST /api/events` - Create new event
- `GET /api/events/{id}` - Get event by ID
- `GET /api/events` - Get all events
- `PUT /api/events/{id}` - Update event
- `DELETE /api/events/{id}` - Delete event

### Registration Endpoints

- `POST /api/registrations` - Register for event
- `GET /api/registrations/{id}` - Get registration details
- `GET /api/registrations` - Get all registrations
- `DELETE /api/registrations/{id}` - Cancel registration

*(Add Swagger/OpenAPI documentation link when available)*

## 🔐 Security Notes

1. **Never commit `.env` file** - Add it to `.gitignore`
2. **Change default passwords** in production
3. **Use strong passwords** for database
4. **Enable HTTPS** in production
5. **Keep dependencies updated** regularly

## 📚 Technology Stack

- **Framework**: Spring Boot 3.5.7
- **Language**: Java 21
- **Database**: PostgreSQL 42.7.8
- **ORM**: Hibernate 7.1.8
- **Migration**: Liquibase 5.0.1
- **Mapping**: MapStruct 1.5.5
- **Build Tool**: Maven 3.8+
- **Containerization**: Docker

## 🤝 Contributing

1. Create a feature branch
2. Make your changes
3. Run tests: `make test`
4. Commit with meaningful messages
5. Push and create a Pull Request


---

**Version**: 0.0.1-SNAPSHOT  
**Last Updated**: 2025-11-21
