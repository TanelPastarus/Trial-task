# Delivery API

## API

The server is running on port 8080. The following endpoints are available:

* GET `/` - default page
* GET `/fee/{city}/{vehicle}` - get the final delivery fee for a city and vehicle
* PUT `/basefee/update` - update the delivery base fee for a city
* PUT `/extrafee/update` - update the delivery extra fee (based on weather) for a city

## Database
The database can be accessed in `localhost:8080/h2-console`. The username is `sa` and password is `123`.

There are 3 tables in the database:
* `CITY_BASE_FEE` - contains the base fees for bike, car and scooter for each city
* `EXTRA_FEES` - contains the extra fees for bike, car and scooter according to different weather conditions described in the task.
* `WEATHER` - contains the weather data for each city.


### CITY_BASE_FEE

Contains the base fees for vehicles per city and can be updated using the PUT method on `/basefee/update` endpoint.

When updating the base fees, all the values have to be provided and the city has to be
in the format `TARTU`, `TALLINN` or `PÄRNU` (uppercase). Values have to be 0 or positive numbers.

### EXTRA_FEES

The extra fees are taken from the vehicle extra fees table. If the extra fee value is -1, that means the usage of the vehicle is forbidden.
The values are assigned to the weather conditions as described in the task (air temp. below -10, air temp. between -10 and 0 etc.).

The values can be updated using the PUT method on `/extrafee/update` endpoint.
When updating the extra fees, all the values have to be provided and the city has to be
in the format `TARTU`, `TALLINN` or `PÄRNU` (uppercase).
The accepted values are -1, 0 or a positive number.

### WEATHER
The weather table contains the weather data for each city. It contains air temperature, city name (as an enum), timestamp, wind speed, wmocode and weather phenomenon. The phenomenons are either `RAINY`, `SNOWY` or `GLAZE`.
* `RAINY` - Weather phenomenon is related to rain
* `SNOWY` - Weather phenomenon is related to snow or sleet
* `GLAZE` - Weather phenomenon is glaze, hail, thunder or thunderstorm (just in case added thunderstorm aswell)

### Else

The cronjob is running every 15 minutes to update the weather database with new weather data. It can be changed from `application.resources` file.

The data.sql file includes the initial data for delivery base fees and vehicle extra fees (that were provided in the task description).

Tests are available in the `src/test/java` folder.