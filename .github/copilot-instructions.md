# Household Management Application

家計管理 (Household Management) is a Spring Boot web application for managing household expenses and budgets. The application provides expense tracking, budget management, and data visualization through charts, all built with Spring Boot, Thymeleaf, and H2 database.

Always reference these instructions first and fallback to search or bash commands only when you encounter unexpected information that does not match the info here.

## Working Effectively

### Bootstrap and Build Commands
- Make Maven wrapper executable: `chmod +x mvnw`
- Clean compile: `./mvnw clean compile` -- **NEVER CANCEL**: First run takes 2.5 minutes (with dependency downloads), subsequent runs take ~3 seconds. Set timeout to 300+ seconds for safety.
- Run tests: `./mvnw test` -- **NEVER CANCEL**: Takes 53 seconds. Set timeout to 120+ seconds.
- Package JAR: `./mvnw clean package` -- **NEVER CANCEL**: Takes 39 seconds. Set timeout to 90+ seconds.
- Skip tests during package: `./mvnw clean package -DskipTests` -- **NEVER CANCEL**: Takes 3 seconds after initial build. Set timeout to 90+ seconds.

### Running the Application
- **Development mode**: `./mvnw spring-boot:run` -- Starts application on port 8080 in ~3 seconds
- **JAR execution**: `java -jar ./target/*.jar` -- Run packaged application
- **Main application URL**: http://localhost:8080
- **H2 database console**: http://localhost:8080/h2-console/
  - JDBC URL: `jdbc:h2:./h2db/h2`
  - Username: `super`
  - Password: (empty)

### Docker Support
- Docker build: `docker build -t household-management .` -- **FAILS due to SSL certificate issues in restricted environments**
- Docker Compose: `docker compose up -d --build` -- **FAILS due to SSL certificate issues in restricted environments**
- **IMPORTANT**: Docker builds fail with certificate validation errors. Use local Maven builds instead.

## Validation

### Manual Testing Scenarios
After making changes, always validate with these scenarios:

1. **Application Startup Validation**:
   - Run `./mvnw spring-boot:run`
   - Verify application starts without errors in ~3 seconds
   - Access http://localhost:8080 and confirm homepage loads (Japanese UI with Bootstrap styling)
   - Verify H2 console is accessible at http://localhost:8080/h2-console

2. **Core Functionality Testing**:
   - Navigate to expenses section (支出一覧) at http://localhost:8080/expenses
   - Navigate to budget section (予算一覧) at http://localhost:8080/budget
   - Test chart data API endpoints: http://localhost:8080/api/chart/expenses and http://localhost:8080/api/chart/budgets
   - Verify charts render properly (Chart.js integration)
   - Test adding a new expense entry via the web interface
   - Test viewing/editing budget categories via the web interface

3. **Database Validation**:
   - Access H2 console with credentials: username=`super`, password=(empty)
   - Verify `household_expenses` and `household_budgets` tables exist
   - Check sample data loads from `data.sql`

### Build Validation Steps
- Always run full test suite: `./mvnw test` before making changes
- **NEVER CANCEL** any build commands - they may take several minutes
- Always build after changes: `./mvnw clean compile`
- Package and test JAR execution after significant changes

### No Lint/Format Commands Available
- This project does not have configured lint or format plugins (no checkstyle, spotbugs, spotless, etc.)
- Code style validation must be done manually
- CI pipeline only runs basic compilation (see `.github/workflows/build.yml`)

## Common Tasks

### Technology Stack
- **Framework**: Spring Boot 3.5.4
- **Java Version**: Java 17
- **Build Tool**: Maven with wrapper (`mvnw`)
- **Database**: H2 (file-based: `./h2db/h2`)
- **Template Engine**: Thymeleaf
- **Testing**: Spock Framework (Groovy tests)
- **UI**: Bootstrap 5.3.2, Chart.js 4.4.0

### Project Structure
```
src/main/java/com/example/
├── Main.java                           # Application entry point
├── controller/                         # REST controllers
│   ├── ChartDataController.java       # Chart data API
│   ├── HouseholdBudgetController.java # Budget management
│   └── HouseholdExpenseController.java # Expense management  
├── entity/                            # JPA entities
│   ├── HouseholdBudget.java          # Budget entity
│   └── HouseholdExpense.java         # Expense entity
├── repository/                        # Data access layer
├── service/                          # Business logic
└── dto/                              # Data transfer objects

src/main/resources/
├── application.properties             # App configuration
├── data.sql                          # Sample data
├── schema.sql                        # Database schema
└── templates/                        # Thymeleaf templates
    ├── budget/                       # Budget management UI
    ├── expenses/                     # Expense management UI
    └── fragments/                    # Reusable UI components

src/test/groovy/                      # Spock tests
```

### Database Configuration
- **Database Type**: H2 file database
- **File Location**: `./h2db/h2` (created automatically)
- **Mode**: MySQL compatibility mode
- **Schema**: Auto-created with `create-drop` strategy
- **Sample Data**: Loaded from `src/main/resources/data.sql`

### Key Dependencies
- `spring-boot-starter-web` - Web framework
- `spring-boot-starter-data-jpa` - Data persistence
- `spring-boot-starter-thymeleaf` - Template engine
- `h2` - Embedded database
- `lombok` - Code generation
- `spock-core`, `spock-spring` - Testing framework

### Troubleshooting
- **Port 8080 in use**: Change port in `application.properties` with `server.port=8081`
- **H2 database issues**: Delete `h2db` directory and restart to recreate database
- **Build failures**: Ensure Java 17 is installed and JAVA_HOME is set correctly
- **Test failures**: Individual Spock tests are in Groovy - use `./mvnw test -Dtest=ClassName` for specific tests
- **Docker build fails**: Expected due to SSL certificate issues - use local Maven builds instead

### Performance Expectations
- **Initial build**: 2.5 minutes (includes dependency downloads)
- **Subsequent builds**: 3-5 seconds (dependencies cached)
- **Test execution**: 53 seconds
- **Application startup**: 3-6 seconds (JAR takes slightly longer)
- **JAR packaging**: 3-39 seconds (depends on if dependencies need downloading)

### CI/CD Pipeline
- **GitHub Actions**: `.github/workflows/build.yml`
- **Build command**: `./mvnw clean compile`
- **No deployment automation** - manual deployment required
- **No automated testing** in CI - only compilation check

## Critical Reminders
- **NEVER CANCEL** Maven commands - builds may take 3+ minutes initially
- Always set timeouts of 300+ seconds for initial builds, 120+ seconds for subsequent builds
- Test the application manually after every significant change
- Docker builds will fail - use local Maven builds exclusively
- Application uses Japanese language interface - this is expected
- H2 console is enabled by default - disable in production by setting `spring.h2.console.enabled=false`