import React from "react";
import "./About.css";

const About: React.FC = () => {
    return (
        <section className="about-container">
            <div className="about-content">
                <img src="/assets/about.jpg" className="about-image" alt="About Us" />
                <div className="about-text">
                    <h2>About us</h2>
                    <p>At any given time, there are both many people looking to adopt a new pet as well as many people looking to find a new home for a pet. Pets come in all ranges and sizes, from the ant colony to the Great Dane.</p>

                    <p>At the moment, the animal adoption system is broken, and very hard to navigate. For those looking to adopt an animal, it is very hard to know where to look. There is no centralized system in place, and oftentimes, the results are found are either way too far away, or else not at all what is being looked for.</p>

                    <p>Additionally, for those looking to find a new home for a pet, it is often very hard to get the word out. Without a mainstream solution to list their pets for adoption, it can be very difficult to just use word of mouth. Many owners unable to take care of their pets may feel overwhelming pressure and not know where to look for assistance. Without a helpful resource, these individuals may resort to a more drastic measure such as abandoning pets.</p>

                    <h2>Our Solution</h2>
                    <p>Create an application for posting animals available for adoption and profiles of those who wish to adopt. Make it easy to browse available pets and include necessary contact information for each pet/shelter.</p>
                </div>
            </div>
        </section>
    );
};

export default About;