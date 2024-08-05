import { useEffect, useState } from "react";

export interface Application {
    adoptionId: number;
    petId: number;
    userId: string;
    streetAddress: number;
    city: string;
    state: string;
    zipCode: string;
    email: string;
    phoneNumber: string;
    requestDescription: string;
    petObj: {
        name: string;
        petTypeObj: {
            name: string;
        };
    };
}

const url = 'http://localhost:8080/api/adoption';

export function useFetchApplications(): Application[] | null {
    const [applications, setApplications] = useState<Application[] | null>(null);

    useEffect(() => {
        fetch(url)
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error(`Failed to fetch data: ${response.status}`);
                }
            })
            .then(data => setApplications(data))
            .catch(error => {
                console.error("Error fetching pets data:", error);
            });
    }, [url]);

    return applications;
}