"use client"
import React, { useState, useEffect } from "react";
import Link from "next/link";
import "./Navbar.css";
import { LuUser2, LuSearch } from "react-icons/lu";

const NavLinks = [
    {
        LinkText: "Home",
        LinkTo: "/",
    }, 
    {
        LinkText: "My Applications",
        LinkTo: "/Edit",
    }, 
    {
        LinkText: "About Us",
        LinkTo: "/About",
    }, 
];

const Navbar: React.FC = () => {
    const [scrolled, setScrolled] = useState(false);

    useEffect(() => {
        const handleScroll = () => {
            const offset = window.scrollY;
            if (offset > 50) {
                setScrolled(true);
            } else {
                setScrolled(false);
            }
        };

        window.addEventListener("scroll", handleScroll);

        return () => {
            window.removeEventListener("scroll", handleScroll);
        };
    }, []);

    const navbarClass = ["navbar-main", scrolled ? "navbar-scrolled" : ""].join(" ");

    return (
        <>
            <nav className={navbarClass}>
                <Link href={"/"}>
                    <img src="/assets/Logo.png" alt="Logo" className="navbar-main-logo" />
                </Link>

                <div className="navbar">
                    {NavLinks.map(({ LinkText, LinkTo }, index) => (
                        <div className="navlink" key={index}>
                            <Link href={LinkTo}>{LinkText}</Link>
                        </div>
                    ))}
                </div>

                <div className="navbar-icons">
                    <Link href={"/"}>
                        <LuUser2 />
                    </Link>
                    <Link href={"/Search"}>
                        <LuSearch />
                    </Link>
                </div>
            </nav>
        </>
    );
};

export default Navbar;