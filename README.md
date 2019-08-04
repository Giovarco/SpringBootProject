# Example of requests
## Creating a new film
- **Request method:** POST
- **Content-Type:** application/json
- **URL:** http://localhost:8080/film
- **Example of body:**
`{
	"title" : "A",
	"locations" : "B"
}`

## Retrieving all the films
- **Request method:** GET
- **Content-Type:** none
- **URL:** http://localhost:8080/film
- **Example of body:** none

## Delete an existing film
- **Request method:** DELETE
- **Content-Type:** none
- **URL:** http://localhost:8080/film/{filmId}
- **Example of body:** none

## Edit an existing film
- **Request method:** PUT
- **Content-Type:** application/json
- **URL:** http://localhost:8080/film/{filmId)
- **Example of body:**
`{
	"title" : "A",
	"locations" : "B"
}`

# To do

- ~~Save new book~~
- ~~Get all books~~
- ~~Implement validators~~
- ~~Delete existing book~~
- ~~Import films from CSV file to the database~~
- ~~Put existing book~~
- ~~Update README with instructions~~