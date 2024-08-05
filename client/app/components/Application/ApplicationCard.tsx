import React, { useEffect, useState } from "react";
import { Card, CardContent, CardMedia, Typography } from "@mui/material";
import { Application } from "./FetchApplications";
import Button from "../Button/Button";
import { useFetchPets, Pet } from "../PetInfo/FetchPets";
import "./ApplicationCard.css";

interface ApplicationCardProps {
    application: Application;
}

const ApplicationCard: React.FC<ApplicationCardProps> = ({ application }) => {
    const [petName, setPetName] = useState<string>("");
    const [petImage, setPetImage] = useState<string>("");
    const { petId } = application;
    const pets = useFetchPets();

    useEffect(() => {
        if (pets.length > 0) {
            const pet: Pet | undefined = pets.find((pet) => pet.petId === petId);
            if (pet) {
                setPetName(pet.name);
                setPetImage(pet.photoLink);
            }
        }
    }, [pets, petId]);

    return (
        <Card key={application.adoptionId} className="application-card">
            <CardMedia
                        component="img"
                        height="140"
                        image={petImage}
                        alt={petName}
                    />
            <CardContent>
                <Typography variant="h5" component="div">
                    Pet Name: {petName}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                    User ID: {application.userId}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                    Address: {application.streetAddress}, {application.city},{" "}
                    {application.state}, {application.zipCode}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                    Email: {application.email}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                    Phone Number: {application.phoneNumber}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                    Description: {application.requestDescription}
                </Typography>
                <Button
                    LinkText="Edit Application"
                    LinkTo={`/AdoptionForm?applicationId=${application.adoptionId}&petId=${application.petId}`}
                />
            </CardContent>
        </Card>
    );
};

export default ApplicationCard;