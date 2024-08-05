import { useEffect, useState } from "react";

export interface PetLocation {
    locationId: number;
    userId: number;
    latitude: number;
    longitude: number;
}

const url = 'http://localhost:8080/api/location';

export function useFetchPetLocation(): Record<number, PetLocation> {
    const [petLocations, setPetLocations] = useState<Record<number, PetLocation>>([]);

    useEffect(() => {
        fetch(url)
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error(`Failed to fetch data: ${response.status}`);
                }
            })
            .then(data => {
                const locationsObject = data.reduce((acc: Record<number, PetLocation>, location: PetLocation) => {
                    acc[location.locationId] = location;
                    return acc;
                }, {});
                setPetLocations(locationsObject);
            })
            .catch(error => {
                console.error("Error fetching pet location data:", error);
            });
    }, []);

    return petLocations;
}