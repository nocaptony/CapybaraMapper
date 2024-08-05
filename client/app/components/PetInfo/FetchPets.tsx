"use client"
import { useEffect, useState } from "react";

export interface Pet {
    petId: number;
    petTypeId: number;
    petType: string;
    locationId: number;
    name: string;
    description: string;
    photoLink: string;
}

const url = 'http://localhost:8080/api/pet';

export function useFetchPets(): Pet[] {
    const [pets, setPets] = useState<Pet[]>([]);

    useEffect(() => {
        fetch(url)
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error(`Failed to fetch data: ${response.status}`);
                }
            })
            .then(data => setPets(data))
            .catch(error => {
                console.error("Error fetching pets data:", error);
            });
    }, [url]);

    return pets;
}