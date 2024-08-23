import json
import random
from datetime import datetime, timedelta

# City list
cities_map = {
    "JFK": ["New York", "USA"],
    "LAX": ["Los Angeles", "USA"],
    "LHR": ["London", "UK"],
    "NRT": ["Tokyo", "Japan"],
    "SYD": ["Sydney", "Australia"]
}

# Flight data generation
flight_data = []
flight_id = 1

cities = list(cities_map.keys())
for i, from_city in enumerate(cities):
    for to_city in cities[i+1:]:
        for _ in range(5):
            departure_time = datetime.strptime(f"{random.randint(0, 12):02d}:{random.randint(0, 59):02d}", "%H:%M")
            arrival_time = departure_time + timedelta(hours=random.randint(1, 12))
            stop_choices = cities.copy() 
            stop_choices.remove(from_city)
            stop_choices.remove(to_city)
            flight_data.append({
                "id": flight_id,
                "from": from_city,
                "to": to_city,
                "stops": [random.choice(stop_choices) for _ in range(random.randint(0, 2))],
                "price": random.randint(200, 1000),
                "departure_time": departure_time.strftime("%H:%M"),
                "arrival_time": arrival_time.strftime("%H:%M")
            })
            flight_id += 1

            # Add reverse direction
            departure_time = datetime.strptime(f"{random.randint(0, 12):02d}:{random.randint(0, 59):02d}", "%H:%M")
            arrival_time = departure_time + timedelta(hours=random.randint(1, 12))
            flight_data.append({
                "id": flight_id,
                "from": to_city,
                "to": from_city,
                "stops": [random.choice(stop_choices) for _ in range(random.randint(0, 2))],
                "price": random.randint(200, 1000),
                "departure_time": departure_time.strftime("%H:%M"),
                "arrival_time": arrival_time.strftime("%H:%M")
            })
            flight_id += 1

# Hotel data generation
hotel_data = []
hotel_id = 1

for city in cities:
    for _ in range(5):
        hotel_data.append({
            "id": hotel_id,
            "name": f"Hotel {random.randint(100, 999)}",
            "address": f"{random.randint(1, 999)} Main Street, {cities_map[city][0]}, {cities_map[city][1]}",
            "stars": random.randint(1, 5),
            "rating": round(random.uniform(5.0, 10.0), 1),
            "amenities": random.sample(["Free Wi-Fi", "Swimming pool", "Fitness center", "Restaurant", "Bar", "Room service", "Parking", "Spa"], 4),
            "price_per_night": random.randint(100, 500),
            "location": city
        })
        hotel_id += 1

# Output to JSON files
with open('flights.json', 'w') as f:
    json.dump(flight_data, f, indent=4)

with open('hotels.json', 'w') as f:
    json.dump(hotel_data, f, indent=4)

print("Mock data generated and saved to flights.json and hotels.json.")
