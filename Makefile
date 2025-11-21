.PHONY: help build run test clean docker-up docker-down docker-logs docker-restart db-shell db-backup db-restore restart status install update lint package run-jar dev prod-build logs-app logs-db ps env-check

# Default target
.DEFAULT_GOAL := help

# Colors for output
GREEN  := \033[0;32m
YELLOW := \033[0;33m
RED    := \033[0;31m
NC     := \033[0m # No Color

# Load .env variables if it exists
ifneq (,$(wildcard .env))
	include .env
	export
endif

ifeq (,$(wildcard .env))
$(warning .env file missing! Please create one based on .env.example)
endif

## help: Show this help message
help:
	@echo "$(GREEN)Eventify - Available Commands$(NC)"
	@echo ""
	@sed -n 's/^##//p' ${MAKEFILE_LIST} | column -t -s ':' | sed -e 's/^/ /'
	@echo ""

## build: Build the application with Maven
build:
	@echo "$(YELLOW)Building application...$(NC)"
	mvn clean install -DskipTests
	@echo "$(GREEN)Build completed!$(NC)"

## build-with-tests: Build the application and run tests
build-with-tests:
	@echo "$(YELLOW)Building application with tests...$(NC)"
	mvn clean install
	@echo "$(GREEN)Build with tests completed!$(NC)"

## run: Run the Spring Boot application
run:
	@echo "$(YELLOW)Starting application...$(NC)"
	mvn spring-boot:run

## test: Run all tests
test:
	@echo "$(YELLOW)Running tests...$(NC)"
	mvn test
	@echo "$(GREEN)Tests completed!$(NC)"

## clean: Clean build artifacts
clean:
	@echo "$(YELLOW)Cleaning project...$(NC)"
	mvn clean
	rm -rf target/
	@echo "$(GREEN)Clean completed!$(NC)"

## docker-up: Start Docker containers
docker-up:
	@echo "$(YELLOW)Starting Docker containers...$(NC)"
	docker compose up -d
	@echo "$(GREEN)Docker containers started!$(NC)"
	@echo "Application: http://localhost:8081"
	@echo "Database: ${DB_HOST}:${DB_PORT}"

## docker-down: Stop Docker containers
docker-down:
	@echo "$(YELLOW)Stopping Docker containers...$(NC)"
	docker compose down
	@echo "$(GREEN)Docker containers stopped!$(NC)"

## docker-down-v: Stop Docker containers and remove volumes
docker-down-v:
	@echo "$(RED)Stopping Docker containers and removing volumes...$(NC)"
	docker compose down -v
	@echo "$(GREEN)Docker containers and volumes removed!$(NC)"

## docker-logs: View Docker container logs
docker-logs:
	docker compose logs -f

## docker-restart: Restart Docker containers
docker-restart: docker-down docker-up
	@echo "$(GREEN)Docker containers restarted!$(NC)"

## docker-build: Build Docker images
docker-build:
	@echo "$(YELLOW)Building Docker images...$(NC)"
	docker compose build
	@echo "$(GREEN)Docker images built!$(NC)"

## db-shell: Access PostgreSQL database shell
db-shell:
	@echo "$(YELLOW)Connecting to database...$(NC)"
	docker exec -it ${DB_HOST} psql -U ${DB_USER} -d ${DB_NAME}

## db-backup: Backup database
db-backup:
	@echo "$(YELLOW)Backing up database...$(NC)"
	docker exec ${DB_HOST} pg_dump -U ${DB_USERNAME} ${DB_NAME} > backup_$(shell date +%Y%m%d_%H%M%S).sql
	@echo "$(GREEN)Database backup completed!$(NC)"

## db-restore: Restore database from backup (usage: make db-restore FILE=backup.sql)
db-restore:
	@echo "$(YELLOW)Restoring database...$(NC)"
	docker exec -i ${DB_HOST} psql -U ${DB_USERNAME} -d ${DB_NAME} < $(FILE)
	@echo "$(GREEN)Database restored!$(NC)"

## restart: Clean, build, and run the application
restart: clean build run

## status: Check application status
status:
	@echo "$(YELLOW)Checking application status...$(NC)"
	@echo ""
	@echo "Docker Containers:"
	@docker ps --filter name=eventify
	@echo ""
	@echo "Application Health:"
	@curl -s http://localhost:8081/actuator/health 2>/dev/null || echo "Application not running"
	@echo ""

## install: Install dependencies and setup project
install:
	@echo "$(YELLOW)Installing dependencies...$(NC)"
	mvn dependency:resolve
	@echo "$(GREEN)Dependencies installed!$(NC)"

## update: Update Maven dependencies
update:
	@echo "$(YELLOW)Updating dependencies...$(NC)"
	mvn versions:display-dependency-updates
	@echo "$(GREEN)Dependency check completed!$(NC)"

## lint: Check code style
lint:
	@echo "$(YELLOW)Checking code style...$(NC)"
	mvn checkstyle:check
	@echo "$(GREEN)Code style check completed!$(NC)"

## package: Package application as JAR
package:
	@echo "$(YELLOW)Packaging application...$(NC)"
	mvn package -DskipTests
	@echo "$(GREEN)Application packaged!$(NC)"
	@echo "JAR location: target/eventify-0.0.1-SNAPSHOT.jar"

## run-jar: Run the packaged JAR file
run-jar:
	@echo "$(YELLOW)Running JAR file...$(NC)"
	java -jar target/eventify-0.0.1-SNAPSHOT.jar

## dev: Start development environment
dev: docker-up
	@echo "$(GREEN)Development environment ready!$(NC)"
	@sleep 3
	$(MAKE) run

## prod-build: Build for production
prod-build:
	@echo "$(YELLOW)Building for production...$(NC)"
	mvn clean package -Pprod -DskipTests
	@echo "$(GREEN)Production build completed!$(NC)"

## logs-app: View application logs
logs-app:
	docker compose logs -f app

## logs-db: View database logs
logs-db:
	docker compose logs -f db

## ps: Show running containers
ps:
	docker compose ps

## env-check: Check environment setup
env-check:
	@echo "$(YELLOW)Checking environment...$(NC)"
	@echo ""
	@echo "Java Version:"
	@java -version 2>&1 | head -n 1
	@echo ""
	@echo "Maven Version:"
	@mvn -version | head -n 1
	@echo ""
	@echo "Docker Version:"
	@docker --version
	@echo ""
	@echo "Docker Compose Version:"
	@docker compose version
	@echo ""
	@if [ -f .env ]; then echo "$(GREEN).env file found$(NC)"; else echo "$(RED).env file missing!$(NC)"; fi
	@echo ""
