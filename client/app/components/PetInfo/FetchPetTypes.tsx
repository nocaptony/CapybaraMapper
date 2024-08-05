import { useEffect, useState } from "react";

export interface PetType {
    petTypeId: number;
    name: string;
}

const url = 'http://localhost:8080/api/petType';

export function useFetchPetTypes(): string[] {
    const [petTypes, setPetTypes] = useState<string[]>([]);

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
                const petTypeNames = data.map((petType: PetType) => petType.name);
                setPetTypes(petTypeNames);
            })
            .catch(error => {
                console.error("Error fetching pet types data:", error);
            });
    }, [url]);

    return petTypes;
}