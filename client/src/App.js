import React, { useState } from 'react';
import {
  Container,
  Typography,
  Grid,
  Card,
  CardContent,
  Divider,
  Box,
} from '@mui/material';
import InfiniteScroll from 'react-infinite-scroll-component';
import FlightTakeoffIcon from '@mui/icons-material/FlightTakeoff';
import FlightLandIcon from '@mui/icons-material/FlightLand';
import HotelIcon from '@mui/icons-material/Hotel';
import StarIcon from '@mui/icons-material/Star';
import Loading from './Loading';
import InputForm from './InputForm';

const SERVER_URL = 'http://localhost:8080';
const loadingElementsCount = 5;

function App() {
  const [origin, setOrigin] = useState(null);
  const [days, setDays] = useState(0);
  const [budget, setBudget] = useState(0);
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [hasMore, setHasMore] = useState(true);
  const [page, setPage] = useState(1);
  const itemsPerPage = 10;
  const [isQueried, setIsQueried] = useState(false);

  const loadData = async (replace = false) => {
    setLoading(true);
    setIsQueried(true);
    console.log(results);

    const suggestResponse = await fetch(
      `${SERVER_URL}/suggest?from=${origin}&budget=${budget}&days=${days}&pageNumber=${page}&pageSize=${itemsPerPage}`
    );
    const suggestData = await suggestResponse.json();

    if (suggestData.length === 0) {
      setResults([]);
      setHasMore(false);
      setLoading(false);
      return;
    }

    // Fetch all hotel data in parallel
    const hotelData = await Promise.all(
      suggestData.map(async (data) => {
        const hotelResponse = await fetch(
          `${SERVER_URL}/hotels/${data.hotelId}`
        );
        return hotelResponse.json();
      })
    );

    // merge the hotel data information and suggestData into results
    const suggestions = suggestData.map((data, index) => {
      return {
        outboundFlight: {
          id: data.fromFlightId,
          from: data.source,
          to: data.destination,
          price: data.fromFlightPrice / 100,
        },
        returnFlight: {
          id: data.toFlightId,
          from: data.destination,
          to: data.source,
          price: data.toFlightPrice / 100,
        },
        hotel: {
          id: hotelData[index].id,
          name: hotelData[index].name,
          address: hotelData[index].address,
          location: hotelData[index].location,
          stars: hotelData[index].stars,
          rating: hotelData[index].rating,
          amenities: hotelData[index].amenities,
          price_per_day: hotelData[index].price_per_night,
        },
        totalCost: data.totalCost / 100,
      };
    });
    setResults(results => ([...(replace ? [] : results), ...suggestions]));
    setHasMore(suggestions.length >= itemsPerPage);
    setLoading(false);
  };

  const fetchMoreData = async () => {
    if (!hasMore) return;
    setPage(page + 1);
    loadData();
  };

  return (
    <Container>
      <Typography padding={2} variant="h3" gutterBottom>
        Travel Search
      </Typography>
      <InputForm
        origin={origin}
        days={days}
        budget={budget}
        setOrigin={setOrigin}
        setDays={setDays}
        setBudget={setBudget}
        loadData={loadData}
        results={results}
        setResults={setResults}
        setPage={setPage}
        setHasMore={setHasMore}
      />
      <Grid container spacing={2} style={{ marginTop: '20px' }}>
        {loading ? (
          Array.from(new Array(loadingElementsCount)).map((_, index) => (
            <Loading key={index} />
          ))
        ) : isQueried ? (
          results.length > 0 ? (
            <InfiniteScroll
              dataLength={results.length}
              next={fetchMoreData}
              hasMore={hasMore}
              loader={<Loading />}
            >
              {results.map((result, index) => (
                <Grid item xs={12} key={index}>
                  <Card
                    raised
                    style={{
                      boxShadow: '0 2px 4px rgba(0, 0, 0, 0.2)',
                      backgroundColor: '#f9f9f9',
                      margin: '20px',
                    }}
                  >
                    <CardContent>
                      <Grid container spacing={2}>
                        <Grid item xs={12} sm={6} md={4}>
                          <Box display="flex" alignItems="center" mb={2}>
                            <FlightTakeoffIcon color="primary" />
                            <Typography variant="h6" ml={1}>
                              Outbound Flight
                            </Typography>
                          </Box>
                          <Divider sx={{ marginBottom: '8px' }} />
                          <Typography>
                            Flight ID: {result.outboundFlight.id}
                          </Typography>
                          <Typography>
                            From: {result.outboundFlight.from} to{' '}
                            {result.outboundFlight.to}
                          </Typography>
                          <Typography>
                            Price: ${result.outboundFlight.price}
                          </Typography>
                        </Grid>

                        <Grid item xs={12} sm={12} md={4}>
                          <Box display="flex" alignItems="center" mb={2}>
                            <HotelIcon color="primary" />
                            <Typography variant="h6" ml={1}>
                              Hotel
                            </Typography>
                          </Box>
                          <Divider sx={{ marginBottom: '8px' }} />
                          <Typography>
                            Hotel Name: {result.hotel.name}
                          </Typography>
                          <Typography>
                            Address: {result.hotel.address}
                          </Typography>
                          <Box display="flex" alignItems="center">
                            <StarIcon color="primary" />
                            <Typography ml={1}>
                              {result.hotel.stars} Star Hotel
                            </Typography>
                          </Box>
                          <Typography>Rating: {result.hotel.rating}</Typography>
                          <Typography>
                            Amenities: {result.hotel.amenities.join(', ')}
                          </Typography>
                          <Typography>
                            Price per day: ${result.hotel.price_per_day}
                          </Typography>
                        </Grid>

                        <Grid item xs={12} sm={6} md={4}>
                          <Box display="flex" alignItems="center" mb={2}>
                            <FlightLandIcon color="primary" />
                            <Typography variant="h6" ml={1}>
                              Return Flight
                            </Typography>
                          </Box>
                          <Divider sx={{ marginBottom: '8px' }} />
                          <Typography>
                            Flight ID: {result.returnFlight.id}
                          </Typography>
                          <Typography>
                            From: {result.returnFlight.from} to{' '}
                            {result.returnFlight.to}
                          </Typography>
                          <Typography>
                            Price: ${result.returnFlight.price}
                          </Typography>
                        </Grid>
                      </Grid>
                      <Box display="flex" alignItems="center" mt={2}>
                        <Typography variant="h6">Total Cost</Typography>
                      </Box>
                      <Divider sx={{ marginBottom: '8px' }} />
                      <Typography>${result.totalCost}</Typography>
                    </CardContent>
                  </Card>
                </Grid>
              ))}
            </InfiniteScroll>
          ) : (
            <Grid item xs={12}>
              <Typography variant="h6" gutterBottom>
                No results found, Increase the budget.
              </Typography>
            </Grid>
          )
        ) : null}
      </Grid>
    </Container>
  );
}

export default App;
