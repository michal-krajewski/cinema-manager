# Cinema manager

Simple application for managing movies played in cinema.
On startup populates database with 

## Documentation
You can find the openapi spec file in `openapi` directory. All currently used endpoints are documented there, 
including `/login` (which is provided by Spring Security configuration) 

## Build
To build application run `mvn clean install`

## Run
The application requires Postgres database to run. You need to also provide your omdb API key.

You need to provide the following env variables:
```
DB_CONNECTION_URL
DB_USER
DB_PASSWORD
OMDB_API_KEY
```

To create admin user, you need to create a new user via API (`/users` endpoint) and then update role in the database manually. 
Currently, no users are created on application startup.

### Local setting
1. Use `docker/docker-compose` file for starting preconfigured database
2. Provide your own Omdb API key in `application-local.yml`
3. Start the application with `local` profile

### Omdb API key
For details visit https://www.omdbapi.com/apikey.aspx 

## Tech decisions

### Tests
Most of the created tests are black-box web API tests (integration tests). 
That approach is most suitable for TDD and provides great coverage based on real use cases, though they are not as fast as tests without full spring context.

The black-box approach was especially useful when security config was introduced - to make sure it works correctly, 
only the underlying application client implementation had to be adjusted to keep the session cookie

Using request/response objects from the domain (instead of raw JSON) provides a much better API for creating tests, but with cost, that exact JSON structure is not validated. 

### Omdb API
None data from the API is currently persisted as it contains current imdb rating, that would have to be periodically updated.
That would increase complexity of that part of application, so I decided to leave it as it is - every time a new request is sent.
That behavior should be changed to at least cache the response

### Movie domain simplicity
The movie domain was simplified to limit the scope of this application. 
Ticket price representation should be reconsidered, as current implementation provides only an option to set constant price for the ticket, 
different formats (2D, 3D, IMAX) would be priced differently.

### User creation
Existing endpoint for user creation was added just for local testing. That endpoint is completely not suitable for production deployment 
and should be reimplemented to provide real registration functionality with email confirmation.

### Domain layer objects
Because of simplicity of current domain, there was no need to introduce an additional domain object layer, so currently persistence layer and
domain layer are using the same classes.
Right now security module shows the best separation, as only `User` class is used internally in two classes.

In case of increased complexity, these layers could be easily separated and hexagonal architecture could be applied, no changes in tests should be needed.

### Package public API
All services are treated as package/module public API, repositories are treated as internal objects and are not shared between main packages.

## Steps before production
- Define hikari configuration (pool size etc.)
- Improve existing security config
  - enable csrf
  - improve logout - enforce clearing local storage, change default cookie name (OWASP recommendation), add tests 
- Create correct UserAPI
- Cache or persist responses from OmdbAPI
- Circuit breaker for OmdbAPI should be considered
- Consider defining currency for pricing
- Setup CI
- Use TestContainers in tests instead of H2 database
- Prepare packaging (like docker image) based on deployment target
