'use client'
import "./AdoptionForm.css";
import Link from "next/link";
import React, { useState, useEffect } from 'react';
import { Card, CardContent, CardMedia, Typography } from "@mui/material";
import { useRouter } from 'next/navigation'
import { useSearchParams } from 'next/navigation'


interface adoptionInterface {
    adoptionId: number;
    streetAddress: string;
    city: string;
    state: string;
    zipCode: string;
    email: string;
    phoneNumber: string;
    requestDescription: string;
}

const adoptionDefault: any = {
    'adoptionId': 0,
    "streetAddress": "",
    "city": "",
    "state": "",
    "zipCode": "",
    "email": "",
    "phoneNumber": "",
    "requestDescription": ""
}

const petDefault = {
    "petId": 1,
    "petTypeObj": {
        "petTypeId": 1,
        "name": "Capybara"
    },
    "locationId": 1,
    "name": "Paul",
    "description": "funny",
    "photoLink": "https://images.unsplash.com/photo-1633123784883-9cc9ba6d8c9e?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "petTypeId": 1
};


function AdoptionForm() {

    const [pet, setPet] = useState(petDefault);
    const [adoption, setAdoption] = useState(adoptionDefault);
    const [errors, setErrors] = useState([]);



    const adoptionURL = 'http://localhost:8080/api/adoption';
    const petURL = 'http://localhost:8080/api/pet';

    const router = useRouter();

    const searchParams = useSearchParams()

    const applicationId = searchParams.get('applicationId')
    const petId = searchParams.get('petId')
    useEffect(() => {
        console.log('checking if I have id val ' + petId);

        if (petId !== null && +petId > 0) {
            console.log('Pet ID IS > 0: ' + petId);
            fetch(`${petURL}/${petId}`)
                .then(response => {
                    if (response.status === 200) {
                        return response.json();
                    } else {
                        return Promise.reject(`Unexpected status code: ${response.status}`);
                    }
                })
                .then(data => {
                    setPet(data)
                })
                .catch(console.log);
        }

        if (applicationId !== null && +applicationId > 0) {
            console.log('App id IS > 0: ' + applicationId);
            fetch(`${adoptionURL}/${applicationId}`)
                .then(response => {
                    if (response.status === 200) {
                        return response.json();
                    } else {
                        return Promise.reject(`Unexpected status code: ${response.status}`);
                    }
                })
                .then(data => {
                    setAdoption(data)
                    setPet(data.petObj)
                })
                .catch(console.log);
        }
    }, []);

    const handleChange = (event: { target: { name: string; value: string; }; }) => {
        const newAdoption = { ...adoption };

        const field = event.target.name;
        const val = event.target.value;
        newAdoption[field] = val;

        setAdoption(newAdoption);
    }

    const handleSubmit = (event: { preventDefault: () => void; }) => {
        event.preventDefault();
        if (applicationId !== null && +applicationId > 0) {
            updateAdoption();
        } else {
            addAdoption();
        }
    }

    const addAdoption = () => {

        const tempAdoption = {
            "adoptionId": 0,
            "streetAddress": adoption.streetAddress,
            "city": adoption.city,
            "state": adoption.state,
            "zipCode": adoption.zipCode,
            "email": adoption.email,
            "phoneNumber": adoption.phoneNumber,
            "requestDescription": adoption.requestDescription,
            "userId": 1,
            "petId": pet.petId
        }

        console.log('WILL ADD ADOPTION: ' + adoption)
        const init = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(tempAdoption)
        };
        fetch(adoptionURL, init)
            .then(response => {
                if (response.status === 201 || response.status === 400) {
                    console.log('response.status' + response.status)
                    return response.json();
                } else {
                    console.log(`Unexpected status code: ${response.status}`)
                    return Promise.reject(`Unexpected status code: ${response.status}`);
                }
            })
            .then(data => {
                console.log("ERROR: " + data)
                if (data.adoptionId) {

                    router.push(`/Edit`);


                } else {
                    console.log('ERROR CREATING ADOPTION' + data)
                    console.log(data)
                    setErrors(data);
                }
            })
            .catch(console.log)
    }

    const updateAdoption = () => {

        adoption.adoptionId = applicationId;

        const init = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(adoption)
        };

        fetch(`${adoptionURL}/${applicationId}`, init)
            .then(response => {
                if (response.status === 204) {
                    return null;
                } else if (response.status === 400) {
                    return response.json();
                } else {
                    return Promise.reject(`Unexpected status code: ${response.status}`);
                }
            })
            .then(data => {
                if (!data) {
                    router.push('/Edit');
                } else {
                    setErrors(data);
                }
            })
            .catch(console.log)
    }

    const handleDelete = (adoptionId: number) => {
        if (window.confirm(`Delete Application?`)) {
            const init = {
                method: 'DELETE'
            };
            fetch(`${adoptionURL}/${adoptionId}`, init)
                .then(response => {
                    if (response.status === 204) {
                        router.push("/Edit");
                    } else {
                        return Promise.reject(`Unexpected Status Code: ${response.status}`);
                    }
                })
                .catch(console.log);
        }
    }


    return (<>
        <section className="adoption-container">
            <div className="adoption-content">
                <div className="adoptionForm">
                    <div className="cardAdoptionForm">
                        <Card className="pet-adoption-card">
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
                                    {pet.name}
                                </Typography>
                                <Typography variant="body2" color="text.secondary">
                                    Location: {pet.locationId}
                                </Typography>
                                <Typography variant="body2" color="text.secondary" className="description">
                                    {pet.description}
                                </Typography>
                            </CardContent>
                        </Card>
                    </div>
                </div>
                <div className="app-form">
                    {errors.length > 0 && (
                        <div className='alert alert-danger'>
                            <p>The following Errors were found:</p>
                            <ul>
                                {errors.map(error => (
                                    <li key={error}>{error}</li>
                                ))}
                            </ul>
                        </div>
                    )}
                    <form onSubmit={handleSubmit}>
                        <fieldset className='form-group'>
                            <label htmlFor='streetAddress'>Street Address</label>
                            <input
                                id='streetAddress'
                                name='streetAddress'
                                type='text'
                                className='form form-control'
                                value={adoption.streetAddress}
                                onChange={handleChange}
                            />
                        </fieldset>
                        <fieldset className='form-group'>
                            <label htmlFor='city'>City</label>
                            <input id='city'
                                name='city'
                                type='text'
                                className='form form-control'
                                value={adoption.city}
                                onChange={handleChange}
                            />
                        </fieldset>
                        <fieldset className='form-group'>
                            <label htmlFor='state'>State</label>
                            <input
                                id='state'
                                name='state'
                                type='text'
                                className='form form-control'
                                value={adoption.state}
                                onChange={handleChange}
                            />
                        </fieldset>
                        <fieldset className='form-group'>
                            <label htmlFor='zipCode'>Zip Code</label>
                            <input
                                id='zipCode'
                                name='zipCode'
                                type='text'
                                className='form form-control'
                                value={adoption.zipCode}
                                onChange={handleChange}
                            />
                        </fieldset>
                        <fieldset className='form-group'>
                            <label htmlFor='email'>Email</label>
                            <input
                                id='email'
                                name='email'
                                type='text'
                                className='form form-control'
                                value={adoption.email}
                                onChange={handleChange}
                            />
                        </fieldset>
                        <fieldset className='form-group'>
                            <label htmlFor='phoneNumber'>Phone Number</label>
                            <input
                                id='phoneNumber'
                                name='phoneNumber'
                                type='text'
                                className='form form-control'
                                value={adoption.phoneNumber}
                                onChange={handleChange}
                            />
                        </fieldset>
                        <fieldset className='form-group'>
                            <label htmlFor='requestDescription'>Additional Information</label>
                            <input
                                id='requestDescription'
                                name='requestDescription'
                                type='text'
                                className='form form-control'
                                value={adoption.requestDescription}
                                onChange={handleChange}
                            />
                        </fieldset>
                        <div className='mt-4'>
                            <button className='btn btn-success mr-2' type="submit">{applicationId ? 'Update Application' : 'Send Application'}</button>
                            <Link href={"/Edit"} className='btn btn-warning' type="button">Cancel</Link>
                            <button className='btn-danger' type="button" onClick={() => handleDelete(adoption.adoptionId)}>{'Delete'}
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </section>
    </>
    );

}

export default AdoptionForm;
