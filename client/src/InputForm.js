import React, { useState } from 'react';
import { Grid, TextField, Button } from '@mui/material';
import Autocomplete from '@mui/material/Autocomplete';

const cities = [
  { label: 'New York City (JFK)', value: 'JFK' },
  { label: 'Los Angeles (LAX)', value: 'LAX' },
  { label: 'London (LHR)', value: 'LHR' },
  { label: 'Tokyo (NRT)', value: 'NRT' },
  { label: 'Sydney (SYD)', value: 'SYD' },
];

const InputForm = ({
  origin,
  days,
  budget,
  setOrigin,
  setDays,
  setBudget,
  loadData,
  results,
  setResults,
  setPage,
  setHasMore,
}) => {
  const [originError, setOriginError] = useState('');
  const [daysError, setDaysError] = useState('');
  const [budgetError, setBudgetError] = useState('');

  const handleSearch = async () => {
    setHasMore(true);
    setPage(1);
    if (!origin) {
      setOriginError('Please select an origin.');
      return;
    } else {
      setOriginError('');
    }

    if (days <= 0) {
      setDaysError('Number of days should be a positive number.');
      return;
    } else {
      setDaysError('');
    }

    if (budget <= 0) {
      setBudgetError('Budget should be a positive number.');
      return;
    } else {
      setBudgetError('');
    }
    loadData(true);
  };

  return (
    <Grid container spacing={2} alignItems="center">
      <Grid item xs={3}>
        <Autocomplete
          options={cities}
          getOptionLabel={(option) => option.label}
          value={cities.find((city) => city.value === origin) || null}
          onChange={(event, newValue) => {
            setOrigin(newValue ? newValue.value : null);
            if (newValue) {
              setOriginError('');
            }
          }}
          isOptionEqualToValue={(option, value) => option.value === value.value}
          renderInput={(params) => (
            <TextField
              {...params}
              label="Origin"
              variant="outlined"
              fullWidth
              error={!!originError}
              helperText={originError}
            />
          )}
          sx={{ height: '100%' }}
        />
      </Grid>
      <Grid item xs={3}>
        <TextField
          type="number"
          label="Number of Days"
          variant="outlined"
          value={days}
          onChange={(e) => setDays(e.target.value)}
          onBlur={() => {
            if (days <= 0) {
              setDaysError('Number of days should be a positive number.');
            } else {
              setDaysError('');
            }
          }}
          error={!!daysError}
          helperText={daysError}
          fullWidth
          sx={{ height: '100%' }}
        />
      </Grid>
      <Grid item xs={3}>
        <TextField
          type="number"
          label="Budget"
          variant="outlined"
          value={budget}
          onChange={(e) => setBudget(e.target.value)}
          onBlur={() => {
            if (budget <= 0) {
              setBudgetError('Budget should be a positive number.');
            } else {
              setBudgetError('');
            }
          }}
          error={!!budgetError}
          helperText={budgetError}
          fullWidth
          sx={{ height: '100%' }}
        />
      </Grid>
      <Grid item xs={3}>
        <Button
          variant="contained"
          color="primary"
          onClick={handleSearch}
          fullWidth
          sx={{ height: '100%' }}
        >
          Search
        </Button>
      </Grid>
    </Grid>
  );
};

export default InputForm;
