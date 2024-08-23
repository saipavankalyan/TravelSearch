import React from 'react';
import { Grid, Card, CardContent, Skeleton } from '@mui/material';

const Loading = () => {
  return (
    <Grid item xs={9}>
      <Card>
        <CardContent>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6} md={4}>
              <Skeleton variant="rectangular" height={30} width="80%" />
              <Skeleton variant="text" />
              <Skeleton variant="text" />
              <Skeleton variant="text" />
            </Grid>
            <Grid item xs={12} sm={6} md={4}>
              <Skeleton variant="rectangular" height={30} width="80%" />
              <Skeleton variant="text" />
              <Skeleton variant="text" />
              <Skeleton variant="text" />
            </Grid>
            <Grid item xs={12} sm={12} md={4}>
              <Skeleton variant="rectangular" height={30} width="80%" />
              <Skeleton variant="text" />
              <Skeleton variant="text" />
              <Skeleton variant="text" />
            </Grid>
          </Grid>
          <Skeleton variant="rectangular" height={30} width="20%" />
          <Skeleton variant="text" />
        </CardContent>
      </Card>
    </Grid>
  );
};

export default Loading;
