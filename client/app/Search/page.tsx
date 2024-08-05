"use client"
import React, { useState } from "react";
import "./Search.css";
import { Card, CardContent, CardMedia, Typography, Button } from "@mui/material";
import { useFetchPets, Pet } from "../components/PetInfo/FetchPets";
import { useFetchPetTypes } from "../components/PetInfo/FetchPetTypes";

const Search: React.FC = () => {
    const pets = useFetchPets();
    const petTypes = useFetchPetTypes();
    const [selectedPetType, setSelectedPetType] = useState<string>("");
    const [searchedPetName, setSearchedPetName] = useState<string>("");
    const [searchResults, setSearchResults] = useState<Pet[]>([]);

    const searchPets = () => {
        const filteredResult = pets.filter(pet =>
            (selectedPetType === "" || pet.petType === selectedPetType) &&
            (searchedPetName === "" || pet.name.toLowerCase().includes(searchedPetName.toLowerCase()))
        );
        setSearchResults(filteredResult);
    };

    return (
        <>
            <section>
                <div className="search-container">
                    <div className="search-content">
                        <h6>Search page</h6>
                        <select value={selectedPetType} onChange={(e) => setSelectedPetType(e.target.value)}>
                            <option value="">Select Pet Type</option>
                            {petTypes.map((type, index) => (
                                <option key={index} value={type}>{type}</option>
                            ))}
                        </select>
                        <input
                            type="text"
                            placeholder="Enter Pet Name"
                            value={searchedPetName}
                            onChange={(e) => setSearchedPetName(e.target.value)}
                        />
                        <Button onClick={searchPets}>Search</Button>
                    </div>
                </div>
            </section>
            <div className="pet-adoption-row">
                {searchResults.map((pet, index) => (
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
                                {pet.petType}
                            </Typography>
                        </CardContent>
                    </Card>
                ))}
            </div>
        </>
    );
};

export default Search;