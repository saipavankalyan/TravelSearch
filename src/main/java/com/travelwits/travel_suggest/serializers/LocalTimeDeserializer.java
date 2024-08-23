package com.travelwits.travel_suggest.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.travelwits.travel_suggest.constants.ApplicationConstants;

import java.io.IOException;
import java.time.LocalTime;

import static com.travelwits.travel_suggest.constants.ApplicationConstants.TIME_FORMAT;

public class LocalTimeDeserializer extends StdDeserializer<LocalTime> {

    public LocalTimeDeserializer() {
        super(LocalTime.class);
    }

    @Override
    public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, IOException {
        String timeString = jsonParser.readValueAs(String.class);
        return LocalTime.parse(timeString, TIME_FORMAT);
    }
}
