import React, { useEffect, useState } from "react";

interface LocationData {
  formatted_address: string;
}

const ReverseGeocodingComponent: React.FC<{ latitude: number; longitude: number }> = ({ latitude, longitude }) => {
  const [city, setCity] = useState<string | null>(null);

  useEffect(() => {
    const fetchCityName = async () => {
      try {
        const response = await fetch(`https://maps.googleapis.com/maps/api/geocode/json?latlng=${latitude},${longitude}&key=${process.env.GOOGLE_MAPS_API_KEY}`);
        if (!response.ok) {
          throw new Error(`Failed to fetch data: ${response.status}`);
        }
        const data = await response.json();
        if (data.results && data.results.length > 0) {
          const locationData: LocationData = data.results[0];
          setCity(locationData.formatted_address);
        } else {
          setCity(null);
        }
      } catch (error) {
        console.error("Error fetching city name:", error);
        setCity(null);
      }
    };

    fetchCityName();
  }, [latitude, longitude]);

  return (
    <div>
      {city ? (
        <p>{city}</p>
      ) : (
        <p>City not found for latitude {latitude} and longitude {longitude}</p>
      )}
    </div>
  );
};

export default ReverseGeocodingComponent;