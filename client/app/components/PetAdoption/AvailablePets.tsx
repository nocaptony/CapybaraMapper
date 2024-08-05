"use client"
import React, { useState } from "react";
import PetAdoptionBox from "./PetAdoptionBox";
import { useFetchPetTypes } from "../PetInfo/FetchPetTypes";
import "./AvailablePets.css"

const AvailablePets: React.FC = () => {
    const petTypes = useFetchPetTypes();
    const [selectedCategory, setSelectedCategory] = useState("All");
    const handleCategoryChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedCategory(event.target.value);
    };

    return (
        <>
            <section className="pet-adoption">
                <h2>Pets Available For Adoption</h2>
                <div className="dropdown-container">
                    <select value={selectedCategory} onChange={handleCategoryChange} className="dropdown">
                        {petTypes ? (
                            <>
                                <option value="All">All</option>
                                {petTypes.map((category, index) => (
                                    <option value={category} key={index}>{category}</option>
                                ))}
                            </>
                        ) : (
                            <p>Loading...</p>
                        )}
                    </select>
                </div>
            </section>

            <PetAdoptionBox selectedCategory={selectedCategory} />
        </>
    );
};

export default AvailablePets;