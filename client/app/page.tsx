import React from "react";
import "./page.css";
import Button from "./components/Button/Button";
import { AnimalPhotos } from "./components/Data";
import AvailablePets from "./components/PetAdoption/AvailablePets";

const Home: React.FC = () => {
  return (
    <>
      <section className="home">
        <div className="container">
          <div className="text-content">
            <h1>Capybara Mapper</h1>
            <p>Create a lifelong friend.</p>
            <Button LinkText="View Details" LinkTo="/About"/>
          </div>
        </div>
      </section>
      <section className="images">
        {AnimalPhotos.map((Img) =>( 
          <div className="images-box" key={Img.id}>
            <img src={Img.ImgUrl} alt=""/>
          </div>
        ))} 
      </section>
      <AvailablePets />
    </>
  );
};

export default Home;