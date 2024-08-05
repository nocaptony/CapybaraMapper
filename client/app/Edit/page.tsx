"use client"
import React from "react";
import { useFetchApplications } from "../components/Application/FetchApplications";
import ApplicationCard from "../components/Application/ApplicationCard";
import "./Edit.css"

const Edit: React.FC = () => {
    const applications = useFetchApplications();

    return (
        <>
            <section className="edit-container">
            <h2 className="header">Applications</h2>
                <div className="edit-content">
                
                    {applications ? (
                        applications.map((application, index) => (
                            <ApplicationCard key={index} application={application} />
                        ))
                    ) : (
                        <p className="loading-text">Loading applications...</p>
                    )}
                </div>
            </section>
        </>
    );
};

export default Edit;