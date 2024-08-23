package com.travelwits.travel_suggest.entity;

import com.travelwits.travel_suggest.dto.SuggestionDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "FLIGHTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SqlResultSetMappings(value = {
        @SqlResultSetMapping(name = "suggestionMapping", classes = {
                @ConstructorResult(targetClass = SuggestionDto.class, columns = {
                        @ColumnResult(name = "fromFlightId", type = Integer.class),
                        @ColumnResult(name = "returnFlightId", type = Integer.class),
                        @ColumnResult(name = "from", type = String.class),
                        @ColumnResult(name = "to", type = String.class),
                        @ColumnResult(name = "fromFlightPrice", type = Integer.class),
                        @ColumnResult(name = "returnFlightPrice", type = Integer.class),
                        @ColumnResult(name = "hotelId", type = Integer.class),
                        @ColumnResult(name = "hotelPrice", type = Integer.class),
                        @ColumnResult(name = "totalCost", type = Integer.class)
                })
        })
})
@NamedNativeQuery(name = "suggestionQuery", query = "select f1.flight_id as fromFlightId,\n" +
        "       f2.flight_id as returnFlightId,\n" +
        "       f1.src as from,\n" +
        "       f1.dest as to,\n" +
        "       f1.price as fromFlightPrice,\n" +
        "       f2.price as returnFlightPrice,\n" +
        "       h.hotel_id as hotelId,\n" +
        "       h.price as hotelPrice,\n" +
        "       (f1.price + f2.price + :days * h.price) as totalCost\n" +
        "      from flights f1\n" +
        "    join flights f2 on f1.dest = f2.src and f2.dest = f1.src\n" +
        "    join hotels h on f1.dest = h.city\n" +
        "where f1.src = :src and (f1.price + f2.price + :days * h.price) <= :budget\n" +
        "order by totalCost asc limit :lmt offset :ofst", resultSetMapping = "suggestionMapping")
public class Flights {

    @Id
    @Column(name = "FLIGHT_ID")
    Integer flightId;

    String src;
    String dest;
    Integer price;

    @Column(name = "DEPARTURE_TIME")
    LocalTime departureTime;

    @Column(name = "ARRIVAL_TIME")
    LocalTime arrivalTime;

    @Column(name = "LAST_UPDATED")
    private Date lastUpdated;

}
