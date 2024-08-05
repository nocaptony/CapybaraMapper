import React from "react";
import { Card, CardContent, CardMedia, Typography } from "@mui/material";
import Button from "../Button/Button";
import { useFetchPets } from "../PetInfo/FetchPets";
import { useFetchPetTypes } from "../PetInfo/FetchPetTypes";
import { useFetchPetLocation } from "../PetInfo/FetchPetLocation";
import "./PetAdoptionBox.css";
import ReverseGeocodingComponent from "../Geocoder/ReverseGeocode";

interface PetAdoptionBoxProps {
    selectedCategory: string;
}

const PetAdoptionBox: React.FC<PetAdoptionBoxProps> = ({ selectedCategory }) => {
    const pets = useFetchPets();
    const petTypes = useFetchPetTypes();
    const petLocations = useFetchPetLocation();

    const filteredPets = selectedCategory === "All" ? pets : pets.filter(pet => petTypes[pet.petTypeId - 1] === selectedCategory);

    return (
        <div className="pet-adoption-row">
            {filteredPets.map((pet, index) => (
                <Card key={index} className="pet-adoption-card">
                    <CardMedia
                        component="img"
                        height="140"
                        image={pet.photoLink}
                        alt={pet.name}
                    />
                    <CardContent>
                        <Typography gutterBottom variant="h5" component="div">
                            {pet.name}
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            {petTypes[pet.petTypeId - 1]}
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            {petLocations[pet.locationId] ? (
                                <ReverseGeocodingComponent
                                    latitude={petLocations[pet.locationId].latitude}
                                    longitude={petLocations[pet.locationId].longitude}
                                />
                            ) : (
                                "Location not available"
                            )}
                        </Typography>
                        <Typography variant="body2" color="text.secondary" className="description">
                            {pet.description}
                        </Typography>
                        <Button LinkText="Fill Out Application" LinkTo={`/AdoptionForm?petId=${pet.petId}`} />
                    </CardContent>
                </Card>
            ))}
        </div>
    );
};

export default PetAdoptionBox;